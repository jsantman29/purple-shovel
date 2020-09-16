package daos;
import java.sql.*;
import java.util.Date;
import objs.*;

public class UserDAO{
    private Database database;

    public UserDAO(Database database){
        this.database = database;
    }
	
	// returns a user object if found in the table, else null
    public User validate(String name,String pass) throws ClassNotFoundException{
        // status[0] = isValid, status[1] = isAdmin, status[2] = isCSR
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
		User user = new User();

        try{
            con = database.getConnection();
            ps = con.prepareStatement("SELECT * FROM T_user WHERE (username=? OR email=?) AND password=?");
            ps.setString(1,name);
            ps.setString(2,name);
            ps.setString(3,pass);

            rs = ps.executeQuery();

			if(rs.next()) {
			    user.setUsername(rs.getString("username"));
                user.setAdmin(rs.getBoolean("isAdmin"));
                user.setAdmin(rs.getBoolean("isCSR"));
                user.setId(rs.getInt("userID"));
                //update last login time
                ps = con.prepareStatement("UPDATE T_user SET lastLogin=? WHERE username=?");
                Date date = new Date();
                java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
                ps.setTimestamp(1, sqlDate);
                ps.setString(2, name);
                ps.executeUpdate();
            }
			else{
			    user = null;
            }

			con.close();
        }catch(Exception e){
            System.out.println(e);
        }
		return user;
    }
	
	// returns false if failed, true if success
	public boolean createUser(String name, String pass, String email, boolean isAdmin, boolean isCSR) throws ClassNotFoundException{
        boolean success = false;
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
		
		try{
            con = database.getConnection();
			ps = con.prepareStatement("SELECT * FROM T_user WHERE username = ?");
			ps.setString(1, name);
			rs = ps.executeQuery();

			if(!rs.next()){
				ps = con.prepareStatement("INSERT INTO T_user (username, password, email, isAdmin, isCSR, createdDate) VALUES (?, ?, ?, ?, ?, ?)");
				//Add parameters of the query. Start with 1, the 0-parameter is the INSERT statement itself
			
				ps.setString(1, name);
				ps.setString(2, pass);
				ps.setString(3, email);
				ps.setBoolean(4, isAdmin);
				ps.setBoolean(5, isCSR);

                Date date = new Date();
                java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());

				ps.setTimestamp(6, sqlDate);
				ps.executeUpdate();
				success = true;
			}

			con.close();
		}catch(Exception e){
            System.out.println(e);
        }
		return success;
	}

	// GETS USERID FOR A GIVEN USERNAME
    public int getUserID(String username) throws ClassNotFoundException{
        boolean success = false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int userID = 0;

        try{
            con = database.getConnection();
            ps = con.prepareStatement("SELECT userID FROM T_user WHERE username = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            rs.next();
            userID = rs.getInt("userID");

            con.close();
        }catch(Exception e){
            System.out.println(e);
        }
        return userID;
    }

    public User retrieve(int userID) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
       User user = new User();

        try {
            connection = database.getConnection();
            statement = connection.prepareStatement("SELECT * FROM T_user WHERE userID = ?");
            statement.setInt(1, userID);
            rs = statement.executeQuery();
            user = convertUser(rs);

        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
            if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
            if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
        }
        return user;
    }

    public User retrieveName(String username) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        User user = new User();

        try {
            connection = database.getConnection();
            statement = connection.prepareStatement("SELECT * FROM T_user WHERE username = ?");
            statement.setString(1, username);
            rs = statement.executeQuery();
            user = convertUser(rs);

        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
            if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
            if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
        }
        return user;
    }

    public User convertUser(ResultSet rs) {
        User user = new User();
        try {
            if (rs.next()) {
                user.setId(rs.getInt("userID"));
                user.setUsername(rs.getString("username"));
                user.setAdmin(rs.getBoolean("isAdmin"));
                user.setCSR(rs.getBoolean("isCSR"));
                user.setLocked(rs.getBoolean("isLocked"));
                user.setPassword(rs.getString("password"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean editUser(User u) throws ClassNotFoundException{
        boolean success = false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            con = database.getConnection();
            ps = con.prepareStatement("UPDATE T_user SET username=?, isAdmin=?, isCSR=?, isLocked=?, password=? WHERE userID=?");

            //Add parameters of the query. Start with 1, the 0-parameter is the INSERT statement itself
            ps.setString(1,u.getUsername());
            ps.setBoolean(2,u.isAdmin());
            ps.setBoolean(3,u.isCSR());
            ps.setBoolean(4,u.isLocked());
            ps.setString(5,u.getPassword());
            ps.setInt(6,u.getId());

            ps.executeUpdate();
            success = true;

            con.close();
        }catch(Exception e){
            System.out.println(e);
        }
        return success;
    }


}