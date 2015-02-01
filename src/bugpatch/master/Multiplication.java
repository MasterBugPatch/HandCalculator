package bugpatch.master;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class Multiplication {

	static HashMap<Integer,String> FinishedRows = new HashMap<Integer,String>();//Placement, Value
	static int AddonNextDigit = 0;
	
	public static boolean numberIsInteger(String s){
		if(!s.contains(".")){
			return true;
		}else{
			String dup = s.replaceAll("0", "");
			if(dup.endsWith(".")){
				return true;
			}else{
				return false;
			}
		}
	}
	
	public static void multiply(String First, String Second){
		String FirstNumber = First;
		String SecondNumber = Second;
		
		int DPL = 0;
		
		//-----------------------------------------------
		// F O R M A T T I N G    T H E       R O W S
		//-----------------------------------------------
		
		if(FirstNumber.length() < SecondNumber.length()){//Check if we need to switch the numbers
			String SaveFirstNumber = FirstNumber;
			FirstNumber = SecondNumber;
			SecondNumber = SaveFirstNumber;
		}
		
		String OrigSaveFirst = FirstNumber;//Used for end display
		String OrigSaveSecond = SecondNumber;
		
		if(numberIsInteger(First) | numberIsInteger(Second) && (!numberIsInteger(First) | !numberIsInteger(Second))){
			if(!numberIsInteger(First)){
				String DTS = First;
				String[] SplitDTS = DTS.split("\\.");
				DPL = DPL + SplitDTS[1].length();
			}else if(!numberIsInteger(Second)){
				String DTS = Second;
				String[] SplitDTS = DTS.split("\\.");
				DPL = DPL + SplitDTS[1].length();
			}
		}else if(!numberIsInteger(First) | !numberIsInteger(Second)){
			String DTS = First;
			String[] SplitDTS = DTS.split("\\.");
			DPL = DPL + SplitDTS[1].length();
			String DTS2 = Second;
			String[] SplitDTS2 = DTS2.split("\\.");
			DPL = DPL + SplitDTS2[1].length();
		}
			
		FirstNumber = FirstNumber.replaceAll("\\.", "");
		SecondNumber = SecondNumber.replaceAll("\\.", "");
		
		//-----------------------------------------------
		// R U N N I N G  T H E  C A L C U L A T I O N S
		//-----------------------------------------------
		
		for(int x = 1;x != SecondNumber.length()+1;x++){//For the length of the second number(amount of rows)
			int ZerosToAdd = x-1;
			for(int i = 1;i != FirstNumber.length()+1;i++){//For each character in the longest number(amount of calculations)
				int PlacementOfFTM = FirstNumber.length() - i;
				String FirstToCalc = String.valueOf(FirstNumber.charAt(PlacementOfFTM));
				int FirstNumberToMultiply = Integer.valueOf(FirstToCalc);
				int SecondNumberToMultiply = Integer.parseInt(String.valueOf(SecondNumber.charAt(SecondNumber.length()-x)));
				int Calculation =  FirstNumberToMultiply*SecondNumberToMultiply;
				String Answer = "" + (Calculation+AddonNextDigit);
				AddonNextDigit = 0;
				String FinalAnswer = null;
				if(i != FirstNumber.length()){
				if(Answer.length() > 1){//We will need to addon in this case
					String ToAddon = null;
					int a = 1;
					for(char c : Answer.toCharArray()){
						if(a == 1){
							ToAddon = "" + c;
							a++;
						}else if(a == 2){
							FinalAnswer = "" + c;
						}
						AddonNextDigit = Integer.parseInt(ToAddon);
					}
				}else{//It's a normal equation
					FinalAnswer = Answer;
				}
				}else{//It's also a normal equation
					FinalAnswer = Answer;
				}
				if(FinishedRows.containsKey(x)){//Check if we need to put a new number or edit the old one
					FinishedRows.put(x, FinalAnswer + FinishedRows.get(x));
				}else{
					FinishedRows.put(x, FinalAnswer);
				}
			}
			
			
			for(int w = 0;w != ZerosToAdd;w++){
				FinishedRows.put(x, FinishedRows.get(x) + "0");
			}
		}
		
		//-----------------------------------------------
		// F O R M A T T I N G   T H E  D I S P L A Y
		//-----------------------------------------------
		ArrayList<String> Corrected = new ArrayList<String>();
		
		//Add the correct 0's and format correctly
		for(int x = 1;x != FinishedRows.size()+1;x++){
			String row = FinishedRows.get(x);
			if(!numberIsInteger(First) | !numberIsInteger(Second)){
			int placement = row.length() - DPL;
			if(DPL > row.length()){
				int ZerosToAdd = DPL-row.length();
				for(int y = 1;y != ZerosToAdd+1;y++){
					row = "0" + row;
				}
			}
			placement = row.length() - DPL;
			row = row.substring(0, placement) + "." + row.substring(placement, row.length());
			}
			Corrected.add(row);
			FinishedRows.put(x, row);
		}
		
		int LongestDigit = 0;
		for(Entry<Integer,String> d : FinishedRows.entrySet()){
			if(d.getValue().length() > LongestDigit){
				LongestDigit = d.getValue().length();
			}
		}
		
		//-----------------------------------------------
		// T H E   F I N A L   D I S P L A Y
		//-----------------------------------------------
		System.out.println("      " + OrigSaveFirst);
		System.out.println("      " + OrigSaveSecond);
		System.out.println(" X    ");
		String Dashes = "";
		for(int x = 0;x != LongestDigit+6;x++){
			Dashes = Dashes + "-";
		}
		
		System.out.println(Dashes);
		Addition.massAddition(FinishedRows);
	}
	
	
	
	public static String silentMultiply(String First, String Second){
		String FirstNumber = First;
		String SecondNumber = Second;
		
		int DPL = 0;
		
		if(FirstNumber.length() < SecondNumber.length()){
			String SaveFirstNumber = FirstNumber;
			FirstNumber = SecondNumber;
			SecondNumber = SaveFirstNumber;
		}
		
		String OrigSaveFirst = FirstNumber;
		String OrigSaveSecond = SecondNumber;
		
		if(numberIsInteger(First) | numberIsInteger(Second) && (!numberIsInteger(First) | !numberIsInteger(Second))){
			if(!numberIsInteger(First)){
				String DTS = First;
				String[] SplitDTS = DTS.split("\\.");
				DPL = DPL + SplitDTS[1].length();
			}else if(!numberIsInteger(Second)){
				String DTS = Second;
				String[] SplitDTS = DTS.split("\\.");
				DPL = DPL + SplitDTS[1].length();
			}
		}else if(!numberIsInteger(First) | !numberIsInteger(Second)){
			String DTS = First;
			String[] SplitDTS = DTS.split("\\.");
			DPL = DPL + SplitDTS[1].length();
			String DTS2 = Second;
			String[] SplitDTS2 = DTS2.split("\\.");
			DPL = DPL + SplitDTS2[1].length();
		}
			
		FirstNumber = FirstNumber.replaceAll("\\.", "");
		SecondNumber = SecondNumber.replaceAll("\\.", "");
		
		for(int x = 1;x != SecondNumber.length()+1;x++){
			int ZerosToAdd = x-1;
			for(int i = 1;i != FirstNumber.length()+1;i++){
				int PlacementOfFTM = FirstNumber.length() - i;
				String FirstToCalc = String.valueOf(FirstNumber.charAt(PlacementOfFTM));
				int FirstNumberToMultiply = Integer.valueOf(FirstToCalc);
				int SecondNumberToMultiply = Integer.parseInt(String.valueOf(SecondNumber.charAt(SecondNumber.length()-x)));
				int Calculation =  FirstNumberToMultiply*SecondNumberToMultiply;
				String Answer = "" + (Calculation+AddonNextDigit);
				AddonNextDigit = 0;
				String FinalAnswer = null;
				if(i != FirstNumber.length()){
				if(Answer.length() > 1){
					String ToAddon = null;
					int a = 1;
					for(char c : Answer.toCharArray()){
						if(a == 1){
							ToAddon = "" + c;
							a++;
						}else if(a == 2){
							FinalAnswer = "" + c;
						}
						AddonNextDigit = Integer.parseInt(ToAddon);
					}
				}else{
					FinalAnswer = Answer;
				}
				}else{
					FinalAnswer = Answer;
				}
				if(FinishedRows.containsKey(x)){
					FinishedRows.put(x, FinalAnswer + FinishedRows.get(x));
				}else{
					FinishedRows.put(x, FinalAnswer);
				}
			}
			
			
			for(int w = 0;w != ZerosToAdd;w++){
				FinishedRows.put(x, FinishedRows.get(x) + "0");
			}
		}
		
		ArrayList<String> Corrected = new ArrayList<String>();
		
		for(int x = 1;x != FinishedRows.size()+1;x++){
			String row = FinishedRows.get(x);
			if(!numberIsInteger(First) | !numberIsInteger(Second)){
			int placement = row.length() - DPL;
			if(DPL > row.length()){
				int ZerosToAdd = DPL-row.length();
				for(int y = 1;y != ZerosToAdd+1;y++){
					row = "0" + row;
				}
			}
			placement = row.length() - DPL;
			row = row.substring(0, placement) + "." + row.substring(placement, row.length());
			}
			Corrected.add(row);
			FinishedRows.put(x, row);
		}
		
		return Addition.silentAddition(FinishedRows);
	}
}
