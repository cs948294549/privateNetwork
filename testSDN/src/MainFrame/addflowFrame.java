package MainFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;

import org.json.JSONException;

import SDNproperty.Flow;
import controllerOP.flowOP;

public class addflowFrame {
	public static JFrame addPane(final String sw){
		JPanel jp=new JPanel();
		final JFrame jf=new JFrame();
		jp.setLayout(new BorderLayout());
		Box baseBox=Box.createVerticalBox();
		baseBox.add(Box.createVerticalStrut(8));
		
		JLabel Switch=new JLabel("SwitchID:"+sw);
		Switch.setFont(new Font("Courier",Font.BOLD,20));
		baseBox.add(Switch);
		baseBox.add(Box.createVerticalStrut(8));
		
		final JPanel slPane=new JPanel();
		Box varBox=Box.createHorizontalBox();

		JLabel switchid=new JLabel("switchid:");
		final JTextField switchidT=new JTextField(20);
		switchidT.setText(sw);
		switchidT.setEditable(false);
		JLabel in_port=new JLabel("in_port:");
		final JTextField in_portT=new JTextField(20);
		JLabel eth_type=new JLabel("eth_type:");
		final JTextField eth_typeT=new JTextField(20);
		JLabel actions=new JLabel("actions:");
		final JTextField actionsT=new JTextField(20);
		JLabel name=new JLabel("flowname:");
		final JTextField nameT=new JTextField(20);
		JLabel eth_src=new JLabel("src_mac:");
		final JTextField eth_srcT=new JTextField(20);
		JLabel eth_dst=new JLabel("dst_mac:");
		final JTextField eth_dstT=new JTextField(20);
		JLabel idletime=new JLabel("idletimeout:");
		final JTextField idletimeT=new JTextField(20);
		JLabel hardtime=new JLabel("hardtimeout:");
		final JTextField hardtimeT=new JTextField(20);
		JLabel ipv4_src=new JLabel("ipv6_src:");
		final JTextField ipv4_srcT=new JTextField(20);
		JLabel ipv4_dst=new JLabel("ipv6_dst:");
		final JTextField ipv4_dstT=new JTextField(20);
		JLabel priority=new JLabel("priority:");
		final JTextField priorityT=new JTextField(20);
		JLabel active=new JLabel("active:");
		final JTextField activeT=new JTextField(20);
	    //final JTextArea flowArea=new JTextArea();
		//flowArea.setLineWrap(true);
		Box varBoxV1=Box.createVerticalBox();
		Box varBoxH11=Box.createHorizontalBox();
		varBoxH11.add(switchid);
		varBoxH11.add(Box.createHorizontalStrut(10));
		varBoxH11.add(switchidT);
		varBoxV1.add(varBoxH11);
		varBoxV1.add(Box.createVerticalStrut(10));
		Box varBoxH12=Box.createHorizontalBox();
		varBoxH12.add(name);
		varBoxH12.add(Box.createHorizontalStrut(10));
		varBoxH12.add(nameT);
		varBoxV1.add(varBoxH12);
		varBoxV1.add(Box.createVerticalStrut(10));
		Box varBoxH13=Box.createHorizontalBox();
		varBoxH13.add(idletime);
		varBoxH13.add(Box.createHorizontalStrut(10));
		varBoxH13.add(idletimeT);
		varBoxV1.add(varBoxH13);
		varBoxV1.add(Box.createVerticalStrut(10));
		Box varBoxH14=Box.createHorizontalBox();
		varBoxH14.add(hardtime);
		varBoxH14.add(Box.createHorizontalStrut(10));
		varBoxH14.add(hardtimeT);
		varBoxV1.add(varBoxH14);
		varBoxV1.add(Box.createVerticalStrut(10));
		Box varBoxH15=Box.createHorizontalBox();
		varBoxH15.add(priority);
		varBoxH15.add(Box.createHorizontalStrut(10));
		varBoxH15.add(priorityT);
		varBoxV1.add(varBoxH15);
		varBoxV1.add(Box.createVerticalStrut(10));
		Box varBoxH16=Box.createHorizontalBox();
		varBoxH16.add(active);
		varBoxH16.add(Box.createHorizontalStrut(10));
		varBoxH16.add(activeT);
		varBoxV1.add(varBoxH16);
		varBoxV1.add(Box.createVerticalStrut(10));
		
		Box varBoxV2=Box.createVerticalBox();
		Box varBoxH21=Box.createHorizontalBox();
		varBoxH21.add(in_port);
		varBoxH21.add(Box.createHorizontalStrut(10));
		varBoxH21.add(in_portT);
		varBoxV2.add(varBoxH21);
		varBoxV2.add(Box.createVerticalStrut(10));
		Box varBoxH22=Box.createHorizontalBox();
		varBoxH22.add(eth_type);
		varBoxH22.add(Box.createHorizontalStrut(10));
		varBoxH22.add(eth_typeT);
		varBoxV2.add(varBoxH22);
		varBoxV2.add(Box.createVerticalStrut(10));
		Box varBoxH23=Box.createHorizontalBox();
		varBoxH23.add(eth_src);
		varBoxH23.add(Box.createHorizontalStrut(10));
		varBoxH23.add(eth_srcT);
		varBoxV2.add(varBoxH23);
		varBoxV2.add(Box.createVerticalStrut(10));
		Box varBoxH24=Box.createHorizontalBox();
		varBoxH24.add(eth_dst);
		varBoxH24.add(Box.createHorizontalStrut(10));
		varBoxH24.add(eth_dstT);
		varBoxV2.add(varBoxH24);
		varBoxV2.add(Box.createVerticalStrut(10));
		Box varBoxH25=Box.createHorizontalBox();
		varBoxH25.add(ipv4_src);
		varBoxH25.add(Box.createHorizontalStrut(10));
		varBoxH25.add(ipv4_srcT);
		varBoxV2.add(varBoxH25);
		varBoxV2.add(Box.createVerticalStrut(10));
		Box varBoxH26=Box.createHorizontalBox();
		varBoxH26.add(ipv4_dst);
		varBoxH26.add(Box.createHorizontalStrut(10));
		varBoxH26.add(ipv4_dstT);
		varBoxV2.add(varBoxH26);
		varBoxV2.add(Box.createVerticalStrut(10));
		
		Box varBoxV3=Box.createVerticalBox();
		Box varBoxH3=Box.createHorizontalBox();
		varBoxH3.add(actions);
		varBoxH3.add(Box.createHorizontalStrut(10));
		varBoxH3.add(actionsT);
		varBoxV3.add(varBoxH3);
		varBoxV3.add(Box.createVerticalStrut(10));
		
		varBox.add(varBoxV1);
		varBox.add(Box.createHorizontalStrut(50));
		varBox.add(varBoxV2);
		varBox.add(Box.createHorizontalStrut(50));
		varBox.add(varBoxV3);
		varBox.add(Box.createHorizontalStrut(50));
		
		slPane.add(varBox);
		baseBox.add(slPane);
		baseBox.add(Box.createVerticalStrut(8));
		
		JButton okButton=new JButton("push flow");
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					Flow flow=new Flow();
					flow.switchid=switchidT.getText();
					flow.name=nameT.getText();
					flow.idletime=idletimeT.getText();
					flow.hardtime=hardtimeT.getText();
					flow.priority=priorityT.getText();
					flow.active=activeT.getText();
					flow.in_port=in_portT.getText();
					flow.eth_type=eth_typeT.getText();
					flow.eth_src=eth_srcT.getText();
					flow.eth_dst=eth_dstT.getText();
					flow.actions=actionsT.getText();
					flowOP.push(flow.serialize());
				} catch (IOException | JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("push success");
				jf.dispose();
				try {
					JFrame flowpane=flowFrame.getflowPane(sw.toString());
				} catch (JSONException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		baseBox.add(okButton);
		baseBox.add(Box.createVerticalStrut(8));
		jp.add(baseBox,BorderLayout.CENTER);
		jf.add(jp);
		jf.setVisible(true);
		jf.setBounds(10,10,1300,650);
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		return jf;
	}
}
