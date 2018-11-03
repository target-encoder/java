import java.util.ArrayList;
import java.util.List;

public class Plane implements Vehicle {
	private String id;
	private int age;
	private double fuel;
	private double weight;
	private int motorCount;
	private Qualified pilot;
	private Qualified copilot;
	private List<Unqualified> crew = new ArrayList<>();
	private boolean start;

	public Plane(String id, int age, double weight, double fuel, int motorCount) {
		this.id = id;
		this.age = age;
		this.fuel = fuel;
		this.weight = weight;
		this.motorCount = motorCount;
		start = false;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int getAge() {
		return age;
	}

	@Override
	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public double getFuel() {
		return fuel;
	}

	@Override
	public void setFuel(double fuel) {
		this.fuel = fuel;
	}

	@Override
	public double getWeight() {
		return weight;
	}

	@Override
	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public Object getAttr(String name) {
		if (name.equals("motorCount"))
			return motorCount;
		return null;
	}

	@Override
	public void setAttr(String name, Object val) {

	}

	@Override
	public void assign(Employee e, String pos) throws MyException {
		if (e.getAttr("licence") != null) {
			if (pilot == null && pos.equalsIgnoreCase("pilot"))
				pilot = (Qualified) e;
			else if (copilot == null && pos.equalsIgnoreCase("copilot"))
				copilot = (Qualified) e;
			else
				throw new MyException("Exception: Plane can't get another qualified employee");
			return;
		} else if (pos.equalsIgnoreCase("crew")) {
			if (crew.size() < 5)
				crew.add((Unqualified) e);
			else
				throw new MyException("Exception: Plane can't get another unqualified employee");
			return;
		}
		throw new MyException("Exception: Can't get this employee");
	}

	@Override
	public boolean qualified(Mission m) {
		if (m.getAttr("load") != null) {
			if (pilot == null || crew.size() < 3)
				return false;
			int distance = (int) m.getAttr("distance");
			int load = (int) m.getAttr("load");
			if (distance != -1 && load != -1) {
				if (load <= maxLoad() && distance <= maxDistanceCargo(load)) {
					start = false;
					return true;
				} else
					return false;
			}
			return true;
		} else if (m.getAttr("passengerCount") != null) {
			if (pilot == null || copilot == null)
				return false;
			int distance = (int) m.getAttr("distance");
			int passengerCount = (int) m.getAttr("passengerCount");
			if (distance != -1 && passengerCount != -1) {
				if (passengerCount <= maxPassengerCount() && distance <= maxDistanceTransportation(passengerCount)) {
					start = false;
					return true;
				} else
					return false;
			}
			return true;
		}
		return false;
	}

	private int maxLoad() {
		return (motorCount + crew.size() + 2) * 10;
	}

	private int maxPassengerCount() {
		return (motorCount + crew.size()) * 15;
	}

	private int maxDistanceCargo(int load) {
		return (int) (fuel / (((weight + load) / 10) + (motorCount / 2)));
	}

	private int maxDistanceTransportation(int pc) {
		return (int) (fuel / ((weight / 10) + (motorCount / 2) + (pc / 30)));
	}

	@Override
	public String toString() {
		return "Vehicle " + id + " " + age + " " + (int) weight + " " + (int) fuel + " " + (int) motorCount;
	}

	@Override
	public boolean has(Employee e) {
		if (pilot != null)
			if (pilot.getId().equals(e.getId()))
				return true;
		if (copilot != null)
			if (copilot.getId().equals(e.getId()))
				return true;
		for (Employee c : crew)
			if (c.getId().equals(e.getId()))
				return true;
		return false;
	}

	@Override
	public boolean atStart() {
		return start;
	}

	@Override
	public void goStart() throws MyException {
		if(pilot != null)
			start = true;
		else
			throw new MyException("Exception: No pilot");
	}

}
