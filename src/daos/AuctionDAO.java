package daos;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import objs.*;

import static objs.StringBuilderHelp.replaceAll;

public class AuctionDAO{
    private Database database;

    public AuctionDAO(Database database){
        this.database = database;
    }

    public Auction convertAuction(ResultSet rs) {
		Auction auction = new Auction();
		try {
			if (rs.next()) {
				auction = new Auction();
				auction.setItemID(rs.getInt("itemID"));
				auction.setItemName(rs.getString("itemName"));
				auction.setItemDescription(rs.getString("itemDescription"));
				auction.setCatId(rs.getInt("catID"));
				auction.setSellerID(rs.getInt("sellerID"));
				auction.setBidIncrement(rs.getFloat("bidIncrement"));
				auction.setStartingBid(rs.getFloat("startingBid"));
				auction.setCurrentBid(rs.getFloat("currentBid"));
				auction.setReserveBid(rs.getFloat("reserveBid"));
				auction.setStart(rs.getTimestamp("auctionStart"));
				auction.setEnd(rs.getTimestamp("auctionEnd"));
				auction.setOpen(rs.getBoolean("isOpen"));
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return auction;
	}

	public List<Auction> convertAuctions(ResultSet rs) {
		List<Auction> auctions = new ArrayList<Auction>();
		try {
			  while(rs.next()) {
				Auction auction = new Auction();
				auction.setItemID(rs.getInt("itemID"));
				auction.setItemName(rs.getString("itemName"));
				auction.setItemDescription(rs.getString("itemDescription"));
				auction.setCatId(rs.getInt("catID"));
				auction.setSellerID(rs.getInt("sellerID"));
				auction.setBidIncrement(rs.getFloat("bidIncrement"));
				auction.setStartingBid(rs.getFloat("startingBid"));
				auction.setCurrentBid(rs.getFloat("currentBid"));
				auction.setReserveBid(rs.getFloat("reserveBid"));
				auction.setStart(rs.getTimestamp("auctionStart"));
				auction.setEnd(rs.getTimestamp("auctionEnd"));
				auction.setOpen(rs.getBoolean("isOpen"));
				auctions.add(auction);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return auctions;
	}

	public Auction retrieve(int itemID) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Auction auction = new Auction();

		try {
			connection = database.getConnection();
			statement = connection.prepareStatement("SELECT * FROM T_itemDetails WHERE itemID = ?");
			statement.setInt(1, itemID);
			rs = statement.executeQuery();
			auction = convertAuction(rs);
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
			if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
			if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
		}
		return auction;
	}

	// returns false if failed, true if success
	public boolean createAuction(Auction a) throws ClassNotFoundException{
        boolean success = false;
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
		
		try{
            con = database.getConnection();

            ps = con.prepareStatement("INSERT INTO T_itemDetails (itemName, itemDescription, catID, sellerID, bidIncrement, startingBid, currentBid, reserveBid, auctionStart, auctionEnd, isOpen) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)");

            //Add parameters of the query. Start with 1, the 0-parameter is the INSERT statement itself
            ps.setString(1, a.getItemName());
            ps.setString(2, a.getItemDescription());
            ps.setInt(3, a.getCatId());
            ps.setInt(4, a.getSellerID());
            ps.setFloat(5, a.getBidIncrement());
            ps.setFloat(6, a.getStartingBid());
            ps.setFloat(7, a.getCurrentBid());
            ps.setFloat(8, a.getReserveBid());

            // converts Java date to SQL date
            java.sql.Timestamp sqlDate = new java.sql.Timestamp(a.getEnd().getTime());
            ps.setTimestamp(10, sqlDate);
            Date date = new Date();
            sqlDate = new java.sql.Timestamp(date.getTime());
            ps.setTimestamp(9, sqlDate);
            ps.setBoolean(11, true);
            ps.executeUpdate();
            success = true;

			con.close();
		}catch(Exception e){
            System.out.println(e);
        }
		return success;
	}

	// returns false if failed, true if success
	public void deleteAuction(int itemID) throws ClassNotFoundException{
		boolean success = false;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try{
			con = database.getConnection();

			ps = con.prepareStatement("DELETE FROM T_itemDetails WHERE itemID = ?");

			//Add parameters of the query. Start with 1, the 0-parameter is the INSERT statement itself
			ps.setInt(1, itemID);
			ps.executeUpdate();

			con.close();
		}catch(Exception e){
			System.out.println(e);
		}
	}

	// ALL SEARCH FUNCTIONS //

	// Lists all auctions
	public List<Auction> listAll() throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<Auction> auctions = new ArrayList<Auction>();

		try {
			connection = database.getConnection();
			statement = connection.prepareStatement("SELECT * FROM T_itemDetails");
			rs = statement.executeQuery();
			auctions=convertAuctions(rs);
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
			if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
			if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
		}
		if(auctions.isEmpty()){
			return null;
		}
		return auctions;
	}

	public List<Auction> keywordSearch(String field, String parentCatID,String catID, String minBid, String maxBid, String opendate, String opentime, String enddate, String endtime, String sortType, String order, List<Property> itemProperties) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<Auction> auctions = new ArrayList<Auction>();
		StringBuilder select = new StringBuilder();
		StringBuilder filtered = new StringBuilder();
		String opend = null;
		String endd = null;
		select.append("SELECT * FROM T_itemDetails WHERE itemID IN (?)");

		try {
			connection = database.getConnection();

			filtered.append("SELECT DISTINCT itemID FROM V_fullItemDetails ");
			filtered.append("WHERE 1=1");
			// if keyword search implemented
			if(field!=null){
				filtered.append(" AND (itemName LIKE ? OR itemDescription LIKE ? OR catName LIKE ? OR catDescription LIKE ? OR parentCatName LIKE ? OR parentCatDescription LIKE ? OR propertyValue LIKE ?)");
				replaceAll(filtered,"\\?", "\"%"+field+"%\"");
			}
			if(parentCatID!=null){
				filtered.append(" AND (parentCatID = ?)");
				replaceAll(filtered, "\\?", parentCatID);
			}
			if(catID!=null){
				filtered.append(" AND (catID = ?)");
				replaceAll(filtered, "\\?", catID);
			}
			if(minBid!=null && !minBid.equals("")){
				filtered.append(" AND (minBid >= ?)");
				replaceAll(filtered, "\\?", minBid);
			}
			if(maxBid!=null && !maxBid.equals("")){
				filtered.append(" AND (maxBid <= ?)");
				replaceAll(filtered, "\\?", maxBid);
			}
			if(opendate!=null && !opendate.equals("")){
				if(opentime!=null){
					opend = opendate + " " + "00:00";
				}
				else{
					opend = opendate + " " + opentime;
				}
				filtered.append(" AND (auctionStart >= '?')");
				replaceAll(filtered, "\\?", opend);
			}
			if(enddate!=null&&!enddate.equals("")){
				if(endtime!=null){
					endd = enddate + " " + "00:00";
				}
				else{
					endd = enddate + " " + endtime;
				}
				filtered.append(" AND (auctionStart >= '?')");
				replaceAll(filtered, "\\?", endd);
			}
			if(!itemProperties.isEmpty()){
				for(Property property : itemProperties){
					filtered.append(" AND (propertyID =");
					filtered.append(property.getPropertyID());
					filtered.append(" AND propertyValue = '?')");
					replaceAll(filtered, "\\?", property.getValue());
				}
			}
			String filters = filtered.toString();
			replaceAll(select, "\\?", filters);

			if(sortType!=null){
				select.append(" ORDER BY ");
				select.append(sortType);
				if(order.equals("ASC")){
					select.append(" ASC");
				}
				else{
					select.append(" DESC");
				}
			}

			String sql = select.toString();
			//System.out.println(sql);
			statement = connection.prepareStatement(sql);
			rs = statement.executeQuery();
			auctions=convertAuctions(rs);
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
			if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
			if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
		}
		return auctions;
	}

	public void saveSearch(String userID, String alertName, String field, String parentCatID, String catID, String minBid, String maxBid, String opendate, String opentime, String enddate, String endtime) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String opend = null;
		String endd = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");

		try {
			connection = database.getConnection();
			ps = connection.prepareStatement("INSERT INTO T_savedSearches (userID, searchName, keyword, parentCatID, catID, minBid, maxBid, startTime, endTime) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setInt(1, Integer.parseInt(userID));
			ps.setString(2, alertName);

			Date date = null;
			java.sql.Timestamp sqlDate = null;

			if(field!=null){
				ps.setString(3, field);
			}
			else{
				ps.setString(3, null);
			}
			if(parentCatID!=null){
				ps.setInt(4, Integer.parseInt(parentCatID));
			}
			else{
				ps.setString(4, null);
			}
			if(catID!=null){
				ps.setInt(5, Integer.parseInt(catID));
			}
			else{
				ps.setString(5, null);
			}
			if(minBid!=null && !minBid.equals("")){
				ps.setFloat(6, Float.parseFloat(minBid));
			}
			else{
				ps.setString(6, null);
			}
			if(maxBid!=null && !maxBid.equals("")){
				ps.setFloat(7, Float.parseFloat(maxBid));
			}
			else{
				ps.setString(7, null);
			}
			if(opendate!=null && !opendate.equals("")){
				if(opentime!=null){
					opend = opendate + " " + "00:00";
				}
				else{
					opend = opendate + " " + opentime;
				}
				date = sdf.parse(opend);
				sqlDate = new java.sql.Timestamp(date.getTime());
				ps.setTimestamp(8, sqlDate);
			}
			else{
				ps.setString(8, null);

			}
			if(enddate!=null&&!enddate.equals("")){
				if(endtime!=null){
					endd = enddate + " " + "00:00";
				}
				else{
					endd = enddate + " " + endtime;
				}
				date = sdf.parse(endd);
				sqlDate = new java.sql.Timestamp(date.getTime());
				ps.setTimestamp(9, sqlDate);
			}
			else{
				ps.setString(9, null);
			}
			System.out.println(ps);
			ps.executeUpdate();

		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
			if (ps != null) try { ps.close(); } catch (SQLException logOrIgnore) {}
			if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
		}
	}

	// CHECKS IF USERID AND ALERTNAME ARE AN EXISTING PAIR IN TABLE
	public boolean validateSave(String userID, String alertName) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
				connection = database.getConnection();
				statement = connection.prepareStatement("SELECT * FROM T_savedSearches WHERE userID = ? AND searchName = ?");
				statement.setInt(1, Integer.parseInt(userID));
				statement.setString(2, alertName);
				rs = statement.executeQuery();
				if(rs.next()){
					return false;
				}
				else{
					return true;
				}
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
			if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
			if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
		}
	}

	// SEARCHES FOR ALL AUCTIONS A USER OWNS
	public List<Auction> userSearch(String userID) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<Auction> auctions = new ArrayList<Auction>();
		try {
			if(userID.equals("")){
				return auctions;
			}
			connection = database.getConnection();
			statement = connection.prepareStatement("SELECT * FROM T_itemDetails WHERE sellerID = ? ORDER BY auctionStart DESC");
			statement.setString(1,userID);
			rs = statement.executeQuery();
			auctions=convertAuctions(rs);

		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
			if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
			if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
		}
		return auctions;
	}

	// SEARCHES FOR ALL AUCTIONS A USER PARTICIPATED IN AS A SELLER
	public List<Auction> userAsSeller(int userID) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<Auction> auctions = new ArrayList<Auction>();


		try {
			connection = database.getConnection();

			statement = connection.prepareStatement("SELECT * FROM T_itemDetails WHERE itemID IN (SELECT itemID FROM V_itemDetailForBidderOrSeller WHERE sellerID = ?) OR itemID IN (SELECT itemID FROM T_itemDetails WHERE sellerID = ?) ");
			statement.setInt(1,userID);
			statement.setInt(2,userID);
			rs = statement.executeQuery();
			auctions=convertAuctions(rs);
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
			if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
			if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
		}
		return auctions;
	}

	// SEARCHES FOR ALL AUCTIONS A USER PARTICIPATED IN AS A BUYER
	public List<Auction> userAsBuyer(int userID) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<Auction> auctions = new ArrayList<Auction>();

		try {
			connection = database.getConnection();

			statement = connection.prepareStatement("SELECT * FROM T_itemDetails WHERE itemID IN (SELECT itemID FROM V_itemDetailForBidderOrSeller WHERE bidderID = ?)");
			statement.setInt(1,userID);
			rs = statement.executeQuery();
			auctions=convertAuctions(rs);

		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
			if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
			if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
		}
		return auctions;
	}

	// RETURNS AUCTIONS SIMILAR TO ITEMID
	public List<Auction> getSimilarItems(int itemID) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<Auction> auctions = new ArrayList<Auction>();

		try {
			connection = database.getConnection();
			statement = connection.prepareStatement("SELECT * FROM T_itemDetails WHERE itemID IN(SELECT similarItemID AS 'itemID' FROM V_similarItems WHERE itemID = ?) AND itemID != ?");
			statement.setInt(1,itemID);
			statement.setInt(2,itemID);
			rs = statement.executeQuery();
			auctions=convertAuctions(rs);
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
			if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
			if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
		}
		return auctions;
	}

	// RETURNS ITEMID OF A FRESHLY CREATED AUCTION
	public int getMostRecentAuction(int userID) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<Auction> auctions = new ArrayList<Auction>();
		int itemID = 0;

		try {
			connection = database.getConnection();
			statement = connection.prepareStatement("SELECT itemID FROM T_itemDetails WHERE auctionStart = (SELECT MAX(auctionStart) FROM T_itemDetails WHERE sellerID = ?)");
			statement.setInt(1,userID);
			rs = statement.executeQuery();
			if(rs.next()){
				itemID = rs.getInt("itemID");
			}
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
			if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
			if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
		}
		return itemID;
	}



	public boolean editAuction(Auction a) throws ClassNotFoundException{
		boolean success = false;
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;

		try{
			con = database.getConnection();
			ps = con.prepareStatement("UPDATE T_itemDetails SET itemName=?, itemDescription=?, catID=?, bidIncrement=?, startingBid=?, reserveBid=? WHERE itemID=?");

			//Add parameters of the query. Start with 1, the 0-parameter is the INSERT statement itself
			ps.setFloat(7, a.getItemID());
			ps.setString(1, a.getItemName());
			ps.setString(2, a.getItemDescription());
			ps.setInt(3, a.getCatId());
			ps.setFloat(4, a.getBidIncrement());
			ps.setFloat(5, a.getStartingBid());
			ps.setFloat(6, a.getReserveBid());

			ps.executeUpdate();

			ps2 = con.prepareStatement("CALL SP_refreshSearchAlertsForItemID(?)");
			ps2.setInt(1, a.getItemID());
			ps2.executeUpdate();
			success = true;

			con.close();
		}catch(Exception e){
			System.out.println(e);
		}
		return success;
	}

		
}