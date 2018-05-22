package SDNproperty;
public class Flow {
	public String  name, priority, idletime, hardtime,  switchid,
	active;
	public String in_port,eth_src,eth_dst,eth_type,ipv4_src,ipv4_dst;
	public String actions;
	public String serialize() {
		String serial = "{";
		if (this.name != null&&!this.name.equals("")) {
			if (serial.length() > 15)
				serial = serial.concat(", ");
			serial = serial.concat("\"name\":\"" + this.name + "\"");
		}
		if (this.priority!= null&&!priority.equals("")) {
			if (serial.length() > 15)
				serial = serial.concat(", ");
			serial = serial.concat("\"priority\":\"" + this.priority + "\"");
		}
		if (this.hardtime!= null) {
			if (serial.length() > 15)
			serial = serial.concat(", ");
			serial = serial.concat("\"hard_timeout\":\"" + hardtime + "\"");
		}
		if (this.switchid!= null) {
			if (serial.length() > 15)
			serial = serial.concat(", ");
			serial = serial.concat("\"switch\":\"" +switchid + "\"");
		}
		
		
		if (this.eth_src!= null&&!eth_src.equals("")) {
			if (serial.length() > 15)
			serial = serial.concat(", ");
			serial = serial.concat("\"eth_src\":\"" + eth_src + "\"");
			
		}
		if (this.eth_dst != null&&!eth_dst.equals("")) {
			if (serial.length() > 15)
				serial = serial.concat(", ");
				serial = serial.concat("\"eth_dst\":\"" + eth_dst + "\"");
			
		}
		if (this.eth_type != null&&!eth_type.equals("")) {
			if (serial.length() > 15)
				serial = serial.concat(", ");
				serial = serial.concat("\"eth_type\":\"" + eth_type  + "\"");
			
		}
		
		if (this.in_port!= null&&!in_port.equals("")) {
			if (serial.length() > 15)
				serial = serial.concat(", ");
				serial = serial.concat("\"in_port\":\"" + in_port + "\"");
			
		}
		if (this.ipv4_src != null&&!this.ipv4_src.equals("")) {
			if (serial.length() > 15)
				serial = serial.concat(", ");
				serial = serial.concat("\"ipv4_src\":\"" + ipv4_src + "\"");
			
		}
		if (this.ipv4_dst != null&&!this.ipv4_dst.equals("")) {
			if (serial.length() > 15)
				serial = serial.concat(", ");
				serial = serial.concat("\"ipv4_dst\":\"" + ipv4_dst  + "\"");
			
		}
		if (this.actions != null&&!this.actions.equals("")) {
			if (serial.length() > 15)
				serial = serial.concat(", ");
				serial = serial.concat("\"actions\":\"" + actions + "\"");
		}
		if (this.active!= null&&!this.active.equals("")) {
			if (serial.length() > 15)
				serial = serial.concat(", ");
				serial = serial.concat("\"active\":\"" + active + "\"");
		}
		serial = serial.concat("}");
		System.out.println(serial);
		return serial;
	}
}
