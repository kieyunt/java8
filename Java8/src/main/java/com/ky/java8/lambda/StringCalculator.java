package com.ky.java8.lambda;

import java.math.BigDecimal;

/**
 * String Calculator that calculate math question in string
 * 
 * @author kieyun
 */
public class StringCalculator {

	private static final String operator = "+-*/";
	
	/**
	 * Calculate based on formula in String
	 * @param String sum (mandatory)
	 * @return double result
	 * @throws Exception 
	 */
	public static double calculate(String sum) throws Exception {
		//handle brackets
		String bracketContent;
		String newSum = "";
		for(int i=0; i<sum.length(); i++) {
			if(sum.charAt(i)=='(') {
				bracketContent = sum.substring(i+2, sum.indexOf(")",i)-1);
				i = sum.indexOf(")");
				newSum = newSum + calculate(bracketContent);
			} else {
				newSum = newSum + sum.charAt(i);
			}
		}
		
		String[] elements = newSum.split(" ");
		BigDecimal result = BigDecimal.ZERO;
		for(int i=0; i<elements.length; i++) {
			if(isNumeric(elements[i])) {
				result = new BigDecimal(elements[i]);
			} else if(operator.indexOf(elements[i])>=0) {
				if(!isNumeric(elements[i+1])) {
					throw new Exception("Invalid number "+elements[i+1]+ " for formula "+sum);
				}
				if(elements[i].equals("+")) {
					result = result.add(new BigDecimal(elements[i+1]));
				} else if(elements[i].equals("-")) {
					result = result.subtract(new BigDecimal(elements[i+1]));
				} else if(elements[i].equals("*")) {
					result = result.multiply(new BigDecimal(elements[i+1]));
				} else if(elements[i].equals("/")) {
					result = result.divide(new BigDecimal(elements[i+1]));
				}
				i++;
			} else {
				throw new Exception("Invalid character "+elements[i]+ " for formula "+sum);
			}
		}
		return result.doubleValue();
	}
	
	/**
	 * Check if the passed in string is number or not
	 * @param String num (mandatory)
	 * @return boolean result
	 */
	private static boolean isNumeric(String num) {
		try {
			Double.parseDouble(num);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("1 + 1 = "+ StringCalculator.calculate("1 + 1"));
		System.out.println("2 * 2 = "+ StringCalculator.calculate("2 * 2"));
		System.out.println("1 + 2 + 3 = "+ StringCalculator.calculate("1 + 2 + 3"));
		System.out.println("6 / 2 = "+ StringCalculator.calculate("6 / 2"));
		System.out.println("11 + 23 = "+ StringCalculator.calculate("11 + 23"));
		System.out.println("11.1 + 23 = "+ StringCalculator.calculate("11.1 + 23"));
		System.out.println("( 11.5 + 15.4 ) + 10.1 = "+ StringCalculator.calculate("( 11.5 + 15.4 ) + 10.1"));
		System.out.println("23 - ( 29.3 - 12.5 ) = "+ StringCalculator.calculate("23 - ( 29.3 - 12.5 )"));
		System.out.println("6 + ( 5 * 3 ) = "+ StringCalculator.calculate("6 + 5 * 3"));
	}
	
}
