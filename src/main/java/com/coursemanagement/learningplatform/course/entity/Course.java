package com.coursemanagement.learningplatform.course.entity;

import com.coursemanagement.learningplatform.studyfield.entity.StudyField;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "course")
@Getter
@Setter
public class Course {

    @Id
    private long id;
    private String name;
    private String description;
    private Integer credit;

    @ManyToOne
    @JoinColumn(name = "sf_id", referencedColumnName = "id")
    private StudyField studyField; // Link to StudyField entity
}
