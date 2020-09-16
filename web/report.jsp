<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:genericpage>

	<jsp:attribute name="title">
      <title> Bid History</title>
    </jsp:attribute>

    <jsp:attribute name="header">
    </jsp:attribute>

    <jsp:attribute name="footer">
    </jsp:attribute>

    <jsp:body>

        <div class="container">
            <div class="section center">
                <c:choose>
                    <c:when test="${param.reportType == 'total'}">
                    <h4 class="header center purple-text"> Total Sales Report</h4>
                    </c:when>
                    <c:when test="${param.reportType == 'item'}">
                        <h4 class="header center purple-text"> Sales Report for ${sales[0].item} ${sales[0].itemType} </h4>
                    </c:when>
                    <c:when test="${param.reportType == 'itemType'}">
                        <h4 class="header center purple-text"> Sales Report for ${sales[0].itemType}</h4>
                    </c:when>
                    <c:when test="${param.reportType == 'user'}">
                        <h4 class="header center purple-text"> Sales Report for ${sales[0].sellerName}</h4>
                    </c:when>
                    <c:when test="${param.reportType == 'bestSelling'}">
                        <h4 class="header center purple-text"> Sales Report by Best Selling Items</h4>
                    </c:when>
                    <c:when test="${param.reportType == 'bestBuyer'}">
                        <h4 class="header center purple-text"> Sales Report by Best Buyers</h4>
                    </c:when>
                    <c:otherwise>
                        <h4 class="header center purple-text"> Error.</h4>
                    </c:otherwise>
                </c:choose>
                <h4 class="header center purple-text"> Total Earnings <fmt:formatNumber value = "${total}" type = "currency"/></h4>
            </div>
            <br>
        </div>
        <div class="container">
            <c:choose>
                <c:when test="${param.reportType == 'total'}">
                    <table class="highlight">
                        <thead>
                        <tr>
                            <th>Buyer</th>
                            <th>Seller</th>
                            <th>Item Name</th>
                            <th>Price Sold</th>
                            <th>Item Type</th>
                            <th>Item</th>
                            <th>Date</th>
                            <th>Sale ID</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.sales}" var="sale">
                            <tr>
                                <td>${sale.buyerName}</td>
                                <td>${sale.sellerName}</td>
                                <td>${sale.itemName}</td>
                                <td><fmt:formatNumber value = "${sale.amount}" type = "currency"/></td>
                                <td>${sale.itemType}</td>
                                <td>${sale.item}</td>
                                <td>${sale.saleTimestamp}</td>
                                <td>${sale.saleID}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:when test="${param.reportType == 'item'}">
                    <table class="highlight">
                        <thead>
                        <tr>
                            <th>Buyer</th>
                            <th>Seller</th>
                            <th>Item Name</th>
                            <th>Price Sold</th>
                            <th>Date</th>
                            <th>Sale ID</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.sales}" var="sale">
                            <tr>
                                <td>${sale.buyerName}</td>
                                <td>${sale.sellerName}</td>
                                <td>${sale.itemName}</td>
                                <td><fmt:formatNumber value = "${sale.amount}" type = "currency"/></td>
                                <td>${sale.saleTimestamp}</td>
                                <td>${sale.saleID}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:when test="${param.reportType == 'itemType'}">
                    <table class="highlight">
                        <thead>
                        <tr>
                            <th>Buyer</th>
                            <th>Seller</th>
                            <th>Item Name</th>
                            <th>Price Sold</th>
                            <th>Item</th>
                            <th>Date</th>
                            <th>Sale ID</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.sales}" var="sale">
                            <tr>
                                <td>${sale.buyerName}</td>
                                <td>${sale.sellerName}</td>
                                <td>${sale.itemName}</td>
                                <td><fmt:formatNumber value = "${sale.amount}" type = "currency"/></td>
                                <td>${sale.item}</td>
                                <td>${sale.saleTimestamp}</td>
                                <td>${sale.saleID}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:when test="${param.reportType == 'user'}">
                    <table class="highlight">
                        <thead>
                        <tr>
                            <th>Buyer</th>
                            <th>Item Name</th>
                            <th>Price Sold</th>
                            <th>Item Type</th>
                            <th>Item</th>
                            <th>Date</th>
                            <th>Sale ID</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.sales}" var="sale">
                            <tr>
                                <td>${sale.buyerName}</td>
                                <td>${sale.itemName}</td>
                                <td><fmt:formatNumber value = "${sale.amount}" type = "currency"/></td>
                                <td>${sale.itemType}</td>
                                <td>${sale.item}</td>
                                <td>${sale.saleTimestamp}</td>
                                <td>${sale.saleID}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:when test="${param.reportType == 'bestSelling'}">
                    <table class="highlight">
                        <thead>
                        <tr>
                            <th>Item</th>
                            <th>Item Type</th>
                            <th>Number of Sales</th>
                            <th>Total Sales</th>
                            <th>Last Sold</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.sales}" var="sale">
                            <tr>
                                <td>${sale.item}</td>
                                <td>${sale.itemType}</td>
                                <td>${sale.count}</td>
                                <td><fmt:formatNumber value = "${sale.amount}" type = "currency"/></td>
                                <td>${sale.saleTimestamp}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:when test="${param.reportType == 'bestBuyer'}">
                    <table class="highlight">
                        <thead>
                        <tr>
                            <th>Buyer</th>
                            <th>Auctions Won</th>
                            <th>Money Spent</th>
                            <th>Last Active</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.sales}" var="sale">
                            <tr>
                                <td>${sale.buyerName}</td>
                                <td>${sale.count}</td>
                                <td><fmt:formatNumber value = "${sale.amount}" type = "currency"/></td>
                                <td>${sale.saleTimestamp}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    Error:
                </c:otherwise>
            </c:choose>

        </div>

    </jsp:body>

</t:genericpage>