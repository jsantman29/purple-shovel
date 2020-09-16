<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>


<t:genericpage>

	<jsp:attribute name="title">
      <title>Auction: ${requestScope.auction.itemName}</title>
    </jsp:attribute>

    <jsp:attribute name="header">
    </jsp:attribute>

    <jsp:attribute name="footer">
    </jsp:attribute>

    <jsp:body>
        <div class="container">
            <div class="section center">

                <h2 class="header center purple-text"> Edit Auction: ${requestScope.auction.itemName}     </h2>
                <h6 class="header center purple-text">${requestScope.message}</h6>
                <c:choose>
                    <c:when test="${requestScope.message == 'Auction successfully updated!'}">
                        <a href="userHub.jsp" class="waves-effect waves-light btn-large">Go Back To User Hub</a>
                    </c:when>
                </c:choose>
                <div class="card-panel blue-grey lighten-5">
                    <h5 class="header center purple-text">${requestScope.error}</h5>
                    <br>
                    <div class="section">
                        <div class="row">
                            <div class="col s4">
                                <h5>Auction ID: ${requestScope.auction.itemID}</h5>
                            </div>
                            <div class="col s4">
                                <h5>Seller ID: ${requestScope.auction.sellerID}</h5>
                            </div>
                            <div class="col s4">
                                <h5 class="text-md">Status:
                                    <c:if test="${requestScope.auction.open}"><p class="green-text">Open</p></c:if>
                                    <c:if test="${!requestScope.auction.open}"><p class="red-text">Closed</p></c:if>
                                </h5>
                            </div>
                        </div>
                    </div>
                    <div class="divider"></div>
                    <br>
                    <h5 class="header center purple-text">(To make a change, you must fill all of the following item details.)</h5>
                    <br>
                    <form class="col s12" action="editAuction" method="POST">
                        <div class="row">
                            <div class="input-field col s6">
                                <input placeholder="${requestScope.auction.itemName}" name="name" id="name" type="text" class="validate">
                                <label for="name">Item Name</label>
                            </div>
                            <c:import url="/forms/fullCatDropdown.jsp"/>
                        </div>
                        <div class="row">
                            <div class="input-field col s12">
                                <textarea placeholder="${requestScope.auction.itemDescription}" id="desc" name="desc" class="materialize-textarea"></textarea>
                                <label for="desc">Item Description</label>
                            </div>
                        </div>
                        <div class="row">
                            <c:if test="${sessionScope.isCSR or sessionScope.isAdmin}">
                                <div class="input-field col">
                                    <input placeholder="${requestScope.auction.currentBid}" id="startingBid" name="startingBid" type="number" min="0.00" step="0.01" class="validate">
                                    <label for="startingBid">Starting Bid</label>
                                </div>
                                <div class="input-field col">
                                    <input placeholder="${requestScope.auction.reserveBid}" id="reserve" name="reserve" type="number" min="0.00" step="0.01" class="validate">
                                    <label for="reserve">Reserve (set 0 for none)</label>
                                </div>
                            </c:if>
                        </div>
                        <div class="divider"></div>
                        <br>
                        <h5 class="header center purple-text">(Optional)</h5>
                        <br>
                        <c:import url="/forms/propertiesForm.jsp"/>
                        <input type="hidden" name="itemID" value="${requestScope.auction.itemID}">
                        <button class="waves-effect waves-light btn-large" type="submit" name="action">Update</button>
                    </form>
                    <a href="userHub.jsp" class="waves-effect waves-light btn-large">Cancel</a>
                </div>
            </div>
        </div>
        <br>
        <br>
        <br>
        <br>

    </jsp:body>

</t:genericpage>

