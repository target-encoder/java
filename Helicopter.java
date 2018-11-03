import java.util.ArrayList;
import java.util.List;

public class Helicopter implements Vehicle {
	private String id;
	private int age;
	private double fuel;
	private double weight;
	private int maxHeight;
	private Qualified pilot;
	private List<Unqualified> crew = new ArrayList<>();
	private boolean start;

	public Helicopter(String id, int age, double weight, double fuel, int maxHeight) {
		this.id = id;
		this.age = age;
		this.fuel = fuel;
		this.weight = weight;
		this.maxHeight = maxHeight;
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
		if (name.equals("maxHeight"))
			return maxHeight;
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
			else
				throw new MyException("Exception: Helicopter can't get another qualified employee");
			return;
		} else if (pos.equalsIgnoreCase("crew")) {
			if (crew.size() < 2)
				crew.add((Unqualified) e);
			else
				throw new MyException("Exception: Helicopter can't get another unqualified employee");
			return;
		}
		throw new MyException("Exception: Can't get this employee");
	}

	@Override
	public boolean qualified(Mission m) {
		if (m.getAttr("weaponCount") != null) {
			if (pilot == null || crew.size() < 1)
				return false;
			int distance = (int) m.getAttr("distance");
			int wc = (int) m.getAttr("weaponCount");
			if (distance != -1 && wc != -1) {
				if (wc <= maxWC() && distance <= maxDistanceFirearm(wc)) {
					start = false;
					return true;
				} else
					return false;
			}
			return true;
		} else if (m.getClass().getSimpleName().equals("Emergency")) {
			if (pilot == null)
				return false;
			int distance = (int) m.getAttr("distance");
			if (distance != -1) {
				if (distance <= maxDistanceEmergency() && maxHeight >= 2000) {
					start = false;
					return true;
				} else
					return false;
			}
			return true;
		}
		return false;
	}

	private int maxWC() {
		return crew.size() * 2;
	}

	private int maxDistanceFirearm(int wc) {
		return (int) (fuel / (crew.size() + wc));
	}

	private int maxDistanceEmergency() {
		return (int) (fuel / (crew.size() + (maxHeight / 1000)));
	}

	@Override
	public String toString() {
		return "Vehicle " + id + " " + age + " " + (int) weight + " " + (int) fuel + " " + (int) maxHeight;
	}

	@Override
	public boolean has(Employee e) {
		if (pilot != null)
			if (pilot.getId().equals(e.getId()))
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
		if (pilot != null)
			start = true;
		else
			throw new MyException("Exception: no pilot");
	}

}
