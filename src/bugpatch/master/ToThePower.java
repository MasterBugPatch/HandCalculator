package bugpatch.master;

public class ToThePower {

	public static String ToThePower(String base, Integer exponent){
		//LATER MAKE IT SO IT SUPPORTS NEGATIVE NUMBERS
		String CurrentNumber = base;
		if(exponent != 0){
		for(int i = 0;i != exponent-1;i++){
			CurrentNumber = Multiplication.silentMultiply(CurrentNumber, base);
		}
		return CurrentNumber;
		}else{
			return "1";
		}
	}
}
