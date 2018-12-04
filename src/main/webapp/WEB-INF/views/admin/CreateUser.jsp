<!DOCTYPE HTML>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<link rel="stylesheet" media="screen" href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <title>Create User</title>
    	<style>
	.error {
		color: #ff0000;
	}
	
	.errorblock{
		color: #000;
		background-color: #ffEEEE;
		border: 3px solid #ff0000;
		padding:8px;
		margin:16px;
	}
	</style>
</head>
<body>
<h1>Create User</h1>

<form:form method="POST" modelAttribute="orgUser" action="/admin/create">
   <table>
    <tr>
        <td><form:label path="login">Login</form:label></td>
        <td><form:input path="login" /></td>
        <td><form:errors path="login"  cssClass="error" /></td>
    </tr>
    <tr>
        <td><form:label path="password">Password</form:label></td>
        <td><form:password path="password" /></td>
        <td><form:errors path="password"  cssClass="error" /></td>
    </tr>
    <tr>
        <td><form:label path="password2">Repeat password</form:label></td>
        <td><form:password path="password2" /></td>
        <td><form:errors path="password2"  cssClass="error" /></td>
    </tr>
    <tr>
        <td><label>Role</label></td>
        <td><select name="roleName">
          <option value="MANAGER">Manager</option>
  		  <option value="ASSISTANT">Assistant</option>
		  <option value="ADMIN">Admin</option>
        </select></td>
    </tr>
    <tr>
        <td colspan="2">
            <input type="submit" value="Add" name="add" class="btn btn-default"/>
        </td>
        <td colspan="2">
            <input type="submit" value="Cancel" name="cancel" class="btn btn-default"/>
        </td>
    </tr>
</table>  
</form:form>
</body>
</html>
