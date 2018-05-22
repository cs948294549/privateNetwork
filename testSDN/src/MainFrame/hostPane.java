package MainFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONException;
import SDNproperty.Host;


public class hostPane extends JPanel{
	public static JSONArray hostPane=null;
	public static void createHostPane() throws IOException, JSONException{
	hostPane=Host.getHost();
	}
	public hostPane() throws IOException, JSONException{
		this.setLayout(new BorderLayout());
		if(hostPane==null){
			createHostPane();
		}
		Box baseBox=Box.createVerticalBox();
		
		baseBox.add(Box.createVerticalStrut(8));
		JLabel jb=new JLabel("Host ("+hostPane.length()+")");
		jb.setFont(new Font("Courier",Font.BOLD,20));
		baseBox.add(jb);
		
		baseBox.add(Box.createVerticalStrut(8));
		JScrollPane scrollpane=new JScrollPane();
		JTable table=getTable(hostPane);
		scrollpane.setViewportView(table);
		scrollpane.setLocation(20,300);
		baseBox.add(scrollpane);
		baseBox.add(Box.createVerticalStrut(8));
		
		add(baseBox,BorderLayout.CENTER);
	}
	public static JTable getTable(JSONArray str) throws IOException, JSONException{
		final JTable table;
		Object[][] tableValue=new Object[][] {
			{null, null, null, null, null},
			{null, null, null, null, null},
			{null, null, null, null, null}
		};
		String[] columnName=new String[] {"host name","ipv6 address","mac address","attachpoint","connect time"};
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
					    		str.getJSONObject(i).getString("ipv6"),
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
				JFrame hostInfo=hostFrame.gethostFrame(name, ip, mac, attachpoint, time);
			}
		});
		return table;
	}
}
