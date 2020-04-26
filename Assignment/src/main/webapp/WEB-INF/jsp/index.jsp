<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="pageTitle" value="Login"/>
<%@include file="common/header.jspf" %>

<h2>Please Sign Up</h2>

<c:url value="/index" var="signUpUrl"/>
<form:form modelAttribute="signUpAtt" action="${signUpUrl}" method="POST">

		<div class="form-group">
        		<form:label path="email">Email (Required)</form:label>
        		<form:input path="email" class="form-control"/>
        		<font color="red"><form:errors path="email" cssClass="error"/></font>
        		
        </div>
		<div class="form-group">
        		<form:label path="firstName">First Name (Required)</form:label>
        		<form:input path="firstName" class="form-control"/>
        		<font color="red"><form:errors path="firstName" cssClass="error"/></font>
        		
        </div>
        <div class="form-group">
        		<form:label path="lastName">Last Name</form:label>
        		<form:input path="lastName" class="form-control"/>
        		
        		
        </div>
        <div class="form-group">
        		<form:label path="phoneNumber">Phone Number (Prefer XXX-XXX-XXXX)</form:label>
        		<form:input path="phoneNumber" class="form-control"/>
        	    		
        </div>
		       
        <input type="submit" value="Sign Up" class="btn btn-primary"/>

</form:form>

<%@include file="common/footer.jspf" %>