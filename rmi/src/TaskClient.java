import java.rmi.Naming;
import java.util.List;
import java.util.Scanner;

public class TaskClient {
    public static void main(String[] args) {
        try {
            // Look up the remote TaskServer object from the RMI registry
            TaskServer taskServer = (TaskServer) Naming.lookup("rmi://localhost:19/TaskServer");

            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("Choose an option:");
                System.out.println("1. Add Task");
                System.out.println("2. Remove Task");
                System.out.println("3. Show All Tasks");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                switch (choice) {
                    case 1:
                        System.out.print("Enter task to add: ");
                        String newTask = scanner.nextLine();
                        taskServer.addTask(newTask);
                        System.out.println("Task added successfully.");
                        break;
                    case 2:
                        System.out.print("Enter task to remove: ");
                        String taskToRemove = scanner.nextLine();
                        taskServer.removeTask(taskToRemove);
                        System.out.println("Task removed successfully.");
                        break;
                    case 3:
                        List<String> tasks = taskServer.getAllTasks();
                        if (tasks != null && !tasks.isEmpty()) {
                            System.out.println("All tasks:");
                            for (String task : tasks) {
                                System.out.println("- " + task);
                            }
                        } else {
                            System.out.println("No tasks available.");
                        }
                        break;
                    case 4:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            }
        } catch (Exception e) {
            System.err.println("TaskClient exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

