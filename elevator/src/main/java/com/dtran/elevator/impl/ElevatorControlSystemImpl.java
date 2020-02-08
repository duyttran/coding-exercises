package com.dtran.elevator.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.dtran.elevator.ElevatorControlSystem;
import com.dtran.elevator.model.Elevator;
import com.dtran.elevator.model.PickupRequest;
import com.dtran.elevator.model.Elevator.Direction;

public class ElevatorControlSystemImpl implements ElevatorControlSystem {

	Map<Integer, Elevator> elevators;
	ElevatorControlAI ai;
	Queue<PickupRequest> pendingRequests = new LinkedList<PickupRequest>();
	int numberOfFloors;
	/**
	 * Initialize elevator system with number of floors and elevators
	 * @param numberOfFloors
	 * @param numberOfElevators
	 */
	public ElevatorControlSystemImpl(int numberOfFloors, int numberOfElevators) {
		this.elevators = new HashMap<Integer, Elevator>();
		this.ai = new ElevatorControlAI();
		this.numberOfFloors = numberOfFloors;
		for (int i = 0; i < numberOfElevators; i++) {
			Elevator elevator = new Elevator(i);
			this.elevators.put(i, elevator);
		}
		
	}
	
	@Override
	public Collection<Elevator> getElevators() {
		return elevators.values();
	}

	@Override
	public void update(int elevatorId, int goalFloorNumber) {
		//make sure floor request is valid
		if (goalFloorNumber > numberOfFloors) {
			return;
		}
		
		//currently allows user to override the current elevator direction
		//TODO: better implemented to queue up goal floor numbers, or in some elevators, don't allow floors not in current trajectory
		//      and only allow elevator goals when in HOLD state
		Elevator elevator = elevators.get(elevatorId);
		if (elevator.getFloorNumber() == goalFloorNumber) {
			return;
		} else {
			elevator.setGoalFloorNumber(goalFloorNumber);
			elevator.setDirection(elevator.getFloorNumber() < goalFloorNumber ? Direction.UP : Direction.DOWN);
		}
	}

	@Override
	public void pickup(PickupRequest request) {
		//make sure floor request is valid
		if (request.getFloorNumber() > numberOfFloors) {
			return;
		}
		
		//if can't find a valid elevator, put on pending requests
		Elevator elevator = ai.getElevator(getElevators(), request);
		if (elevator != null) {
			update(elevator.getElevatorId(), request.getFloorNumber());			
		} else {
			pendingRequests.add(request);
		}
	}

	@Override
	public void step() {
		
		//move elevators in direction of goal
		for (Elevator elevator : elevators.values()) {
			move(elevator);
		}
		
		//assign elevators if can find valid
		while (!pendingRequests.isEmpty()) {
			Elevator elevator = ai.getElevator(getElevators(), pendingRequests.peek());
			if (elevator != null) {
				PickupRequest request = pendingRequests.poll();
				update(elevator.getElevatorId(), request.getFloorNumber());
			} else {
				break;
			}
		}
	}
	
	/**
	 * Moves elevator in correct direction, or sets to HOLD if reached desired Goal Floor Number
	 * @param elevator
	 */
	public void move(Elevator elevator) {
		if (elevator.getGoalFloorNumber() == elevator.getFloorNumber()) {
			elevator.setDirection(Direction.HOLD);
			return;
		} else if (elevator.getFloorNumber() < elevator.getGoalFloorNumber()) {
			elevator.setFloorNumber(elevator.getFloorNumber() + 1);
		} else {
			elevator.setFloorNumber(elevator.getFloorNumber() - 1);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Elevator elevator : getElevators()) {
			sb.append(elevator).append("\n");
		}
		return sb.toString();
	}

}
