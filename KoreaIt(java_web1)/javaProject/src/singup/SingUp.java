package singup;

import java.awt.Button;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

public class SingUp {

	static String idStr = null;
	static int pwNum = 0;
	static int rrnNum = 0;

	public static void main(String[] args) {

		// TODO : 회원가입 프레임
		Frame f = new Frame("회원가입");
		f.setBounds(521, 62, 500, 700);
		f.setLayout(null);

		f.setVisible(true);
		// -------------------------------------
		// TODO : 회원가입 타이틀 레이블
		Font labelFontTitle = new Font("", Font.BOLD, 40);
		Label lb = new Label("회원가입");
		lb.setBounds(160, 80, 200, 50);
		lb.setFont(labelFontTitle);

		f.add(lb);

		// TODO : 회원가입 레이블

		Font labelFont = new Font("궁서", Font.BOLD, 20);

		Label lbName = new Label("이름"); // 이름
		lbName.setBounds(60, 180, 100, 30);
		lbName.setFont(labelFont);
		f.add(lbName);

		Label lbId = new Label("아이디"); // 아이디
		lbId.setBounds(60, 256, 100, 30);
		lbId.setFont(labelFont);

		f.add(lbId);

		Label lbPw = new Label("비밀번호"); // 비밀번호
		lbPw.setBounds(60, 335, 100, 30);
		lbPw.setFont(labelFont);

		f.add(lbPw);

		Label lbRrn = new Label("주민등록번호"); // 주민등록번호
		lbRrn.setBounds(60, 430, 130, 30);
		lbRrn.setFont(labelFont);

		f.add(lbRrn);
		// --------------------------------------------------------

		// TODO : 회원가입 입력창
		Font textFont = new Font("", Font.BOLD, 20);

		TextField tfName = new TextField(""); // 이름 입력창
		tfName.setBounds(60, 210, 300, 30);
		tfName.setFont(textFont);

		f.add(tfName);
		TextField tfId = new TextField(""); // 아이디 입력창
		tfId.setBounds(60, 286, 300, 30);
		tfId.setFont(textFont);

		f.add(tfId);

		TextField tfPw = new TextField(""); // 비밀번호 입력창
		tfPw.setBounds(60, 365, 300, 30);
		tfPw.setFont(textFont);

		Label lbPwCon = new Label("( 8자리 이상)"); // 비밀번호 조건
		Font conFont = new Font("", Font.PLAIN, 10);
		lbPwCon.setBounds(60, 390, 300, 30);
		lbPwCon.setFont(conFont);

		f.add(tfPw);
		f.add(lbPwCon);
		
		TextField tfRrn = new TextField(""); // 주민등록번호 입력창
		tfRrn.setBounds(60, 460, 300, 30);
		tfRrn.setFont(textFont);

		Label lbRrnCon = new Label("( '--' 를 포함해서 13자리를 입력해주세요)"); // 비밀번호 조건
		lbRrnCon.setBounds(60, 490, 300, 30);
		lbRrnCon.setFont(conFont);

		f.add(tfRrn);
		f.add(lbRrnCon);

		// TODO 버튼(중복확인, 회원가입 버튼, 가입취소 버튼)

		Font fontCheck = new Font("", Font.BOLD, 15);
		Button btnCheck = new Button("중복확인");
		btnCheck.setBounds(370, 285, 100, 30);
		btnCheck.setFont(fontCheck);

		f.add(btnCheck);

		Font fontYesNo = new Font("", Font.TRUETYPE_FONT, 30);
		Button btnYes = new Button("회원가입");
		btnYes.setBounds(80, 550, 160, 50);
		btnYes.setFont(fontYesNo);
		
		

		f.add(btnYes);

		Button btnNo = new Button("가입취소");
		btnNo.setBounds(260, 550, 160, 50);
		btnNo.setFont(fontYesNo);

		f.add(btnNo);

		// --------------------------------------------------------------

		// TODO 버튼을 눌렀을 때 기능 구현(따로 클래스 사용해서 구현)

		btnCheck.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				CheckButton cb = new CheckButton();
				idStr = cb.check(f, btnCheck, tfId); // 중복이나 조건에 맞는 아이디를 검사

			}
		});


		// TODO 회원가입 버튼 기능 구현
		btnYes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				// TODO 비밀번호 자릿수 검사
				PwCheck pc = new PwCheck();
				pwNum = pc.pwCheck(tfPw);
				// --------------------------------------------------------------
				
				
				// TODO 주민번호 자릿수 검사
				RrnCheck rc = new RrnCheck();
				rrnNum = rc.rrnCheck(tfRrn);
				// -------------------------------------------------------------

				// TODO 회원가입 성공 실패 판단
				YesNo yn = new YesNo();
				yn.yes(f, tfName, idStr, tfPw, pwNum, tfRrn, rrnNum);

			}
		});
		
		//TODO 취소 버튼 기능 구현
		btnNo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				YesNo yn = new YesNo();
				yn.no(f);
				
			}
		});

		// TODO 프레임 창 닫는 기능
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// 회원가입 창에서 x번튼을 눌렀을 때 팝업창을 통해 다시 한번 확인하는 기능
				int yesOrNo = JOptionPane.showConfirmDialog(f, "회원가입을 종료하시겠습니까?", "알림창", JOptionPane.YES_NO_OPTION);

				if (yesOrNo == 0) {
					f.dispose();
				}

			}
		});

		// -------------------------------------------------------------

	}


}
