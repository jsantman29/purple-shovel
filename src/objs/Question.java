package objs;

//import java.util.Date;

public class Question {
    private int questionID;
    private int userID;
    private String textType;
    //private boolean hasBeenRead;
    private int itemID;
    //private int sellerID;
    private String questionText;
    private String answerText;
    //private Date questionTimestamp;

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }

    
    public String getTextType() {
        return textType;
    }
    public void setTextType(String textType) {
        this.textType = textType;
    }
     
    /**
    public boolean getHasBeenRead() {
        return hasBeenRead;
    }

    public void setHasBeenRead(boolean hasBeenRead) {
        this.hasBeenRead = hasBeenRead;
    }
     **/
    
    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    /**
    public int getSellerID() {
        return sellerID;
    }
    
    public void setSellerID(int sellerID) {
        this.sellerID = sellerID;
    }
     **/
    
    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
    
    /**
    public Date getMessageTimestamp() {
        return questionTimestamp;
    }

    public void setMessageTimestamp(Date questionTimestamp) {
        this.questionTimestamp = questionTimestamp;
    }
    **/
    

    
}
