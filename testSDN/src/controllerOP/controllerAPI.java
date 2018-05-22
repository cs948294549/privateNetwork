package controllerOP;
import java.io.*;
import java.net.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class controllerAPI {
	private static String ConnectURL(URL url) throws IOException{
		String json="";
		URLConnection conn=url.openConnection();
		BufferedReader rd=new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line="";
		while((line=rd.readLine())!=null){
			json=json.concat(line);
		}
		return json;
	}
	public static JSONArray getDevice() throws IOException, JSONException{
		String str="";
		URL url=new URL("http://"+Config.ControllerIP+":8080/wm/device/");
		str=str.concat(ConnectURL(url));
		//str="[{\"entityClass\":\"DefaultEntityClass\",\"mac\":[\"76:cf:05:72:9a:8b\"],\"ipv4\":[\"10.0.0.1\"],\"ipv6\":[],\"vlan\":[\"0x0\"],\"attachmentPoint\":[{\"switchDPID\":\"00:00:00:00:00:00:00:01\",\"port\":1,\"errorStatus\":null}],\"lastSeen\":1460957754237},{\"entityClass\":\"DefaultEntityClass\",\"mac\":[\"36:90:a0:ec:ef:80\"],\"ipv4\":[\"10.0.0.3\"],\"ipv6\":[],\"vlan\":[\"0x0\"],\"attachmentPoint\":[{\"switchDPID\":\"00:00:00:00:00:00:00:02\",\"port\":2,\"errorStatus\":null}],\"lastSeen\":1460957754252},{\"entityClass\":\"DefaultEntityClass\",\"mac\":[\"66:07:a9:66:f0:e8\"],\"ipv4\":[\"10.0.0.2\"],\"ipv6\":[],\"vlan\":[\"0x0\"],\"attachmentPoint\":[{\"switchDPID\":\"00:00:00:00:00:00:00:01\",\"port\":2,\"errorStatus\":null}],\"lastSeen\":1460957754252},{\"entityClass\":\"DefaultEntityClass\",\"mac\":[\"12:c6:7d:8d:aa:42\"],\"ipv4\":[\"10.0.0.4\"],\"ipv6\":[],\"vlan\":[\"0x0\"],\"attachmentPoint\":[{\"switchDPID\":\"00:00:00:00:00:00:00:02\",\"port\":3,\"errorStatus\":null}],\"lastSeen\":1460957749245}]";
		JSONArray jsonDevice=new JSONArray(str);
		return jsonDevice;
	}
	public static JSONArray getLinks() throws IOException, JSONException{
		String str="";
		URL url=new URL("http://"+Config.ControllerIP+":8080/wm/topology/links/json");
		str=str.concat(ConnectURL(url));
		//str="[{\"src-switch\":\"00:00:00:00:00:00:00:01\",\"src-port\":3,\"dst-switch\":\"00:00:00:00:00:00:00:02\",\"dst-port\":1,\"type\":\"internal\",\"direction\":\"bidirectional\"}]";
		//System.out.println(str);
		JSONArray jsonLink=new JSONArray(str);
		return jsonLink;
	}
	public static JSONArray getSwitchFeature(String sw)throws IOException, JSONException{
		String str="";
		URL url=new URL("http://"+Config.ControllerIP+":8080/wm/core/switch/all/port-desc/json");
		str=str.concat(ConnectURL(url));
		//str="{\"00:00:00:00:00:00:00:02\": {\"portDesc\": [{\"portNumber\": \"3\",\"hardwareAddress\": \"0e:d2:cf:ce:8f:9b\",\"name\":\"Switch2-eth3\",\"config\":\"0\",\"state\":\"0\",\"currentFeatures\":\"192\",\"advertisedFeatures\":\"0\",\"supportedFeatures\": \"0\",\"peerFeatures\": \"0\"},{\"portNumber\": \"1\",\"hardwareAddress\": \"6a:1a:9d:05:28:31\",\"name\":\"Switch2-eth1\",\"config\": \"0\",\"state\": \"0\",\"currentFeatures\":\"192\",\"advertisedFeatures\": \"0\",\"supportedFeatures\": \"0\",\"peerFeatures\":\"0\"}]},\"00:00:00:00:00:00:00:01\": {\"portDesc\":[]}}";
		JSONObject json=new JSONObject(str);
		JSONArray jsonSwitch=json.getJSONObject(sw).getJSONArray("portDesc");
		return jsonSwitch;
	}
	public static JSONArray getswitches() throws IOException, JSONException{
		String str="";
		URL url=new URL("http://"+Config.ControllerIP+":8080/wm/core/controller/switches/json");
		str=str.concat(ConnectURL(url));
		//str="[{\"inetAddress\":\"/192.168.137.130:55444\",\"connectedSince\":1461046070735,\"switchDPID\":\"00:00:00:00:00:00:00:02\"},{\"inetAddress\":\"/192.168.137.130:55445\",\"connectedSince\":1461046070725,\"switchDPID\":\"00:00:00:00:00:00:00:01\"}]";
		JSONArray jsonSwitches=new JSONArray(str);
		return jsonSwitches;
	}
	public static JSONArray getFlows(String sw) throws JSONException, IOException{
		String str="";
		URL url=new URL("http://"+Config.ControllerIP+":8080/wm/core/switch/all/flow/json");
		str=str.concat(ConnectURL(url));
		//str="{\"00:00:00:00:00:00:00:02\":{\"flows\":[]},\"00:00:00:00:00:00:00:01\":{\"flows\":[{\"version\":\"OF_10\",\"cookie\":\"9007199254740992\",\"tableId\":\"0x0\",\"packetCount\":\"14\",\"byteCount\":\"1372\",\"durationSeconds\":\"14\",\"priority\":\"1\",\"idleTimeoutSec\":\"5\",\"hardTimeoutSec\":\"0\",\"match\":{\"in_port\":\"1\",\"eth_src\":\"8a:e9:f0:67:7d:31\",\"eth_dst\":\"d2:d4:b5:ce:a7:dc\",\"eth_type\":\"0x0x800\",\"ipv4_src\":\"10.0.0.1\",\"ipv4_dst\":\"10.0.0.2\"},\"actions\":{\"actions\":\"output=2\"}},{\"version\":\"OF_10\",\"cookie\":\"9007199254740992\",\"tableId\":\"0x0\",\"packetCount\":\"14\",\"byteCount\":\"1372\",\"durationSeconds\":\"14\",\"priority\":\"1\",\"idleTimeoutSec\":\"5\",\"hardTimeoutSec\":\"0\",\"match\":{\"in_port\":\"2\",\"eth_src\":\"d2:d4:b5:ce:a7:dc\",\"eth_dst\":\"8a:e9:f0:67:7d:31\",\"eth_type\":\"0x0x800\",\"ipv4_src\":\"10.0.0.2\",\"ipv4_dst\":\"10.0.0.1\"},\"actions\":{\"actions\":\"output=1\"}}]}}";
		JSONObject allflows=new JSONObject(str);
		JSONArray jsonflows=allflows.getJSONObject(sw).getJSONArray("flows");
		return jsonflows;
	}
	
}
