<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>



<!-- Pickers  -->
<div class="row">
    <div class="col s6">
        <input type="text" name="date" class="datepicker">
    </div>
    <div class="col s6">
        <input type="text" name="time" class="timepicker">
    </div>
</div>

<c:set var="date" scope="request" value="${requestScope.date}"/>
<c:set var="time" scope="request" value="${requestScope.time}"/>