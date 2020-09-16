package objs;

import java.util.ArrayList;
import java.util.List;

public class Category{

    private int id;
    private int parentId;
    private String name;
    private String desc;
    private ArrayList<Category> children = new ArrayList<Category>();

    public Category(){
    }

    public Category(String name, int id, int parentId, String desc) {
        this.name = name;
        this.id = id;
        this.parentId = parentId;
        this.desc = desc;
    }

    public List<Category> getChildren(){
        return children;
    }

    public void setChildren(ArrayList<Category> children){ this.children = children;}

    public int getParentId(){return parentId;}

    public void setParentId(int parentId){this.parentId = parentId;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category findParent(int parentId){
        if(id == parentId){
            return this;
        }
        for(Category child : children){
            if(child.findParent(parentId) != null) {
                return child;
            }
        }
        return null;
    }

    public void addChild(Category child){
        children.add(child);
    }
}
