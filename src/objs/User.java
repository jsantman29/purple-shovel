package objs;

// T_userCredentials table
import java.util.Date;

public class User {
	
	private int id;
	private String username;
	private boolean locked;
	private boolean admin;
	private boolean csr;
	private Date createdDate;
	private Date lastLogin;
	private String password;
	
	public int getId(){ return id; }
	public void setId(int id){ this.id = id; }
	
	public boolean isLocked(){ return locked; }
	public void setLocked(boolean locked){ this.locked = locked;}
	
	public boolean isAdmin(){return admin;}
	public void setAdmin(boolean admin){this.admin = admin;}
	
	public boolean isCSR(){return csr;}
	public void setCSR(boolean csr){this.csr = csr;}
	
	public String getUsername(){ return username; }
	public void setUsername(String username){this.username = username;}
	
	public String getPassword(){return password;}
	public void setPassword(String password){this.password = password;}
	
}
