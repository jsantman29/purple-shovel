<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage>

	<jsp:attribute name="title">
      <title>User Results</title>
    </jsp:attribute>

    <jsp:attribute name="header">
    </jsp:attribute>

    <jsp:attribute name="footer">
    </jsp:attribute>

    <jsp:body>
        <div class="container">
            <div class="section center">
                <h4 class="header purple-text">User Results</h4>
                <h4 class="header purple-text">${error}</h4>
            </div>
        </div>
        <div class="container">
            <div class="section center">
                <div class="collection">
                    <c:choose>
                        <c:when test="${not empty requestScope.users}">
                            <c:forEach items="${requestScope.users}" var="user">
                                <t:userdetails user="${user}"/>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            ${requestScope.message}
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <br>
        <br>
    </jsp:body>

</t:genericpage>