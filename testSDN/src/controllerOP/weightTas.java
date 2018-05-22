package controllerOP;
import org.json.JSONException;
import org.json.JSONObject;

import SDNproperty.Node;
public class weightTas {
	public static JSONObject nodes;
	public static JSONObject write;
	public weightTas() throws JSONException{
		nodes=new JSONObject();
		write=new JSONObject();
		write.put("00:00:00:00:00:00:00:02", "1");
		write.put("00:00:00:00:00:00:00:03", "5");
		nodes.put("00:00:00:00:00:00:00:01", write);
		
		write=new JSONObject();
		write.put("00:00:00:00:00:00:00:01", "1");
		write.put("00:00:00:00:00:00:00:04", "2");
		nodes.put("00:00:00:00:00:00:00:02", write);
		
		write=new JSONObject();
		write.put("00:00:00:00:00:00:00:01", "1");
		write.put("00:00:00:00:00:00:00:04", "2");
		nodes.put("00:00:00:00:00:00:00:02", write);
		
		write=new JSONObject();
		write.put("00:00:00:00:00:00:00:01", "5");
		write.put("00:00:00:00:00:00:00:04", "5");
		nodes.put("00:00:00:00:00:00:00:03", write);
		
		write=new JSONObject();
		write.put("00:00:00:00:00:00:00:02", "2");
		write.put("00:00:00:00:00:00:00:03", "5");
		nodes.put("00:00:00:00:00:00:00:04", write);
	}
	public static int getweight(String srcDpid,String dstDpid) throws JSONException{
		weightTas t=new weightTas();
		int w = 0;
		try {
			w =Integer.parseInt(t.nodes.getJSONObject(srcDpid).getString(dstDpid));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("weight is not exsit!");
		}
		return w;
	}
}
