package objs;

import java.util.Date;

public class Sale {
    private String buyerName;
    private String sellerName;
    private float amount; // dollar sales
    private int count; // amount of sales
    private String itemName;
    private String itemType; // PARENT CATEGORY
    private String item; // CHILD CATEGORY
    private Date saleTimestamp;
    private int saleID;

    public int getCount(){return count;}
    public void setCount(int count){this.count=count;}

    public String getBuyerName(){ return buyerName;}
    public void setBuyerName(String buyerName){this.buyerName = buyerName;}

    public String getSellerName(){return sellerName;}
    public void setSellerName(String sellerName){this.sellerName = sellerName;}

    public float getAmount(){return amount;}
    public void setAmount(float amount){this.amount = amount;}

    public String getItemName(){return itemName;}
    public void setItemName(String itemName){this.itemName = itemName;}

    public String getItemType(){return itemType;}
    public void setItemType(String itemType){this.itemType = itemType;}

    public String getItem(){return item;}
    public void setItem(String item){this.item = item;}

    public Date getSaleTimestamp(){return saleTimestamp;}
    public void setSaleTimestamp(Date saleTimestamp){this.saleTimestamp = saleTimestamp;}

    public int getSaleID(){return saleID;}
    public void setSaleID(int saleID){this.saleID = saleID;}
}

