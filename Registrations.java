import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.Collections;
public class Registrations {

	HashMap<Integer, ArrayList<Integer>> reg = new HashMap<Integer, ArrayList<Integer>>();
	HashMap<Integer, ArrayList<Integer>> group = new HashMap<Integer, ArrayList<Integer>>();
	Groups g;
	Users u;
	
	public Registrations(Users u , Groups g){
		this.g = g;
		this.u = u;
	}
	
	boolean register_user(int u_id, int g_id , int count ){
		if(u_id <= 0 || g_id<=0){
			System.out.println("  " +count + ":  ERROR ");
			System.out.println("  ID must be a positive integer.");
			return false;
		}
		else if(u_id>0 && g_id > 0 && !u.user_id_exists(u_id)){
			System.out.println("  " +count + ":  ERROR ");
			System.out.println("  User with this ID does not exist.");
			return false;
		}
		else if(u_id > 0 && g_id > 0 && u.user_id_exists(u_id) && !g.group_id_exists(g_id)){
			System.out.println("  " +count + ":  ERROR ");
			System.out.println("  Group with this ID does not exist.");
			return  false;
		}
		else if(u_id > 0 && g_id > 0 && u.user_id_exists(u_id) && g.group_id_exists(g_id) && registration_exists(u_id,g_id)){
			System.out.println("  " +count + ":  ERROR ");
			System.out.println("  This registration already exists.");
			return false;
		}
		else{
		System.out.println("  "+count+ ":  OK");
		//two cases,u_id is already in the list or otherwise
			if(reg.containsKey(u_id)){ //u_id is already in some group
				ArrayList<Integer> a = reg.get(u_id);
				a.add(g_id);
				Collections.sort(a);
				reg.put(u_id, a);
				add_people_in_group(u_id , g_id);
				return true;
			}
			else{ //first timers
				ArrayList<Integer> a = new ArrayList<Integer>();
				a.add(g_id);
				reg.put(u_id, a);
				add_people_in_group(u_id,g_id);
				return true;
			}
		}
	}
	
	void add_people_in_group(int u_id , int g_id){
		if(group.containsKey(g_id)){
			ArrayList<Integer> a = group.get(g_id);
			a.add(u_id);
			Collections.sort(a);
			group.put(g_id, a);
		}
		else{
			ArrayList<Integer> a = new ArrayList<Integer>();
			a.add(u_id);
			group.put(g_id ,  a);
		}
	}
	
	//find people in group other than u_id
	ArrayList<Integer> find_people_in_group(int u_id , int g_id){ 
		if(group.containsKey(g_id)){
			ArrayList<Integer> a = group.get(g_id);
			ArrayList<Integer> templist = new ArrayList<Integer>();
			templist = extracted(a);
			for(int i = 0; i<templist.size() ; i++){
				if(templist.get(i) == u_id){
					templist.remove(i);
				}
			}
			Collections.sort(templist);
			return templist;
			
		}
		else{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private ArrayList<Integer> extracted(ArrayList<Integer> a) {
		return (ArrayList<Integer>) a.clone();
	}
	//return true of registration exist
	boolean registration_exists(int u_id , int g_id){
		if(reg.containsKey(u_id)){
			ArrayList<Integer> a = reg.get(u_id);
			if(a.contains(g_id)){
			return true;
			}
			else{
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	void print_registrations(){
		Map<Integer, ArrayList<Integer>> map = new TreeMap<Integer, ArrayList<Integer>>(reg);
		Set<Entry<Integer, ArrayList<Integer>>> u_set = map.entrySet();
		Iterator<Entry<Integer, ArrayList<Integer>>> i = u_set.iterator();
		while(i.hasNext()){
			Map.Entry<Integer, ArrayList<Integer>> token = i.next();
			int key = token.getKey();
			ArrayList<Integer> list = token.getValue();
			System.out.print("      [" + key + ", " + u.get_user_name(key) +"]->{");
			
			for(int index=0 ; index<list.size();index++){
				System.out.print(list.get(index) +"->" + g.get_group(list.get(index)));
				if(index != list.size()-1){
					System.out.print(", ");
				}
			}
			System.out.print("}\n");
			//System.out.print("      " + token.getKey() + "->" + token.getValue() +"\n");
		}
	}
	
	Groups get_group(){
		return g;
	}
	
	Users get_user(){
		return u;
	}
}
