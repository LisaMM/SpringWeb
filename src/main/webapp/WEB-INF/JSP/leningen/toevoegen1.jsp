<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='form' uri='http://www.springframework.org/tags/form'%>
<!doctype html>
<html lang='nl'>
	<head>
		<title>Lening toevoegen</title>
		<link rel='stylesheet'
			href='${pageContext.servletContext.contextPath}/styles/default.css'>
	</head>
	<body>
		<a href="<c:url value='/'/>">Menu</a>
		<h1>Lening toevoegen</h1>
		<h2>Stap 1</h2>
		<c:url value ="/leningen" var="url"/>
		<form:form action="${url}" method="post" commandName="lening">
			<form:label path="voornaam">Voornaam:
			<form:errors path="voornaam" cssClass="fout"/></form:label>
			<form:input path="voornaam" size="50" autofocus="autofocus"/>
			<form:label path="familienaam">Familienaam:
			<form:errors path="familienaam" cssClass="fout"/></form:label>
			<form:input path="familienaam" size="50"/>
			<form:label path="doel">Doel van de lening:
			<form:errors path="doel" cssClass="fout"/></form:label>
			<form:input path="doel" size="100"/>
			<input type='submit' value='Volgende stap' name='van1naar2'>
		</form:form>
	</body>
</html>