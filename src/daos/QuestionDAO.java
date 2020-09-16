package daos;

import objs.Auction;
import objs.Database;
import objs.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static objs.StringBuilderHelp.replaceAll;

public class QuestionDAO {
    private Database database;

    public QuestionDAO(Database database){
        this.database = database;
    }

    public Question convertQuestion(ResultSet rs) {
		Question question = new Question();
		try {
			if (rs.next()) {
				question = new Question();

				question.setQuestionID(rs.getInt("itemFeedbackID"));
				question.setUserID(rs.getInt("userID"));
				//question.setTextType(rs.getString("textType"));
				//question.setItemID(rs.getInt("itemID"));
				question.setQuestionText(rs.getString("questionText"));
				question.setAnswerText(rs.getString("answerText"));

			} else {
				return question;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return question;
	}

	public List<Question> convertQuestions(ResultSet rs) {
		List<Question> questions = new ArrayList<Question>();
		try {
			  while(rs.next()) {

				  Question question = new Question();

				  question.setQuestionID(rs.getInt("itemFeedbackID"));
				  question.setUserID(rs.getInt("userID"));
				  //question.setTextType(rs.getString("textType"));
				  //question.setItemID(rs.getInt("itemID"));;
				  question.setQuestionText(rs.getString("questionText"));
				  question.setAnswerText(rs.getString("answerText"));

				  questions.add(question);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return questions;
	}

	public Question retrieve(int questionID) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Question question = new Question();

		try {
			connection = database.getConnection();
			statement = connection.prepareStatement("SELECT * FROM T_qa WHERE itemFeedbackID = ?");
			if(statement == null) {
				return null;
			}
			statement.setInt(1, questionID);
			rs = statement.executeQuery();
			question = convertQuestion(rs);
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
			if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
			if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
		}
		return question;
	}

	
	// returns false if failed, true if success
	public boolean createQuestion(Question a) throws ClassNotFoundException{
        boolean success = false;
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

		try{
            con = database.getConnection();

            
			ps = con.prepareStatement("INSERT INTO T_qa (userID, questionText) VALUES (?, ?)");

            //Add parameters of the query. Start with 1, the 0-parameter is the INSERT statement itself
			ps.setInt(1, a.getUserID());
			ps.setString(2, a.getQuestionText());
			ps.executeUpdate();
			success = true;

			con.close();
		}catch(Exception e){
            System.out.println(e);
        }
		return success;
	}
	
	public boolean createAnswer(Question a) throws ClassNotFoundException{
        boolean success = false;
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

		try{
            con = database.getConnection();

			ps = con.prepareStatement("UPDATE T_qa SET answerText = ? WHERE itemFeedbackID = ?");

            //Add parameters of the query. Start with 1, the 0-parameter is the INSERT statement itself
			ps.setString(1, a.getAnswerText());
			ps.setInt(2, a.getQuestionID());
			
			ps.executeUpdate();
			success = true;

			con.close();
		}catch(Exception e){
            System.out.println(e);
        }
		return success;
	}

	// ALL SEARCH FUNCTIONS //

	// Lists all questions by user
	public List<Question> listAllFromUser(int userID) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<Question> questions = new ArrayList<Question>();

		try {
			connection = database.getConnection();
			statement = connection.prepareStatement("SELECT * FROM V_qaSearch WHERE userID = ?");
			statement.setInt(1, userID);
			rs = statement.executeQuery();
			questions=convertQuestions(rs);
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
			if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
			if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
		}
		if(questions.isEmpty()){
			return questions;
		}
		return questions;
	}
	
	// Lists all questions
	public List<Question> listAllQuestions() throws SQLException {
			Connection connection = null;
			PreparedStatement statement = null;
			ResultSet rs = null;
			List<Question> questions = new ArrayList<Question>();

			try {
				connection = database.getConnection();
				statement = connection.prepareStatement("SELECT * FROM T_qa");
				rs = statement.executeQuery();
				while(rs.next()){
					Question question = new Question();
					question.setQuestionID(rs.getInt("itemFeedbackID"));
					question.setUserID(rs.getInt("userID"));
					question.setQuestionText(rs.getString("questionText"));
					question.setAnswerText(rs.getString("answerText"));
					questions.add(question);
				}
			} finally {
				if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
				if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
				if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
			}
			if(questions.isEmpty()){
				return questions;
			}
			return questions;
		}

	public List<Question> searchQuestions(String type, String parameter) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<Question> questions = new ArrayList<Question>();

		try {
			connection = database.getConnection();
			if(type!=null && type.equals("userID")){
				statement = connection.prepareStatement("SELECT * FROM T_qa WHERE itemFeedbackID IN (SELECT itemFeedbackID FROM V_qaSearch WHERE userID = ?)");
				statement.setInt(1, Integer.parseInt(parameter));
			}
			else if(type!=null && type.equals("questions")){
				statement = connection.prepareStatement("SELECT * FROM T_qa WHERE itemFeedbackID IN (SELECT itemFeedbackID FROM V_qaSearchHelpQ WHERE searchText LIKE ?)");
				statement.setString(1, "%"+parameter+"%");
			}
			else if(type!=null && type.equals("answers")){
				statement = connection.prepareStatement("SELECT * FROM T_qa WHERE itemFeedbackID IN (SELECT itemFeedbackID FROM V_qaSearchHelpA WHERE searchText LIKE ?)");
				statement.setString(1, "%"+parameter+"%");
			}
			else if(type!=null&&type.equals("all")){
				statement = connection.prepareStatement("SELECT * FROM T_qa WHERE itemFeedbackID IN (SELECT itemFeedbackID FROM V_qaSearchHelpQA WHERE searchText LIKE ?)");
				statement.setString(1, "%"+parameter+"%");
			}
			else{
				return questions;
			}
			System.out.println(statement);
			rs = statement.executeQuery();
			while(rs.next()){
				Question question = new Question();
				question.setQuestionID(rs.getInt("itemFeedbackID"));
				question.setUserID(rs.getInt("userID"));
				question.setQuestionText(rs.getString("questionText"));
				question.setAnswerText(rs.getString("answerText"));
				questions.add(question);
			}
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
			if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
			if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
		}
		if(questions.isEmpty()){
			return questions;
		}
		return questions;
	}
}