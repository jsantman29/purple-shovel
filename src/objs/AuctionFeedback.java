package objs;
// T_itemFeedback table

public class AuctionFeedback extends Auction{
	
	private int itemFeedbackID; 
	private int userID; 
	private String questionText; 
	private String answerText;
    // inherits itemID and sellerID of Auction	
	
	public int getItemFeedbackID(){ return itemFeedbackID; }
	public void setItemFeedbackID(int itemFeedbackID){ this.itemFeedbackID = itemFeedbackID; }
	
	public int getUserID(){ return userID; }
	public void setUserID(int userID){ this.userID = userID; }
	
	public String getQuestionText(){ return questionText; }
	public void setQuestionText(String questionText){ this.questionText = questionText;}
	
	public String getAnswerText(){ return answerText;}
	public void setAnswerText(String answerText){ this.answerText = answerText;}
	
}
	

	
	