package MainFrame;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import controllerOP.authMysql;


public class Login extends JFrame {
	public Login(){
		this.setBounds(10,10,300,200);
		this.setTitle("Login");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		JPanel login=new JPanel();
		JPanel login1=new JPanel();
		login.setLayout(new BorderLayout());
		Box baseBox=Box.createVerticalBox();
		Box baseBoxH1=Box.createHorizontalBox();
		JLabel user=new JLabel("user:");
		final JTextField username=new JTextField(10);
		baseBoxH1.add(user);
		baseBoxH1.add(Box.createHorizontalStrut(10));
		baseBoxH1.add(username);
		baseBox.add(baseBoxH1);
		Box baseBoxH2=Box.createHorizontalBox();
		JLabel password=new JLabel("password:");
		final JTextField pwd=new JTextField(10);
		baseBoxH2.add(password);
		baseBoxH2.add(Box.createHorizontalStrut(10));
		baseBoxH2.add(pwd);
		baseBox.add(baseBoxH2);
		Box baseBoxH3=Box.createHorizontalBox();
		JLabel mac=new JLabel("mac:");
		final JTextField Mac=new JTextField(10);
		baseBoxH3.add(mac);
		baseBoxH3.add(Box.createHorizontalStrut(10));
		baseBoxH3.add(Mac);
		baseBox.add(baseBoxH3);
		
		Box baseBoxH4=Box.createHorizontalBox();
		JButton lg=new JButton("login");
		lg.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Mac.getText();
				username.getText();
				pwd.getText();
				ResultSet rs=authMysql.searchSQL("select * from user where user='"+username.getText()+"'and password='"+pwd.getText()+"'");
				try {
					if(rs.next()){
						ResultSet rs1=authMysql.searchSQL("select vlan from usertovlan where user='"+username.getText()+"'");
						if(rs1.next()){
							authMysql.operateSQL("insert into portal(user,mac,vlan) values('"+username.getText()+"','"+Mac.getText()+"','"+rs1.getString("vlan")+"')");
						}else{
							JOptionPane.showMessageDialog(null, "user belongs no vlan!", "error", JOptionPane.ERROR_MESSAGE);
						}
						JOptionPane.showMessageDialog(null, "login successful!", "error", JOptionPane.ERROR_MESSAGE);
					}
					else{
						JOptionPane.showMessageDialog(null, "user or password is error!", "error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		JButton quit=new JButton("exit");
		quit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		baseBoxH4.add(lg);
		baseBoxH4.add(Box.createHorizontalStrut(10));
		baseBoxH4.add(quit);
		baseBox.add(baseBoxH4);
		
		login.add(baseBox,BorderLayout.CENTER);
		login1.add(login,BorderLayout.CENTER);
		getContentPane().add(login1);
	}
	public static void main(String argv[]){
		Login lg=new Login();
		lg.setVisible(true);
	}
}
