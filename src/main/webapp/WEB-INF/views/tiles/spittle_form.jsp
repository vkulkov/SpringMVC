<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<div>
    <sec:authorize access="hasRole('ROLE_SPITTER')">
        <s:url value="/spittles" var="spittle_url"/>
        <sf:form modelAttribute="spittle" method="post" action="${spittle_url}">
            <sf:label path="text">
                <s:message code="label.spittle" text="Enter spittle:"/>
            </sf:label>
            <sf:textarea path="text" rows="2" cols="40"/>
            <sf:errors path="text"/>
        </sf:form>
        <br/>
        <div class="spitItSubmitIt">
            <input type="submit" value="Spit it!" class="status-btn round-btn disabled"/>
        </div>
    </sec:authorize>
</div>
