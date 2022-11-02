package com.springcore.ElevatorSystem;

public class AddJobWorker implements Runnable{
	
	private Elevator elevator;
	private Request request;
	
	AddJobWorker(Elevator elevator, Request request) {
		this.elevator = elevator;
		this.request = request;
	}

	public void run() {

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		elevator.addJob(request);
	}
}
