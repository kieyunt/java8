package com.ky.java8.function;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class FunctionTester {

	public static void main(String[] args) {
		FunctionTester test = new FunctionTester();
		//test.consumerTest();
		test.functionTest();
	} 
	
	private void consumerTest() {
		//Consumer
		Consumer<String> consumer = a -> System.out.println("Consumer : "+a.toLowerCase());
		consumer.accept("Hello World");

		//Consumer as parameter
		List<Student> students = Arrays.asList(new Student("John",3), new Student("May", 5));
		updateStudentInfo(students, System.out::println);
		updateStudentInfo(students, e->{e.egpa *= 1.5;});
		updateStudentInfo(students, e->System.out.println(e.name+" >> "+e.egpa));
		
		//BiConsumer
		BiConsumer<String, String> biConsumer = (a, b) -> System.out.println("Biconsumer : "+a+"\n"+b);
		biConsumer.accept("Hello", "World");
		biConsumer.andThen(biConsumer).accept("cute", "puppy");
	}
	
	private void updateStudentInfo(List<Student> students, Consumer<Student> consumer) {
		for(Student st: students) {
			consumer.accept(st);
		}
	}
	
	private void functionTest() {
		Function<Integer, String> converter = i->Integer.toString(i);
		System.out.println(converter.apply(3).length());
		System.out.println(converter.apply(30).length());
		System.out.println(calc(i->"Multiply Result = "+(i*2),10));
		System.out.println(calc(i->"Division Result = "+(i/2),10));
		
		BiFunction<String, String, String> bi = (x,y) -> x + y;
		System.out.println("BiFunction : "+bi.apply("hello", " geek"));
		
		List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		Function<Integer, Integer> f1 = a->a*a;
		System.out.println(numbers.stream().map(f1).collect(java.util.stream.Collectors.toList()));
		Function<Double, Double> f2 = Math::sqrt;
		System.out.println(f2.apply(4225D));
	}
	
	private String calc(Function<Integer, String> bi, Integer i) {
		return bi.apply(i);
	}
	
}

class Student {
	String name;
	double egpa; 
	
	Student(String name, double egpa) {
		this.name = name;
		this.egpa = egpa;
	}
}
