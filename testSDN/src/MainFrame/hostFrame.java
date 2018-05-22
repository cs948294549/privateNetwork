package MainFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

public class hostFrame {
	public static JFrame gethostFrame(Object name,Object ip,Object mac,Object attachpoint,Object time){
		final JFrame jf=new JFrame();
		JPanel jp=new JPanel();
		jp.setLayout(new BorderLayout());
		Box baseBox=Box.createVerticalBox();
		baseBox.add(Box.createVerticalStrut(8));
		
		JLabel titleLabel=new JLabel("host info");
		titleLabel.setFont(new Font("����", Font.PLAIN, 30));
		baseBox.add(titleLabel);
		baseBox.add(Box.createVerticalStrut(8));
		
		JLabel nameLabel=new JLabel("host name:"+name);
		nameLabel.setFont(new Font("����", Font.PLAIN, 20));
		baseBox.add(nameLabel);
		baseBox.add(Box.createVerticalStrut(8));
		
		JLabel ipLabel=new JLabel("IPv6 address"+ip);
		ipLabel.setFont(new Font("����", Font.PLAIN, 20));
		baseBox.add(ipLabel);
		baseBox.add(Box.createVerticalStrut(8));
		
		JLabel macLabel=new JLabel("MAC address"+mac);
		macLabel.setFont(new Font("����", Font.PLAIN, 20));
		baseBox.add(macLabel);
		baseBox.add(Box.createVerticalStrut(8));
		
		JLabel pointLabel=new JLabel("attachpoint:"+attachpoint);
		pointLabel.setFont(new Font("����", Font.PLAIN, 20));
		baseBox.add(pointLabel);
		baseBox.add(Box.createVerticalStrut(8));
		
		JLabel timeLabel=new JLabel("connect time:"+time);
		timeLabel.setFont(new Font("����", Font.PLAIN, 20));
		baseBox.add(timeLabel);
		baseBox.add(Box.createVerticalStrut(8));
		
		jp.add(baseBox,BorderLayout.CENTER);
		jf.add(jp);
		jf.setVisible(true);
		jf.setBounds(10,10,1300,650);
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		return jf;
	}
}
