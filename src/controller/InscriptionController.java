package controller;

import entity.Project;
import entity.User;
import model.InscriptionModel;

import javax.swing.*;
import java.util.List;

public class InscriptionController {

    private InscriptionModel inscriptionModel;

    public InscriptionController() {
        inscriptionModel = new InscriptionModel(); // Crear una instancia del modelo
    }

    /**
     * Inscribirse en un proyecto si el usuario es un voluntario y no está ya inscrito.
     */
    public boolean signUpForProject(int userId, int projectId) {
        return inscriptionModel.signUpForProject(userId, projectId);
    }

    /**
     * Listar las inscripciones de un voluntario.
     */
    public List<Project> listInscriptionsForVolunteer(int userId) {
        return inscriptionModel.listInscriptionsForVolunteer(userId);
    }

    /**
     * Listar los voluntarios inscritos en un proyecto.
     */
    public List<User> listVolunteersInProject(int projectId) {
        return inscriptionModel.listVolunteersInProject(projectId);
    }

    public void createInscription(int userId, int projectId) {
        boolean success = inscriptionModel.createInscription(userId, projectId);
        if (success) {
            JOptionPane.showMessageDialog(null, "Successfully joined the project.");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to join the project.");
        }
    }
    public String getUserProjectsString(int userId) {
        List<Project> userProjects = inscriptionModel.getProjectsByUserId(userId); // Assuming this is implemented in `InscriptionModel`
        if (userProjects.isEmpty()) {
            return "No projects found for the user.";
        }

        StringBuilder projectsString = new StringBuilder("User Projects:\n");
        for (Project project : userProjects) {
            projectsString.append("ID: ").append(project.getId())
                    .append(", Title: ").append(project.getTitle())
                    .append(", Description: ").append(project.getDescription())
                    .append("\n");
        }
        return projectsString.toString();
    }



}
