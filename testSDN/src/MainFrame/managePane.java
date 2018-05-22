package MainFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import controllerOP.manageNetwork;

public class managePane extends JPanel{
	public static JTextField mac=new JTextField(20);
	public final JTextField vlan=new JTextField(20);
	public static JSONObject dictionVlan=new JSONObject();
	public static JSONObject dictionMAC=new JSONObject();
	public managePane() throws IOException, JSONException{
		this.setLayout(new BorderLayout());
		if(hostPane.hostPane==null){
			hostPane.createHostPane();
		}
		Box baseBox=Box.createVerticalBox();
		
		baseBox.add(Box.createVerticalStrut(8));
		JLabel jb=new JLabel("Host ("+hostPane.hostPane.length()+")");
		jb.setFont(new Font("Courier",Font.BOLD,20));
		baseBox.add(jb);
		
		baseBox.add(Box.createVerticalStrut(8));
		JScrollPane scrollpane=new JScrollPane();
		JTable table=getTable(hostPane.hostPane);
		scrollpane.setViewportView(table);
		scrollpane.setLocation(20,300);
		baseBox.add(scrollpane);
		baseBox.add(Box.createVerticalStrut(8));
		JPanel hostMac=new JPanel();
		hostMac.add(new JLabel("host MAC"));
		hostMac.add(mac);
		baseBox.add(hostMac);
		
		JSONArray json=new JSONArray();
		try {
			//json=new JSONArray("[{\"guid\":\"NetworkId2\",\"portMac\":[],\"gateway\":null,\"name\":\"vlan2\"},{\"guid\":\"NetworkId1\",\"portMac\":[{\"port\":\"1\",\"mac\":\"00:00:00:00:00:01\"}],\"gateway\":null,\"name\":\"vlan1\"}]");
			json=manageNetwork.getNetwork();
		} catch (JSONException e1) {
			System.out.println("input errors");
		}
		final JTree tree;
		final DefaultTreeModel treeModel;
		JScrollPane treescroll=new JScrollPane();
		Box baseBox1=Box.createVerticalBox();
		baseBox1.add(Box.createVerticalStrut(8));
		DefaultMutableTreeNode rootNode=new DefaultMutableTreeNode("network");
		for(int i=0;i<json.length();i++){
			DefaultMutableTreeNode child1=new DefaultMutableTreeNode(json.getJSONObject(i).getString("name"));
			for(int j=0;j<json.getJSONObject(i).getJSONArray("portMac").length();j++){
				DefaultMutableTreeNode child11=new DefaultMutableTreeNode(json.getJSONObject(i).getJSONArray("portMac").getJSONObject(j).getString("mac"));
				child1.add(child11);
				dictionMAC.put(json.getJSONObject(i).getJSONArray("portMac").getJSONObject(j).getString("mac"), json.getJSONObject(i).getString("guid"));
			}
			rootNode.add(child1);
			dictionVlan.put(json.getJSONObject(i).getString("name"), json.getJSONObject(i).getString("guid"));
		}
		treeModel=new DefaultTreeModel(rootNode);
		tree=new JTree(treeModel);
		tree.setBounds(700,10,1300,600);
		tree.addTreeSelectionListener(new TreeSelectionListener(){
			public void valueChanged(TreeSelectionEvent e){
				if(!tree.isSelectionEmpty()){
					/*TreePath[] selectionPaths=tree.getSelectionPaths();
					for(int i=0;i<selectionPaths.length;i++){
						TreePath treePath=selectionPaths[i];
						Object[] path=treePath.getPath();
						for(int j=0;j<path.length;j++){
							DefaultMutableTreeNode node=(DefaultMutableTreeNode)path[j];
							textArea.append(node.getUserObject()+(j==(path.length-1)?"":"-->"));
						}
					}*/
					TreePath treePath=tree.getSelectionPath();
					DefaultMutableTreeNode paNode=(DefaultMutableTreeNode)treePath.getLastPathComponent();
					if(!paNode.isRoot()){
						vlan.setText(paNode.getUserObject().toString());
					}
				}
			}
		});
		treescroll.setViewportView(tree);
		baseBox1.add(treescroll);
		baseBox1.add(Box.createVerticalStrut(8));
		
		JButton add=new JButton("add");
		add.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				TreePath selectionPath;
				try{
					selectionPath=tree.getSelectionPath();
					DefaultMutableTreeNode paNode=(DefaultMutableTreeNode)selectionPath.getLastPathComponent();
					if(paNode.isRoot()){//input vlan
						if(!vlan.getText().toString().equals("")){
							try{
								DefaultMutableTreeNode node=new DefaultMutableTreeNode(vlan.getText());
								try{
									dictionVlan.getString(vlan.getText());
									JOptionPane.showMessageDialog(null, "vlan is exist", "error", JOptionPane.ERROR_MESSAGE);
									}catch(Exception e2){
										manageNetwork.createNetwork("{\"network\":{\"name\":\""+vlan.getText().toString()+"\"}}","NetworkId="+vlan.getText());
										dictionVlan.put(vlan.getText(),"NetworkId="+vlan.getText());
										System.out.println(dictionVlan);
										treeModel.insertNodeInto(node, paNode, paNode.getChildCount());
										TreePath path=selectionPath.pathByAddingChild(node);
										if(!tree.isVisible(path)){
											tree.makeVisible(path);
										}
								}
							}catch(Exception e1){System.out.println("add vlan error!");}
						}else{
							JOptionPane.showMessageDialog(null, "please input vlan", "error", JOptionPane.ERROR_MESSAGE);
						}
					}
					else if(paNode.getParent().toString().equals("network")){//input mac
							DefaultMutableTreeNode node=new DefaultMutableTreeNode(mac.getText());
							System.out.println(mac.getText());
							System.out.println(dictionMAC);
							try{
								dictionMAC.getString(mac.getText());
								System.out.println(dictionVlan);
								JOptionPane.showMessageDialog(null, "host is exist", "error", JOptionPane.ERROR_MESSAGE);
								}catch(Exception e2){
									System.out.println(dictionVlan);
									System.out.println(paNode.getUserObject());
									manageNetwork.bindHost("{\"attachment\":{\"mac\":\""+mac.getText().toString()+"\"}}",dictionVlan.getString(paNode.getUserObject().toString()),mac.getText());
									dictionMAC.put(mac.getText(), dictionVlan.getString(paNode.getUserObject().toString()));
									treeModel.insertNodeInto(node, paNode, paNode.getChildCount());
									TreePath path=selectionPath.pathByAddingChild(node);
									if(!tree.isVisible(path)){
										tree.makeVisible(path);
									}
							}
					}
				}catch(Exception e1){JOptionPane.showMessageDialog(null, "please press any key", "error", JOptionPane.ERROR_MESSAGE);}
			vlan.setText("");	
			}
			});
		JButton del=new JButton("delete");
		del.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					DefaultMutableTreeNode node=(DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
					if(!node.isRoot()){
						if(node.getParent().toString().equals("network")){
							try{
								manageNetwork.rmNetwork(dictionVlan.getString(node.getUserObject().toString()));
								dictionVlan.remove(node.getUserObject().toString());
								DefaultMutableTreeNode nextNode=node.getNextSibling();
								if(nextNode==null){
									nextNode=(DefaultMutableTreeNode) node.getParent();
								}
								treeModel.removeNodeFromParent(node);
								tree.setSelectionPath(new TreePath(nextNode.getPath()));
							}catch(Exception e1){
								System.out.println("fail to delete vlan");
							}
						}else{
							try{
								manageNetwork.rmHost(dictionMAC.getString(node.getUserObject().toString()),node.getUserObject().toString());
								dictionMAC.remove(node.getUserObject().toString());
								System.out.println(dictionMAC);
								DefaultMutableTreeNode nextNode=node.getNextSibling();
								if(nextNode==null){
									nextNode=(DefaultMutableTreeNode) node.getParent();
								}
								treeModel.removeNodeFromParent(node);
								tree.setSelectionPath(new TreePath(nextNode.getPath()));
							}catch(Exception e1){
								System.out.println("fail to delete host");
							}
						}
					}
				}catch(Exception e1){JOptionPane.showMessageDialog(null, "please press any key", "error", JOptionPane.ERROR_MESSAGE);}
				vlan.setText("");
			}
		});
		JPanel treePane=new JPanel();
		Box vlanboxV=Box.createVerticalBox();
		Box vlanboxH1=Box.createHorizontalBox();
		vlanboxH1.add(new JLabel("vlan name:"));
		vlanboxH1.add(Box.createHorizontalStrut(10));
		vlanboxH1.add(vlan);
		vlanboxV.add(vlanboxH1);
		Box vlanboxH2=Box.createHorizontalBox();
		vlanboxH2.add(add);
		vlanboxH2.add(Box.createHorizontalStrut(50));
		//vlanboxH2.add(alter);
		vlanboxH2.add(Box.createHorizontalStrut(50));
		vlanboxH2.add(del);
		vlanboxH2.add(Box.createHorizontalStrut(50));
		vlanboxV.add(vlanboxH2);
		treePane.add(vlanboxV);
		baseBox1.add(treePane);
		baseBox1.add(Box.createVerticalStrut(8));
		
		final JSplitPane splitPane=new JSplitPane();
		splitPane.setLeftComponent(baseBox);
		splitPane.setRightComponent(baseBox1);
		splitPane.setDividerLocation(600);
		this.add(splitPane,BorderLayout.CENTER);
	}
	public static JTable getTable(JSONArray str) throws IOException, JSONException{
		final JTable table;
		Object[][] tableValue=new Object[][] {
			{null, null, null, null, null},
			{null, null, null, null, null},
			{null, null, null, null, null}
		};
		String[] columnName=new String[] {"host name","ip address","mac address","attachpoint","connect time"};
		final DefaultTableModel tableModel = new DefaultTableModel(tableValue,columnName){
			Class[] columnTypes = new Class[] {
					String.class, String.class, String.class, String.class, String.class
					};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			};
		table=new JTable(tableModel);
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		dtm.setRowCount(0);
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
				for(int i=0;i<str.length();i++)
				{
					    Object[] row = {
					    		str.getJSONObject(i).getString("name"),
					    		str.getJSONObject(i).getString("ip"),
					    		str.getJSONObject(i).getString("mac"),
					    		str.getJSONObject(i).getString("attachPoint")+"-"+str.getJSONObject(i).getString("port"),
					    		sdf.format(str.getJSONObject(i).getLong("time"))
					    		};
					    dtm.addRow(row);
				}
				} catch (JSONException e1) {
					e1.printStackTrace();
					}
		table.setAutoResizeMode(4);
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		table.setSelectionBackground(Color.YELLOW);
		table.setSelectionForeground(Color.RED);
		table.setRowHeight(30);//���ñ����ʽ
		table.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				int selectedRow=table.getSelectedRow();
				Object name=tableModel.getValueAt(selectedRow, 0);
				Object ip=tableModel.getValueAt(selectedRow, 1);
				Object mac=tableModel.getValueAt(selectedRow, 2);
				Object attachpoint=tableModel.getValueAt(selectedRow, 3);
				Object time=tableModel.getValueAt(selectedRow, 4);
				managePane.mac.setText(mac.toString());
			}
		});
		return table;
	}
	public static void main(String argv[]) throws IOException, JSONException{
		JFrame jf=new JFrame();
		jf.add(new managePane());
		jf.setVisible(true);
		jf.setBounds(10,10,1300,600);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
