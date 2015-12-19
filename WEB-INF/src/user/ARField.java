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
 * ARField class is an ARServer Field container class to hide the complexity on use
 * 1. simplfy the acess Field with flexible ways
 * 2. create various type of Field and set values for common using
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
public class ARField {
    private Field fld;
    
	/**
	 * buildField - create a Field object by type method. 
	 * @param int type.
	 * @return Field.
	 */	 
    public static Field buildField(int type) {
        switch(type) {
            case Constants.AR_DATA_TYPE_INTEGER:
                return new IntegerField();
            case Constants.AR_DATA_TYPE_REAL:
                return new RealField();
            case Constants.AR_DATA_TYPE_DIARY:
                return new DiaryField();
            case Constants.AR_DATA_TYPE_ENUM:
                return new SelectionField();
            case Constants.AR_DATA_TYPE_TIME:
                return new DateTimeField();
            case Constants.AR_DATA_TYPE_DECIMAL:
                return new DecimalField();
            case Constants.AR_DATA_TYPE_ATTACH:
                return new AttachmentField();
            case Constants.AR_DATA_TYPE_CURRENCY:
                return new CurrencyField();
            case Constants.AR_DATA_TYPE_DATE:
                return new DateOnlyField();
            case Constants.AR_DATA_TYPE_TIME_OF_DAY:
                return new TimeOnlyField();
            case Constants.AR_DATA_TYPE_TABLE:
                return new TableField();
            case Constants.AR_DATA_TYPE_COLUMN:
                return new ColumnField();
            case Constants.AR_DATA_TYPE_VIEW:
                return new ViewField();
            case Constants.AR_DATA_TYPE_DISPLAY:
                return new DisplayField();
            case Constants.AR_DATA_TYPE_CHAR:
            default:
                return new CharacterField();
        }
    }
    /**
	 * ARField - constructor. 
	 * @param None.
	 */
    public ARField() {
        this(Constants.AR_DATA_TYPE_CHAR);
    }
    /**
	 * ARField - constructor. 
	 * @param int type.
	 */
    public ARField(int type) {
        fld = buildField(type);
    } 
    /**
	 * ARField - constructor. 
	 * @param Field object.
	 */
    public ARField(Field field) {
        set(field);
    }
    /**
	 * set - set Field method. 
	 * @param Field object.
	 * @return None.
	 */
    public void set(Field field) {
        fld = field;
    }
   /**
	 * get - get Field method. 
	 * @param None.
	 * @return Field.
	 */
    public Field get() {
        return fld;
    }
   /**
	 * setup - setup Field method. 
	 * @param String form name.
	 * @param String field name.
	 * @param int field ID.
	 * @param boolean reservedIDOK.
     * @param int create mode. Open or Protected at create (1 or 2) (1)
	 * @return None.
	 */
    public void setup(String form, String name, int id, boolean f, int mode) {
        fld.setForm(form);
        fld.setName(name);
        fld.setReservedIDOK(f);
        fld.setFieldID(id);
        fld.setCreateMode(mode);
    }
   /**
	 * setup - setup Field option method. 
	 * @param int field option. Required, Optional, System, or Display-only (1-4) (2)
	 * @param String owner name.
	 * @param String help text.
	 * @return None.
	 */
    public void setupOption(int option, String owner, String helptxt) {
        fld.setFieldOption(option);
        fld.setOwner(owner);
        fld.setHelpText(helptxt); 
 //     fld.setFieldMap(new RegularFieldMapping());
    }

// Set field option
/*
    
        switch (fieldType) {
            case Constants.AR_FIELD_NONE:
                return null;
            case Constants.AR_FIELD_REGULAR:
                return new RegularFieldMapping();
            case Constants.AR_FIELD_JOIN:
                int index = getInt("   form index (0): ", 0);
                int field = getInt("   real Id (0): ", 0);

                return new JoinFieldMapping(index, field);
            case Constants.AR_FIELD_VIEW:
                return new ViewFieldMapping(getString("Field Name", ""));
            case Constants.AR_FIELD_VENDOR:
                return new VendorFieldMapping(getString("Field Name", ""));
            default:
                return null;
        }

            int auditOption = 0;
            //InputReader.getInt("Field option for Audit/Copy (0): ", 0);
            field.setAuditOption(auditOption);

            int type = field.getDataType();
            if (InputReader.getBooleanForChangingInfo("Have new default value? (F): ", false)) {
                Value val = InputReader.getValue(type);
                field.setDefaultValue(val);
            }

            // Set permissions
            List<PermissionInfo> permissions = InputReader.getPermissionInfoList(true);
            field.setPermissions(permissions);

            // Set limits
            if (InputReader.getBooleanForChangingInfo("Have field limits? (T): ", true)) {
                FieldLimit limitInfo = InputReader.getFieldLimitInfo(type);
                field.setFieldLimit(limitInfo);
            }

            // Set DisplayInstanceList
            DisplayInstanceMap dispInstanceList = InputReader.getDisplayInstanceMap();
            field.setDisplayInstance(dispInstanceList);

            // Set Change DiaryList
            String diary = InputReader.getString("DiaryList: ", "");
            field.appendDiaryText(diary);

            // Set the field mapping structure
            FieldMapping mapInfo = InputReader.getFieldMappingInfo();
            field.setFieldMap(mapInfo);

            // Set Object properties
            ObjectPropertyMap propList = InputReader.getObjectPropertyMap();
            field.setObjectProperty(propList);
*/
   /**
	 * showEnum - display Field Enum information method. 
	 * @param None.
	 * @return None.
	 */
    public void showEnum() {
        SelectionFieldLimit sFieldLimit = (SelectionFieldLimit)fld.getFieldLimit();  
        if (sFieldLimit != null) {  
            System.out.println("--------Enum Information-------");
            List<EnumItem> eItemList = sFieldLimit.getValues();  
            for (EnumItem eItem : eItemList) { 
                System.out.print( eItem.getEnumItemNumber());
                System.out.println( ",\t" + eItem.getEnumItemName());  
            }  
        }  
    }
   /**
	 * show - display Field information method. 
	 * @param None.
	 * @return None.
	 */
    public void show() {
        Map<String, Object> hmap = JavaAPI.parseToString(fld.toString());
        hmap.put("Last Update Time", String.valueOf(fld.getLastUpdateTime().toDate()));
        System.out.println("----------Field Information----------");
        Iterator iter = hmap.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry mentry = (Map.Entry)iter.next();
            System.out.println(mentry.getKey() + ": " + mentry.getValue());
        }
        if (fld instanceof SelectionField) 
            showEnum();
    }
    // test program
    // check in TestJavaAPI.java

}
