package bugpatch.master;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class Addition {

	
	public static void massAddition(HashMap<Integer,String>Rows){
		Boolean isDecimalEquation = false;
		int DecimalPlaceIfDecimal = 0;
		
		//-----------------------------------------------
		// F O R M A T T I N G    T H E       R O W S
		//-----------------------------------------------
		for(Entry<Integer,String> e : Rows.entrySet()){//Check if it is a decimal equation
			if(e.getValue().contains(".")){
				isDecimalEquation = true;
			}
		}
		
		//Format decimals correctly if it is a decimal equation
		HashMap<Integer,String> FinalRows = Rows;
		if(isDecimalEquation){
			Entry<Integer,String> LDPfr = null;
			HashMap<Integer,String> CorrectedRows = new HashMap<Integer,String>();
			for(Entry<Integer,String> d : Rows.entrySet()){
				if(d.getValue().contains(".")){
						String[] split = d.getValue().split("\\.");
						int ThisValue = split[1].length();
						if(LDPfr != null){
							if(ThisValue > LDPfr.getValue().length()){
								LDPfr = d;
							}
						}else{
							LDPfr = d;
						}
				}
			}

			//Align decimal places and add zeros when need be
			String[] split = LDPfr.getValue().split("\\.");
			DecimalPlaceIfDecimal = split[1].length();
			for(Entry<Integer,String> d : Rows.entrySet()){
				String row = d.getValue();
				if(row.contains(".")){
				String[] SplitRow = row.split("\\.");
				String DecimalRemainder = SplitRow[1];
				if(LDPfr.getValue().length() > DecimalRemainder.length()){
					int ZerosToAdd = LDPfr.getValue().length() - DecimalRemainder.length();
					for(int x = 1;x != ZerosToAdd+1;x++){
						row = row + "0";//For each time we need to add a zero modify the string
					}
					CorrectedRows.put(d.getKey(), d.getValue());
				}
				}
			}
			
			for(Entry<Integer,String> d : CorrectedRows.entrySet()){//For each row
				String NewValue = d.getValue().replaceAll("\\.", "");//Remove all decimals for calculations and formatting
				FinalRows.put(d.getKey(), NewValue);
			}
				
	  }
		//Find the longest digit, will be useful throughout calculations
		int LongestDigit = 0;
		for(Entry<Integer,String> d : FinalRows.entrySet()){
			if(d.getValue().length() > LongestDigit){
				LongestDigit = d.getValue().length();
			}
		}
		HashMap<Integer, String> CacheUpdate = new HashMap<Integer, String>();
		for(Entry<Integer,String> d : FinalRows.entrySet()){
			String QuickSave = d.getValue();
			if(LongestDigit > d.getValue().length()){
				int ZerosToAdd = LongestDigit - d.getValue().length();
				for(int x = 1;x != ZerosToAdd+1;x++){
					QuickSave = "0" + QuickSave;
				}
			}
			CacheUpdate.put(d.getKey(), QuickSave);
		}
		
		//Put the results into FinalRows
		for(Entry<Integer,String> c : CacheUpdate.entrySet()){
			FinalRows.put(c.getKey(), c.getValue());
		}
		
		//-----------------------------------------------
		// F O R M A T T I N G    T H E   C O L U M N S
		//-----------------------------------------------
		HashMap<Integer,String> DuplicateFR = FinalRows;//This is just a dynamic FinalRows
		HashMap<Integer, ArrayList<Integer>> Columns = new HashMap<Integer, ArrayList<Integer>>();
		for(int o = 1;o != LongestDigit+1;o++){//The longest digit tells us how many columns we need to have
			for(int w = 1; w != DuplicateFR.size()+1;w++){
				String cur = DuplicateFR.get(w);
				String LastDigit = "" + cur.charAt(cur.length() - 1);
				int TransformedLD = Integer.valueOf(LastDigit);
				if(Columns.containsKey(o)){
					ArrayList<Integer> ForColumns = Columns.get(o);
					ForColumns.add(TransformedLD);
					Columns.put(o, ForColumns);
				}else{
					ArrayList<Integer> ForColumns = new ArrayList<Integer>();
					ForColumns.add(TransformedLD);
					Columns.put(o, ForColumns);
				}
				String correctedcur = cur.substring(0, cur.length() - 1);
				DuplicateFR.put(w, correctedcur);
			}
		}
		
		//------------------------------------------------
		// R U N N I N G   T H E   C A L C U L A T I O N S
		//------------------------------------------------
				String FinalAnswer = "";
				int RollOverInteger = 0;
				for(int x = 1;x != Columns.size()+1;x++){
					int ColumnAnswer = 0;
					for(int i : Columns.get(x)){
						ColumnAnswer = ColumnAnswer + i;
					}
					ColumnAnswer = ColumnAnswer + RollOverInteger;
					RollOverInteger = 0;
					if(x != Columns.size()){
						if(("" + ColumnAnswer).length() > 1){
							//Here we will take the first character and second character and assign them to remainder and column answer
							String SCA = "" + ColumnAnswer;
							ColumnAnswer = Integer.valueOf(SCA.substring(SCA.length() -1));
							SCA = SCA.substring(0, SCA.length() - 1);
							RollOverInteger = Integer.valueOf(SCA);
						}
					}
					FinalAnswer = ColumnAnswer + FinalAnswer;
					
				}
				
				
		//------------------------------------------------
		// D I S P L A Y I N G    T H E    R E S U L T S
		//------------------------------------------------
			
		//Modify the rows one last time before displaying, remove unncessary 0's, etc
		HashMap<Integer,String> DisplayableRows = new HashMap<Integer,String>();
		for(int x = 1;x != CacheUpdate.size()+1;x++){
			String s = CacheUpdate.get(x);
			s = s.replaceFirst("^0+(?!$)", " ");
			int SpacesToAdd = LongestDigit - s.length();
			for(int p = 1;p != SpacesToAdd+1;p++){
				s = " " + s;
			}
			DisplayableRows.put(x, s);
		}
		
		for(int x = 1;x != DisplayableRows.size()+1;x++){
			System.out.println("      " + DisplayableRows.get(x));
		}
		
		//Place the decimal place accurately
		FinalAnswer = FinalAnswer.substring(0, FinalAnswer.length()-DecimalPlaceIfDecimal) + "." + FinalAnswer.substring(FinalAnswer.length()-DecimalPlaceIfDecimal, FinalAnswer.length());
		
		System.out.println("-----------------------");
		
		if(FinalAnswer.endsWith(".")){
			FinalAnswer = FinalAnswer.substring(0, FinalAnswer.length()-1);
		}
		
		System.out.println("      " + FinalAnswer);
	}
	
	public static String silentAddition(HashMap<Integer,String>Rows){
		Boolean isDecimalEquation = false;
		int DecimalPlaceIfDecimal = 0;
		for(Entry<Integer,String> e : Rows.entrySet()){
			if(e.getValue().contains(".")){
				isDecimalEquation = true;
			}
		}
		
		HashMap<Integer,String> FinalRows = Rows;
		if(isDecimalEquation){
			Entry<Integer,String> LDPfr = null;
			HashMap<Integer,String> CorrectedRows = new HashMap<Integer,String>();
			for(Entry<Integer,String> d : Rows.entrySet()){
				if(d.getValue().contains(".")){
						String[] split = d.getValue().split("\\.");
						int ThisValue = split[1].length();
						if(LDPfr != null){
							if(ThisValue > LDPfr.getValue().length()){
								LDPfr = d;
							}
						}else{
							LDPfr = d;
						}
				}
			}

			String[] split = LDPfr.getValue().split("\\.");
			DecimalPlaceIfDecimal = split[1].length();
			for(Entry<Integer,String> d : Rows.entrySet()){
				String row = d.getValue();
				if(row.contains(".")){
				String[] SplitRow = row.split("\\.");
				String DecimalRemainder = SplitRow[1];
				if(LDPfr.getValue().length() > DecimalRemainder.length()){
					int ZerosToAdd = LDPfr.getValue().length() - DecimalRemainder.length();
					for(int x = 1;x != ZerosToAdd+1;x++){
						row = row + "0";
					}
					CorrectedRows.put(d.getKey(), d.getValue());
				}
				}
			}
			
			for(Entry<Integer,String> d : CorrectedRows.entrySet()){
				String NewValue = d.getValue().replaceAll("\\.", "");
				FinalRows.put(d.getKey(), NewValue);
			}
				
	  }
		int LongestDigit = 0;
		for(Entry<Integer,String> d : FinalRows.entrySet()){
			if(d.getValue().length() > LongestDigit){
				LongestDigit = d.getValue().length();
			}
		}
		HashMap<Integer, String> CacheUpdate = new HashMap<Integer, String>();
		for(Entry<Integer,String> d : FinalRows.entrySet()){
			String QuickSave = d.getValue();
			if(LongestDigit > d.getValue().length()){
				int ZerosToAdd = LongestDigit - d.getValue().length();
				for(int x = 1;x != ZerosToAdd+1;x++){
					QuickSave = "0" + QuickSave;
				}
			}
			CacheUpdate.put(d.getKey(), QuickSave);
		}
		
		for(Entry<Integer,String> c : CacheUpdate.entrySet()){
			FinalRows.put(c.getKey(), c.getValue());
		}
		
		HashMap<Integer,String> DuplicateFR = FinalRows;
		HashMap<Integer, ArrayList<Integer>> Columns = new HashMap<Integer, ArrayList<Integer>>();
		for(int o = 1;o != LongestDigit+1;o++){
			for(int w = 1; w != DuplicateFR.size()+1;w++){
				String cur = DuplicateFR.get(w);
				String LastDigit = "" + cur.charAt(cur.length() - 1);
				int TransformedLD = Integer.valueOf(LastDigit);
				if(Columns.containsKey(o)){
					ArrayList<Integer> ForColumns = Columns.get(o);
					ForColumns.add(TransformedLD);
					Columns.put(o, ForColumns);
				}else{
					ArrayList<Integer> ForColumns = new ArrayList<Integer>();
					ForColumns.add(TransformedLD);
					Columns.put(o, ForColumns);
				}
				String correctedcur = cur.substring(0, cur.length() - 1);
				DuplicateFR.put(w, correctedcur);
			}
		}
		
		//Actual Addition
				String FinalAnswer = "";
				int RollOverInteger = 0;
				for(int x = 1;x != Columns.size()+1;x++){//For each column in order
					int ColumnAnswer = 0;
					for(Integer i : Columns.get(x)){
						ColumnAnswer = ColumnAnswer + i;
					}
					ColumnAnswer = ColumnAnswer + RollOverInteger;
					RollOverInteger = 0;
					if(x != Columns.size()){
						if(("" + ColumnAnswer).length() > 1){
							String SCA = "" + ColumnAnswer;
							ColumnAnswer = Integer.valueOf(SCA.substring(SCA.length() -1));
							SCA = SCA.substring(0, SCA.length() - 1);
							RollOverInteger = Integer.valueOf(SCA);
						}
					}
					FinalAnswer = ColumnAnswer + FinalAnswer;
					
				}
				
		HashMap<Integer,String> DisplayableRows = new HashMap<Integer,String>();
		for(int x = 1;x != CacheUpdate.size()+1;x++){
			String s = CacheUpdate.get(x);
			s = s.replaceFirst("^0+(?!$)", " ");
			int SpacesToAdd = LongestDigit - s.length();
			for(int p = 1;p != SpacesToAdd+1;p++){
				s = " " + s;
			}
			DisplayableRows.put(x, s);
		}
		
		FinalAnswer = FinalAnswer.substring(0, FinalAnswer.length()-DecimalPlaceIfDecimal) + "." + FinalAnswer.substring(FinalAnswer.length()-DecimalPlaceIfDecimal, FinalAnswer.length());
		
		if(FinalAnswer.endsWith(".")){
			FinalAnswer = FinalAnswer.substring(0, FinalAnswer.length()-1);
		}
		
		return FinalAnswer;
	}
}