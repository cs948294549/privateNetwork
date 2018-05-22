package controllerOP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class manageNetwork {
	public static String createNetwork(String vlaninfo,String network) throws IOException,JSONException{
		String jsonResponse="";
		URL url=new URL("http://192.168.137.129:8080/networkService/v1.1/tenants/default/networks/"+network);
		URLConnection conn=url.openConnection();
		conn.setDoOutput(true);
		OutputStreamWriter wr=new OutputStreamWriter(conn.getOutputStream());
		System.out.println(vlaninfo);
		wr.write(vlaninfo);
		wr.flush();
		BufferedReader rd=new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while((line=rd.readLine())!=null){
			jsonResponse=jsonResponse.concat(line);
		}
		wr.close();
		rd.close();
		JSONObject json=new JSONObject(jsonResponse);
		System.out.println(jsonResponse);
		return json.getString("status");
	}
	public static String bindHost(String hostinfo,String network,String port) throws IOException,JSONException{
		String jsonResponse="";
		URL url=new URL("http://192.168.137.129:8080/networkService/v1.1/tenants/default/networks/"+network+"/ports/"+port+"/attachment");
		HttpURLConnection connection=null;
		connection=(HttpURLConnection)url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("X-HTTP-Method-Override", "PUT");
		connection.setDoOutput(true);
		OutputStreamWriter wr=new OutputStreamWriter(connection.getOutputStream());
		wr.write(hostinfo);
		wr.flush();
		
		BufferedReader rd=new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		while((line=rd.readLine())!=null){
			jsonResponse =jsonResponse.concat(line);
		}
		wr.close();
		rd.close();
		
		JSONObject json=new JSONObject(jsonResponse);
		System.out.println(json);
		return json.getString("status");
	}
	public static String rmNetwork(String network) throws IOException,JSONException{
		String jsonResponse="";
		URL url=new URL("http://192.168.137.129:8080/networkService/v1.1/tenants/default/networks/"+network);//ports/port1/attachment
		HttpURLConnection connection=null;
		connection=(HttpURLConnection)url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("X-HTTP-Method-Override", "DELETE");
		connection.setDoOutput(true);
		OutputStreamWriter wr=new OutputStreamWriter(connection.getOutputStream());
		wr.write("");
		wr.flush();
		
		BufferedReader rd=new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		while((line=rd.readLine())!=null){
			jsonResponse =jsonResponse.concat(line);
		}
		wr.close();
		rd.close();
		
		JSONObject json=new JSONObject(jsonResponse);
		System.out.println(json);
		return json.getString("status");
	}
	public static String rmHost(String network,String port) throws IOException,JSONException{
		String jsonResponse="";
		URL url=new URL("http://192.168.137.129:8080/networkService/v1.1/tenants/default/networks/"+network+"/ports/"+port+"/attachment");//ports/port1/attachment
		HttpURLConnection connection=null;
		connection=(HttpURLConnection)url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("X-HTTP-Method-Override", "DELETE");
		connection.setDoOutput(true);
		OutputStreamWriter wr=new OutputStreamWriter(connection.getOutputStream());
		wr.write("");
		wr.flush();
		
		BufferedReader rd=new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		while((line=rd.readLine())!=null){
			jsonResponse =jsonResponse.concat(line);
		}
		wr.close();
		rd.close();
		
		JSONObject json=new JSONObject(jsonResponse);
		System.out.println(json);
		return json.getString("status");
	}
	public static JSONArray getNetwork() throws IOException, JSONException{
		String str="";
		URL url=new URL("http://192.168.137.129:8080/networkService/v1.1/tenants/default/networks");
		str=str.concat(ConnectURL(url));
		JSONArray jsonNetwork=new JSONArray(str);
		return jsonNetwork;
	}
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
	public static void main(String argv[]) throws Exception{
		//createNetwork("{\"network\":{\"name\":\"vlan1\"}}","NetworkId=vlan");//\"gateway\":\"10.1.0.254\",
		//bindHost("{\"attachment\":{\"mac\":\"00:00:00:00:00:01\"}}","NetworkId1","1");//\"id\":\"NetworkId2\",
	    //rmNetwork("NetworkId1");
		//[{"guid":"NetworkId2","portMac":[],"gateway":null,"name":"vlan2"},{"guid":"NetworkId1","portMac":[{"port":"1","mac":"00:00:00:00:00:01"}],"gateway":null,"name":"vlan1"}]
		System.out.println(getNetwork());
		//rmHost("NetworkId1","2");
		
	}
}
