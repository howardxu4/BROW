
<%@ page import="java.net.URLDecoder.*,java.util.*,user.*,com.bmc.arsys.api.*" %>

<%   
    String form = request.getParameter("q");
     
    // JavaAPI
    ARQuery query = new ARQuery();

    out.println("{ ");
    try {
        List <String[]> fieldList= query.getListFieldsInForm(form);
        out.println("\"Form\": \"" + form + "\",");

        if (fieldList != null) {
            out.println ("\"Number\": \"" + fieldList.size() + "\", ");
            out.println("\"Ids\" : [");
            for( int i = 0; i < fieldList.size(); i++ ){
                if (i != 0) out.println(",");
                out.println("[\"" + fieldList.get(i)[0] + "\", \"" + fieldList.get(i)[4] + "\"]");
            }
            out.println("]");
        }
        else {
            out.println("\"Error\": \"Null return from query Fields in Form!?\"");
        }
    } catch (Exception e) {
        out.println("\"Error\"  : \""  + form + "\"");
    }
    out.println("}");
%>
