<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<!doctype html>
<html lang='nl'>
	<head>
		<title>Menu</title>
		<link rel='stylesheet'
			href='${pageContext.servletContext.contextPath}/styles/default.css'>
	</head>
	<body>
		<nav>
			<ul class='zonderbolletjes'>
				<li><a href="<c:url value='/filialen'/>">Filialen</a></li>
				<li><a href="<c:url value='/filialen/toevoegen'/>">
					Filiaal toevoegen</a></li>
				<li><a href="<c:url value='/werknemers'/>">Werknemers</a></li>
				<li><a href="<c:url value='/leningen/toevoegen'/>">
					Lening toevoegen</a></li>
			</ul>
		</nav>
	</body>
</html>