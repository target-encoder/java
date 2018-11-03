import java.util.ArrayList;
import java.util.List;

public class Bus implements Vehicle {
	private String id;
	private int age;
	private double fuel;
	private double weight;
	private double engineVolume;
	private Qualified driver;
	private List<Unqualified> crew = new ArrayList<>();
	private boolean start;

	public Bus(String id, int age, double weight, double fuel, double engineVolume) {
		this.id = id;
		this.age = age;
		this.fuel = fuel;
		this.weight = weight;
		this.engineVolume = engineVolume;
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
		if (name.equals("engineVolume"))
			return engineVolume;
		return null;
	}

	@Override
	public void setAttr(String name, Object val) {

	}

	@Override
	public void assign(Employee e, String pos) throws MyException {
		if (e.getAttr("licence") != null) {
			if (driver == null && pos.equalsIgnoreCase("driver"))
				driver = (Qualified) e;
			else
				throw new MyException("Exception: Bus can't get another qualified employee");
			return;
		} else if (pos.equalsIgnoreCase("crew")) {
			if (crew.size() < 2)
				crew.add((Unqualified) e);
			else
				throw new MyException("Exception: Bus can't get another unqualified employee");
			return;
		}
		throw new MyException("Exception: Can't get this employee");

	}

	@Override
	public boolean qualified(Mission m) {
		if (m.getAttr("passengerCount") != null) {
			if (driver == null || crew.size() < 1)
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
		} else if (m.getClass().getSimpleName().equals("Emergency")) {
			if (driver == null)
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
		return (int) ((crew.size() + (engineVolume / 1000)) * 10);
	}

	private int maxDistanceTransportation(int pc) {
		return (int) ((fuel * 10) / ((pc / 10) + crew.size()));
	}

	private int maxDistanceEmergency() {
		return (int) ((fuel * 10) / (crew.size() + 1));
	}

	@Override
	public String toString() {
		return "Vehicle " + id + " " + age + " " + (int) weight + " " + (int) fuel + " " + (int) engineVolume;
	}

	@Override
	public boolean has(Employee e) {
		if (driver != null)
			if (driver.getId().equals(e.getId()))
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
		if (driver != null)
			start = true;
		else
			throw new MyException("Exception: No driver");
	}

}
