<%@page contentType="text/html; charset=UTF-8"%><%@include
	file="/taglibs.jsp"%>
<html>
<head>
<title>Attachment filename encoder webapp</title>
</head>
<body>
<div>
<h1>Attachment filename encoder test web application</h1>
<table border="1">
	<thead>
		<tr>
			<th>Without extension</th>
			<th>With extension ".txt"</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td><a href="test.jsp?en">English test</a></td>
			<td><a href="test.jsp?en&ext=.txt">English test</a></td>
		</tr>
		<tr>
			<td><a href="test.jsp?zh">Chinese test</a></td>
			<td><a href="test.jsp?zh&ext=.txt">Chinese test</a></td>
		</tr>
		<tr>
			<td><a href="test.jsp?ja">Japanese test</a></td>
			<td><a href="test.jsp?ja&ext=.txt">Japanese test</a></td>
		</tr>
	</tbody>
</table>
</div>
</body>
</html>