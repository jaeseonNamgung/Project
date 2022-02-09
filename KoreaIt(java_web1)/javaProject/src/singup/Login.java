package singup;


import java.awt.Font;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import singup.SingUp;

public class Login {
	public static void main(String[] args) {
		
		//TODO :로그인창 프레임
		Frame page = new Frame("로그인");
		page.setBounds(521, 162, 500, 500);
		page.setLayout(null);//�ڵ���ġ ����
	
	
		 //TODO :로그인창 배경사진 
		ImageIcon bg = new ImageIcon("background.png"); 
		JLabel background = new JLabel(bg); 
		background.setBounds( 1, 1, 500, 531);

		
		//TODO :폰트
		Font font = new Font("궁서", Font.PLAIN, 20);
		Font font1 = new Font("궁서", Font.BOLD, 50);
		
		
		//TODO :배경 로고
		JLabel loginText = new JLabel("L O G I N");
		loginText.setFont(font1);
		loginText.setBounds(135, 60, 300, 120);


		//TODO :아이디 비번 사진
		ImageIcon login = new ImageIcon("login.png");
		JLabel loginFrame = new JLabel(login);
		loginFrame.setBounds(25, 160, 450, 170);
		
		
		//TODO :id찾기버튼 사진
		ImageIcon fid = new ImageIcon("fid.png");
		JButton idFrame = new JButton(fid);
		idFrame.setBounds(320, 320, 40, 18);
	
		
		//TODO :pw찾기버튼 사진
		ImageIcon fpw = new ImageIcon("fpw.png");
		JButton pwFrame = new JButton(fpw);
		pwFrame.setBounds(365, 320, 40, 18);
		
		
		//TODO :아이디 치는 창
		TextField tfId = new TextField();
		tfId.setFont(font);  
		tfId.setBounds(225, 204, 165, 23);
	

		//TODO :비밀번호 치는 창
		TextField tfPw = new TextField();
		tfPw.setFont(font);
		tfPw.setEchoChar('*');
		tfPw.setBounds(225, 264, 165, 23);
		
		
		//TODO :로그인 버튼(사진)
		ImageIcon loginbtn = new ImageIcon("loginbtn.png");
		JButton btnlogin = new JButton();
		btnlogin.setIcon(loginbtn);
		btnlogin.setBounds(120, 375, 130, 35);
		btnlogin.setBorderPainted(false);
		btnlogin.setFocusPainted(false);
		//btnlogin.setEnabled(false);
		btnlogin.setContentAreaFilled(false);
		

		//TODO :회원가입 버튼(사진)
		ImageIcon registerbtn = new ImageIcon("regbtn.png");
		JButton regbtn = new JButton();
		regbtn.setIcon(registerbtn);
		regbtn.setBounds(250, 375, 130, 35);
		regbtn.setBorderPainted(false);
		regbtn.setFocusPainted(false);
		regbtn.setContentAreaFilled(false);
		
		
		
		// �� �� ��ư�� ActionListener�� ������ �������̽�
				ActionListener al = new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						if (e.getSource() == btnlogin) {	
							// ����� ID, PW�� ����� ����
							File memberInfo = new File("D:\\JAVA\\javaProject\\member.txt");			
							
							String readData;
							StringTokenizer st;
							String[] array;
							
							boolean check = false;
							
							String id = tfId.getText();
							String pw = tfPw.getText();
							
							
							
							try {
								// ��ü ����
								BufferedReader br = new BufferedReader(new FileReader(memberInfo));
								
								// ������ null�� �ƴ� ������
								while ((readData = br.readLine()) != null) {
									//st = new StringTokenizer(readData, " ");
									array = readData.split(" ");
									
									/*
									 * String txtID = st.nextToken(); String txtPW = st.nextToken();
									 */
									 
									
									// �Է��� ID�� txt ������ ID�� ����, �Է��� PW�� txt ������ PW�� ������
									if (id.equals(array[1]) && pw.equals(array[2])) {
										// ������ false�� ����� �α��� ���� check ���� true�� �ٲٰ�
										check = true;
										break;
									// ID, PW�� ���� �ʴٸ�
									}else {
										check = false;
									}
								}
								// ���� ���� true�̸� �α��� ���� �޼��� ��°� �Բ� â �̵�
								if (check == true) {
									JOptionPane.showMessageDialog(null, "환영합니다");
									//MainPageFrame.main(null);
								// ���� ���� false�̸� �α��� ���� �޼��� ���
								}else {
									JOptionPane.showMessageDialog(null, "아이디나 비밀번호를 확인해주세요");
								}
								
								// BufferedReader ����
								br.close();
							} catch (Exception e2) {
								e2.printStackTrace();
							}
							
						}else if (e.getSource() == regbtn) {
							SingUp.main(null);
						} // if
						
					}
				};		
		
		
		
		
		
		
		
		
		btnlogin.addActionListener(al);
		regbtn.addActionListener(al);
		page.add(tfId);
		page.add(tfPw);
		page.add(idFrame);
		page.add(pwFrame);
		page.add(btnlogin);
		page.add(regbtn);
		page.add(loginFrame);
		page.add(loginText);
		page.add(background);
		
		page.setVisible(true);
        page.addWindowListener(new WindowAdapter() {
        	
        	@Override
        	public void windowClosing(WindowEvent e) {
        		 System.exit(0);
        	}
	
		
			
		});
		
		
		
		
		
		
		
	}//main

}

