import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class KMP{
    private String pattern;
	private int[][] dfa;
	private int R = 256;
    
    // Part of the program is from lecture slides at page 60
	// DFA cunstruction for KMP
    public KMP(String pattern){  
		this.pattern = pattern;
		int M = pattern.length();
		dfa = new int[R][M];
		dfa[pattern.charAt(0)][0] = 1;
		for (int X = 0, j = 1; j < M; j++){
			for( int c = 0; c < R; c++){
				dfa[c][j] = dfa[c][X];
			}
			dfa[pattern.charAt(j)][j] = j+1;
			X = dfa[pattern.charAt(j)][X];
		}
    }
    
	// Using DFA construct above to search throug the txt
    public int search(String txt){
		int x;
		int y;
		int M = pattern.length();
		int N = txt.length();
		for(x = 0, y = 0; x < N && y < M; x++){ //The running time of the search should be O(M+N)
			y = dfa[txt.charAt(x)][y];
		}
		if( y == M ){
			return x - M;
		}else{
			return N;
		}
    }
    
    
    public static void main(String[] args) throws FileNotFoundException{
	Scanner s;
	if (args.length > 0){
	    try{
		s = new Scanner(new File(args[0]));
	    }
	    catch(java.io.FileNotFoundException e){
		System.out.println("Unable to open "+args[0]+ ".");
		return;
	    }
	    System.out.println("Opened file "+args[0] + ".");
	    String text = "";
	    while(s.hasNext()){
		text += s.next();
	    }
	    
	    for(int i = 1; i < args.length; i++){
		KMP k = new KMP(args[i]);
		int index = k.search(text);
		if(index >= text.length()){
		    System.out.println("The pattern \"" + args[i] + "\" was not found."+" The length is "+index);
		}
		else System.out.println("The string \"" + args[i] + "\" was found at index " + index + ".");
	    }
	}
	else{
	    System.out.println("usage: java SubstringSearch <filename> <pattern_1> <pattern_2> ... <pattern_n>.");
	}
    }
}
