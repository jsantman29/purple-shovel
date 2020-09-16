
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
        <c:choose>
            <c:when test="${sessionScope.isAdmin}">
        <div class="container">
            <div class="section center">
                <h5 class="header center purple-text">Generate a sales report.</h5>
                <h5 class="header center purple-text">${requestScope.message}</h5>
                <div class="card-panel blue-grey lighten-5">
                    <form id="report" class="col s12" action="salesReport" method="GET">
                        <div class="row">
                            <div class="input-field col s6">
                                <select id="reportType" name="reportType">
                                    <option value="" disabled selected>Select</option>
                                    <option value="total">Total Earnings</option>
                                    <option value="item">Item</option>
                                    <option value="itemType">Item type</option>
                                    <option value="user">End-user</option>
                                    <option value="bestSelling">Best-selling items</option>
                                    <option value="bestBuyer">Best buyers</option>
                                </select>
                                <label>Report Type</label>
                            </div>
                            <div id="itemType" class="test" style="display: none;"><c:import url="/forms/basicCatdropdown.jsp"/></div>
                            <div id="item" class="test" style="display: none;"><c:import url="/forms/fullCatDropdown.jsp"/></div>
                            <div id="user" class="test" style="display: none;"><div class="input-field col s6">
                                <input placeholder="Username" id="name" name="username" type="text" class="validate"><label for="user">Username</label>
                            </div></div>
                        </div>
                        <button class="waves-effect waves-light btn-large" type="submit" name="action">Generate</button>
                        <button class="waves-effect waves-light btn-large" type="reset" onclick="return resetForm();">Reset</button>
                    </form>
                </div>
            </div>
        </div>
            </c:when>
            <c:otherwise>
                <div class="section-center">
                <h5 class="header center purple-text">You shouldn't be here.</h5>
                </div>
            </c:otherwise>
        </c:choose>

        <script src="${pageContext.request.contextPath}/js/materialize.js"></script>
        <script src="${pageContext.request.contextPath}/js/jquery-3.4.0.js"></script>
        <script type="text/javascript">
            $(function() {
                $('#reportType').change(function(){
                    $('.test').hide();
                    $('#' + $(this).val()).show();
                });
            });
        </script>

    </jsp:body>
</t:genericpage>

