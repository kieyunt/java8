package com.ky.spring.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {
	String message = "Welcome to Spring MVC";
	
	@RequestMapping("/hello")
	public ModelAndView showMessage(@RequestParam(value="name", required=false, defaultValue="world") String name) {
		System.out.println("in controller");
		
		ModelAndView mv = new ModelAndView("helloWorld");
		mv.addObject("message", message);
		mv.addObject("name", name);
		return mv;
	}
	
	@RequestMapping("hello2")
	public String printHello(ModelMap model) {
		model.addAttribute("message", message + " Framework ! - method printHello()");
		return "hello2";
	}
}
