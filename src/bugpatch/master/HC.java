package bugpatch.master;

import java.util.HashMap;
import java.util.Scanner;

public class HC {

	public static void main(String[] args){
		outL("HandCalculator - V Beta 1.0");
		outL("Opensource by MasterBugPatch");
		inputHandler();
	}
	public static void inputHandler(){
		outL("Enter an equation:");
		String input;
		Scanner in = new Scanner(System.in);
		input = in.nextLine();
		if(input.contains("+")){
			System.out.println("Addition Excepted");
			String[] split = input.split("\\+");
			HashMap<Integer,String> SendingToAddition = new HashMap<Integer,String>();
			for(int x = 1;x != split.length+1;x++){
				SendingToAddition.put(x, split[x-1]);
			}
			Addition.massAddition(SendingToAddition);
		}else if(input.contains("-")){
			System.out.println("Subtraction Accepted");
			String[] split = input.split("-");
			Subtraction.subtract(split[0], split[1]);
		}else if(input.contains("*")){
			System.out.println("Multiplication Accepted");
			String[] split = input.split("\\*");
			Multiplication.multiply(split[0],split[1]);
		}else if(input.contains("/")){
			
		}else if(input.contains("^")){
			System.out.println("Powers Accepted");
			String[] split = input.split("\\^");
			System.out.println(ToThePower.ToThePower(split[0], Integer.parseInt(split[1])));
		}else{
			System.out.println("Invalid Acception");
		}
		inputHandler();
	}
	public static void outL(String s){
		System.out.println(s);
	}
}
