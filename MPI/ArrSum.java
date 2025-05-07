import mpi.MPI;

public class ArrSum {
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

/*Commands
 Go to extracted folder of MPI
In it go to bin and copy the path
Go to environment variable and change the path:
In it go to user variable and click on path and then new
Give Variable name as:MPJ_HOME 
Variable value:Path upto the version not bin

Go to System Variable and there new path double click and again new then give the name as %MPJ_HOME%\bin

Write the code
Go to the bin again and check for mpjrun file and copy its path
Go to the lib file and their check the mpj file and copy its path

Go to terminal and first command
javac -cp .;"C:\Users\sayal\Desktop\MPI\mpj-v0_44\lib\mpj.jar" ArrSum.java 

2."C:\Users\sayal\Desktop\MPI\mpj-v0_44\bin\mpjrun.bat"

 */