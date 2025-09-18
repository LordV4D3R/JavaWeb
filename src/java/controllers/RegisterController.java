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
import models.UserDAO;

/**
 *
 * @author YourName
 */
@WebServlet(name = "RegisterController", urlPatterns = {"/RegisterController"})
public class RegisterController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try {
            // Lấy parameters từ form
            String userID = request.getParameter("userID");
            String fullName = request.getParameter("fullName");
            String roleID = request.getParameter("roleID");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            String action = request.getParameter("action");
            
            // Kiểm tra action
            if (!"Register".equals(action)) {
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }
            
            // Validation cơ bản
            String validationError = validateInput(userID, fullName, roleID, password, confirmPassword);
            if (validationError != null) {
                request.setAttribute("ERROR_MESSAGE", validationError);
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }
            
            // Lấy UserDAO instance
            UserDAO userDAO = UserDAO.getInstance();
            
            // Thêm user mới
            String result = userDAO.addUser(userID, fullName, roleID, password);
            
            // Kiểm tra kết quả
            if (result.contains("successfully")) {
                // Đăng ký thành công
                request.setAttribute("SUCCESS_MESSAGE", result);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                // Có lỗi xảy ra
                request.setAttribute("ERROR_MESSAGE", result);
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            // Log lỗi và hiển thị thông báo
            log("Error in RegisterController: " + e.toString());
            e.printStackTrace();
            request.setAttribute("ERROR_MESSAGE", "System error occurred. Please try again later.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
    
    /**
     * Validation input từ form
     */
    private String validateInput(String userID, String fullName, String roleID, 
                                String password, String confirmPassword) {
        
        // Kiểm tra các field bắt buộc
        if (userID == null || userID.trim().isEmpty()) {
            return "User ID is required";
        }
        
        if (fullName == null || fullName.trim().isEmpty()) {
            return "Full Name is required";
        }
        
        if (password == null || password.trim().isEmpty()) {
            return "Password is required";
        }
        
        if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
            return "Password confirmation is required";
        }
        
        // Kiểm tra độ dài
        if (userID.trim().length() < 3) {
            return "User ID must be at least 3 characters long";
        }
        
        if (fullName.trim().length() < 2) {
            return "Full Name must be at least 2 characters long";
        }
        
        if (password.trim().length() < 3) {
            return "Password must be at least 3 characters long";
        }
        
        // Kiểm tra password confirmation
        if (!password.equals(confirmPassword)) {
            return "Password confirmation does not match";
        }
        
        // Kiểm tra userID format (chỉ cho phép chữ và số)
        if (!userID.matches("^[a-zA-Z0-9]+$")) {
            return "User ID can only contain letters and numbers";
        }
        
        // Kiểm tra roleID hợp lệ
        if (roleID == null || roleID.trim().isEmpty()) {
            roleID = "US"; // Default role
        }
        
        if (!roleID.equals("US") && !roleID.equals("AD") && !roleID.equals("MG")) {
            return "Invalid role selected";
        }
        
        return null; // No error
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
        return "Register Controller - Handles user registration";
    }
}