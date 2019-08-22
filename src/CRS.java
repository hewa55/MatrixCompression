public class CRS {
    int[] value;
    int[] column_index;
    int[] row_pointer;

    public CRS(IntMatrix matrix){
        this.row_pointer = new int[matrix.getDim()+1];
        int non_zero = get_non_zero(matrix);
        this.column_index = new int[non_zero];
        this.value = new int[non_zero];
        int k = 0;
        int t = 0;
        row_pointer[t]=0;
        t++;
        for (int i = 0; i < matrix.getDim() ; i++) {
            for (int j = 0; j < matrix.getDim() ; j++) {
                if(matrix.get(i,j)!=0){
                    value[k] = matrix.get(i,j);
                    column_index[k] = j;
                    k++;
                }
            }
            row_pointer[t] = k;
            t++;
        }
    }

    private int get_non_zero(IntMatrix matrix){
        int k =0;
        for (int i = 0; i < matrix.getDim(); i++) {
            for (int j = 0; j < matrix.getDim(); j++) {
                if(matrix.get(i,j)!=0){
                    k++;
                }
            }
        }
        return k;
    }
}
