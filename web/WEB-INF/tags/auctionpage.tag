<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="Auction template" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@attribute name="auction" required="true" type="objs.Auction"%>

<c:set var="auction" scope="request" value="${auction}"/>


<t:genericpage>

	<jsp:attribute name="title">
      <title>Auction: ${auction.itemName}</title>
    </jsp:attribute>

    <jsp:attribute name="header">
    </jsp:attribute>

    <jsp:attribute name="footer">
    </jsp:attribute>

    <jsp:body>

        <h2 class="header center red-text"> ${delete}</h2>
        <c:if test="${not empty auction}">
        <c:url var="bidHistory" value="search">
            <c:param name="typeOfSearch" value="bidHistory"/>
            <c:param name="itemID" value="${auction.itemID}"/>
            <c:param name="itemName" value="${auction.itemName}"/>
        </c:url>

        <c:url var="similarItems" value="search">
            <c:param name="typeOfSearch" value="similarItems"/>
            <c:param name="itemID" value="${auction.itemID}"/>
            <c:param name="itemName" value="${auction.itemName}"/>
        </c:url>

        <c:url var="editAuction" value="editAuction">
            <c:param name="itemID" value="${auction.itemID}"/>
        </c:url>

        <c:url var="deleteAuction" value="delete">
            <c:param name="deleteType" value="auction"/>
            <c:param name="itemID" value="${auction.itemID}"/>
        </c:url>

        <div class="container">
            <div class="section center">
                <h2 class="header center purple-text"> Auction: ${auction.itemName}</h2>
            </div>
            <br>
        </div>

        <div class="container">
            <div class="section center">

                <div class="divider"></div>
                <div class="section">
                    <div class="row">
                        <div class="col s3">
                            <h6 class="center-align">Auction ID: ${auction.itemID}</h6>
                        </div>
                        <div class="col s4">
                            <h5 class="center-align">Item Name: ${auction.itemName}</h5>
                        </div>
                        <div class="col s3">
                            <h6 class="center-align">Seller ID: ${auction.sellerID}</h6>
                        </div>
                    </div>
                </div>
                <div class="divider"></div>
                <div class="section">
                    <div class="row">
                        <div class="col s3">
                            <h6 class="center-align">Starting Bid: ${auction.startingBid}</h6>
                        </div>
                        <div class="col s4">
                            <h5 class="center-align">Current Bid: ${auction.currentBid}</h5>
                        </div>
                        <div class="col s3">
                            <h6 class="center-align">Bid Increment: ${auction.bidIncrement}</h6>
                        </div>
                    </div>
                </div>
                <div class="divider"></div>
                <div class="section">
                    <div class="row">
                        <div class="col s3">
                        </div>
                        <div class="col s4">
                            <h5>Status:
                                <c:if test="${auction.open}">Open</c:if>
                                <c:if test="${!auction.open}">Closed</c:if></h5>
                        </div>
                        <div class="col s3">
                        </div>
                    </div>
                </div>
                <div class="divider"></div>
                <div class="section">
                    <div class="row">
                        <div class="col s10">
                            <h6>Description: ${auction.itemDescription}</h6>
                        </div>
                        <br>
                    </div>
                </div>
                <div class="divider center-align"></div>
                <c:import url="/forms/propertiesDisplay.jsp"/>
                <div class="section center-align">
                    <div class="row center-align">
                        <div class="col s2 center-align">
                            <a href="${similarItems}" class="center-align">
                                <button class="waves-effect waves-light btn-large center-align">Similar Items</button>
                            </a>
                        </div>
                        <div class="col s2 center-align">
                            <a href="${bidHistory}" class="center-align">
                                <button class="waves-effect waves-light btn-large">View Bids</button>
                            </a>
                        </div>
                        <c:if test="${not empty sessionScope.username}">
                            <c:if test="${auction.sellerID != sessionScope.userID}">
                                <div class="col s4 center-align">
                                    <div class="card-panel blue-grey lighten-5 center-align">
                                        <h6>${requestScope.message}</h6>
                                        <form action="Bid" method="POST">
                                            <input type="hidden" name="itemID" value="${auction.itemID}">
                                            <c:choose>
                                                <c:when test="${auction.currentBid == 0}">
                                                    <div class="input-field">
                                                        <input placeholder="${auction.startingBid + auction.bidIncrement}" id="bid" name="bid" type="number" min="${((auction.startingBid + auction.bidIncrement)*100.0)/100.0}" step="0.01" class="validate">
                                                        <label for="bid">Bid</label>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="input-field">
                                                        <input placeholder="${auction.currentBid + auction.bidIncrement}" id="bid" name="bid" type="number" min="${((auction.currentBid + auction.bidIncrement)*100.0)/100.0}" step="0.01" class="validate">
                                                        <label for="bid">Bid</label>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:choose>
                                                <c:when test="${auction.currentBid == 0}">
                                                    <div class="input-field">
                                                        <input placeholder="${auction.startingBid + auction.bidIncrement}" id="maxBid" name="maxBid" type="number" min="${auction.startingBid + auction.bidIncrement}" step="0.01" class="validate">
                                                        <label for="maxBid">Max Auto Bid</label>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="input-field">
                                                        <input placeholder="${auction.currentBid + auction.bidIncrement}" id="maxBid" name="maxBid" type="number" min="${auction.currentBid + auction.bidIncrement}" step="0.01" class="validate">
                                                        <label for="maxBid">Max Auto Bid</label>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                            <button class="waves-effect waves-light btn" type="submit" name="action">Submit Bid</button>
                                        </form>
                                    </div>
                                </div>
                            </c:if>
                            <!-- if logged in, show buttons to edit auction if possible and enter bids-->
                            <c:if test="${sessionScope.isCSR or sessionScope.isAdmin or (auction.sellerID == sessionScope.userID)}">
                                <div class="col s2 center-align">
                                    <a href="${editAuction}" class="center-align">
                                        <button class="waves-effect waves-light btn-large">Edit Auction</button>
                                    </a>
                                </div>
                            </c:if>
                            <c:if test="${sessionScope.isCSR or sessionScope.isAdmin}">
                                <div class="col s2 center-align">
                                    <a href="${deleteAuction}" class="center-align">
                                        <button class="waves-effect waves-light btn-large">Delete Auction</button>
                                    </a>
                                </div>
                            </c:if>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <br>
        <br>
        </c:if>

       <%--
       Start: ${auction.start}<br/>
       End: ${auction.end}<br/>
       --%>

    </jsp:body>

</t:genericpage>

