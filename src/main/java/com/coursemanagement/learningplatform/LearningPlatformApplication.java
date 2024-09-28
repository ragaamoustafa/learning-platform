package com.coursemanagement.learningplatform;

import com.coursemanagement.learningplatform.course.CourseService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication // equivalent to using @Configuration, @EnableAutoConfiguration, and @ComponentScan.
public class LearningPlatformApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(LearningPlatformApplication.class, args);

        // Get the CourseService bean from the Spring context and start the application
        CourseService courseService = context.getBean(CourseService.class);
//        courseService.startApplication();
    }
}
