package MainFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONException;

import controllerOP.controllerAPI;
import controllerOP.flowOP;

public class flowFrame{
	public static JFrame getflowPane(final String sw) throws JSONException, IOException{
		final JFrame jf=new JFrame();
		JPanel jp=new JPanel();
		jp.setLayout(new BorderLayout());
		JSONArray str=controllerAPI.getFlows(sw);
		Box baseBox=Box.createVerticalBox();
		baseBox.add(Box.createVerticalStrut(8));
		
		JLabel switchjb=new JLabel("switchID : "+sw);
		switchjb.setFont(new Font("宋体", Font.PLAIN, 17));
		baseBox.add(switchjb);
		baseBox.add(Box.createVerticalStrut(8));
		
		JScrollPane scrollpaneport=new JScrollPane();
		JTable porttable = getswitchTable(sw);
		scrollpaneport.setViewportView(porttable);
		baseBox.add(scrollpaneport);
		baseBox.add(Box.createVerticalStrut(8));
		
		JLabel flowjb=new JLabel("Flows ("+str.length()+")");
		flowjb.setFont(new Font("宋体", Font.PLAIN, 17));
		baseBox.add(flowjb);
		baseBox.add(Box.createVerticalStrut(8));
		
		JScrollPane scrollpaneflow=new JScrollPane();
		JTable flowtable = getTable(sw);
		scrollpaneflow.setViewportView(flowtable);
		baseBox.add(scrollpaneflow);
		baseBox.add(Box.createVerticalStrut(8));
		
		JButton addflow=new JButton("addflow");
		addflow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				jf.dispose();
				JFrame addflowjf=addflowFrame.addPane(sw);
			}
		});
		baseBox.add(addflow);
		baseBox.add(Box.createVerticalStrut(8));
		
		JButton delflow=new JButton("delflows");
		delflow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				jf.dispose();
				try {
					flowOP.removeall(sw);
					JFrame flowpane=flowFrame.getflowPane(sw.toString());
				} catch (JSONException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		baseBox.add(delflow);
		baseBox.add(Box.createVerticalStrut(8));
		
		jp.add(baseBox,BorderLayout.CENTER);
		jf.add(jp);
		jf.setVisible(true);
		jf.setBounds(10,10,1300,650);
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		return jf;
	}
	private static JTable getswitchTable(String sw) throws JSONException, IOException{
		JTable table = new JTable();
		JSONArray str=controllerAPI.getSwitchFeature(sw);
		Object[][] tableValue=new Object[][] {
			{null, null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null, null}
		};
		String[] columnName=new String[] {
				"portNumber","hardwareAddress","name","config","state","currentFeatures","advertisedFeatures","supportedFeatures","peerFeatures"
		};
		DefaultTableModel tableModel = new DefaultTableModel(tableValue,columnName){
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class, String.class, String.class, String.class,String.class,String.class
				};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
				}
			};
			table=new JTable(tableModel);
			DefaultTableModel dtm = (DefaultTableModel) table.getModel();
			dtm.setRowCount(0);
				try {
					for(int i=0;i<str.length();i++)
					{
						Object[] row = {
								str.getJSONObject(i).getString("portNumber"),
								str.getJSONObject(i).getString("hardwareAddress"),
								str.getJSONObject(i).getString("name"),
								str.getJSONObject(i).getString("config"),
								str.getJSONObject(i).getString("state"),
								str.getJSONObject(i).getString("currentFeatures"),
								str.getJSONObject(i).getString("advertisedFeatures"),
								str.getJSONObject(i).getString("supportedFeatures"),
								str.getJSONObject(i).getString("peerFeatures"),
						};
						dtm.addRow(row);
					}
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
		table.setBorder(new LineBorder(new Color(0, 1, 0)));
		table.setAutoResizeMode(4);
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		table.setSelectionBackground(Color.YELLOW);
		table.setSelectionForeground(Color.RED);
		table.setRowHeight(30);//设置表格样式	
		return table;
	}
	private static JTable getTable(String sw) throws JSONException, IOException{
		JTable table = new JTable();
		JSONArray str=controllerAPI.getFlows(sw);
		Object[][] tableValue=new Object[][] {
			{null, null, null, null, null, null, null,null,null,null,null},
			{null, null, null, null, null, null, null,null,null,null,null},
			{null, null, null, null, null, null, null,null,null,null,null}
		};
		String[] columnName=new String[] {
				"version","cookie","tableId","packetCount","byteCount","durationSeconds","priority","idleTimeoutSec","hardTimeoutSec","match","actions"
		};
		DefaultTableModel tableModel = new DefaultTableModel(tableValue,columnName){
				Class[] columnTypes = new Class[] {
					String.class, String.class, String.class, String.class, Integer.class, String.class, String.class,String.class,String.class,String.class,String.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
		};
		table=new JTable(tableModel);
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		dtm.setRowCount(0);
		try {
			for(int i=0;i<str.length();i++)
			{
			     Object[] row = {
			    		 str.getJSONObject(i).getString("version"),
			    		 str.getJSONObject(i).getString("cookie"),
			    		 str.getJSONObject(i).getString("tableId"),
			    		 str.getJSONObject(i).getString("packetCount"),
			    		 str.getJSONObject(i).getString("byteCount"),
			    		 str.getJSONObject(i).getString("durationSeconds"),
			    		 str.getJSONObject(i).getString("priority"),
			    		 str.getJSONObject(i).getString("idleTimeoutSec"),
			    		 str.getJSONObject(i).getString("hardTimeoutSec"),
			    		 str.getJSONObject(i).getString("match"),
			    		 str.getJSONObject(i).getJSONObject("instructions").getString("instruction_apply_actions")};
        		 dtm.addRow(row);
			}
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		table.setBorder(new LineBorder(new Color(0, 1, 0)));
		table.setAutoResizeMode(4);
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		table.setSelectionBackground(Color.YELLOW);
		table.setSelectionForeground(Color.RED);
		table.setRowHeight(30);//设置表格样式
		return table;
	}
}
