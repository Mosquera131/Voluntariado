package model;

import database.ConfigDB;
import entity.User;
import helper.Role;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserModel {
    public boolean registerUser(String name, String email, String password, String role) {
        Connection objConnection = ConfigDB.openConnection();

        try {
            // Verificar si el email ya está registrado
            String checkEmail = "SELECT * FROM users WHERE email = ?;";
            PreparedStatement checkEmailStmt = objConnection.prepareStatement(checkEmail);
            checkEmailStmt.setString(1, email);

            ResultSet emailResult = checkEmailStmt.executeQuery();
            if (emailResult.next()) {
                JOptionPane.showMessageDialog(null, "El correo electrónico ya está registrado.");
                return false;
            }

            // Insertar el nuevo usuario
            String insertUser = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?);";
            PreparedStatement insertStmt = objConnection.prepareStatement(insertUser);
            insertStmt.setString(1, name);
            insertStmt.setString(2, email);
            insertStmt.setString(3, password); // En una aplicación real, debes hashear la contraseña
            insertStmt.setString(4, role);

            int rowsAffected = insertStmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente.");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Hubo un error al registrar el usuario.");
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en el registro: " + e.getMessage());
            return false;
        } finally {
            ConfigDB.closeConnection();
        }
    }
    public boolean loginUser(String email, String password) {
        Connection objConnection = ConfigDB.openConnection();

        try {
            // Verificar si el correo electrónico y la contraseña son correctos
            String loginQuery = "SELECT * FROM users WHERE email = ?;";
            PreparedStatement loginStmt = objConnection.prepareStatement(loginQuery);
            loginStmt.setString(1, email);

            ResultSet result = loginStmt.executeQuery();
            if (result.next()) {
                String storedPassword = result.getString("password");
                String storedRole = result.getString("role");

                // Compara la contraseña almacenada (en texto plano o hasheada) con la proporcionada
                if (storedPassword !=null &&  storedPassword.equals(password)) {  // Aquí deberías usar una comparación segura (con hash)
                    JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso. Rol: " + storedRole);
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Contraseña incorrecta.");
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en el inicio de sesión: " + e.getMessage());
            return false;
        } finally {
            ConfigDB.closeConnection();
        }
    }
    public List<User> findAll() {
        List<User> listUsers = new ArrayList<>();
        Connection objConnection = ConfigDB.openConnection();

        try {
            String sql = "SELECT * FROM users;";
            PreparedStatement stmt = objConnection.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(Role.valueOf(resultSet.getString("role")));

                listUsers.add(user);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error retrieving users: " + e.getMessage());
        } finally {
            ConfigDB.closeConnection();
        }

        return listUsers;
    }


}
