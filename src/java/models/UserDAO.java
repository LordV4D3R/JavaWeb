package business;

import models.UserDTO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserDAO extends ArrayList<UserDTO> {
    private static UserDAO instance;
    private static final String FILE_NAME = "users.cat";

    // Singleton pattern
    private UserDAO() {
        loadFromFile();
    }

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    // Thêm user mới từ web form
    public String addUser(String userID, String fullName, String roleID, String password) {
        try {
            // Validation
            String validationError = validateUserInput(userID, fullName, roleID, password);
            if (validationError != null) {
                return validationError;
            }

            // Kiểm tra userID đã tồn tại chưa
            if (findUserById(userID) != null) {
                return "User ID already exists: " + userID;
            }

            UserDTO newUser = new UserDTO(userID, fullName, roleID, password);
            this.add(newUser);
            
            // Auto save
            saveToFile();
            
            return "User added successfully with ID: " + userID;
        } catch (Exception e) {
            return "Error adding user: " + e.getMessage();
        }
    }

    // Update user
    public String updateUser(String userID, String fullName, String roleID, String password) {
        try {
            UserDTO user = findUserById(userID);
            
            if (user == null) {
                return "User with ID " + userID + " not found";
            }

            // Validation
            String validationError = validateUserInput(userID, fullName, roleID, password);
            if (validationError != null) {
                return validationError;
            }

            // Update thông tin
            user.setFullName(fullName);
            user.setRoleID(roleID);
            user.setPassword(password);

            // Auto save
            saveToFile();
            
            return "User updated successfully";
        } catch (Exception e) {
            return "Error updating user: " + e.getMessage();
        }
    }

    // Xóa user
    public String deleteUser(String userID) {
        try {
            UserDTO user = findUserById(userID);
            
            if (user == null) {
                return "User with ID " + userID + " not found";
            }

            this.remove(user);
            saveToFile();
            
            return "User deleted successfully";
        } catch (Exception e) {
            return "Error deleting user: " + e.getMessage();
        }
    }

    // Lấy tất cả users
    public ArrayList<UserDTO> getAllUsers() {
        return new ArrayList<>(this);
    }

    // Tìm user theo ID
    public UserDTO findUserById(String userID) {
        if (userID == null || userID.trim().isEmpty()) {
            return null;
        }
        
        for (UserDTO user : this) {
            if (user.getUserID().equals(userID.trim())) {
                return user;
            }
        }
        return null;
    }

    // Login - kiểm tra username và password
    public UserDTO login(String userID, String password) {
        if (userID == null || password == null) {
            return null;
        }
        
        UserDTO user = findUserById(userID);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    // Tìm users theo role
    public ArrayList<UserDTO> findUsersByRole(String roleID) {
        ArrayList<UserDTO> result = new ArrayList<>();
        
        if (roleID == null || roleID.trim().isEmpty()) {
            return result;
        }

        for (UserDTO user : this) {
            if (user.getRoleID().equals(roleID.trim())) {
                result.add(user);
            }
        }
        return result;
    }

    // Tìm users theo tên
    public ArrayList<UserDTO> findUsersByName(String fullName) {
        ArrayList<UserDTO> result = new ArrayList<>();
        
        if (fullName == null || fullName.trim().isEmpty()) {
            return result;
        }

        for (UserDTO user : this) {
            if (user.getFullName().toLowerCase().contains(fullName.toLowerCase().trim())) {
                result.add(user);
            }
        }
        return result;
    }

    // Đổi mật khẩu
    public String changePassword(String userID, String oldPassword, String newPassword) {
        try {
            UserDTO user = findUserById(userID);
            
            if (user == null) {
                return "User not found";
            }

            if (!user.getPassword().equals(oldPassword)) {
                return "Current password is incorrect";
            }

            if (newPassword == null || newPassword.trim().length() < 3) {
                return "New password must be at least 3 characters long";
            }

            user.setPassword(newPassword);
            saveToFile();
            
            return "Password changed successfully";
        } catch (Exception e) {
            return "Error changing password: " + e.getMessage();
        }
    }

    // Validation input
    private String validateUserInput(String userID, String fullName, String roleID, String password) {
        // Validate userID
        if (userID == null || userID.trim().isEmpty()) {
            return "User ID cannot be empty";
        }

        if (userID.trim().length() < 3) {
            return "User ID must be at least 3 characters long";
        }

        // Validate fullName
        if (fullName == null || fullName.trim().isEmpty()) {
            return "Full name cannot be empty";
        }

        if (fullName.trim().length() < 2) {
            return "Full name must be at least 2 characters long";
        }

        // Validate roleID
        if (roleID == null || roleID.trim().isEmpty()) {
            return "Role ID cannot be empty";
        }

        // Validate password
        if (password == null || password.trim().isEmpty()) {
            return "Password cannot be empty";
        }

        if (password.trim().length() < 3) {
            return "Password must be at least 3 characters long";
        }

        return null; // No error
    }

    // Save to file (private method, tự động gọi)
    private void saveToFile() {
        try (FileWriter fw = new FileWriter(FILE_NAME)) {
            for (UserDTO user : this) {
                fw.write(user.getUserID() + "," + 
                        user.getFullName() + "," + 
                        user.getRoleID() + "," + 
                        user.getPassword() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
        }
    }

    // Load from file (tự động gọi khi khởi tạo)
    private void loadFromFile() {
        File file = new File(FILE_NAME);
        
        if (!file.exists()) {
            // Tạo user admin mặc định nếu file chưa tồn tại
            this.add(new UserDTO("admin", "Administrator", "AD", "123"));
            saveToFile();
            return;
        }

        try (Scanner scFile = new Scanner(file)) {
            this.clear();
            
            while (scFile.hasNextLine()) {
                String userStr = scFile.nextLine().trim();
                
                if (userStr.isEmpty()) continue;

                String[] userData = userStr.split(",");
                
                if (userData.length != 4) continue; // Skip invalid lines

                try {
                    UserDTO user = new UserDTO(
                        userData[0].trim(),
                        userData[1].trim(),
                        userData[2].trim(),
                        userData[3].trim()
                    );

                    this.add(user);
                } catch (Exception e) {
                    System.err.println("Error parsing line: " + userStr);
                }
            }
            
            // Nếu không có user nào, tạo admin mặc định
            if (this.isEmpty()) {
                this.add(new UserDTO("admin", "Administrator", "AD", "123"));
                saveToFile();
            }
            
        } catch (FileNotFoundException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
    }

    // Method để force reload dữ liệu từ file (nếu cần)
    public void reloadFromFile() {
        loadFromFile();
    }

    // Method để force save (nếu cần)
    public String forceSave() {
        try {
            saveToFile();
            return "Data saved successfully";
        } catch (Exception e) {
            return "Error saving data: " + e.getMessage();
        }
    }

    // Kiểm tra userID có tồn tại không
    public boolean isUserIdExists(String userID) {
        return findUserById(userID) != null;
    }

    // Lấy số lượng users
    public int getUserCount() {
        return this.size();
    }

    // Lấy số lượng users theo role
    public int getUserCountByRole(String roleID) {
        return findUsersByRole(roleID).size();
    }

    // Kiểm tra user có phải admin không
    public boolean isAdmin(String userID) {
        UserDTO user = findUserById(userID);
        return user != null && "AD".equals(user.getRoleID());
    }

    // Lấy danh sách tất cả roles
    public ArrayList<String> getAllRoles() {
        ArrayList<String> roles = new ArrayList<>();
        for (UserDTO user : this) {
            if (!roles.contains(user.getRoleID())) {
                roles.add(user.getRoleID());
            }
        }
        return roles;
    }
}