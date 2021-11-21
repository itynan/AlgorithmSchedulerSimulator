package def;
import java.util.ArrayList;
import java.io.File;
import java.util.*;

public class Main{
	
	/*You will write a program to simulate a few CPU scheduling policies we
	discussed in class. You will be using Java or C or C++ to implement the simulator. The
	simulator selects a task to run from ready queue based on the scheduling algorithm. Since
	the project intends to simulate a CPU scheduler, it does not require any actual process
	creation or execution. When a task is scheduled, the simulator will simply print out what
	task is selected to run at a given time. Your simulator will output data similar to how a 
	Gantt Chart illustrates process execution*/
	

	public static ArrayList<Integer> fromFileAL = new ArrayList<Integer>();
	
	/*Array list objects will serve as ready queue
	once the object is processed in order to output Gantt Chart,
	it is removed from the Array List*/
	
	
	public static void main(String[] args) throws Exception {
		
		String fileName ="/Users/iliyatynan/eclipse-workspace/OSQueueRev/src/def/input.txt";
		Scanner s = new Scanner(System.in);
			
		PreReadyQueue PRQ = new PreReadyQueue();  
		//reads processes into AL 
		fromFileAL= PRQ.constructRQ(fileName);
		//creates sorted RQs per scheduling algorithm method
		SchedAlgrms.SchAlgs(fromFileAL);
		//runs algorithm + prints Gantt Chart-inspired scheduling output 
		

	   
	}


	

}

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

package def;
import java.util.ArrayList;
public class ProcessData extends ComparatorForSort {

	Object procID;
	Object arrTime;
	Object burst;

		
	public ProcessData(Object procID,Object arrTime, Object burst) {
		
		this.procID   = procID;
		this.arrTime = arrTime;
		this.burst   = burst;	
		
	}

	public Object getPID() {
		return procID;
	}
	public Object getArrTime() {
		return arrTime;
	}
	public Object getBurst() {
		return burst;
	}
	
	public void setBurst() {
		
		int burstTemp = (Integer)burst;
		burstTemp--;
		burst=(Integer)burstTemp;	
	}
	
	
}

	
package def;

import java.util.Comparator;

public class ComparatorForSort{
	
	public static Comparator<ProcessData> ArrTimeComp = 
		new Comparator<ProcessData>() 
	{
				
		@Override
		public int compare(ProcessData p1, ProcessData p2) 
		{
			int AT1 = (Integer)p1.getArrTime();
			int AT2 = (Integer)p2.getArrTime();
			
			return AT1-AT2;
		}
	};
	
	public static Comparator<ProcessData> BurstComp = 
		new Comparator<ProcessData>() 
	{	
				
		@Override
		public int compare(ProcessData b1, ProcessData b2) 
		{
			int BUR1 = (Integer)b1.getBurst();
			int BUR2 = (Integer)b2.getBurst();
			
			return(BUR1-BUR2);
		}
	
	};
}

package def;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SchedAlgrms extends ComparatorForSort{
	
	static ArrayList<ProcessData> ArrTimeAL  = new ArrayList<ProcessData>();
	static ArrayList<ProcessData> BurstAL    = new ArrayList<ProcessData>();
	
	
	public static int TQ = 3; //Time Quantum
	
	public static void SchAlgs(ArrayList<Integer> fromFileAL) {
	
	for(int i =0 ; i < fromFileAL.size(); i += 3)
    {	
    	ProcessData p1 = new ProcessData(fromFileAL.get(i),
    			(Integer)fromFileAL.get(i+1),fromFileAL.get(i+2));
    	
    	ArrTimeAL.add(p1);
    	BurstAL  .add(p1);
    
    
    }

    Collections.sort(ArrTimeAL, ProcessData.ArrTimeComp);
    
    Collections.sort(BurstAL, ProcessData.ArrTimeComp);
    Collections.sort(BurstAL, ProcessData.BurstComp);

		
		
	ProcessData[] burstArr = new ProcessData[ArrTimeAL.size()];
    //for Round Robin
    for(int i = 0; i< ArrTimeAL.size(); i++) {
    	burstArr[i] = ArrTimeAL.get(i);
    	
    }
    
    ArrayList<ProcessData> RRAL = (ArrayList<ProcessData>)ArrTimeAL.clone();
    //duplicated to send to funcs methods
    
    
   
    
    //*****************FCFS*****************
    //**************************************
   
    	
    boolean fR = false;
    int FCFSCount = 0;
    int FCFSWaitTime = 0;
    
    int[] FCFSWaitTimeArr = new int[ArrTimeAL.size()];
    int[] ResTimeArr = new int[ArrTimeAL.size()];
    
    int ALSize  = ArrTimeAL.size();
    
	for(int i=0; i < ALSize;i++) {
		    	
    	int ALVal = (Integer)ArrTimeAL.get(i).getBurst();
    	
    	for(int j = 0; j<ALVal;j++) {
    		
    		if(fR == false) {
	    		System.out.println();
	    		System.out.println("First come-first served:");
    		
	    		fR=true;
    		}
    		
    		
    		System.out.println("P"+ArrTimeAL.get(i).getPID()
    				+" running..."+ArrTimeAL.get(i).getBurst());
    		FCFSCount++;
    		ArrTimeAL.get(i).setBurst();
    		
    		if((Integer)ArrTimeAL.get(i).getBurst()==0&& i+1!=ALSize) {
    			
    			FCFSWaitTimeArr[i+1]=FCFSCount;  
    			ResTimeArr[i]=FCFSWaitTimeArr[i]-(Integer)ArrTimeAL.get(i).getArrTime();
    		}
			
		}// facilitates removal from ready queue when burst is finished
    		
	}
	int burstAcc = 0;
	int resTimeAcc = 0;
	int fcTatAcc = 0;
	int diff = 0;
	
	for(int i=0; i < FCFSWaitTimeArr.length;i++) {
		System.out.println("P"+ArrTimeAL.get(i).getPID()+" wait: "+FCFSWaitTimeArr[i]);
		burstAcc += FCFSWaitTimeArr[i];
		
		System.out.println("P"+ArrTimeAL.get(i).getPID()+" response: "+ResTimeArr[i]);
		resTimeAcc+=ResTimeArr[i];
		
		
		diff = FCFSWaitTimeArr[i]-(int)ArrTimeAL.get(i).getArrTime();
		
		System.out.println("P"+ArrTimeAL.get(i).getPID()+" turn around: "+
		((int)ArrTimeAL.get(i).getBurst()+diff));
		fcTatAcc +=(int)ArrTimeAL.get(i).getBurst()+diff;
		
	}
	System.out.println("avg wait time="+1.0*burstAcc/FCFSWaitTimeArr.length);
	System.out.println("avg response time="+1.0*resTimeAcc/ResTimeArr.length);
	System.out.println("avg TA time="+1.0*fcTatAcc/ResTimeArr.length);
	// System.out.println(FCFSCount);
	
	System.out.println("Idle");

    
    //*********************SJF*********************
    //*********************************************
    
   
    boolean bFR = false;
    int SJFCount = 0;
 
    int SJFWaitTime = 0;
    int[] SJFWaitTimeArr = new int[BurstAL.size()];
    int[] ResBurstTimeArr = new int[ArrTimeAL.size()];
    
    int BuSize  = BurstAL.size();

    for(int i=0; i < BurstAL.size();i++) {
    	
    	int ALVal =(Integer)BurstAL.get(i).getBurst();
    	int waitTime = ALVal;
    	
    	for(int j = 0; j<ALVal;j++) {
    		
    		if(bFR == false) {
    		System.out.println();
    		System.out.println("Shortest Job First:");
    		bFR=true;
    		}
    	
    		System.out.println("P"+BurstAL.get(i).
    		getPID()+" running...");
    		BurstAL.get(i).setBurst();
    		SJFCount++;
    		
    		if((Integer)BurstAL.get(i).getBurst()==0&& i+1!=BuSize) {
    			
    			SJFWaitTimeArr[i+1]=SJFCount;   	
    			
    			ResBurstTimeArr[i]=SJFWaitTimeArr[i]-
    			(Integer)ArrTimeAL.get(i).getArrTime();
    		}
    		
    	}    	
    }
    int buAcc = 0;
    int resBurstAcc=0;
    int sjfTatAcc = 0;
   // int diff=0;
    
    for(int i=0; i < SJFWaitTimeArr.length;i++) {
		System.out.println("P"+BurstAL.get(i).getPID()+" wait: "+SJFWaitTimeArr[i]);
		buAcc += SJFWaitTimeArr[i];
		System.out.println("P"+BurstAL.get(i).getPID()+" response: "+SJFWaitTimeArr[i]);
		resBurstAcc+=ResBurstTimeArr[i];
		
		diff = SJFWaitTimeArr[i]-(int)ArrTimeAL.get(i).getArrTime();
		
		System.out.println("P"+BurstAL.get(i).getPID()+" turn around: "+
		((int)BurstAL.get(i).getBurst()+diff));
		sjfTatAcc +=(int)BurstAL.get(i).getBurst()+diff;
		
	
	}
    System.out.println("avg wait time="+1.0*buAcc/SJFWaitTimeArr.length);
    System.out.println("avg response time="+1.0*resBurstAcc/SJFWaitTimeArr.length);
    System.out.println("avg TA time="+1.0*sjfTatAcc/SJFWaitTimeArr.length);
    System.out.println("Idle");

    
    
    //*****************Round Robin*****************
    //*********************************************
    
    
    int acc= 0;
    for(int i = 0; i< burstArr.length; i++) 
    	acc+=(int)burstArr[i].getBurst();
    
    int[] waitArr = new int[burstArr.length];
    int[] resArr = new int[burstArr.length];
    int[] tatArr= new int[burstArr.length];
    
    
    //accum for RR
    
    System.out.println();
    System.out.println("ROUND ROBIN");

    int i =0;
    int RRCount = 0 ;
    while(acc>0) {
    for(i = 0; i<burstArr.length; i++) {
    	
    	if((int)burstArr[i].burst==0) 
			i++;

    
    	for(int j = 0; j< TQ; j++) {
    		if(acc==0) {	
    			break;
	    	}
    		
    		if(i==burstArr.length&&acc>0) 
	    		i = 0;
    		if((int)burstArr[i].burst==0) {
    			break;
    		}
    		
	    		
    		System.out.println("P"+burstArr[i].getPID()+
    		" running...");
    		RRCount++;
    		
    		burstArr[i].burst=(int)burstArr[i].burst-1; 
    		
    		if((int)burstArr[i].getBurst() == (0))
    			tatArr[i] = RRCount -(int)burstArr[i].getArrTime();
    		
    		acc--;
    		
    		if(i==burstArr.length && acc>0) 
    			i = 0;
    	}    	
    }
    
    }
    int j=0;
    int waitAcc= 0;
    int resAcc = 0;
    int tatAcc = 0;
    
    for(int f=0; f <burstArr.length; f++) {
    	resArr[f]=j-(int)burstArr[f].getArrTime();
    	System.out.println("P"+burstArr[f].getPID()+" response: "+resArr[f]);
    	resAcc+=resArr[f];
    	j+=TQ;
    }
    
    for(int k =0; k <burstArr.length; k++) {
    	System.out.println("P"+burstArr[k].getPID()+" Turn around: "+tatArr[k]);
    	tatAcc =+tatArr[k];
    }
    /*f((int)burstArr[i].getBurst() == (0))
    			tatArr[i] = RRCount -(int)burstArr[i].arrTime;*/
    
    for(int g =0; g <burstArr.length; g++) {
    	waitArr[g]=RRCount-  resArr[g] ;
    	waitAcc +=waitArr[g];
    	System.out.println("P"+(int)burstArr[g].getPID()+" wait time: "+waitArr[g]);
    	
    }
    
    System.out.println("Avg Wait = "+ ((1.0*waitAcc)/burstArr.length));
    System.out.println("Avg Response = "+(1.0*resAcc)/burstArr.length);
    System.out.println("Avg TAT = "+((1.0*tatAcc)/burstArr.length));
    
    System.out.println("Idle");

  }
}   
//
//public class Funcs{
//	
//	
//	public void waitTime(ArrayList<ProcessData> AL) {
//		
//		int[] count = new int[AL.size()];
//		int[] waitT = new int[AL.size()];
//		int[] turnA = new int[AL.size()];
//		
//		
//		for(int i = 0; i < AL.size(); i++) {
//			count[i] = count[i-1]+(Integer)AL.get(i-1).getBurst();
//			
//			waitT[i] = count[i] - (Integer)AL.get(i).getArrTime();
//		}
//		for(int i = 0; i < AL.size(); i++) {
//			turnA[i] = (Integer)AL.get(i-1).getBurst()+count[i];
//		}
//		
//	}
//}


