package bugpatch.master;

import java.util.HashMap;

public class Subtraction {

	public static void subtract(String First, String Second){
		String DynamicFirst = First; //Copies that will be edited through out calculations
		String DynamicSecond = Second;
		boolean IsDecimalEquation = false;
		int DecimalPlaces = 0;
		boolean IsNegative = false;
		
		//-----------------------------------------------
		// F O R M A T T I N G    T H E       R O W S
		//-----------------------------------------------
		if(DynamicFirst.contains(".") | DynamicSecond.contains(".")){//Simple decimal equation check
			IsDecimalEquation = true;
		}
		if(IsDecimalEquation){//Making the decimal places line up and adding zeros correctly
			if(DynamicFirst.contains(".") && !DynamicSecond.contains(".")){
				String ToSplit = DynamicFirst;
				String Split[] = ToSplit.split("\\.");
				String AfterDec = Split[1];
				DecimalPlaces = AfterDec.length();
				DynamicSecond = DynamicSecond + ".";
				for(int x = 1;x != DecimalPlaces+1;x++){
					DynamicSecond = DynamicSecond + "0";
				}
			}else if(DynamicSecond.contains(".") && !DynamicFirst.contains(".")){
				String ToSplit = DynamicSecond;
				String Split[] = ToSplit.split("\\.");
				String AfterDec = Split[1];
				DecimalPlaces = AfterDec.length();
				DynamicFirst = DynamicFirst + ".";
				for(int x = 1;x != DecimalPlaces+1;x++){
					DynamicFirst = DynamicFirst + "0";
				}
			}else if(DynamicFirst.contains(".") && DynamicSecond.contains(".")){
				String SplitFirst[] = DynamicFirst.split("\\.");
				String SplitSecond[] = DynamicSecond.split("\\.");
				if(SplitFirst[1].length() > SplitSecond[1].length()){
					DecimalPlaces = SplitFirst[1].length();
				}else if(SplitSecond[1].length() > SplitFirst[1].length()){
					DecimalPlaces = SplitSecond[1].length();
				}else{
					DecimalPlaces = SplitFirst[1].length();
				}
			}
		}
		
		DynamicFirst = DynamicFirst.replaceAll("\\.", "");//We already credited for all decimal places, we can remove them now
		DynamicSecond = DynamicSecond.replaceAll("\\.", "");
		
		DynamicFirst = DynamicFirst.replaceFirst("^0+(?!$)", "");//No reason to display any 0's in front, can risk calculations
		DynamicSecond = DynamicSecond.replaceFirst("^0+(?!$)", "");
	
		if(PublicFunctions.NumberIsLarger(DynamicSecond, DynamicFirst)){//Custom greater check to bypass infinite
			IsNegative = true;
			String CacheFirst = DynamicFirst;
			DynamicFirst = DynamicSecond;
			DynamicSecond = CacheFirst;
		}
		
		//------------------------------------------------
		// I M P O R T I N G   S I N G L E   N U M B E R S
		//------------------------------------------------
		HashMap<Integer,Integer> FirstRow = new HashMap<Integer,Integer>();//Will be modified with remainders
		HashMap<Integer,Integer> SecondRow = new HashMap<Integer,Integer>();
		
		//We will now begin creating the rows based off our Dynamics
		String FirstImport = DynamicFirst;//We want something to edit without changing the actual DynamicFirst
		for(int x = 1;x != DynamicFirst.length()+1;x++){
			String StringValue = "" + FirstImport.charAt(FirstImport.length() - 1);
			int Value = Integer.valueOf(StringValue);
			FirstImport = FirstImport.substring(0, FirstImport.length()-1);
			FirstRow.put(x, Value);
		}
		
		String SecondImport = DynamicSecond;
		for(int x = 1;x != DynamicSecond.length()+1;x++){
			String StringValue = "" + SecondImport.charAt(SecondImport.length() - 1);
			int Value = Integer.valueOf(StringValue);
			SecondImport = SecondImport.substring(0, SecondImport.length()-1);
			SecondRow.put(x, Value);
		}
		
		//------------------------------------------------
		// R U N N I N G   T H E   C A L C U L A T I O N S
		//------------------------------------------------
		String AnswerWODecimals = "";
		for(int column = 1; column != FirstRow.size()+1; column++){//For each column
			int FirstValue = FirstRow.get(column);
			int SecondValue = 0;
			if(SecondRow.containsKey(column)){
				SecondValue = SecondRow.get(column);
			}
			if(FirstValue >= SecondValue){
				String AnswerToPlace = "" + (FirstValue-SecondValue);
				if(AnswerToPlace.length() > 1){//Here we check for remainders
					if(column != FirstRow.size()){
					String StringFirst = "" + FirstValue;
					String ToRollOver = "" + StringFirst.charAt(0);
					int RollOverInteger = Integer.parseInt(ToRollOver);
					int Remainder = Integer.parseInt("" + StringFirst.charAt(StringFirst.length()-1));
					FirstRow.put(column+1, FirstRow.get(column+1)+RollOverInteger);//We now have to add 1 to the next columns value
					FirstRow.put(column, Remainder);
					FirstValue = FirstRow.get(column);
					AnswerWODecimals = (FirstValue-SecondValue) + AnswerWODecimals;
					}else{
						AnswerWODecimals = (FirstValue-SecondValue) + AnswerWODecimals;//Remainders don't matter on the last column
					}
				}else{
					AnswerWODecimals = (FirstValue-SecondValue) + AnswerWODecimals;//Normal calculation
				}
			}else{
				//Here we borrow from every other digit in the row from the left
				int StartingPoint = column+1;
				for(;StartingPoint != FirstRow.size()+1;StartingPoint++){
					int CurrentValue = FirstRow.get(StartingPoint);
					String NewValueString = "";
					if(StartingPoint != FirstRow.size()){
					if(CurrentValue != 0){
					NewValueString = "1" + (CurrentValue-1);
					}else{
						NewValueString = "9";
					}
					}else{
					if(CurrentValue != 0){
					NewValueString = "" + (CurrentValue-1);
					}else{
						NewValueString = "0";
					}
					}
					int ValueToSave = Integer.parseInt(NewValueString);
					FirstRow.put(StartingPoint, ValueToSave);
				}
				
				String ThisNewValueString = "1" + FirstValue;
				int ThisNewValue = Integer.parseInt(ThisNewValueString);
				FirstRow.put(column, ThisNewValue);
				FirstValue = FirstRow.get(column);
				AnswerWODecimals = (FirstValue-SecondValue) + AnswerWODecimals;//All borrowing/remainders have been fixed, we can calculate normally
			}
			
		}
		
		//-------------------------------------------------
		// S H O W I N G   W O R K  O R G A N I Z A T I O N
		//-------------------------------------------------
		AnswerWODecimals = AnswerWODecimals.replaceFirst("^0+(?!$)", "");//Fixing up the zeros
		String FinalAnswer = AnswerWODecimals;
		
		int LongestDigit = 0;
		if(DynamicFirst.length() > DynamicSecond.length()){
			LongestDigit = DynamicFirst.length();
		}else if(DynamicSecond.length() > DynamicFirst.length()){
			LongestDigit = DynamicSecond.length();
		}else if(DynamicFirst == DynamicSecond){
			LongestDigit = DynamicFirst.length();
		}
		
		if(IsDecimalEquation){
			FinalAnswer = AnswerWODecimals.substring(0, AnswerWODecimals.length()-DecimalPlaces) + "." + AnswerWODecimals.substring(AnswerWODecimals.length()-DecimalPlaces, AnswerWODecimals.length());
			if(FinalAnswer.startsWith(".")){
				FinalAnswer = "0" + FinalAnswer;
			}
		}
		
		if(IsNegative){
			FinalAnswer = "-" + FinalAnswer;
		}
		
		//We need spaces to organize things accurately to show borrowing work
		String FirstSpaced = "";
		for(int x = 0;x != DynamicFirst.length();x++){
			FirstSpaced = FirstSpaced + "  " + DynamicFirst.charAt(x);
		}
		
		String SecondSpaced = "";
		for(int x = 0;x != DynamicSecond.length();x++){
			SecondSpaced = SecondSpaced + "  " + DynamicSecond.charAt(x);
		}
		
		String FinalSpaced = "";
		for(int x = 0;x != FinalAnswer.length();x++){
			FinalSpaced = FinalSpaced + "  " + FinalAnswer.charAt(x);
		}
		
		//We want to keep the dashes aligned with the equation
		String Dashes = "";
		String DashesToPut = Multiplication.silentMultiply("" + LongestDigit, "" + 3);
		DashesToPut = DashesToPut.replaceAll("\\.", "");
		int DashesAmount = Integer.parseInt(DashesToPut);
		for(int x = 0;x != DashesAmount+6;x++){
			Dashes = Dashes + "-";
		}
		//-----------------------------------------------
		// F I N A L            D I S P L A Y M E N T
		//-----------------------------------------------
		String RemainderRow = "";
		for(int x = 1; x!= FirstRow.size()+1;x++){
			int New = FirstRow.get(x);
			if(New > 9){
			RemainderRow = New + " " + RemainderRow;
			}else{
				String RemainderChar = "" + DynamicFirst.charAt(DynamicFirst.length()-x);
				if(New != Integer.parseInt(RemainderChar)){
				RemainderRow = " " + New + " " + RemainderRow;
				}else{
					RemainderRow = "   " + RemainderRow;//If we didn't change the number to borrow, no point of showing it in remainders
				}
			}
		}
		//Align all the rows/equations/answers by length
		if(RemainderRow.length() != FirstSpaced.length()){
			int ToCorrect = FirstSpaced.length()-RemainderRow.length();
			for(int x = 1;x != ToCorrect+1;x++){
				RemainderRow = " " + RemainderRow;
			}
		}
		if(SecondSpaced.length() != FirstSpaced.length()){
			int ToCorrect = FirstSpaced.length()-SecondSpaced.length();
			for(int x = 1;x != ToCorrect+1;x++){
				SecondSpaced = " " + SecondSpaced;
			}
		}
		
		//Outprint the final results
		System.out.println("Rem:  " + RemainderRow);
		System.out.println(Dashes);
		System.out.println("     " + FirstSpaced);
		System.out.println("-    " + SecondSpaced);
		System.out.println(Dashes); 
		System.out.println("Answ:" + FinalSpaced);
		System.out.println("Unspaced: " + FinalAnswer);
	}
	
	public static String silentSubtract(String First, String Second){
		String DynamicFirst = First;
		String DynamicSecond = Second;
		boolean IsDecimalEquation = false;
		int DecimalPlaces = 0;
		boolean IsNegative = false;
		
		if(DynamicFirst.contains(".") | DynamicSecond.contains(".")){
			IsDecimalEquation = true;
		}
		if(IsDecimalEquation){
			if(DynamicFirst.contains(".") && !DynamicSecond.contains(".")){
				String ToSplit = DynamicFirst;
				String Split[] = ToSplit.split("\\.");
				String AfterDec = Split[1];
				DecimalPlaces = AfterDec.length();
				DynamicSecond = DynamicSecond + ".";
				for(int x = 1;x != DecimalPlaces+1;x++){
					DynamicSecond = DynamicSecond + "0";
				}
			}else if(DynamicSecond.contains(".") && !DynamicFirst.contains(".")){
				String ToSplit = DynamicSecond;
				String Split[] = ToSplit.split("\\.");
				String AfterDec = Split[1];
				DecimalPlaces = AfterDec.length();
				DynamicFirst = DynamicFirst + ".";
				for(int x = 1;x != DecimalPlaces+1;x++){
					DynamicFirst = DynamicFirst + "0";
				}
			}else if(DynamicFirst.contains(".") && DynamicSecond.contains(".")){
				String SplitFirst[] = DynamicFirst.split("\\.");
				String SplitSecond[] = DynamicSecond.split("\\.");
				if(SplitFirst[1].length() > SplitSecond[1].length()){
					DecimalPlaces = SplitFirst[1].length();
				}else if(SplitSecond[1].length() > SplitFirst[1].length()){
					DecimalPlaces = SplitSecond[1].length();
				}else{
					DecimalPlaces = SplitFirst[1].length();
				}
			}
		}
		
		DynamicFirst = DynamicFirst.replaceAll("\\.", "");
		DynamicSecond = DynamicSecond.replaceAll("\\.", "");
		
		DynamicFirst = DynamicFirst.replaceFirst("^0+(?!$)", "");
		DynamicSecond = DynamicSecond.replaceFirst("^0+(?!$)", "");
	
		if(PublicFunctions.NumberIsLarger(DynamicSecond, DynamicFirst)){
			IsNegative = true;
			String CacheFirst = DynamicFirst;
			DynamicFirst = DynamicSecond;
			DynamicSecond = CacheFirst;
		}
		
		HashMap<Integer,Integer> FirstRow = new HashMap<Integer,Integer>();
		HashMap<Integer,Integer> SecondRow = new HashMap<Integer,Integer>();
		
		String FirstImport = DynamicFirst;
		for(int x = 1;x != DynamicFirst.length()+1;x++){
			String StringValue = "" + FirstImport.charAt(FirstImport.length() - 1);
			int Value = Integer.valueOf(StringValue);
			FirstImport = FirstImport.substring(0, FirstImport.length()-1);
			FirstRow.put(x, Value);
		}
		
		String SecondImport = DynamicSecond;
		for(int x = 1;x != DynamicSecond.length()+1;x++){
			String StringValue = "" + SecondImport.charAt(SecondImport.length() - 1);
			int Value = Integer.valueOf(StringValue);
			SecondImport = SecondImport.substring(0, SecondImport.length()-1);
			SecondRow.put(x, Value);
		}
		
		String AnswerWODecimals = "";
		for(int column = 1; column != FirstRow.size()+1; column++){
			int FirstValue = FirstRow.get(column);
			int SecondValue = 0;
			if(SecondRow.containsKey(column)){
				SecondValue = SecondRow.get(column);
			}
			if(FirstValue >= SecondValue){
				String AnswerToPlace = "" + (FirstValue-SecondValue);
				if(AnswerToPlace.length() > 1){
					if(column != FirstRow.size()){
					String StringFirst = "" + FirstValue;
					String ToRollOver = "" + StringFirst.charAt(0);
					int RollOverInteger = Integer.parseInt(ToRollOver);
					int Remainder = Integer.parseInt("" + StringFirst.charAt(StringFirst.length()-1));
					FirstRow.put(column+1, FirstRow.get(column+1)+RollOverInteger);
					FirstRow.put(column, Remainder);
					FirstValue = FirstRow.get(column);
					AnswerWODecimals = (FirstValue-SecondValue) + AnswerWODecimals;
					}else{
						AnswerWODecimals = (FirstValue-SecondValue) + AnswerWODecimals;
					}
				}else{
					AnswerWODecimals = (FirstValue-SecondValue) + AnswerWODecimals;
				}
			}else{
				int StartingPoint = column+1;
				for(;StartingPoint != FirstRow.size()+1;StartingPoint++){
					int CurrentValue = FirstRow.get(StartingPoint);
					String NewValueString = "";
					if(StartingPoint != FirstRow.size()){
					if(CurrentValue != 0){
					NewValueString = "1" + (CurrentValue-1);
					}else{
						NewValueString = "9";
					}
					}else{
					if(CurrentValue != 0){
					NewValueString = "" + (CurrentValue-1);
					}else{
						NewValueString = "0";
					}
					}
					int ValueToSave = Integer.parseInt(NewValueString);
					FirstRow.put(StartingPoint, ValueToSave);
				}
				
				String ThisNewValueString = "1" + FirstValue;
				int ThisNewValue = Integer.parseInt(ThisNewValueString);
				FirstRow.put(column, ThisNewValue);
				FirstValue = FirstRow.get(column);
				AnswerWODecimals = (FirstValue-SecondValue) + AnswerWODecimals;
			}
			
		}
		
		AnswerWODecimals = AnswerWODecimals.replaceFirst("^0+(?!$)", "");
		String FinalAnswer = AnswerWODecimals;
		
		
		if(IsDecimalEquation){
			FinalAnswer = AnswerWODecimals.substring(0, AnswerWODecimals.length()-DecimalPlaces) + "." + AnswerWODecimals.substring(AnswerWODecimals.length()-DecimalPlaces, AnswerWODecimals.length());
			if(FinalAnswer.startsWith(".")){
				FinalAnswer = "0" + FinalAnswer;
			}
		}
		
		if(IsNegative){
			FinalAnswer = "-" + FinalAnswer;
		}
		
		return FinalAnswer;
	}
}
