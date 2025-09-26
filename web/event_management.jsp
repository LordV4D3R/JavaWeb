<%-- 
    Document   : event_management
    Created on : Apr 26, 2025, 10:30:00 AM
    Author     : Computing Fundamental - HCM Campus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="models.EventDTO"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Event Management</title>
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
        <h2>Event Management</h2>
        
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
        
        <!-- ADD EVENT FORM -->
        <div class="form-section">
            <h3>Add New Event</h3>
            <form action="MainController" method="post">
                <table>
                    <tr>
                        <td><label>Event ID:</label></td>
                        <td><input type="number" name="eventId" required/></td>
                    </tr>
                    <tr>
                        <td><label>Event Name:</label></td>
                        <td><input type="text" name="eventName" required/></td>
                    </tr>
                    <tr>
                        <td><label>Event Date (d-M-y):</label></td>
                        <td><input type="text" name="eventDate" placeholder="25-12-2025" required/></td>
                    </tr>
                    <tr>
                        <td><label>Location:</label></td>
                        <td><input type="text" name="location" placeholder="Must start with F, 6-11 chars" required/></td>
                    </tr>
                    <tr>
                        <td><label>Number of Attendees:</label></td>
                        <td><input type="number" name="numOfAttendee" min="1" required/></td>
                    </tr>
                    <tr>
                        <td><label>Status:</label></td>
                        <td>
                            <select name="status">
                                <option value="true">Active</option>
                                <option value="false">Inactive</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="submit" name="action" value="Add Event"/>
                            <input type="reset" value="Clear"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        
        <!-- SEARCH FORM -->
        <div class="form-section">
            <h3>Search Events</h3>
            <form action="MainController" method="post">
                <p>
                    <label>Search by Name or Location:</label>
                    <input type="text" name="keyword" value="${KEYWORD}" placeholder="Enter Event Name or Location"/>
                    <input type="submit" name="action" value="Search Events"/>
                    <input type="submit" name="action" value="Show All Events"/>
                    <input type="submit" name="action" value="Show Active Events"/>
                </p>
            </form>
        </div>

        <!-- EVENT LIST TABLE -->
        <%
            List<EventDTO> eventList = (List<EventDTO>) request.getAttribute("SEARCH_RESULT");
            if (eventList != null && !eventList.isEmpty()) {
        %>
        
        <div class="form-section">
            <h3>Event List</h3>
            <table>
                <tr>
                    <th>Event ID</th>
                    <th>Event Name</th>
                    <th>Event Date</th>
                    <th>Location</th>
                    <th>Attendees</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
                <%
                    for (EventDTO event : eventList) {
                %>
                <tr>
                    <td><%= event.getId() %></td>
                    <td><%= event.getName() %></td>
                    <td><%= event.getEventDate() != null ? event.getEventDate().toString() : "N/A" %></td>
                    <td><%= event.getLocation() %></td>
                    <td><%= event.getNumOfAttendee() %></td>
                    <td><%= event.isStatus() ? "Active" : "Inactive" %></td>
                    <td>
                        <a href="MainController?action=Update Event&eventId=<%= event.getId() %>">Edit</a>
                        |
                        <a href="MainController?action=Delete Event&eventId=<%= event.getId() %>" 
                           onclick="return confirm('Are you sure you want to delete event <%= event.getName() %>?')">Delete</a>
                    </td>
                </tr>
                <% } %>
            </table>
        </div>
        
        <% } else { %>
        <div class="form-section">
            <p>No events found! Click "Show All Events" to display all events.</p>
        </div>
        <% } %>
        
        <!-- UPDATE EVENT FORM (hiển thị khi có event được chọn để update) -->
        <%
            EventDTO eventToUpdate = (EventDTO) request.getAttribute("EVENT_TO_UPDATE");
            if (eventToUpdate != null) {
        %>
        
        <div class="form-section">
            <h3>Update Event</h3>
            <form action="MainController" method="post">
                <table>
                    <tr>
                        <td><label>Event ID:</label></td>
                        <td>
                            <input type="number" name="eventId" value="<%= eventToUpdate.getId() %>" readonly style="background-color: #f0f0f0;"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label>Event Name:</label></td>
                        <td><input type="text" name="eventName" value="<%= eventToUpdate.getName() %>" required/></td>
                    </tr>
                    <tr>
                        <td><label>Event Date (d-M-y):</label></td>
                        <td><input type="text" name="eventDate" value="<%= eventToUpdate.getEventDate() != null ? eventToUpdate.getEventDate().format(java.time.format.DateTimeFormatter.ofPattern("d-M-y")) : "" %>" required/></td>
                    </tr>
                    <tr>
                        <td><label>Location:</label></td>
                        <td><input type="text" name="location" value="<%= eventToUpdate.getLocation() %>" required/></td>
                    </tr>
                    <tr>
                        <td><label>Number of Attendees:</label></td>
                        <td><input type="number" name="numOfAttendee" value="<%= eventToUpdate.getNumOfAttendee() %>" min="1" required/></td>
                    </tr>
                    <tr>
                        <td><label>Status:</label></td>
                        <td>
                            <select name="status">
                                <option value="true" <%= eventToUpdate.isStatus() ? "selected" : "" %>>Active</option>
                                <option value="false" <%= !eventToUpdate.isStatus() ? "selected" : "" %>>Inactive</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="submit" name="action" value="Update Event"/>
                            <input type="button" value="Cancel" onclick="window.location.href='MainController?action=Show All Events'"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        
        <% } %>
        
        <!-- Navigation Links -->
        <hr/>
        <p>
            <a href="MainController?action=Show All Events">Refresh</a> |
            <a href="user_management.jsp">User Management</a> |
            <a href="login.jsp">Logout</a>
        </p>
    </body>
</html>