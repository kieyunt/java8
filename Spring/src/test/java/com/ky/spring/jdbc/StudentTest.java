package com.ky.spring.jdbc;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StudentTest {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans-jdbc.xml");
		StudentJdbcTemplate studentJDBCTemplate = (StudentJdbcTemplate) context.getBean("studentJDBCTemplate");

		studentJDBCTemplate.create(5, "mary", 1200);
		studentJDBCTemplate.create(6, "paul", 1800);

		System.out.println("------Listing Multiple Records--------");
		List<Student> students = studentJDBCTemplate.listStudents();
		students.forEach(a -> {System.out.println(a.getNo()+")"+a.getName()+"-"+a.getSalary());});

		studentJDBCTemplate.update(5, "mary 2", 1350);
		students.forEach(a -> {System.out.println(a.getNo()+")"+a.getName()+"-"+a.getSalary());});
		
		studentJDBCTemplate.delete(6);
		students.forEach(a -> {System.out.println(a.getNo()+")"+a.getName()+"-"+a.getSalary());});
	}

}
