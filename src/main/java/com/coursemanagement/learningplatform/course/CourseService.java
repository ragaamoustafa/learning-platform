package com.coursemanagement.learningplatform.course;

import com.coursemanagement.learningplatform.course.entity.Course;
import com.coursemanagement.learningplatform.course.entity.RecommenderType;
import com.coursemanagement.learningplatform.course.repository.CourseRepositoryImpl;
import com.coursemanagement.learningplatform.school.SchoolA;
import com.coursemanagement.learningplatform.school.SchoolB;
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
    private StudyFieldRepository studyFieldRepository;

    @Autowired
    private CourseRepositoryImpl courseRepository;

    @Autowired
    @Qualifier("schoolAByBeanInjection")
    private SchoolA schoolA;

    @Autowired
    @Qualifier("schoolBByBeanInjection")
    private SchoolB schoolB;

    private final Scanner scanner = new Scanner(System.in);

    @EventListener(ApplicationReadyEvent.class)
    public void startApplication() {
        log.info("Welcome to the Course Recommender Application");
        chooseOption();
    }

    // Updated method name and structure for options
    private void chooseOption() {
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.println("Choose an option:");
                System.out.println("1. Get a random course");
                System.out.println("2. Get courses by study field");
                System.out.println("3. Perform Admin operations");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        getRandomCourse(RecommenderType.ALL);
                        validInput = true;
                        break;
                    case 2:
                        getRandomCourse(RecommenderType.SFID);
                        validInput = true;
                        break;
                    case 3:
                        adminOperations(); // Handle Admin operations
                        validInput = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }

        continueRecommendation();
    }

    // Admin operations handler
    private void adminOperations() {
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.println("Choose an Admin operation:");
                System.out.println("1. Add a new course");
                System.out.println("2. Update an existing course");
                System.out.println("3. Delete a course");
                System.out.println("4. View all courses");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        addCourse();
                        validInput = true;
                        break;
                    case 2:
                        updateCourse(); // Update course operation
                        validInput = true;
                        break;
                    case 3:
                        deleteCourse(); // Delete course operation
                        validInput = true;
                        break;
                    case 4:
                        viewAllCourses(); // View all courses
                        validInput = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 4.");
                scanner.nextLine();
            }
        }

        continueRecommendation();
    }

    private void updateCourse() {
        // Show all courses first
        viewAllCourses();

        // Get course ID to update
        System.out.print("Enter the ID of the course to update: ");
        long id = scanner.nextLong();
        scanner.nextLine();

        // Proceed to update the course
        System.out.print("New name: ");
        String name = scanner.nextLine();

        System.out.print("New description: ");
        String description = scanner.nextLine();

        System.out.print("New credit: ");
        Integer credit = scanner.nextInt();

        System.out.print("New Study Field ID: ");
        long studyFieldId = scanner.nextLong();
        scanner.nextLine();

        StudyField studyField = studyFieldRepository.findById(studyFieldId).orElse(null);



        Course course = new Course();
        course.setId(id);
        course.setName(name);
        course.setDescription(description);
        course.setCredit(credit);
        while (studyField == null) {
            if(!studyFieldInvalidMessage()) {
                chooseOption();
                return;
            }
            viewAllStudyFields();
            System.out.print("Study Field ID: ");
            studyFieldId = scanner.nextLong();
            scanner.nextLine();
            studyField = studyFieldRepository.findById(studyFieldId).orElse(null);

        }

        course.setStudyField(studyField);

        courseRepository.updateCourse(course);
        System.out.println("Course updated successfully.");
    }

    private void deleteCourse() {
        // Show all courses first
        viewAllCourses();

        // Get course ID to delete
        System.out.print("Enter the ID of the course to delete: ");
        long id = scanner.nextLong();
        scanner.nextLine();

        courseRepository.deleteCourse(id);
        System.out.println("Course deleted successfully.");
    }

    private void addCourse() {
        System.out.println("Enter course details:");

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Credit: ");
        Integer credit = scanner.nextInt();

        viewAllStudyFields();
        System.out.print("Study Field ID: ");
        long studyFieldId = scanner.nextLong();
        scanner.nextLine();

        StudyField studyField = studyFieldRepository.findById(studyFieldId).orElse(null);



        Course course = new Course();
        // ID will auto-increment in the database
        course.setName(name);
        course.setDescription(description);
        course.setCredit(credit);
        while(studyField == null) {
            if(!studyFieldInvalidMessage()) {
                chooseOption();
                return;
            }
            viewAllStudyFields();
            System.out.print("Study Field ID: ");
            studyFieldId = scanner.nextLong();
            scanner.nextLine();
            studyField = studyFieldRepository.findById(studyFieldId).orElse(null);
        }
        course.setStudyField(studyField);

        courseRepository.addCourse(course);
        System.out.println("Course added successfully.");
    }

    private boolean studyFieldInvalidMessage() {
        System.out.println("Invalid study field");
        System.out.println("Choose an option: ");
        System.out.println("1) Retry.");
        System.out.println("2) Back to main menu.");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice == 1;
    }

    private void getRandomCourse(RecommenderType type) {
        String courseName;

        switch (type) {
            case SFID:
                courseName = getRandomCourseByStudyField();
                break;
            case ALL:
            default:
                courseName = schoolA.recommendCourse(0).getName();
                break;
        }

        if (courseName != null) {
            System.out.println("Recommended Course: " + courseName);
        } else {
            System.out.println("No courses available.");
        }
    }

    private void viewAllStudyFields(){
        List<StudyField> studyFields = studyFieldRepository.findAll();
        if (!studyFields.isEmpty()) {
            System.out.println("Available Study Fields:");
            studyFields.stream().map(studyField -> studyField.getId() + ". " + studyField.getName()).forEach(System.out::println);
        }
        else {
            System.out.println("No study fields available.");
        }
    }

    private String getRandomCourseByStudyField() {
        viewAllStudyFields();
            long sfId = 0;
            boolean validInput = false;

            while (!validInput) {
                System.out.print("Choose a study field by ID: ");
                try {
                    sfId = scanner.nextLong();
                    scanner.nextLine();
                    validInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid choice: Please select a study field by entering its number.");
                    scanner.nextLine();
                }
            }
        return schoolB.recommendCourse(sfId).getName();

    }

    private void viewAllCourses() {
        List<Course> courses = courseRepository.findAll();

        if (courses.isEmpty()) {
            System.out.println("No courses available.");
        } else {
            System.out.println("Available Courses:");
            for (Course course : courses) {
                System.out.println(course.getId() + ": " + course.getName() + " (" + course.getStudyField().getName() + ")");
            }
        }
    }

    private void continueRecommendation() {
        System.out.print("Do you want to perform another operation? (yes/no): ");
        String answer = scanner.nextLine();

        if (answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")) {
            chooseOption();
        } else {
            System.out.println("Thank you for using the Course Recommender Application! Goodbye!");
            System.exit(0);
        }
    }
}
