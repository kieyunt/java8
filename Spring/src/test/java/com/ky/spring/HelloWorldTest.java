package com.ky.spring;

import org.apache.log4j.Logger;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloWorldTest  {
	static Logger log = Logger.getLogger(HelloWorldTest.class.getName());
	
	public static void main(String[] args) {
		  AbstractApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		  log.info("Going to create HellowWorld object");
	      HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
	      obj.getMessage();
	      obj.getMessage1();
	      obj.getMessage2();
	      obj.getMessage3();
//	      obj.setMessage("XYZ");
//	      obj.getMessage();
//	      
//	      HelloWorld obj2 = (HelloWorld) context.getBean("helloWorld");
//	      obj2.getMessage();
	      context.registerShutdownHook();
	      log.info("Exiting the program");
	}
	
}
