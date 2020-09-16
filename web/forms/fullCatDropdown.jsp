<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<div class="input-field col s6">
    <select name="catID" id="catID">
        <option value="" disabled selected>Select</option>
            <c:forEach items="${sessionScope.categories}" var="category">
                <optgroup label = "${category.name}">
                    <c:forEach items="${category.children}" var="subCategory">
                        <option value="${subCategory.id}">${subCategory.name}</option>
                    </c:forEach>
                </optgroup>
            </c:forEach>
    </select>
    <label>Item</label>
</div>

<c:set var="catID" scope="request" value="${requestScope.catID}"/>

<script src="${pageContext.request.contextPath}js/jquery-3.4.0.js"></script>
<script src="${pageContext.request.contextPath}js/materialize.js"></script>
