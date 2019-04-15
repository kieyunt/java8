package com.ky.java8.methodreference;

import java.util.ArrayList;
import java.util.List;

public class MethodReferenceTester {

	public static void main(String[] args) {
		List<String> names1 = new ArrayList<String>();
		names1.add("Mahesh ");
		names1.add("Suresh ");
		names1.add("Ramesh ");
		names1.add("Naresh ");
		names1.add("Kalpesh ");
		
		System.out.println("Method Reference:");
		names1.forEach(System.out::println);
		System.out.println("\nLamda way:");
		names1.forEach(a -> System.out.println(a+"*"));
	}

}
