<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User Registration</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        
        .container {
            max-width: 500px;
            margin: 50px auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0,0,0,0.1);
        }
        
        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        label {
            display: block;
            margin-bottom: 5px;
            color: #333;
            font-weight: bold;
        }
        
        input[type="text"], 
        input[type="password"], 
        select {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
            box-sizing: border-box;
        }
        
        input[type="text"]:focus, 
        input[type="password"]:focus, 
        select:focus {
            border-color: #007bff;
            outline: none;
            box-shadow: 0 0 5px rgba(0,123,255,0.3);
        }
        
        .btn-container {
            text-align: center;
            margin-top: 30px;
        }
        
        input[type="submit"], 
        input[type="reset"] {
            background-color: #007bff;
            color: white;
            padding: 12px 30px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            margin: 0 10px;
            transition: background-color 0.3s;
        }
        
        input[type="reset"] {
            background-color: #6c757d;
        }
        
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
        
        input[type="reset"]:hover {
            background-color: #545b62;
        }
        
        .error-message {
            color: #dc3545;
            background-color: #f8d7da;
            border: 1px solid #f5c6cb;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
            text-align: center;
        }
        
        .success-message {
            color: #155724;
            background-color: #d4edda;
            border: 1px solid #c3e6cb;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
            text-align: center;
        }
        
        .login-link {
            text-align: center;
            margin-top: 20px;
        }
        
        .login-link a {
            color: #007bff;
            text-decoration: none;
        }
        
        .login-link a:hover {
            text-decoration: underline;
        }
        
        .required {
            color: red;
        }
        
        .form-help {
            font-size: 12px;
            color: #6c757d;
            margin-top: 5px;
        }
    </style>
    
    <script>
        function validateForm() {
            var userID = document.forms["registerForm"]["userID"].value;
            var fullName = document.forms["registerForm"]["fullName"].value;
            var password = document.forms["registerForm"]["password"].value;
            var confirmPassword = document.forms["registerForm"]["confirmPassword"].value;
            
            // Check empty fields
            if (userID == "" || fullName == "" || password == "") {
                alert("Please fill in all required fields!");
                return false;
            }
            
            // Check userID length
            if (userID.length < 3) {
                alert("User ID must be at least 3 characters long!");
                return false;
            }
            
            // Check fullName length
            if (fullName.length < 2) {
                alert("Full Name must be at least 2 characters long!");
                return false;
            }
            
            // Check password length
            if (password.length < 3) {
                alert("Password must be at least 3 characters long!");
                return false;
            }
            
            // Check password confirmation
            if (password != confirmPassword) {
                alert("Password confirmation does not match!");
                return false;
            }
            
            return true;
        }
        
        function clearForm() {
            document.getElementById("registerForm").reset();
        }
    </script>
</head>
<body>
    <div class="container">
        <h2>User Registration</h2>
        
        <!-- Display error message if exists -->
        <% 
            String errorMessage = (String) request.getAttribute("ERROR_MESSAGE");
            if (errorMessage != null) {
        %>
        <div class="error-message">
            <%= errorMessage %>
        </div>
        <% } %>
        
        <!-- Display success message if exists -->
        <% 
            String successMessage = (String) request.getAttribute("SUCCESS_MESSAGE");
            if (successMessage != null) {
        %>
        <div class="success-message">
            <%= successMessage %>
        </div>
        <% } %>
        
        <form name="registerForm" id="registerForm" action="RegisterController" method="POST" onsubmit="return validateForm()">
            <div class="form-group">
                <label for="userID">User ID <span class="required">*</span></label>
                <input type="text" id="userID" name="userID" value="<%= request.getParameter("userID") != null ? request.getParameter("userID") : "" %>" required>
                <div class="form-help">At least 3 characters, letters and numbers only</div>
            </div>
            
            <div class="form-group">
                <label for="fullName">Full Name <span class="required">*</span></label>
                <input type="text" id="fullName" name="fullName" value="<%= request.getParameter("fullName") != null ? request.getParameter("fullName") : "" %>" required>
                <div class="form-help">At least 2 characters</div>
            </div>
            
            <div class="form-group">
                <label for="roleID">Role</label>
                <select id="roleID" name="roleID">
                    <option value="US" <%= "US".equals(request.getParameter("roleID")) ? "selected" : "" %>>User</option>
                    <option value="AD" <%= "AD".equals(request.getParameter("roleID")) ? "selected" : "" %>>Admin</option>
                    <option value="MG" <%= "MG".equals(request.getParameter("roleID")) ? "selected" : "" %>>Manager</option>
                </select>
                <div class="form-help">Select your role in the system</div>
            </div>
            
            <div class="form-group">
                <label for="password">Password <span class="required">*</span></label>
                <input type="password" id="password" name="password" required>
                <div class="form-help">At least 3 characters</div>
            </div>
            
            <div class="form-group">
                <label for="confirmPassword">Confirm Password <span class="required">*</span></label>
                <input type="password" id="confirmPassword" name="confirmPassword" required>
                <div class="form-help">Re-enter your password</div>
            </div>
            
            <div class="btn-container">
                <input type="submit" value="Register" name="action">
                <input type="reset" value="Clear" onclick="clearForm()">
            </div>
        </form>
        
        <div class="login-link">
            <p>Already have an account? <a href="login.jsp">Login here</a></p>
        </div>
    </div>
</body>
</html>