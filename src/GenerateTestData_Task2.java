import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import static java.lang.StrictMath.round;

public class GenerateTestData_Task2 {

    public void generateTestData_base_case(int seed, int smallest, int largest){
        try {
            BasicMultiplier multiplier = new BasicMultiplier();
            Random generator = new Random(seed);
            FileWriter writer;
            writer = new FileWriter("r_analysis_2.csv");

            writer.append("mat_size");
            writer.append(',');
            writer.append("avg times");
            writer.append('\n');
            int no_samples = 10;
            for (int i = smallest; i < largest; i= i+1) {
                long duration=0;
                for (int j = 0; j < no_samples; j++) {
                    // for random matrices
                    IntMatrix matrix1 = newMatrix(generator, i);
                    IntMatrix matrix2 = newMatrix(generator, i);

                    duration += getTime_standard(matrix1,matrix2,multiplier);
                }
                writer.append(Integer.toString(i));
                writer.append(',');
                writer.append(Long.toString(duration/no_samples));
                writer.append('\n');
                System.out.println(i);
            }
            writer.flush();
            writer.close();
        } catch (IOException ex){
            System.out.println("should have done something proper");

        }
    }

    public void generateTestData_time_sparsity(int seed, int mat_size, double sparsity){
        try {
            BasicMultiplier multiplier = new BasicMultiplier();
            Random generator = new Random(seed);
            FileWriter writer;
            writer = new FileWriter("r_time_sparsity_2.csv");

            writer.append("mat_size");
            writer.append(',');
            writer.append("sparsity");
            writer.append(',');
            writer.append("time_in_ns_native");
            writer.append(',');
            writer.append("time_in_ns_advanced");
            writer.append(',');
            writer.append("time_in_ns_crs");
            writer.append('\n');

            int no_samples = 4;

            for (double i = sparsity; i < 1.01; i= i+0.02) {
                long duration2=0;
                long duration3=0;
                long duration4 = 0;
                for (int j = 0; j < no_samples; j++) {
                    IntMatrix matrix1 = new_sparse_matrix(generator, mat_size,i);
                    IntMatrix matrix2 = new_sparse_matrix(generator, mat_size,i);

                    duration2 += getTime_standard(matrix1,matrix2,multiplier);
                    duration3 += getTime_skip_zero_advanced(matrix1,matrix2,multiplier);
                    duration4 += getTime_crs(matrix1,matrix2,multiplier);
                }
                writer.append(Integer.toString(mat_size));
                writer.append(',');
                writer.append(Double.toString(i));
                writer.append(',');
                writer.append(Long.toString(duration2/no_samples));
                writer.append(',');
                writer.append(Long.toString(duration3/no_samples));
                writer.append(',');
                writer.append(Long.toString(duration4/no_samples));
                writer.append('\n');
                System.out.println(i);
            }
            writer.flush();
            writer.close();
        } catch (IOException ex){
            System.out.println("should have done something proper");

        }
    }


    public void generateTestData_skip_zero(int seed, int smallest, int largest, double sparsity){
        try {
            BasicMultiplier multiplier = new BasicMultiplier();
            Random generator = new Random(seed);
            FileWriter writer;
            writer = new FileWriter("r_basic_skip_zero_2.csv");

            writer.append("mat_size");
            writer.append(',');
            writer.append("avg_skip_zero");
            writer.append(',');
            writer.append("avg_native");
            writer.append(',');
            writer.append('\n');
            int no_samples = 10;
            for (int i = smallest; i < largest; i= i+1) {
                long duration=0;
                long duration2=0;

                for (int j = 0; j < no_samples; j++) {
                    // for random matrices
                    IntMatrix matrix1 = new_sparse_matrix(generator, i,sparsity);
                    IntMatrix matrix2 = new_sparse_matrix(generator, i,sparsity);

                    duration += getTime_skip_zero(matrix1,matrix2,multiplier);
                    duration2 += getTime_standard(matrix1,matrix2,multiplier);

                }
                writer.append(Integer.toString(i));
                writer.append(',');
                writer.append(Long.toString(duration/no_samples));
                writer.append(',');
                writer.append(Long.toString(duration2/no_samples));
                writer.append('\n');
                System.out.println(i);
            }
            writer.flush();
            writer.close();
        } catch (IOException ex){
            System.out.println("should have done something proper");

        }
    }

    public void generateTestData_all_alg(int seed, int smallest, int largest, double sparsity){
        try {
            BasicMultiplier multiplier = new BasicMultiplier();
            Random generator = new Random(seed);
            FileWriter writer;
            writer = new FileWriter("r_all_alg_2.csv");

            writer.append("mat_size");
            writer.append(',');
            writer.append("avg_skip_basic");
            writer.append(',');
            writer.append("avg_native");
            writer.append(',');
            writer.append("avg_skip_advanced");
            writer.append(',');
            writer.append("time_in_ns_crs");
            writer.append('\n');
            int no_samples = 10;
            for (int i = smallest; i < largest; i= i+3) {
                long duration=0;
                long duration2=0;
                long duration3=0;
                long duration4 = 0;
                for (int j = 0; j < no_samples; j++) {
                    // for random matrices
                    IntMatrix matrix1 = new_sparse_matrix(generator, i,sparsity);
                    IntMatrix matrix2 = new_sparse_matrix(generator, i,sparsity);

                    duration += getTime_skip_zero(matrix1,matrix2,multiplier);
                    duration2 += getTime_standard(matrix1,matrix2,multiplier);
                    duration3 += getTime_skip_zero_advanced(matrix1,matrix2,multiplier);
                    duration4 += getTime_crs(matrix1,matrix2,multiplier);
                }
                writer.append(Integer.toString(i));
                writer.append(',');
                writer.append(Long.toString(duration/no_samples));
                writer.append(',');
                writer.append(Long.toString(duration2/no_samples));
                writer.append(',');
                writer.append(Long.toString(duration3/no_samples));
                writer.append(',');
                writer.append(Long.toString(duration4/no_samples));
                writer.append('\n');
                System.out.println(i);
            }
            writer.flush();
            writer.close();
        } catch (IOException ex){
            System.out.println("should have done something proper");

        }
    }


    private long getTime_standard(IntMatrix matrix1, IntMatrix matrix2, BasicMultiplier multiplier){
        long startTime = System.nanoTime();
        IntMatrix matrix = multiplier.multiply(matrix1,matrix2);
        long endTime = System.nanoTime();
        return endTime-startTime;
    }
    private long getTime_skip_zero(IntMatrix matrix1, IntMatrix matrix2, BasicMultiplier multiplier){
        long startTime = System.nanoTime();
        IntMatrix matrix = multiplier.multiply_skip_zero(matrix1,matrix2);
        long endTime = System.nanoTime();
        return endTime-startTime;
    }

    private long getTime_skip_zero_advanced(IntMatrix matrix1, IntMatrix matrix2, BasicMultiplier multiplier){
        long startTime = System.nanoTime();
        IntMatrix matrix = multiplier.multiply_skip_zero_changed_iterations(matrix1,matrix2);
        long endTime = System.nanoTime();
        return endTime-startTime;
    }

    private long getTime_crs(IntMatrix matrix1, IntMatrix matrix2, BasicMultiplier multiplier){
        CRS mat1 = new CRS(matrix1);
        CRS mat2 = new CRS(matrix2);
        long startTime = System.nanoTime();
        IntMatrix matrix = multiplier.multiply_crs(mat1,mat2);
        long endTime = System.nanoTime();
        return endTime-startTime;
    }

    private IntMatrix newMatrix(Random generator, int size){
        IntMatrix Matrix = new IntMatrix(size);
        for (int i = 0; i <size ; i++) {
            for (int j = 0; j <size ; j++) {
                Matrix.set(i,j,generator.nextInt());
            }
        }
        return Matrix;
    }

    private IntMatrix new_zero_matrix(int size){
        IntMatrix Matrix = new IntMatrix(size);
        for (int i = 0; i <size ; i++) {
            for (int j = 0; j <size ; j++) {
                Matrix.set(i,j,0);
            }
        }
        return Matrix;
    }

    // creates a sparse matrix in which about sparsity% of the entries are 0
    public IntMatrix new_sparse_matrix(Random generator, int size, double sparsity){
        IntMatrix Matrix = new IntMatrix(size);
        boolean is_zero;
        int entry;
        for (int i = 0; i <size ; i++) {
            for (int j = 0; j <size ; j++) {
                entry = 0;
                is_zero = new Random().nextInt(100)<sparsity*100;
                if(is_zero){
                    entry = generator.nextInt();
                }
                Matrix.set(i,j,entry);
            }
        }
        return Matrix;
    }

}
