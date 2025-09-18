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
import javax.servlet.http.HttpSession;
import models.UserDAO;
import models.UserDTO;

/**
 *
 * @author YourName
 */
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try {
            // Lấy parameters từ form
            String userID = request.getParameter("userID");
            String password = request.getParameter("password");
            String action = request.getParameter("action");
            
            // Kiểm tra action
            if (!"Login".equals(action)) {
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
            
            // Validation cơ bản
            if (userID == null || userID.trim().isEmpty() || 
                password == null || password.trim().isEmpty()) {
                request.setAttribute("ERROR_MESSAGE", "Please enter both User ID and Password");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
            
            // Lấy UserDAO instance và kiểm tra login
            UserDAO userDAO = UserDAO.getInstance();
            UserDTO foundUser = userDAO.login(userID.trim(), password);
            
            if (foundUser == null) {
                // Login failed
                request.setAttribute("ERROR_MESSAGE", "Invalid User ID or Password");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                // Login successful
                HttpSession session = request.getSession();
                session.setAttribute("LOGIN_USER", foundUser);
                session.setAttribute("USER_ID", foundUser.getUserID());
                session.setAttribute("USER_ROLE", foundUser.getRoleID());
                session.setAttribute("USER_NAME", foundUser.getFullName());
                
                // Log successful login
                log("User logged in: " + foundUser.getUserID() + " - " + foundUser.getFullName());
                
                // Redirect based on role
                String redirectPage = getRedirectPageByRole(foundUser.getRoleID());
                request.getRequestDispatcher(redirectPage).forward(request, response);
            }
            
        } catch (Exception e) {
            log("Error in LoginController: " + e.toString());
            e.printStackTrace();
            request.setAttribute("ERROR_MESSAGE", "System error occurred. Please try again later.");
            request.getRequestDispatcher("LoginJSP.jsp").forward(request, response);
        }
    }
    
    /**
     * Xác định trang chuyển hướng dựa trên role
     */
    private String getRedirectPageByRole(String roleID) {
        switch (roleID) {
            case "AD": // Admin
                return "admin_dashboard.jsp";
            case "MG": // Manager
                return "manager_dashboard.jsp";
            case "US": // User
            default:
                return "user_dashboard.jsp";
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
        return "Login Controller - Handles user authentication";
    }
}