/**The Matrix class contains methods for manipulating matrices.  All matrices used in 
 * this class are assumed to be 2D double arrays, of the form [rows][columns].
 * 
 * @author Matthew Polk
 *
 */
public class Matrix {
	/**The multiply method multiplies two matrices together and returns the answer.
	 * Both matrices must be two dimensional arrays.
	 * 
	 * @param a The first matrix, a 2D array.
	 * @param b The second matrix, a 2D array.
	 * @return The product of a and b, also a 2D array.  Will return null if a and b
	 * cannot be multiplied together, which occurs when the number of columns in a does 
	 * not equal the number of rows in b.
	 */
	public static double[][] multiply(double[][] a, double[][] b){
		if(a[0].length != b.length)//Failure
		{System.out.println("You suck!");
			return null;
		}
		double[][] ans = new double[a.length][b[0].length];
		//row of a * column of b
		for(int i = 0; i < a.length; i++){
			for(int j = 0; j < b[0].length; j++){
				for(int k = 0; k < a[0].length; k++){
					ans[i][j] += a[i][k]*b[k][j];
				}
			}
		}
		return ans;
	}
}
