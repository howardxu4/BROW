<%@ page import="java.util.*,user.*,com.bmc.arsys.api.*" %>
<HTML>
<BODY>
<FORM METHOD=POST ACTION="saveserver.jsp">
Change user name? <INPUT TYPE=TEXT NAME=username SIZE=20><BR>
Change password? <INPUT TYPE=PASSWORD NAME=password SIZE=20><BR>
Change server? <INPUT TYPE=TEXT NAME=server SIZE=20><BR>
Change port? <INPUT TYPE=TEXT NAME=port SIZE=20><BR>
<P><INPUT TYPE=SUBMIT>
</FORM>
<%
JavaAPI server = JavaAPI.getInstance();
%>
Current setting are:<BR>
User Name: <%= server.getProperty( "username" ) %><BR>
Password: <%= "******" %><BR>
Server: <%= server.getProperty("server") %><BR>
Port: <%= server.getProperty("port") %><BR>
</BODY>
</HTML>