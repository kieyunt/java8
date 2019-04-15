package com.ky.java8.predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class PredicateTester {

	public static void main(String[] args) {
		PredicateTester tester = new PredicateTester();
		tester.runPredicateSample();
	}
	
	public void runPredicateSample() {
		Predicate<String> p = s -> s.length() > 5;
		System.out.println(p.test("hello world"));
		System.out.println(p.test("hello"));
		
		List<Box> inventory = Arrays.asList(new Box(30, "green"), new Box(155, "green"), new Box(75, "red"));
		System.out.println(filter(inventory, PredicateTester::isGreenApple));
		System.out.println(filter(inventory, PredicateTester::isHeavyApple));
		
		List<Student> students = Arrays.asList(new Student(1, 3, "John"), new Student(2, 15, "Jane"), new Student(3, 9, "Jack"));
		System.out.println(findStudents(students, createCustomPredicate(10_0D)));
		
		Function<Double, Predicate<Student>> customeFunction = thresold -> (e -> e.gpa > thresold);
		System.out.println(findStudents(students, customeFunction.apply(8D)));
		
		//BiPredicate
		BiPredicate<Integer, Integer> bp = (x,y) -> x * y > 10;
		System.out.println("BiPredicate Test 3, 2 : "+bp.test(3, 2));
		System.out.println("BiPredicate Test 3, 5 : "+bp.test(3, 5));
		System.out.println(bp.and((x,y)->x>2).test(6, 2));
	}
	
	public static boolean isGreenApple(Box box) {
		return box.getColor().equals("green");
	}
	
	public static boolean isHeavyApple(Box box) {
		return box.getWeight()>150;
	}
	
	public List<Box> filter(List<Box> inventory, Predicate<Box> p1) {
		List<Box> result = new ArrayList<Box>();
		for(Box box : inventory) {
			if(p1.test(box)) {
				result.add(box);
			}
		}
		return result;
	}

	private static Predicate<Student> createCustomPredicate(double thresold) {
		return e -> e.gpa > thresold;
	}
	
	private static List<Student> findStudents(List<Student> employees, Predicate<Student> condition) {
		List<Student> resultList = new ArrayList<Student>();
		for(Student student : employees) {
			if(condition.test(student)) {
				resultList.add(student);
			}
		}
		return resultList;
	}
}

class Box {
	private int weight; 
	private String color;
	
	public Box(int weight, String color) {
		this.setWeight(weight);
		this.setColor(color);
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	@Override
	public String toString() {
		return "Apple {color='"+this.color+"', weight="+this.weight+"}";
	}
}

class Student {
	public int id; 
	public long gpa;
	public String name;
	
	Student(int id, long g, String name) {
		this.id = id; 
		this.gpa = g;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return id + ">"+name+": "+gpa;
	}
}