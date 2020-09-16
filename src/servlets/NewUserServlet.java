import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import daos.*;
import objs.*;

@WebServlet(name = "NewUserServlet")

public class NewUserServlet extends HttpServlet {
	private UserDAO userDAO;
	
	public void init() throws ServletException {
        String driver = "com.mysql.jdbc.Driver";
        String url = "url";
        String username = "username";
        String password = "password";
        Database database = new Database(driver, url, username, password);
        this.userDAO = new UserDAO(database);
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  

		HttpSession session = request.getSession(true);
		response.setContentType("text/html");  
          
		String n=request.getParameter("username");  
		String p=request.getParameter("password");
		String em=request.getParameter("email");
		boolean isAdmin = false;
		boolean isCSR = false;

		String csr = request.getParameter("isCSR");

		if(csr!=null && csr.equals("on")){
			isCSR = true;
		}

		try {

			if( n.equals("") || p.equals("") || em.equals("")) {
				request.setAttribute("message", "Sorry, but all fields must be filled to create a new account.");
				request.getRequestDispatcher("newUser.jsp").include(request, response);
			}

			else if (userDAO.createUser(n, p, em, isAdmin, isCSR)) {
				request.setAttribute("message", "Congratulation! Your new account is created! Please login with your new credentials.");
				request.getRequestDispatcher("./").include(request, response);
				
			} else {
				request.setAttribute("message", "Sorry, but the username or email you entered has been used.");
				request.getRequestDispatcher("newUser.jsp").include(request, response);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }

}