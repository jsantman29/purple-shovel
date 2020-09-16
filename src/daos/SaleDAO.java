package daos;

import objs.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SaleDAO {
    private Database database;

    public SaleDAO(Database database){
        this.database = database;
    }

    // Lists all sales
    public List<Sale> listSales(String reportType, String parameter) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        List<Sale> sales = new ArrayList<Sale>();

        try {
            connection = database.getConnection();
            if(reportType.equals("total")){
                statement = connection.prepareStatement("SELECT invoiceID, finalBidAmmount, billingTimestamp, itemName, catName, parentCatID, buyerusername, sellerusername FROM V_auctionWinFullDetails ORDER BY billingTimestamp DESC");
                rs = statement.executeQuery();
                while (rs.next()) {
                    Sale sale = new Sale();
                    sale.setSaleID(rs.getInt("invoiceID"));
                    sale.setCount(1);
                    sale.setAmount(rs.getFloat("finalBidAmmount"));
                    sale.setSaleTimestamp(rs.getDate("billingTimestamp"));
                    sale.setItemName(rs.getString("itemName"));
                    sale.setBuyerName(rs.getString("buyerusername"));
                    sale.setSellerName(rs.getString("sellerusername"));
                    sale.setItem(rs.getString("catName"));
                    statement = connection.prepareStatement("SELECT catName FROM T_itemCategory WHERE catID = ?");
                    statement.setInt(1, rs.getInt("parentCatID"));
                    rs2 = statement.executeQuery();
                    if (rs2.next()) {
                        sale.setItemType(rs2.getString("catName"));
                    }
                    sales.add(sale);
                }
            }
            else if(reportType.equals("item")){
                statement = connection.prepareStatement("SELECT invoiceID, finalBidAmmount, billingTimestamp, itemName, catName, parentCatID, buyerusername, sellerusername FROM V_auctionWinFullDetails WHERE catID = ? ORDER BY billingTimestamp DESC");
                statement.setInt(1, Integer.parseInt(parameter));
                rs = statement.executeQuery();
                while (rs.next()) {
                    Sale sale = new Sale();
                    sale.setSaleID(rs.getInt("invoiceID"));
                    sale.setAmount(rs.getFloat("finalBidAmmount"));
                    sale.setSaleTimestamp(rs.getDate("billingTimestamp"));
                    sale.setItemName(rs.getString("itemName"));
                    sale.setBuyerName(rs.getString("buyerusername"));
                    sale.setSellerName(rs.getString("sellerusername"));
                    sale.setItem(rs.getString("catName"));
                    statement = connection.prepareStatement("SELECT catName FROM T_itemCategory WHERE catID = ?");
                    statement.setInt(1, rs.getInt("parentCatID"));
                    rs2 = statement.executeQuery();
                    if (rs2.next()) {
                        sale.setItemType(rs2.getString("catName"));
                    }
                    sales.add(sale);
                }
            }
            else if(reportType.equals("itemType")){
                statement = connection.prepareStatement("SELECT invoiceID, finalBidAmmount, billingTimestamp, itemName, catName, parentCatID, buyerusername, sellerusername FROM V_auctionWinFullDetails WHERE parentCatID = ? ORDER BY billingTimestamp DESC");
                statement.setInt(1, Integer.parseInt(parameter));
                rs = statement.executeQuery();
                while (rs.next()) {
                    Sale sale = new Sale();
                    sale.setSaleID(rs.getInt("invoiceID"));
                    sale.setCount(1);
                    sale.setAmount(rs.getFloat("finalBidAmmount"));
                    sale.setSaleTimestamp(rs.getDate("billingTimestamp"));
                    sale.setItemName(rs.getString("itemName"));
                    sale.setBuyerName(rs.getString("buyerusername"));
                    sale.setSellerName(rs.getString("sellerusername"));
                    sale.setItem(rs.getString("catName"));
                    statement = connection.prepareStatement("SELECT catName FROM T_itemCategory WHERE catID = ?");
                    statement.setInt(1, rs.getInt("parentCatID"));
                    rs2 = statement.executeQuery();
                    if (rs2.next()) {
                        sale.setItemType(rs2.getString("catName"));
                    }
                    sales.add(sale);
                }
            }
            else if(reportType.equals("user")){
                statement = connection.prepareStatement("SELECT invoiceID, finalBidAmmount, billingTimestamp, itemName, catName, parentCatID, buyerusername, sellerusername FROM V_auctionWinFullDetails WHERE sellerusename = ? ORDER BY billingTimestamp DESC");
                statement.setString(1, parameter);
                rs = statement.executeQuery();
                while (rs.next()) {
                    Sale sale = new Sale();
                    sale.setSaleID(rs.getInt("invoiceID"));
                    sale.setCount(1);
                    sale.setAmount(rs.getFloat("finalBidAmmount"));
                    sale.setSaleTimestamp(rs.getDate("billingTimestamp"));
                    sale.setItemName(rs.getString("itemName"));
                    sale.setBuyerName(rs.getString("buyerusername"));
                    sale.setSellerName(rs.getString("sellerusername"));
                    sale.setItem(rs.getString("catName"));
                    statement = connection.prepareStatement("SELECT catName FROM T_itemCategory WHERE catID = ?");
                    statement.setInt(1, rs.getInt("parentCatID"));
                    rs2 = statement.executeQuery();
                    if (rs2.next()) {
                        sale.setItemType(rs2.getString("catName"));
                    }
                    sales.add(sale);
                }
            }
            else if(reportType.equals("bestSelling")){
                statement = connection.prepareStatement("SELECT SUM(finalBidAmmount) AS finalBidAmmount, MAX(billingTimestamp) AS billingTimestamp, COUNT(*) AS numberOfSales, catName, parentCatID, catID FROM V_auctionWinFullDetails GROUP BY catID ORDER BY finalBidAmmount DESC");
                rs = statement.executeQuery();
                while (rs.next()) {
                    Sale sale = new Sale();
                    sale.setCount(rs.getInt("numberOfSales"));
                    sale.setAmount(rs.getFloat("finalBidAmmount"));
                    sale.setSaleTimestamp(rs.getDate("billingTimestamp"));
                    sale.setItem(rs.getString("catName"));
                    statement = connection.prepareStatement("SELECT catName FROM T_itemCategory WHERE catID = ?");
                    statement.setInt(1, rs.getInt("parentCatID"));
                    rs2 = statement.executeQuery();
                    if (rs2.next()) {
                        sale.setItemType(rs2.getString("catName"));
                    }
                    sales.add(sale);
                }
            }
            else if(reportType.equals("bestBuyer")){
                statement = connection.prepareStatement("SELECT * FROM V_totalEarningsByUser ORDER BY totalEarnings DESC");
                rs = statement.executeQuery();
                while (rs.next()) {
                    Sale sale = new Sale();
                    sale.setSaleTimestamp(rs.getDate("maxTimestamp"));
                    sale.setCount(rs.getInt("totalAuctions"));
                    sale.setAmount(rs.getFloat("totalEarnings"));
                    sale.setBuyerName(rs.getString("username"));
                    sales.add(sale);
                }
            }
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
            if (rs2 != null) try { rs2.close(); } catch (SQLException logOrIgnore) {}
            if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
            if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
        }
        return sales;
    }

    public float netEarnings(String reportType, String parameter) throws SQLException{

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        float earnings = 0.00f;

        try {
            connection = database.getConnection();

            if(reportType.equals("total")||reportType.equals("bestSelling")||reportType.equals("bestBuyer")) {
                statement = connection.prepareStatement("SELECT * FROM V_totalEarnings");
            }else if(reportType.equals("item")){
                statement = connection.prepareStatement("SELECT SUM(finalBidAmmount) AS totalEarnings FROM V_auctionWinFullDetails WHERE catID=?");
                statement.setInt(1, Integer.parseInt(parameter));
            } else if(reportType.equals("itemType")){
                statement = connection.prepareStatement("SELECT SUM(finalBidAmmount) AS totalEarnings FROM V_auctionWinFullDetails WHERE parentCatID=?");
                statement.setInt(1, Integer.parseInt(parameter));
            }else if(reportType.equals("user")){
                statement = connection.prepareStatement("SELECT SUM(finalBidAmmount) AS totalEarnings FROM V_auctionWinFullDetails WHERE sellerusername=?");
                statement.setString(1, parameter);
            } else{
                return earnings;
            }
            rs = statement.executeQuery();

            if(rs.next()){
                earnings = rs.getFloat("totalEarnings");
            }
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
            if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
            if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
        }
        return earnings;
    }

}
