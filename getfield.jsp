
<%@ page import="java.util.*,user.*,com.bmc.arsys.api.*" %>

<%   
    String form = request.getParameter("q");
    String fieldId = request.getParameter("id");
   
    // JavaAPI
    ARQuery query = new ARQuery();
    
    if (fieldId != null) {
        out.print("{ ");
        try {
            Field field = query.ctx.getField(form, Integer.parseInt(fieldId));
            out.println("\"Select\": \"" + form + "\", ");
            out.println("\"Entry\" :\"" + field.getName() + "\", "); 
            Map<String, Object> hmap = query.ctx.parseToString(field.toString());
            out.println("\"Number\" : \"" + hmap.size() + "\",");
            out.println("\"Value\" : [ ");
            boolean f = false;
            Iterator iter = hmap.entrySet().iterator();
            while(iter.hasNext()) {
                Map.Entry mentry = (Map.Entry)iter.next();
                if (f) out.println(", ");
                else f = true;
                out.print("[ \"" + mentry.getKey() + "\", \"" + ((String)mentry.getValue()).replaceAll("\"", "'") + "\"]");
            }
            out.println("]");
        }
        catch(Exception e) {
            out.println(",\"Error\" :\"" + e.getMessage() + "\"");
        }
        out.println("}");
    }

%>
