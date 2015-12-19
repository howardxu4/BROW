
<%@ page import="java.util.*,user.*,com.bmc.arsys.api.*" %>

<%   
    String form = request.getParameter("q");

    // JavaAPI
    ARQuery query = new ARQuery();
   
    Form cform = query.ctx.getForm(form);

    Timestamp tmstmp = cform.getLastUpdateTime();
    
    out.println("Name: " + cform.getName() );
    out.println("&nbsp;&nbsp;&nbsp;&nbsp;    New Name: " + cform.getNewName());
    out.println("&nbsp;&nbsp;&nbsp;&nbsp;    Owner: " + cform.getOwner()+ "<BR>");
    out.println("Last Changed By: " + cform.getLastChangedBy()+ "<BR>");
    out.println("Last Update On: " + tmstmp.toDate().toString()+ "<BR>");
    out.println("Help Text: <textarea>" + cform.getHelpText() + "</textarea>");
    
%>
