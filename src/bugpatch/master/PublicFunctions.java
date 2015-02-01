package bugpatch.master;

public class PublicFunctions {

	public static Boolean NumberIsLarger(String First, String Second){
		boolean ToReturn = false;
		if(First.length() > Second.length()){
			ToReturn = true;
		}else if(First.length() == Second.length()){
			if(!First.equalsIgnoreCase(Second)){
			int x = 0;
			while(x != -1){
				String CurrentFirst = "" + First.charAt(x);
				String CurrentSecond = "" + Second.charAt(x);
				int CF = Integer.parseInt(CurrentFirst);
				int CS = Integer.parseInt(CurrentSecond);
				if(CF > CS){
					ToReturn = true;
					x = -1;
				}else if(CS > CF){
					ToReturn = false;
					x = -1;
				}
				if(x != -1){
				x++;
				}
			}
		}
		}else{
			ToReturn = false;
		}
		return ToReturn;
	}
	
}
