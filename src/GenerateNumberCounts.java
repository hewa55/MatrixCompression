import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class GenerateNumberCounts {
    public void generate_sizes(int seed, int smallest, int largest, double sparsity){
        try{
            FileWriter writer;
            writer = new FileWriter("r_size_2.csv");

            IntMatrix matrix;
            GenerateTestData_Task2 data_task2 = new GenerateTestData_Task2();
            Random generator = new Random(1234);
            writer.append("size");
            writer.append(',');
            writer.append("numbers_stored_crs");
            writer.append(',');
            writer.append("numbers_stored_standard");
            writer.append('\n');

            for (int i = smallest; i < largest; i= i+5) {
                matrix= data_task2.new_sparse_matrix(generator,i,sparsity);
                int size = get_size_compressed(matrix);
                writer.append(Integer.toString(i));
                writer.append(',');
                writer.append(Integer.toString(size));
                writer.append(',');
                writer.append(Long.toString(i*i));
                writer.append('\n');
                System.out.println(i);
            }
            writer.flush();
            writer.close();
        } catch (IOException ex){
            System.out.println("should have done something proper");

        }
    }

    public void generate_sizes_sparsity(int seed, int mat_size, double sparsity){
        try{
            FileWriter writer;
            writer = new FileWriter("r_size_sparsity_2.csv");

            IntMatrix matrix;
            GenerateTestData_Task2 data_task2 = new GenerateTestData_Task2();
            Random generator = new Random(1234);
            writer.append("sparsity");
            writer.append(',');
            writer.append("numbers_stored_crs");
            writer.append(',');
            writer.append("numbers_stored_standard");
            writer.append('\n');

            for (double i = sparsity; i < 1.01; i= i+0.05) {
                matrix= data_task2.new_sparse_matrix(generator,mat_size,i);
                int size = get_size_compressed(matrix);
                writer.append(Double.toString(i));
                writer.append(',');
                writer.append(Integer.toString(size));
                writer.append(',');
                writer.append(Long.toString(mat_size*mat_size));
                writer.append('\n');
                System.out.println(i);
            }
            writer.flush();
            writer.close();
        } catch (IOException ex){
            System.out.println("should have done something proper");

        }
    }


    private int get_size_compressed(IntMatrix matrix){
        CRS crs = new CRS(matrix);
        return crs.value.length+crs.column_index.length+crs.row_pointer.length;
    }
}
