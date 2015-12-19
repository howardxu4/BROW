
<%@ page import="java.util.*,user.*" %>

<%   
    String form = request.getParameter("q");

    // JavaAPI
    ARQuery query = new ARQuery();

    List<String[]> lList = query.getListFieldsInForm(form);
    out.println("[");
    int i = 0;
    for (String[] lst: lList) {
        if (i > 0)  out.println(",");
        i++;
        out.println("[\"" + lst[0] + "\",\"" + lst[1] +"\",\"" + lst[2] + "\",\"" + lst[3] + "\",\"" + lst[4] + "\"]");
    }
    out.println("]");
%>
