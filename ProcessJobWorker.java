package com.springcore.ElevatorSystem;

public class ProcessJobWorker implements Runnable {
	
	private Elevator elevator;
	
	ProcessJobWorker(Elevator elevator){
		this.elevator = elevator;
	}
	

	public void run() {
		elevator.StartElevator();
	}

}
