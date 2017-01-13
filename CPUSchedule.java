/***************************************************************************************************
Program: CPUSchedule.java
Course: TSN2101 Operating System
Year: 2016/17 Trimester 1
Group Leader  : ARSYAD BIN ANUAR 1142700937  syaads@yahoo.com.my   019-3456645
Group Member 1: AHMAD NAZMI BIN BAHRUDDIN 1142700845 ahmadnazmi14@yahoo.com   011-23744228
Group Member 2: AKMAL SAFWAN BIN AB AZIZ 1142701017  akmalsafwanabaziz@gmail.com   017-3418230
Group Member 3: MUHAMMAD SYAWALUDIN ANAS YUSOF 1142700818	1142700818@student.mmu.edu.my	019-6555237
Lecture Section: TC03
***************************************************************************************************/ 

import java.util.*;

public class CPUSchedule
{    
    private int Id, BurstTime, ArriveTime, Priority;     
    
    //--------------------set Method--------------------
    public void setId(int id){
        Id = id;
    }
    
    public void setBurst(int bt){
        BurstTime = bt;
    }
    
    public void setArrive(int at){
        ArriveTime = at;
    }
	
	public void setPriority(int pt){
        Priority = pt;
    }
    
    //--------------------get Method--------------------
    public int getId(){return Id;}
    public int getBurst(){return BurstTime;}
    public int getArrive(){return ArriveTime;}
	public int getPriority(){return Priority;}
    //public Average()
    
    
    public static void main(String[] args){  
        
        //Variable declaraion
        int Choice, Prev, Subt, ProcessCounter, Current, WaitingTime, TurnAroundTime, PrevPos, Pos, FirstArrival, TotalTurnAround=0, TotalWaiting=0, TotalTime=0, CurrentTime=0, Smallest=0;
        float AvgWaiting, AvgTurnAround;
        Scanner scan = new Scanner(System.in);
        
        //----------Insert Quantum and No of Process
        System.out.print("Quantum: ");
        int Quantum = scan.nextInt();
        System.out.print("Number of Process (Between 3 to 10): ");
        int NoProcess = scan.nextInt();
        while((NoProcess<3) || (NoProcess>10)){
            System.out.println("------------------------------------");
            System.out.println("ERROR - Invalid Number of Process!");
            System.out.println("------------------------------------");
            System.out.println(" ");
            System.out.print("Enter again: ");
            NoProcess = scan.nextInt();
            if((NoProcess>=3)&&(NoProcess<=10)){
                break;
            }
        }
        
        CPUSchedule[] P = new CPUSchedule[NoProcess];
        CPUSchedule[] P2 = new CPUSchedule[NoProcess];	//Copy of P
		ArrayList<Integer> QueueId = new ArrayList<Integer>(NoProcess);
		ArrayList<Integer> QueueBurst = new ArrayList<Integer>(NoProcess);
		
		ArrayList<Integer> QueueBurst1 = new ArrayList<Integer>(NoProcess);
		ArrayList<Integer> QueueId1 = new ArrayList<Integer>(NoProcess);
		
		ArrayList<Integer> QueueBurst2 = new ArrayList<Integer>(NoProcess);
		ArrayList<Integer> QueueId2 = new ArrayList<Integer>(NoProcess);
		
		ArrayList<Integer> QueueBurst3 = new ArrayList<Integer>(NoProcess);
		ArrayList<Integer> QueueId3 = new ArrayList<Integer>(NoProcess);
		
        //-----------Insert Arrival and Burst Time for Process
        for(int i=0; i<NoProcess; i++){
            P[i] = new CPUSchedule();
			P2[i] = new CPUSchedule();
			
			
            System.out.print("Arrive Time of P[" + (i+1) + "]: ");
            P[i].setArrive(scan.nextInt());
            System.out.print("Burst Time of P[" + (i+1) + "]: ");
            P[i].setBurst(scan.nextInt());
			System.out.print("Priority of P[" + (i+1) + "] (Between 1 to 6): ");
            P[i].setPriority(scan.nextInt());
			
			while((P[i].getPriority()<1) || (P[i].getPriority()>6)){
				System.out.println("------------------------------------");
				System.out.println("ERROR - Invalid Number of Priority!");
				System.out.println("------------------------------------");
				System.out.println(" ");
				System.out.print("Enter again: ");
				P[i].setPriority(scan.nextInt());
				if((P[i].getPriority()>=1) && (P[i].getPriority()<=6)){
					break;
				}
			}
			
			TotalTime += P[i].getBurst();
			P[i].setId(P[i].getId() + i);
			
        }
		
		for(int i=0; i<NoProcess; i++){
			if(P[i].getArrive() < P[Smallest].getArrive())
				Smallest = i;
		}
			
		FirstArrival = P[Smallest].ArriveTime;
		
		if(FirstArrival != 0){
			TotalTime += FirstArrival;
		}
        
        //------------Sort Objects in Array according to Arrival Time
        for(int i=0; i<NoProcess; i++){
            for(int j=i+1; j<NoProcess; j++){
                if((P[j].getArrive()) < (P[i].getArrive())){
                    int tempArrive = P[i].getArrive();
                    int tempBurst = P[i].getBurst();
                    int tempId = P[i].getId();
					int tempPriority = P[i].getPriority();
                    
                    P[i].setArrive(P[j].getArrive());
                    P[i].setBurst(P[j].getBurst());
                    P[i].setId(P[j].getId());
					P[i].setPriority(P[j].getPriority());
                    
                    P[j].setArrive(tempArrive);
                    P[j].setBurst(tempBurst);
                    P[j].setId(tempId);
                    P[j].setPriority(tempPriority);
                }
            }
        }
        
		//Create a copy of P
		for(int i=0; i<NoProcess; i++){
			P2[i].setArrive(P[i].getArrive());
			P2[i].setBurst(P[i].getBurst());
			P2[i].setId(P[i].getId());					
			P2[i].setPriority(P[i].getPriority());
		}
		
		//FCFS------------------------------------------------------------------------------
		Pos =- 1;
		PrevPos = 0;
		//Current = 0;
		System.out.println("\n************************************************");
		System.out.println("FCFS Pre-emptive Priority Scheduling\n");
		
		System.out.print("Start->");
		
		for(CurrentTime = FirstArrival; CurrentTime < TotalTime; CurrentTime++)
		{
            PrevPos = Pos;
			//Check the burst time on each processes
			for(int i = 0; i < NoProcess; i++){
				if((P[i].getBurst() != 0)&&(P[i].getArrive() <= CurrentTime)){
					Pos = i;
					break;
				}
			}
			//Check priority
			for( int i = 0; i < NoProcess; i++){
				if((Pos != i)&&(P[i].getPriority() == P[Pos].getPriority())){
					if(P[i].getArrive() < P[Pos].getArrive()){
						Pos = i;
					}
				}
				else if((P[i].getBurst() != 0)&&(P[i].getPriority() < P[Pos].getPriority())
							&&(P[i].getArrive() <= CurrentTime)){
								Pos = i;
							}
			}       
			
			//Finding Process Number
			Current = P[Pos].getId()+1;
			
			P[Pos].setBurst(P[Pos].getBurst() - 1);
			
			//Gant Chart
			if(PrevPos != Pos){
				System.out.print("P" + Current + "->");
			}
			if(P[Pos].getBurst()==0){
				TurnAroundTime = CurrentTime - P[Pos].getArrive();
				TurnAroundTime++;
				TotalTurnAround += TurnAroundTime;
			}
			
         }
		 
		 System.out.print("End");
		 
		 //Waiting Time
		TotalWaiting -= TotalTime;
		TotalWaiting += TotalTurnAround;
		
		//Calculate Average Waiting Time and Average Turnaround Time
		AvgTurnAround = (float)TotalTurnAround/NoProcess;
		AvgWaiting = (float)TotalWaiting/NoProcess;
	
			
		System.out.println("\n\nTotal Waiting Time = " + TotalWaiting);
		System.out.println("Total Turnaround Time = " + TotalTurnAround);
		System.out.println("\nAverage Waiting Time = " + AvgWaiting);
		System.out.println("Average Turnaround Time = " + AvgTurnAround + "\n");
        
		//Reset to original value
		for(int i=0; i<NoProcess; i++){
			P[i].setArrive(P2[i].getArrive());
			P[i].setBurst(P2[i].getBurst());
			P[i].setId(P2[i].getId());
			P[i].setPriority(P2[i].getPriority());
		}
		
		//RR---------------------------------------------------------------------------------
		System.out.println("\n************************************************");
		System.out.println("Round Robin Scheduling\n");
		System.out.print("Start->");
		
		ProcessCounter = 1;
		Subt = 0;
		Prev = 0;
		TotalTurnAround = 0;
		TurnAroundTime = 0;
		TotalWaiting = 0;
		QueueBurst.add(P[0].getBurst());
		QueueId.add(P[0].getId());
		
		for(CurrentTime = FirstArrival; CurrentTime<TotalTime;)
		{
			//Find Process Number
			Current = QueueId.get(0) + 1;
			
			//Gant Chart
			if(Prev != Current)
				System.out.print("P" + Current + "->");
			
			//Check Subtract
			if(QueueBurst.get(0) < Quantum){
				Subt = QueueBurst.get(0);
			}
			else{
				Subt = Quantum;
			}
			
			//Subtract Burst Time and Increase Current Time
			QueueBurst.set(0, QueueBurst.get(0) - Subt);
			CurrentTime += Subt;

			//Check for arrival time
			for(int i = ProcessCounter; i<NoProcess; i++){
				if(P[i].getArrive() <= CurrentTime){
					QueueBurst.add(P[i].getBurst());
					QueueId.add(P[i].getId());
				}
				else if(P[i].getArrive() > CurrentTime){
					break;
				}
				ProcessCounter += 1;
			}
			
			//Check Remaining Burst Time
			if(QueueBurst.get(0) != 0){
				QueueBurst.add(QueueBurst.get(0));
				QueueId.add(QueueId.get(0));
			}
			else{
				Current -= 1;
				
				TurnAroundTime = CurrentTime - P[Current].getArrive();
				TotalTurnAround += TurnAroundTime;
				
				Current += 1;
			}
			
			//Previous process
			Prev=Current;
			
			//Delete Current Process
			QueueId.remove(0);
			QueueBurst.remove(0);
		}
		
		System.out.print("End");
		
		//Waiting Time
		TotalWaiting -= TotalTime;
		TotalWaiting += TotalTurnAround;
		
		//Calculate Average Waiting Time and Average Turnaround Time
		AvgTurnAround = (float)TotalTurnAround/NoProcess;
		AvgWaiting = (float)TotalWaiting/NoProcess;
	
			
		System.out.println("\n\nTotal Waiting Time = " + TotalWaiting);
		System.out.println("Total Turnaround Time = " + TotalTurnAround);
		System.out.println("\nAverage Waiting Time = " + AvgWaiting);
		System.out.println("Average Turnaround Time = " + AvgTurnAround + "\n");
		
		//Reset to original value
		for(int i=0; i<NoProcess; i++){
			P[i].setArrive(P2[i].getArrive());
			P[i].setBurst(P2[i].getBurst());
			P[i].setId(P2[i].getId());
			P[i].setPriority(P2[i].getPriority());
		}
		
		//STRN/SJF----------------------------------------------------------------------------------
		System.out.println("\n************************************************");
		System.out.println("SJF Pre-emptive Scheduling\n");
		System.out.print("Start->");
		
		Pos =- 1;
		PrevPos = 0;
		TotalTurnAround = 0;
		TurnAroundTime = 0;
		TotalWaiting = 0;
		for(CurrentTime = FirstArrival; CurrentTime<TotalTime; CurrentTime++)
		{
			PrevPos = Pos;
			
			//Check if there is Burst Time on any process
			for(int i=0; i<NoProcess; i++)
			{
				if((P[i].getBurst() != 0) && (P[i].getArrive() <= CurrentTime))
					Pos = i;
			}
			
			//Check who has the shortest burst AND has arrived/already in queue
			for(int i=0; i<NoProcess; i++)
			{
				if((Pos != i) && (P[i].getBurst() == P[Pos].getBurst())){
					if(P[i].getArrive() < P[Pos].getArrive()){
						Pos = i;
					}
				}
				else if((P[i].getBurst() != 0) && (P[i].getBurst() < P[Pos].getBurst())
						  && (P[i].getArrive() <= CurrentTime))
						  {
							  Pos = i;
						  }	  
			}
			
			/* System.out.println(Pos + "\t\t" + CurrentTime + "\t\t" + P[Pos].getBurst()); */
	
			
			//Burst Time deduction by 1 millisecond
			P[Pos].setBurst(P[Pos].getBurst() - 1);
			
			//Find process number
			Current = P[Pos].getId() + 1;
			
			//Gant Chart
			if(PrevPos != Pos){
				System.out.print("P" + Current + "->");
			}

			//Turnaround Time for process
			if(P[Pos].getBurst() == 0)
			{
				TurnAroundTime = CurrentTime - P[Pos].getArrive();
				TurnAroundTime++;
				TotalTurnAround += TurnAroundTime;
			}
		}
		
		System.out.print("End");
		
		//Waiting Time
		TotalWaiting -= TotalTime;
		TotalWaiting += TotalTurnAround;
		
		//Calculate Average Waiting Time and Average Turnaround Time
		AvgTurnAround = (float)TotalTurnAround/NoProcess;
		AvgWaiting = (float)TotalWaiting/NoProcess;
	
			
		System.out.println("\n\nTotal Waiting Time = " + TotalWaiting);
		System.out.println("Total Turnaround Time = " + TotalTurnAround);
		System.out.println("\nAverage Waiting Time = " + AvgWaiting);
		System.out.println("Average Turnaround Time = " + AvgTurnAround + "\n");
        
		//Reset to original value
		for(int i=0; i<NoProcess; i++){
			P[i].setArrive(P2[i].getArrive());
			P[i].setBurst(P2[i].getBurst());
			P[i].setId(P2[i].getId());
			P[i].setPriority(P2[i].getPriority());
		}
		
		//Three Level Queue-----------------------------------------------------------------------
		Pos = 0;
		TotalTurnAround = 0;
		TurnAroundTime = 0;
		TotalWaiting = 0;
		QueueBurst1.add(P[0].getBurst());
		QueueId1.add(P[0].getId());
		QueueBurst2.add(P[0].getBurst());
		QueueId2.add(P[0].getId());
		QueueBurst3.add(P[0].getBurst());
		QueueId3.add(P[0].getId());
		
		System.out.println("\n************************************************");
		System.out.println("Three Level Queue\n");
		System.out.print("Start->");
		for(CurrentTime = FirstArrival; CurrentTime<TotalTime;)
		{
			for(int i=Pos; i<NoProcess; i++){
				//Check if process arrived/already
				if(P[i].getArrive() > CurrentTime){
					break;
				}
				
				//Priority 1-2; Add to Queue1
				else if(P[i].getPriority() <= 2){
					QueueBurst1.add(P[i].getBurst());
					QueueId1.add(P[i].getId());
				}
				
				
				//Priority 3-4; Add to Queue2
				else if(P[i].getPriority() <= 4){
					QueueBurst2.add(P[i].getBurst());
					QueueId2.add(P[i].getId());
				}
				
				//Priority 5-6; Add to Queue3
				else{
					QueueBurst3.add(P[i].getBurst());
					QueueId3.add(P[i].getId());
				}
				
				Pos++;
			}
			
			//Special Condition: If Queue2 is empty, Queue3 to Queue2
			if(QueueId2.isEmpty()){
				QueueId2.addAll(QueueId3);
				QueueBurst2.addAll(QueueBurst3);
				
				QueueBurst3.add(P[0].getBurst());
				QueueId3.add(P[0].getId());
			}
			
			//Check which not empty
			if(!QueueId1.isEmpty()){
				Choice = 1;
			}
			else{
				Choice = 2;
			}
			
			switch(Choice){
				case 1: //Round Robin
						//Find Process Number
						Current = QueueId1.get(0) + 1;
						
						//Gantt Chart
						if(Prev != Current){
							System.out.print("P" + Current + "->");
						}
						
						//Check Subtract
						if(QueueBurst1.get(0) < Quantum){
							Subt = QueueBurst1.get(0);
						}
						else{
							Subt = Quantum;
						}
						
						//Subtract Burst Time and Increase Current Time
						QueueBurst1.set(0, QueueBurst1.get(0) - Subt);
						CurrentTime += Subt;
						
						//Check Remaining Burst Time
						if(QueueBurst1.get(0) != 0){
							QueueBurst1.add(QueueBurst1.get(0));
							QueueId1.add(QueueId1.get(0));
						}
						else{
							Current -= 1;
							
							TurnAroundTime = CurrentTime - P[Current].getArrive();
							TotalTurnAround += TurnAroundTime;
							
							Current += 1;
						}
						
						//previous process
						Prev = Current;
						
						//Delete Current Process
						QueueBurst1.remove(0);
						QueueId1.remove(0);
				
				case 2: //FCFS
						//Find Process No
						Current = QueueId2.get(0) + 1;
						
						//Gant Chart
						if(Prev != Current){
							System.out.print("P" + Current + "->");
						}
						
						//Burst Time decrease
						QueueBurst2.set(0, QueueBurst2.get(0) - 1);
						CurrentTime++;
						
						if(QueueBurst2.get(0) == 0){
							Current -= 1;
							
							TurnAroundTime = CurrentTime - P[Current].ArriveTime;
							TotalTurnAround += TurnAroundTime;
							
							QueueBurst2.remove(0);
							QueueId2.remove(0);
							
							Current += 1;
						}
						
						//Previous Process
						Prev = Current;
			}
		}
		
		System.out.print("End");
		
		//Waiting Time
		TotalWaiting -= TotalTime;
		TotalWaiting += TotalTurnAround;
		
		//Calculate Average Waiting Time and Average Turnaround Time
		AvgTurnAround = (float)TotalTurnAround/NoProcess;
		AvgWaiting = (float)TotalWaiting/NoProcess;
	
			
		System.out.println("\n\nTotal Waiting Time = " + TotalWaiting);
		System.out.println("Total Turnaround Time = " + TotalTurnAround);
		System.out.println("\nAverage Waiting Time = " + AvgWaiting);
		System.out.println("Average Turnaround Time = " + AvgTurnAround + "\n");
			
		
		
		
	}
}