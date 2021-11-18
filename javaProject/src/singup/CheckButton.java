package singup;

import java.awt.Button;
import java.awt.Frame;
import java.awt.TextField;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class CheckButton {
	static String str = null;
	
	public String check(Frame f, Button buttonCheck, TextField textId) {

		String path = "D:\\JAVA\\javaProject\\id.txt";
		File file = new File(path);
		BufferedReader br;
		StringTokenizer st;
		
		boolean isFlag = true; // 중복 값이 없거나 조건에 맞는 아이디일 경우만 true로 변경해서 값을 넣을수 있게 하는 일종에 열쇠 기능
		String reg = "^[0-9a-zA-b]*$";
		String regNumber = "^[0-9]";
		Matcher matcher, matcherNumber;
		String strTemp = null; // 중복이 없거나 조건에 맞는 아이디를 반환

		matcher = Pattern.compile(reg).matcher(textId.getText()); // 특수문자 문자열 정규식
		matcherNumber = Pattern.compile(regNumber).matcher(textId.getText()); // 앞에 숫자 정규식

		try {

			br = new BufferedReader(new FileReader(path));
			// TODO 빈 문자열 확인
			if (textId.getText().isEmpty()) { // 빈 문자열을 입력할 경우 경고문을 띄운다.
				JOptionPane.showMessageDialog(f, "아이디를 입력하세요");
				isFlag = false;

			} // if
				// ------------------------------------------------------------------
				// TODO : 공백 & 특수문자 & 첫 문자 숫자 확인
			else if ((matcher.find() == false) || matcherNumber.find() == true) {
				JOptionPane.showMessageDialog(f, "공백 또는 특수문자가 포함되었거나, 숫자로 시작하는 아이디는 사용할 수 없습니다");
				isFlag = false;
			} // else if
				// ------------------------------------------------------------------
			else {

				st = new StringTokenizer(br.readLine(), "/");  // 회원 아이디를 '/'로 구분

				// TODO 첫 가입일 경우 판단
				if (st.nextToken() == "1") {
					strTemp = textId.getText();
				}
				// ------------------------------------------------------------------
				// TODO 중복검사
				while (st.hasMoreTokens()) {
					str = st.nextToken();

					if (str.equals(textId.getText())) {
						JOptionPane.showMessageDialog(f, "중복된 아이디입니다");
						textId.setText("");
						isFlag = false;
						break;
					} // if
				} // while
				// ------------------------------------------------------------------

				// TODO 중복이 없는 경우
				if (isFlag) {
					
					JOptionPane.showMessageDialog(f, "사용 가능한 아이디입니다");
					strTemp = textId.getText();

				} // if
				// ------------------------------------------------------------------
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return strTemp;
	}

}
