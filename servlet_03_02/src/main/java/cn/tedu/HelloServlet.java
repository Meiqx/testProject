package cn.tedu;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.context.Context;

/**
 * Servlet implementation class HelloServlet
 */
public class HelloServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = "��ʦ��";
		Context context = new Context();
		context.setVariable("msg", name);
		
		ThUtils.write("hello", response, context);
	}
}
