package controller;

import entity.User;
import model.UserModel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    private UserModel userModel;

    public UserController() {
        userModel = new UserModel(); // Crear una instancia del modelo
    }
    public void create() {
        String name = JOptionPane.showInputDialog("Enter your name:");
        String email = JOptionPane.showInputDialog("Enter your email:");
        String password = JOptionPane.showInputDialog("Enter your password:");
        String role = JOptionPane.showInputDialog("Enter your role (VOLUNTEER or PUBLISHER):").toUpperCase();

        if (!role.equals("VOLUNTEER") && !role.equals("PUBLISHER")) {
            JOptionPane.showMessageDialog(null, "Invalid role. Please enter either 'VOLUNTEER' or 'PUBLISHER'.");
            return;
        }

        boolean success = registerUser(name, email, password, role);

        if (success) {
            JOptionPane.showMessageDialog(null, "User registered successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to register user. Please try again.");
        }
    }

    /**
     * Registra un nuevo usuario en el sistema.
     */
    public boolean registerUser(String name, String email, String password, String role) {
        // Llamar al método del modelo para registrar al usuario
        return userModel.registerUser(name, email, password, role);
    }

    /**
     * Inicia sesión con el correo electrónico y la contraseña proporcionada.
     */
    public boolean loginUser(String email, String password) {
        // Llamar al método del modelo para iniciar sesión
        return userModel.loginUser(email, password);
    }

    public static List<User> getAllUser(){
        UserModel objMode = new UserModel();
        List<User> listUsers = new ArrayList<>();
        for(Object iterator: objMode.findAll()){
            User objUser = (User) iterator;
            listUsers.add(objUser);
        }
        return  listUsers;
    }
}
