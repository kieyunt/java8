package com.ky.spring.injection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class InjectionTest {

	public static void main(String[] args) {
	      ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	      TextEditor te = (TextEditor) context.getBean("textEditor");
	      te.spellCheck();
	      System.out.println(te.getAddressList());
	      System.out.println(te.getAddressSet());
	      System.out.println(te.getAddressMap());
	      System.out.println(te.getAddressProp());
	}

}
