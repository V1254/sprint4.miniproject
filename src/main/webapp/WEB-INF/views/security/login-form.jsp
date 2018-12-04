<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Login page</title>
<style>
.error {
	color: red;
}
</style>
</head>
<body>
<h1>Login page</h1>

<p>
<c:if test="${error == true}">
	<b class="error">Invalid login or password.</b>
</c:if>
<c:if test="${logout == true}">
	<b class="logout">You have been logged out.</b>
</c:if>
</p>

	<c:url value="/login" var="loginUrl"/>
	<form action="${loginUrl}" method="post">       
		<c:if test="${param.error != null}">        
			<p>
				Invalid username and password.
			</p>
		</c:if>
		<c:if test="${param.logout != null}">       
			<p>
				You have been logged out.
			</p>
		</c:if>
		<p>
			<label for="username">Username</label>
			<input type="text" id="username" name="username"/>	
		</p>
		<p>
			<label for="password">Password</label>
			<input type="password" id="password" name="password"/>	
		</p>
		<input type="hidden"                        
			name="${_csrf.parameterName}"
			value="${_csrf.token}"/>
		<button type="submit" class="btn">Log in</button>
	</form>	

</body>
</html>