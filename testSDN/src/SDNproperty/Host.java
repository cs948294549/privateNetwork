package SDNproperty;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import MainFrame.topoPane;
import controllerOP.controllerAPI;
import controllerOP.getTopo;


public class Host {
	public static String host_ip;
	public static String host_mac;
	public static String host_port;
	public static String last_seen;
	public static JSONArray getHost() throws IOException, JSONException{
		JSONArray hostjson=new JSONArray();
		if(topoPane.nodes==null){
			topoPane.createNodes();
		}
		JSONArray hosts=topoPane.nodes;
		for(int i=0,j=0;i<hosts.length();i++){
			if(hosts.getJSONObject(i).getString("type").equals("host")){
				JSONObject host=new JSONObject();
				host.put("mac", hosts.getJSONObject(i).getString("mac"));
				host.put("ipv6", hosts.getJSONObject(i).getString("ipv6"));
				host.put("attachPoint", hosts.getJSONObject(i).getString("attachPoint"));
				host.put("port", hosts.getJSONObject(i).getString("port"));
				host.put("time", hosts.getJSONObject(i).getString("time"));
				host.put("name", topoPane.nodeName.getString(hosts.getJSONObject(i).getString("mac")));
				
				hostjson.put(j,host);
				j++;
			}
		}
		System.out.println("主机节点"+hostjson);
		return hostjson;
	}
}
