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

	

