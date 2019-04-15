package com.ky.spring.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ky.spring.exception.SpringException;
import com.ky.spring.web.beans.Student;

@Controller
public class StudentController {

	@RequestMapping(value="/student", method=RequestMethod.GET)
	public ModelAndView student() {
		return new ModelAndView("student","command",new Student());
	}
	
	@RequestMapping(value="/addStudent", method=RequestMethod.POST)
	@ExceptionHandler({SpringException.class})
	public String addStudent(@ModelAttribute("SpringWeb")Student student, ModelMap model) {
		if(student.getName().length()<5) {
			throw new SpringException("Given name is too short");
		} else if(student.getAge()<10) {
			throw new SpringException("Given age is too low");
		} else if(student.getId()<0) {
			throw new RuntimeException("unknown id");
		}
		model.addAttribute("name",student.getName());
		model.addAttribute("age",student.getAge());
		model.addAttribute("id",student.getId());
		return "result";
		//return "redirect:/result";
	}
	
	@GetMapping(value="/result")
	public String resultPage() {
		return "result";
	}
	
	@RequestMapping(value="/staticPage") 
	public String gotoStaticPage() {
		return "redirect:/pages/final.htm";
	}
}
