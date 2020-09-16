<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<div class="input-field col s6">
    <select name="parentCatID" id="parentCatID">
        <option value="" disabled selected>Select</option>
        <c:forEach items="${sessionScope.categories}" var="category">
                <option value="${category.id}">${category.name}</option>
        </c:forEach>
    </select>
    <label>Item Type</label>
</div>

<c:set var="parentCatID" scope="request" value="${requestScope.parentCatID}"/>