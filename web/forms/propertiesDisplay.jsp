<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<div id="showProperties">
<div class="row"><h6 class="text-md-center purple-text">Item Properties</h6></div>
<c:choose>
    <c:when test="${empty requestScope.properties}">
        <div class="text-body col s3">
            <b>No properties.</b>
        </div>
    </c:when>
    <c:otherwise>
        <c:forEach items="${requestScope.properties}" var="property" varStatus="rowCounter">
            <c:if test="${rowCounter.count % 4 == 1}">
                <div class="row">
            </c:if>
            <div class="text-body col s3">
                <b>${property.propertyName} </b>${property.value}
            </div>
            <c:if test="${rowCounter.count % 4 == 0}">
                </div>
            </c:if>
        </c:forEach>
    </c:otherwise>
</c:choose>
<div class = "row">
    <div class="input-field col s12">
    </div>
</div>
</div>



<script type="text/javascript">
    function show(elementId) {
        document.getElementById("id1").style.display="none";
        document.getElementById("id2").style.display="none";
        document.getElementById("id3").style.display="none";
        document.getElementById(elementId).style.display="block";
    }
</script>

