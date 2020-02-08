package com.dtran.elevator;

import java.util.Collection;

import com.dtran.elevator.model.Elevator;
import com.dtran.elevator.model.PickupRequest;

/**
 * ElevatorControlSystem encompasses many elevators and the logic to move elevators and service two types of
 * request from a user
 * <p/><t><li>Pickup request given a direction and floor</li>
 * <li>Elevator to floor request given a elevator id and floor</li><t/>
 * <p/>This class will also act as the state holder for the current system and ability to step through time ticks
 * that will process requests and move elevators 
 *
 */
public interface ElevatorControlSystem {
	
	/**
	 * Retrieves all the elevators in this system
	 * @return Collection of elevators
	 */
	public Collection<Elevator> getElevators();
	
	/**
	 * Tells a elevator to go to a given floor number. This is equivalent to having an operator in an elevator click a floor number to go to
	 * @param elevatorId id of the elevator to send update to
	 * @param goalFloorNumber floor number to set elevator goal floor number to
	 */
	public void update(int elevatorId, int goalFloorNumber);
	
	/**
	 * Submits a pickup request for an elevator. Might not be serviced immediately, given elevators are currently busy
	 * @param request 
	 */
	public void pickup(PickupRequest request);
	
	/**
	 * Takes one tick through time for the state of the Elevator Control System, which triggers elevators to be moved and request to be assigned. 
	 */
	public void step();
}
