
<%@ page import="java.util.*,user.*" %>

<%   
    String v = request.getParameter("q");
    String[] forms = {"Disp", "View", "Vendor", "Join", "Regular", "ALL"};
    int n = 0;
    while(n < forms.length) {
        if (forms[n].equals(v))
            break;
        n++;
    }
    
    // JavaAPI
    ARQuery query = new ARQuery();
    List<String> lList = query.getListForm(n);
    
    out.println("[");
    for (int i = 0; i < lList.size(); i++)
        if (i == 0)
            out.println('"' + lList.get(i) + '"');
        else       
            out.println( ",\"" + lList.get(i) + '"');
    out.println("]");
%>
