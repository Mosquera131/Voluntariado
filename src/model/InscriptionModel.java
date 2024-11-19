package model;

import database.ConfigDB;
import entity.Project;
import entity.User;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InscriptionModel {
    public boolean signUpForProject(int userId, int projectId) {
        Connection objConnection = ConfigDB.openConnection();

        try {
            // Verificar si el usuario es un voluntario
            String checkUserRole = "SELECT role FROM users WHERE id = ?;";
            PreparedStatement checkRoleStmt = objConnection.prepareStatement(checkUserRole);
            checkRoleStmt.setInt(1, userId);

            ResultSet roleResult = checkRoleStmt.executeQuery();
            if (roleResult.next() && "VOLUNTARIO".equals(roleResult.getString("role"))) {
                // Verificar si el voluntario ya está inscrito en el proyecto
                String checkInscription = "SELECT * FROM inscriptions WHERE user_id = ? AND project_id = ?;";
                PreparedStatement checkInscriptionStmt = objConnection.prepareStatement(checkInscription);
                checkInscriptionStmt.setInt(1, userId);
                checkInscriptionStmt.setInt(2, projectId);

                ResultSet inscriptionResult = checkInscriptionStmt.executeQuery();
                if (!inscriptionResult.next()) {
                    // El voluntario no está inscrito, procedemos a la inscripción
                    String insertInscription = "INSERT INTO inscriptions (user_id, project_id) VALUES (?, ?);";
                    PreparedStatement insertStmt = objConnection.prepareStatement(insertInscription);
                    insertStmt.setInt(1, userId);
                    insertStmt.setInt(2, projectId);
                    insertStmt.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Inscripción exitosa al proyecto.");
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Ya estás inscrito en este proyecto.");
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Solo los voluntarios pueden inscribirse en proyectos.");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la inscripción: " + e.getMessage());
            return false;
        } finally {
            ConfigDB.closeConnection();
        }
    }

    public List<Project> listInscriptionsForVolunteer(int userId) {
        List<Project> listProjects = new ArrayList<>();
        Connection objConnection = ConfigDB.openConnection();

        try {
            // Consulta para obtener todos los proyectos en los que el voluntario está inscrito
            String sql = "SELECT p.id, p.title, p.description, p.start_date, p.end_date FROM inscriptions i " +
                    "INNER JOIN projects p ON i.project_id = p.id WHERE i.user_id = ?;";
            PreparedStatement stmt = objConnection.prepareStatement(sql);
            stmt.setInt(1, userId);

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setTitle(resultSet.getString("title"));
                project.setDescription(resultSet.getString("description"));
                project.setStart_date(LocalDate.parse(resultSet.getString("start_date")));
                project.setEnd_date(LocalDate.parse(resultSet.getString("end_date")));
                listProjects.add(project);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener las inscripciones: " + e.getMessage());
        } finally {
            ConfigDB.closeConnection();
        }

        return listProjects;
    }

    public List<User> listVolunteersInProject(int projectId) {
        List<User> listUsers = new ArrayList<>();
        Connection objConnection = ConfigDB.openConnection();

        try {
            // Consulta para obtener todos los voluntarios inscritos en el proyecto
            String sql = "SELECT u.id, u.name, u.email FROM inscriptions i " +
                    "INNER JOIN users u ON i.user_id = u.id WHERE i.project_id = ?;";
            PreparedStatement stmt = objConnection.prepareStatement(sql);
            stmt.setInt(1, projectId);

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                listUsers.add(user);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener los voluntarios: " + e.getMessage());
        } finally {
            ConfigDB.closeConnection();
        }

        return listUsers;
    }
    public boolean createInscription(int userId, int projectId) {
        Connection objConnection = ConfigDB.openConnection();

        try {
            // Check if the user is already registered in the project
            String checkQuery = "SELECT * FROM inscriptions WHERE user_id = ? AND project_id = ?;";
            PreparedStatement checkStmt = objConnection.prepareStatement(checkQuery);
            checkStmt.setInt(1, userId);
            checkStmt.setInt(2, projectId);

            ResultSet resultSet = checkStmt.executeQuery();
            if (resultSet.next()) {
                JOptionPane.showMessageDialog(null, "You are already registered for this project.");
                return false;
            }

            // Insert the new inscription
            String insertQuery = "INSERT INTO inscriptions (user_id, project_id) VALUES (?, ?);";
            PreparedStatement insertStmt = objConnection.prepareStatement(insertQuery);
            insertStmt.setInt(1, userId);
            insertStmt.setInt(2, projectId);

            int rowsInserted = insertStmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Successfully registered for the project!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Failed to register for the project.");
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        } finally {
            ConfigDB.closeConnection();
        }
    }

    public List<Project> getProjectsByUserId(int userId) {
        Connection connection = ConfigDB.openConnection();
        List<Project> projects = new ArrayList<>();
        String query = "SELECT p.* FROM projects p " +
                "JOIN inscriptions i ON p.id = i.project_id " +
                "WHERE i.user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setTitle(resultSet.getString("title"));
                project.setDescription(resultSet.getString("description"));
                project.setStart_date(resultSet.getDate("start_date").toLocalDate());
                project.setEnd_date(resultSet.getDate("end_date").toLocalDate());
                projects.add(project);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving user projects: " + e.getMessage());
        } finally {
            ConfigDB.closeConnection();
        }

        return projects;
    }
}
