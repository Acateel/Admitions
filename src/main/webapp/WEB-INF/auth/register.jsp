<%--
  Created by IntelliJ IDEA.
  User: Taras
  Date: 26.05.2022
  Time: 18:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <jsp:include page="../bootstrapHead.html"/>
</head>
<body>
<jsp:include page="../Navbar.jsp"/>

<form action="Registration" method="post">
    <div class="container">
        <h1>Register</h1>
        <p>Please fill in this form to create an account.</p>
        <hr>
        <em class="text-danger">* this is a required field</em>
        <br/>
        <label for="email">Email<span class="text-danger">*</span></label>
        <input type="text" class="form-control" placeholder="Enter Email" name="email" id="email" required>
        <br/>
        <label for="psw">Password<span class="text-danger">*</span></label>
        <input type="password" class="form-control" placeholder="Enter Password" name="psw" id="psw" required>
        <br/>
        <label for="psw-repeat">Repeat Password<span class="text-danger">*</span></label>
        <input type="password" class="form-control" placeholder="Repeat Password" name="psw-repeat" id="psw-repeat" required>
        <br/>
        <label for="lastname">Last Name<span class="text-danger">*</span></label>
        <input type="text" class="form-control" placeholder="Enter Last Name" name="lastname" id="lastname" required>
        <br/>
        <label for="firstname">First Name<span class="text-danger">*</span></label>
        <input type="text" class="form-control" placeholder="Enter First Name" name="firstname" id="firstname" required>
        <br/>
        <label for="surname">Surname<span class="text-danger">*</span></label>
        <input type="text" class="form-control" placeholder="Enter Surname" name="surname" id="surname" required>
        <br/>
        <label for="city">City<span class="text-danger">*</span></label>
        <input type="text" class="form-control" placeholder="Enter City" name="city" id="city" required>
        <br/>
        <label for="region">Region<span class="text-danger">*</span></label>
        <input type="text" class="form-control" placeholder="Enter Region" name="region" id="region" required>
        <br/>
        <label for="education">Name of educational institution<span class="text-danger">*</span></label>
        <input type="text" class="form-control" placeholder="Enter name" name="education" id="education" required>
        <br/>
        <label for="attestation">Attestation</label>
        <input type="file" class="form-control" placeholder="Select attestation" name="attestation" id="attestation" required>
        <hr>
        <button type="submit" class="button">Register</button>
    </div>

    <div class="container">
        <p>Already have an account? <a href="Login">Login</a>.</p>
    </div>
</form>
</body>
</html>
