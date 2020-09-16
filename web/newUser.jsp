<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage>

	<jsp:attribute name="title">
      <title>Create User Form</title>
    </jsp:attribute>

    <jsp:attribute name="header">
    </jsp:attribute>

    <jsp:attribute name="footer">
    </jsp:attribute>

    <jsp:body>
		<div class="container">
			<div class="section center">
				<h5 class="header center purple-text">Enter credentials here.</h5>
				<h5 class="header center purple-text">${requestScope.message}</h5>
				<form class="col s12" action="newuser" method="POST">
					<div class="row">
						<div class="input-field col s4 offset-s4">
							Username
							<textarea placeholder="Username" name="username" class="materialize-textarea"></textarea>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s4 offset-s4">
							Password
							<input placeholder="Password" name="password" type="password" class="validate">
						</div>
					</div>
					<div class="row">
						<div class="input-field col s4 offset-s4">
							Email
							<input placeholder="Email" name="email" type="text" class="validate">
						</div>
					</div>
					<div class="row">
						<c:if test="${sessionScope.isAdmin}">
							<div class="input-field col s12">
								<label for="one">
									<input id="one" type="checkbox" name="isCSR"/>
									<span><b>Customer Sales Representative</b></span>
								</label>
							</div>
						</c:if>
					</div>
					<button class="waves-effect waves-light btn-large" type="submit" name="action">Create</button>
				</form>
			</div>
		</div>
		<br />
    </jsp:body>

</t:genericpage>