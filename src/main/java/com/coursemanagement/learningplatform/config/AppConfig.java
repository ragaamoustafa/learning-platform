package com.coursemanagement.learningplatform.config;

import com.coursemanagement.learningplatform.course.repository.CourseRepository;
import com.coursemanagement.learningplatform.school.SchoolA;
import com.coursemanagement.learningplatform.school.SchoolB;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final CourseRepository courseRepository;
    @Bean("schoolAByBeanInjection")
    public SchoolA schoolA() {
        return new SchoolA(courseRepository);
    }

    @Bean("schoolBByBeanInjection")
    public SchoolB schoolC() {
        return new SchoolB(courseRepository);
    }

}
