package MainFrame;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import org.json.JSONException;

public class SDNFrame extends JFrame {
	static JTabbedPane tabbedPane=new JTabbedPane();
	controllerPane controllerPane=new controllerPane();
	pathPane path=new pathPane();
	//managePane manage=new managePane();
	manageSQL manage=new manageSQL();
	topoPane topoPane=new topoPane();
	JButton fresh=new JButton("fresh");
	public SDNFrame() throws IOException,JSONException, InterruptedException{
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setBounds(10,10,1300,660);
		this.setTitle("SDN");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		tabbedPane.addTab("controller", controllerPane);
		tabbedPane.addTab("topo", topoPane);
		tabbedPane.addTab("management",manage);
		tabbedPane.addTab("path", path);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
/*		add(fresh, BorderLayout.EAST);
		fresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(tabbedPane.getSelectedIndex()>0){
				System.out.println(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex())+tabbedPane.getSelectedIndex());
				switch(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex())){
				case "path":tabbedPane.removeTabAt(2);path=new pathPane();tabbedPane.insertTab("path", null, path, null, 2);tabbedPane.setSelectedComponent(path);break;
//				case "management":tabbedPane.removeTabAt(2);try {
//						manage=new managePane();
//					} catch (IOException | JSONException e2) {
//						// TODO Auto-generated catch block
//						e2.printStackTrace();
//					}tabbedPane.insertTab("management", null, manage, null, 2);tabbedPane.setSelectedComponent(manage);break;
				case "topo":tabbedPane.removeTabAt(1);try {
						topoPane=new topoPane();
					} catch (IOException | JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}tabbedPane.insertTab("topo", null, topoPane, null, 1);tabbedPane.setSelectedComponent(topoPane);;break;
				default:tabbedPane.removeAll();
				tabbedPane.addTab("controller", controllerPane);
				tabbedPane.addTab("topo", topoPane);
				//tabbedPane.addTab("management",manage);
				tabbedPane.addTab("path", path);break;
				}
				}
				else{
					tabbedPane.removeAll();
					tabbedPane.addTab("controller", controllerPane);
					tabbedPane.addTab("topo", topoPane);
					//tabbedPane.addTab("management",manage);
					tabbedPane.addTab("path", path);
				}
				getContentPane().add(tabbedPane, BorderLayout.CENTER);
			}
		});
		*/
	}
	public static void main(String argv[])throws IOException, JSONException, InterruptedException{
		SDNFrame mf=new SDNFrame();
//		while(true){
//		mf.setPreferredSize(new Dimension(1300,660));//new Dimension£¨£¬£©
//		mf.pack();
//		mf.remove(SDNFrame.tabbedPane);
//		mf.topoPane=new topoPane();
//		mf.tabbedPane=new JTabbedPane();
//		mf.tabbedPane.addTab("controller", mf.controllerPane);
//		mf.tabbedPane.addTab("topo", mf.topoPane);
//		mf.getContentPane().add(mf.tabbedPane, BorderLayout.CENTER);
//		Thread.sleep(2000);
//		}
	}
}
