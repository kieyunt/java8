package com.ky.spring;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class HelloWorld implements InitializingBean, DisposableBean  {
	private String message;
	private String message1;
	private String message2;
	private String message3;

	public void getMessage() {
		System.out.println("Your message : "+message);
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void initMethod() {
		System.out.println("init method");
	}
	
	public void destroyMethod() {
		System.out.println("destroy method");
	}

	public void afterPropertiesSet() throws Exception {
		System.out.println("afterPropertiesSet()");
	}

	public void destroy() throws Exception {
		System.out.println("method destroy");
	}

	public void getMessage1() {
		System.out.println("Your message 1: "+message1);
	}

	public void setMessage1(String message1) {
		this.message1 = message1;
	}

	public void getMessage2() {
		System.out.println("Your message 2: "+message2);
	}

	public void setMessage2(String message2) {
		this.message2 = message2;
	}

	public void getMessage3() {
		System.out.println("Your message 3: "+message3);
	}

	public void setMessage3(String message3) {
		this.message3 = message3;
	}
}
