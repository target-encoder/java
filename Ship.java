import java.util.ArrayList;
import java.util.List;

public class Ship implements Vehicle {
	private String id;
	private int age;
	private double fuel;
	private double weight;
	private double engineOutput;
	private Qualified captain;
	private List<Unqualified> crew = new ArrayList<>();
	private boolean start;

	public Ship(String id, int age, double weight, double fuel, double engineOutput) {
		this.id = id;
		this.age = age;
		this.fuel = fuel;
		this.weight = weight;
		this.engineOutput = engineOutput;
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
		if (name.equals("engineOutput"))
			return engineOutput;
		return null;
	}

	@Override
	public void setAttr(String name, Object val) {

	}

	@Override
	public void assign(Employee e, String pos) throws MyException {
		if (e.getAttr("licence") != null) {
			if (captain == null && pos.equalsIgnoreCase("captain"))
				captain = (Qualified) e;
			else
				throw new MyException("Exception: Ship can't get another qualified employee");
			return;
		} else if (pos.equalsIgnoreCase("crew")) {
			if (crew.size() < 10)
				crew.add((Unqualified) e);
			else
				throw new MyException("Exception: Ship can't get another unqualified employee");
			return;
		}
		throw new MyException("Exception: Can't get this employee");

	}

	@Override
	public boolean qualified(Mission m) {
		if (m.getAttr("passengerCount") != null) {
			if (captain == null || crew.size() < 5)
				return false;
			int distance = (int) m.getAttr("distance");
			int pc = (int) m.getAttr("passengerCount");
			if (distance != -1 && pc != -1) {
				if (pc <= maxPC() && distance <= maxDistanceTransportation(pc)) {
					start = false;
					return true;
				} else
					return false;
			}
			return true;
		} else if (m.getAttr("load") != null) {
			if (captain == null || crew.size() < 5)
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
		} else if (m.getClass().getSimpleName().equals("Emergency")) {
			if (captain == null || crew.size() < 2)
				return false;
			int distance = (int) m.getAttr("distance");
			if (distance != -1) {
				if (distance < maxDistanceEmergency()) {
					start = false;
					return true;
				} else
					return false;
			}
			return true;
		}
		return false;
	}

	private int maxPC() {
		return (int) ((crew.size() + (engineOutput / 5000)) * 20);
	}

	private int maxLoad() {
		return (int) (((engineOutput / 1000) + crew.size()) * 15);
	}

	private int maxDistanceTransportation(int pc) {
		return (int) ((fuel) / ((pc / 100) + crew.size()));
	}

	private int maxDistanceCargo(int load) {
		return (int) ((fuel * 20) / (((load + weight) / 100) + (engineOutput / 10000)));
	}

	private int maxDistanceEmergency() {
		return (int) (fuel / (engineOutput / 5000));
	}

	@Override
	public String toString() {
		return "Vehicle " + id + " " + age + " " + (int) weight + " " + (int) fuel + " " + (int) engineOutput;
	}

	@Override
	public boolean has(Employee e) {
		if (captain != null)
			if (captain.getId().equals(e.getId()))
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
		if (captain != null)
			start = true;
		else
			throw new MyException("Exception: No captain");
	}

}
