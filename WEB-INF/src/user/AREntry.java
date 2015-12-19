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
 * AREntry class is an ARServer Entry container class to hide the complexity on use
 * 1. simplfy the acess Entry with flexible ways
 * 2. create various type of Entry and set values for common using
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
public class AREntry {
    public JavaAPI ctx;
    private Entry entry;
    private String schemaName;
    private String entryID;
    
    /**
	 * AREntry - constructor. 
	 * @param None.
	 */
    public AREntry() {
        this(new Entry());
    }
    /**
	 * AREntry - constructor. 
	 * @param String formName.
	 */
    public AREntry(String fm) {
        this();
        set(fm);
    }
    /**
	 * AREntry - constructor. 
	 * @param Entry.
	 */
    public AREntry(Entry ent) {
        ctx = JavaAPI.getInstance();
        set(ent);
    }
    /**
	 * AREntry - constructor. 
	 * @param String formName.
     * @param String entryID
	 */
    public AREntry(String fm, String eId) {
        this();
        set(fm, eId);
    }
    /**
	 * set - set Entry method. 
	 * @param Entry object.
	 * @return None.
	 */
    public void set(Entry ent) {
        entry = ent;
    }
    /**
	 * set - set Entry method. 
	 * @param String formName.
     * @param String entryID
	 * @return None.
	 */
    public void set(String fm, String eId) {
       try {
            entry = ctx.getEntry(schemaName=fm, entryID=eId, null);
        } 
        catch (ARException e) {  
            System.out.println(e.getMessage());  
        }
    }
    /**
	 * set - set Entry method. 
	 * @param String formName.
	 * @return None.
	 */
    public void set(String fm) {
        schemaName=fm;
    }
    /**
	 * get - get Entry method. 
	 * @param None.
	 * @return Entry.
	 */
    public Entry get() {
        return entry;
    }
    /**
	 * put - put Field value method. 
	 * @param int fieldID.
     * @param String value
     * @param DataType dataType
	 * @return None.
	 */
    public void put(int fieldID, String value, DataType dataType) {
        // INTEGER, REAL, CHAR, TIME (expecting Timestamp String),
        // BITMASK, DECIMAL, ULONG, ENUM
        if (entry != null)
            entry.put(fieldID, new Value(value, dataType));
    }
    /**
	 * put - put Field value method. 
	 * @param int fieldID.
     * @param String value
	 * @return None.
	 */
    public void put(int fieldID, String value) {
        if (entry != null)
            entry.put(fieldID, new Value(value));
    }
    /**
	 * put - put Field value method. 
	 * @param int fieldID.
     * @param Value value
	 * @return None.
	 */
    public void put(int fieldID, Value value) {
        if (entry != null)
            entry.put(fieldID, value);
    }
    /**
	 * show - display Entry information method. 
	 * @param None.
	 * @return None.
	 */
    public void show() {
        List<String[]> list = getEntryInfo();
        int l = list.size();
        System.out.println("----------Entry Information----------");
        if (l > 0) {
            int ll = list.get(0).length;
            for(int i=0; i<l; i++) {
                for (int j=0; j<ll; j++) {
                    if (j > 0) System.out.print(", ");
                    System.out.print(list.get(i)[j] );
                }
                System.out.println("");
            }
        }
    }
   /**
	 * disp - display Entry raw information method. 
	 * @param None.
	 * @return None.
	 */
    public void disp() {
        System.out.println("----------Entry Raw Information----------");
        List<String[]> list = getEntryInfos();
        int l = list.size();
        if (l > 0) {
            int ll = list.get(0).length;
            for(int i=0; i<l; i++) {
                for (int j=0; j<ll; j++) {
                    if (j > 0) System.out.print(", ");
                    System.out.print(list.get(i)[j] );
                }
                System.out.println("");
            }
        }
        
    }
    /**
	 * getEntryInfos - get Entry raw information method. 
	 * @param String formName.
     * @param String entryID
	 * @return List<String[]> field,value.
	 */
    public List<String[]> getEntryInfos(String fm, String eId) {
        set(fm, eId);
        return getEntryInfos();
    }
    /**
	 * getEntryInfos - get Entry raw information method. 
	 * @param None.
	 * @return List<String[]> field,value.
	 */
    public List<String[]> getEntryInfos() {
        Map<String, Object> hmap = JavaAPI.parseToString(entry.toString());
        List <String[]> lst = new ArrayList<String[]>();
        try {
            for (Integer fieldID : entry.keySet()) {
                Field fld = ctx.getField(schemaName, fieldID);
                String value = (String)hmap.get(fieldID.toString());
                // compress data in { } -- attachment
                if (value != null && value.length() > 100) {
                    int idx = value.indexOf("{");
                    if (idx != -1) {
                        int eidx = value.indexOf("}");
                        if (eidx != -1) value = value.substring(0,idx+1) + "..." + value.substring(eidx);                
                    }
                }
                // Name, Value, Option, DataType, FieldType, FieldID
                String[] ss = {fld.getName(), value, 
                    String.valueOf(fld.getFieldOption()),
                    String.valueOf(fld.getDataType()),
                    String.valueOf(fld.getFieldType()),
                    String.valueOf(fieldID)};
                lst.add(ss);
            }
        } catch( ARException e) {
            System.out.println(e.getMessage());
        }
        return lst;
    } 
    /**
	 * dumpAttach - dump attach to a file method. 
	 * @param String file name. 
	 * @param byte[] attach data.
	 * @return None.
	 */
    public void dumpAttach(String aName, byte[] attach) {  
        int lastPos = aName.lastIndexOf('\\');  
        String aNameShort = (lastPos < 0) ? aName : aName.substring(lastPos + 1);
        try {
            FileOutputStream fos = new FileOutputStream(aNameShort);  
            fos.write(attach);  
            fos.close(); 
        } catch (FileNotFoundException e) {  
            System.out.println(e.getMessage());  
        } catch (IOException e) {  
            System.out.println(e.getMessage());  
        }          
    }
    /**
	 * getFieldValueString - get Field Value String method. 
	 * @param int fieldID.
	 * @return Object field: value.
	 */
    public Object[] getFieldValueString( Integer fieldID) {
        Object [] rtn = {null, ""};
        try {    
            Value val = entry.get(fieldID);  
            Field field = ctx.getField(schemaName, fieldID);  
            rtn[0] = field;
            if (val != null && val.toString() != null) {  
                if (field instanceof CharacterField) {  
                    if (fieldID != 15) {  
                        rtn[1] += val.toString();  
                    } else {  
                        StatusHistoryValue shVal = StatusHistoryValue.decode(val.getValue().toString());  
                        StringBuilder shBuilder = new StringBuilder();  
                        if (shVal != null) {  
                            for (StatusHistoryItem shItem : shVal) {  
                                if (shItem != null) {  
                                    shBuilder.append(shItem.getTimestamp().toDate().toString());  
                                    shBuilder.append(", " + shItem.getUser() + ". ");                           //shBuilder.append("\u0004").append(shItem.getUser()).append("\u0003");  
                                } else {  
//                                    shBuilder.append("\u0003");  
                                }  
                            }  
                            rtn[1] += shBuilder.toString();  
                        }  
                    }  
                } else if (field instanceof SelectionField) {  
                    SelectionFieldLimit sFieldLimit = (SelectionFieldLimit)field.getFieldLimit();  
                    if (sFieldLimit != null) {  
                        List<EnumItem> eItemList = sFieldLimit.getValues();  
                        for (EnumItem eItem : eItemList) {  
                            if (eItem.getEnumItemNumber() == Integer.parseInt(val.toString())) {  
                                rtn[1] += eItem.getEnumItemName();  
                            }  
                        }  
                    }  
                } else if (field instanceof CurrencyField) {  
                    CurrencyValue cCurValue = (CurrencyValue) val.getValue();  
                    if (cCurValue != null) {  
                        for (FuncCurrencyInfo curInfo : cCurValue.getFuncCurrencyList()) {  
                            rtn[1] += curInfo.getValue() + " " + curInfo.getCurrencyCode();  
                        }  
                    }  
                } else if (field instanceof DateTimeField) {  
                    Timestamp callDateTimeTS = (Timestamp) val.getValue();  
                    if (callDateTimeTS != null) {  
                        rtn[1] += callDateTimeTS.toDate().toString();  
                    }  
                } else if (field instanceof DiaryField) {  
                    for (DiaryItem dlv : (DiaryListValue)val.getValue()) {  
                        rtn[1] += "Created by " + dlv.getUser() + " @ " + dlv.getTimestamp().toDate().toString() + " " + dlv.getText();  
                    }  
                } else if (field instanceof AttachmentField) {  
                    AttachmentValue aVal = (AttachmentValue) val.getValue();  
                    if (aVal != null) {  
                        String aName;  
                        String[] aDetails = aVal.getValueFileName().split("\\.(?=[^\\.]+$)");  
                        if (aDetails.length == 2) {  
                            aName = aDetails[0] + "." + aDetails[1];  
                        } else {  
                            aName = aDetails[0];  
                        }  
                        rtn[1] += aVal.getValueFileName(); //aName;
                        byte[] attach = ctx.getEntryBlob(schemaName, entryID, fieldID);    
                        if (false) // Turn OFF now!!
                        dumpAttach(aName, attach);   
                    }  
                } else {  
                    rtn[1] += val.toString();  
                }  
            }
            else
                rtn[1] += "null";

        } catch (ARException e) {  
            System.out.println(e.getMessage());  
        }
        return rtn;
    }
    /**
	 * getEntryInfo - get Entry information method. 
	 * @param String formName.
     * @param String entryID
	 * @return List<String[]> field,value.
	 */
    public List<String[]> getEntryInfo(String fm, String eId) {
        set(fm, eId);
        return getEntryInfo();
    }
    /**
	 * getEntryInfo - get Entry information method. 
	 * @param None.
	 * @return List<String[]> field,value.
	 */
    public List<String[]> getEntryInfo() {
        List <String[]> lst = new ArrayList<String[]>();
        for (Integer fieldID : entry.keySet()) {
            Object[] s = getFieldValueString(fieldID);
            if (s[0] != null) {
                Field fld = (Field)s[0];
                // Name, Value, Option, DataType, FieldType, FieldID
                String[] ss = {fld.getName(), (String)s[1], 
                    String.valueOf(fld.getFieldOption()),
                    String.valueOf(fld.getDataType()),
                    String.valueOf(fld.getFieldType()),
                    String.valueOf(fieldID)};
                lst.add(ss);
            }
        }
        return lst;
    }  
    // test program
    // check in TestJavaAPI.java

}
