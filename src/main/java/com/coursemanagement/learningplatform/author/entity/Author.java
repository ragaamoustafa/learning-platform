package com.coursemanagement.learningplatform.author.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "author")
@Getter
@Setter
public class Author {
    @Id
    private long id;

    private String name;
    private String email;
    private String birthdate;


}
