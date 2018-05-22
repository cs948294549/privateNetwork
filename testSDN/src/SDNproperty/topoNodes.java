package SDNproperty;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import controllerOP.getTopo;
/**
 * Œ¥ π”√
 */
public class topoNodes {
	public class Node{
		private String name;
		private String dpid;
		Adj firstdge;
	}
	public class Adj{
		private int number;
		private String in_port;
		private String out_port;
		private int cost;
		Adj next;
	}
	public Node [] Nodes;
	public topoNodes() throws JSONException, IOException{
		/*JSONArray sw=Switch.getSwitch();
		JSONObject diction=new JSONObject();
		Nodes=new Node[sw.length()];
		for(int i=0;i<sw.length();i++){
			Nodes[i]=new Node();
			Nodes[i].firstdge=null;
			Nodes[i].name=sw.getJSONObject(i).getString("name");
			Nodes[i].dpid=sw.getJSONObject(i).getString("dpid");
			diction.put(sw.getJSONObject(i).getString("dpid"), i);
		}
		System.out.println(Nodes[1].dpid);
		System.out.println(diction);
		JSONArray links=getTopo.getLink();
		for(int i=0;i<links.length();i++){
			if(links.getJSONObject(i).getString("type").equals("switch")){
				Adj adj=new Adj();
				adj.in_port=links.getJSONObject(i).getString("rport");
				adj.out_port=links.getJSONObject(i).getString("lport");
				adj.number=diction.getInt(links.getJSONObject(i).getString("lswitch"));
				adj.cost=1;
				adj.next=Nodes[diction.getInt(links.getJSONObject(i).getString("rswitch"))].firstdge;
				Nodes[diction.getInt(links.getJSONObject(i).getString("rswitch"))].firstdge=adj;
				
				adj=new Adj();
				adj.in_port=links.getJSONObject(i).getString("lport");
				adj.out_port=links.getJSONObject(i).getString("rport");
				adj.number=diction.getInt(links.getJSONObject(i).getString("rswitch"));
				adj.cost=1;
				adj.next=Nodes[diction.getInt(links.getJSONObject(i).getString("lswitch"))].firstdge;
				Nodes[diction.getInt(links.getJSONObject(i).getString("lswitch"))].firstdge=adj;
			}
		}*/
		Nodes=new Node[6];
		Adj adj=new Adj();
		for(int i=0;i<6;i++){
			Nodes[i]=new Node();
			Nodes[i].dpid=""+i+"";
		}
		adj=new Adj();
		adj.number=1;
		adj.cost=1;
		adj.next=Nodes[0].firstdge;
		Nodes[0].firstdge=adj;
		
		adj=new Adj();
		adj.number=2;
		adj.cost=1;
		adj.next=Nodes[0].firstdge;
		Nodes[0].firstdge=adj;
		adj=new Adj();
		adj.number=2;
		adj.cost=3;
		adj.next=Nodes[1].firstdge;
		Nodes[1].firstdge=adj;
		adj=new Adj();
		adj.number=0;
		adj.cost=1;
		adj.next=Nodes[1].firstdge;
		Nodes[1].firstdge=adj;
		adj=new Adj();
		adj.number=5;
		adj.cost=1;
		adj.next=Nodes[1].firstdge;
		Nodes[1].firstdge=adj;
		adj=new Adj();
		adj.number=1;
		adj.cost=3;
		adj.next=Nodes[2].firstdge;
		Nodes[2].firstdge=adj;
		adj=new Adj();
		adj.number=0;
		adj.cost=1;
		adj.next=Nodes[2].firstdge;
		Nodes[2].firstdge=adj;
		adj=new Adj();
		adj.number=3;
		adj.cost=1;
		adj.next=Nodes[2].firstdge;
		Nodes[2].firstdge=adj;
		adj=new Adj();
		adj.number=4;
		adj.cost=3;
		adj.next=Nodes[2].firstdge;
		Nodes[2].firstdge=adj;
		adj=new Adj();
		adj.number=2;
		adj.cost=1;
		adj.next=Nodes[3].firstdge;
		Nodes[3].firstdge=adj;
		adj=new Adj();
		adj.number=2;
		adj.cost=3;
		adj.next=Nodes[4].firstdge;
		Nodes[4].firstdge=adj;
		adj=new Adj();
		adj.number=1;
		adj.cost=1;
		adj.next=Nodes[5].firstdge;
		Nodes[5].firstdge=adj;
		//*/
		for(int i=0;i<Nodes.length;i++){
			Adj t=new Adj();
			System.out.println(i+Nodes[i].dpid+Nodes[i].name);
			t=Nodes[i].firstdge;
			while(t!=null){
				t=t.next;
			}
		}
		dijkstra();
	}
	public void dijkstra(){
		Boolean[] s=new Boolean[Nodes.length];
		int []dist=new int[Nodes.length];
		int []prev=new int[Nodes.length];
		int num=1,k=num;
		for(int i=0;i<Nodes.length;i++){
			dist[i]=32767;
			s[i]=false;
			prev[i]=-1;
		}
		s[num]=true;
		k=num;
		dist[num]=0;
		prev[num]=num;
		for(int j=1;j<Nodes.length;j++){
		Adj t=new Adj();
		t=Nodes[k].firstdge;
		while(t!=null){
			if(!s[t.number]&&(dist[k]+t.cost)<dist[t.number]){
				dist[t.number]=dist[k]+t.cost;
				prev[t.number]=k;
			}
			t=t.next;
		}
		int m=32767;
		for(int i=0;i<Nodes.length;i++){
			if(!s[i]&&m>dist[i]){
				m=dist[i];
				k=i;
			}
		}
		s[k]=true;
		}
		for(int i=0;i<Nodes.length;i++){
			System.out.print(dist[i]);
		}
		System.out.println("");
		for(int i=0;i<Nodes.length;i++){
			System.out.print(prev[i]);
		}
		System.out.println("");
		System.out.println("1 to 4");
		int j=4;
		while(dist[j]!=0){
			System.out.print(prev[j]);
			j=prev[j];
		}
	}
	public static void main(String argv[]) throws JSONException, IOException{
		new topoNodes();
	}
}
