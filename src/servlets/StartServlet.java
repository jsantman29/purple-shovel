import daos.*;
import objs.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "StartServlet")
public class StartServlet extends HttpServlet {
    private CategoryDAO categoryDAO;
    private PropertyDAO propertyDAO;

    public void init() throws ServletException {
        String driver = "com.mysql.jdbc.Driver";
        String url = "url";
        String username = "username";
        String password = "password";
        Database database = new Database(driver, url, username, password);
        this.categoryDAO = new CategoryDAO(database);
        this.propertyDAO = new PropertyDAO(database);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        HttpSession session = request.getSession(true);

        try {
            List<Category> rootCategories = categoryDAO.init();
            List<Property> availableProperties = propertyDAO.listAll();
            session.setAttribute("categories", rootCategories);
            session.setAttribute("properties", availableProperties);
            session.setAttribute("numberOfProperties", availableProperties.size());
            request.getRequestDispatcher("index.jsp").include(request, response);
        } catch (SQLException e) {
            throw new ServletException("Cannot retrieve areas", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        request.getRequestDispatcher("index.jsp").include(request, response);
    }

}

