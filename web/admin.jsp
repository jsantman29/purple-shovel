<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage>

	<jsp:attribute name="title">
      <title>Admin</title>
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
                        <div class="col s12 center">
                            <a href="reportForm.jsp">
                                <button class="waves-effect waves-light btn-large">Sales Report</button>
                            </a>
                            <br>
                        </div>
                    </div>
                    <div class="row">
                    <div class="col s12 center">
                        <a href="newUser.jsp">
                            <button class="waves-effect waves-light btn-large">New User or Customer Sales Representative</button>
                        </a>
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
    </jsp:body>

</t:genericpage>

