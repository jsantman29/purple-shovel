
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
        <div class="section no-pad-bot" id="index-banner">

        <div class="container">
            <div class="section center">
                <h1 class="header center purple-text">BuY mE</h1>
            <div class="row center">
                <h5 class="header col s12 light">${requestScope.message}</h5>
                <h5 class="header col s12 light">Fulfill ur dreams, live out ur memes</h5>
            </div>
        </div>
        <br>
        <br>

        <c:import url="/forms/searchBox.jsp" />
        <br>
        <br>

    </jsp:body>

</t:genericpage>

