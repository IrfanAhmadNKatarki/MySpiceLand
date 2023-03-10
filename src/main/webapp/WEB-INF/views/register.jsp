<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style>
</style>


<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
	 <link rel="stylesheet" href="/css/register.css" />
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    
    <div style="font-size: 3.6rem;"></div>
        <a href="/index"><div style="color:black;  font-size: 3.6rem;">Home</div></a>
        
      </nav>
	<style>



</style>
<body class="body">
	<center>
		<div class="container">
			<h1 class="form-title">Sign Up </h1>
			<form action="/register" method="post">

				<div class="main-user-info">

					<div class="user-input-box">
						<label for="fullName">Full Name</label> <input type="text"
							required id="fullName" name="name" placeholder="Enter Full Name" />
					</div>
					<div class="user-input-box">
						<label for="username">Username</label> <input type="text" required
							pattern="[A-Za-z]{3,10}"
							title="User name must be between 3-10 characters!!" id="username"
							name="username" placeholder="Enter Username" />
					</div>
					<div class="user-input-box">
						<label for="email">Email</label> <input type="email" id="email"
							required name="email" placeholder="Enter Email" />
					</div>
					<div class="user-input-box">
						<label for="phoneNumber">Phone Number</label> <input type="text"
							required id="phoneNumber" name="contact" pattern="^\d{10}$"
							title="Invalid Phone number!!" placeholder="Enter Phone Number" />
					</div>
					<div class="user-input-box">
						<label for="password">Password</label> <input type="password"
							required id="password" name="pass" placeholder="Enter Password"
							pattern="[A-Za-z0-9#@$&]{3,10}"
							title="Password must be between 3-10 characters and type only valid characters!!" />
					</div>
					<div class="user-input-box">
						<label for="confirmPassword">Confirm Password</label> <input
							type="password" id="confirmPassword" name="cnfmpass" required
							placeholder="Confirm Password" />
					</div>
					
					<!--  <h1 class="address">Address</h1>  -->
					 <br><br>
					<div class="user-input-box">
						<label for="confirmPassword">Street</label> <input type="text"
							id="address" name="street" required placeholder="Street" />
					</div>
					<div class="user-input-box">
						<label for="confirmPassword">Area</label> <input type="text"
							id="address" name="area" required placeholder="Area" />
					</div>
					<div class="user-input-box">
						<label for="confirmPassword">City</label> <input type="text"
							id="address" name="city" required placeholder="City" />
					</div>
					<div class="user-input-box">
						<label for="confirmPassword">Pincode</label> <input type="text"
							id="address" name="pincode"  pattern="^\d{6}$"  title="Invalid pincode enter 6 digit pincode!!" required placeholder="Pincode" />
					</div>
					
					
						<select id="vehicleList" name="role" required>
							
								<option value="" disabled selected>Select</option>
								<option value="ADMIN">ADMIN</option>
								<option value="USER">USER</option>
								<option value="DELIVERY">DELIVERY</option>
						</select>
						</div>

					<div class="form-submit-btn">
						<input type="submit" value="Submit and Back to Login">
					</div>
			</form>
<c:if test="${not empty error }">
						<div style="color: white;">
							<h3>${error}</h3>
						</div>
						<br />
					</c:if>

					<c:if test="${not empty error }">
						<div style="color:white;">
							<h3>${userna}</h3>
						</div>
						<br />
					</c:if>

			

			</form>
		</div>
	</center>
</body>
</html>

<!-- 

	<div class="container">
		<h1>Admin Registration Form:</h1>
		<div class="card">
			<div class="card-body">
				<form action="/admin/register" method="post">

					<div class="form-group row">
						<label for="name" class="col-sm-2 col-form-label"> Name</label>
						<div class="col-sm-7">
							<input type="text" class="form-control" name="name"
								placeholder="Enter name">
						</div>
					</div>




					<div class=" form-group row">
						<label for="email" class="col-sm-2 col-form-label"> Email</label>
						<div class="col-sm-7">
							<input type="text" class="form-control" name="email"
								placeholder="Enter Email">
						</div>
					</div>

					<div class=" form-group row">
						<label for="username" class="col-sm-2 col-form-label">
							Username</label>
						<div class="col-sm-7">
							<input type="text" class="form-control" name="username"
								placeholder="Enter Username">
						</div>
					</div>


					<div class="form-group row">
						<label for="password" class="col-sm-2 col-form-label">Password</label>
						<div class="col-sm-7">
							<input type="password" class="form-control" name="pass"
								placeholder="Enter Password">
						</div>
					</div>

					<div class="form-group row">
						<label for="cnfmpass" class="col-sm-2 col-form-label">Confirm
							Password</label>
						<div class="col-sm-7">
							<input type="password" class="form-control" name="cnfmpass"
								placeholder="Enter Password">
						</div>
					</div>

					<div class="form-group row">
						<label for="address" class="col-sm-2 col-form-label">Address</label>
						<div class="col-sm-7">
							<input type="text" class="form-control" name="address"
								placeholder="Enter Address">
						</div>
					</div>

					<div class="form-group row">
						<label for="contact" class="col-sm-2 col-form-label">Contact
							No</label>
						<div class="col-sm-7">
							<input type="text" class="form-control" name="contact"
								placeholder="Enter Contact Address">
						</div>
					</div>

					<button type="submit" class="btn btn-primary">Submit and
						Back to Login</button>

				</form>
				<br />
				<form action="/index" method="get">
					<button type="submit" class="btn btn-primary">Cancel</button>
				</form>
			</div>
		</div>
	</div>-->


