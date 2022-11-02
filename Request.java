package com.springcore.ElevatorSystem;


public class Request implements Comparable<Request> {
	
	private InternalRequest internalRequest;
	private ExternalRequest externalRequest;



	public Request(InternalRequest internalRequest, ExternalRequest externalRequest) {
		super();
		this.internalRequest = internalRequest;
		this.externalRequest = externalRequest;
	}

	public InternalRequest getInternalRequest() {
		return internalRequest;
	}

	public void setInternalRequest(InternalRequest internalRequest) {
		this.internalRequest = internalRequest;
	}

	public ExternalRequest getExternalRequest() {
		return externalRequest;
	}

	public void setExternalRequest(ExternalRequest externalRequest) {
		this.externalRequest = externalRequest;
	}

	public int compareTo(Request o) {
		// TODO Auto-generated method stub
		if (this.getInternalRequest().getDestinationFloor() == o.getInternalRequest().getDestinationFloor()) {
			return 0;
		} else if (this.getInternalRequest().getDestinationFloor() > o.getInternalRequest().getDestinationFloor()) {
			return 1;
		} else {
			return -1;
		}
	}
	
}
