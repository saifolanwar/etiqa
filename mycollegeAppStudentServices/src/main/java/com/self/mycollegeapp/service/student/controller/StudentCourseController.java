package com.self.mycollegeapp.service.student.controller;

import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.self.mycollegeapp.service.student.model.entity.StudentCourse;

@RestController
public class StudentCourseController extends AbstractController {

	@RequestMapping(value = "/mycollegeapp/services/student/get_course_list", method = RequestMethod.GET)
	public Hashtable getStudentCourseList(HttpServletRequest request, HttpSession session) {
		List<StudentCourse> listStudentCourse = studentCourseRepository.findAll();
		//we can process further and put it into bean if the bean contain more attribute
		return successResponse(listStudentCourse);
	}
	
	@RequestMapping(value = "/mycollegeapp/services/student/add_course_list", method = RequestMethod.POST)
	public Hashtable addToStudentCourseList(HttpServletRequest request, HttpSession session) {
    	Hashtable hashtable = new Hashtable();
		String name = request.getParameter("name");
		String course = request.getParameter("course");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		System.out.println("name course phone email "+name+" "+course+" "+phone+" "+email+" ");
		StudentCourse studentCourse = new StudentCourse();
		studentCourse.setName(name);
		studentCourse.setCourse(course);
		studentCourse.setPhone(phone);
		studentCourse.setEmail(email);
		studentCourseRepository.save(studentCourse);
		return successResponse(hashtable);
	}
	
	@RequestMapping(value = "/mycollegeapp/services/student/update_course_list", method = RequestMethod.POST)
	public Hashtable updateToStudentCourseList(HttpServletRequest request, HttpSession session) {
    	Hashtable hashtable = new Hashtable();
    	String id = request.getParameter("id");
		String name = request.getParameter("name");
		String course = request.getParameter("course");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		System.out.println("id name course phone email "+name+" "+course+" "+phone+" "+email+" "+id);
		StudentCourse studentCourse = new StudentCourse();
		studentCourse.setId(Integer.valueOf(id));
		studentCourse.setName(name);
		studentCourse.setCourse(course);
		studentCourse.setPhone(phone);
		studentCourse.setEmail(email);
		studentCourseRepository.save(studentCourse);
		return successResponse(hashtable);
	}
	
	@RequestMapping(value = "/mycollegeapp/services/student/delete_course_list", method = RequestMethod.POST)
	public Hashtable deleteToStudentCourseList(HttpServletRequest request, HttpSession session) {
    	Hashtable hashtable = new Hashtable();
    	String id = request.getParameter("id");
		String name = request.getParameter("name");
		String course = request.getParameter("course");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		System.out.println("id name course phone email "+name+" "+course+" "+phone+" "+email+" "+id);
		StudentCourse studentCourse = new StudentCourse();
		studentCourse.setId(Integer.valueOf(id));
		studentCourse.setName(name);
		studentCourse.setCourse(course);
		studentCourse.setPhone(phone);
		studentCourse.setEmail(email);
		studentCourseRepository.delete(studentCourse);
		return successResponse(hashtable);
	}

}

