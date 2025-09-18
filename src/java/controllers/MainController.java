/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author YourName
 */
@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    private static final String WELCOME = "login.jsp";
    private static final String LOGIN = "LoginController";
    private static final String REGISTER = "RegisterController";
    private static final String SEARCH_USER = "SearchUserController";
    private static final String UPDATE_USER = "UpdateUserController";
    private static final String DELETE_USER = "DeleteUserController";
    private static final String LOGOUT = "LogoutController";
    private static final String CHANGE_PASSWORD = "ChangePasswordController";
    
    // Event controllers
    private static final String SEARCH_EVENT = "SearchEventController";
    private static final String ADD_EVENT = "AddEventController";
    private static final String UPDATE_EVENT = "UpdateEventController";
    private static final String DELETE_EVENT = "DeleteEventController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = WELCOME;
        
        try {
            String action = request.getParameter("action");
            
            if (action == null) {
                url = WELCOME;
            } else {
                switch (action) {
                    // Authentication actions
                    case "Login":
                        url = LOGIN;
                        break;
                    case "Register":
                        url = REGISTER;
                        break;
                    case "Logout":
                        url = LOGOUT;
                        break;
                        
                    // User management actions
                    case "Search Users":
                    case "SearchUser":
                        url = SEARCH_USER;
                        break;
                    case "Update User":
                    case "UpdateUser":
                        url = UPDATE_USER;
                        break;
                    case "Delete User":
                    case "DeleteUser":
                        url = DELETE_USER;
                        break;
                    case "Change Password":
                    case "ChangePassword":
                        url = CHANGE_PASSWORD;
                        break;
                        
                    // Event management actions
                    case "Search Events":
                    case "SearchEvent":
                        url = SEARCH_EVENT;
                        break;
                    case "Add Event":
                    case "AddEvent":
                        url = ADD_EVENT;
                        break;
                    case "Update Event":
                    case "UpdateEvent":
                        url = UPDATE_EVENT;
                        break;
                    case "Delete Event":
                    case "DeleteEvent":
                        url = DELETE_EVENT;
                        break;
                        
                    default:
                        // Unknown action, redirect to welcome page
                        url = WELCOME;
                        log("Unknown action: " + action);
                        break;
                }
            }
            
        } catch (Exception e) {
            log("Error at MainController: " + e.toString());
            e.printStackTrace();
            url = WELCOME;
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Main Controller - Routes requests to appropriate controllers";
    }
}