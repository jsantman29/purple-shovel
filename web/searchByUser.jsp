
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage>

	<jsp:attribute name="title">
      <title>Home</title>
    </jsp:attribute>

    <jsp:attribute name="header">
    </jsp:attribute>

    <jsp:attribute name="footer">
    </jsp:attribute>

    <jsp:body>
        <div class="container">
            <div class="section center">
                <h5 class="header center purple-text">Search for auctions by username.</h5>
                <h5 class="header center purple-text">${requestScope.message}</h5>
                <div class="card-panel blue-grey lighten-5">
                    <form class="col s12" action="search" method="GET">
                        <input type="hidden" name="typeOfSearch" value="participation">
                        <div class="row">
                            <div class="input-field col s12">
                                <textarea id="field" name="username" class="materialize-textarea"></textarea>
                                <label for="field">Enter Username</label>
                            </div>
                        </div>
                        <button class="waves-effect waves-light btn-large" type="submit" name="action">Search</button>
                        <button class="waves-effect waves-light btn-large" type="reset" onclick="return resetForm();">Reset</button>
                    </form>
                </div>
            </div>
        </div>

        <script src="${pageContext.request.contextPath}/js/jquery-3.4.0.js"></script>
        <script type="text/javascript">
            function resetForm(){
                setTimeout(function(){
                    $('#two').prop('disabled', true);
                }, 50);
                return true;
            }

            $('#one').change(function() {
                if (this.checked) {
                    $('#two').prop('disabled', false);
                }
                else{
                    $('#two').prop('disabled', true);
                }
            });
        </script>

    </jsp:body>
</t:genericpage>

