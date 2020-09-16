<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage>

	<jsp:attribute name="title">
      <title>Forums</title>
    </jsp:attribute>

    <jsp:attribute name="header">
    </jsp:attribute>

    <jsp:attribute name="footer">
    </jsp:attribute>

    <jsp:body>
        <div class="row"></div>
        <div class="container">
            <div class="row">
            <div class="container">
                <c:if test="${not empty sessionScope.username}">
                    <div class = "col s3">
                        <button class="waves-light btn-small" id="questionBar">Post</button>
                    </div>
                </c:if>
                <c:if test="${sessionScope.isAdmin or sessionScope.isCSR}">
                    <div class = "col s3">
                        <button class="waves-light btn-small" id="answerBar">Answer</button>
                    </div>
                </c:if>
                    <div class = "col s3">
                        <button class="waves-light btn-small" id="showSearchBar">Search</button>
                    </div>
                <div class = "col s3">
                    <form action="questionBoard" method="GET">
                    <button type="submit" class="waves-light btn-small">Show All</button>
                    </form>
                </div>
                </div>
            </div>
            </div>

                <div class="container"  id="questionDiv" style="display:none;">
                    <div class="card-panel blue-grey lighten-5">
                        <form class="col s12" action = "submitQuestion" method = "POST">
                            <div class = "row">
                                <input type="hidden" name="isCSR" value="false">
                                <input type="hidden" name="type" value="question">
                                <div class="input-field col s9"><input type="text" class="validate" name="questionText" placeholder = "ArchmageAntonidas.wav">
                                </div>
                            </div>
                            <div class = "row">
                                <button class="waves-light btn-small col s2 offset-s3" type="submit">Ask</button>
                                <button class="waves-light btn-small col s2 offset-s2" type="reset">Reset</button>
                            </div>
                        </form>
                    </div>
                </div>

        <div class="container" id="answerDiv" style="display:none;">
            <div class="card-panel blue-grey lighten-5">
                <form class="col s12" action = "submitQuestion" method = "POST">
                    <div class = "row">
                        <input type="hidden" name="isCSR" value="true">
                        <input type="hidden" name="type" value="answer">
                        <div class="input-field col s3"><input class="validate" type="number" min="0" name="questionID" placeholder="Question ID to Answer"></div>
                        <div class="input-field col s9"><input class="validate" type="text" name="answerText" placeholder = "ArchmageAntonidas.wav">
                        </div>
                    </div>
                    <div class = "row">
                        <button class="waves-light btn-small col s2 offset-s3" type="submit">Answer</button>
                        <button class="waves-light btn-small col s2 offset-s2" type="reset">Reset</button>
                    </div>
                </form>
            </div>
        </div>

            <div class="container" id="searchDiv" style="display:none;">
            <div class="card-panel blue-grey lighten-5">
                <form class="col s12" action = "search" method = "GET">
                <div class = "row">
                    <input type="hidden" name="typeOfSearch" value="question">
                    <div class="input-field col s3"><select id="searchType" name="searchType">
                        <option value="" disabled selected>Select</option>
                        <option value="all">All</option>
                        <option value="questions">Questions</option>
                        <option value="answers">Answers</option>
                        <option value="userID">UserID</option>
                    </select>
                        <label>Search Type</label></div>
                        <div id="all" class="fields" style="display:none"><div class="input-field col s9" id="other"><input class="validate" type="text" name="searchText" placeholder = "ArchmageAntonidas.wav"></div>
                        </div>
                        <div id="questions" class="fields" style="display:none"><div class="input-field col s9" id="other"><input class="validate" type="text" name="questionText" placeholder = "ArchmageAntonidas.wav"></div>
                        </div>
                        <div id="answers" class="fields" style="display:none"><div class="input-field col s9" id="other"><input class="validate" type="text" name="answerText" placeholder = "ArchmageAntonidas.wav"></div>
                        </div>
                        <div id="userID" class="fields" style="display:none"><div class="input-field col s9" id="inputUserID"><input class="validate" type="number" name="userID" placeholder = "ArchmageAntonidas.wav"></div>
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
        <div class="container">
        <h5 class="center red-text"><b>${requestScope.error}</b></h5>
            <h5 class="center green-text"><b>${requestScope.success}</b></h5>
        <table class="centered striped">
            <thead>
            <tr>
                <th>Question Number</th>
                <th>UserID</th>
                <th>Question</th>
                <th>Answer</th>
            </tr>
            </thead>
            <tbody>
            <%--    catching this messageList from the backend     --%>
            <c:forEach items="${requestScope.questionList}" var="questions">
                <tr>
                    <td>${questions.questionID}</td>
                    <td>${questions.userID}</td>
                    <td>${questions.questionText}</td>
                    <td>${questions.answerText}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </div>
        <br>
        <br>
        <br>
        <br>
        <script src="${pageContext.request.contextPath}/js/materialize.js"></script>
        <script src="${pageContext.request.contextPath}/js/jquery-3.4.0.js"></script>
        <script type="text/javascript">
            $(function() {
                $('#searchType').change(function(){
                    $('.fields').hide();
                    $('#' + $(this).val()).show();
                });
            });
            $('#showSearchBar').click(function() {
                $('#searchDiv').toggle('fast', function() {
                    // Animation complete.
                });
                $('#questionDiv').hide('fast', function() {
                    // Animation complete.
                });
                $('#answerDiv').hide('fast', function() {
                    // Animation complete.
                });
            });
            $('#questionBar').click(function() {
                $('#questionDiv').toggle('fast', function() {
                    // Animation complete.
                });
                $('#answerDiv').hide('fast', function() {
                    // Animation complete.
                });
                $('#searchDiv').hide('fast', function() {
                    // Animation complete.
                });
            });
            $('#answerBar').click(function() {
                $('#answerDiv').toggle('fast', function() {
                    // Animation complete.
                });
                $('#questionDiv').hide('fast', function() {
                    // Animation complete.
                });
                $('#searchDiv').hide('fast', function() {
                    // Animation complete.
                });
            });
        </script>
     
    </jsp:body>

</t:genericpage>