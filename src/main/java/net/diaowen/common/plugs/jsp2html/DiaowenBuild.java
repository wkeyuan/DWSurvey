package net.diaowen.common.plugs.jsp2html;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DiaowenBuild extends HttpServlet {

	private static final long serialVersionUID = -6169393681734349288L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext sc = getServletContext();
		new DWSurveyBuild().build(request, response, sc);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("<p align=center><font size=3 color=red>Andrew</font></p>");
	}

}
