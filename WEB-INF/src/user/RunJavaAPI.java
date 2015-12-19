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
 * RunJavaAPI class is an ARServer JavaAPI utility
 * 1. provide export, import, show, remove a Formd utility
 * 2. provide query, show, save, create, update, remove variors Field utility
 * 3. provide the command line utility for fitting batch process
 * This class has implemented the methods CRUD and dump for convenient using.
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
public class RunJavaAPI {
    ARQuery query;
    static private boolean noAsk = false;
    /**
	 * RunJavaAPI - constructor. 
	 * @param None.
	 */
    public RunJavaAPI() {
        query = new ARQuery();
    }
    /**
	 * setNoAsk - set no interactive. 
	 * @param booean on/off
     * @return None.
	 */
    static public void setNoAsk(boolean f) {
        noAsk = f;
    }
    /**
	 * getYes - get yes/no interactively. 
	 * @param String pop string
     * @return booean true/false.
	 */
    private boolean getYes(String popString) {
        if (noAsk)
            return noAsk;
        Scanner kbd = new Scanner (System.in);
        System.out.println(popString);
        String yorn = kbd.nextLine();
        return (yorn.contains("y") || yorn.contains("Y"));       
    }
    /**
	 * verifyFile - verify the def format. 
	 * @param String fileName
     * @return booean true/false.
	 */
    private boolean verifyFile(String fileName) {
        boolean rtn = false;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            boolean isSchema = false;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("begin schema"))
                    isSchema = true;
                else if (isSchema && line.contains("name")) {
                    if (line.indexOf(":") != -1)
                        line = line.substring(line.indexOf(":")+1).trim();
                    else
                        line = null;
                    break;
                }
            }
            bufferedReader.close();
            if (line != null) {
                System.out.println("Form name: " + line);
                try {
                    if (query.ctx.getForm(line) != null)
                        System.out.println("The form " + line + " exists!!!");
                }
                catch( ARException e) {
                    rtn = true;
                }
            }
            else
                System.out.println("Bad def format file: " + fileName);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rtn;
    }
    /**
	 * expFormToFile - export the form to def file. 
	 * @param String fileName
     * @return None.
	 */
    private void expFormToFile(String formName) {
        try {
            String fileName = "my"+formName+".def";
            List<StructItemInfo > l = new ArrayList<StructItemInfo>();
            l.add(new StructItemInfo(query.ctx.getForm(formName)));
            query.ctx.exportDefToFile(l, false,fileName,true);
            System.out.println("Form " + formName + " is exported to " + fileName);
        } catch (ARException e) {
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    /**
	 * impFormFromFile - import the form from def file. 
	 * @param String fileName
     * @return None.
	 */    
    private void impFormFromFile(String fileName) {
        if (verifyFile(fileName))        
        try {
            query.ctx.importDefFromFile(fileName, Constants.AR_IMPORT_OPT_OVERWRITE);  
            System.out.printf("Successfully imported file: %s\n", fileName);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    /**
	 * showFormInfo - show the form information. 
	 * @param String formName
     * @return None.
	 */     
    private void showFormInfo(String formName) {
        try {
            ARForm fm = new ARForm(query.ctx.getForm(formName));
            fm.show();
            List <String[]> lList = query.getListFieldsInForm(formName);
            System.out.println("FieldName    Option   DataType   FieldType   ID");
            System.out.println("=========    ======   ========   =========   ==");
            for (String[] lst: lList) {
                    System.out.println("[\"" + lst[0] + "\",\"" + lst[1] +"\",\"" + lst[2] + "\",\"" + lst[3] + "\",\"" + lst[4] + "\"]");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    /**
	 * showFieldInfo - show the field information. 
	 * @param String formName
     * @param String fieldId
     * @return None.
	 */      
    private void showFieldInfo(String formName, String fieldId) {
        try {
            int fldId = 0;
            if (fieldId != null) {
                try {
                    fldId = Integer.parseInt(fieldId);
                } catch (Exception e) {}
            }
            ARField field = new ARField();
            List <Field> fields = query.ctx.getListFieldObjects(formName);
            for (Field fld: fields) {
                if (fldId == 0) {
                    field.set(fld);
                    field.show();
                }
                else if (fld.getFieldID() == fldId){
                    field.set(fld);
                    field.show();  
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    /**
	 * delFormOnServer - del the form on ARserver. 
	 * @param String formName
     * @return None.
	 */  
    private void delFormOnServer(String formName) {
        if (getYes("Are you sure you want to delete the form: " + formName + "? (y/n)")){
            try {
                Form tf = query.ctx.getForm(formName);
                List<String> lst = query.queryEntryID(formName);
                for( String entryId: lst) {
                    query.ctx.deleteEntry(formName, entryId, 0);
                }
                query.ctx.deleteForm(formName, 0);
                System.out.println("Deleted " + formName + " with " + lst.size() + " entries");
            } catch( ARException e ) {
                System.out.println(e.getMessage());
            }   
        }
    }
    /**
	 * showFormEntries - show entries in the form. 
	 * @param String formName
     * @param String query statement.
     * @return None.
	 */   
    private void showFormEntries(String formName, String qString) {
        try {
            query.set(qString);
            List<String>lst = query.queryEntryID(formName);
            AREntry entry = new AREntry();
            int i = 0;
            for (String entryId: lst) {
                if (i++ < 5) {
                    System.out.println(formName + " ......... " + entryId);
                    entry.set(formName, entryId);
                    System.out.println(entry.get().toString());
                }
                else
                    System.out.println(formName + " ......... " + entryId);
            }
            System.out.println("\nTotal entries " + i);
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    /**
	 * checkValue - check commas in value. 
	 * @param String value
     * @return value coverted.
	 */   
    private String checkValue(String value) {
        if (value != null && value.contains(",")) {
            if (value.contains("\""))
                value = value.replaceAll("\"","\"\"");
            value = "\"" + value + "\""; 
        }
        return value;
    }
    /**
	 * saveEntries - save entries in a CSV file. 
	 * @param String formName
     * @param List<String> entryIdList.
     * @return None.
	 */   
    private void saveEntries(String formName, List<String> entryIdList) {
        String fileName = formName + "_Fields.csv";
        fileName = fileName.replaceAll(":","_").replaceAll(" ", "_");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName);
            fileWriter.append("Name,Value,Option,DataType,FieldType,FieldID\n");
            AREntry entry = new AREntry();
            for (String entryId: entryIdList) {
                fileWriter.append("EntryID,"+ formName + ",,READ,,'"+ entryId +"'\n");
                List<String[]> list = entry.getEntryInfos(formName, entryId);
                int l = list.size();
                if (l > 0) {
                    int ll = list.get(0).length;
                    for(int i=0; i<l; i++) {
                        for (int j=0; j<ll; j++) {
                            if (j > 0) fileWriter.append(",");
                            if (j == 1) 
                                fileWriter.append(checkValue(list.get(i)[j]));
                            else
                                fileWriter.append(list.get(i)[j] );
                        }
                        fileWriter.append("\n");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
                System.out.println("\n" + entryIdList.size() + " Entries in Form " + formName + " are saved to " + fileName);
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }
        }
    }
    /**
	 * saveEntriesToFile - save entries to a CSV file. 
	 * @param String formName
     * @param String query statement.
     * @return None.
	 */  
    private void saveEntriesToFile(String formName, String qString) {
        try {
            query.set(qString);
            List<String>lst = query.queryEntryID(formName);
            System.out.println("Name  Value  Option  DataType  FieldType  FieldID");
            System.out.println("====  =====  ======  ========  =========  =======");
            if (lst.size() > 0) {
                AREntry entry = new AREntry();
                int i = 0;
                for (String entryId: lst) {
                    if (i++ < 3) {
                        entry.set(formName, entryId);
                        entry.show();
                    }
                    else
                        System.out.println(formName + "......... " + entryId);
                } 
                saveEntries(formName, lst);
            }
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    /**
	 * getAttachmentValue - get AttachmentValue from value string. 
	 * @param String value
     * @return AttachmentValue.
	 */  
    private AttachmentValue getAttachmentValue(String value) throws IOException{
        int i = value.indexOf("Name=");
        int j = value.indexOf(",");
        String Name = value.substring(i+5, j);
        i = value.indexOf("File Path=");
        j = value.length()-2;
        String Path = value.substring(i+10, j);
        if (Path.equals("null"))
            Path = Name;
        return new AttachmentValue(Name, Path);
    }
    /**
	 * getValue - get Value from value string. 
     * @param int dtype DataType
	 * @param String value
     * @return Value.
	 */
    private Value getValue(int dtype, String value) throws IOException {
        switch (dtype) {
            case 2: // INTEGER
                return new Value(value, DataType.INTEGER);
            case 3: // REAL
                return new Value(value, DataType.REAL);
            case 4: // CHAR
                return new Value(value, DataType.CHAR);
            case 5: // DIARY
                break;
            case 6: // ENUM
                return new Value(value, DataType.ENUM);
            case 7: // TIME
                return new Value(value, DataType.TIME);
            case 8: // BITMASK
                return new Value(value, DataType.BITMASK);
            case 9: // BYTES
                break;
            case 10:// DECIMAL 
                return new Value(value, DataType.DECIMAL);
            case 11:// ATTACH
                return new Value(getAttachmentValue(value));
            case 12:// CURRENCY
                break;
            case 13:// DATE
            case 14:// TIME_OF_DAY
                return new Value(value);
                //break;
            case 40:// ULONG
                return new Value(value, DataType.ULONG);
            default:
                break;
        }
        return null;
    }
    /**
     * checkRequired - process entry Required fields
     * @param Entry entry
     * @param List <Field> fields
     * @return None.
    */
    private void checkRequired( Entry entry, List <Field> fields) {
        int [] coreIDs = { 3,5,6,15 };
        for (int id : coreIDs)
            entry.remove(id);

        Map<String, Object> hmap = JavaAPI.parseToString(entry.toString());
        for (Field fld: fields) {
            if (fld.getFieldOption() == 1) {
                String value = (String)hmap.get("" + fld.getFieldID());
                if (value == null || value.isEmpty() || value.equals("null"))
                    entry.remove(fld.getFieldID());
            }
        }	
    }
    /**
     * processEntryOP - process entry operation. 
     * @param String OP ADD/SET
     * @param String formName
     * @param String entryId
     * @List<String[]> list of Field ID, Value, DataType in entry
     * @boolean doIt true/false
     * @return None.
	 */
    private void processEntryOP(String OP, String formName, String entryId, List<String[]> lst, boolean doIt) {
        try {
            // ADD
            AREntry myEntry = new AREntry(formName);
            // SET
            boolean isSet = OP.equals("SET"); 
            if (isSet)
                myEntry.set(formName, entryId);

            for (String[] s : lst) {
                if (!s[1].equals("null")) {
                    System.out.println(s[5] + " : " + s[3] + " --> " + s[1]);
                    int fid = Integer.parseInt(s[5]);
                    // ADD, Status History, Submitter, Status, Shot Description
                    if (!isSet || fid > 15 || fid == 2 || fid == 7 || fid == 8 )
                    try {
                        Value value = getValue(Integer.parseInt(s[3]), s[1]);
                        if (value != null) 
                            myEntry.put(Integer.parseInt(s[5]), value); 
                        else
                            System.out.println("@@Unhandled " + s[3] + " on value: " + s[1]);
                    }
                    catch (Exception e) {
                        System.out.println("DataType " + s[1] + "failed! " + e.getMessage());
                    }
                    else
                        System.out.println("@@System handle " + s[3] + " on value: " + s[1]);
                }
            }  
            if (doIt) {
                if (!isSet){    // ADD
                    String entryIdOut = query.ctx.createEntry(formName, myEntry.get());
                }
                else {          // SET
                    checkRequired(myEntry.get(),  query.ctx.getListFieldObjects(formName));
                    query.ctx.setEntry(formName, entryId, myEntry.get(), null, 0);
                }
            }
            else
                myEntry.disp();
 
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    /**
	 * mysplit - check commas in value. 
	 * @param String line
     * @return String[] splited by comma.
	 */  
    private String[] mysplit(String line) {
        List<String> lst = new ArrayList<String>();
        int s = 0;
        boolean f = true;
        for(int i = 0; i<line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') f = !f;
            if (f && c == ',') {
                lst.add(line.substring(s,i));
                s = i+1;
            }
        }
        lst.add(line.substring(s));
        return lst.toArray(new String[0]);
    }
    /**
	 * operateEntriesFromFile - process entry operation from file. 
	 * @param String fileName
     * @boolean doIt true/false
     * @return None.
	 */
    private void operateEntriesFromFile(String fileName, boolean doIt) {
        BufferedReader fileReader = null;
        boolean goahead = true;
        if (doIt)
            goahead = getYes("Have you checked the file " + fileName + " for process entry operations? (y/n)");
        if (goahead)
        try {
            fileReader = new BufferedReader(new FileReader(fileName));
            int total = 0;
            String line = "";
            String formName = "";
            String entryId = "";
            String OP = "";
            List<String[]> lst = new ArrayList<String[]>();
            boolean start = false;
            
            
            //Read the CSV file header to skip it
            fileReader.readLine();
            //Read the file line by line starting from the second line
            while ((line = fileReader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length > 6) 
                    tokens = mysplit(line);                
                if (tokens.length == 6) {
                    if (tokens[0].equals("EntryID")) {
                        for(int i = 0; i < 6; i++) {
                            System.out.print(tokens[i]);
                            if (i < 5) System.out.print(", ");
                            else System.out.println("");
                        }
                        if (start) {
                            processEntryOP(OP, formName, entryId, lst, doIt);
                            lst.clear();
                        }
                        formName = tokens[1].trim();
                        OP = tokens[3].trim();
                        entryId = tokens[5].replaceAll("'","");
                        start = (OP.equals("ADD") || OP.equals("SET"));
                        if (OP.equals("DEL") && doIt) {
                            if (getYes("Are you really want to delete the entry " + tokens[5] + " in " + formName + "? (y/n)")) {
                                try {
                                query.ctx.deleteEntry(formName, entryId, 0);
                                System.out.println("Entry " + entryId + " be deleted from Form " + formName);
                                }
                                catch (ARException e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        }
                        else if (OP.equals("READ") && !doIt) {
                            AREntry entry = new AREntry();
                            entry.set(formName, entryId);
                            entry.show();
                        }
                        total++;
                    }
                    else if (start) {
                        lst.add(tokens);
                    }
                }
                else
                    System.out.println("!Check :" + tokens.length + " @" + line);
            }
            if (start) {
                processEntryOP(OP, formName, entryId, lst, doIt);
                lst.clear();
            }
            fileReader.close();
            if (total == 0)
                System.out.println("No entry found in " + fileName );
            else 
                System.out.println("\nFile " + fileName + " contains (" + total + ") entry records");
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }       
    }
    /**
	 * usage - show help informatione. 
	 * @param None
     * @return None.
	 */   
    static private void usage() {
        System.out.println("Usage: java RunJavaAPI {TASKNUM}  {param1}  [param2]");
        System.out.println("  TASKNUM:");
        System.out.println("    1   - export a form to file     {formName}");
        System.out.println("    2   - import a form from file   {fileName}");
        System.out.println("    3   - show a form information   {formName}");
        System.out.println("    4   - show field[s] information {formName}  [fiedID]");
        System.out.println("    5   - remove a form on server   {formName}");
        System.out.println("    6   - show form entries info    {formNane}  [qString]");
        System.out.println("    7   - save entries to file      {formName}  [qString]");
        System.out.println("    8   - verify entries in file    {fileName}");
        System.out.println("    9   - operate entries from file {fileName}");
    }
    /**
	 * run - run JavaAPI Utility. 
     * @param int witch utility
	 * @param String param1
     * @param String param2
     * @return None.
	 */    
    private void run(int which, String param1, String param2) {
        switch(which) {
            case 1:
                expFormToFile(param1);
                break;
            case 2:
                impFormFromFile(param1);
                break;
            case 3:
                showFormInfo(param1);
                break;
            case 4:
                showFieldInfo(param1, param2);
                break;
            case 5:
                delFormOnServer(param1);
                break;
            case 6:
                showFormEntries(param1, param2);
                break;
            case 7:
                saveEntriesToFile(param1, param2);
                break;
            case 8:
                operateEntriesFromFile(param1, false);
                break; 
            case 9:
                operateEntriesFromFile(param1, true);
                break;          
            default:
                usage();
                break;
        }
    }
    /**
     * main - Main utility program entry
     * @param String[] agrs
     * @return None
     */   
    static public void main(String[] args) {
        int param0 = 0;
        String param1=null;
        String param2 = null;
        if (args.length > 0) {
            try {
            param0 = Integer.parseInt(args[0]);
            }
            catch(Exception e){}
        }
        if (args.length > 1) {
            param1 = args[1];
            if (args.length > 2) {
                param2 = args[2];
                if (args.length > 3)
                    RunJavaAPI.setNoAsk(true);
            }
        }
        if (param0 == 0 || param1 == null)
            usage();
        else {
            new RunJavaAPI().run(param0, param1, param2);
        }
    }
}