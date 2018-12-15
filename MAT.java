import edu.princeton.cs.algs4.StdArrayIO;
import java.util.Scanner;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


  
public final class MAT {
    
    public static int[][] theMAT(int N) {
         int p;
         int q;
       
         boolean MAT[][] = new boolean [N*N][N*N];
     
           
            for (int i= 0; i < N; i++){
                for (int j = 0; j < N; j++){
                    p = (N*i)+j;
                    for (int k = 0; k < N; k++){
                        for (int l = 0; l < N; l++){
                            q = (N*k)+l;
                            if (k == i && l==j) MAT[p][q] = true;
                            else if (k== i-1 && l==j && i != 0) MAT[p][q] = true; 
                            else if (k==i  && l==j-1 && j != 0) MAT[p][q] = true;  
                            else if (k==i && l==j+1 && j !=N-1) MAT[p][q] = true;
                            else if (k==i+1 && l==j && i != N-1) MAT[p][q] = true;
                            else MAT[p][q] = false; }}}}
                            
       
       int[][] a = new int[N*N][N*N];
        for (int i = 0; i < N*N; i++)
            for(int j = 0; j < N*N; j++)
        {
            if(MAT[i][j]) a[i][j] = 1;
            else a[i][j] = 0;
        }
        return a;
    }
    public static void main(String [] args) {
        StdOut.println("MAT");
        StdOut.println("--------------------");
        int[][] a = theMAT(4);
        StdArrayIO.print(a);
        StdOut.println();
        
    }
    
}  

