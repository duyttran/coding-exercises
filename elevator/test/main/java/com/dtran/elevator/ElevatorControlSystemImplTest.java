package com.dtran.elevator;

import com.dtran.elevator.impl.ElevatorControlSystemImpl;
import com.dtran.elevator.model.PickupRequest;
import com.dtran.elevator.model.Elevator.Direction;

public class ElevatorControlSystemImplTest {
	public static void main(String[] args) {
		ElevatorControlSystem cs = new ElevatorControlSystemImpl(6, 2);
		System.out.println(cs);
		cs.step();
		System.out.println(cs);
		cs.pickup(new PickupRequest(4, Direction.UP));
		System.out.println(cs);
		cs.step();
		System.out.println(cs);
		cs.step();
		System.out.println(cs);
//		cs.pickup(new PickupRequest(3, Direction.DOWN));
		System.out.println(cs);
		cs.step();
		System.out.println(cs);
		cs.step();
		System.out.println(cs);
		cs.step();
		System.out.println(cs);
		cs.pickup(new PickupRequest(3, Direction.DOWN));
		System.out.println(cs);
		cs.step();
		System.out.println(cs);
	}
}
