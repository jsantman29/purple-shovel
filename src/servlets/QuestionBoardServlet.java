import daos.AuctionDAO;
import daos.QuestionDAO;
import daos.QuestionDAO;
import objs.Auction;
import objs.Database;
import objs.Message;
import objs.Question;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "QuestionBoardServlet")
public class QuestionBoardServlet extends HttpServlet {

    private QuestionDAO questionDAO;

    public void init() throws ServletException {
        String driver = "com.mysql.jdbc.Driver";
        String url = "url";
        String username = "username";
        String password = "password";
        Database database = new Database(driver, url, username, password);
        this.questionDAO = new QuestionDAO(database);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        try {
            // get the data from the database
            List<Question> questions = questionDAO.listAllQuestions();

            if(questions.isEmpty()){
                request.setAttribute("error", "No questions found. :(");
                request.getRequestDispatcher("questionBoard.jsp").include(request, response);
            } else {
                // here we need to make sure the "messageList" matches the front end list name
                request.setAttribute("questionList", questions);
                request.getRequestDispatcher("questionBoard.jsp").include(request, response);
            }

            // test

        } catch (SQLException e) {
            throw new ServletException("Cannot retrieve areas", e);
        }
    }


}
