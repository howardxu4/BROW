
<%@ page import="java.net.URLDecoder.*,java.util.*,user.*,com.bmc.arsys.api.*" %>

<%   
    String form = request.getParameter("q");
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
        List <String> lst;
        if (f == 1) 
            lst = query.ctx.getListFilter(form);
        else if (f == 2) 
            lst = query.ctx.getListEscalation(form);
        else 
            lst= query.ctx.getListActiveLink(form);
    
        out.println("\"Form\": \"" + form + "\",");
        out.println("\"Select\": \"" + names[f] + "\",");
    
        if (lst != null) {
            out.println ("\"Number\": \"" + lst.size() + "\", ");
            out.println("\"Ids\" : [");
            boolean otherline = false;
            for( String s: lst ){
                if (otherline) out.println(",");
                else otherline = true;
                out.println("\"" + s + "\"");
            }
            out.println("]");
        }
        else {
            out.println("\"Error\": \"Null return from query " + names[f] + " in Form!?\"");
        }
    } catch (Exception e) {
        out.println("\"Error\"  : \""  + form + "\"");
    }
    out.println("}");
%>
