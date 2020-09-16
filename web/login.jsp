<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage>

	<jsp:attribute name="title">
      <title>Login Form</title>
    </jsp:attribute>

    <jsp:attribute name="header">
    </jsp:attribute>

    <jsp:attribute name="footer">
    </jsp:attribute>

    <jsp:body>
        <div class="container">
            <div class="section center">
                <h5 class="header center purple-text">Please login with your username or email and password.</h5>
                <h5 class="header center purple-text">${requestScope.message}</h5>
                <form class="col s12" action="login" method="POST">
                    <div class="row">
                        <div class="input-field col s4 offset-s4">
                            Username
                            <input placeholder="Username" id="name" name="username" type="text" class="validate">
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s4 offset-s4">
                            Password
                            <input placeholder="Password" id="pass" name="password" type="password" class="validate">
                        </div>
                    </div>
                    <button class="waves-effect waves-light btn-large" type="submit" name="action">Login</button>
                </form>
            </div>
        </div>
    </jsp:body>

</t:genericpage>