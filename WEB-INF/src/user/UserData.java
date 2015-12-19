package user;

public class UserData {
    String username;
    String password;
    String server;
    String port;
    public void setUsername( String value ){
        username = value;
    }
    public void setPassword( String value ){
        password = value;
    }
    public void setServer( String value ){
        server = value;
    }
    public void setPort( String value ){
        port = value;
    }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getServer() { return server; }
    public String getPort() { return port; }
}