import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdArrayIO;
import edu.princeton.cs.algs4.StdOut;


    
public class Mat_Array {
      
    public int[] pivot; //at most n pivots, so assume would occupy n slots
    public int[] free;//at most n free variables, so assume would occupy n slots.
    public int[][] original;
    public int rank = 0;
    public static int numfree = 0;
    public static int [][] solution_array;
    public int[] binaryrep;
    
    public Mat_Array(int[][] original_array, int n) {
        pivot = new int[n+1];
        free = new int[n];
        original = new int[n][n+1];
        
        
        for (int x=0; x<n; x++) {
            for (int y=0; y<n+1;y++) {
                original[x][y] = original_array[x][y];
                
            }
        }
        computeSolution(n);
        
    }

    public void computeREF(int s) {
        int row = 0;
        int column = 0;
        int i = 0;
        int j = 0;
        //check the pivot position and doing operations   
        for ( row=0; row<s; row++) {
            for (; column<s; column++) {
                //find the first nonzero entry in this column
                for (i=row; i<s; i++) {
                    if (original[i][column] == 1) break;
                }
 //If there is a nonzero entry in this column, but it's not in the current 
 //row, switch this row with the one whose entry in this column is nonzero
                if (i>row && i<s)
                    switchRows(row, i, s);//helper function here.
                if (i<s) {
                    pivot[rank] = column;
                    rank++;
                    break;
                }
                //if i==n, that means in this particular column, there is not a pivot
                else {                   
                    free[numfree] = column;
                    numfree++;
                }
                
            }           
            // if we have reached the last column, we are in REF form.
            if (column == s) return;
            //kill the nonzero entries in this column below the pivot
            for (int x=row+1; x<s; x++) {
                if (original[x][column]==1) {
                    for (j=column; j<s+1; j++) {
                        original[x][j] +=original[row][j];
                        original[x][j] %= 2;
                    }
                }
            }
            column++;
        }
        
        
    }
    
    private void switchRows(int a, int b, int s) {
        int[] value = new int[s+1];
        for (int j=0; j<s+1; j++) {
            value[j] = original[a][j];
            original[a][j] =original[b][j];
            original[b][j] = value[j];
        }
    }
    
    public void computeRREF(int a) {
        computeREF(a);
        int i,j,k,x;
        for (j = rank-1; j>=0; j--) {
            for (i = j-1; i>=0; i--) {
                if (original[i][pivot[j]]==1) {
                    for (x=0; x<a+1; x++) {                
                        original[i][x] += original[j][x];                   
                        original[i][x] %= 2;     
                    }
                }
            }
        }
        for (int m=0; m<a; m++) {
            for (int y=0; y<a+1;y++) {
                System.out.print(original[m][y] + " ");
            }
            System.out.println();
        }
    }
    
    public void computeSolution(int m) {
        computeRREF(m);
        int[] solution = new int[m];
        solution_array=new int[(int) Math.pow(2,numfree)][m];
        binaryrep = new int[(int)Math.pow(2,numfree)];
        for (int i=0; i<Math.pow(2,numfree); i++) {
            binary(i);
            for (int j=0; j<numfree; j++) {
                solution[free[j]] = binaryrep[j]; //store the value of free var in the array
            }
            for (int k=0; k< rank; k++) {
                ////////////////////////////////////////
                solution[pivot[k]] = original[k][m];// read the augmented vector in that row*
                ////////////////////////////////////////
                for (int j=0; j<numfree; j++) {
                    // do operation to sort the value of pivot in solution.
                    solution[pivot[k]] += binaryrep[j]*original[k][free[j]];
                    // mod 2.
                    solution[pivot[k]] %= 2;
                }
            }
            for (int j=0; j<m; j++) {
                solution_array[i][j] = solution[j];
            }
        }
        System.out.print("All the solutions are: ");
        System.out.println();
        for (int i=0; i<Math.pow(2,numfree); i++) {
            for (int j=0; j<m; j++) {
                System.out.print(solution_array[i][j]+" ");
                
            }
            System.out.println();
        }
    }
    
    public void binary(int i) {
         //Is there a better way to do this?
        int[] temp= new int[numfree];
        int m=0;
        int k=numfree-1;
        while (i>0 && k>=0) {
            if (i%2==0) binaryrep[k--]=0;
            else binaryrep[k--] = i%2;
            i = i/2;
        }       
        for (int x=0; x<numfree; x++) {
            System.out.print(binaryrep[x]+" ");
            
        }
        System.out.println(); 
    }       
    
//    public static int[][] mat_form(int n) {
//        int N = n*n;
//        int[][] mat = new int[N][N+1];
//        for (int i=0; i<N; i++) {
//            for (int j=0; j<N+1; j++) {
//                if (j==N) mat[i][j]=1;
//                else if (i==j) mat[i][j]=1;
//                else if (i==j+1 && i%n!=0) mat[i][j]=1;
//                else if (j==i+1 && j%n!=0) mat[i][j]=1;
//                else if (j==i+n || i==j+n) mat[i][j]=1;
//                else mat[i][j]=0;
//            }
//        }
//        
//        return mat;
//    }
    
    public static int[][] theMAT(int M, int N) {
         int p;
         int q;
       
         boolean MATMN[][] = new boolean [M*N][M*N+1];
     
           
            for (int i= 0; i < M; i++){
                for (int j = 0; j < N; j++){
                    p = (N*i)+j;
                    for (int k = 0; k < M; k++){
                        for (int l = 0; l < N; l++){
                            q = (N*k)+l;
                            if (k == i && l==j) MATMN[p][q] = true;
                            else if (i== k+1 && l==j ) MATMN[p][q] = true; //id left
                           
                            
                            
                            else if (k==i  && l==j-1 && j != 0) MATMN[p][q] = true; //off diag under
                            
                            else if (k==i && j==l-1 && l != 0) MATMN[p][q] = true; // off diag above
                           
                            
                            else if (k==i+1 && l==j ) MATMN[p][q] = true; // id right
                            else MATMN[p][q] = false; }}}}
            for (int i=0; i<M*N; i++) {
                for (int j=0; j<M*N+1; j++) {
                    if (j==M*N) MATMN[i][j]=true;
                }
            }
                            
       
       int[][] a = new int[M*N][M*N+1];
        for (int i = 0; i < M*N; i++)
            for(int j = 0; j < M*N+1; j++)
        {
            if(MATMN[i][j]) a[i][j] = 1;
            else a[i][j] = 0;
        }
        return a;
    }
    
    public static void main(String args[]) {
//       int n=4;
//       int[][] originalarray = new int[n][n+1];
//       In in = new In(args[0]);
//       int[] a=in.readAllInts();
//       in.close();
//    
//        // Sorting on a;
//       int s = 0;
//       for (int i=0; i<n; i++){
//           //augmented matrix
//           for (int m=0; m<n+1; m++) {
//               originalarray[i][m]=a[s++];
//           }
//       }
       int[][] mat = theMAT(2,3);
       StdArrayIO.print(mat);
       StdOut.println();
       Mat_Array refform = new Mat_Array(mat,6); 
       System.out.println("The nullity is: "+numfree);
       

       
    }
}

   
       
       
