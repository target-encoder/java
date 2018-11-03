
public interface Vehicle {
	public String getId();

	public void setId(String id);

	public int getAge();

	public void setAge(int age);

	public double getFuel();

	public void setFuel(double fuel);

	public double getWeight();

	public void setWeight(double weight);

	public Object getAttr(String name);

	public void setAttr(String name, Object val);

	public void assign(Employee e, String pos) throws MyException;

	public boolean qualified(Mission m);
	
	public boolean has(Employee e);
	
	public boolean atStart();
	
	public void goStart() throws MyException;
}
