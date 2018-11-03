import java.util.ArrayList;
import java.util.List;

public class Submarine implements Vehicle {
	private String id;
	private int age;
	private double fuel;
	private double weight;
	private double maxDepth;
	private Qualified captain;
	private List<Unqualified> crew = new ArrayList<>();
	private boolean start;

	public Submarine(String id, int age, double weight, double fuel, double maxDepth) {
		this.id = id;
		this.age = age;
		this.fuel = fuel;
		this.weight = weight;
		this.maxDepth = maxDepth;
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
		if (name.equals("maxDepth"))
			return maxDepth;
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
				throw new MyException("Exception: Submarine can't get another qualified employee");
			return;
		} else if (pos.equalsIgnoreCase("crew")) {
			if (crew.size() < 5)
				crew.add((Unqualified) e);
			else
				throw new MyException("Exception: Submarine can't get another unqualified employee");
			return;
		}
		throw new MyException("Exception: Can't get this employee");
	}

	@Override
	public boolean qualified(Mission m) {
		if (m.getAttr("weaponCount") != null) {
			if (captain == null || crew.size() < 4)
				return false;
			int distance = (int) m.getAttr("distance");
			int wc = (int) m.getAttr("weaponCount");
			if (distance != -1 && wc != -1) {
				if (wc <= maxWC() && distance <= maxDistanceFirearms(wc) && maxDepth >= 1000) {
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
		return (int) (crew.size() * 2);
	}

	private int maxDistanceFirearms(int wc) {
		return (int) ((fuel * 5) / (wc + crew.size()));
	}

	@Override
	public String toString() {
		return "Vehicle " + id + " " + age + " " + (int) weight + " " + (int) fuel + " " + (int) maxDepth;
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
	public void goStart() {
		start = true;
	}
}
