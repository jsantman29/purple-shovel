<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>



<!-- Search Box -->
<div class="container">
    <div class="section center">
        <div class="row center">
            <div class="row center">
                <form action="search" method="GET">
                    <input type="hidden" name="typeOfSearch" value="all">
                    <input type="text" name="field" placeholder = "You want it... We got it ( ͡° ͜ʖ ͡°)">
                    <button class="waves-effect waves-light btn-large" type="submit" name="action">Search</button>
                </form>
            </div>
        </div>
    </div>
    <br><br>
</div>

<c:set var="field" scope = "request" value="${requestScope.field}"/>

