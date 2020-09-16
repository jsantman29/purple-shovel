
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage>

	<jsp:attribute name="title">
      <title>${requestScope.title} ${param.itemName} ${param.username}</title>
    </jsp:attribute>

    <jsp:attribute name="header">
    </jsp:attribute>

    <jsp:attribute name="footer">
    </jsp:attribute>

    <jsp:body>
        <div class="container">
            <div class="section center">
                <h4 class="header purple-text">${requestScope.title} ${param.itemName}${param.username}</h4>
            </div>
        </div>
        <div class="container">
            <div class="section center">
                <div class="collection">
                <c:choose>
                    <c:when test="${not empty requestScope.auctions}">
                        <c:forEach items="${requestScope.auctions}" var="auction">
                            <t:auctiondetails auction="${auction}"/>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        ${requestScope.message}
                    </c:otherwise>
                </c:choose>
                    <c:if test="${param.typeOfSearch == 'participation'}">
                        <c:choose>
                        <c:when test="${not empty requestScope.bidIn}">
                            <h5 class="header purple-text">${param.username} is a buyer/bidder in these auctions.</h5>
                            <c:forEach items="${requestScope.bidIn}" var="auction">
                                <t:auctiondetails auction="${auction}"/>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <h5 class="header purple-text">${param.username} has not bid in any auctions.</h5>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${not empty requestScope.soldIn}">
                                <h5 class="header purple-text">${param.username} is a seller in these auctions.</h5>
                                <c:forEach items="${requestScope.soldIn}" var="auction">
                                    <t:auctiondetails auction="${auction}"/>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <h5 class="header purple-text">${param.username} has not put up any auctions.</h5>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </div>
            </div>
        </div>
        <br>
        <br>
    </jsp:body>

</t:genericpage>