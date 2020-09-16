
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage>

	<jsp:attribute name="title">
      <title>Home</title>
    </jsp:attribute>

    <jsp:attribute name="header">
    </jsp:attribute>

    <jsp:attribute name="footer">
    </jsp:attribute>

    <jsp:body>
        <div class="container">
            <div class="section center">
                <h6 class="header center purple-text">Search. (Leave blank to display all) <a href="searchByUser.jsp">Look by username here.</a></h6>
                <h6 class="header center purple-text">${requestScope.message}</h6>
                <div class="card-panel blue-grey lighten-5">
                <form class="col s12" action="search" method="GET">
                    <input type="hidden" name="typeOfSearch" value="all">
                    <div class="row">
                        <div class="input-field col s12">
                            <textarea id="field" name="field" class="materialize-textarea"></textarea>
                            <label for="field">Keywords</label>
                        </div>
                    </div>
                    <div class="row">
                        <c:import url="/forms/basicCatdropdown.jsp"/>
                        <c:import url="/forms/fullCatDropdown.jsp"/>
                    </div>
                    <div class="row">
                        <div class="input-field col s2">
                            <input placeholder="0.00" id="minBid" name="minBid" type="number" min="0.00" step="0.01" class="validate">
                            <label for="minBid">Min Bid</label>
                        </div>
                        <div class="input-field col s2">
                            <input placeholder="0.00" id="maxBid" name="maxBid" type="number" min="0.00" step="0.01" class="validate">
                            <label for="minBid">Max Bid</label>
                        </div>
                        <div class="input-field col s2">
                            <input placeholder="Open Date" name="opendate" id="opendate" type="text" class="datepicker">
                            <label for="opendate">Open Date</label>
                        </div>
                        <div class="input-field col s2">
                            <input placeholder="Open Time" name="opentime" id="opentime" type="text" class="timepicker">
                            <label for="opentime">Open Time</label>
                        </div>
                        <div class="input-field col s2">
                            <input placeholder="End Date" name="enddate" id="enddate" type="text" class="datepicker">
                            <label for="enddate">End Date</label>
                        </div>
                        <div class="input-field col s2">
                            <input placeholder="End Time" name="endtime" id="endtime" type="text" class="timepicker">
                            <label for="endtime">End Time</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s3">
                            <select id="sortType" name="sortType">
                                <option value="" disabled selected>Sort By</option>
                                <option value="itemName">Name</option>
                                <option value="parentCatName">Category</option>
                                <option value="currentBid">Price</option>
                                <option value="auctionEnd">Date</option>
                            </select>
                            <label>Sort By</label>
                        </div>
                        <div class="input-field col s3">
                            <select id="order" name="order">
                                <option value="ASC">Ascending</option>
                                <option value="DESC">Descending</option>
                            </select>
                            <label>Sort Order</label>
                        </div>
                        <c:if test="${not empty username}">
                        <div class="input-field col s4">
                            <input placeholder="Alert Name" id="two" disabled name="alertName" type="text" class="validate">
                            <label for="two">Alert Name</label>
                        </div>
                        <div class="input-field col s2">
                            <label for="one">
                                <input id="one" name="isAlert" type="checkbox" />
                                <span>Save as Alert</span>
                            </label>
                        </div>
                        </c:if>
                    </div>
                    <c:import url="/forms/propertiesForm.jsp"/>
                    <button class="waves-effect waves-light btn-large" type="submit" name="action">Search</button>
                    <button class="waves-effect waves-light btn-large" type="reset" onclick="return resetForm();">Reset</button>
                </form>
                </div>
            </div>
        </div>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>

        <script src="${pageContext.request.contextPath}/js/jquery-3.4.0.js"></script>
        <script type="text/javascript">
            function resetForm(){
                setTimeout(function(){
                        $('#two').prop('disabled', true);
                }, 50);
                return true;
            }

            $('#one').change(function() {
                if (this.checked) {
                    $('#two').prop('disabled', false);
                }
                else{
                    $('#two').prop('disabled', true);
                }
            });
        </script>

    </jsp:body>
</t:genericpage>

