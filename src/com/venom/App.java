package com.venom;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class App {

    static final String driver = "com.mysql.cj.jdbc.Driver";
    static final String url = "jdbc:mysql://localhost:3306/venom_library";
    static final String username = "root";
    static final String password = "root";

    static Connection conn;
    static Statement stmt;
    static ResultSet rs;

    public static void main(String[] args) {

        try {

            Class.forName(driver);

            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.createStatement();

            while (!conn.isClosed()) {
                showMenu();
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void showMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;

        while (isContinue) {
            clearTerminal();

            System.out.println("===== Venom Library Book Database =====");
            System.out.println("1. \tBook List");
            System.out.println("2. \tSearch Book");
            System.out.println("3. \tAdd Book");
            System.out.println("4. \tUpdate Book");
            System.out.println("5. \tDelete Book");

            System.out.print("Select menu : ");
            byte userInput = scanner.nextByte();

            switch(userInput) {
                case 1:
                    bookList();
                    break;
                
                case 2:
                    //Do Something
                    break;

                case 3:
                    addBook();
                    break;

                case 4:
                    //Do Something
                    break;

                case 5:
                    //Do Something
                    break;

                default:
                    System.err.println("Wrong input (1-5) : ");
            }
            isContinue = prompt("Do you want to continue? (y/n) : ");
        }
    }

    private static void bookList() {
        String sqlQuery = "SELECT * FROM books";

        try {
            rs = stmt.executeQuery(sqlQuery);

            System.out.println("===== Book List =====");
            while (rs.next()) {

                /*
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String genre = rs.getString("genre");
                Date publishing_date = rs.getDate("publishing_date");
                String publisher = rs.getString("publisher");
                
                System.out.println(String.format("%d. | %-50s | %-20s | %-10s | %s | %s", id, title, author, genre, publishing_date, publisher));
                */

                Book book = new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getString("genre"), rs.getDate("publishing_date"), rs.getString("publisher"));
                System.out.println(String.format("%d. | %-50s | %-20s | %-10s | %s | %s", book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getPublishingDate(), book.getPublisher()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addBook() {
        Scanner scanner = new Scanner (System.in);
        boolean isInsertingData = true;
        boolean isDataCorrect = false;

        while (isInsertingData) {
            clearTerminal();
            try {
                while (!isDataCorrect) {
                    System.out.println("===== Add Book =====");
                    System.out.print("Title\t\t: ");
                    String title = scanner.nextLine().trim();

                    System.out.print("Author\t\t: ");
                    String author = scanner.nextLine().trim();

                    System.out.print("Genre\t\t: ");
                    String genre = scanner.nextLine().trim();

                    System.out.println("Publising Date\t: ");
                    System.out.print("(YYYY-MM-DD)\t:");
                    String publishingDate = scanner.nextLine().trim();

                    System.out.print("Publisher\t: ");
                    String publisher = scanner.nextLine().trim();

                    System.out.println("==================");

                    System.out.println("Title\t\t: " + title);
                    System.out.println("Author\t\t: " + author);
                    System.out.println("Genre\t\t: " + genre);
                    System.out.println("Publising Date\t: " + publishingDate);
                    System.out.println("Publisher\t: " + publisher);

                    isDataCorrect = prompt("Is the data you inserted correct? (y/n) : ");

                    String sqlQuery = "INSERT INTO books (title, author, genre, publishing_date, publisher) VALUE ('%s', '%s', '%s', '%s', '%s')";
                    sqlQuery = String.format(sqlQuery, title, author, genre, publishingDate, publisher);

                    stmt.execute(sqlQuery);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            isInsertingData = prompt("Do you want to add a new data again? (y/n) : ");
        }
    }


    private static boolean prompt(String message) {
        Scanner scanner = new Scanner (System.in);

        System.out.print(message);
        char userInput = scanner.next().charAt(0);

        if(Character.toLowerCase(userInput) == 'y')
            return true;
        else if (Character.toLowerCase(userInput) == 'n')
            return false;
        else
            return prompt(message);
    }

    private static void clearTerminal() {
        try {
            new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.err.print("Cannot clearing the terminal");
        }
    }

}
