<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                <h4 class="header center purple-text"> Bid History For: ${param.itemName}</h4>
                <h2 class="header center red-text"> ${delete}</h2>
            </div>
            <br>
        </div>
        <div class="container">
        <table class="highlight">
            <thead>
            <tr>
                <th>Username</th>
                <th>Bid</th>
                <th>BidTimestamp</th>
                <th>Bid ID</th>
                <c:if test="${sessionScope.isAdmin or sessionScope.isCSR}"><th>Action</th></c:if>
            </tr>
            </thead>
            <b>${requestScope.error}</b>
            <tbody>
                <%--    catching this messageList from the backend     --%>
            <c:forEach items="${requestScope.bids}" var="bidd">
                <tr>
                    <td>${bidd.username}</td>
                    <td><fmt:formatNumber value = "${bidd.bid}" type = "currency"/></td>
                    <td>${bidd.bidTimestamp}</td>
                    <td>${bidd.transactionID}</td>
                    <c:url var="deleteBid" value="delete">
                        <c:param name="deleteType" value="bid"/>
                        <c:param name="itemID" value="${param.itemID}"/>
                        <c:param name="bidID" value="${bidd.transactionID}"/>
                        <c:param name="typeOfSearch" value="bidHistory"/>
                    </c:url>
                    <c:if test="${sessionScope.isAdmin or sessionScope.isCSR}"><td><a href="${deleteBid}" id="delete" class="red-text">Delete</a></td></c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </div>

    </jsp:body>

</t:genericpage>