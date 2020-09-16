<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@attribute name="title" fragment="true" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>

<!-- CSS  -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/materialize.css" type="text/css" rel="stylesheet"/>
<link href="${pageContext.request.contextPath}/css/style2.css" type="text/css" rel="stylesheet"/>

  <jsp:invoke fragment="title"/>

  <head id="pageheader">
    <jsp:invoke fragment="header"/>
    <ul>
      <c:choose>
        <c:when test="${not empty sessionScope.username}">
          <li><a href="./">Home </a></li>
          <c:choose>
          <c:when test = "${fn:substring(sessionScope.username, 0, 4) == 'bing'}">
          <li><a>Welcome, ${sessionScope.username}&#128149</a></li>
          </c:when>
          <c:otherwise>
          <li><a>Welcome, ${sessionScope.username}</a></li>
          </c:otherwise>
          </c:choose>
          <li><a href="logout">Logout </a></li>
          <li><a href="${pageContext.request.contextPath}/userHub.jsp">User Hub </a></li>
          <c:if test="${sessionScope.isAdmin or sessionScope.isCSR}">
            <li><a href="${pageContext.request.contextPath}/staff.jsp">Staff Portal </a></li>
          </c:if>
          <c:if test="${sessionScope.isAdmin}">
            <li><a href="${pageContext.request.contextPath}/admin.jsp">Admin Portal </a></li>
          </c:if>
          <li><a href="${pageContext.request.contextPath}/search.jsp">Search Auctions</a></li>
          <li><a href="${pageContext.request.contextPath}/questionBoard">Question Board</a></li>
        </c:when>
          
        
        <c:otherwise>
          <li><a href="./">Home </a></li>
          <li><a href="${pageContext.request.contextPath}/login.jsp">Login </a></li>
          <li><a href="${pageContext.request.contextPath}/newUser.jsp">Create new User</a></li>
          <li><a href="${pageContext.request.contextPath}/search.jsp">Search Auctions</a></li>
          <li><a href="${pageContext.request.contextPath}/questionBoard">Question Board</a></li>
        </c:otherwise>
      </c:choose>
    </ul>
  </head>


  <body>
    <div id="body">
      <jsp:doBody/>
      <br/>
      <br/>
    </div>
  </body>

  <footer id="pagefooter">
    <jsp:invoke fragment="footer"/>
    <div class="footer">
      <p>CS336 Group 32</p>
    </div>
  </footer>


<!--  Scripts-->
<script src="${pageContext.request.contextPath}/js/jquery-3.4.0.js"></script>
<script src="${pageContext.request.contextPath}/js/materialize.js"></script>
<t:javascript/>
</html>