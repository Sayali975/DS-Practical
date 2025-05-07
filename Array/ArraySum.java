import mpi.MPI;

public class ArraySum {
    public static void main(String[] args) throws Exception {
        MPI.Init(args);
       
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
       
        int unitsize = 5;
        int root = 0;
        int send_buffer[] = new int[unitsize * size];  // Allocate everywhere

        if (rank == root) {
            System.out.println("Distributing elements to processes:");
            for (int i = 0; i < unitsize * size; i++) {
                send_buffer[i] = i + 1;
                System.out.println("Element " + i + " = " + send_buffer[i]);
            }
        }

        int receive_buffer[] = new int[unitsize];
        int new_receive_buffer[] = new int[size];  // Allocate everywhere

        MPI.COMM_WORLD.Scatter(
            send_buffer, 0, unitsize, MPI.INT,
            receive_buffer, 0, unitsize, MPI.INT,
            root
        );

        int local_sum = 0;
        for (int i = 0; i < unitsize; i++) {
            local_sum += receive_buffer[i];
        }

        System.out.println("Intermediate sum at process " + rank + " is " + local_sum);

        MPI.COMM_WORLD.Gather(
            new int[]{local_sum}, 0, 1, MPI.INT,
            new_receive_buffer, 0, 1, MPI.INT,
            root
        );

        if (rank == root) {
            int total_sum = 0;
            for (int i = 0; i < size; i++) {
                total_sum += new_receive_buffer[i];
            }
            System.out.println("Final sum: " + total_sum);
        }

        MPI.Finalize();
    }
}