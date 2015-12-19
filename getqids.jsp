
<%@ page import="java.net.URLDecoder.*,java.util.*,user.*,com.bmc.arsys.api.*" %>

<%   
    String form = request.getParameter("q");
    String qstr = request.getParameter("str");
     
    // JavaAPI
    ARQuery query = new ARQuery();
    if (qstr != null && qstr != "") {
        query.set( java.net.URLDecoder.decode(qstr, "UTF-8") );
    }
    out.println("{ ");
    try {
        List<String> entryList = query.queryEntryID(form);
        out.println("\"Form\": \"" + form + "\",");

        if (entryList != null) {
            out.println ("\"Number\": \"" + entryList.size() + "\", ");
            out.println("\"Ids\" : [");
            for( int i = 0; i < entryList.size(); i++ ){
                if (i != 0) out.println(",");
                out.println("\"" + entryList.get(i) + "\"");
            }
            out.println("]");
        }
        else {
            out.println("\"Error\": \"Null return from queryEntrysByQual!?\"");
        }
    } catch (Exception e) {
        out.println("\"Error\"  : \""  + qstr + "\"");
    }
    out.println("}");
%>
