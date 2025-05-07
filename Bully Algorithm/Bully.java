import java.util.*;

public class Bully {
    int coordinator;
    int max_processes;
    boolean processes[];

    public Bully(int max) {
        max_processes = max;
        processes = new boolean[max_processes];
        coordinator = max;

        System.out.println("Creating processes...");
        for (int i = 0; i < max; i++) {
            processes[i] = true;
            System.out.println("P" + (i + 1) + " created");
        }
        System.out.println("Process P" + coordinator + " is the coordinator");
    }

    void displayProcesses() {
        for (int i = 0; i < max_processes; i++) {
            System.out.println("P" + (i + 1) + " is " + (processes[i] ? "up" : "down"));
        }
        System.out.println("Process P" + coordinator + " is the coordinator");
    }

    void upProcess(int process_id) {
        if (!processes[process_id - 1]) {
            processes[process_id - 1] = true;
            System.out.println("Process " + process_id + " is now up.");
        } else {
            System.out.println("Process " + process_id + " is already up.");
        }
    }

    void downProcess(int process_id) {
        if (!processes[process_id - 1]) {
            System.out.println("Process " + process_id + " is already down.");
        } else {
            processes[process_id - 1] = false;
            System.out.println("Process " + process_id + " is down.");
        }
    }

    void runElection(int process_id) {
        if (!processes[process_id - 1]) {
            System.out.println("Process " + process_id + " is down and cannot start the election.");
            return;
        }

        System.out.println("Process " + process_id + " is initiating an election...");
        boolean higherExists = false;

        for (int i = process_id; i < max_processes; i++) {
            if (processes[i]) {
                System.out.println("Election message sent from process " + process_id + " to process " + (i + 1));
                higherExists = true;
            }
        }

        if (higherExists) {
            for (int i = max_processes - 1; i >= 0; i--) {
                if (processes[i]) {
                    coordinator = i + 1;
                    break;
                }
            }
        } else {
            coordinator = process_id;
        }

        System.out.println("Process " + coordinator + " is elected as the new coordinator.");
    }

    public static void main(String[] args) {
        Bully bully = null;
        int max_processes, process_id, choice;
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Bully Algorithm ---");
            System.out.println("1. Create processes");
            System.out.println("2. Display processes");
            System.out.println("3. Up a process");
            System.out.println("4. Down a process");
            System.out.println("5. Run election algorithm");
            System.out.println("6. Exit Program");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter the number of processes: ");
                    max_processes = sc.nextInt();
                    bully = new Bully(max_processes);
                    break;
                case 2:
                    bully.displayProcesses();
                    break;
                case 3:
                    System.out.print("Enter the process number to up: ");
                    process_id = sc.nextInt();
                    bully.upProcess(process_id);
                    break;
                case 4:
                    System.out.print("Enter the process number to down: ");
                    process_id = sc.nextInt();
                    bully.downProcess(process_id);
                    break;
                case 5:
                    System.out.print("Enter the process number which will perform election: ");
                    process_id = sc.nextInt();
                    bully.runElection(process_id);
                    bully.displayProcesses();
                    break;
                case 6:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
