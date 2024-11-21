import controller.InscriptionController;
import controller.ProjectController;
import controller.UserController;
import database.ConfigDB;
import entity.Project;
import entity.User;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class Main {

    private static UserController userController = new UserController();
    private static ProjectController projectController = new ProjectController();
    private static InscriptionController inscriptionController = new InscriptionController();

    public static void main(String[] args) {
        ConfigDB.openConnection();
        ConfigDB.closeConnection();

        String email;
        String password;
        User userlogin = new User();
        boolean exit = false;

        do {
            String choice = menu();
            switch (choice) {
                case "1":
                    // Log in

                    email = JOptionPane.showInputDialog("Email please");

                    if (email == null) {
                        JOptionPane.showMessageDialog(null, "Operación cancelada.");
                        break; // Regresa al menú
                    }
                    password = JOptionPane.showInputDialog("Password please");

                    if (password == null) {
                        JOptionPane.showMessageDialog(null, "Operación cancelada.");
                        break; // Regresa al menú
                    }

                     userController.loginUser(email,password);


                    List<User> listUsers = userController.getAllUser();


                    boolean flagUser = false;

                    for (User user : listUsers) {
                        if (user.getEmail().equals(email) ) {
                            JOptionPane.showMessageDialog(null, "Logged in as " + user.getRole().name());
                            userlogin = user;
                          flagUser = true;

                            break;
                        }
                    }
                    System.out.println(flagUser);

                    if (!flagUser) {
                        JOptionPane.showMessageDialog(null, "User not found.");
                    }

                    while (flagUser) {

                        if (userlogin.getRole().name().equals("PUBLISHER")) {
                            String publisherChoice = menuPublisher();
                            switch (publisherChoice) {
                                case "1":
                                    String title = JOptionPane.showInputDialog("Enter the project title:");
                                    String description = JOptionPane.showInputDialog("Enter the project description:");
                                    String startDateStr = JOptionPane.showInputDialog("Enter the project start date (YYYY-MM-DD):");
                                    String endDateStr = JOptionPane.showInputDialog("Enter the project end date (YYYY-MM-DD):");

                                    LocalDate startDate = LocalDate.parse(startDateStr);
                                    LocalDate endDate = LocalDate.parse(endDateStr);

                                    projectController.createProject(userlogin.getId(), title, description, startDate, endDate);
                                    break;

                                case "2":
                                    JOptionPane.showMessageDialog(null, projectController.listAllProjects());

                                    break;

                                case "3":
                                    JOptionPane.showMessageDialog(null, projectController.showVolunteersByProjects(userlogin.getId()));

                                    break;

                                case "4":
                                    flagUser = false;
                                    break;
                                default:
                                    JOptionPane.showMessageDialog(null, "Invalid option.");
                            }
                        } else {
                            String volunteerChoice = menuVoluntary();
                            switch (volunteerChoice) {
                                case "1":
                                    String availableProjects = projectController.getAllString();
                                    JOptionPane.showMessageDialog(null, availableProjects.isEmpty() ? "No projects available." : availableProjects);
                                    break;
                                case "2":
                                    try {
                                        int projectId = Integer.parseInt(JOptionPane.showInputDialog("Enter the Project ID to join:"));
                                        inscriptionController.createInscription(userlogin.getId(), projectId);
                                    } catch (NumberFormatException e) {
                                        JOptionPane.showMessageDialog(null, "Invalid Project ID. Please enter a valid number.");
                                    }
                                    break;
                                case "3":
                                    String userProjects = inscriptionController.getUserProjectsString(userlogin.getId());
                                    JOptionPane.showMessageDialog(null, userProjects.isEmpty() ? "You are not part of any projects." : userProjects);
                                    break;
                                case "4":
                                    flagUser = false;
                                    break;
                                default:
                                    JOptionPane.showMessageDialog(null, "Invalid option.");
                            }
                        }
                    }
                    break;

                case "2":
                    userController.create();
                    break;

                case "3":
                    JOptionPane.showMessageDialog(null, "Have a good day!");
                    exit = true;
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Invalid option.");
            }
        } while (!exit);
    }

    public static String menu() {
        return JOptionPane.showInputDialog("""
                1. Log in
                2. Sign Up
                3. Exit
                
                Choose an option:
                """);
    }

    public static String menuVoluntary() {
        return JOptionPane.showInputDialog("""
                1. List available projects
                2. Join a project
                3. List projects you belong to
                4. Logout
                
                Choose an option:
                """);
    }

    public static String menuPublisher() {
        return JOptionPane.showInputDialog("""
                1. Create a project
                2. List all projects you have created
                3. List volunteers in your projects
                4. Logout
                
                Choose an option:
                """);
    }
}
