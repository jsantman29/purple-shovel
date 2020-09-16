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
		<c:choose>
			<c:when test="${sessionScope.isAdmin or sessionScope.isCSR}">
				<div class="container">
				<div class="section center">
				<div class="divider"></div>
				<div class="section">
					<div class="row">
						<div class="col s6">
							<h5 class="center">Current userID:</h5>
							<p class="light"> ${sessionScope.userID}</p>
						</div>
						<div class="col s6">
							<h5 class="center">Current user: </h5>
							<p class="light">${sessionScope.username}</p>
						</div>
					</div>
				</div>
				<div class="divider"></div>
				<div class="section">
					<div class="row">
						<div class="col s4 center">
							<a href="${pageContext.request.contextPath}/questionBoard">
								<button class="waves-light btn-large">Answer Questions</button>
							</a>
						</div>
						<div class="col s4 center">
							<button class="waves-light btn-large" id="showUserBar">Manage Users</button>
						</div>
						<div class="col s4 center">
							<c:url var="manageAuctions" value="search">
								<c:param name="typeOfSearch" value="manageAuctions"/>
							</c:url>
							<a href="${manageAuctions}">
								<button class="waves-light btn-large">Manage Auctions</button>
							</a>
						</div>
					</div>
						<div class="container" id="userDiv" style="display:none;">
							<div class="text-body center">${error}</div>
							<div class="card-panel blue-grey lighten-5">
								<form class="col s12" action = "search" method = "GET">
									<div class = "row">
										<input type="hidden" name="typeOfSearch" value="user">
										<div class="validate input-field col s3"><select id="searchType" name="searchType">
											<option value="" disabled selected>Select</option>
											<option value="username">Username</option>
											<option value="userID">UserID</option>
										</select>
											<label>Search Type</label></div>
										<div id="username" class="fields" style="display:none"><div class="input-field col s9"><input class="validate" type="text" name="username" placeholder = "Username"></div>
										</div>
										<div id="userID" class="fields" style="display:none"><div class="input-field col s3 offset-s6"><input class="validate" min="1.0" type="number" name="userID" placeholder = "UserID"></div>
										</div>
									</div>
									<div class = "row">
										<button class="waves-light btn-small col s2 offset-s3" type="submit">Submit</button>
										<button class="waves-light btn-small col s2 offset-s2" type="reset">Reset</button>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
				</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="section-center">
					<h5 class="header center purple-text">You shouldn't be here.</h5>
				</div>
			</c:otherwise>
		</c:choose>

		<script src="${pageContext.request.contextPath}/js/materialize.js"></script>
		<script src="${pageContext.request.contextPath}/js/jquery-3.4.0.js"></script>
		<script type="text/javascript">
			$(function() {
				$('#searchType').change(function(){
					$('.fields').hide();
					$('#' + $(this).val()).show();
				});
			});
			$('#showUserBar').click(function() {
				$('#userDiv').toggle('fast', function() {
					// Animation complete.
				});
			});
		</script>

    </jsp:body>

</t:genericpage>

