package daos;
import java.sql.*;
import java.util.*;
import java.util.Date;

import objs.*;

public class CategoryDAO{
    private Database database;

    public CategoryDAO(Database database){
        this.database = database;
    }

    public List<Category> init() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Category> categories = new ArrayList<Category>();
        try {
            connection = database.getConnection();
            String sql = "SELECT * FROM T_itemCategory";
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery(sql);
            while(rs.next()) {
                String catName = rs.getString("catName");
                int catID = rs.getInt("catID");
                int parentCatID = rs.getInt("parentCatID");
                String desc = rs.getString("catDescription");
                Category cat = new Category(catName, catID, parentCatID, desc);
                if(parentCatID!=0){
                    if(parentCatID==1){
                        categories.add(cat);
                    }
                    else{
                        categories.get(parentCatID-2).addChild(cat);
                    }
                }
            }
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
            if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
            if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
        }
        connection.close();
        return categories;
    }

    public String getDescriptionName(int catID) throws ClassNotFoundException, SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String name = null;
        try {
            connection = database.getConnection();
            String sql = "SELECT catName FROM T_itemCategory WHERE catID = ?";
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery(sql);
            if(rs.next()) {
                name = rs.getString("catName");
            }
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
            if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
            if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
        }
        connection.close();
        return name;
    }

}