
public class Qualified implements Employee {
	private String id;
	private String name;
	private String surname;
	private String licence;

	public Qualified(String id, String name, String surname, String licence) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.licence = licence;
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
		if (name.equals("licence"))
			return licence;
		return null;
	}

	@Override
	public void setAttr(String name, Object val) {

	}

	@Override
	public String toString() {
		return "Personel " + id + " " + name + " " + surname + " " + licence;
	}
	
	

}
