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
        // Implementa una lógica similar para listar proyectos.
        // Llamar al método correspondiente en el modelo (si existe)
        return projectModel.listAllProjects(); // Este método debe implementarse en el modelo.
    }
    public void showVolunteersByProjects(int userId) {
        // Fetch projects created by the user (assuming "userId" refers to a publisher)
        List<Project> projects = projectModel.listProjectsByUserId(userId);

        // Iterate over each project and get the volunteers
        for (Project project : projects) {
            System.out.println("Project: " + project.getTitle());
            List<User> volunteers = projectModel.getVolunteersByProjectId(project.getId());
            if (volunteers.isEmpty()) {
                System.out.println("No volunteers for this project.");
            } else {
                System.out.println("Volunteers: ");
                for (User volunteer : volunteers) {
                    System.out.println(volunteer.getName());
                }
            }
            System.out.println();
        }
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
