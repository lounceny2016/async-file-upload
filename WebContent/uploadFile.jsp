<html>
<head>
<title>File Upload Form</title>
</head>
<body>
<b>Select a file to upload:</b><br />
<form action="UploadServlet" method="post" enctype="multipart/form-data">
<input type="file" name="file" size="50" />
<br />
<input type="submit" value="Upload" />
</form>
</body>
</html>