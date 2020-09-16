<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<!-- TODO this shit dont work -->

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
            <div class="section center">

                <h5 class="header center purple-text">${requestScope.message}</h5>
                <c:if test="${not empty requestScope.auction}">
                    <c:url var="myURL" value="auction">
                        <c:param name="itemID" value="${auction.itemID}"/>
                    </c:url>
                    <a href="${myURL}">
                        <button class="waves-effect waves-light btn">See Your New Auction</button>
                    </a>
                </c:if>
                <h5 class="header center purple-text">${requestScope.error}</h5>

                <form class="col s12" action="newAuction" method="POST">
                    <div class="row">
                        <div class="input-field col s6">
                            <input placeholder="Item Name" name="name" id="name" type="text" class="validate">
                            <label for="name">Item Name</label>
                        </div>
                        <c:import url="/forms/fullCatDropdown.jsp"/>
                    </div>
                    <div class="row">
                        <div class="input-field col s12">
                            <textarea id="desc" name="desc" class="materialize-textarea"></textarea>
                            <label for="desc">Item Description</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s3">
                            <input placeholder="0.00" id="startingBid" name="startingBid" type="number" min="0.00" step="0.01" class="validate">
                            <label for="startingBid">Starting Price</label>
                        </div>
                        <div class="input-field col s3">
                            <input placeholder="0.00" id="reserve" name="reserve" type="number" min="0.00" step="0.01" class="validate">
                            <label for="reserve">Reserve</label>
                        </div>
                        <div class="input-field col s3">
                            <input placeholder="End Date" id="date" name="date" type="text" class="datepicker">
                            <label for="date">End Date</label>
                        </div>
                        <div class="input-field col s3">
                            <input placeholder="End Date" id="time" name="time" type="text" class="timepicker">
                            <label for="time">End Time</label>
                        </div>
                    </div>
                    <c:import url="/forms/propertiesForm.jsp"/>
                    <button class="waves-effect waves-light btn-large" id="submitForm" type="submit" name="action">Create</button>
                    <button class="waves-effect waves-light btn-large" type="reset" name="action">Reset</button>
                </form>
                    ${requestScope.Date}
                    ${requestScope.catID}
            </div>
        </div>

    </jsp:body>

</t:genericpage>

<!--
Item Name: <input type="text" name="name"/> <br/>
Description:<input type="text" name="desc"/> <br/>
Bid Increment: <input type="number" name="bidIncrement" min="0.00" step="0.01" /><br/>
Starting Bid: <input type="number" name=min="0.00" step="0.01" /><br/>
reserve: <input type="text" name="desc"/><br/>
End Date: <input type="text" class="datepicker" name="date"><br/>
End Time: <input type="text" class="timepicker" name="time"><br/>
-->
