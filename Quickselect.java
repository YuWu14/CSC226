/**
 *
 * September 18, 2019
 * CSC 226 - Fall 2019
 */
import java.util.*;
import java.io.*;

public class Quickselect {

    // Swaps arr[i] with array[j]
    public static int[] swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
		return arr;
    }

	/*
	This function is to find the median of an array
	and return the median
	*/
	public static int FindMedian(int[] arr, int x){
		Arrays.sort(arr);
		if(x/2 == 0){
			return arr[0];
		}if(x%2 == 0){
			return arr[x/2];}
		return arr[x/2-1];
	}

    // Partitions the array in two, with all the elements on the left side of arr[p] being smaller than arr[p], and
    //all the elements on the right being greater than it.
    public static int partition(int arr[], int p, int right, int pivotIndex) {
		int i = 0;
		for(i = 0; i < right; i++){
			if(arr[i] == pivotIndex){
				break;
			}
		}
        swap(arr, right, i);
        int x = arr[right];
        for (int j = p; j < right; j++) {
            if (arr[j] <= x) {	
                swap(arr, p, j);
                p++;
            }
        }
        swap(arr, p, right);
        return p;
    }


    //
    public static int Quickselect(int[] arr, int k) {
        //Write your code here 
		if(arr.length >= k-1){
			int x = 0;
			int y = arr.length; // This is the number of elements on the list.

			int[] median = new int[(int)((y+8)/9)];

			int r = 0; //This is the pivot;
			if(y>9){
				for(int i = 0; i < (y-y%9)/9; i++){
					median[i] = FindMedian(Arrays.copyOfRange(arr,i*9,((i+1)*9)-1),8); // Find median
				}
				median[median.length-1] = FindMedian(Arrays.copyOfRange(arr,(median.length-1)*9,y),y-(median.length-1)*9); // For the last group
																														   // find the median.
			}else{
				median[0] = FindMedian(arr,y-1);// If the original array has less than 9 elements, call find median directly.
			}
			
			if(median.length == 1){
				r = median[0];	//Basis case, if the median of medians has found, stop recursive process.
			}else{
				Quickselect(median,k);
			}		
			int p = partition(arr,0,y-1,r); // Partition the original array.
			if(p > k-1){					// If the index for the pivot is bigger than k
				int[] arrleft = Arrays.copyOfRange(arr, 0, p);
				return Quickselect(arrleft,k);
			}
			if(p < k-1){					// If the index for the pivot is less than k
				int[] arrright = Arrays.copyOfRange(arr, p+1, y);
				return Quickselect(arrright,k-p-1);
				
			}
			if(p == k-1){
				return arr[p];		//	Basis: if the index of pivot is equal to k, stop recursive.
			}
		}
		return -1;	// If the length of array is less than k, return -1.

    }
    
    public static void main(String[] args) {
        Scanner s;
        int[] array;
        int k;
        if(args.length > 0) {
	    try{
		s = new Scanner(new File(args[0]));
		int n = s.nextInt();
		array = new int[n];
		for(int i = 0; i < n; i++){
		    array[i] = s.nextInt();
		}
	    } catch(java.io.FileNotFoundException e) {
		System.out.printf("Unable to open %s\n",args[0]);
		return;
	    }
	    System.out.printf("Reading input values from %s.\n", args[0]);
        }
	else {
	    s = new Scanner(System.in);
	    System.out.printf("Enter a list of non-negative integers. Enter a negative value to end the list.\n");
	    int temp = s.nextInt();
	    ArrayList<Integer> a = new ArrayList<Integer>();
	    while(temp >= 0) {
		a.add(temp);
		temp=s.nextInt();
	    }
	    array = new int[a.size()];
	    for(int i = 0; i < a.size(); i++) {
		array[i]=a.get(i);
	    }
	    
	    System.out.println("Enter k");
        }
        k = s.nextInt();
        System.out.println("The " + k + "th smallest number is the list is "
			   + Quickselect(array,k));	
    }
}
