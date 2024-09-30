package com.coursemanagement.learningplatform.course.repository;

import com.coursemanagement.learningplatform.course.entity.Course;
import com.coursemanagement.learningplatform.studyfield.entity.StudyField;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseRepositoryImpl implements CourseRepository {

    private final JdbcTemplate jdbcTemplate;

    public CourseRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Course> findAllByStudyFieldId(long sfId) {
        String sql = "SELECT * FROM course WHERE sf_id = ?";
        return jdbcTemplate.query(sql, new Object[]{sfId}, courseRowMapper());
    }

    @Override
    public List<Course> findAll() {
        String sql = "SELECT * FROM course";
        return jdbcTemplate.query(sql, courseRowMapper());
    }

    @Override
    public void addCourse(Course course) {
        String sql = "INSERT INTO course (name, description, credit, sf_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, course.getName(), course.getDescription(),
                course.getCredit(), course.getStudyField() != null ? course.getStudyField().getId() : null);
    }
    @Override
    public void updateCourse(Course course) {
        String sql = "UPDATE course SET name = ?, description = ?, credit = ?, sf_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, course.getName(), course.getDescription(), course.getCredit(),
                course.getStudyField() != null ? course.getStudyField().getId() : null, course.getId());
    }

    @Override
    public Course viewCourse(long courseId) {
        String sql = "SELECT * FROM course c JOIN study_field sf ON c.sf_id = sf.id WHERE c.id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{courseId}, courseRowMapper()); // Correct case
    }

    @Override
    public void deleteCourse(long id) {
        String sql = "DELETE FROM course WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private RowMapper<Course> courseRowMapper() {
        return (rs, rowNum) -> {
            Course course = new Course();
            course.setId(rs.getLong("id"));
            course.setName(rs.getString("name"));
            course.setDescription(rs.getString("description"));
            course.setCredit(rs.getInt("credit"));

            // Fetch the StudyField entity
            long sfId = rs.getLong("sf_id");
            course.setStudyField(fetchStudyFieldById(sfId));

            return course;
        };
    }

    private StudyField fetchStudyFieldById(long sfId) {
        String sql = "SELECT * FROM study_field WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{sfId}, (rs, rowNum) -> {
            StudyField studyField = new StudyField();
            studyField.setId(rs.getLong("id"));
            studyField.setName(rs.getString("name"));  // Assuming StudyField has a name field
            return studyField;
        });
    }
}
