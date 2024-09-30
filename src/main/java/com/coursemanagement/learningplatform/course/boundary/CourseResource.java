package com.coursemanagement.learningplatform.course.boundary;

import com.coursemanagement.learningplatform.course.CourseRecommender;
import com.coursemanagement.learningplatform.course.control.CourseController;
import com.coursemanagement.learningplatform.course.entity.Course;
import com.coursemanagement.learningplatform.course.entity.CourseRequest;
import com.coursemanagement.learningplatform.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseResource {
    private final CourseRepository courseRepository;
    private final CourseController courseController;
    private final CourseRecommender courseRecommender;

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable("id") long id) {
        return ResponseEntity.ok(courseRepository.viewCourse(id));
    }

    @PostMapping
    public ResponseEntity<String> addCourse(@RequestBody CourseRequest course) {
        courseRepository.addCourse(courseController.mapToCourseEntity(course));
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/discover")
    public ResponseEntity<Course> discover() {
        return ResponseEntity.ok(courseRecommender.recommendCourse(0));
    }

    @GetMapping("/discover/{studyFieldId}")
    public ResponseEntity<Course> discoverByStudyField(@PathVariable("studyFieldId") long studyFieldId) {
        return ResponseEntity.ok(courseRecommender.recommendCourse(studyFieldId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable("id") long id) {
        courseRepository.deleteCourse(id);
        return ResponseEntity.ok("Success");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCourse(@PathVariable("id") long id, @RequestBody CourseRequest course) {
        courseRepository.updateCourse(courseController.mapToCourseEntity(id, course));
        return ResponseEntity.ok("Success");
    }

}
