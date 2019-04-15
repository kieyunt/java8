package com.ky.spring.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StudentTest {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans-aop.xml");
		
		Student student = (Student) context.getBean("student");
		student.getName();
		System.out.println();
		student.getAge();
		System.out.println();
		student.printThrowException();
	}
	
}
