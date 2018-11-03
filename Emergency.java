
public class Emergency implements Mission{
	private int distance;

	public Emergency(int distance) {
		this.distance = distance;
	}

	@Override
	public Object getAttr(String name) {
		if(name.equals("distance"))
			return distance;
		return null;
	}

	@Override
	public void setAttr(String name, Object val) {
		
	}
	
	
}
