<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="Auction template" pageEncoding="UTF-8"%>
<%@attribute name="auction" required="true" type="objs.Auction"%>

<c:url var="myURL" value="auction">
    <c:param name="itemID" value="${auction.itemID}"/>
</c:url>

<a href="${myURL}" class="collection-item z-depth-2 border-dark" >
    <div class="row center">
        <div class="col center">
            <h6>Status:
                <c:if test="${auction.open}">Open</c:if>
                <c:if test="${!auction.open}">Closed</c:if></h6>
        </div>
    </div>
    <div class="row">
        <div class="col s2">
            <h6>Auction ID:</h6>
        </div>
        <div class="col s2">
            <h6>Item Name:</h6>
        </div>
        <div class="col s2">
            <h6>Seller ID:</h6>
        </div>
        <div class="col s2">
            <h6>Bid Increment:</h6>
        </div>
        <div class="col s2">
            <h6>Current Bid:</h6>
        </div>
        <div class="col s2">
            <h6>Starting Bid:</h6>
        </div>
    </div>
    <div class="row">
        <div class="col s2">
            <h6>${auction.itemID}</h6>
        </div>
        <div class="col s2">
            <h6>${auction.itemName}</h6>
        </div>
        <div class="col s2">
            <h6>${auction.sellerID}</h6>
        </div>
        <div class="col s2">
            <h6>${auction.bidIncrement}</h6>
        </div>
        <div class="col s2">
            <h6>${auction.currentBid}</h6>
        </div>
        <div class="col s2">
            <h6>${auction.startingBid}</h6>
        </div>
    </div>
    <div class="row center">
        <div class="col center">
            <div class="divider center"></div>
            <br>
            <h6 class="center">Description: ${auction.itemDescription}</h6>
            <br>
        </div>
        <br>
    </div>
</a>
