import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import daos.*;
import objs.*;

@WebServlet(name = "LogoutServlet")

public class LogoutServlet extends HttpServlet {
	private UserDAO userDAO;
	
	public void init() throws ServletException {
        String driver = "com.mysql.jdbc.Driver";
        String url = "url";
        String username = "username";
        String password = "password";
        Database database = new Database(driver, url, username, password);
        this.userDAO = new UserDAO(database);
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)  
        throws ServletException, IOException {  
  
		response.setContentType("text/html");  
           
		HttpSession session = request.getSession(true);
		session.invalidate();
		request.setAttribute("message", "Successfully logged out!");
		request.getRequestDispatcher("./").forward(request, response);
    }  

}