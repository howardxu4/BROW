package com.mytest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Scanner;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletDemo extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException{
        String header = doHeader(request);
        String params = doParams(request);
		doResponse(response, "Hello Servlet Get", params, header, "");
	}
    public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException{
        String header = doHeader(request);
        String body = doBody(request);
        String params = doParams(request);
        doResponse(response, "Hello Servlet Post", params, header, body);	
	}
    private String doParams(HttpServletRequest request) 
    throws IOException{
        String params = "";
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            String paramValue = "";
            for (int i = 0; i < paramValues.length; i++) {
                paramValue += paramValues[i] + ",";
            }
            params += paramName + ":" + paramValue;
        }
        return params;
    }
    private String doHeader(HttpServletRequest request)
    throws IOException{
        String header = "";
        Enumeration headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String headerName = (String)headerNames.nextElement();
            header += headerName + " = " + request.getHeader(headerName) + ",";
        }
        return header;
    }
    public static String doBody(HttpServletRequest request) 
    throws IOException {
        Scanner s = null;
        try {
            s = new Scanner(request.getInputStream(), "UTF-8").useDelimiter("\\A");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s.hasNext() ? s.next() : "";
    }
    private void doResponse(HttpServletResponse resp, String msg, 
        String params, String header, String body)throws IOException {
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<h1>" + msg + "</h1>");
        out.println("<h3>Params: " + params + "</h3>");
        out.println("<h3>Header: " + header + "</h3>");
        out.println("<h3>Body: " + body + "</h3>");        
		out.println("</body>");
		out.println("</html>");
	}
}