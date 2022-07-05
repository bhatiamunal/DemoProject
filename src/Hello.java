import java.sql.*;
import java.util.*;  
/*
1) public ResultSet executeQuery(String sql): is used to execute SELECT query. It returns the object of ResultSet.
2) public int executeUpdate(String sql): is used to execute specified query, it may be create, drop, insert, update, delete etc.
3) public boolean execute(String sql): is used to execute queries that may return multiple results.
4) public int[] executeBatch(): is used to execute batch of commands.
 * 
 * */
public class Hello {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String query="";
		int choose = 0;
		int i = 0,loop=0;
		int showResultCount = 0;
		do {
			curdOperations obj = new curdOperations();
			//obj.addUpdateDeleteData(query);
			Scanner myObj = new Scanner(System.in);  
			//initialValue obj1 = new initialValue();
			System.out.println("Choose Menu: "
					+ "1. Add User"
					+ "2. View User"
					+ "3. Update User"
					+ "4. Delete User"
					+ "5. Exit");
			// Create a Scanner object
				choose = myObj.nextInt();  // Read user input
			 
			switch (choose) {
		      case 1:
		        System.out.println("Welcome to Add User");
		        System.out.println("Enter Last Name");
		        String LastName = myObj.next();
		        System.out.println("Enter First Name");
		        String FirstName = myObj.next();
		        System.out.print("Enter Address");
		        String Address = myObj.next();
		        System.out.println("Enter City");
		        String City = myObj.next();   
		        System.out.println("Enter PID");
		        String pid = myObj.next();
		        LinkedList<String> ll = new LinkedList<String>();
		        ll.add(pid);
		        ll.add(LastName);
		        ll.add(FirstName);
		        ll.add(Address);
		        ll.add(City);
//		        System.out.println(ll);
		        query ="INSERT INTO Persons (PersonID, LastName, FirstName,Address,City)VALUES (?, ?,?,?,?)";
		        showResultCount = obj.addUpdateDeleteData(query,ll);
		        System.out.println("Number of Row Inserted "+ showResultCount);
		        break;
		      case 2:
		        System.out.println("View User");
		        query ="select * from  Persons ";
		        ArrayList data = obj.viewData(query);
		        for(int it=0;it<data.size();it++)
		        {
		        	 
		        	data.get(it);
		        	HashMap<String, String> hashmap= (HashMap<String, String>) data.get(it);
		            
//		        	System.out.println(data.get(it));
		        	System.out.println( hashmap.get("Address") +" " + hashmap.get("FirstName") +" " + hashmap.get("id") +" " + hashmap.get("LastName") +" " + hashmap.get("City"));
		        	
		        }
		        break;
		      case 3:
		        System.out.println("Update User");
		        LinkedList<String> ll1 = new LinkedList<String>();
		        query ="UPDATE Persons "
                + "SET FirstName = ? "
                + "WHERE PersonID = ?";
		        System.out.println("Enter First Name");
		        String FirstName_up = myObj.next();
		        System.out.println("Enter ID");
		        String id = myObj.next();
		        ll1.add(FirstName_up);
		        ll1.add(id);
		        
		        showResultCount = obj.addUpdateDeleteData(query,ll1);
		        break;
		      case 4:
		        System.out.println("Delete User");
		        LinkedList<String> ll2 = new LinkedList<String>();
		        System.out.println("Enter Id");
		        String personID_delete = myObj.next();
		        ll2.add(personID_delete);
		        query ="DELETE FROM   Persons "
		               
		                + "WHERE PersonID = ?";
		        showResultCount = obj.addUpdateDeleteData(query,ll2);
		        break;
		      case 5:
		        System.out.println("Exit");
		        loop=1;
		        break;
		      default:
		    	  System.out.println("Pls Correct Choice");
		    	  break;
		    }
		}
		while (loop == 0);
		
	}
	
}
class initialValue{
	String url="jdbc:mysql://localhost:3306/emp?autoReconnect=true&useSSL=false";
	String name="root";
	String pass = "1234";
	//String query ="select * from  Persons where PersonID =101";
	Statement st;
	Connection conn ;
	void initialValue() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url,name,pass);
		st = conn.createStatement();
	}
}
class curdOperations extends initialValue {
//	employee obj=new employee();
	String url="jdbc:mysql://localhost:3306/emp?autoReconnect=true&useSSL=false";
	String name="root";
	String pass = "1234";
	//String query ="select * from  Persons where PersonID =101";
	Statement st;
	Connection conn ;
	ArrayList<HashMap<String,String>> emp = new ArrayList<HashMap<String,String>>(); 
	employee obj ;
	public int addUpdateDeleteData(String query,LinkedList ll) throws Exception {
		//Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url,name,pass);
//		st = conn.createStatement();
		PreparedStatement stmt=conn.prepareStatement(query);  
	    for(int i=0;i<ll.size();i++){  
//	    	System.out.println(i+(String) ll.get(i));
	    	stmt.setString(i+1,(String) ll.get(i));//1 specifies the first parameter in the query  
	    }  
		
		return  stmt.executeUpdate();
	}
	public ArrayList viewData(String query) throws Exception{
		//Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url,name,pass);
		st = conn.createStatement();
		// Let us select all the records and display them.
        ResultSet rs = st.executeQuery(query);		      
      
        // Extract data from result set
        while (rs.next()) {
        	obj=new employee(rs.getString("PersonID"),rs.getString("LastName"),rs.getString("FirstName"),rs.getString("Address"),rs.getString("City")) ;
        	 emp.add(obj.employee_data());
        }
        return emp;
        
	}
	
}
 class employee {
	  private String PersonID; // private = restricted access
	  private String LastName;
	  private String FirstName;
	  private String Address ;
	  private String City;
	  HashMap<String, String> empData = new HashMap<String, String>();
	  public  employee(String PersonID,String LastName,String FirstName,String Address,String City) {
		  this.PersonID = PersonID;
		  this.LastName = LastName;
		  this.FirstName = FirstName;
		  this.Address = Address;
		  this.City = City;
		  
	  }
	  public HashMap  employee_data() {
		  empData.put("id",PersonID);
		  empData.put("LastName",LastName);
		  empData.put("FirstName",FirstName);
		  empData.put("Address",Address);
		  empData.put("City",City);
		  return  empData;

	  }
//	  // Getter
//	  public String getName() {
//	    return name;
//	  }
//	  	// Setter
//		  public void setName(String newName) {
//		    this.name = newName;
//		  }
//
//	  public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public String getPosition() {
//		return position;
//	}
//
//	public void setPosition(String position) {
//		this.position = position;
//	}
//
//	public String getAge() {
//		return Age;
//	}
//
//	public void setAge(String age) {
//		Age = age;
//	}
//
//	public String getSalary() {
//		return Salary;
//	}
//
//	public void setSalary(String salary) {
//		Salary = salary;
//	}

	
}