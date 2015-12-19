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
 * TestJavaAPI class is the full test for all JavaAPI and used in JSP modules  
 * 1. test the basic functions for each JavaAPI modules
 * 2. test the used function in JSP for verify the web page application
 * 3. provide the List of values of constants.AR_ENUMS. 
 * This class has implemented the various  methods for reference using.
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
public class TestJavaAPI {

    static public final int  JAVAAPI =1;
    static public final int  ARFORM  =2;
    static public final int  ARFIELD =4;
    static public final int  ARENTRY =8;
    static public final int  ARQUERY =16;
    static public final int  TESTALL =31;
    static public final int  SHOWCONST = 32;
    static public final int  GETDATA = 64;
    static public final int  GETFORM = 128;
    static public final int  GETINFO = 256;
    static public final int  GETQIDS = 512;
    static public final int  GETENTRY = 1024;
    static public final int  GETFIDS = 2048;
    static public final int  GETFIELD = 4096;

    private int ctest;
    private JavaAPI ctx;
    private String schemaName;
    private String queryStr;
    
    /**
	 * usage - print out usage info. 
	 * @param None.
	 * @return None.
	 */
    public static void usage() {
        System.out.println("Usage: java user.TestJavaAPI {TESTBIT} [FormName] [QueryString]");
        System.out.println("TESTBIT:");
        System.out.println("     1  - JAVAAPI  ");   
        System.out.println("     2  - ARFORM  ");   
        System.out.println("     4  - ARFIELD  ");   
        System.out.println("     8  - ARENTRY  ");   
        System.out.println("    16  - ARQUERY  ");   
        System.out.println("    31  - TESTALL  ");   
        System.out.println("    32  - SHOWCONST  ");   
        System.out.println("    64  - GETDATA  ");   
        System.out.println("   128  - GETFORM  ");   
        System.out.println("   256  - GETINFO  ");   
        System.out.println("   512  - GETQIDS  ");   
        System.out.println("  1024  - GETENTRY  ");   
        System.out.println("  2048  - GETFIDS  ");   
        System.out.println("  4096  - GETFIELD  ");   
    }
    /**
	 * printConstantsType - Dump value of Constant type. 
	 * @param None.
	 * @return None.
	 */	
    public static void printConstantsType() {
        System.out.println("---------FORM_TYPE----------");
        System.out.println(Constants.AR_SCHEMA_REGULAR + "\tAR_SCHEMA_REGULAR");
        System.out.println(Constants.AR_SCHEMA_JOIN + "\tAR_SCHEMA_JOIN");
        System.out.println(Constants.AR_SCHEMA_VIEW + "\tAR_SCHEMA_VIEW");
        System.out.println(Constants.AR_SCHEMA_DIALOG + "\tAR_SCHEMA_DIALOG");
        System.out.println(Constants.AR_SCHEMA_VENDOR + "\tAR_SCHEMA_VENDOR");
        System.out.println("---------FIELD_TYPE----------");
        System.out.println(Constants.AR_FIELD_OFFSET + "\tAR_FIELD_OFFSET");
        System.out.println(Constants.AR_FIELD_TYPE_DATA + "\tAR_FIELD_TYPE_DATA");
        System.out.println(Constants.AR_FIELD_TYPE_TRIM + "\tAR_FIELD_TYPE_TRIM");
        System.out.println(Constants.AR_FIELD_TYPE_CONTROL + "\tAR_FIELD_TYPE_CONTROL");
        System.out.println(Constants.AR_FIELD_TYPE_PAGE + "\tAR_FIELD_TYPE_PAGE");
        System.out.println(Constants.AR_FIELD_TYPE_PAGE_HOLDER + "\tAR_FIELD_TYPE_PAGE_HOLDER");
        System.out.println(Constants.AR_FIELD_TYPE_TABLE + "\tAR_FIELD_TYPE_TABLE");
        System.out.println(Constants.AR_FIELD_TYPE_COLUMN + "\tAR_FIELD_TYPE_COLUMN");
        System.out.println(Constants.AR_FIELD_TYPE_ATTACH + "\tAR_FIELD_TYPE_ATTACH");
        System.out.println(Constants.AR_FIELD_TYPE_ATTACH_POOL + "\tAR_FIELD_TYPE_ATTACH_POOL");
        System.out.println(Constants.AR_FIELD_TYPE_ALL + "\tAR_FIELD_TYPE_ALL");
        System.out.println("---------DATA_TYPE----------");
        System.out.println(Constants.AR_DATA_TYPE_NULL + "\tAR_DATA_TYPE_NULL");
        System.out.println(Constants.AR_DATA_TYPE_KEYWORD + "\tAR_DATA_TYPE_KEYWORD");
        System.out.println(Constants.AR_DATA_TYPE_INTEGER + "\tAR_DATA_TYPE_INTEGER");
        System.out.println(Constants.AR_DATA_TYPE_REAL + "\tAR_DATA_TYPE_REAL");
        System.out.println(Constants.AR_DATA_TYPE_CHAR + "\tAR_DATA_TYPE_CHAR");
        System.out.println(Constants.AR_DATA_TYPE_DIARY + "\tAR_DATA_TYPE_DIARY");
        System.out.println(Constants.AR_DATA_TYPE_ENUM + "\tAR_DATA_TYPE_ENUM");
        System.out.println(Constants.AR_DATA_TYPE_TIME + "\tAR_DATA_TYPE_TIME");
        System.out.println(Constants.AR_DATA_TYPE_BITMASK + "\tAR_DATA_TYPE_BITMASK");
        System.out.println(Constants.AR_DATA_TYPE_BYTES + "\tAR_DATA_TYPE_BYTES");
        System.out.println(Constants.AR_DATA_TYPE_DECIMAL + "\tAR_DATA_TYPE_DECIMAL");
        System.out.println(Constants.AR_DATA_TYPE_ATTACH + "\tAR_DATA_TYPE_ATTACH");            
        System.out.println(Constants.AR_DATA_TYPE_CURRENCY + "\tAR_DATA_TYPE_CURRENCY");
        System.out.println(Constants.AR_DATA_TYPE_DATE + "\tAR_DATA_TYPE_DATE");
        System.out.println(Constants.AR_DATA_TYPE_TIME_OF_DAY + "\tAR_DATA_TYPE_TIME_OF_DAY");
        System.out.println(Constants.AR_MAX_STD_DATA_TYPE + "\tAR_MAX_STD_DATA_TYPE");

        System.out.println(Constants.AR_DATA_TYPE_JOIN + "\tAR_DATA_TYPE_JOIN");
        System.out.println(Constants.AR_DATA_TYPE_TRIM + "\tAR_DATA_TYPE_TRIM");
        System.out.println(Constants.AR_DATA_TYPE_CONTROL + "\tAR_DATA_TYPE_CONTROL");
        System.out.println(Constants.AR_DATA_TYPE_TABLE + "\tAR_DATA_TYPE_TABLE");
        System.out.println(Constants.AR_DATA_TYPE_COLUMN + "\tAR_DATA_TYPE_COLUMN");
        System.out.println(Constants.AR_DATA_TYPE_PAGE + "\tAR_DATA_TYPE_PAGE");
        System.out.println(Constants.AR_DATA_TYPE_PAGE_HOLDER + "\tAR_DATA_TYPE_PAGE_HOLDER");
        System.out.println(Constants.AR_DATA_TYPE_ATTACH_POOL + "\tAR_DATA_TYPE_ATTACH_POOL");

        System.out.println(Constants.AR_DATA_TYPE_ULONG + "\tAR_DATA_TYPE_ULONG");
        System.out.println(Constants.AR_DATA_TYPE_COORDS + "\tAR_DATA_TYPE_COORDS");
        System.out.println(Constants.AR_DATA_TYPE_VIEW + "\tAR_DATA_TYPE_VIEW");
        System.out.println(Constants.AR_DATA_TYPE_DISPLAY + "\tAR_DATA_TYPE_DISPLAY");
        System.out.println(Constants.AR_DATA_TYPE_MAX_TYPE + "\tAR_DATA_TYPE_MAX_TYPE");

        System.out.println(Constants.AR_DATA_TYPE_VALUE_LIST + "\tAR_DATA_TYPE_VALUE_LIST");
        System.out.println(Constants.AR_DATA_TYPE_UNKNOWN + "\tAR_DATA_TYPE_UNKNOWN");
    }   
    /**
	 * TestJavaAPI - constructor. 
	 * @param None.
	 */     
    public TestJavaAPI() {
        this(TESTALL, "", null);
    }
    /**
	 * TestJavaAPI - constructor. 
	 * @param int testBitMask.
     * @param String formName
     * @param String query statement.
	 */     
    public TestJavaAPI(int n, String nm, String qStr) {
        set(n);
        setForm(nm);
        setQstr(qStr);
    }
    /**
	 * set - set TestJavaAPI method. 
	 * @param int testBitNask.
	 * @return None.
	 */
    private void set(int n) {
        ctest = n;
    }
    /**
	 * setForm - set TestJavaAPI method. 
	 * @param String formName.
	 * @return None.
	 */
    public void setForm(String nm) {
        schemaName = nm;
    }
    /**
	 * setQstr - set query statement method. 
	 * @param String query statment.
	 * @return None.
	 */
    public void setQstr(String qstr) {
        queryStr = qstr;
    }

    /**
     * testResetServer - test reset AR server method 
     * @param String user
     * @param String password
     * @return None ( this test only works on certain seeting! )
     */
    private void testResetServer(String User, String Pswd) {
        Properties prop = new Properties();
        prop.setProperty("username", User);
        prop.setProperty("password", Pswd);
        prop.setProperty("server", "localhost");
        prop.setProperty("port", "9989");
        ctx = JavaAPI.getNewInstance(prop);
        System.out.println("State: " + JavaAPI.getState());
        System.out.println("Message: " + JavaAPI.getMessage());
    }
    /**
     * testAPI - Test JavaAPI method 
     * @param None
     * @return None
     */
    private void testAPI() {
        System.out.println("---------JavaAPI Test (1)------------");
        ctx = JavaAPI.getInstance();
        ctx.setProperty("formname", "User");
        System.out.println("State: " + JavaAPI.getState());
        System.out.println("Message: " + JavaAPI.getMessage());
        System.out.println("Property: " + ctx.getProperty("formname"));
        testResetServer("ARDmin","AR#Admin#");
        testResetServer("Demo","");
        List<String> l = ctx.getARSystemInfo();
        for (String s : l)
            System.out.println(s);
        ctx.cleanup();
    }
    /**
     * testForm - Test ARForm method 
     * @param None
     * @return None
     */
    private void testForm() {
        System.out.println("---------ARForm Test (2)------------");
        ARForm f = new ARForm();
        f.setup("F1", "Me", "First try");
        f.show();
        f = new ARForm(Constants.AR_SCHEMA_REGULAR);
        f.setup("Form2", "You", "Let me see ...");
        f.show();
    }
    /**
     * testFied - Test ARField method 
     * @param None
     * @return None
     */
    private void testField() {
        System.out.println("---------ARField Test (4)------------");
        ARField f = new ARField();
        f.setup("MyEmpty", "newField", 77776, false, 1);
        f.setupOption(2, "Me", "Help");
        f.show();
        
        f = new ARField(Constants.AR_DATA_TYPE_INTEGER);
        f.setup("MyDisp", "dispField", 77777, false, 1);
        f.setupOption(4, "You", "Display");
        f.get().setFieldMap(new RegularFieldMapping());
        f.show();
        
        f = new ARField(Constants.AR_DATA_TYPE_TABLE);
        f.setup("MyTable", "joinField", 77778, false, 1);
        f.setupOption(4, "We", "Join");        
        f.get().setFieldMap(new JoinFieldMapping(0, 0));
        f.show();
        
        f = new ARField(Constants.AR_DATA_TYPE_VIEW);
        f.setup("MyView", "viewField", 77779, true, 1);
        f.setupOption(4, "He", "View");        
        f.get().setFieldMap(new ViewFieldMapping("View Option"));
        f.show();
    }
    /**
     * testEntry - Test AREntry method 
     * @param None
     * @return None
     */
    private void testEntry() {
        System.out.println("---------AREntry Test (8)------------");
        AREntry ent = new AREntry();
        System.out.println(JavaAPI.getMessage());

        String formName = "User";  
        if (schemaName != "")
            formName = schemaName;
        
        ARQuery query = new ARQuery();
        try {    
            int [] fieldIds = {1};
            List<Entry>lst = query.queryEntry(formName, fieldIds);
            int l = lst.size();
            System.out.println("Size: " + l);
            for (int i = 0; i < l || i < 3; i++) {
                String eID = lst.get(i).getEntryId(); 
                System.out.println("-----------------" + eID + "-------------");
                ent.set(formName, eID);
                ent.show();
            }
 
        } catch (ARException e) {  
            query.ctx.printStatusList();  
        } 
    }
    /**
     * testQuery - Test ARQuery method 
     * @param None
     * @return None
     */
    private void testQuery() {
        System.out.println("---------ARQuery Test (16)------------");
        ARQuery query = new ARQuery();
        System.out.println(JavaAPI.getMessage());
       
        String formName = "User";
        if (schemaName != "")
            formName = schemaName;
            
        String qString = "( \'Create Date\' > \"1/1/2010\" )";
        String qString1 = "'Request ID' > \" \"";
        if (queryStr != null )
            qString = queryStr;
        System.out.println("@@@@ " + formName + " @@@@[" + qString + "]");
        try {
            ARForm form = new ARForm(query.ctx.getForm(formName));
            form.show();
            System.out.println(form.get().toString());
        
            List<String> llst = query.queryEntryID(formName);
            System.out.println("----------------\nSize: " + llst.size());
            for (String s: llst)
                System.out.println(s);
            System.out.println("----------------");

            int [] fldIDs = query.getFieldIDs(formName, true);
            List<Entry>lst = query.queryEntry(formName, fldIDs);
            System.out.println("Size: " + lst.size());
            
            query.set(qString1);
            lst = query.queryEntry(formName, null);
            System.out.println("Size: " + lst.size());
            
            query.set(qString);
            lst = query.queryEntry(formName, fldIDs);
            System.out.println("Size: " + lst.size());
            
            Entry entry = lst.get(0);
            int id = Integer.parseInt(entry.getEntryId());
            ARField fld = new ARField(query.ctx.getField(formName, id));
            fld.show();
            
            for (Integer i : entry.keySet()) {  
                String FieldName = query.getFieldNameByID(formName, i.intValue());
                System.out.println(FieldName + " : " + entry.get(i)); 
            } 
            
        }
        catch (ARException e) {  
            query.ctx.printStatusList();
        }  
    }
    /**
     * testQuery1 - Test JSP GetData method 
     * @param None
     * @return None
     */   
    private void testQuery1() {
        System.out.println("---------GetData Test (64)------------");
        String[] forms = {"Disp", "View", "Vendor", "Join", "Regular", "ALL" };
        String selName = "ALL";
        if (schemaName != "")
            selName = schemaName;
        int n = 0;
        while(n < forms.length) {
            if (forms[n].equals(selName))
                break;
            n++;
        }
        ARQuery query = new ARQuery();
        System.out.println(JavaAPI.getMessage());
        
        try {
            List<String> lList = query.getListForm(n);
            for (int i = 0; i < lList.size(); i++)
                System.out.println(lList.get(i));
            System.out.println( n + "  " + forms[n > 5? 5:n] + " : " + lList.size());
        }
        catch (ARException e) {  
            query.ctx.printStatusList(); 
        }  
    }
    /**
     * testQuery2 - Test JSP GetForm method 
     * @param None
     * @return None
     */  
    private void testQuery2() {
        System.out.println("---------GetForm Test (128)------------");
        ARQuery query = new ARQuery();
        System.out.println(JavaAPI.getMessage());
       
        String formName = "User";
        if (schemaName != "")
            formName = schemaName;
        try {
            List<String[]> lList = query.getListFieldsInForm(formName);
            for (String[] lst: lList) {
                System.out.println("[\"" + lst[0] + "\",\"" + lst[1] +"\",\"" + lst[2] + "\",\"" + lst[3] + "\",\"" + lst[4] + "\"]");
            }
        }
        catch (ARException e) {  
            query.ctx.printStatusList(); 
        }  
    }
    /**
     * testQuery3 - Test JSP GetInfo method 
     * @param None
     * @return None
     */  
    private void testQuery3() {
        System.out.println("---------GetInfo Test (256)------------");
        ARQuery query = new ARQuery();
        System.out.println(JavaAPI.getMessage());
       
        String formName = "User";
        if (schemaName != "")
            formName = schemaName;
        try {
            ARForm form = new ARForm(query.ctx.getForm(formName));  
            Map<String, Object> hmap = form.getInfo();
            System.out.println("Name: " + hmap.get("Name"));
            System.out.println("New Name: " + hmap.get("New Name"));
            System.out.println("Owner: " + hmap.get("Owner"));
            System.out.println("Last Changed By: " +hmap.get("Last Changed By"));
            System.out.println("Last Update Time: " + hmap.get("Last Update Time"));
            System.out.println("Default VUI: " + hmap.get("Default VUI"));
            System.out.println("Help Text: " + hmap.get("Help Text"));
            System.out.println("-------------");
            form.show();
        }
        catch (ARException e) {  
            query.ctx.printStatusList(); 
        }  
    }
    /**
     * testQuery4 - Test JSP GetQids method 
     * @param None
     * @return None
     */  
    private void testQuery4() {
        System.out.println("---------GetQids Test (512)------------");
        ARQuery query = new ARQuery();
        System.out.println(JavaAPI.getMessage());
        
        String formName = "User";
        if (schemaName != "")
            formName = schemaName;
        if (queryStr != null )
            query.set(queryStr);
        try {
            List<Entry> entryList = query.queryEntry(formName);
            for( int i = 0; i < entryList.size(); i++ ){
                System.out.println( entryList.get(i).getEntryId());
            }
            System.out.println("-------------------------------");
            int [] fldIds = {1};
            entryList = query.queryEntry(formName, fldIds);
            for( int i = 0; i < entryList.size(); i++ ){
                System.out.println( entryList.get(i).getEntryId());
            }
        }
        catch (ARException e) {  
            query.ctx.printStatusList(); 
        }  
    }
    /**
     * testQuery5 - Test JSP GetEntry method 
     * @param None
     * @return None
     */  
    private void testQuery5() {
        System.out.println("---------GetEntry Test (1024)------------");
        ARQuery query = new ARQuery();
        System.out.println(JavaAPI.getMessage());
        
        String formName = "User";
        if (schemaName != "")
            formName = schemaName;
        String entryId = "000000000000001";
        if (queryStr != null )
            entryId = queryStr;
        
        List<String[]> list = query.getEntryFields(formName,  entryId);
        int l = list.size();
        if (l > 0) {
            int ll = list.get(0).length;
            for(int i=0; i<l; i++) {
                for (int j=0; j<ll; j++) {
                    if (j > 0) System.out.print(",");
                    System.out.print(list.get(i)[j] );
                }
                System.out.println("");
            }
        }        
        
        System.out.println("-------------------------------");
        AREntry ent = new AREntry(formName, entryId);
        ent.disp(); 
    }
    /**
     * testQuery6 - Test JSP GetFids method 
     * @param None
     * @return None
     */  
    private void testQuery6() {
        System.out.println("---------GetFids Test (2048)------------");
        ARQuery query = new ARQuery();
        System.out.println(JavaAPI.getMessage());
        
        String formName = "User";
        if (schemaName != "")
            formName = schemaName; 
        try { 
            List <String[]> fieldList= query.getListFieldsInForm(formName);
            for( int i = 0; i < fieldList.size(); i++ ){
                System.out.println( fieldList.get(i)[0] + ",\t(" + fieldList.get(i)[4] + ")");             }
        }
        catch(Exception e) {
            System.out.println("Error :" + e.getMessage());
        }        
    }
    /**
     * testQuery7 - Test JSP GetField method 
     * @param None
     * @return None
     */  
    private void testQuery7() {
        System.out.println("---------GetField Test (4096)------------");
        ARQuery query = new ARQuery();
        System.out.println(JavaAPI.getMessage());
        
        String formName = "User";
        if (schemaName != "")
            formName = schemaName;
        String fieldId = "1";
        if (queryStr != null )
            fieldId = queryStr;
        
        try {
            Field field = query.ctx.getField(formName, Integer.parseInt(fieldId));
            System.out.println( field.getName() + "\t(" + fieldId + ")"); 
            Map<String, Object> hmap = query.ctx.parseToString(field.toString());;
            Iterator iter = hmap.entrySet().iterator();
            while(iter.hasNext()) {
                Map.Entry mentry = (Map.Entry)iter.next();
                System.out.println(mentry.getKey() + ",\t" + (String)mentry.getValue());
            }
        }
        catch(Exception e) {
            System.out.println("Error :" + e.getMessage());
        }
    }    
    /**
     * test - test bitmask method
     * @param int n constans
     * @return boolean true/false
     */
    private boolean test(int n) {
        return ((ctest & n) != 0);
    }
    /**
     * run - Run all test methods 
     * @param None
     * @return None
     */
    private void run() {
        if (test(JAVAAPI)) testAPI();
        if (test(ARFORM)) testForm();
        if (test(ARFIELD)) testField();
        if (test(ARENTRY)) testEntry();
        if (test(ARQUERY)) testQuery();
        if (test(SHOWCONST)) printConstantsType();
        if (test(GETDATA)) testQuery1();
        if (test(GETFORM)) testQuery2();
        if (test(GETINFO)) testQuery3();
        if (test(GETQIDS)) testQuery4();
        if (test(GETENTRY))testQuery5();
        if (test(GETFIDS))testQuery6();
        if (test(GETFIELD))testQuery7();
    }
    /**
     * main - Main test program entry
     * @param String[] agrs
     * @return None
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            usage();
            System.exit(0);
        }
        int n = new Integer(args[0]).intValue();
        String name = "";
        if (args.length > 1)
           name = args[1];
        String qstr = null;
        if (args.length > 2)
           qstr = args[2];
        TestJavaAPI test = new TestJavaAPI(n, name, qstr);
        test.run();
    }
}
