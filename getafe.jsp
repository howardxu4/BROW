
<%@ page import="java.net.URLDecoder.*,java.util.*,user.*,com.bmc.arsys.api.*" %>

<%   
    String name = request.getParameter("q");
    String id = request.getParameter("id");
    int f = 0;
    if (id != null)
        if (id.equals("2")) f=1;
        else if (id.equals("3")) f=2;
   
    // JavaAPI
    ARQuery query = new ARQuery();

    out.println("{ ");
    try {
        String [] names = {"ActiveLink", "Filter", "Escalation"};
        Map<String, Object> hmap;
        if (f == 1) 
            hmap = query.ctx.parseToString( query.ctx.getFilter(name).toString());
        else if (f == 2) 
            hmap = query.ctx.parseToString( query.ctx.getEscalation(name).toString());
        else 
            hmap = query.ctx.parseToString( query.ctx.getActiveLink(name).toString());
    
        out.println("\"Select\": \"" + names[f] + "\",");
    
        out.println("\"Entry\" :\"" + name + "\", "); 
        out.println("\"Number\" : \"" + hmap.size() + "\",");
        out.println("\"Value\" : [ ");
        boolean otherline = false;
        Iterator iter = hmap.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry mentry = (Map.Entry)iter.next();
            if (otherline) out.println(", ");
            else otherline = true;
            out.print("[ \"" + mentry.getKey() + "\", \"" + ((String)mentry.getValue()).replaceAll("\"", "'") + "\"]");
        }
        out.println("]");
        
    } catch (Exception e) {
        out.println("\"Error\"  : \""  + name + "\"");
    }
    out.println("}");
%>
