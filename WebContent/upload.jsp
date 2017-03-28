<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<title>KYC Information</title>
</head>
<link rel="stylesheet" href="/UploadServletApp/css/upload.css" type="text/css">

<body>
	<!-- sidebar menu -->
	<div id="mySidenav" class="sidenav">
	<div class="muskDiv" >
			<center style="padding-top: 0px;">
				<span class="muskSpan" >MUSK</span>
			</center>
	</div>
		<div class="adminDiv">
			<center style="padding-top: 30px;">
				<span style="font-size: larger;">Admin</span>
			</center>
		</div>
		<div>
			<a href="#"><i class="fa fa-home"><span class="spanContent">Home</span></i></a> <a
				href="#"><i class="fa fa-tasks"><span class="spanContent">Anchors</span></i></a> <a
				href="#"><i class="fa fa-users"><span class="spanContent">Suppliers</span></i></a> <a
				href="#"><i class="fa fa-file-text"><span class="spanContent">Profile</span></i></a>
		</div>
	</div>

	<!-- top navigation -->
	<div id="topNav" class="top_nav">
		<div style="float: right; padding-right: 3px; margin-bottom: 5px;">
			<i class="fa fa-sign-out">Logout</i>
		</div>
		<div style="float: right; margin-right: 2%; margin-bottom: 5px;">
			<i class="fa fa-bolt">Support</i>
		</div>
	</div>
	<!-- content tabs -->
	<div class="content_div" style="margin-left: 23%;">
		<h2 style="padding-left: 5px;">
			<strong>KYC Company</strong>
		</h2>
		<hr>
		<div class="inner_content_div">
			<h4 style="padding-left: 5px;">
				<strong>PAN CARD</strong>
			</h4>
			<div class="input_div">
				<div class="form-group row">
					<input class="form-control" type="text" id="example-text-input-pan"
						<%if (request.getMethod() == "POST") {%>
						value="<%=request.getAttribute("PAN")%>" onchange='checkValidation()'<%} else {%>
						placeholder="PAN No." <%}%>>
				</div>
				<div class="form-group row">
					<input class="form-control" type="text" id="example-text-input-name"
						<%if (request.getMethod() == "POST") {%>
						value="<%=request.getAttribute("NAME")%>"onchange='checkValidation()' <%} else {%>
						placeholder="Company Name" <%}%>>
				</div>
				<div class="form-group row">
					<input class="form-control" type="text" id="example-text-input-date"
						<%if (request.getMethod() == "POST") {%>
						value="<%=request.getAttribute("DATE")%>"<%} else {%>
						placeholder="Date Of Corporation" <%}%>>
				</div>
			</div>
			<div class="input_div">
				<div class="text-center">
					<form method="post" id="uploadFileFormSubmitId"
						action="UploadServlet" enctype="multipart/form-data">
						<!-- <button type="button" id="buttonId" style="margin-top: 10%;"
							class="fa fa-cloud-upload btn btn-primary "
							onclick='uploadFile()'>Upload</button> -->
							<input type="file"style="visibility:hidden;"
							 id="uploadfileId" name="uploadFile" onchange="this.form.submit()">
							<label for="uploadfileId" class="fa fa-cloud-upload btn btn-primary ">Upload</label>
						
						<!-- <input type="submit" id="uploadFileFormSubmitId" value="Upload" />  --> 
					</form>
					<p class="card-text">Or drag and Drop file</p>
				</div>
			</div>
		</div>

		<div class="inner_content_div">
			<h4 style="padding-left: 5px;">
				<strong>TIN </strong>
			</h4>
			<div class="input_div">
				<div class="form-group row">
					<input class="form-control" type="text" id="example-text-input"
						<%if (request.getMethod() == "POST") {%>
						value="<%=request.getAttribute("TIN")%>" <%} else {%>
						placeholder="TIN No." <%}%>>
				</div>

			</div>

		</div>

		<div class="inner_content_div">

			<h4 style="padding-left: 5px;"><strong>Address proof</strong></h4>
			<div class="form-group row">

				<div>
					&nbsp; &nbsp; &nbsp;<input type="radio" name="imgsel" value="" checked="">
					<lebel>Electricity Bill</lebel>
					&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
					&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input
						type="radio" name="imgsel" value="">
					<lebel>Water Bill</lebel>
					&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
					&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input
						type="radio" name="imgsel" value="">
					<lebel>Telephone Bill</lebel>
				</div>
			</div>
		</div>
	</div>
</body>
</html>