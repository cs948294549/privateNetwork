package MainFrame;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONException;
import SDNproperty.Switch;

public class switchPane extends JPanel{
	public static JSONArray switchPane=null;
	public static void createSwitchPane() throws JSONException, IOException{
		switchPane=Switch.getSwitch();
	}
	public switchPane() throws IOException, JSONException{
		setLayout(new BorderLayout());
		//JSONArray str=Switch.getSwitch();
		Box baseBox=Box.createVerticalBox();
		if(switchPane==null){
			createSwitchPane();
		}
		baseBox.add(Box.createVerticalStrut(8));
		JLabel jb=new JLabel("Switch ("+switchPane.length()+")");
		jb.setFont(new Font("Courier",Font.BOLD,20));
		baseBox.add(jb);
		
		JScrollPane scrollpane=new JScrollPane();
		JTable table=getTable(switchPane);
		scrollpane.setViewportView(table);
		scrollpane.setLocation(20,300);
		baseBox.add(scrollpane);
		baseBox.add(Box.createVerticalStrut(8));
		
		add(baseBox,BorderLayout.CENTER);
	}
	public static JTable getTable(JSONArray str) throws JSONException, IOException{
		final JTable table;
		Object[][] tableValue=new Object[][] {
			{null, null, null, null},
			{null, null, null, null},
			{null, null, null, null}
		};
		String[] columnName=new String[] {"switch name","DPID","connect time"};
		final DefaultTableModel tableModel=new DefaultTableModel(tableValue,columnName){
			Class[] columnTypes = new Class[] {
					String.class, String.class, String.class, String.class
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
					    		 str.getJSONObject(i).getString("dpid"),
					    		 //str.getJSONObject(i).getString("ip"),
					    		 sdf.format(str.getJSONObject(i).getLong("time"))
					    		 };
					     dtm.addRow(row);
					}
				} catch (JSONException e1) {
					e1.printStackTrace();
					}
		table.setAutoResizeMode(4);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setSelectionBackground(Color.YELLOW);
		table.setSelectionForeground(Color.RED);
		table.setRowHeight(30);//���ñ����ʽ
		table.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				int selectedRow=table.getSelectedRow();
				Object sw=tableModel.getValueAt(selectedRow, 1);
				try {
					JFrame flowpane=flowFrame.getflowPane(sw.toString());
				} catch (JSONException | IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		return table;
	}
}
