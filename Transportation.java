
public class Transportation implements Mission {
	private int distance;
	private int passengerCount;

	public Transportation(int distance, int passengerCount) {
		this.distance = distance;
		this.passengerCount = passengerCount;
	}

	@Override
	public Object getAttr(String name) {
		if (name.equals("distance"))
			return distance;
		else if (name.equals("passengerCount"))
			return passengerCount;
		return null;
	}

	@Override
	public void setAttr(String name, Object val) {

	}

}
