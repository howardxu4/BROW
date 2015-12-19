//******************************************************************************
// Package Declaration
//******************************************************************************
package user;
//******************************************************************************
// Import Specifications
//******************************************************************************	

import com.bmc.arsys.api.*;
import java.util.*;
import java.io.*;

/**
 *******************************************************************************
 * <B> Class Description: </B><p><pre>
 *
 * JavaAPI class is a ARServerUser wrapper class to hide the complexity on use
 * 1. simplfy the login with flexible ways
 * 2. provide the current state of class
 * 3. build the basic methods for common using
 * This class has implemented the static method for convenient using singleton pattern.
 *
 * </pre>
 *******************************************************************************
 * <B> Author: </B><p><pre>
 *
 *  Howard Xu
 *
 * </pre>
 *******************************************************************************
 * <B> Resources: </B><ul>
 *  All BMC ARSystem API documents are in:  
 *  <BMC ARSystem Install Dir>/arserver/api/doc/ardoc90_build001.jar
 * </ul>
 *******************************************************************************
 * <B> Notes: </B><ul>
 *  This is the global class for ARSystem API, for all other objects
 *  refer to ARForm, ARField, AREntry, ARQuery... classes  
 * </ul>
 *******************************************************************************
*/

public class JavaAPI extends ARServerUser {

    private static JavaAPI c_instance = null;
    private static Properties c_prop = null;
    private static String c_pfile = "config.properties";
    private static boolean c_debug = false;
    private static int c_state = -1;
    private static String c_message = "Uninitialized";
    
	/**
	 * setPropertyFile - set property file  nameof helper static method. 
	 * @param String fileName.
	 * @return None.
	 */	       
    public static void setPropertyFile(String fileName) {
        c_pfile = fileName;
    }
	/**
	 * setDebug - set debug flag helper static method. 
	 * @param bool flag.
	 * @return None.
	 */	       
    public static void setDebug(boolean flag) {
        c_debug = flag;
    }    
	/**
	 * getState - get class state of helper static method. 
	 * @param None.
	 * @return current class state.
	 */	       
    public static int getState() {
        return c_state;
    }
	/**
	 * getMessage - get class state message of helper static method. 
	 * @param None.
	 * @return current class state message.
	 */	       
    public static String getMessage() {
        return c_message;
    }
 	/**
	 * getInstance - get class current instance of helper static method. 
	 * @param None.
	 * @return current class instance.
	 */	       
    public static synchronized JavaAPI getInstance() {
        if (c_instance == null) 
            new JavaAPI(null);
        return c_instance;
    }
 	/**
	 * getNewInstance - get reset class current instance of helper static method. 
	 * @param Properties.
	 * @return current class instance.
	 */	 
    public static synchronized JavaAPI getNewInstance(Properties p) {
        JavaAPI t_instance = c_instance;
        Properties t_prop = c_prop;
        c_instance = null;
        new JavaAPI(p);
        if (t_instance != null) {
            if (c_instance == null) {
                c_instance = t_instance;
                c_prop = t_prop;
            }
            else {
                t_instance.logout();
            }   
        }
        return c_instance;
    } 
    // Object private methods for internal use only
    // Connect the current user to the server.
    private void connect() {
        try {
            this.verifyUser();
            c_instance = this;
            c_message = "Connected to AR Server " + this.getServer() + " By " + this.getProperty("username");
            c_state = 0;
        } catch (ARException e) {
            //This exception is triggered by a bad server, password or,
            //if guest access is turned off, by an unknown username.
            c_message = "Cannot verify user " + this.getUser() + ".";
            c_state = -3;
            if (c_debug) {
                printStatusList();
                System.out.print("Stack Trace:");
                e.printStackTrace();
            }
        }    
    }
    // Object private methods for internal use only
    // Load property file
    private void loadProp() {
        c_prop = new Properties();
        InputStream input = null;
        try {
            input = getClass().getClassLoader().getResourceAsStream(c_pfile);
            if (input == null) 
                input = new FileInputStream(c_pfile);
            if (input != null) 
                c_prop.load(input);
        } 
        catch (IOException e) {
            c_state = -2;
            c_message = "Load Properties failed on " + c_pfile;
            if (c_debug) {
                System.out.print("Stack Trace:");
                e.printStackTrace();
            }
        } 
        finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {}
            }
        }    
    }
    // Object private methods for internal use only
    // JavaAPI constructor
    private JavaAPI(Properties p) {
        c_state = -1;
        if (p == null)
            loadProp();
        else
            c_prop = p;
        if(c_state != -2) {
            this.setServer(this.getProperty("server"));
            this.setUser(this.getProperty("username"));
            this.setPassword(this.getProperty("password"));
            this.setPort(Integer.parseInt(this.getProperty("port")));
            connect();
        }  
    }
 	/**
	 * cleanup - logout from the server method. 
	 * @param None.
	 * @return None.
	 */	 
    public void cleanup() {
        // Logout the user from the server. This releases the resource
        // allocated on the server for the user.
        this.logout();
        c_message = "User logged out.";
        c_state = 1;
    }
  	/**
	 * getProperty - get value from properties method. 
	 * @param String key.
	 * @return String value.
	 */	 
    public static String getProperty(String key) {
        return c_prop.getProperty(key);
    }
  	/**
	 * setProperty - get value from properties method. 
	 * @param String key.
	 * @param String value.
     * @return String orevious value or null
	 */	 
    public static String setProperty(String key, String value) {
        return (String)c_prop.setProperty(key, value);
    }
    // private methods for internal use only
    // setKV -- set Key Value in Map
    static private void setKV(String s, HashMap<String, Object> hmap) {
        int idx = s.indexOf("=");
        if (idx != -1) 
            hmap.put(s.substring(0,idx).trim(), s.substring(idx+1).replaceAll("<null>", "null") );
    }
    /**
     * parseToString - unpack Form, Field toString info
     * @param String of toString format
     * @return Map of key - value pairs
     */
    static public Map<String, Object> parseToString(String s) {
        HashMap<String, Object> hmap = new HashMap<String, Object>();
        if (s != null && (s.charAt(0) == '[' || s.charAt(0) == '{')) {
            StringBuilder sb = new StringBuilder(s.substring(1,s.length()-1));
            int i = 0;
            int j = 0;
            int k = 0;
            int l = sb.length();
            while(j < l) {
                char c = sb.charAt(j);
                if (c == '[' || c == '{') k++;
                if (c == ']' || c == '}') k--;
                if (c == ',' && k == 0) {
                    setKV(sb.substring(i, j), hmap);
                    i = j+1;
                }
                j++;
            }
            if (i < j) {
                setKV(sb.substring(i, j), hmap);
            }
        }
        return hmap;
    }
    /**
	 * printStatusList - dump status for debug method. 
	 * @param  None.
	 * @return None.
	 */
    public void printStatusList() {
        List<StatusInfo> statusList = this.getLastStatus();
        if (statusList == null || statusList.size()==0) {
            System.out.println("Status List is empty.");
            return;
        }
        System.out.print("Message type: ");
        switch(statusList.get(0).getMessageType())
        {
            case Constants.AR_RETURN_OK:
                System.out.println("Note");
                break;
            case Constants.AR_RETURN_WARNING:
                System.out.println("Warning");
                break;
            case Constants.AR_RETURN_ERROR:
                System.out.println("Error");
                break;
            case Constants.AR_RETURN_FATAL:
                System.out.println("Fatal Error");
                break;
            default:
                System.out.println("Unknown (" +
                statusList.get(0).getMessageType() + ")");
                break;
        }

        System.out.println("Status List");
        for (int i=0; i < statusList.size(); i++) {
            System.out.println(statusList.get(i).getMessageText());
            System.out.println(statusList.get(i).getAppendedText());
        }
    }
    /**
	 * getARSystemDetails - get AR System Info method. 
	 * @param None.
	 * @return List of Strings of AR System Info.
	 */
    public List<String> getARSystemInfo() { 
        List<String> list = new ArrayList<String>();
        list.add("Connected to AR Server: " + this.getServerInfoStr());  
        list.add("AR Server version: " + this.getServerVersion());  
        list.add("List all connected users and last accessed time:");  
        try {  
            for (UserInfo user : this.getListUser(Constants.AR_USER_LIST_CURRENT)) {  
                list.add("   " + user.getUserName() + " - "  
                    + user.getLastAccessTime().toDate());  
            }  
        } catch (ARException e) {  
            System.out.println(e.getMessage());  
        } 
        return list;
    }
}
