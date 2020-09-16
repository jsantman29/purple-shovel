<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="User Details" pageEncoding="UTF-8"%>
<%@attribute name="user" required="true" type="objs.User"%>

<c:url var="myURL" value="editUser">
    <c:param name="userID" value="${user.id}"/>
</c:url>

<a href="${myURL}" class="collection-item z-depth-2 border-dark" >
    <div class="row">
        <div class="col s3">
            <h6>UserID:</h6>
        </div>
        <div class="col s3">
            <h6>Username:</h6>
        </div>
        <div class="col s2">
            <h6>isLocked:</h6>
        </div>
        <div class="col s2">
            <h6>isAdmin:</h6>
        </div>
        <div class="col s2">
            <h6>isCSR:</h6>
        </div>
    </div>
    <div class="row">
        <div class="col s3">
            <h6>${user.id}</h6>
        </div>
        <div class="col s3">
            <h6>${user.username}</h6>
        </div>
        <div class="col s2">
            <h6>${user.locked}</h6>
        </div>
        <div class="col s2">
            <h6>${user.admin}</h6>
        </div>
        <div class="col s2">
            <h6>${user.CSR}</h6>
        </div>
    </div>
</a>
