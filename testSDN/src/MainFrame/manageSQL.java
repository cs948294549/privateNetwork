package MainFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import controllerOP.authMysql;

public class manageSQL extends JPanel {
	private static JTable table1;
	private static JTable table2;
	private static Box baseBox1;
	private static Box baseBox2;
	private static Box baseBox3;
	private static JSplitPane splitPane;
	private static JSplitPane splitPane1;
	private static JTextField text1;
	private static JTextField text2;
	private static JTextField pwd;
	private static JTextField usertovlan;
	private static JTree tree;
	private static DefaultTreeModel treeModel;
	public manageSQL(){
		this.setLayout(new BorderLayout());
		
		
//////////////////////user
		baseBox1=Box.createVerticalBox();
		JLabel jb1=new JLabel("user");
		baseBox1.add(jb1);
		final JScrollPane scrollpane1=new JScrollPane();
		table1=getTable("select * from user");
		scrollpane1.setViewportView(table1);
		scrollpane1.setLocation(20,300);
		baseBox1.add(scrollpane1);
		baseBox1.add(Box.createVerticalStrut(8));
		Box baseBoxH1=Box.createHorizontalBox();
		JLabel username=new JLabel("user:");
		text1=new JTextField(10);
		baseBoxH1.add(username);
		baseBoxH1.add(text1);
		baseBox1.add(baseBoxH1);
		baseBox1.add(Box.createVerticalStrut(8));
		
		Box baseBoxH2=Box.createHorizontalBox();
		JLabel password=new JLabel("password:");
		pwd=new JTextField(10);
		baseBoxH2.add(password);
		baseBoxH2.add(pwd);
		baseBox1.add(baseBoxH2);
		baseBox1.add(Box.createVerticalStrut(8));
		
		Box baseBoxH3=Box.createHorizontalBox();
		JButton search1=new JButton("search user");
		search1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				baseBox1.remove(scrollpane1);
				if(!text1.getText().equals("")){
				table1=getTable("select * from user where user='"+text1.getText()+"'");
				}
				else{
					table1=getTable("select * from user");
				}
				scrollpane1.setViewportView(table1);
				scrollpane1.setLocation(20,300);
				baseBox1.add(scrollpane1,1);
				splitPane.setLeftComponent(baseBox1);

			}
		});
		JButton add1=new JButton("add user");
		add1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!text1.getText().equals("")){
					authMysql.operateSQL("insert into user(user,password) values('"+text1.getText()+"','"+pwd.getText()+"')");
					baseBox1.remove(scrollpane1);
					table1=getTable("select * from user");
					scrollpane1.setViewportView(table1);
					scrollpane1.setLocation(20,300);
					baseBox1.add(scrollpane1,1);
					splitPane.setLeftComponent(baseBox1);
					}else{
						JOptionPane.showMessageDialog(null, "username can't be empty!", "error", JOptionPane.ERROR_MESSAGE);
						}
			}
		});
		JButton del1=new JButton("del user");
		del1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				authMysql.operateSQL("delete from user where user='"+text1.getText()+"'");
				authMysql.operateSQL("delete from usertovlan where user='"+text1.getText()+"'");
				authMysql.operateSQL("delete from portal where user='"+text1.getText()+"'");
				baseBox1.remove(scrollpane1);
				table1=getTable("select * from user");
				scrollpane1.setViewportView(table1);
				scrollpane1.setLocation(20,300);
				baseBox1.add(scrollpane1,1);
				splitPane.setLeftComponent(baseBox1);
			}
		});
		baseBoxH3.add(add1);
		baseBoxH3.add(del1);
		baseBoxH3.add(search1);
		baseBox1.add(baseBoxH3);
		baseBox1.add(Box.createVerticalStrut(8));
		
///////////////////////usertovlan
		baseBox2=Box.createVerticalBox();
		JLabel jb2=new JLabel("usertovlan");
		baseBox2.add(jb2);
		final JScrollPane scrollpane2=new JScrollPane();
		table2=getTable1("select * from usertovlan");
		scrollpane2.setViewportView(table2);
		scrollpane2.setLocation(20,300);
		baseBox2.add(scrollpane2);
		baseBox2.add(Box.createVerticalStrut(8));
		Box baseBox2H1=Box.createHorizontalBox();
		usertovlan=new JTextField(10);
		JLabel vlan_user=new JLabel("user:");
		baseBox2H1.add(vlan_user);
		baseBox2H1.add(usertovlan);
		baseBox2.add(baseBox2H1);
		baseBox2.add(Box.createVerticalStrut(8));
		
		Box baseBox2H2=Box.createHorizontalBox();
		text2=new JTextField(10);
		JLabel vlan_vlan=new JLabel("vlan:");
		baseBox2H2.add(vlan_vlan);
		baseBox2H2.add(text2);
		baseBox2.add(baseBox2H2);
		baseBox2.add(Box.createVerticalStrut(8));
		
		Box baseBox2H3=Box.createHorizontalBox();
		JButton search2=new JButton("search user-vlan");
		search2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				baseBox2.remove(scrollpane2);
				if(!text2.getText().equals("")&&usertovlan.getText().equals("")){
					table2=getTable1("select * from usertovlan where vlan='"+text2.getText()+"'");
				}
				else if(text2.getText().equals("")&&!usertovlan.getText().equals("")){
					table2=getTable1("select * from usertovlan where user='"+usertovlan.getText()+"'");
				}
				else{
					table2=getTable1("select * from usertovlan");
				}
				scrollpane2.setViewportView(table2);
				scrollpane2.setLocation(20,300);
				baseBox2.add(scrollpane2,1);
				splitPane1.setLeftComponent(baseBox2);
			}
		});
		JButton add2=new JButton("add user-vlan");
		add2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!usertovlan.getText().equals("")){
					authMysql.operateSQL("insert into usertovlan(user,vlan) values('"+usertovlan.getText()+"','"+text2.getText()+"')");
					baseBox2.remove(scrollpane2);
					table2=getTable1("select * from usertovlan");
					scrollpane2.setViewportView(table2);
					scrollpane2.setLocation(20,300);
					baseBox2.add(scrollpane2,1);
					splitPane1.setLeftComponent(baseBox2);
				}
				else{
					JOptionPane.showMessageDialog(null, "username can't be empty!", "error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		JButton del2=new JButton("del user-vlan");
		del2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!text2.getText().equals("")&&usertovlan.getText().equals("")){
					authMysql.operateSQL("delete from usertovlan where vlan='"+text2.getText()+"'");
					authMysql.operateSQL("delete from portal where vlan='"+text2.getText()+"'");
				}
				else if(text2.getText().equals("")&&!usertovlan.getText().equals("")){
					authMysql.operateSQL("delete from usertovlan where user='"+usertovlan.getText()+"'");
					authMysql.operateSQL("delete from portal where user='"+usertovlan.getText()+"'");
				}
				baseBox2.remove(scrollpane2);
				table2=getTable1("select * from usertovlan");
				scrollpane2.setViewportView(table2);
				scrollpane2.setLocation(20,300);
				baseBox2.add(scrollpane2,1);
				splitPane1.setLeftComponent(baseBox2);
			}
		});
		baseBox2H3.add(add2);
		baseBox2H3.add(del2);
		baseBox2H3.add(search2);
		baseBox2.add(baseBox2H3);
		baseBox2.add(Box.createVerticalStrut(8));
///////////////////////portal
		baseBox3=Box.createVerticalBox();
		JLabel jb3=new JLabel("portal");
		baseBox3.add(jb3);
		final JScrollPane treescroll=new JScrollPane();
		treeModel=treemod();
		tree=new JTree(treeModel);
		tree.setBounds(700,10,1300,600);
		treescroll.setViewportView(tree);
		baseBox3.add(treescroll);
		JButton renew=new JButton("new");
		renew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				baseBox3.remove(treescroll);
				treeModel=treemod();
				tree=new JTree(treeModel);
				treescroll.setViewportView(tree);
				treescroll.setLocation(20,300);
				baseBox3.add(treescroll,1);
				splitPane1.setRightComponent(baseBox3);
			}
		});
		JButton delete=new JButton("delete");
		delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DefaultMutableTreeNode node=(DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				if(!node.isRoot()){
					if(node.getParent().getParent().getParent().toString().equals("network")){
						//System.out.println(node.toString());
						authMysql.operateSQL("delete from portal where mac='"+node.toString()+"'");
						DefaultMutableTreeNode nextNode=node.getNextSibling();
						if(nextNode==null){
							nextNode=(DefaultMutableTreeNode) node.getParent();
						}
						treeModel.removeNodeFromParent(node);
						tree.setSelectionPath(new TreePath(nextNode.getPath()));
					}
				}
			}
		});
		baseBox3.add(renew);
		baseBox3.add(Box.createVerticalStrut(8));
		baseBox3.add(delete);
		baseBox3.add(Box.createVerticalStrut(8));
		splitPane=new JSplitPane();
		splitPane1=new JSplitPane();
		splitPane.setLeftComponent(baseBox1);
		splitPane.setRightComponent(splitPane1);
		splitPane.setDividerLocation(400);
		splitPane1.setLeftComponent(baseBox2);
		splitPane1.setRightComponent(baseBox3);
		splitPane1.setDividerLocation(400);
		this.add(splitPane,BorderLayout.CENTER);
	}
	public static DefaultTreeModel treemod(){
		DefaultTreeModel treeModel;
		DefaultMutableTreeNode rootNode=new DefaultMutableTreeNode("network");
		ResultSet rs=authMysql.searchSQL("select distinct vlan from usertovlan");
		DefaultMutableTreeNode child;
		DefaultMutableTreeNode child1;
		DefaultMutableTreeNode child11;
		try {
			while(rs.next()){
			child=new DefaultMutableTreeNode(rs.getString("vlan"));
			ResultSet rs1=authMysql.searchSQL("select distinct user from portal where vlan='"+rs.getString("vlan")+"'");
			while(rs1.next()){
				child1=new DefaultMutableTreeNode(rs1.getString("user"));
				ResultSet rs11=authMysql.searchSQL("select mac from portal where user='"+rs1.getString("user")+"'");
				while(rs11.next()){
					child11=new DefaultMutableTreeNode(rs11.getString("mac"));
					child1.add(child11);
				}
				child.add(child1);
			}
			rootNode.add(child);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		treeModel=new DefaultTreeModel(rootNode);
		return treeModel;
	}
	public static JTable getTable(String sql){
		final JTable table;
		Object[][] tableValue=new Object[][] {
			{null, null},
			{null, null},
			{null, null}
		};
		String[] columnName=new String[] {"user","password"};
		final DefaultTableModel tableModel = new DefaultTableModel(tableValue,columnName){
			Class[] columnTypes = new Class[] {
					String.class, String.class
					};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			};
		table=new JTable(tableModel);
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		dtm.setRowCount(0);
		if(sql!=""){
			ResultSet rs=authMysql.searchSQL(sql);
			try {
				while(rs.next()){
					//System.out.println(rs.getString("user")+"-----"+rs.getString("password"));
					Object[] row = {
							rs.getString("user"),
							rs.getString("password")
				    		};
				    dtm.addRow(row);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		table.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				int selectedRow=table.getSelectedRow();
				Object user=tableModel.getValueAt(selectedRow, 0);
				Object pword=tableModel.getValueAt(selectedRow, 1);
				manageSQL.text1.setText(user.toString());
				manageSQL.pwd.setText(pword.toString());
			}
		});
		table.setAutoResizeMode(4);
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		table.setSelectionBackground(Color.YELLOW);
		table.setSelectionForeground(Color.RED);
		table.setRowHeight(30);//设置表格样式
		return table;
	}
	
	public static JTable getTable1(String sql){
		final JTable table;
		Object[][] tableValue=new Object[][] {
			{null, null},
			{null, null},
			{null, null}
		};
		String[] columnName=new String[] {"user","vlan"};
		final DefaultTableModel tableModel = new DefaultTableModel(tableValue,columnName){
			Class[] columnTypes = new Class[] {
					String.class, String.class
					};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			};
		table=new JTable(tableModel);
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		dtm.setRowCount(0);
		if(sql!=""){
			ResultSet rs=authMysql.searchSQL(sql);
			try {
				while(rs.next()){
					Object[] row = {
							rs.getString("user"),
							rs.getString("vlan")
				    		};
				    dtm.addRow(row);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		table.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				int selectedRow=table.getSelectedRow();
				Object user=tableModel.getValueAt(selectedRow, 0);
				Object vlan=tableModel.getValueAt(selectedRow, 1);
				manageSQL.usertovlan.setText(user.toString());
				manageSQL.text2.setText(vlan.toString());
			}
		});
		table.setAutoResizeMode(4);
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		table.setSelectionBackground(Color.YELLOW);
		table.setSelectionForeground(Color.RED);
		table.setRowHeight(30);//设置表格样式
		return table;
	}
	public static void main(String argv[]){
		JFrame jf=new JFrame();
		jf.add(new manageSQL());
		jf.setVisible(true);
		jf.setBounds(10,10,1300,600);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
