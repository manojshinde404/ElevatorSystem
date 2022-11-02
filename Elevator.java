package com.springcore.ElevatorSystem;

import java.util.TreeSet;

public class Elevator {
	
	private Direction currDirection = Direction.UP;
	private State curState = State.IDLE;
	private int currFloor = 0;
	
	//jobs which are fetched
	private TreeSet<Request> currJobs = new TreeSet<Request>();
	
	//up pending Jobs
	private TreeSet<Request> upPendingJobs = new TreeSet<Request> ();
	
	//down pending jobs
	private TreeSet<Request> downPendingJobs = new TreeSet<Request> ();
	
	public void StartElevator() {
		System.out.println("The Elevator has Starting Functioning");
		while(true) {
			if(checkIfJob()) {
				if (currDirection == Direction.UP) {
					Request request = currJobs.pollFirst();
					processUpRequest(request);
					if (currJobs.isEmpty()) {
						addPendingDownJobsToCurrentJobs();
					}
				}
				if (currDirection == Direction.DOWN) {
					Request request = currJobs.pollLast();
					processDownRequest(request);
					if (currJobs.isEmpty()) {
						addPendingUpJobsToCurrentJobs();
					}

				}
			}
		}
	}
	
	public boolean checkIfJob() {

		if (currJobs.isEmpty()) {
			return false;
		}
		return true;

	}
	
	
	private void processUpRequest(Request request) {
		int startFloor = currFloor;
		if (startFloor < request.getExternalRequest().getSourceFloor()) {
			for (int i = startFloor; i <= request.getExternalRequest().getSourceFloor(); i++) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("We have reached floor -- " + i);
				currFloor = i;
			}
		}

		System.out.println("Reached Source Floor--opening door");

		startFloor = currFloor;

		for (int i = startFloor + 1; i <= request.getInternalRequest().getDestinationFloor(); i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("We have reached floor -- " + i);
			currFloor = i;
			if (checkIfNewJobCanBeProcessed(request)) {
				break;
			}
		}

	}
	
	
	private void processDownRequest(Request request) {

		int startFloor = currFloor;
		if (startFloor < request.getExternalRequest().getSourceFloor()) {
			for (int i = startFloor; i <= request.getExternalRequest().getSourceFloor(); i++) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("We have reached floor -- " + i);
				currFloor = i;
			}
		}

		System.out.println("Reached Source Floor--opening door");

		startFloor = currFloor;

		for (int i = startFloor - 1; i >= request.getInternalRequest().getDestinationFloor(); i--) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("We have reached floor -- " + i);
			currFloor = i;
			if (checkIfNewJobCanBeProcessed(request)) {
				break;
			}
		}

	}

	private boolean checkIfNewJobCanBeProcessed(Request currentRequest) {
		if (checkIfJob()) {
			if (currDirection == Direction.UP) {
				Request request = currJobs.pollLast();
				if (request.getInternalRequest().getDestinationFloor() < currentRequest.getInternalRequest()
						.getDestinationFloor()) {
					currJobs.add(request);
					currJobs.add(currentRequest);
					return true;
				}
				currJobs.add(request);

			}

			if (currDirection == Direction.DOWN) {
				Request request = currJobs.pollFirst();
				if (request.getInternalRequest().getDestinationFloor() > currentRequest.getInternalRequest()
						.getDestinationFloor()) {
					currJobs.add(request);
					currJobs.add(currentRequest);
					return true;
				}
				currJobs.add(request);

			}

		}
		return false;

	}

	private void addPendingDownJobsToCurrentJobs() {
		if (!downPendingJobs.isEmpty()) {
			System.out.println("Pick a pending down job and execute it by putting in current job");
			currJobs = downPendingJobs;
			currDirection = Direction.DOWN;
		} else {
			curState = State.IDLE;
			System.out.println("The elevator is in Idle state");
		}

	}

	private void addPendingUpJobsToCurrentJobs() {
		if (!upPendingJobs.isEmpty()) {
			System.out.println("Pick a pending up job and execute it by putting in current job");

			currJobs = upPendingJobs;
			currDirection = Direction.UP;
		} else {
			curState = State.IDLE;
			System.out.println("The elevator is in Idle state");

		}

	}

	public void addJob(Request request) {
		
		if (curState == State.IDLE) {
			if (currFloor == request.getExternalRequest().getSourceFloor()) {
				
				System.out.println("Added current queue job -- lift state is - " + curState + " location is - "
						+ currFloor + " to move to floor - " + request.getInternalRequest().getDestinationFloor());
			}
			else {
				System.out.println("Added current queue job -- lift state is - " + curState + " location is - "
						+ currFloor + " to move to floor - " + request.getExternalRequest().getSourceFloor());
			}
			curState = State.MOVING;
			currDirection = request.getExternalRequest().getDirectionToGo();
			currJobs.add(request);
		} else if (curState == State.MOVING) {

			if (request.getExternalRequest().getDirectionToGo() != currDirection) {
				addtoPendingJobs(request);
			} else if (request.getExternalRequest().getDirectionToGo() == currDirection) {
				if (currDirection == Direction.UP
						&& request.getInternalRequest().getDestinationFloor() < currFloor) {
					addtoPendingJobs(request);
				} else if (currDirection == Direction.DOWN
						&& request.getInternalRequest().getDestinationFloor() > currFloor) {
					addtoPendingJobs(request);
				} else {
					currJobs.add(request);
				}

			}

		}

	}

	public void addtoPendingJobs(Request request) {
		if (request.getExternalRequest().getDirectionToGo() == Direction.UP) {
			System.out.println("Add to pending up jobs");
			upPendingJobs.add(request);
		} else {
			System.out.println("Add to pending down jobs");
			downPendingJobs.add(request);
		}
	}

}

enum State {

	MOVING, STOPPED, IDLE

}

enum Direction {

	UP, DOWN

}
