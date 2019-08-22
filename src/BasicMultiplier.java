import java.util.ArrayList;
import java.util.Arrays;

public class BasicMultiplier {
    public String toString() {
        return "BasicMultiplier";
    }

    public IntMatrix multiply(IntMatrix a, IntMatrix b) {
        int dim = a.getDim();
        IntMatrix result = new IntMatrix(dim);

        for(int i = 0; i < dim; i++) {
            for(int j = 0; j < dim; j++) {
                int sum = 0;
                for(int k = 0; k < dim; k++) {
                    sum += a.get(i,k) * b.get(k,j);
                }
                result.set(i,j,sum);
            }
        }
        return result;
    }


    public IntMatrix multiply_skip_zero(IntMatrix a, IntMatrix b) {
        int dim = a.getDim();
        IntMatrix result = new IntMatrix(dim);

        for(int i = 0; i < dim; i++) {
            for(int j = 0; j < dim; j++) {
                int sum = 0;
                for(int k = 0; k < dim; k++) {
                    if(a.get(i,k)==0||b.get(k,j)==0){
                        continue;
                    }
                    sum += a.get(i,k) * b.get(k,j);
                }
                result.set(i,j,sum);
            }
        }
        return result;
    }

    public IntMatrix multiply_skip_zero_changed_iterations(IntMatrix a, IntMatrix b) {
        int dim = a.getDim();
        IntMatrix result = new IntMatrix(dim);

        for(int i = 0; i < dim; i++) {
            for(int k = 0; k < dim; k++) {
                if(a.get(i,k)!=0){
                    for (int j = 0; j < dim; j++) {
                        result.set(i,j, result.get(i,j) + a.get(i, k) * b.get(k, j));
                    }
                }
            }
        }
        return result;
    }

    public IntMatrix multiply_crs(CRS a, CRS b) {
        int dim = a.row_pointer.length-1;
        IntMatrix result = new IntMatrix(dim);
        for (int i = 0; i < a.row_pointer.length-1; i++) {
            for (int k = a.row_pointer[i]; k <a.row_pointer[i+1]; k++) {
                for (int j = b.row_pointer[a.column_index[k]]; j < b.row_pointer[a.column_index[k]+1]; j++) {
                    result.set(i,b.column_index[j],result.get(i,b.column_index[j])+a.value[k]*b.value[j]);
                }
            }
        }
        return result;
    }
}
