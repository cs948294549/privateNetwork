package MainFrame;
import java.awt.BorderLayout;
import java.awt.Font;
import java.io.IOException;
import javax.swing.*;
import org.json.JSONException;

public class controllerPane extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public controllerPane() throws IOException, JSONException{
		setLayout(new BorderLayout());
		if(switchPane.switchPane==null){
			switchPane.createSwitchPane();
		}
		if(hostPane.hostPane==null){
			hostPane.createHostPane();
		}
		Box baseBox=Box.createVerticalBox();
		
		baseBox.add(Box.createVerticalStrut(8));
		JLabel switchjb=new JLabel("Switch ("+switchPane.switchPane.length()+")");
		switchjb.setFont(new Font("Courier",Font.BOLD,20));
		baseBox.add(switchjb);
		
		JScrollPane scrollpaneswitch=new JScrollPane();
		JTable tableswitch=switchPane.getTable(switchPane.switchPane);
		scrollpaneswitch.setViewportView(tableswitch);
		scrollpaneswitch.setLocation(20,300);
		baseBox.add(scrollpaneswitch);
		baseBox.add(Box.createVerticalStrut(8));
		
		baseBox.add(Box.createVerticalStrut(8));
		JLabel hostjb=new JLabel("Host ("+hostPane.hostPane.length()+")");
		hostjb.setFont(new Font("Courier",Font.BOLD,20));
		baseBox.add(hostjb);
		
		baseBox.add(Box.createVerticalStrut(8));
		JScrollPane scrollpanehost=new JScrollPane();
		JTable tablehost=hostPane.getTable(hostPane.hostPane);
		scrollpanehost.setViewportView(tablehost);
		scrollpanehost.setLocation(20,300);
		baseBox.add(scrollpanehost);
		baseBox.add(Box.createVerticalStrut(8));
		
		add(baseBox,BorderLayout.CENTER);
	}
}
