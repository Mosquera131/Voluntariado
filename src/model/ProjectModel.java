package model;

import database.ConfigDB;
import entity.Project;
import entity.User;
import helper.Role;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectModel {
    public boolean createProject(int userId, String title, String description, LocalDate startDate, LocalDate endDate) {
        Connection objConnection = ConfigDB.openConnection();

        try {
            // Verificar si el usuario es un Publicante
            String checkUserRole = "SELECT role FROM users WHERE id = ?;";
            PreparedStatement checkRoleStmt = objConnection.prepareStatement(checkUserRole);
            checkRoleStmt.setInt(1, userId);

            ResultSet roleResult = checkRoleStmt.executeQuery();
            if (roleResult.next() && "PUBLICANTE".equals(roleResult.getString("role"))) {
                // Si es un Publicante, insertamos el proyecto
                String insertProject = "INSERT INTO projects (title, description, start_date, end_date, created_by) VALUES (?, ?, ?, ?, ?);";
                PreparedStatement insertStmt = objConnection.prepareStatement(insertProject);
                insertStmt.setString(1, title);
                insertStmt.setString(2, description);
                insertStmt.setDate(3, Date.valueOf(startDate));
                insertStmt.setDate(4, Date.valueOf(endDate));
                insertStmt.setInt(5, userId);

                int rowsAffected = insertStmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Proyecto creado con éxito.");
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Error al crear el proyecto.");
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Solo los Publicantes pueden crear proyectos.");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear el proyecto: " + e.getMessage());
            return false;
        } finally {
            ConfigDB.closeConnection();
        }
    }
    // metodo para listar todos los proyectos disponibles

    public List<Project> listAllProjects() {
        List<Project> projects = new ArrayList<>();
        Connection objConnection = ConfigDB.openConnection();

        try {
            String sql = "SELECT * FROM projects;";
            PreparedStatement stmt = objConnection.prepareStatement(sql);

            ResultSet resultSet = stmt.executeQuery();
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
            JOptionPane.showMessageDialog(null, "Error al obtener los proyectos: " + e.getMessage());
        } finally {
            ConfigDB.closeConnection();
        }

        return projects;
    }
    // New method to list projects by a specific user
    public List<Project> listProjectsByUserId(int userId) {
        List<Project> projectList = new ArrayList<>();
        Connection conn = ConfigDB.openConnection();

        try {
            String sql = "SELECT * FROM projects WHERE created_by = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setTitle(resultSet.getString("title"));
                project.setDescription(resultSet.getString("description"));
                project.setStart_date(resultSet.getDate("start_date").toLocalDate());
                project.setEnd_date(resultSet.getDate("end_date").toLocalDate());
                projectList.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConfigDB.closeConnection();
        }

        return projectList;
    }
    // New method to get volunteers by project ID
    public List<User> getVolunteersByProjectId(int projectId) {
        List<User> volunteerList = new ArrayList<>();
        Connection conn = ConfigDB.openConnection();

        try {
            String sql = "SELECT users.* FROM users " +
                    "JOIN inscriptions ON users.id = inscriptions.user_id " +
                    "WHERE inscriptions.project_id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, projectId);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                User volunteer = new User();
                volunteer.setId(resultSet.getInt("id"));
                volunteer.setName(resultSet.getString("name"));
                volunteer.setEmail(resultSet.getString("email"));
                volunteer.setRole(Role.valueOf(resultSet.getString("role")));
                volunteerList.add(volunteer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConfigDB.closeConnection();
        }

        return volunteerList;
    }


}
