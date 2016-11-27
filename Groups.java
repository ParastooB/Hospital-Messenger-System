import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

public class Groups {
	HashMap<Integer, String> groups = new HashMap<Integer,String>();
	HashMap<String, Integer> groups_by_name = new HashMap<String ,Integer>();
	public Groups(){
		
	}
	
	//returns true if user added 
	boolean add_group(int g_id , String g_name, int count){ 
		if(g_id > 0 && !group_id_exists(g_id) && !g_name.substring(0,1).matches("\\s") && !g_name.substring(0,1).matches("\\d")) {
			System.out.println("  "+count+ ":  OK");
			groups.put(g_id, g_name);
			groups_by_name.put(g_name , g_id);
			return true;
		}
		else if(g_id <= 0){
			System.out.println("  " +count + ":  ERROR ");
			System.out.println("  ID must be a positive integer.");
			return false;
		}
		else if(g_id >0 && group_id_exists(g_id)){
			System.out.println("  " +count + ":  ERROR ");
			System.out.println("  ID already in use.");
			return false;
		}
		else {
			System.out.println("  " +count + ":  ERROR ");
			System.out.println("  Group name must start with a letter.");
			return false;
		
		}
	}
	
	void list_groups(int count){
		if(groups.isEmpty()){
			System.out.println("  " + count+ ":  OK");
			System.out.println("  There are no groups registered in the system yet.");
		}
		else{
			System.out.println("  " + count+ ":  OK");
			list();
			
			}
		}
	
	void list(){
		Map<String , Integer> map = new TreeMap<String , Integer>(groups_by_name);
		Set<Entry<String , Integer>> u_set = map.entrySet();
		Iterator<Entry<String , Integer>> i = u_set.iterator();
		while(i.hasNext()){
			Map.Entry<String , Integer> token = i.next();
			System.out.print("  " + token.getValue() + "->" + token.getKey() +"\n");
	}
	}
	//returns true if id is already in the map
	boolean group_id_exists(int u_id){ 
		if(groups.containsKey(u_id)){
			return true;
		}
		else{
			return false;
		}
	}
	
	void print_groups(){
		Map<Integer, String> map = new TreeMap<Integer, String>(groups);
		Set<Entry<Integer, String>> u_set = map.entrySet();
		Iterator<Entry<Integer, String>> i = u_set.iterator();
		while(i.hasNext()){
			Map.Entry<Integer, String> token = i.next();
			System.out.print("      " + token.getKey() + "->" + token.getValue() +"\n");
		}
	}
	
	String get_group(int id){
		return groups.get(id);
	}
}
