package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Employee;

public class App {
	public static Double inSalary = 0.0;

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
			
		List<Employee> emp = new ArrayList<>(); 
		List<String> emails = new ArrayList<>(); 
		List<Double> salaries = new ArrayList<>();
		
		try (Scanner input = new Scanner(System.in)){
			print("Enter full file path: ");
			File source = new File(input.next());
			
			print("Enter salary: ");
			inSalary = input.nextDouble();
			
			try (BufferedReader br = new BufferedReader(new FileReader(source.getPath()))){
				String line = br.readLine();
				
				while(line != null) {
					String[] fields = line.split(",");
					emp.add(new Employee(fields[0], fields[1], Double.parseDouble(fields[2])));
					line = br.readLine();
				}
			} catch (IOException e) {
				println("IO Error: " + e.getMessage());
				e.printStackTrace();
			}
			
		} catch (InputMismatchException e) {
			println("Input Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		println("Email of people whose salary is more than 2000.00: ");
		
		emails = emp.stream().filter(x -> x.getSalary() > inSalary).map(App::getEmailsFromStream).sorted().collect(Collectors.toList());
		
		salaries = emp.stream().filter(x -> x.getName().toUpperCase().charAt(0) == 'M' ).map(App::getSalariesFromStream).collect(Collectors.toList());
		
		Double sumSalaries = salaries.stream().reduce(0.0, (x , y) -> x + y);
		
		emails.forEach(System.out::println);
		
		printf("Sum of salary of people whose name starts with 'M': %.2f", sumSalaries);
	}
	
	//prints
	
	public static <T> void print(T value) {
		System.out.print(value);
	}
	
	public static <T> void println(T value) {
		System.out.println(value);
	}
	
	public static <T> void printf(String argument, T value) {
		System.out.printf(argument, value);
	}
	
	// other methods
	
	public static Double getSalariesFromStream(Employee e) {
		return e.getSalary();
	}
	
	public static String getEmailsFromStream(Employee e) {
		return e.getEmail();
	}

}
