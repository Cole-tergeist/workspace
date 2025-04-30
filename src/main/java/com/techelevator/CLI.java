package com.techelevator;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.util.Scanner;

public class CLI {

    private static final String BASE_URL = "http://localhost:8080";

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Welcome to BirthMatch! ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Send Friend Request");
            System.out.println("4. View Friends");
            System.out.println("5. Schedule Appointment");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    registerUser(scanner);
                    break;
                case 2:
                    loginUser(scanner);
                    break;
                case 3:
                    sendFriendRequest();
                    break;
                case 4:
                    viewFriends();
                    break;
                case 5:
                    scheduleAppointment();
                    break;
                case 6:
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1-6.");
            }
        }
        scanner.close();
    }

    private static int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void registerUser(Scanner scanner) {
        try {
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            System.out.print("Enter role (ROLE_USER or ROLE_PROFESSIONAL): ");
            String role = scanner.nextLine();

            String jsonInputString = String.format(
                    "{\"name\":\"%s\", \"username\":\"%s\", \"password\":\"%s\", \"role\":\"%s\"}",
                    name, username, password, role
            );

            URL url = new URL(BASE_URL + "/register");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("User registered successfully!");
            } else {
                System.out.println("Failed to register user. Response Code: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loginUser(Scanner scanner) {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();


            System.out.print("\nEnter password: ");
            String password = scanner.nextLine();

            String jsonInputString = String.format(
                    "{\"username\":\"%s\", \"password\":\"%s\"}",
                    username, password
            );

            URL url = new URL(BASE_URL + "/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Failed to login. Response Code: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void sendFriendRequest() {
        System.out.println("Friend request not implemented yet.");
    }

    private static void viewFriends() {
        System.out.println("View friends not implemented yet.");
    }

    private static void scheduleAppointment() {
        System.out.println("Schedule appointment not implemented yet.");
    }
}