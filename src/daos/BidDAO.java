package daos;
import java.sql.*;
import java.util.*;
import java.util.Date;

import objs.*;

public class BidDAO {
    private Database database;

    public BidDAO(Database database){
        this.database = database;
    }

    public Bid convertBid(ResultSet rs) {
        Bid bid= new Bid();
        try {
            if (rs.next()) {
                bid= new Bid();
                bid.setItemID(rs.getInt("itemid"));
                bid.setUserID(rs.getInt("buyerID"));
                bid.setSellerID(rs.getInt("sellerID"));
                bid.setInvoiceId(rs.getInt("invoiceID"));
                bid.setBid(rs.getFloat("finalBidAmmount"));
                bid.setBidTimestamp(rs.getTimestamp("billingTimestamp"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bid;
    }

    public List<Bid> convertBids(ResultSet rs) {
        List<Bid> history = new ArrayList<Bid>();
        try {
            while (rs.next()) {
                Bid bid= new Bid();
                bid.setItemID(rs.getInt("itemid"));
                bid.setUserID(rs.getInt("buyerID"));
                bid.setSellerID(rs.getInt("sellerID"));
                bid.setInvoiceId(rs.getInt("invoiceID"));
                bid.setBid(rs.getFloat("finalBidAmmount"));
                bid.setBidTimestamp(rs.getTimestamp("billingTimestamp"));
                history.add(bid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return history;
    }

    // Lists all sales
    public List<Bid> listAllSales() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Bid> history = new ArrayList<Bid>();

        try {
            connection = database.getConnection();
            statement = connection.prepareStatement("SELECT * FROM T_billing");
            rs = statement.executeQuery();
            history = convertBids(rs);
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
            if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
            if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
        }
        if(history.isEmpty()){
            return null;
        }
        return history;
    }

    // Lists all bids for a specific auction
    public List<Bid> listAuctionBids(int itemID) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Bid> history = new ArrayList<Bid>();

        try {
            connection = database.getConnection();
            statement = connection.prepareStatement("SELECT * FROM V_bidTransactionsWithUsernames WHERE itemid = ? ORDER BY bidTimestamp DESC");
            statement.setInt(1, itemID);
            //System.out.println(statement);
            rs = statement.executeQuery();
            while(rs.next()){
                Bid bid = new Bid();
                bid.setTransactionID(rs.getInt("transactionID"));
                bid.setBid(rs.getFloat("bid"));
                bid.setUserID(rs.getInt("userID"));
                bid.setItemID(rs.getInt("itemID"));
                bid.setBidTimestamp(rs.getTimestamp("bidTimestamp"));
                bid.setUsername(rs.getString("username"));
                history.add(bid);
            }

        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
            if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
            if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
        }

        return history;
    }
    
	public Bid retrieve(int transactionID) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Bid bid = new Bid();

		try {
			connection = database.getConnection();
			//To Do change SQL statement for bids, not auctions
			statement = connection.prepareStatement("SELECT * FROM T_bidTransactions WHERE transactionid = ?");
			statement.setInt(1, transactionID);
			rs = statement.executeQuery();
			bid = convertBid(rs);
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
			if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
			if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
		}
		return bid;
	}

    //add a bid
    public boolean addBid(Bid bid) {
            Connection con = null;
            PreparedStatement ps = null;

        try{
            con = database.getConnection();

            ps = con.prepareStatement("INSERT INTO T_bidTransactions (userID, itemID, isAutoBid, bid, bidMax, bidTimestamp) VALUES (?, ?, ?, ?, ?, ?)");

            //Add parameters of the query. Start with 1, the 0-parameter is the INSERT statement itself
            ps.setInt(1, bid.getUserID());
            ps.setInt(2, bid.getItemID());
            ps.setBoolean(3, bid.isAutoBid());
            ps.setFloat(4, bid.getBid());
            ps.setFloat(5, bid.getBidMax());

            Date date = new Date();
            java.sql.Timestamp  sqlDate = new java.sql.Timestamp(date.getTime());
            ps.setTimestamp(6, sqlDate);
            ps.executeUpdate();

            con.close();
        }catch(Exception e){
            System.out.println(e);
        }
        return true;
    }

    public void deleteBid(int bidID) throws ClassNotFoundException{
        boolean success = false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            con = database.getConnection();
            ps = con.prepareStatement("DELETE FROM T_bidTransactions WHERE transactionID = ?");
            //Add parameters of the query. Start with 1, the 0-parameter is the INSERT statement itself
            ps.setInt(1, bidID);
            ps.executeUpdate();
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }


}
