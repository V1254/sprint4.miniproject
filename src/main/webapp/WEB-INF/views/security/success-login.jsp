<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Welcome page</title>
</head>
<body>
<h1>Welcome page</h1>
<p>You have successfully logged in.<br/>
<a href="${pageContext.request.contextPath}/">Home page</a><br/></p>
	<c:url value="/logout" var="logoutUrl"/>
	<form action="${logoutUrl}" method="get">       
		<input type="hidden"                        
			name="${_csrf.parameterName}"
			value="${_csrf.token}"/>
		<button type="submit" class="btn">Log out</button>
	</form>				
</body>
</html>