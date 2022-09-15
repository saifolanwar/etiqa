package com.self.mycollegeapp.service.student.model;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.self.mycollegeapp.service.student.model.entity.StudentCourse;

public interface StudentCourseRepository extends CrudRepository<StudentCourse, Integer> {

	public List<StudentCourse> findAll();
}
