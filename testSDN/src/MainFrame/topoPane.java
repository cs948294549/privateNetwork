package MainFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import controllerOP.getTopo;
import twaver.Element;
import twaver.Generator;
import twaver.Group;
import twaver.Link;
import twaver.Node;
import twaver.TDataBox;
import twaver.TWaverUtil;
import twaver.network.TNetwork;
import twaver.network.background.ColorBackground;

public class topoPane extends JPanel{
	public static JSONArray nodes;
	public static JSONObject nodeName=new JSONObject();
	public static JSONObject pathName=new JSONObject();
	public static void createNodes() throws IOException, JSONException{
		nodes=getTopo.getNode();
		for(int i=0,j=0,k=0;i<nodes.length();i++){
			if(nodes.getJSONObject(i).getString("type").equals("host")){
				nodeName.put(nodes.getJSONObject(i).getString("mac"), "host"+(j+1));
				nodeName.put("host"+(j+1),nodes.getJSONObject(i).getString("ipv6"));
				j++;
			}
			else{
				nodeName.put(nodes.getJSONObject(i).getString("dpid"), "switch"+(k+1));
				nodeName.put("switch"+(k+1),nodes.getJSONObject(i).getString("dpid"));
				pathName.put(nodes.getJSONObject(i).getString("dpid"), k);
				pathName.put(""+k, nodes.getJSONObject(i).getString("dpid"));
				k++;
			}
		}
		System.out.println("node dict:"+nodeName);
		System.out.println("path dict:"+pathName);
	}
	public topoPane() throws IOException, JSONException{
		this.setLayout(new BorderLayout());
		TDataBox box = new TDataBox();
		registerImage();
		if(nodes.isNull(0)){
			createNodes();
		}
		for(int i=0;i<nodes.length();i++){
			if(nodes.getJSONObject(i).getString("type").equals("host")){
				double x = 30+Math.random()*400;
				double y = 30+Math.random()*450;
				Node node=new Node(nodes.getJSONObject(i).getString("mac"));
				//node.setName("<html><center>"+nodes.getJSONObject(i).getString("ip")+"<br>"+nodes.getJSONObject(i).getString("mac")+"<br>"+nodeName.getString(nodes.getJSONObject(i).getString("mac"))+"</center></html>");
				node.setName("<html><center>"+nodes.getJSONObject(i).getString("ipv6")+"<br>");
				node.setImage("host");
				node.setLocation(x, y);
				box.addElement(node);
			}
			if(nodes.getJSONObject(i).getString("type").equals("switch")){
				double x = 100+Math.random()*500;
				double y = 100+Math.random()*450;
				Node node=new Node(nodes.getJSONObject(i).getString("dpid"));
				//node.setName("<html><center>"+nodes.getJSONObject(i).getString("dpid")+"<br>"+nodes.getJSONObject(i).getString("ip")+"<br>"+nodeName.getString(nodes.getJSONObject(i).getString("dpid"))+"</center></html>");
				node.setName("<html><center>"+nodes.getJSONObject(i).getString("dpid")+"<br>");
				node.setImage("switch");
				node.setLocation(x, y);
				box.addElement(node);
			}
		}
		JSONArray links=getTopo.getLink();
		for(int i=0;i<links.length();i++){
			if(links.getJSONObject(i).getString("type").equals("host")){
			Link link = new Link((Node)box.getElementByID(links.getJSONObject(i).getString("host")), (Node)box.getElementByID(links.getJSONObject(i).getString("switch")));
			//link.setName(nodeName.getString(links.getJSONObject(i).getString("host"))+"--"+nodeName.getString(links.getJSONObject(i).getString("switch"))+"port:"+links.getJSONObject(i).getString("port"));
			
			link.putLinkFlowing(true);
			link.putLinkFlowingColor(Color.green);//������ɫ
			link.putLinkOutlineColor(Color.black);//��Χ��ɫ
			link.putLinkColor(Color.WHITE);//������ɫ
			box.addElement(link);
			}
			if(links.getJSONObject(i).getString("type").equals("switch")){
				Link link = new Link((Node)box.getElementByID(links.getJSONObject(i).getString("lswitch")), (Node)box.getElementByID(links.getJSONObject(i).getString("rswitch")));
				//link.setName(nodeName.getString(links.getJSONObject(i).getString("lswitch"))+"port:"+links.getJSONObject(i).getString("lport")+"--"+nodeName.getString(links.getJSONObject(i).getString("rswitch"))+"port:"+links.getJSONObject(i).getString("rport"));
				
				link.putLinkFlowing(true);
				link.putLinkFlowingColor(Color.green);//������ɫ
				link.putLinkOutlineColor(Color.black);//��Χ��ɫ
				link.putLinkColor(Color.WHITE);//������ɫ
				box.addElement(link);
			}
		}
		final TNetwork network = new TNetwork(box);
		network.setBackground(new ColorBackground(Color.white));
		
		network.getCanvas().addMouseListener(new MouseAdapter() {
			  public void mouseClicked(MouseEvent e) {
			  if (e.getClickCount() == 2) {
			    Element element = network.getElementPhysicalAt(e.getPoint());
			      if (element == null) {
			        //JOptionPane.showMessageDialog(network, "double click on nothing.");
			      } else {
			        //JOptionPane.showMessageDialog(network,element.getName() + " is double clicked.");
			    	  if(element.toString().equals("twaver.Link")){
			    	   ((Link) element).putLinkFlowing(true);
			    	   ((Link) element).putLinkFlowingColor(Color.RED);//������ɫ
			    	   ((Link) element).putLinkOutlineColor(Color.red);//��Χ��ɫ
			    	   ((Link) element).putLinkColor(Color.WHITE);//������ɫ
			    	  }
			    	  if(element.toString().equals("twaver.Node")){
			    		  System.out.println(element);
			    	  }
			      }
			    }
			  }
		}
			  );
		network.doLayout(4, true, new Runnable() {
			  public void run() {
			    //JOptionPane.showMessageDialog(network, "layout finished");
			  }
			}, new Generator() {
			  public Object generate(Object object) {
			    Element element = (Element) object;
			    if (element instanceof Group) {
			      if (((Group) element).isExpand()) {
			        Rectangle bounds = network.getElementBounds(element);
			        bounds.grow(-bounds.width / 4, -bounds.height / 4);
			        return new Dimension(bounds.width, bounds.height);
			      }
			    }
			    return new Dimension(element.getWidth() + 20, 
			                        element.getHeight() + 20);
			  }
			});
		this.add(network,BorderLayout.CENTER);
	}
	private static void registerImage(){
		//TWaverʹ��ͼƬĬ����ע���ʹ�õķ�񣬵ڶ���Ԫ��Ϊ��Ⱦ��ɫ
		    ImageIcon hosticon = TWaverUtil.getImageIcon(topoPane.class.getResource("/host.png").toString(), Color.CYAN);
		    TWaverUtil.registerImageIcon("host",hosticon); 
		    ImageIcon swicon = TWaverUtil.getImageIcon(topoPane.class.getResource("/switch.png").toString());
		    TWaverUtil.registerImageIcon("switch",swicon); 
		  }
}
