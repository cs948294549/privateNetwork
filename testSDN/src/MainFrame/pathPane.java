package MainFrame;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

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
import SDNproperty.Node;
import controllerOP.AllNodes;
import controllerOP.searchPath;
import controllerOP.weightTas;

public class pathPane extends JPanel{
	public pathPane(){
		this.setLayout(new BorderLayout());
		JPanel pane1=new JPanel();
		final JTextArea pathArea=new JTextArea();
		final JTextArea pathArea1=new JTextArea();
		Box info=Box.createHorizontalBox();
		final JLabel src=new JLabel("src-Host :");
		src.setFont(new Font("Courier",Font.BOLD,20));
		info.add(src);
		info.add(Box.createHorizontalStrut(10));
		final JTextField srcT=new JTextField(20);
		info.add(srcT);
		info.add(Box.createHorizontalStrut(10));
		final JLabel dst=new JLabel("dst-Host :");
		dst.setFont(new Font("Courier",Font.BOLD,20));
		info.add(dst);
		info.add(Box.createHorizontalStrut(10));
		final JTextField dstT=new JTextField(20);
		info.add(dstT);
		info.add(Box.createHorizontalStrut(10));
		JButton show=new JButton("show all path");
		show.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					if(!dstT.getText().equals("")&&!dstT.getText().equals("")&&!getDpid(srcT.getText()).equals("none")&&!getDpid(dstT.getText()).equals("none")){
						AllNodes.sers=new ArrayList<Object[]>();
						AllNodes.stack = new Stack<Node>(); 
						pathArea.setText("");
						AllNodes.showpath(getDpid(srcT.getText()), getDpid(dstT.getText()));
						pathArea.append(srcT.getText()+" to "+dstT.getText()+" path:\n");
						
						int weight = 0;		
						for(int i=0;i<AllNodes.sers.size();i++){
							weight = 0;
							String start ="";
							Object[] o=AllNodes.sers.get(i);
							for(int j=0;j<o.length;j++){
								Node nNode = (Node) o[j];  
								if(j<(o.length-1)){
									if(j == 0) start = nNode.getName();
									else{
										weight = weight + weightTas.getweight(start, nNode.getName());
										start = nNode.getName();
									}
									
									pathArea.append(nNode.getName()+"->");
								}else{
									weight = weight + weightTas.getweight(start, nNode.getName());
									pathArea.append(nNode.getName()+ "weight" + weight);
								}
							}
							pathArea.append("\n");
						}
					}else{
						if(dstT.getText().equals("")||dstT.getText().equals("")){
							JOptionPane.showMessageDialog(null, "src ip or dst ip is none", "error", JOptionPane.ERROR_MESSAGE);
						}else{
							JOptionPane.showMessageDialog(null, "path is not exist", "error", JOptionPane.ERROR_MESSAGE);
						}
					}
				} catch (IOException | JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		info.add(show);
		info.add(Box.createHorizontalStrut(10));
		JButton showflowpath=new JButton("show flow path");
		showflowpath.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!dstT.getText().equals("")&&!dstT.getText().equals("")&&!searchPath.showPath(srcT.getText(),dstT.getText()).isEmpty()){
					ArrayList<String> path=searchPath.showPath(srcT.getText(),dstT.getText());
					pathArea1.setText("");
					pathArea1.append(srcT.getText()+"   to   "+dstT.getText()+":");
					pathArea1.append("\n");
					pathArea1.append(srcT.getText()+"->");
					for(int i=0;i<path.size();i++){
							pathArea1.append(path.get(i)+"->");
					}
					pathArea1.append("->"+dstT.getText());
				}else{
					JOptionPane.showMessageDialog(null, "path is not exist", "error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		info.add(showflowpath);
		pane1.add(info,BorderLayout.CENTER);
		JLabel path=new JLabel("path:");
		path.setFont(new Font("Courier",Font.BOLD,20));
		add(path,BorderLayout.WEST);
		JScrollPane pathScroll=new JScrollPane();
		pathArea.setLineWrap(false);
		pathArea.setEditable(false);
		pathScroll.setViewportView(pathArea);
		
		JScrollPane pathScroll1=new JScrollPane();
		pathArea1.setLineWrap(false);
		pathArea1.setEditable(false);
		pathScroll1.setViewportView(pathArea1);
		
		Box boxpane2=Box.createVerticalBox();
		JLabel path1=new JLabel("all path:");
		JLabel path2=new JLabel("select path");
		boxpane2.add(path1);
		boxpane2.add(pathScroll);
		boxpane2.add(path2);
		boxpane2.add(pathScroll1);
		
		JPanel pane3=new JPanel();
		Box hbox=Box.createHorizontalBox();
		JLabel pathselect=new JLabel("choose path");
		final JTextField pathnum=new JTextField(20);
		JButton write=new JButton("write");
		write.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int num = Integer.valueOf(pathnum.getText()).intValue();
				Object[] o1;
				Object[] o2;
				if(num<=AllNodes.sers.size()){
					o1=AllNodes.sers.get(num);
					o2=new Object[o1.length];
					for(int i=0;i<o1.length;i++){
						Node nNode = (Node) o1[i];
						o2[i]=nNode.getName();
					}
					try {
						searchPath.writeFlow(srcT.getText(), dstT.getText(), o2);
					} catch (JSONException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(null, "please choose right path!", "error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		hbox.add(pathselect);
		hbox.add(pathnum);
		hbox.add(write);
		pane3.add(hbox,BorderLayout.CENTER);
		
		add(pane1,BorderLayout.NORTH);
		add(boxpane2,BorderLayout.CENTER);
		add(pane3,BorderLayout.SOUTH);
	}
	public static String getDpid(String ipv6) throws JSONException, IOException{
		if(topoPane.nodes==null){
			topoPane.createNodes();
		}
		JSONArray hosts=topoPane.nodes;
		for(int i=0;i<hosts.length();i++){
			if(hosts.getJSONObject(i).getString("type").equals("host")&&hosts.getJSONObject(i).getString("ipv6").equals(ipv6)){
				return hosts.getJSONObject(i).getString("attachPoint");
			}
		}
		return "none";
	}
	public static void main(String argv[]) throws JSONException, IOException{
		JFrame jf=new JFrame();
		pathPane path=new pathPane();
		jf.add(path);
		jf.setVisible(true);
		jf.setBounds(10,10,400,300);
		jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
		System.out.println(getDpid("2001:db8::30"));
	}
}
