<jsp:useBean id="user" class="user.UserData" scope="session"/>
<jsp:setProperty name="user" property="*"/> 
<%@ page import="java.util.*,user.*,com.bmc.arsys.api.*" %>
<%
   String name = request.getParameter( "password" );
   if (name == null || name == "") user.setPassword("");
%>
<HTML>
<BODY>
<%

   Properties prop = new Properties();
   prop.setProperty("username", user.getUsername().trim());
   prop.setProperty("password", user.getPassword().trim());
   prop.setProperty("server", user.getServer().trim());
   prop.setProperty("port", user.getPort());
   JavaAPI server = JavaAPI.getNewInstance(prop);
   out.println("State: " + JavaAPI.getState() + "<BR>");
   out.println("Message: " + JavaAPI.getMessage() + "<BR><BR>");
   List<String> l = server.getARSystemInfo();
   for (String s : l)
        out.println(s + "<BR>");
    out.println("<BR>");
%>
<A HREF="changeserver.jsp">Continue</A> changing or go to
<A HREF="discover.html">Discover</A> server inside

</BODY>
</HTML>