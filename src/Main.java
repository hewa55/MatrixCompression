import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        // creates all relevant files with _2 extension in the name
        // eg. r_analysis will be created as r_analysis_2

        GenerateTestData_Task2 task2 = new GenerateTestData_Task2();
        System.out.println("r_analysis");
        // output r_analysis.csv
        task2.generateTestData_base_case(1234,3,250);
        System.out.println("r_time_spartsity");
        // output r_time_sparsity.csv
        task2.generateTestData_time_sparsity(1234,200,0.00);
        System.out.println("r_all_alg");
        // output r_all_alg.csv
        task2.generateTestData_all_alg(1234,3,250,0.04);
        System.out.println("r_basic_skip_zero");
        // output r_basic_skip_zero.csv
        task2.generateTestData_skip_zero(1234,3,250,0.04);

        GenerateNumberCounts generateNumberCounts = new GenerateNumberCounts();
        System.out.println("r_size");
        // output r_size.csv
        generateNumberCounts.generate_sizes(1234,0,1000,0.04);
        System.out.println("r_size_sparsity");
        // output r_size_sparsity.csv
        generateNumberCounts.generate_sizes_sparsity(1234,600,0);
    }
}
