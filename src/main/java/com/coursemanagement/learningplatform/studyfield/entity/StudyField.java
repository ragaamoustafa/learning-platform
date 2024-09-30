package com.coursemanagement.learningplatform.studyfield.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "study_field")
@Getter
@Setter
public class StudyField {

    @Id
    private long id;

    @Column(nullable = false)
    private String name;
}
