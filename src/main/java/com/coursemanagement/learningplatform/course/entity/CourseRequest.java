package com.coursemanagement.learningplatform.course.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CourseRequest {
    private String name;
    private String description;
    private Integer credit;
    private Long studyFieldId;
}
