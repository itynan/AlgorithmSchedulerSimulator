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

