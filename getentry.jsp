
<%@ page import="java.util.*,user.*,com.bmc.arsys.api.*" %>

<%   
    String form = request.getParameter("q");
    String entryId = request.getParameter("id");
   
    // JavaAPI
    ARQuery query = new ARQuery();
    
    if (entryId != null) {
        out.println("{ \"Entry\" :\"" + entryId + "\", ");   
        List<String[]> lList = query.getEntryFields(form,  entryId);
        out.println("\"Number\" : \"" + lList.size() + "\",");
        out.println("\"Value\" : [ ");
        int i = 0;
        for (String[] lst: lList) {
            if (i > 0) out.println(", ");
            else i = 1;
            out.print("[ \"" + lst[0]);
            String value = lst[1];
            if (value == null) value = "null";
            else {
                StringBuffer sf = new StringBuffer(value);
                for(int j = 0; j<value.length(); j++)
                     if ( sf.charAt(j) < ' ' ) sf.setCharAt(j, ' ');
                value = sf.toString();
            }
            out.print("\", \"" + value.replace("'","").replace("\"",""));
            out.print("\", \"" + lst[2]);
            out.print("\", \"" + lst[3] + "\"] ");
        }
        out.println("]");
        out.println("}");
    }

%>
