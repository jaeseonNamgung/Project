package singup;

import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


import javax.swing.JOptionPane;

import singup.Login;

public class YesNo {

	public void yes(Frame f, TextField tfName, String idStr,TextField tfPw, int pwNum, TextField tfRrn,int rrnNum) {

		if(idStr==null) {  // idStr이 null일 경우 중복확인을 하지 않았다고 판단
			JOptionPane.showMessageDialog(f, "중복확인 해주세요");
		}
		  else if (pwNum == 1) {
			JOptionPane.showMessageDialog(f, "비밀번호를 다시 입력하세요");
		} else if (rrnNum == 1) {
			JOptionPane.showMessageDialog(f, "주민등록번호를 다시 입력하세요");
		}

		else {
			String pathId = "D:\\JAVA\\javaProject\\id.txt";  // Id 경로
			String pathMember = "D:\\JAVA\\javaProject\\member.txt";  // 모든 회원정보 경로
			BufferedWriter bwId,bwMember;
			try {
				bwId = new BufferedWriter(new FileWriter(pathId,true));
				bwMember = new BufferedWriter(new FileWriter(pathMember, true)); 
				
				bwId.write(idStr + "/");  // id.txt 텍스트파일에 회원 아이디를 저장
				bwId.flush();
				
				bwMember.write(tfName.getText() + " " + idStr + " " + tfPw.getText() + " " + tfRrn.getText() + "\r\n");
				
				//(tfName.getText() + "," + idStr + "," + tfPw.getText() +  "," + 
				//		tfRrn.getText()+ "/"); // member.txt 텍스트파일에 회원정보를 저장
				bwMember.flush();
				
				bwId.close();
				bwMember.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			JOptionPane.showMessageDialog(f, "반갑습니다!");
			Login.main(null);
			
			

		}

	}

	public void no(Frame f) {

		int yesOrNo = JOptionPane.showConfirmDialog(f, "회원가입을 종료하시겠습니까?", "알림창", JOptionPane.YES_NO_OPTION);

		if (yesOrNo == 0) {
			new Login();
			f.dispose();
		} 


	}

}
