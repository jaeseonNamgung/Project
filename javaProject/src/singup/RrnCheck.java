package singup;

import java.awt.TextField;

public class RrnCheck {
	public int rrnCheck(TextField tfRrn) {
		int num = 1; // 1일 경우 오류
		if(tfRrn.getText().length() == 14 || tfRrn.getText().equals("-")) {  // 주민번호가 14자리('-' 포함)이거나 '-' 포함될경우 true
			num = 0; // 0일 경우 정상
		}
		return num; 
		
	}
}
