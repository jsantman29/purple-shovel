<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<div class="row"><h6 class="text-md-center purple-text">Item Properties</h6></div>
        <c:forEach items="${sessionScope.properties}" var="property" varStatus="rowCounter">
            <c:if test="${rowCounter.count % 4 == 1}">
                <div class="row">
            </c:if>
            <c:choose>
                <c:when test="${property.filter}">
                    <div class="input-field col s3">
                        <input placeholder="${property.propertyName}" name="prop.${property.propertyID}" id="${property.propertyID}" type="text" class="validate">
                        <label for="${property.propertyID}">${property.propertyName}</label>
                    </div>

                </c:when>
                <c:otherwise>
                    <div class="input-field col s3">
                    <select name="prop.${property.propertyID}" id="p${property.propertyID}">
                        <option value="" disabled selected>Select</option>
                        <option value="yes">Yes</option>
                        <option value="no">No</option>
                    </select>
                        <label for="${property.propertyID}">${property.propertyName}</label>
                    </div>
                </c:otherwise>
            </c:choose>
            <c:if test="${rowCounter.count % 4 == 0}">
                </div>
            </c:if>
        </c:forEach>
<div class = "row">
    <div class="input-field col s12">
    </div>
</div>

<script src="${pageContext.request.contextPath}js/jquery-3.4.0.js"></script>
<script src="${pageContext.request.contextPath}js/materialize.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        $('select').formSelect();
    });
</script>