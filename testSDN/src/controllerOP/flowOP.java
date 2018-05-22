package controllerOP;
import java.io.*;
import java.net.*;
import org.json.JSONException;
import org.json.JSONObject;

public class flowOP {
	public static String push(String flow) throws IOException,JSONException{
		String warning="warning!";
		String respond="";
		URL url=new URL("http://"+Config.ControllerIP+":8080/wm/staticflowpusher/json");
		URLConnection conn=url.openConnection();
		conn.setDoOutput(true);
		OutputStreamWriter wr=new OutputStreamWriter(conn.getOutputStream());
		wr.write(flow);
		wr.flush();
		
		BufferedReader rd=new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while((line=rd.readLine())!=null){
			respond=respond.concat(line);
		}
		wr.close();
		rd.close();
		JSONObject jsonRespond=new JSONObject(respond);
		if(jsonRespond.getString("status").equals("Entry pushed")||jsonRespond.getString("status").equals(warning)){
			return "successfully pushed";
		}
		else{
			return jsonRespond.getString("status");
		}
	}
	public static String remove(String name) throws IOException,JSONException{
		String jsonRespond="";
		String str="{\"name\":\""+name+"\"}";
		URL url=new URL("http://"+Config.ControllerIP+":8080/wm/staticflowpusher/json");
		HttpURLConnection conn=(HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("X-HTTP-Method-Override", "DELETE");
		conn.setDoOutput(true);
		OutputStreamWriter wr=new OutputStreamWriter(conn.getOutputStream());
		wr.write(str);
		wr.flush();
		
		BufferedReader rd=new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line="";
		while((line=rd.readLine())!=null){
			jsonRespond=jsonRespond.concat(line);
		}
		return jsonRespond;
	}
	public static String removeall() throws IOException,JSONException{
		String jsonRespond="";
		URL url=new URL("http://"+Config.ControllerIP+":8080/wm/staticflowpusher/clear/all/json");
		URLConnection conn=url.openConnection();
		conn.connect();
		BufferedReader rd=new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line="";
		while((line=rd.readLine())!=null){
			jsonRespond=jsonRespond.concat(line);
		}
		JSONObject js=new JSONObject(jsonRespond);		
		return js.getString("status");
	}
	public static String removeall(String sw) throws IOException,JSONException{
		String jsonRespond="";
		URL url=new URL("http://"+Config.ControllerIP+":8080/wm/staticflowpusher/clear/"+sw+"/json");
		URLConnection conn=url.openConnection();
		conn.connect();
		BufferedReader rd=new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line="";
		while((line=rd.readLine())!=null){
			jsonRespond=jsonRespond.concat(line);
		}
		JSONObject js=new JSONObject(jsonRespond);		
		return js.getString("status");
	}
	public static void main(String argv[]) throws IOException, JSONException{
		removeall();
	}
}
