package objs;

import java.util.ArrayList;
import java.util.List;

public class Property {

	private int propertyID;
	private List<Integer> categoryIds = new ArrayList<Integer>();
	private String propertyName;
	private String propertyDesc;
	private int itemId;
	private String value;
	private boolean filter;

	public int getItemId(){ return itemId;}
	public void setItemId(int itemId){this.itemId = itemId;}

	public int getPropertyID(){ return propertyID;}
	public void setPropertyID(int propertyID){ this.propertyID = propertyID;}

	public String getPropertyName(){ return propertyName;}
	public void setPropertyName(String propertyName){this.propertyName = propertyName;}

    public String getPropertyDesc(){ return propertyDesc;}
    public void setPropertyDesc(String propertyDesc){this.propertyDesc = propertyDesc;}

	public String getValue(){ return value;}
	public void setValue(String value){ this.value = value;}

	public boolean isFilter(){return filter;}
	public void setFilter(boolean filter){this.filter=filter;}

	public List<Integer> getCategoryIds(){ return categoryIds;}
	public void setCategoryIds(List<Integer> categoryIds){this.categoryIds = categoryIds;}

}