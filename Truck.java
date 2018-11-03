
public class Truck implements Vehicle {
	private String id;
	private int age;
	private double fuel;
	private double weight;
	private double engineVolume;
	private int torque;
	private Qualified driver;
	private boolean start;

	public Truck(String id, int age, double weight, double fuel, double engineVolume, int torque) {
		this.id = id;
		this.age = age;
		this.fuel = fuel;
		this.weight = weight;
		this.engineVolume = engineVolume;
		this.torque = torque;
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
		if (name.equals("torque"))
			return torque;
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
				throw new MyException("Truck can't get another qualified employee");
		} else {
			throw new MyException("Truck can't get another unqualified employee");
		}
	}

	@Override
	public boolean qualified(Mission m) {
		if (m.getAttr("load") != null) {
			if (driver == null)
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
		} else if (m.getAttr("weaponCount") != null) {
			if (driver == null)
				return false;
			int distance = (int) m.getAttr("distance");
			int wc = (int) m.getAttr("weaponCount");
			if (distance != -1 && wc != -1) {
				if (wc <= maxWC() && distance <= maxDistanceFirearms(wc) && torque >= 600 && engineVolume >= 6000) {
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
		return (int) (((engineVolume / 1000) + (torque / 100)) * 3);
	}

	private int maxWC() {
		return (int) ((engineVolume / 2000) + (torque / 300));
	}

	private int maxDistanceCargo(int load) {
		return (int) ((fuel * 10) / (((load + weight) / 30) + (engineVolume / 2000)));
	}

	private int maxDistanceFirearms(int wc) {
		return (int) ((fuel * 10) / ((weight / 30) + (engineVolume / 2000) + wc));
	}

	@Override
	public String toString() {
		return "Vehicle " + id + " " + age + " " + (int) weight + " " + (int) fuel + " " + (int) engineVolume + " "
				+ (int) torque;
	}

	@Override
	public boolean has(Employee e) {
		if (driver != null)
			if (driver.getId().equals(e.getId()))
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
