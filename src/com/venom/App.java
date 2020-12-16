package com.venom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import java.sql.ResultSet;
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
            System.out.println("2. \tAdd Book");
            System.out.println("3. \tUpdate Book");
            System.out.println("4. \tDelete Book");

            System.out.print("Select menu : ");
            byte userInput = scanner.nextByte();

            switch(userInput) {
                case 1:
                    bookList();
                    break;
                case 2:
                    addBook();
                    break;
                case 3:
                    updateBook();
                    break;
                case 4:
                    deleteBook();
                    break;
                default:
                    System.err.println("Wrong input (1-5) : ");
            }
            isContinue = prompt("Do you want to continue? (y/n) : ");
        }
    }

    private static void bookList() {
        clearTerminal();

        String sqlQuery = "SELECT * FROM books";

        try {
            rs = stmt.executeQuery(sqlQuery);

            System.out.println("===== Book List =====");
            System.out.println(" NO. | TITLE                                              | AUTHOR               | GENRE      | DATE       | PUBLISHER ");
            while (rs.next()) {
                Book book = new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getString("genre"), rs.getDate("publishing_date"), rs.getString("publisher"));
                System.out.println(String.format("%3d. | %-50s | %-20s | %-10s | %s | %s", book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getPublishingDate(), book.getPublisher()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addBook() {
        clearTerminal();

        Scanner scanner = new Scanner (System.in);

        boolean isInsertingData = true;
        boolean isDataCorrect = false;

        String title = "";
        String author = "";
        String genre = "";
        String publishingDate = "";
        String publisher = "";

        String sqlQuery = "INSERT INTO books (title, author, genre, publishing_date, publisher) VALUE ('%s', '%s', '%s', '%s', '%s')";

        while (isInsertingData) {
            try {
                while (!isDataCorrect) {
                    System.out.println("===== Add Book =====");
                    System.out.print("Title\t\t: ");
                    title = scanner.nextLine().trim();

                    System.out.print("Author\t\t: ");
                    author = scanner.nextLine().trim();

                    System.out.print("Genre\t\t: ");
                    genre = scanner.nextLine().trim();

                    System.out.println("Publising Date\t: ");
                    System.out.print("(YYYY-MM-DD)\t: ");
                    publishingDate = scanner.nextLine().trim();

                    System.out.print("Publisher\t: ");
                    publisher = scanner.nextLine().trim();

                    System.out.println("==================");

                    System.out.println("Title\t\t: " + title);
                    System.out.println("Author\t\t: " + author);
                    System.out.println("Genre\t\t: " + genre);
                    System.out.println("Publising Date\t: " + publishingDate);
                    System.out.println("Publisher\t: " + publisher);

                    isDataCorrect = prompt("Is the data you inserted is correct? (y/n) : ");
                }
                System.out.println("Data inserted...");
                sqlQuery = String.format(sqlQuery, title, author, genre, publishingDate, publisher);
                stmt.execute(sqlQuery);
            } catch (Exception e) {
                e.printStackTrace();
            }
            isInsertingData = prompt("Do you want to add another data? (y/n) : ");
        }
    }

    private static void updateBook() {
        bookList();
        
        Scanner scanner = new Scanner (System.in);

        boolean isUpdatingData = true;
        boolean isDataCorrect = false;

        int id = 0;
        String title = "";
        String author = "";
        String genre = "";
        String publishingDate = "";
        String publisher = "";

        String sqlQuery = "UPDATE books SET title='%s', author='%s', genre='%s', publishing_date='%s', publisher='%s' WHERE id=%d";

        while (isUpdatingData) {
            try {
                while (!isDataCorrect) {

                    System.out.println("===== Update Book =====");
                    System.out.print("Select which book id you want to update : ");
                    id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("==================");

                    System.out.print("Title\t\t: ");
                    title = scanner.nextLine().trim();

                    System.out.print("Author\t\t: ");
                    author = scanner.nextLine().trim();

                    System.out.print("Genre\t\t: ");
                    genre = scanner.nextLine().trim();

                    System.out.println("Publising Date\t: ");
                    System.out.print("(YYYY-MM-DD)\t: ");
                    publishingDate = scanner.nextLine().trim();

                    System.out.print("Publisher\t: ");
                    publisher = scanner.nextLine().trim();
                        
                    isDataCorrect = prompt("Is the data you updated is correct? (y/n) : ");
                }
                System.out.println("Data updated...");
                sqlQuery = String.format(sqlQuery, title, author, genre, publishingDate, publisher, id);
                stmt.execute(sqlQuery);
            } catch (Exception e) {
                e.printStackTrace();
            }
            isUpdatingData = prompt("Do you want to update another data? (y/n) : ");
        }
    }

    private static void deleteBook() {
        bookList();

        Scanner scanner = new Scanner (System.in);
        boolean isIdCorrect = false;
        String sqlQuery = "DELETE FROM books WHERE id = %d";
        int id = 0;

        try {
            while (!isIdCorrect) {
                System.out.print("Select which ID wants to be deleted : ");
                id = scanner.nextInt();
                isIdCorrect = prompt("Are you sure to delete book id " + id + "? (y/n) : ");
            }
            System.out.println("Book deleted...");
            sqlQuery = String.format(sqlQuery, id);
            stmt.execute(sqlQuery);
        } catch (Exception e) {
            e.printStackTrace();
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