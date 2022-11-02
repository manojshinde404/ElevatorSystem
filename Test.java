package com.springcore.ElevatorSystem;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Elevator elevator = new Elevator();
		
		ProcessJobWorker processJobWorker = new ProcessJobWorker(elevator);
		
		Thread thread = new Thread(processJobWorker);
		thread.start();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		InternalRequest internalRequest = new InternalRequest(0);
		ExternalRequest externalRequest = new ExternalRequest(Direction.UP,5);
		
		Request request = new Request(internalRequest, externalRequest);
		
		new Thread(new AddJobWorker(elevator, request)).start();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
