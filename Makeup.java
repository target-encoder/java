import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Makeup {

	private static List<Employee> employees;
	private static List<Vehicle> vehicles;
	private static BufferedWriter out;

	public static void main(String[] args) throws IOException {
		List<String> inputs = null;
		employees = new ArrayList<>();
		vehicles = new ArrayList<>();
		out = new BufferedWriter(new FileWriter(args[1]));
		try {
			inputs = Files.lines(Paths.get(args[0])).filter(str -> str.length() > 0).collect(Collectors.toList());
		} catch (IOException e) {
			out.write("Input file not found");
		}
		for (String in : inputs)
			parseInput(in);
		out.close();
	}

	private static void parseInput(String input) throws IOException {
		if (input.startsWith("CreatePersonel")) {
			String[] parts = input.split(" ");
			try {
				addEmployee(parts);
				out.write("Personel Created " + parts[2]);
			} catch (MyException e) {
				out.write(e.getMessage());
			}
		} else if (input.startsWith("CreateVehicle")) {
			String[] parts = input.split(" ");
			try {
				addVehicle(parts);
				out.write("Vehicle Created " + parts[2]);
			} catch (MyException e) {
				out.write(e.getMessage());
			}
		} else if (input.startsWith("ShowPersonel")) {
			String[] parts = input.split(" ");
			try {
				out.write(getPersonel(parts[1]).toString());
			} catch (MyException e) {
				out.write(e.getMessage());
			}
		} else if (input.startsWith("ShowVehicle")) {
			String[] parts = input.split(" ");
			try {
				out.write(getVehicle(parts[1]).toString());
			} catch (MyException e) {
				out.write(e.getMessage());
			}
		} else if (input.startsWith("AssignPersonel")) {
			String[] parts = input.split(" ");
			try {
				assignPersonnel(parts);
				out.write("Personel " + parts[1] + " Assigned To Vehicle " + parts[2]);
			} catch (MyException e) {
				out.write(e.getMessage());
			}
		} else if (input.startsWith("MoveToStartPoint")) {
			String[] parts = input.split(" ");
			try {
				if (getVehicle(parts[1]).getFuel() >= Integer.parseInt(parts[2]) && !getVehicle(parts[1]).atStart()) {
					getVehicle(parts[1]).setFuel(getVehicle(parts[1]).getFuel() - Integer.parseInt(parts[2]));
					getVehicle(parts[1]).goStart();
				}
				else
					throw new MyException("Exception: Not enough fuel to move to start point or already at start");
				out.write("Vehicle " + parts[1] + " Moved To Start Point");
			} catch (MyException e) {
				out.write(e.getMessage());
			}
		} else if (input.startsWith("EmergencyAction")) {
			String[] parts = input.split(" ");
			try {
				if (getVehicle(parts[1]).qualified(new Emergency(Integer.parseInt(parts[2]))))
					out.write("Emergency Mission Accomplished By " + parts[1]);
				else
					throw new MyException("Exception: Requirements are not met");
			} catch (MyException e) {
				out.write(e.getMessage());
			}
		} else if (input.startsWith("CanEmergencyAction")) {
			String[] parts = input.split(" ");
			try {
				if (getVehicle(parts[1]).qualified(new Emergency(-1)))
					out.write("Positive");
				else
					out.write("Negative");
			} catch (MyException e) {
				out.write(e.getMessage());
			}
		} else if (input.startsWith("UseFireArm")) {
			String[] parts = input.split(" ");
			try {
				if (getVehicle(parts[1]).qualified(new Firearm(Integer.parseInt(parts[2]), Integer.parseInt(parts[3]))))
					out.write("Firearm Mission Accomplished By " + parts[1]);
				else
					throw new MyException("Exception: Requirements are not met");
			} catch (MyException e) {
				out.write(e.getMessage());
			}
		} else if (input.startsWith("CanUseFireArm")) {
			String[] parts = input.split(" ");
			try {
				if (getVehicle(parts[1]).qualified(new Firearm(-1, -1)))
					out.write("Positive");
				else
					out.write("Negative");
			} catch (MyException e) {
				out.write(e.getMessage());
			}
		} else if (input.startsWith("Transport")) {
			String[] parts = input.split(" ");
			try {
				if (getVehicle(parts[1]).qualified(new Transportation(Integer.parseInt(parts[2]), Integer.parseInt(parts[3]))))
					out.write("Transport Mission Accomplished By " + parts[1]);
				else
					throw new MyException("Exception: Requirements are not met");
			} catch (MyException e) {
				out.write(e.getMessage());
			}
		} else if (input.startsWith("CanTransport")) {
			String[] parts = input.split(" ");
			try {
				if (getVehicle(parts[1]).qualified(new Transportation(-1, -1)))
					out.write("Positive");
				else
					out.write("Negative");
			} catch (MyException e) {
				out.write(e.getMessage());
			}
		} else if (input.startsWith("TransferCargo")) {
			String[] parts = input.split(" ");
			try {
				if (getVehicle(parts[1]).qualified(new Cargo(Integer.parseInt(parts[2]), Integer.parseInt(parts[3]))))
					out.write("Cargo Mission Accomplished By " + parts[1]);
				else
					throw new MyException("Exception: Requirements are not met");
			} catch (MyException e) {
				out.write(e.getMessage());
			}
		} else if (input.startsWith("CanTransferCargo")) {
			String[] parts = input.split(" ");
			try {
				if (getVehicle(parts[1]).qualified(new Cargo(-1, -1)))
					out.write("Positive");
				else
					out.write("Negative");
			} catch (MyException e) {
				out.write(e.getMessage());
			}
		}
		out.write("\n");
	}

	private static void addEmployee(String[] parts) throws MyException {
		if (employees.stream().filter(e -> e.getId().equals(parts[2])).count() > 0)
			throw new MyException("Exception: Employee with same ID exists");
		if (parts[1].equals("U")) {
			employees.add(new Unqualified(parts[2], parts[3], parts[4], Integer.parseInt(parts[5])));
		} else if (parts[1].equals("Q")) {
			employees.add(new Qualified(parts[2], parts[3], parts[4], parts[5]));
		}
	}

	private static void addVehicle(String[] parts) throws MyException {
		if (vehicles.stream().filter(v -> v.getId().equals(parts[2])).count() > 0)
			throw new MyException("Exception: Vehicle with same ID exists");
		if (parts[1].equals("Bus")) {
			vehicles.add(new Bus(parts[2], Integer.parseInt(parts[3]), Double.parseDouble(parts[4]),
					Double.parseDouble(parts[5]), Double.parseDouble(parts[6])));
		} else if (parts[1].equals("Truck")) {
			vehicles.add(new Truck(parts[2], Integer.parseInt(parts[3]), Double.parseDouble(parts[4]),
					Double.parseDouble(parts[5]), Double.parseDouble(parts[6]), Integer.parseInt(parts[7])));
		} else if (parts[1].equals("Plane")) {
			vehicles.add(new Plane(parts[2], Integer.parseInt(parts[3]), Double.parseDouble(parts[4]),
					Double.parseDouble(parts[5]), Integer.parseInt(parts[6])));
		} else if (parts[1].equals("Helicopter")) {
			vehicles.add(new Helicopter(parts[2], Integer.parseInt(parts[3]), Double.parseDouble(parts[4]),
					Double.parseDouble(parts[5]), Integer.parseInt(parts[6])));
		} else if (parts[1].equals("Ship")) {
			vehicles.add(new Ship(parts[2], Integer.parseInt(parts[3]), Double.parseDouble(parts[4]),
					Double.parseDouble(parts[5]), Double.parseDouble(parts[6])));
		} else if (parts[1].equals("Submarine")) {
			vehicles.add(new Submarine(parts[2], Integer.parseInt(parts[3]), Double.parseDouble(parts[4]),
					Double.parseDouble(parts[5]), Double.parseDouble(parts[6])));
		}
	}

	private static Vehicle getVehicle(String id) throws MyException {
		try {
			return vehicles.stream().filter(v -> v.getId().equals(id)).findFirst().get();
		} catch (NoSuchElementException e) {
			throw new MyException("Exception: Vehicle with given ID " + id + " does not exist");
		}
	}

	private static Employee getPersonel(String id) throws MyException {
		try {
			return employees.stream().filter(e -> e.getId().equals(id)).findFirst().get();
		} catch (NoSuchElementException e) {
			throw new MyException("Exception: Employee with given ID " + id + " does not exist");
		}
	}

	private static void assignPersonnel(String[] parts) throws MyException {
		for(Vehicle v : vehicles)
			if(v.has(getPersonel(parts[1])))
				throw new MyException("Exception: Personel "+parts[1]+" is already assigned");
		getVehicle(parts[2]).assign(getPersonel(parts[1]), parts[3]);
	}

}
