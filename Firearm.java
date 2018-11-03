
public class Firearm implements Mission {
	private int distance;
	private int weaponCount;

	public Firearm(int distance, int weaponCount) {
		this.distance = distance;
		this.weaponCount = weaponCount;
	}

	@Override
	public Object getAttr(String name) {
		if (name.equals("distance"))
			return distance;
		else if (name.equals("weaponCount"))
			return weaponCount;
		return null;
	}

	@Override
	public void setAttr(String name, Object val) {

	}

}
