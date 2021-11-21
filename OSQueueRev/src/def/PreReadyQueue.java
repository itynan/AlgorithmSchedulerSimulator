package def;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class PreReadyQueue {

	
	ArrayList<Integer> fromFileAL  = new ArrayList<Integer>();
	//facilitates easy read from file
	
	
		
	
	public ArrayList<Integer> constructRQ(String fileName) throws FileNotFoundException 
	{
	

		Scanner s = new Scanner(new File(fileName));
        ArrayList<Integer> list = new ArrayList<Integer>();
        
        while (s.hasNext()){
        	fromFileAL.add(s.nextInt());
           // s.nextLine(); 
        }
       
        s.close();
        
        
	
	    return fromFileAL;
	    
		}
	
}

