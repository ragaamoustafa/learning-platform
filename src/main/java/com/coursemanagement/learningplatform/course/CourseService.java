package com.coursemanagement.learningplatform.course;

import com.coursemanagement.learningplatform.course.entity.RecommenderType;
import com.coursemanagement.learningplatform.studyfield.entity.StudyField;
import com.coursemanagement.learningplatform.studyfield.repository.StudyFieldRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Slf4j
@Service // Business logic
public class CourseService {

    @Autowired
    private CourseRecommender courseRecommender;

    @Autowired
    @Qualifier("filterCoursesByStudyField")
    private CourseRecommender filterCoursesByStudyField;

    @Autowired
    private StudyFieldRepository studyFieldRepository;


    private final Scanner scanner = new Scanner(System.in);

    @EventListener(ApplicationReadyEvent.class)
    public void startApplication() {
        log.info("Welcome to the Course Recommender Application");
        chooseCourseType();
    }

    private void chooseCourseType() {
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.println("Choose an option:");
                System.out.println("1. Get a random course");
                System.out.println("2. Get courses by study field");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        getRandomCourse(RecommenderType.ALL);
                        validInput = true; // Input was valid, exit the loop
                        break;
                    case 2:
                        getRandomCourse(RecommenderType.SFID);
                        validInput = true; // Input was valid, exit the loop
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) { // Handle non-integer input
                System.out.println("Invalid input. Please enter either 1 or 2 to select an option.");
                scanner.nextLine();
            }
        }

        continueRecommendation(); // Proceed to the next step after valid input
    }

    private void getRandomCourse(RecommenderType type) {
        String courseName;

        switch (type) {
            case SFID:
                courseName = getRandomCourseByStudyField();
                break;
            case ALL:
            default:
                courseName = courseRecommender.recommendCourse(0);
                break;
        }

        if (courseName != null) {
            System.out.println("Recommended Course: " + courseName);
        } else {
            System.out.println("No courses available.");
        }
    }

    private String getRandomCourseByStudyField() {
        List<StudyField> studyFields = studyFieldRepository.findAll();

        if (!studyFields.isEmpty()) {
            System.out.println("Available Study Fields:");
            studyFields.stream().map(studyField -> studyField.getId() + ". " + studyField.getName()).forEach(System.out::println);

            long sfId = 0;
            boolean validInput = false;

            while (!validInput) {
                //select a study field
                System.out.print("Choose a study field by ID: ");

                try {
                    sfId = scanner.nextLong();
                    scanner.nextLine();
                    validInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid choice: Please select the study field by entering its number from the available options.");
                    scanner.nextLine();
                }
            }
            return filterCoursesByStudyField.recommendCourse(sfId);
        } else {
            System.out.println("No study fields available.");
            return null;
        }
    }

    private void continueRecommendation() {
        System.out.print("Do you want to get another course recommendation? (yes/no): ");
        String answer = scanner.nextLine();

        if (answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")) {
            chooseCourseType(); // Go back to choose course type
        } else {
            System.out.println("Thank you for using the Course Recommender Application! Goodbye!");
            System.exit(0);
        }
    }
}
