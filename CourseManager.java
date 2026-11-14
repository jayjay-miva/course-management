import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class CourseManager {
    private static final String FILE_NAME = "courses.txt";

    private ArrayList<Course> courses = new ArrayList<>();
    private Scanner input;

    public CourseManager(Scanner input) {
        this.input = input;
    }

    public void addCourse() {
        // remove spaces so "csc 101" and "CSC101" are treated the same
        System.out.print("Enter course code: ");
        String code = input.nextLine().trim().toUpperCase().replace(" ", "");
        if (code.isEmpty()) {
            System.out.println("Course code cannot be empty.");
            return;
        }

        // check if the course is already added
        if (findCourse(code, 0) != null) {
            System.out.println("This course has already been added.");
            return;
        }

        System.out.print("Enter course title: ");
        String title = toTitleCase(input.nextLine().trim());
        if (title.isEmpty()) {
            System.out.println("Course title cannot be empty.");
            return;
        }

        System.out.print("Enter course unit: ");
        int unit;
        try {
            unit = Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid unit. Please enter a number.");
            return;
        }

        if (unit <= 0 || unit > 6) {
            System.out.println("Unit must be between 1 and 6.");
            return;
        }

        courses.add(new Course(code, title, unit));
        System.out.println(code + " added successfully.");
    }

    public void viewCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses added yet.");
            return;
        }

        System.out.println();
        printHeader();
        System.out.println("--------------------------------------------------");
        for (Course c : courses) {
            c.show();
        }
        System.out.println("--------------------------------------------------");
    }

    // recursive search, checks one course at a time
    private Course findCourse(String code, int index) {
        if (index >= courses.size()) {
            return null;
        }
        if (courses.get(index).getCode().equals(code)) {
            return courses.get(index);
        }
        return findCourse(code, index + 1);
    }

    public void searchCourse() {
        System.out.print("Enter course code to search: ");
        String code = input.nextLine().trim().toUpperCase().replace(" ", "");
        if (code.isEmpty()) {
            System.out.println("You did not enter a code.");
            return;
        }

        Course c = findCourse(code, 0);
        if (c == null) {
            System.out.println("Course not found.");
        } else {
            System.out.println("Course found:");
            printHeader();
            c.show();
        }
    }

    // recursive function to add up the units
    private int countUnits(int index) {
        if (index >= courses.size()) {
            return 0;
        }
        return courses.get(index).getUnit() + countUnits(index + 1);
    }

    public void totalUnits() {
        int total = countUnits(0);
        System.out.println("You have " + courses.size() + " course(s) with a total of " + total + " units.");
    }

    public void saveToFile() {
        if (courses.isEmpty()) {
            System.out.println("There is nothing to save.");
            return;
        }

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME));
            for (Course c : courses) {
                writer.println(c.getCode() + "|" + c.getTitle() + "|" + c.getUnit());
            }
            writer.close();
            System.out.println("Courses saved to " + FILE_NAME + ".");
        } catch (IOException e) {
            System.out.println("Error: could not save to file.");
        }
    }

    public void loadFromFile() {
        ArrayList<Course> loaded = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\|");
                if (parts.length != 3) {
                    System.out.println("Skipping bad line: " + line);
                    continue;
                }

                try {
                    loaded.add(new Course(parts[0], parts[1], Integer.parseInt(parts[2])));
                } catch (NumberFormatException e) {
                    System.out.println("Skipping bad line: " + line);
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("No saved file found. Save some courses first.");
            return;
        } catch (IOException e) {
            System.out.println("Error: could not read the file.");
            return;
        }

        courses = loaded;
        System.out.println(courses.size() + " course(s) loaded from " + FILE_NAME + ".");
    }

    private void printHeader() {
        System.out.printf("%-10s%-35s%s%n", "CODE", "TITLE", "UNIT");
    }

    // makes "intro to computing" into "Intro To Computing"
    private String toTitleCase(String text) {
        String[] words = text.toLowerCase().split("\\s+");
        String result = "";
        for (String w : words) {
            if (w.length() > 0) {
                result += Character.toUpperCase(w.charAt(0)) + w.substring(1) + " ";
            }
        }
        return result.trim();
    }
}
