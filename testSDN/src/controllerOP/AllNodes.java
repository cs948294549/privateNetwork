package controllerOP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import SDNproperty.Node;
import MainFrame.topoPane;

public class AllNodes {
	 /* ��ʱ����·���ڵ��ջ */  
    public static Stack<Node> stack = new Stack<Node>();  
    /* �洢·���ļ��� */  
    public static ArrayList<Object[]> sers = new ArrayList<Object[]>();  
  
    /* �жϽڵ��Ƿ���ջ�� */  
    public static boolean isNodeInStack(Node node)  
    {  
        Iterator<Node> it = stack.iterator();  
        while (it.hasNext()) {  
            Node node1 = (Node) it.next();  
            if (node == node1)  
                return true;  
        }  
        return false;  
    }  
  
    /* ��ʱջ�еĽڵ����һ������·����ת������ӡ��� */  
    public static void showAndSavePath()  
    {  
        Object[] o = stack.toArray();
        for (int i = 0; i < o.length; i++) {  
            Node nNode = (Node) o[i];  
              
            if(i < (o.length - 1))  
                System.out.print(nNode.getName() + "->");  
            else  
                System.out.print(nNode.getName());  
        }  
        sers.add(o); /* ת�� */  
        System.out.println("\n");  
    }  
  
    /* 
     * Ѱ��·���ķ���  
     * cNode: ��ǰ����ʼ�ڵ�currentNode 
     * pNode: ��ǰ��ʼ�ڵ����һ�ڵ�previousNode 
     * sNode: �������ʼ�ڵ�startNode 
     * eNode: �յ�endNode 
     */  
    public static boolean getPaths(Node cNode, Node pNode, Node sNode, Node eNode) {  
        Node nNode = null;  
        /* ������������ж�˵�����ֻ�·��������˳�Ÿ�·������Ѱ·������false */  
        if (cNode != null && pNode != null && cNode == pNode)  
            return false;  
  
        if (cNode != null) {  
            int i = 0;  
            /* ��ʼ�ڵ���ջ */  
            stack.push(cNode);  
            /* �������ʼ�ڵ�����յ㣬˵���ҵ�һ��·�� */  
            if (cNode == eNode)  
            {  
                /* ת������ӡ�����·��������true */  
                showAndSavePath(); 
                //stack.pop();
                return true;  
            }  
            /* �������,����Ѱ· */  
            else  
            {  
                /*  
                 * ���뵱ǰ��ʼ�ڵ�cNode�����ӹ�ϵ�Ľڵ㼯�а�˳������õ�һ���ڵ� 
                 * ��Ϊ��һ�εݹ�Ѱ·ʱ����ʼ�ڵ�  
                 */  
                nNode = cNode.getRelationNodes().get(i);  
                while (nNode != null) {  
                    /* 
                     * ���nNode���������ʼ�ڵ����nNode����cNode����һ�ڵ����nNode�Ѿ���ջ�� ��  
                     * ˵��������· ��Ӧ�������뵱ǰ��ʼ�ڵ������ӹ�ϵ�Ľڵ㼯��Ѱ��nNode 
                     */  
                    if (pNode != null  
                            && (nNode == sNode || nNode == pNode || isNodeInStack(nNode))) {  
                        i++;  
                        if (i >= cNode.getRelationNodes().size())  
                            nNode = null;  
                        else  
                            nNode = cNode.getRelationNodes().get(i);  
                        continue;  
                    }  
                    /* ��nNodeΪ�µ���ʼ�ڵ㣬��ǰ��ʼ�ڵ�cNodeΪ��һ�ڵ㣬�ݹ����Ѱ·���� */  
                    if (getPaths(nNode, cNode, sNode, eNode))/* �ݹ���� */  
                    {  
                        /* ����ҵ�һ��·�����򵯳�ջ���ڵ� */  
                        stack.pop();  
                    }  
                    /* ��������cNode�����ӹ�ϵ�Ľڵ㼯�в���nNode */  
                    i++;  
                    if (i >= cNode.getRelationNodes().size())  
                        nNode = null;  
                    else  
                        nNode = cNode.getRelationNodes().get(i);  
                }  
                /*  
                 * ��������������cNode�����ӹ�ϵ�Ľڵ�� 
                 * ˵������cNodeΪ��ʼ�ڵ㵽�յ��·���Ѿ�ȫ���ҵ�  
                 */  
                stack.pop();  
                return false;  
            }  
        } else  
            return false;  
    }  
  
    public static void showpath(String srcdpid,String dstdpid) throws IOException, JSONException {  
        // ����ڵ��ϵ 
    	JSONArray links=new JSONArray();
    	JSONObject pathName=new JSONObject();
    	try {
			links=controllerAPI.getLinks();
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(pathName.toString().equals("{}")){
		topoPane.createNodes();
		pathName=topoPane.pathName;
    	}
//    	System.out.println(links);
//    	System.out.println(pathName);
    	pathName.length();
//        int nodeRalation[][] =
//        {  
//            {1},      //0  
//            {0,5,2,3},//1  
//            {1,4},    //2  
//            {1,4},    //3  
//            {2,3,5},  //4  
//            {1,4}     //5  
//        };
        int nodeRalation[][] =new int[pathName.length()/2][];
        for(int j=0;j<nodeRalation.length;j++){ 
        	JSONArray listjson=new JSONArray();
	        for(int k=0,i=0;i<links.length();i++){
	        	String src=links.getJSONObject(i).getString("src-switch");
	        	String dst=links.getJSONObject(i).getString("dst-switch");
	        	if((int)pathName.get(src)==j){
	        		listjson.put(k,pathName.get(dst));
	        		k++;
	        	}
	        	if((int)pathName.get(dst)==j){
	        		listjson.put(k,pathName.get(src));
	        		k++;
	        	}
	        }
	        int[] list=new int[listjson.length()];
	        for(int i=0;i<listjson.length();i++){
	        	list[i]=listjson.getInt(i);
	        }
	        nodeRalation[j]=list;
        }
        // ����ڵ����� 
        
        Node[] node = new Node[nodeRalation.length];  
          
        for(int i=0;i<nodeRalation.length;i++)  
        {  
            node[i] = new Node();
            node[i].setName(pathName.getString(""+i));  
        }  
          
        /* ������ڵ�������Ľڵ㼯�� */  
        for(int i=0;i<nodeRalation.length;i++)  
        {  
            ArrayList<Node> List = new ArrayList<Node>();  
              
            for(int j=0;j<nodeRalation[i].length;j++)  
            {  
                List.add(node[nodeRalation[i][j]]);  
            }  
            node[i].setRelationNodes(List);  
            List = null;  //�ͷ��ڴ�  
        }  
  
        /* ��ʼ��������·�� */  
        System.out.println(getPaths(node[pathName.getInt(srcdpid)], null, node[pathName.getInt(srcdpid)], node[pathName.getInt(dstdpid)]));  //"00:00:00:00:00:00:00:04"
    }  
    public static void main(String argv[]){
    	try {
			showpath("00:00:00:00:00:00:00:01","00:00:00:00:00:00:00:01");
			for(int i=0;i<sers.size();i++){
				Object[] o=sers.get(i);
				for(int j=0;j<o.length;j++){
					Node nNode = (Node) o[j];  
					if(j<(o.length-1)){
						System.out.print(nNode.getName()+"->");
					}else{
						System.out.print(nNode.getName());
					}
				}
				System.out.println();
			}
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
