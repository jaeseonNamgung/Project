package singup;
import java.awt.TextField;
public class PwCheck {

	public int pwCheck(TextField tfPw) {
	
		int Number = 1; // 1일 경우 오류
		
		if(tfPw.getText().length() >= 8 ) {
			Number = 0;   // 0일 경우 정상
		}
		return Number; 
	}
}
