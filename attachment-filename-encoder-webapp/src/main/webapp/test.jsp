<%@page contentType="text/html; charset=UTF-8"%><%@include
	file="/taglibs.jsp"%><%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<html>
<head>
<title>Attachment filename encoder webapp</title>
</head>
<%
	String ext = StringUtils.defaultString(request.getParameter("ext"),
			"");

	List l = new ArrayList();

	if (request.getParameter("en") != null) {
		for (int i = 0; i <= 160; i++) {
			l.add(StringUtils.repeat("a", i) + ext);
		}
	}

	if (request.getParameter("zh") != null) {
		for (int i = 0; i <= 160; i++) {
			l.add(StringUtils.repeat("啊", i) + ext);
		}
	}

	if (request.getParameter("ja") != null) {
		for (int i = 0; i <= 160; i++) {
			l.add(StringUtils.repeat("あ", i) + ext);
		}
	}

	pageContext.setAttribute("filenames", l);
%>
<body>
<div>
<table border="1">
	<thead>
		<tr>
			<th>No.</th>
			<th>Original</th>
			<th>Encoded</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${filenames}" var="filename" varStatus="status">
			<c:url value="download" var="url">
				<c:param name="filename" value="${filename}" />
			</c:url>
			<c:url value="download" var="urlEncoded">
				<c:param name="filename" value="${filename}" />
				<c:param name="encode" value="true" />
			</c:url>
			<tr>
				<td>${status.index}</td>
				<td><a href="${url}">${filename}</a></td>
				<td><a href="${urlEncoded}">${filename}</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</div>
</body>
</html>