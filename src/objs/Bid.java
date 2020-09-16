package objs;
// T_bidTransactions table
import java.sql.Timestamp;
import java.util.Date;

public class Bid extends Auction{
	// inherits itemID of Auction
	private int invoiceId;
	private int transactionID;
	private int userID;
	private String username;
	private boolean autoBid;
	private float bid;
	private float bidMax;
	private Date bidTimestamp;

	public int getInvoiceId(){return invoiceId;}
	public void setInvoiceId(int invoiceId){this.invoiceId = invoiceId;}

	public int getTransactionID(){ return transactionID;}
	public void setTransactionID(int transactionID){ this.transactionID = transactionID;}
	/**
	public void removeBid(int transactionID) {
		this.transactionID = -1;
	}
	**/
	public String getUsername(){return username;};
	public void setUsername(String username){this.username = username;}

	public int getUserID(){ return userID;}
	public void setUserID(int userID){ this.userID = userID;}
	
	public boolean isAutoBid(){ return autoBid;}
	public void setAutoBid(boolean autoBid){ this.autoBid = autoBid;}
	
	public float getBid(){ return bid;}
	public void setBid(float bid){ this.bid = bid;}
	
	public float getBidMax(){ return bidMax;}
	public void setBidMax(float bidMax){ this.bidMax = bidMax;}
	
	public Date getBidTimestamp(){ return bidTimestamp;}
	public void setBidTimestamp(Date bidTimestamp){ this.bidTimestamp = bidTimestamp;}
}