
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.*;
import java.util.Scanner;

public class Employee {


    static {
        System.out.println("***** Welcome to the Employee Management System *****");
    }


    Calendar cal=Calendar.getInstance();
    String id=""+(cal.get(Calendar.MONTH)+1)+"LPU";
    Scanner sc=new Scanner(System.in);
    ArrayList<Integer> age = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<Long> cellNo = new ArrayList<>();
    ArrayList<String> designation = new ArrayList<>();
    ArrayList<Integer> salary = new ArrayList<>();
    int i = 0;
    int count = 0;

    public void createProfile() throws SQLException {
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/nikhildb","root","nikhil");
        System.out.println("Enter your name : ");
        name.add(sc.next());
        System.out.println("Enter your age : ");
        age.add(sc.nextInt());
        System.out.println("Enter your cell number : ");
        cellNo.add(sc.nextLong());
        System.out.println("Enter your designation : ");
        designation.add(sc.next());
        if(designation.get(i).equalsIgnoreCase("Programmer")){
            salary.add(46000);
        }
        if(designation.get(i).equalsIgnoreCase("Tester")){
            salary.add(25000);
        }
        if(designation.get(i).equalsIgnoreCase("Manager")){
            salary.add(100000);
        }
        if(designation.get(i).equalsIgnoreCase("DataScientist")){
            salary.add(80000);
        }
        if(designation.get(i).equalsIgnoreCase("BusinessAnalyst")){
            salary.add(44000);
        }
        if(designation.get(i).equalsIgnoreCase("AndroidDeveloper")){
            salary.add(52000);
        }
        if(designation.get(i).equalsIgnoreCase("FullStackDeveloper")){
            salary.add(75000);
        }

        PreparedStatement ps = con.prepareStatement("insert into employee values(?,?,?,?,?,?)");
        ps.setString(1,id+(i+1));
        ps.setInt(2,age.get(i));
        ps.setString(3,name.get(i));
        ps.setLong(4,cellNo.get(i));
        ps.setString(5,designation.get(i));
        ps.setInt(6,salary.get(i));
        ps.executeUpdate();
        i++;
        count = i;
    }

    public void display()  {

        for(int i = 0; i < count; i++) {
            System.out.println("Employee Id = "+id+(i+1)+" Age = " + age.get(i) + ", Name = " + name.get(i) + "," +
                    " " +
                    "Cell Number = "+cellNo.get(i) + ","+" "+
                    "Designation" +
                    " = " + designation.get(i) +
                    ", " +
                    "Salary = " + salary.get(i));
        }

    }
    public void raiseSalary() throws SQLException {
        System.out.println("Enter the employee id of the Employee : ");
        int eid = sc.nextInt();
        System.out.println("Enter the amount by which you want to raise the salary..");
        int amount = sc.nextInt();
        int maxRaise = ((salary.get(eid-1)*30)/100)+salary.get(eid-1);
        if(salary.get(eid-1)+amount <= maxRaise){
            salary.set(eid-1,salary.get(eid-1)+amount);
        }
        else {
            System.out.println("Can't raise salary more than 30% of actual salary!!!!");
        }
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/nikhildb","root","nikhil");
        PreparedStatement ps=con.prepareStatement("Update employee set esalary=? where eid=? ");
        ps.setInt(1,salary.get(eid-1));
        ps.setString(2,id+eid);
        ps.executeUpdate();
    }


    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Employee employee = new Employee();
        int option = -1;
        while (option != 0) {
            System.out.println("Enter '1' to create Profile, '2' to display the Profile, '3' to raise the salary, '4'" +
                    " to clear the Data" +
                    " " +
                    "and " +
                    "'0' to exit");
            option = sc.nextInt();
            switch (option) {
                case 1 -> {
                    employee.createProfile();
                    boolean flag = true;
                    while (flag) {
                        System.out.println("Enter 'yes' to continue or 'No' to go to main menu");
                        String choice = sc.next();
                        if (choice.equalsIgnoreCase("yes")) {
                            employee.createProfile();
                        } else {
                            flag = false;
                        }
                    }
                }
                case 2 -> employee.display();
                case 3 -> employee.raiseSalary();
                case 4->{
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/nikhildb","root", "nikhil");
                    Statement stm = con.createStatement();
                    stm.execute("TRUNCATE TABLE EMPLOYEE");
                }
                case 0 -> System.out.println("Exiting....");
                default -> System.out.println("Invalid Option....");
            }
        }

    }
}
