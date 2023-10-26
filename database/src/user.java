public class user 
{
		protected String password;
	 	protected String username;
	    protected String role;
	    
	 
	    //constructors
	    public user() {
	    }
	 
	    public user(String email) 
	    {
	        this.username = username;
	    }
	    
	    public user(String username, String password, String role) {
	    this.username = username;
    	this.password = password;
    	this.role = role;
	    }
	 
	
	    public user(String password, String role) 
	    {
	    	this.password = password;
	    	this.role = role;
	    }
	    
	   //getter and setter methods
	    public String getUsername() {
	        return username;
	    }
	    public void setUsername(String username) {
	        this.username = username;
	    }
	    
	    public String getPassword() {
	        return password;
	    }
	    public void setPassword(String password) {
	        this.password = password;
	    }
	  
	    public String getRole() {
	    	return role;
	    }
	    public void setRole(String role) {
	    	this.role = role;
	    }
	   
	}