<%-- 
    Document   : user_management
    Created on : Apr 26, 2025, 10:00:00 AM
    Author     : Computing Fundamental - HCM Campus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="models.UserDTO"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Management</title>
        <style>
            table { border-collapse: collapse; width: 100%; }
            th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
            th { background-color: #f2f2f2; }
            .form-section { margin: 20px 0; padding: 15px; border: 1px solid #ccc; }
            .error { color: red; }
            .success { color: green; }
        </style>
    </head>
    <body>
        <h2>User Management</h2>
        
        <!-- Display Error/Success Messages -->
        <%
            String errorMessage = (String) request.getAttribute("ERROR_MESSAGE");
            if (errorMessage != null) {
        %>
        <p class="error"><%= errorMessage %></p>
        <% } %>
        
        <%
            String successMessage = (String) request.getAttribute("SUCCESS_MESSAGE");
            if (successMessage != null) {
        %>
        <p class="success"><%= successMessage %></p>
        <% } %>
        
        <!-- ADD USER FORM -->
        <div class="form-section">
            <h3>Add New User</h3>
            <form action="MainController" method="post">
                <table>
                    <tr>
                        <td><label>User ID:</label></td>
                        <td><input type="text" name="userID" required/></td>
                    </tr>
                    <tr>
                        <td><label>Full Name:</label></td>
                        <td><input type="text" name="fullName" required/></td>
                    </tr>
                    <tr>
                        <td><label>Role:</label></td>
                        <td>
                            <select name="roleID">
                                <option value="US">User</option>
                                <option value="AD">Admin</option>
                                <option value="MG">Manager</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><label>Password:</label></td>
                        <td><input type="password" name="password" required/></td>
                    </tr>
                    <tr>
                        <td><label>Confirm Password:</label></td>
                        <td><input type="password" name="confirmPassword" required/></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="submit" name="action" value="Add User"/>
                            <input type="reset" value="Clear"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        
        <!-- SEARCH FORM -->
        <div class="form-section">
            <h3>Search Users</h3>
            <form action="MainController" method="post">
                <p>
                    <label>Search by User ID or Name:</label>
                    <input type="text" name="keyword" value="${KEYWORD}" placeholder="Enter User ID or Name"/>
                    <input type="submit" name="action" value="Search Users"/>
                    <input type="submit" name="action" value="Show All Users"/>
                </p>
            </form>
        </div>

        <!-- USER LIST TABLE -->
        <%
            List<UserDTO> userList = (List<UserDTO>) request.getAttribute("SEARCH_RESULT");
            if (userList != null && !userList.isEmpty()) {
        %>
        
        <div class="form-section">
            <h3>User List</h3>
            <table>
                <tr>
                    <th>User ID</th>
                    <th>Full Name</th>
                    <th>Role ID</th>
                    <th>Password</th>
                    <th>Actions</th>
                </tr>
                <%
                    for (UserDTO user : userList) {
                %>
                <tr>
                    <td><%= user.getUserID() %></td>
                    <td><%= user.getFullName() %></td>
                    <td><%= user.getRoleID() %></td>
                    <td><%= user.getPassword() %></td>
                    <td>
                        <a href="MainController?action=Update User&userID=<%= user.getUserID() %>">Edit</a>
                        |
                        <a href="MainController?action=Delete User&userID=<%= user.getUserID() %>" 
                           onclick="return confirm('Are you sure you want to delete user <%= user.getUserID() %>?')">Delete</a>
                    </td>
                </tr>
                <% } %>
            </table>
        </div>
        
        <% } else { %>
        <div class="form-section">
            <p>No users found! Click "Show All Users" to display all users.</p>
        </div>
        <% } %>
        
        <!-- UPDATE USER FORM (hiển thị khi có user được chọn để update) -->
        <%
            UserDTO userToUpdate = (UserDTO) request.getAttribute("USER_TO_UPDATE");
            if (userToUpdate != null) {
        %>
        
        <div class="form-section">
            <h3>Update User</h3>
            <form action="MainController" method="post">
                <table>
                    <tr>
                        <td><label>User ID:</label></td>
                        <td>
                            <input type="text" name="userID" value="<%= userToUpdate.getUserID() %>" readonly style="background-color: #f0f0f0;"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label>Full Name:</label></td>
                        <td><input type="text" name="fullName" value="<%= userToUpdate.getFullName() %>" required/></td>
                    </tr>
                    <tr>
                        <td><label>Role:</label></td>
                        <td>
                            <select name="roleID">
                                <option value="US" <%= "US".equals(userToUpdate.getRoleID()) ? "selected" : "" %>>User</option>
                                <option value="AD" <%= "AD".equals(userToUpdate.getRoleID()) ? "selected" : "" %>>Admin</option>
                                <option value="MG" <%= "MG".equals(userToUpdate.getRoleID()) ? "selected" : "" %>>Manager</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><label>Password:</label></td>
                        <td><input type="password" name="password" value="<%= userToUpdate.getPassword() %>" required/></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="submit" name="action" value="Update User"/>
                            <input type="button" value="Cancel" onclick="window.location.href='MainController?action=Show All Users'"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        
        <% } %>
        
        <!-- Navigation Links -->
        <hr/>
        <p>
            <a href="MainController?action=Show All Users">Refresh</a> |
            <a href="login.jsp">Logout</a>
        </p>
    </body>
</html>