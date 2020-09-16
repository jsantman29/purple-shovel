<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="General Javascript" pageEncoding="UTF-8"%>

<script type="text/javascript">
    $(document).ready(function(){
        $('select').formSelect();
    });
    $('#textarea1').val('New Text');
    M.textareaAutoResize($('#textarea1'));
    $(document).ready(function(){
        $('.datepicker').datepicker({autoClose: true, format: 'yyyy/mm/dd'});
    });

    $(document).ready(function(){
        $('.timepicker').timepicker({twelveHour: false});
    });
</script>
