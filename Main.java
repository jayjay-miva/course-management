import java.util.Scanner;

// Student Course Management System
// Simple console program to keep track of courses for the semester
public class Main {

    public static void showMenu() {
        System.out.println();
        System.out.println("===== STUDENT COURSE MANAGEMENT =====");
        System.out.println("1. Add Course");
        System.out.println("2. View All Courses");
        System.out.println("3. Search Course by Code");
        System.out.println("4. Compute Total Units");
        System.out.println("5. Save to File");
        System.out.println("6. Load from File");
        System.out.println("7. Exit");
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        CourseManager manager = new CourseManager(input);
        boolean running = true;

        while (running) {
            showMenu();
            System.out.print("Enter your choice (1-7): ");

            if (!input.hasNextLine()) {
                break;
            }
            String choice = input.nextLine().trim();

            switch (choice) {
                case "1":
                    manager.addCourse();
                    break;
                case "2":
                    manager.viewCourses();
                    break;
                case "3":
                    manager.searchCourse();
                    break;
                case "4":
                    manager.totalUnits();
                    break;
                case "5":
                    manager.saveToFile();
                    break;
                case "6":
                    manager.loadFromFile();
                    break;
                case "7":
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice, please pick a number from 1 to 7.");
            }
        }

        input.close();
    }
}
