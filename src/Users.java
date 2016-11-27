
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
public class Users {

	HashMap<Integer, String> users = new HashMap<Integer,String>();
	HashMap<String, Integer> users_by_name = new HashMap<String ,Integer>();
	public Users(){
		
	}
	
	//returns true if user added 
	boolean add_user(int u_id , String u_name, int count){ 
		if(u_id > 0 && !user_id_exists(u_id) &&  !u_name.substring(0,1).matches("\\s") && !u_name.substring(0,1).matches("\\d") ){
			System.out.println("  "+count+ ":  OK");
			users.put(u_id, u_name);
			users_by_name.put(u_name, u_id);
			return true;
		}
		else if(u_id <= 0){
			System.out.println("  " +count + ":  ERROR ");
			System.out.println("  ID must be a positive integer.");
			return false;
		}
		else if(u_id >0 && user_id_exists(u_id)){
			System.out.println("  " +count + ":  ERROR ");
			System.out.println("  ID already in use.");
			return false;
		}
		else {
			System.out.println("  " +count + ":  ERROR ");
			System.out.println("  User name must start with a letter.");
			return false;
		
		}
	}
	
	void list_users(int count){
		if(users.isEmpty()){
			System.out.println("  " + count+ ":  OK");
			System.out.println("  There are no users registered in the system yet.");
		}
		else{
			System.out.println("  " + count+ ":  OK");
			list();
			
		}
	}
	
	void list(){
		Map<String , Integer> map = new TreeMap<String , Integer>(users_by_name);
		Set<Entry<String , Integer>> u_set = map.entrySet();
		Iterator<Entry<String , Integer>> i = u_set.iterator();
		while(i.hasNext()){
			Map.Entry<String , Integer> token = i.next();
			System.out.print("  " + token.getValue() + "->" + token.getKey() +"\n");
	}
	}
	
	//returns true if id is already in the map
	boolean user_id_exists(int u_id){ 
		if(users.containsKey(u_id)){
			return true;
		}
		else{
			return false;
		}
	}
	
	void print_users(){
		Map<Integer, String> map = new TreeMap<Integer, String>(users);
		Set<Entry<Integer, String>> u_set = map.entrySet();
		Iterator<Entry<Integer, String>> i = u_set.iterator();
		while(i.hasNext()){
			Map.Entry<Integer, String> token = (Map.Entry<Integer, String>)i.next();
			System.out.print("      " + token.getKey() + "->" + token.getValue() +"\n");
		}
	}
	
	String get_user_name(int id){
		return users.get(id);
	}
	
}
