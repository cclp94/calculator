package calculator;

import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator1 {
	static Stack<Float> vals = new Stack<Float>();
	static Stack<String> ops = new Stack<String>();
	static boolean bResult = true;
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Type your sequence: ");
		String sequence = scan.nextLine();
		calculateSequence(sequence);
	}
	
	
	public static String extract(String sequence){
		String stringPat;
		if(vals.isEmpty() || (!ops.isEmpty()&&ops.peek().equals("(")))
			stringPat = "^((-?(\\d(.\\d)?)+)|([=!]={1})|[!-+*^/()]|([<>]=?))";
		else
			stringPat = "^(((\\d(.\\d)?)+)|([=!]={1})|[!-+*^/()]|([<>]=?))";
		Pattern pat = Pattern.compile(stringPat);
		Matcher match = pat.matcher(sequence);
		if(match.find()){
			System.out.println(match.group());
			return match.group();
		}else
			return null;
	}
	
	public static void calculateSequence(String operation){
		String sequence = operation;
		sequence = sequence.replaceAll(" ", "");
		//System.out.println(sequence);
		String current = null;
		while(!sequence.isEmpty()){
			current = extract(sequence);
			sequence = sequence.substring(current.length());
			//System.out.println(current);
			if(isNumber(current)){
				vals.push(Float.parseFloat(current));
			}
			else{
				if(ops.isEmpty())
					ops.push(current);
				else if(prec(current) >= prec(ops.peek())&&!ops.peek().equals("(")){
					doOps(current);
					ops.push(current);
				}else{
					ops.push(current);
				}
			}
		}
		current = "$";
		while(ops.size() > 0){
			doOps(current);
		}
		System.out.println(operation);
		System.out.println(vals.peek());
	}
	
	public static void doOps(String current){
		Float value = null;
		System.out.println(ops);
		if(ops.peek().equals("!")){
			ops.pop();
			float n = vals.pop();
			value = fact((int) n);
			vals.push(value);
		}
		while(!ops.isEmpty()&&(prec(current) >= prec(ops.peek()))&&!ops.peek().equals("(")){
			if(ops.peek().equals(")")){
				ops.pop();
				ops.pop();
				continue;
			}
			System.out.println("-----Before-----");
			System.out.println(vals);
			System.out.println(ops);
			String operation = ops.pop();
			float n1 = vals.pop(), n2 = vals.pop();
			System.out.println("-----After-----");
			System.out.println(vals+" n1 -> "+n1+", n2 -> "+n2);
			System.out.println(ops+" op -> "+operation);
			while(value == null){			
				if(operation.equals("+")){
					value =(n2 + n1);
				}else if(operation.equals("-")){
					value =(n2 - n1);
				}else if(operation.equals("*")){
					value =(n2 * n1);
				}else if(operation.equals("/")){
					value =(n2 / n1);
				}else if(operation.equals("^")){
					value = pow(n2, (int) n1);
				}else if(operation.equals("==")){
					if(n2 == n1){
						System.out.println("The Expression is true");
						System.exit(1);
					}else{
						bResult = false;
						System.out.println("The Expression is false");
						System.exit(1);
					}
				}else if(operation.equals("!=")){
					if(n2 != n1){
						System.out.println("The Expression is true");
						System.exit(1);
					}else{
						bResult = false;
						System.out.println("The Expression is false");
						System.exit(1);
					}
				}else if(operation.equals(">")){
					if(n2 > n1){
						System.out.println("The Expression is true");
						System.exit(1);
					}else{
						bResult = false;
						System.out.println("The Expression is false");
						System.exit(1);
					}
				}else if(operation.equals("<")){
					if(n2 < n1){
						System.out.println("The Expression is true");
						System.exit(1);
					}else{
						bResult = false;
						System.out.println("The Expression is false");
						System.exit(1);
					}
				}else if(operation.equals(">=")){
					if(n2 >= n1){
						System.out.println("The Expression is true");
						System.exit(1);
					}else{
						bResult = false;
						System.out.println("The Expression is false");
						System.exit(1);
					}
				}else if(operation.equals("<=")){
					if(n2 == n1){
						System.out.println("The Expression is true");
						System.exit(1);
					}else{
						bResult = false;
						System.out.println("The Expression is false");
						System.exit(1);
					}
				}
			}
			System.out.println("----------result--------");
			System.out.println(value);
			vals.push(value);
			value = null;
		}
	}
	
	public static boolean isNumber(String operation){
		try{
			Float.parseFloat(operation.toString());
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}
	
	public static float prec(String z){		
		if(z.equals("+")||z.equals("-"))
			return 6;
		else if(z.equals("*")||z.equals("/"))
			return 5;
		else if(z.equals("^"))
			return 4;
		else if(z.equals("("))
			return 1;
		else if(z.equals(")"))
			return 9;
		else if(z.equals("!"))
			return 2;
		else if(z.equals("==")||z.equals("!="))
			return 8;
		else if(z.equals(">")||z.equals(">=")||z.equals("<=")||z.equals("<"))
			return 7;
		return 10;
	}
	
	public static float pow(float n1, int n2){
		float result =1;
		for(float i = 0; i < n2; i++)
			result = result * n1;
		return result;
	}
	
	public static float fact(int n){
		int result = 1;
		for(int i = n; i > 1; i--){
			result *= i;
		}
		return result;
	}
	

}
