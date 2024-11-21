package controller;

import entity.Project;
import entity.User;
import model.ProjectModel;

import java.time.LocalDate;
import java.util.List;

public class ProjectController {
    private ProjectModel projectModel;

    public ProjectController() {
        projectModel = new ProjectModel(); // Crear una instancia del modelo
    }

    /**
     * Crear un nuevo proyecto si el usuario tiene el rol de Publicante.
     */
    public boolean createProject(int userId, String title, String description, LocalDate startDate, LocalDate endDate) {
        return projectModel.createProject(userId, title, description, startDate, endDate);
    }

    /**
     * Listar todos los proyectos disponibles.
     */
    public List<Project> listAllProjects() {

        return projectModel.listAllProjects();
    }

    public String showVolunteersByProjects(int userId) {
        StringBuilder result = new StringBuilder();

        // Obtener proyectos del modelo
        List<Project> projects = projectModel.listProjectsByUserId(userId);

        for (Project project : projects) {
            result.append("Project: ").append(project.getTitle()).append("\n");
            List<User> volunteers = projectModel.getVolunteersByProjectId(project.getId());

            if (volunteers.isEmpty()) {
                result.append("  No volunteers for this project.\n");
            } else {
                result.append("  Volunteers:\n");
                for (User volunteer : volunteers) {
                    result.append("    - ").append(volunteer.getName()).append("\n");
                }
            }
            result.append("\n");
        }

        return result.toString();
    }

    public String getAllString() {
        List<Project> projects = listAllProjects();
        if (projects.isEmpty()) {
            return "No projects available.";
        }

        StringBuilder projectsString = new StringBuilder("Available Projects:\n");
        for (Project project : projects) {
            projectsString.append("ID: ").append(project.getId())
                    .append(", Title: ").append(project.getTitle())
                    .append(", Description: ").append(project.getDescription())
                    .append("\n");
        }
        return projectsString.toString();
    }


}
