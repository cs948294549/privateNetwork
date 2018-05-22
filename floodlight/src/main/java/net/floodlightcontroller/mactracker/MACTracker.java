package net.floodlightcontroller.mactracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.List;

import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.OFPacketIn;
import org.projectfloodlight.openflow.protocol.OFPortDesc;
import org.projectfloodlight.openflow.protocol.OFType;
import org.projectfloodlight.openflow.types.DatapathId;
import org.projectfloodlight.openflow.util.HexString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IFloodlightProviderService;
import net.floodlightcontroller.core.IOFMessageListener;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.core.IOFSwitchListener;
import net.floodlightcontroller.core.PortChangeType;
import net.floodlightcontroller.core.IListener.Command;
import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.IFloodlightModule;
import net.floodlightcontroller.core.module.IFloodlightService;
import net.floodlightcontroller.packet.Ethernet;

public class MACTracker implements IOFMessageListener, IFloodlightModule ,IOFSwitchListener {

	protected IFloodlightProviderService floodlightProvider;
	protected Set<Long> macAddresses;
	protected static Logger logger;
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "MACTracker";
	}

	@Override
	public boolean isCallbackOrderingPrereq(OFType type, String name) {
		// TODO Auto-generated method stub
		return (type.equals(OFType.PACKET_IN) && ((name.equals("linkdiscovery") || (name.equals("devicemanager")))));
		//return (type.equals(OFType.PACKET_IN) && name.equals("forwarding"));
	}

	@Override
	public boolean isCallbackOrderingPostreq(OFType type, String name) {
		// TODO Auto-generated method stub
		return (type.equals(OFType.PACKET_IN) && name.equals("forwarding"));
		//return (type.equals(OFType.PACKET_IN) &&(name.equals("linkdiscovery") || (name.equals("devicemanager"))));
	}

	@Override
	public void switchAdded(DatapathId switchId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void switchRemoved(DatapathId switchId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void switchActivated(DatapathId switchId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void switchPortChanged(DatapathId switchId, OFPortDesc port, PortChangeType type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void switchChanged(DatapathId switchId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Class<? extends IFloodlightService>> getModuleServices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Class<? extends IFloodlightService>, IFloodlightService> getServiceImpls() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Class<? extends IFloodlightService>> getModuleDependencies() {
		// TODO Auto-generated method stub
		Collection<Class<? extends IFloodlightService>> l=new ArrayList<Class<? extends IFloodlightService>>();
		l.add(IFloodlightProviderService.class);
		return l;
	}

	@Override
	public void init(FloodlightModuleContext context) throws FloodlightModuleException {
		// TODO Auto-generated method stub
		floodlightProvider=context.getServiceImpl(IFloodlightProviderService.class);
		macAddresses=new ConcurrentSkipListSet<Long>();
		logger=LoggerFactory.getLogger(MACTracker.class);
	}

	@Override
	public void startUp(FloodlightModuleContext context) throws FloodlightModuleException {
		// TODO Auto-generated method stub
		floodlightProvider.addOFMessageListener(OFType.PACKET_IN, this);
	}

	@Override
	public Command receive(IOFSwitch sw, OFMessage msg,
			FloodlightContext cntx) {
		// TODO Auto-generated method stub
		Ethernet eth=IFloodlightProviderService.bcStore.get(cntx,IFloodlightProviderService.CONTEXT_PI_PAYLOAD);
		Long sourceMAC=Ethernet.toLong(eth.getSourceMACAddress().getBytes());
		if(!macAddresses.contains(sourceMAC)){
			macAddresses.add(sourceMAC);
			System.out.println("llllllllllllllllllllllllllllllllllllllllllllllllllll");
			logger.info("MAC Address:{} seen on switch :{}",HexString.toHexString(sourceMAC),sw.getId());
		}
		switch (msg.getType()) {
		case PACKET_IN:
			return processPacketIn(sw, (OFPacketIn)msg, cntx);
		default:break;
		}
		return Command.CONTINUE;
	}

	private Command processPacketIn(IOFSwitch sw, OFPacketIn msg,
			FloodlightContext cntx) {
		// TODO Auto-generated method stub
		Ethernet eth=IFloodlightProviderService.bcStore.get(cntx,IFloodlightProviderService.CONTEXT_PI_PAYLOAD);
		Long sourceMAC=Ethernet.toLong(eth.getSourceMACAddress().getBytes());
		String srcmacaddress=HexString.toHexString(sourceMAC);
		//��ȡ�ĵ�ַ��16λ��Ҫ�ü���4λ
		char[] p=new char[17];
		srcmacaddress.getChars(6, srcmacaddress.length(),p, 0);
		srcmacaddress=new String(p);
		System.out.println("srcmac:"+srcmacaddress);
		
		Long dstMAC=Ethernet.toLong(eth.getDestinationMACAddress().getBytes());
		String dstmacaddress=HexString.toHexString(dstMAC);
		p=new char[17];
		dstmacaddress.getChars(6, dstmacaddress.length(),p, 0);
		dstmacaddress=new String(p);
		System.out.println("dstmac:"+dstmacaddress);
		
		Command ret = Command.CONTINUE;
		if((!dstmacaddress.substring(0,8).equals("33:33:ff")&&!srcmacaddress.substring(0,8).equals("33:33:ff"))&&(!srcmacaddress.equals("ff:ff:ff:ff:ff:ff")&&!dstmacaddress.equals("ff:ff:ff:ff:ff:ff"))){
		Connection conn=null;
		try{
			if((conn=connect())!=null){
				// statement����ִ��SQL���
	            Statement statement = conn.createStatement();

	            // Ҫִ�е�SQL���
	            String sql = "select * from portal where mac='"+srcmacaddress+"'";
	            String sql1= "select * from portal where mac='"+dstmacaddress+"'";
	            System.out.println(sql);
	            System.out.println(sql1);
	            // �����
	            ResultSet rs = statement.executeQuery(sql);
	            boolean rs_bool=false;
	            //String rs_vlan="";
	            List<String> strList = new ArrayList<String>();
	            while(rs.next()){
	            	rs_bool=true;
	            	strList.add(rs.getString("vlan"));
	            		}
	            System.out.println("search1:"+strList.toString());
	            rs.close();
	            
	            ResultSet rs1 = statement.executeQuery(sql1);
	            boolean rs1_bool=false;
	            //String rs1_vlan="";
	            List<String> strList1 = new ArrayList<String>();
	            while(rs1.next()){
	            	rs1_bool=true;
	            	strList1.add(rs1.getString("vlan"));
	            	}
	            System.out.println("search2:"+strList1.toString());
	            rs1.close();
	            System.out.println(rs_bool);
	            System.out.println(rs1_bool);
	           if(rs_bool&&rs1_bool) {
	        	   strList1.retainAll(strList);
		        	   System.out.println(strList1);
		        	   if(!strList1.isEmpty()){
		        	    ret=Command.CONTINUE;
		        	    }
		        	   else{
		        		   ret=Command.STOP;
		        	   }
		            }
	           else if(dstmacaddress.equals("00:00:00:00:00:05")||srcmacaddress.equals("00:00:00:00:00:05")){
	        	   		ret=Command.CONTINUE;
	        	   }
	           else{
	        	   ret=Command.STOP;
	           }
	           conn.close();
			}
			}catch(Exception e){e.printStackTrace();}
		}else {
			ret=Command.CONTINUE;
		}
		return ret;
	}
	private Connection connect(){
		 // ����������
		String driver = "com.mysql.jdbc.Driver";
		
		// URLָ��Ҫ���ʵ����ݿ���scutcs
		String url = "jdbc:mysql://127.0.0.1:3306/test";
		
		// MySQL����ʱ���û���
		String user = "root";
		
		 // MySQL����ʱ������
		String password = "root";
		Connection conn=null;
		 try { 
	            // ������������
	            Class.forName(driver);
	            // �������ݿ�
	            conn = DriverManager.getConnection(url, user, password);
		 }catch(Exception e){e.printStackTrace();}
		 return conn;
	}
	
}
