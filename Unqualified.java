
public class Unqualified implements Employee {
	private String id;
	private String name;
	private String surname;
	private int workingHours;

	public Unqualified(String id, String name, String surname, int workingHours) throws MyException {
		this.id = id;
		this.name = name;
		this.surname = surname;
		if (workingHours > 12 || workingHours < 6)
			throw new MyException("Exception: Working hours are not in the allowed parameter range!");
		this.workingHours = workingHours;
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
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getSurname() {
		return surname;
	}

	@Override
	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public Object getAttr(String name) {
		if (name.equals("workingHours"))
			return workingHours;
		return null;
	}

	@Override
	public void setAttr(String name, Object val) {

	}

	@Override
	public String toString() {
		return "Personel " + id + " " + name + " " + surname + " " + workingHours;
	}

}
