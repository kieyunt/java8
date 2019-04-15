package com.ky.java8.lambda;

import java.math.BigDecimal;

/**
 * String Calculator that calculate math question in string
 * 
 * @author kieyun
 */
public class Java8StringCalculator {
	private final String SPACE = " ";
	MathOperation addition = (a,b) -> a.add(b);
	MathOperation substract = (a,b) -> a.subtract(b);
	MathOperation multiple = (a,b) -> a.multiply(b);
	MathOperation division = (a,b) -> a.divide(b);
	
	/**
	 * Calculate based on formula in String
	 * @param String sum (mandatory)
	 * @return double result
	 * @throws Exception 
	 */
	public double calculate(String sum) throws Exception {
		String newSum = "";
		int additionalNewSumSize = 0;
		int x = 0;
		if((sum.indexOf("+")>-1 || sum.indexOf("-")>-1) &&
		   (sum.indexOf("*")>-1 || sum.indexOf("/")>-1)){
			for(int i=0; i<sum.length(); i++) {
				if(sum.charAt(i)=='*' || sum.charAt(i)=='/') {
					// to make sure that's no brackets for multiply and divide
					// 1 + 2 * 3 * 4 / 2 
					if(i>=4 && sum.charAt(i-4)!='(') {
						// to handle continuous multiple & divide operators 
						x = 4; 
						while(sum.length()>=(i+x) && (sum.charAt(i+x)=='*' || sum.charAt(i+x)=='/')) {
							x = x + 4;
						}
						newSum = newSum.substring(0, i-3+additionalNewSumSize) + " ( " + sum.substring(i-2,i+3+(x-4))+ " ) ";
						i = i+3+(x-4);
						additionalNewSumSize = additionalNewSumSize + 4 + (x-4);
					} else if(i==2) { // if 3rd character is multiply or divide operator
						x = 4; 
						while(sum.length()>=(i+x) && (sum.charAt(i+x)=='*' || sum.charAt(i+x)=='/')) {
							x = x + 4;
						}
						newSum = "( "+ sum.substring(0, i+3+(x-4)) + " ) ";
						i = i+3+(x-4);
						additionalNewSumSize = additionalNewSumSize + 4 + (x-4);
					} else {
						newSum = newSum + sum.charAt(i);
					}
				} else {
					newSum = newSum + sum.charAt(i);
				}
			}
			System.out.println("new sum >> "+newSum);
			sum = newSum;
		}
	
		//handle brackets
		String bracketContent;
		newSum = "";
		for(int i=0; i<sum.length(); i++) {
			if(sum.charAt(i)=='(') {
				bracketContent = sum.substring(i+2, sum.indexOf(")",i)-1);
				i = sum.indexOf(")",i);
				newSum = newSum + calculate(bracketContent);
			} else {
				newSum = newSum + sum.charAt(i);
			}
		}
		
		String[] elements = newSum.split(SPACE);
		BigDecimal result = BigDecimal.ZERO;
		for(int i=0; i<elements.length; i++) {
			if(i==0) {
				result = new BigDecimal(elements[0]);
				continue;
			}
			if(elements[i].equals("+")) {
				result = operate(result, new BigDecimal(elements[i+1]), addition);
			} else if(elements[i].equals("-")) {
				result = operate(result, new BigDecimal(elements[i+1]), substract);
			} else if(elements[i].equals("*")) {
				result = operate(result, new BigDecimal(elements[i+1]), multiple);
			} else if(elements[i].equals("/")) {
				result = operate(result, new BigDecimal(elements[i+1]), division);
			} else {
				throw new Exception("Invalid operator "+elements[i]+ " for formula "+sum);
			}
			i++;
		}
		return result.doubleValue();
	}
	
	interface MathOperation {
		BigDecimal operation(BigDecimal a, BigDecimal b);
	}
	
	private BigDecimal operate(BigDecimal a, BigDecimal b, MathOperation mathOperation) {
		return mathOperation.operation(a, b);
	}
	
	public static void main(String[] args) throws Exception {
		Java8StringCalculator Java8StringCalculator = new Java8StringCalculator();
		System.out.println("1 + 1 = "+ Java8StringCalculator.calculate("1 + 1"));
		System.out.println("2 * 2 = "+ Java8StringCalculator.calculate("2 * 2"));
		System.out.println("1 + 2 + 3 = "+ Java8StringCalculator.calculate("1 + 2 + 3"));
		System.out.println("6 / 2 = "+ Java8StringCalculator.calculate("6 / 2"));
		System.out.println("11 + 23 = "+ Java8StringCalculator.calculate("11 + 23"));
		System.out.println("11.1 + 23 = "+ Java8StringCalculator.calculate("11.1 + 23"));
		System.out.println("( 11.5 + 15.4 ) + 10.1 = "+ Java8StringCalculator.calculate("( 11.5 + 15.4 ) + 10.1"));
		System.out.println("23 - ( 29.3 - 12.5 ) = "+ Java8StringCalculator.calculate("23 - ( 29.3 - 12.5 )"));
		System.out.println("6 + ( 5 * 3 ) = "+ Java8StringCalculator.calculate("6 + ( 5 * 3 )"));
		System.out.println("5 * 3 + 7 = "+ Java8StringCalculator.calculate("5 * 3 + 7"));
		System.out.println("1 * 2 * 3 * 4 + 2 = "+ Java8StringCalculator.calculate("1 * 2 * 3 * 4 + 2"));
		System.out.println("5 * 2 - 3 * 7 + 2 = "+ Java8StringCalculator.calculate("5 * 2 - 3 * 7 + 2"));
	}
	
}
