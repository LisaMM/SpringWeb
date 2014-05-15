<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<!DOCTYPE html>
<html lang="nl">
	<head>
		<title>Info</title>
		<link rel='stylesheet'
			href='${pageContext.servletContext.contextPath}/styles/default.css'>
	</head>
	<body>
		<a href="<c:url value='/'/>">Menu</a>
		<div>Deze website hoort bij de VDAB cursus over Spring.</div>
	</body>
</html>