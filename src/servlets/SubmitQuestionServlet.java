import daos.AuctionDAO;
import daos.BidDAO;
import daos.QuestionDAO;
import objs.Auction;
import objs.Database;
import objs.Message;
import objs.Property;
import objs.Question;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "SubmitQuestionServlet")
public class SubmitQuestionServlet extends HttpServlet {

    private QuestionDAO questionDAO;

    public void init() throws ServletException {
        String driver = "com.mysql.jdbc.Driver";
        String url = "url";
        String username = "username";
        String password = "password";
        Database database = new Database(driver, url, username, password);
        this.questionDAO = new QuestionDAO(database);
    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(true);
        response.setContentType("text/html");
        boolean error = false;

        Question q = new Question();
        try {
        	
            int userID = (Integer) session.getAttribute("userID");
            String isCSR = request.getParameter("isCSR");
            String type = request.getParameter("type");
            String textType = new String();
            
            if(isCSR!=null && isCSR.equals("false")) {
            	String questionText = request.getParameter("questionText");
            	if(questionText!=null && questionText.equals(""))
            	{
            		 request.setAttribute("error", "Empty field(s).");
                     request.getRequestDispatcher("questionBoard.jsp").include(request, response);
            	} else {
            		textType = "q";
                    q.setUserID(userID);
                    q.setQuestionText(questionText);
                    q.setTextType(textType);
                    questionDAO.createQuestion(q);
                    request.setAttribute("success", "Question posted. Show all to refresh.");
                    request.getRequestDispatcher("questionBoard.jsp").forward(request, response);
            	}
            } else if(isCSR.equals("true")) {
                if(type.equals("question")){
                    String questionText = request.getParameter("questionText");
                    if(questionText!=null && questionText.equals(""))
                    {
                        request.setAttribute("error", "Empty field(s).");
                        request.getRequestDispatcher("questionBoard.jsp").include(request, response);
                    } else {
                        textType = "q";
                        q.setUserID(userID);
                        q.setQuestionText(questionText);
                        q.setTextType(textType);
                        questionDAO.createQuestion(q);
                        request.setAttribute("success", "Question posted. Show all to refresh.");
                        request.getRequestDispatcher("questionBoard.jsp").forward(request, response);
                    }
                }
                if(type.equals("answer")){
                    int questionID = Integer.parseInt(request.getParameter("questionID"));

                    q = questionDAO.retrieve(questionID);
                    String answerText = request.getParameter("answerText");
                    if (answerText.equals("")) {
                        request.setAttribute("error", "Empty field(s).");
                        request.getRequestDispatcher("questionBoard.jsp").include(request, response);
                    } else {
                        textType = "a";
                        //q.setUserID(userID);
                        q.setAnswerText(answerText);
                        q.setTextType(textType);
                        questionDAO.createAnswer(q);
                        request.setAttribute("success", "Answer posted. Show all to refresh.");
                        request.getRequestDispatcher("questionBoard.jsp").forward(request, response);
                    }
                }
            }

        }   catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
    }


}
