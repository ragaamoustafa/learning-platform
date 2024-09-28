package com.coursemanagement.learningplatform.course.entity;

import com.coursemanagement.learningplatform.studyfield.entity.StudyField;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
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

    @ManyToOne
    @JoinColumn(name = "sf_id", referencedColumnName = "id")
    private StudyField studyField; // Link to StudyField entity
}
