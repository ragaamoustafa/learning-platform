package com.coursemanagement.learningplatform.studyfield.entity;

import com.coursemanagement.learningplatform.course.entity.Course;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Entity
@Table(name = "study_field")
@Getter
@Setter
public class StudyField {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "studyField", cascade = CascadeType.ALL)
    private Set<Course> courses; // Link back to Course entities
}
