package daos;
import java.sql.*;
import java.util.*;
import java.util.Date;

import objs.*;

public class PropertyDAO{
    private Database database;

    public PropertyDAO(Database database){
        this.database = database;
    }

    public Property convertProperty(ResultSet rs) {
        Property property= new Property();
        try {
            if (rs.next()) {
                property= new Property();
                property.setPropertyID(rs.getInt("propertyID"));
                property.setPropertyName(rs.getString("propertyName"));
                property.setPropertyDesc(rs.getString("propertyDescription"));
                property.setFilter(rs.getBoolean("isFilter"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return property;
    }

    public List<Property> convertProperties(ResultSet rs) {
        List<Property> properties = new ArrayList<Property>();
        try {
            while (rs.next()) {
                Property property= new Property();
                property.setPropertyID(rs.getInt("propertyID"));
                property.setPropertyName(rs.getString("propertyName"));
                property.setPropertyDesc(rs.getString("propertyDescription"));
                property.setFilter(rs.getBoolean("isFilter"));
                property.setValue(rs.getString("propertyValue"));
                properties.add(property);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public List<Property> loadProperties(ResultSet rs) {
        List<Property> properties = new ArrayList<Property>();
        try {
            while (rs.next()) {
                Property property= new Property();
                property.setPropertyID(rs.getInt("propertyID"));
                property.setPropertyName(rs.getString("propertyName"));
                property.setPropertyDesc(rs.getString("propertyDescription"));
                property.setFilter(rs.getBoolean("isFilter"));
                property.setValue(rs.getString("propertyValue"));
                properties.add(property);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return properties;
    }

    // INCOMPLETE
    public List<Property> getItemProperties(int itemID) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Property> properties = new ArrayList<Property>();

        try {
            connection = database.getConnection();
            statement = connection.prepareStatement("SELECT propertyID, propertyName, propertyValue, propertyDescription, isFilter FROM V_fullItemDetails WHERE itemID = ?");
            statement.setInt(1, itemID);
            rs = statement.executeQuery();
            properties = convertProperties(rs);
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
            if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
            if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
        }
        if(properties.isEmpty()){
            return properties;
        }
        return properties;
    }

    // Returns all the possible item properties
    public List<Property> listAll() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Property> properties = new ArrayList<Property>();

        try {
            connection = database.getConnection();
            statement = connection.prepareStatement("SELECT * FROM T_itemProperty");
            rs = statement.executeQuery();
            while(rs.next()){
                Property property = new Property();
                property.setPropertyID(rs.getInt("propertyID"));
                property.setPropertyName(rs.getString("propertyName"));
                property.setPropertyDesc(rs.getString("propertyDescription"));
                property.setFilter(rs.getBoolean("isFilter"));
                properties.add(property);
            }
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
            if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
            if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
        }
        if(properties.isEmpty()){
            return properties;
        }
        return properties;
    }

    // UPDATES THE PROPERTIES OF AN ITEMID GIVEN A LIST OF PROPERTIES TO UPDATE
    public void updateProperties(int itemID, List<Property> properties) throws ClassNotFoundException, SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = database.getConnection();
            statement = connection.prepareStatement("UPDATE T_itemPropertyMap SET propertyValue = ? WHERE itemID = ? AND propertyID = ?");
            for(Property property : properties){
                statement.setString(1, property.getValue());
                statement.setInt(2, itemID);
                statement.setInt(3, property.getPropertyID());
                statement.addBatch();
            }
            statement.executeBatch();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
            if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
            if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
        }
    }

    public void insertProperties(int itemID, List<Property> properties) throws ClassNotFoundException, SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = database.getConnection();
            statement = connection.prepareStatement("INSERT INTO T_itemPropertyMap VALUES(?,?,?)");
            for(Property property : properties){
                statement.setInt(1, property.getPropertyID());
                statement.setInt(2, itemID);
                statement.setString(3, property.getValue());
                statement.addBatch();
            }
            statement.executeBatch();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
            if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
            if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
        }
    }
}
