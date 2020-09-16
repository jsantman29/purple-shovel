import daos.*;
import objs.Auction;
import objs.Bid;
import objs.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.sql.*;
import java.util.*;
import java.util.Date;

import objs.*;

@WebServlet(name = "SalesReportServlet")

public class SalesReportServlet extends HttpServlet {
    private AuctionDAO auctionDAO;
    private SaleDAO saleDAO;
    private Database database;

    public void init() throws ServletException {
        String driver = "com.mysql.jdbc.Driver";
        String url = "url";
        String username = "username";
        String password = "password";
        Database database = new Database(driver, url, username, password);
        this.auctionDAO = new AuctionDAO(database);
        this.saleDAO = new SaleDAO(database);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		response.setContentType("text/html");
		String reportType = request.getParameter("reportType");
		String parameter = "";

		try {
		    List<Sale> sales = new ArrayList<Sale>();
		    float total = 0.00f;

			if(reportType!=null){
			    if(reportType.equals("item")){
			        parameter = request.getParameter("catID");
			        if(parameter==null || parameter.equals("")){
                        request.setAttribute("message", "Please select an item for this report.");
                        request.getRequestDispatcher("reportForm.jsp").include(request,response);
                        return;
                    }
                }
                if(reportType.equals("itemType")){
                    parameter = request.getParameter("parentCatID");
                    if(parameter==null || parameter.equals("")){
                        request.setAttribute("message", "Please select an item type for this report.");
                        request.getRequestDispatcher("reportForm.jsp").include(request,response);
                        return;
                    }
                }
                if(reportType.equals("user")){
                    parameter = request.getParameter("username");
                    if(parameter==null || parameter.equals("")){
                        request.setAttribute("message", "Please enter a username for this report.");
                        request.getRequestDispatcher("reportForm.jsp").include(request,response);
                        return;
                    }
                }
                sales = saleDAO.listSales(reportType, parameter);
                total = saleDAO.netEarnings(reportType,parameter);
                if(sales.isEmpty()||total==0){
                    request.setAttribute("message", "No sales for this report.");
                    request.getRequestDispatcher("reportForm.jsp").include(request,response);
                }
                else {
                    request.setAttribute("sales", sales);
                    request.setAttribute("total", total);
                    request.getRequestDispatcher("report.jsp").include(request, response);
                }
            }else{
                request.setAttribute("message", "Please select a type of report to generate.");
                request.getRequestDispatcher("reportForm.jsp").include(request,response);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

}