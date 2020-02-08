package com.dtran.elevator.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.dtran.elevator.model.Elevator;
import com.dtran.elevator.model.PickupRequest;
import com.dtran.elevator.model.Elevator.Direction;

/**
 * ElevatorControlAI holds the information on how to best retrieve an elevator given a request. This might
 * also hold in the future, how to distribute elevators efficiently while they are idle
 */
public class ElevatorControlAI {
	
	/**
	 * Gets the closest valid elevator to service the request. If there are no valid elevators, then will return null
	 * @param elevators
	 * @param request
	 * @return
	 */
	public Elevator getElevator(Collection<Elevator> elevators, final PickupRequest request) {
		//find all valid elevators first
		List<Elevator> validElevators = new LinkedList<Elevator>() ;
		for (Elevator elevator : elevators) {
			if (isValid(elevator, request)) {
				validElevators.add(elevator);					
			}
		}
		
		//simple O(nlogn) sort
		//since there are only 16 elevators, it is small enough data set to just sort and find the best elevator every time 
		//we get a new request
		Collections.sort(validElevators, new Comparator<Elevator>() {
			@Override
			public int compare(Elevator e1, Elevator e2) {
				int distance1 = Math.abs(e1.getFloorNumber() - request.getFloorNumber());
				int distance2 = Math.abs(e2.getFloorNumber() - request.getFloorNumber());
				if (distance1 > distance2) {
					return 1;
				} else if (distance1 < distance2) {
					return -1;
				} else {
					return 0;
				}
			}
		});
		
		return validElevators.isEmpty() ? null : validElevators.get(0);
	}
	
	/**
	 * An elevator is valid to be used for a request if it is idle (in HOLD direction) or the request is within the same path that 
	 * the elevator is already traveling
	 * @param elevator
	 * @param request
	 * @return
	 */
	private boolean isValid(Elevator elevator, PickupRequest request) {
		return isIdle(elevator) || isOnSamePath(elevator, request);
	}
	
	/**
	 * Helper method for checking if elevator is idle
	 * @param elevator
	 * @return
	 */
	private boolean isIdle(Elevator elevator) {
		return elevator.getFloorNumber() == elevator.getGoalFloorNumber() && elevator.getDirection() == Direction.HOLD;
	}
	
	/**
	 * Helper method for checking if request is on the same path as the elevator
	 * @param elevator
	 * @param request
	 * @return
	 */
	private boolean isOnSamePath(Elevator elevator, PickupRequest request) {
		int currFloor = elevator.getFloorNumber();
		int pickupFloor = request.getFloorNumber();
		int goalFloor = elevator.getGoalFloorNumber();
		boolean isBetween = (currFloor <= pickupFloor && pickupFloor <= goalFloor)
				|| (currFloor >= pickupFloor && pickupFloor >= goalFloor);
		boolean isSameDirection = elevator.getDirection() == request.getDirection();
		return isBetween && isSameDirection;
	}
}
