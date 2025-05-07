import java.util.*;

public class Ring {
    int max_processes;
    int coordinator;
    boolean processes[];
    ArrayList<Integer> pid;

    public Ring(int max) {
        max_processes = max;
        processes = new boolean[max];
        pid = new ArrayList<>();
        coordinator = max;

        for (int i = 0; i < max; i++) {
            processes[i] = true;
            System.out.println("P" + (i + 1) + " created.");
        }

        System.out.println("P" + coordinator + " is the coordinator");
    }

    void displayProcesses() {
        for (int i = 0; i < max_processes; i++) {
            System.out.println("P" + (i + 1) + " is " + (processes[i] ? "up" : "down"));
        }
        System.out.println("P" + coordinator + " is the coordinator");
    }

    void upProcess(int process_id) {
        if (!processes[process_id - 1]) {
            processes[process_id - 1] = true;
            System.out.println("Process P" + process_id + " is now up.");
        } else {
            System.out.println("Process P" + process_id + " is already up.");
        }
    }

    void downProcess(int process_id) {
        if (!processes[process_id - 1]) {
            System.out.println("Process P" + process_id + " is already down.");
        } else {
            processes[process_id - 1] = false;
            System.out.println("Process P" + process_id + " is down.");
        }
    }

    void displayArrayList(ArrayList<Integer> pid) {
        System.out.print("[ ");
        for (Integer x : pid) {
            System.out.print(x + " ");
        }
        System.out.println("]");
    }

    void initElection(int process_id) {
        if (!processes[process_id - 1]) {
            System.out.println("Process P" + process_id + " is down. Cannot initiate election.");
            return;
        }

        pid.clear();
        int current = process_id - 1;

        do {
            if (processes[current]) {
                pid.add(current + 1);
                System.out.print("Process P" + (current + 1) + " sending message. Current list: ");
                displayArrayList(pid);
            }
            current = (current + 1) % max_processes;
        } while (current != (process_id - 1));

        coordinator = Collections.max(pid);
        System.out.println("Process P" + process_id + " has declared P" + coordinator + " as the new coordinator.");
        pid.clear();
    }

    public static void main(String[] args) {
        Ring ring = null;
        int max_processes, process_id, choice;
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Ring Algorithm ---");
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
                    ring = new Ring(max_processes);
                    break;
                case 2:
                    ring.displayProcesses();
                    break;
                case 3:
                    System.out.print("Enter the process number to up: ");
                    process_id = sc.nextInt();
                    ring.upProcess(process_id);
                    break;
                case 4:
                    System.out.print("Enter the process number to down: ");
                    process_id = sc.nextInt();
                    ring.downProcess(process_id);
                    break;
                case 5:
                    System.out.print("Enter the process number to initiate election: ");
                    process_id = sc.nextInt();
                    ring.initElection(process_id);
                    break;
                case 6:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
