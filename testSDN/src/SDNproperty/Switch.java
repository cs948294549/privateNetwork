package SDNproperty;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import MainFrame.topoPane;
import controllerOP.getTopo;

public class Switch {
	public static String Dpid;
	public static String flow_count;
	public static String packet;
	public static String bytes;
	public static String connect_time;
	public static JSONArray getSwitch() throws JSONException, IOException{
		JSONArray switches=new JSONArray();
		JSONArray switchjson=new JSONArray();
		if(topoPane.nodes==null){
			topoPane.createNodes();
		}
		switches=topoPane.nodes;
		for(int i=0,j=0;i<switches.length();i++){
			if(switches.getJSONObject(i).getString("type").equals("switch")){
				JSONObject Switch=new JSONObject();
				Switch.put("ip", switches.getJSONObject(i).getString("ip"));
				Switch.put("time", switches.getJSONObject(i).getString("time"));
				Switch.put("dpid",switches.getJSONObject(i).getString("dpid"));
				Switch.put("name",topoPane.nodeName.getString(switches.getJSONObject(i).getString("dpid")));
				switchjson.put(j,Switch);
				j++;
			}
		}
		System.out.println("交换机节点"+switchjson);
		return switchjson;
	}
}
