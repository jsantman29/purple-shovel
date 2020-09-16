<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>


<t:genericpage>

	<jsp:attribute name="title">
      <title>User: ${requestScope.user.username}</title>
    </jsp:attribute>

    <jsp:attribute name="header">
    </jsp:attribute>

    <jsp:attribute name="footer">
    </jsp:attribute>

    <jsp:body>
        <div class="container">
            <div class="section center">

                <h2 class="header center purple-text"> Edit User: ${requestScope.user.username}   </h2>
                <h6 class="header center purple-text">${requestScope.message}</h6>
                <c:choose>
                    <c:when test="${requestScope.message == 'User successfully updated!'}">
                        <a href="userHub.jsp" class="waves-effect waves-light btn-large">Go Back To User Hub</a>
                    </c:when>
                </c:choose>
                <div class="card-panel blue-grey lighten-5">
                    <h5 class="header center purple-text">${requestScope.error}</h5>
                    <h5 class="header center purple-text">To make a change, you must fill all fields.</h5>
                    <br>
                    <div class="section">
                        <div class="row">
                            <div class="col s12">
                                <h5 class="center-align">User ID: ${requestScope.user.id}</h5>
                            </div>
                        </div>
                    </div>
                    <div class="divider"></div>
                    <br>
                    <form class="col s12" action="editUser" method="POST">
                        <div class="row">
                            <div class="input-field col s6">
                                <input placeholder="${requestScope.user.username}" name="username" id="username" type="text" class="validate">
                                <label for="username">Username</label>
                            </div>
                            <div class="input-field col s6">
                                <input placeholder="${requestScope.user.password}" name="password" id="password" type="text" class="validate">
                                <label for="password">Password</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="input-field col s2">
                                <select id="isAdmin" name="isAdmin">
                                    <option value="false">False</option>
                                    <option value="true">True</option>
                                </select>
                                <label>isAdmin</label>
                            </div>
                            <div class="input-field col s2">
                                <select id="isCSR" name="isCSR">
                                    <option value="false">False</option>
                                    <option value="true">True</option>
                                </select>
                                <label>isCSR</label>
                            </div>
                            <div class="input-field col s2">
                                <select id="isLocked" name="isLocked">
                                    <option value="false">False</option>
                                    <option value="true">True</option>
                                </select>
                                <label>isLocked</label>
                            </div>
                        </div>
                        <input type="hidden" name="userID" value="${requestScope.user.id}">
                        <button class="waves-effect waves-light btn-large" type="submit" name="action">Update</button>
                    </form>
                    <a href="staff.jsp" class="waves-effect waves-light btn-large">Cancel</a>
                </div>
            </div>
        </div>
        <br>
        <br>
        <br>
        <br>

    </jsp:body>

</t:genericpage>

