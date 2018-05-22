package controllerOP;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class getTopo {
	public static JSONArray getNode() throws IOException, JSONException{
		JSONObject node;
		JSONArray nodeall=new JSONArray();
		JSONArray nodes=controllerAPI.getDevice();
		int nodelength=0;
		for(int i=0;i<nodes.length();i++){
			if(!nodes.getJSONObject(i).getJSONArray("attachmentPoint").toString().equals("[]")){
			node=new JSONObject();
			node.put("mac", nodes.getJSONObject(i).getJSONArray("mac").getString(0));
			//node.put("ip",nodes.getJSONObject(i).getJSONArray("ipv4").getString(0));
			node.put("ipv6",nodes.getJSONObject(i).getJSONArray("ipv6").getString(0));
			node.put("attachPoint",nodes.getJSONObject(i).getJSONArray("attachmentPoint").getJSONObject(0).getString("switchDPID"));
			node.put("port",nodes.getJSONObject(i).getJSONArray("attachmentPoint").getJSONObject(0).getString("port"));
			node.put("time",nodes.getJSONObject(i).getString("lastSeen"));
			
			node.put("type", "host");
			
			nodeall.put(nodelength, node);
			nodelength++;
			}
		}
		//nodelength=nodeall.length();
		nodes=controllerAPI.getswitches();
		for(int i=0;i<nodes.length();i++){
			node=new JSONObject();
			node.put("ip", nodes.getJSONObject(i).getString("inetAddress"));
			node.put("time", nodes.getJSONObject(i).getString("connectedSince"));
			node.put("dpid", nodes.getJSONObject(i).getString("switchDPID"));
			node.put("type", "switch");

			nodeall.put(nodelength, node);
			nodelength++;
		}
		System.out.println("总节点"+nodeall);
		return nodeall;
	}
	public static JSONArray getLink() throws IOException, JSONException{
		JSONObject link;
		JSONArray linkall=new JSONArray();
		JSONArray links=controllerAPI.getDevice();
		int linklength=0;
		for(int i=0;i<links.length();i++){
			if(!links.getJSONObject(i).getJSONArray("attachmentPoint").toString().equals("[]")){
			link=new JSONObject();
			link.put("switch",links.getJSONObject(i).getJSONArray("attachmentPoint").getJSONObject(0).getString("switchDPID"));
			link.put("port",links.getJSONObject(i).getJSONArray("attachmentPoint").getJSONObject(0).getString("port"));
			link.put("host",links.getJSONObject(i).getJSONArray("mac").getString(0));
			
			link.put("type", "host");
			
			linkall.put(linklength, link);
			linklength++;
			}
		}
		links=controllerAPI.getLinks();
		for(int i=0;i<links.length();i++){
			link=new JSONObject();
			link.put("lswitch", links.getJSONObject(i).getString("src-switch"));
			link.put("lport", links.getJSONObject(i).getString("src-port"));
			link.put("rswitch", links.getJSONObject(i).getString("dst-switch"));
			link.put("rport", links.getJSONObject(i).getString("dst-port"));
			
			link.put("type", "switch");
			
			linkall.put(linklength, link);
			linklength++;
		}
		System.out.println("总链路"+linkall);
		return linkall;
	}
	public static void main(String argv[]){
		try {
			System.out.println(getNode());
			controllerAPI.getLinks();
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
