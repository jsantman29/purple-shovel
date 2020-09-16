<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage>

	<jsp:attribute name="title">
      <title>Hub</title>
    </jsp:attribute>

    <jsp:attribute name="header">
    </jsp:attribute>

    <jsp:attribute name="footer">
    </jsp:attribute>

    <jsp:body>

        <div class="container">
            <h2 class="header purple-text"> ${sessionScope.username}'s hub</h2>
            <br>
        </div>

        <div class="container">
        <div class="section center">
            <div class="divider"></div>
            <div class="section">
                <div class="row">
                    <div class="col s6">
                        <h5 class="center">Current userID:</h5>
                        <p class="light"> ${sessionScope.userID}</p>
                    </div>
                    <div class="col s6">
                        <h5 class="center">Current user: </h5>
                        <p class="light">${sessionScope.username}</p>
                    </div>
                </div>
            </div>
            <div class="divider"></div>
            <div class="section">
                <div class="row">
                    <div class="col s6">
                        <form action="search" method="GET">
                            <input type="hidden" name="typeOfSearch" value="userAuctions">
                            <input type="hidden" name="userID" value="${sessionScope.userID}">
                            <button class="waves-effect waves-light btn-large" type="submit" name="action">Manage your auctions</button>
                        </form>
                    </div>
                    <div class="col s6">
                        <a href="newAuction.jsp">
                            <button class="waves-effect waves-light btn-large">Create New Auction</button>
                        </a>
                        <br>
                    </div>
                </div>
            </div>
        <br>
        <br>

    </jsp:body>

</t:genericpage>