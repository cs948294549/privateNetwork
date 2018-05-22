package controllerOP;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONException;

import SDNproperty.Flow;

public class searchPath {
	public static ArrayList<Object[]> path = new ArrayList<Object[]>(); 
//	public searchPath(){
//		Object[] o=new Object[3];
//		o[0]="00:00:00:00:00:00:00:01";
//		o[1]="00:00:00:00:00:00:00:02";
//		o[2]="00:00:00:00:00:00:00:04";
//		path.add(o);
//		o=new Object[3];
//		o[0]="00:00:00:00:00:00:00:01";
//		o[1]="00:00:00:00:00:00:00:03";
//		o[2]="00:00:00:00:00:00:00:04";
//		path.add(o);
//	}
	public static String getpathPort(String src_sw,String dst_sw) throws JSONException, IOException{
		JSONArray o=controllerAPI.getLinks();
		for(int i=0;i<o.length();i++){
			if(o.getJSONObject(i).getString("src-switch").equals(src_sw)&&o.getJSONObject(i).getString("dst-switch").equals(dst_sw)){
				return o.getJSONObject(i).getString("src-port");
			}
			else if(o.getJSONObject(i).getString("src-switch").equals(dst_sw)&&o.getJSONObject(i).getString("dst-switch").equals(src_sw)){
				return o.getJSONObject(i).getString("dst-port");
			}
		}
		return "none";
	}
	public static void writeFlow(String src_host,String dst_host,Object[] path) throws JSONException, IOException{
		JSONArray o=controllerAPI.getDevice();
		System.out.println("write flow:"+o);
		String src_port="";
		String dst_port="";
		String src_mac="";
		String dst_mac="";
		for(int i=0;i<o.length();i++){
			if(o.getJSONObject(i).getJSONArray("ipv6").length()!=0) {
			if(o.getJSONObject(i).getJSONArray("ipv6").getString(0).equals(src_host)){
				src_port=o.getJSONObject(i).getJSONArray("attachmentPoint").getJSONObject(0).getString("port");
				src_mac=o.getJSONObject(i).getJSONArray("mac").getString(0);
			}
			if(o.getJSONObject(i).getJSONArray("ipv6").getString(0).equals(dst_host)){
				dst_port=o.getJSONObject(i).getJSONArray("attachmentPoint").getJSONObject(0).getString("port");
				dst_mac=o.getJSONObject(i).getJSONArray("mac").getString(0);
			}
			}
		}
		for(int i=0;i<path.length;i++){
			Flow flow=new Flow();
			flow.eth_type="0x86dd";
			flow.active="true";
			flow.eth_src=src_mac;
			flow.eth_dst=dst_mac;
			flow.name="flow-"+path[i].toString()+src_host+dst_host;
			flow.priority="32767";
			flow.switchid=path[i].toString();
			if(i<path.length-1){
				flow.actions="output="+getpathPort(path[i].toString(),path[i+1].toString());
			}else{
				flow.actions="output="+dst_port;
			}
			try {
				flowOP.push(flow.serialize());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("one path write error!");
			}
		}
		for(int i=path.length-1;i>=0;i--){
			Flow flow=new Flow();
			flow.eth_type="0x86dd";
			flow.active="true";
			flow.eth_src=dst_mac;
			flow.eth_dst=src_mac;
			flow.name="flow-"+path[i].toString()+dst_host+src_host;
			flow.priority="32767";
			flow.switchid=path[i].toString();
			if(i>0){
				flow.actions="output="+getpathPort(path[i].toString(),path[i-1].toString());
			}else{
				flow.actions="output="+src_port;
			}
			try {
				flowOP.push(flow.serialize());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static ArrayList<String> showPath(String src_host,String dst_host){
		JSONArray dev;
		JSONArray link;
		ArrayList<String> path = new ArrayList<String>();
		try {
			dev = controllerAPI.getDevice();
			link=controllerAPI.getLinks();
			String src_sw="";
			String dst_sw="";
			String src_mac="";
			String dst_mac="";
			for(int i=0;i<dev.length();i++){
				if(dev.getJSONObject(i).getJSONArray("ipv6").length()!=0) {
				if(dev.getJSONObject(i).getJSONArray("ipv6").getString(0).equals(src_host)){
					src_sw=dev.getJSONObject(i).getJSONArray("attachmentPoint").getJSONObject(0).getString("switchDPID");
					src_mac=dev.getJSONObject(i).getJSONArray("mac").getString(0);
				}
				if(dev.getJSONObject(i).getJSONArray("ipv6").getString(0).equals(dst_host)){
					dst_sw=dev.getJSONObject(i).getJSONArray("attachmentPoint").getJSONObject(0).getString("switchDPID");
					dst_mac=dev.getJSONObject(i).getJSONArray("mac").getString(0);
				}
				}
			}
			String start_sw=src_sw;
			path.add(start_sw);
			boolean check=true;
			while(!start_sw.equals(dst_sw)&&check){
				int ck=path.size();
				JSONArray flow=controllerAPI.getFlows(start_sw);
				System.out.println(flow);
				for(int i=0;i<flow.length();i++){
			if(flow.getJSONObject(i).getJSONObject("match").length()!=0) {
				if(flow.getJSONObject(i).getJSONObject("match").getString("eth_src").equals(src_mac)&&flow.getJSONObject(i).getJSONObject("match").getString("eth_dst").equals(dst_mac)){
					char[] p=new char[3];
					flow.getJSONObject(i).getJSONObject("instructions").getJSONObject("instruction_apply_actions").getString("actions").getChars(7, flow.getJSONObject(i).getJSONObject("instructions").getJSONObject("instruction_apply_actions").getString("actions").length(),p, 0);
					String port=new String(p);
					for(int j=0;j<link.length();j++){
						if(link.getJSONObject(j).getString("src-switch").equals(start_sw)&&link.getJSONObject(j).getString("src-port").equals(port.trim())){
							start_sw=link.getJSONObject(j).getString("dst-switch");
							path.add(start_sw);
						}
					}
				}
				}
				}
				if(ck==path.size()){
					check=false;
					path.clear();
				}
			}
			//path.add(dst_sw);
			return path;
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			return path;
		}
	}
//	public static void main(String argv[]) throws JSONException, IOException{
//		searchPath pa=new searchPath();
//		JFrame mainframe=new JFrame();
//		JPanel jp=new JPanel();
//		JPanel jp1=new JPanel();
//		JPanel jp2=new JPanel();
//		jp2.setLayout(new BorderLayout());
//		jp1.setLayout(new BorderLayout());
//		jp.setLayout(new BorderLayout());
//		Box basebox=Box.createVerticalBox();
//		Box hbox=Box.createHorizontalBox();
//		JLabel src=new JLabel("src-ip");
//		hbox.add(src);
//		hbox.add(Box.createHorizontalStrut(10));
//		final JTextField srcT=new JTextField(20);
//		hbox.add(srcT);
//		Box hbox1=Box.createHorizontalBox();
//		JLabel dst=new JLabel("dst-ip");
//		hbox1.add(dst);
//		hbox1.add(Box.createHorizontalStrut(10));
//		final JTextField dstT=new JTextField(20);
//		hbox1.add(dstT);
//		basebox.add(hbox);
//		basebox.add(hbox1);
//		jp1.add(basebox,BorderLayout.CENTER);
//		final JTextArea pathArea=new JTextArea();
//		JScrollPane pathScroll=new JScrollPane();
//		pathArea.setLineWrap(false);
//		pathArea.setEditable(false);
//		pathScroll.setViewportView(pathArea);
//		Box basebox1=Box.createHorizontalBox();
//		JButton write=new JButton("write");
//		write.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				Object[] o1=path.get(0);//124
//				Object[] o2=path.get(1);//134
//				try {
//					writeFlow("10.0.0.1","10.0.0.3",o1);
//					writeFlow("10.0.0.2","10.0.0.4",o1);
//					writeFlow("10.0.0.1","10.0.0.4",o2);
//					writeFlow("10.0.0.2","10.0.0.3",o2);
//				} catch (JSONException | IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			}
//		});
//		JButton show=new JButton("showpath");
//		show.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				if(!dstT.getText().equals("")&&!dstT.getText().equals("")&&!showPath(srcT.getText(),dstT.getText()).isEmpty()){
//						ArrayList<String> path=showPath(srcT.getText(),dstT.getText());
//						pathArea.setText("");
//						pathArea.append(srcT.getText()+"   to   "+dstT.getText()+":");
//						pathArea.append("\n");
//						pathArea.append(srcT.getText()+"->");
//						for(int i=0;i<path.size();i++){
//							if(i<path.size()-1){
//								pathArea.append(path.get(i)+"->");
//							}else{
//								pathArea.append(path.get(i));
//							}
//						}
//						pathArea.append("->"+dstT.getText());
//					}else{
//						JOptionPane.showMessageDialog(null, "path is not exist", "error", JOptionPane.ERROR_MESSAGE);
//					}
//				}
//		});
//		basebox1.add(Box.createHorizontalStrut(500));
//		basebox1.add(write);
//		basebox1.add(Box.createHorizontalStrut(10));
//		basebox1.add(show);
//		jp2.add(basebox1,BorderLayout.CENTER);
//		jp.add(jp1,BorderLayout.NORTH);
//		jp.add(pathScroll,BorderLayout.CENTER);
//		jp.add(jp2, BorderLayout.SOUTH);
//		mainframe.add(jp);
//		mainframe.setVisible(true);
//		mainframe.setBounds(10,10,1300,600);
//		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}
}
