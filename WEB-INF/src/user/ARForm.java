//******************************************************************************
// Package Declaration
//******************************************************************************
package user;
//******************************************************************************
// Import Specifications
//******************************************************************************
import com.bmc.arsys.api.*;
import java.util.*;
/**
 *******************************************************************************
 * <B> Class Description: </B><p><pre>
 *
 * ARForm class is an ARServer Form container class to hide the complexity on use
 * 1. simplfy the acess Form with flexible ways
 * 2. create various type of Form and set values for common using
 * 3. provide the current information of the instance
 * This class has implemented the methods set, get, show for convenient using.
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
 * 
 * </ul>
 *******************************************************************************
*/
public class ARForm {
    private Form fm;
    
	/**
	 * buildForm - create a Form object by type method. 
	 * @param int type.
	 * @return Form.
	 */	
    public static Form buildForm(int type) {
        switch(type) {
            case Constants.AR_SCHEMA_DIALOG:
                return new DisplayOnlyForm();
            case Constants.AR_SCHEMA_JOIN:
                return new JoinForm(); //getJoinForm();
            case Constants.AR_SCHEMA_VIEW:
                return new ViewForm();  //getViewForm();
            case Constants.AR_SCHEMA_VENDOR:
                return new VendorForm(); //getVendorForm();
            case Constants.AR_SCHEMA_REGULAR:
            default:
                return new RegularForm();
        }
    }
    /**
	 * ARForm - constructor. 
	 * @param None.
	 */
    public ARForm() {
        this(Constants.AR_SCHEMA_REGULAR);
    }
    /**
	 * ARForm - constructor. 
	 * @param int type.
	 */
    public ARForm(int type) {
        fm = buildForm(type);
    } 
    /**
	 * ARForm - constructor. 
	 * @param Form object.
	 */
    public ARForm(Form form) {
        set(form);
    }
    /**
	 * set - set Form method. 
	 * @param Form object.
	 * @return None.
	 */
    public void set(Form form) {
        fm = form;
    }
   /**
	 * get - get Form method. 
	 * @param None.
	 * @return Form.
	 */
    public Form get() {
        return fm;
    }
    /**
	 * setup - setup Form method. 
	 * @param String form name.
	 * @param String owner name.
	 * @param String help text.
	 * @return None.
	 */
    public void setup(String name, String owner, String helptxt) {
        fm.setName(name);
        fm.setOwner(owner);
        fm.setHelpText(helptxt);
    }
    /**
	 * getInfo - get Form information method. 
	 * @param None.
	 * @return Map<String, Object> hmap.
	 */
    public Map<String, Object> getInfo() {
        Map<String, Object> hmap = JavaAPI.parseToString(fm.toString());
        hmap.put("Last Update Time", String.valueOf(fm.getLastUpdateTime().toDate()));
        return hmap;
    }
    /**
	 * show - display Form information method. 
	 * @param None.
	 * @return None.
	 */
    public void show() {
        System.out.println("----------Form Information----------");
        Map<String, Object> hmap = getInfo();
        Iterator iter = hmap.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry mentry = (Map.Entry)iter.next();
            System.out.println(mentry.getKey() + " = " + mentry.getValue());
        }
    }
    // test program
    // check in TestJavaAPI.java
}
