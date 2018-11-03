
public class Cargo implements Mission {
	private int distance;
	private int load;

	public Cargo(int distance, int load) {
		this.distance = distance;
		this.load = load;
	}

	@Override
	public Object getAttr(String name) {
		if (name.equals("distance"))
			return distance;
		else if (name.equals("load"))
			return load;
		return null;
	}

	@Override
	public void setAttr(String name, Object val) {

	}

}
