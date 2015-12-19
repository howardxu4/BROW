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
 * ARQuery class is an ARServer Query help class to hide the complexity on use
 * 1. simplfy the acess query with flexible ways
 * 2. create various type of Query for common using
 * 3. provide the List of Form, Field, Entry information returned by query 
 * This class has implemented the various query methods for convenient using.
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
public class ARQuery {
    private String queryString;
    public JavaAPI ctx;
    
    /**
	 * ARQuery - constructor. 
	 * @param None.
	 */   
    public ARQuery() {
        this(null);
    }
    /**
	 * ARQuery - constructor. 
	 * @param String query statement.
	 */
    public ARQuery(String qstr) {
        set(qstr);
        ctx = JavaAPI.getInstance();
    }
    /**
	 * set - set query method. 
	 * @param String query statement.
	 * @return None.
	 */
    public void set(String qstr) {
        queryString = qstr;
    }
    /**
	 * getEntryListByQual - Retrieve entries from the form using the given qualification method. 
	 * @param String formName.
	 * @return List <Entry>.
	 */	     
    public List<Entry> queryEntries(String formName)throws ARException {
        List<Entry> entryList = null;
        ctx = JavaAPI.getInstance();
        
        // Retrieve the detail info of all fields from the form.
        List <Field> fields = ctx.getListFieldObjects(formName);

        // Create the search qualifier.
        QualifierInfo qual = ctx.parseQualification(queryString,
            fields,null, Constants.AR_QUALCONTEXT_DEFAULT);

        int[] fieldIds = {2, 7, 8};
        OutputInteger nMatches = new OutputInteger();
        List<SortInfo> sortOrder = new ArrayList<SortInfo>();
        sortOrder.add(new SortInfo(2, Constants.AR_SORT_DESCENDING));

        // Retrieve entries from the form using the given qualification.
        entryList = ctx.getListEntryObjects(
            formName, qual, 0, Constants.AR_NO_MAX_LIST_RETRIEVE,
            sortOrder, fieldIds, true, nMatches);
        return entryList;
    }
    /**
	 * queryEntryID - Retrieve entrieIDs from the form using EntryListInfo from getListEntry. 
	 * @param String formName.
	 * @return list<String> EntryIDs.
	 */	
    public List<String> queryEntryID(String schemaName)throws ARException {
        List<String> entryIdList = new ArrayList<String>();
        
        // Create the search qualifier.
        QualifierInfo qual = ctx.parseQualification(schemaName, queryString);  
        List<EntryListInfo> eListInfos = ctx.getListEntry(schemaName, qual, 0, 0, null, null, false, null);  
        for (EntryListInfo eListInfo : eListInfos) 
            entryIdList.add( eListInfo.getEntryID() );  
        return entryIdList;
    }
    /**
	 * queryEntry - Retrieve entries from the form using getListEntryObjects. 
	 * @param String formName.
	 * @return List <Entry>.
	 */	
    public List<Entry> queryEntry(String schemaName)throws ARException {
        List<Entry> entryList = new ArrayList<Entry>();
        return queryEntry(schemaName, null);
    }
    /**
	 * queryEntry - Retrieve entries from the form using getListEntryObjects with fieldIDs. 
	 * @param String formName.
     * @param int[] fieldIDs.
	 * @return List <Entry>.
	 */	
    public List<Entry> queryEntry(String schemaName, int[] fieldIDs)throws ARException {
         // Create the search qualifier.
        QualifierInfo qual = ctx.parseQualification(schemaName, queryString);  
        List<Entry> entries = ctx.getListEntryObjects(schemaName, qual, 0, 0, null, fieldIDs, false, null);  
        return entries;
    }
    /**
	 * getFieldIDs - get all field IDs in a Form. 
	 * @param String formName.
     * @param boolean nofld15 -- no field 15 inclue due to performance issue.
	 * @return int[] fieldIDs.
	 */	
    public int[] getFieldIDs(String formName, boolean nofld15)throws ARException {  
        List<Integer> integers = getListField(formName);  
        if (nofld15)
            integers.remove((Integer) 15); 
        return convertIntegers(integers);
    }
    /**
	 * convertIntegers - convert Integer List to int array. 
	 * @param List<Integer> integers.
	 * @return int[] ints.
	 */	
    public static int[] convertIntegers(List<Integer> integers) {  
        int[] ints = new int[integers.size()];  
        Iterator<Integer> iterator = integers.iterator();  
        for (int i = 0; i < ints.length; i++) {  
            ints[i] = iterator.next();  
        }  
        return ints;  
    } 
    /**
	 * getListField - getr List to Field. 
	 * @paran String formName.
	 * @return List<Integer> integers.
	 */
    public List<Integer> getListField(String formName) throws ARException{
        return ctx.getListField(formName, Constants.AR_FIELD_TYPE_DATA, 0);
    }
    /**
	 * getFieldNameByID - getr Field Name by FieldID. 
	 * @paran String formName.
     * @0aram int fieldID.
	 * @return String FieldName.
	 */
    public String getFieldNameByID(String formName, int fieldID) throws ARException {
        return ctx.getField(formName, fieldID).getName();
    }
    /**
	 * getListFieldsInForm - get list fields in a from method. 
	 * @param String formName.
	 * @return List <String[]>. [ name, option, data_type, field_type, and ID ]
	 */	 
    public List <String[]> getListFieldsInForm(String formName) throws ARException{
        List<String[]> lList = new ArrayList<String[]>();           
        List <Field> fields = ctx.getListFieldObjects(formName);
        for (Field fld: fields) {
            // Output field's name, option, type, field_type, and ID.
            String[] lst = {
                fld.getName(),
                String.valueOf(fld.getFieldOption()),
                String.valueOf(fld.getDataType()),
                String.valueOf(fld.getFieldType()),    
                String.valueOf(fld.getFieldID()) };
            lList.add(lst);
        }
        return lList;
    }
    /**
	 * getEntryFields - get list field values in an entry of from method. 
	 * @param String formName.
     * @param String entryId
	 * @return List <String[]>. [ Name, Value, Option, DataType, FieldType ]
	 */	  
    public List <String[]> getEntryFields(String formName, String entryId) {
        AREntry ent = new AREntry(formName, entryId);
        return ent.getEntryInfo();
    }
    /**
	 * getListForm - get list Form name method. 
	 * @param int formTyoe.
	 * @return List <String>.
	 */
    public List<String> getListForm(int type)throws ARException{  
        switch (type) {
        case 0: //"Disp":
            return ctx.getListDisplayOnlyForm(0);
        case 1: //"View":
            return ctx.getListViewForm(0);
        case 2: //"Vendor":
            return ctx.getListVendorForm(0);
        case 3: //"Join":
            return ctx.getListJoinForm(0);
        case 4: //"Regular":
            return ctx.getListRegularForm(0);
        case 5: //"ALL":
        default:
            return ctx.getListForm();
        }
    }
    // test program
    // check in TestJavaAPI.java

}
