package objs;

// T_itemDetails table
import java.util.Date;
import java.util.*;

public class Auction{
	
    private int itemID;
	private String itemName;
	private String itemDescription;
	private int sellerID;
	private int catId;
	private float bidIncrement;
	private float startingBid;
	private float currentBid;
	private float reserveBid;
	private Date start;
	private Date end;
	private boolean open;
	private float minBid = bidIncrement + currentBid;

	public float getMinBid(){ return minBid;}

	public int getItemID(){ return itemID;}
	public void setItemID(int itemID){ this.itemID = itemID;}

	public String getItemName(){ return itemName;}
	public void setItemName(String itemName){ this.itemName = itemName;}
	
	public String getItemDescription(){ return itemDescription;}
	public void setItemDescription(String itemDescription){ this.itemDescription = itemDescription;}
	
	public int getSellerID(){ return sellerID;}
	public void setSellerID(int sellerID){ this.sellerID = sellerID;}

	public int getCatId(){return catId;}
	public void setCatId(int catId){this.catId=catId;}
	
	public float getBidIncrement(){ return bidIncrement; }
	public void setBidIncrement(float bidIncrement){ this.bidIncrement = bidIncrement;}
	
	public float getStartingBid(){ return startingBid; }
	public void setStartingBid(float startingBid){ this.startingBid = startingBid;}
	
	public float getCurrentBid(){ return currentBid; }
	public void setCurrentBid(float currentBid){ this.currentBid = currentBid;}
	
	public float getReserveBid(){ return reserveBid;}
	public void setReserveBid(float reserveBid){ this.reserveBid = reserveBid; }
	
	public Date getStart() { return start;}
	public void setStart(Date start){ this.start = start;}
	
	public Date getEnd() { return end;}
	public void setEnd(Date end){ this.end = end;}

	public boolean isOpen(){return open;}
	public void setOpen(boolean open){this.open = open;}

	public static float getBidIncrement(float currentBid){
		if(currentBid>=0.01&&currentBid<=0.99){
			return 0.05f;
		}
		else if(currentBid>=1.00&&currentBid<=4.99){
			return 0.25f;
		}
		else if(currentBid>=5.00&&currentBid<=24.99){
			return 0.25f;
		}
		else if(currentBid>=25.00&&currentBid<=99.99){
			return 0.50f;
		}
		else if(currentBid>=100.00&&currentBid<=249.99){
			return 2.50f;
		}
		else if(currentBid>=250.00&&currentBid<=499.99){
			return 5.00f;
		}
		else if(currentBid>=500.00&&currentBid<=999.99){
			return 10.00f;
		}
		else if(currentBid>=1000.00&&currentBid<=2499.99){
			return 25.00f;
		}
		else if(currentBid>=2500.00&&currentBid<=4999.99){
			return 50.00f;
		}
		else{
			return 100.00f;
		}
	}
}
