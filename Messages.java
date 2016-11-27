import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

public class Messages {
	
	HashMap<Integer,String> allms = new HashMap<Integer , String>();
	HashMap<Integer, ArrayList<Integer>> newms = new HashMap<Integer, ArrayList<Integer>>();
	HashMap<Integer, ArrayList<Integer>> oldms = new HashMap<Integer, ArrayList<Integer>>();
	Registrations r;
	Users u;
	Groups g;
	int m_id = 1;
	public Messages(Registrations R){
		this.r = R;
		this.u = R.get_user();
		this.g= R.get_group();
	}
	
	boolean send_message(int u_id , int g_id , String text,int count){ //change newms and allms
		if(u_id <=0 || g_id <= 0){
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
		else if(u_id > 0 && g_id > 0 && u.user_id_exists(u_id) && g.group_id_exists(g_id) && text.isEmpty()){
			System.out.println("  " +count + ":  ERROR ");
			System.out.println("  A message may not be an empty string.");
			return  false;
		}
		else if(u_id > 0 && g_id > 0 && u.user_id_exists(u_id) && g.group_id_exists(g_id) && !text.isEmpty() && !r.registration_exists(u_id, g_id)){
			System.out.println("  " +count + ":  ERROR ");
			System.out.println("  User not authorized to send messages to the specified group.");
			return false;
		}
		else{
			System.out.println("  " +count + ":  OK");
			allms.put(m_id, text);
			distribute_messages(u_id , g_id , m_id);
			m_id++;
			return true;
		}
	}
	
	boolean read_message(int u_id , int m_id , int count){
		if(u_id <=0 || m_id <= 0){
			System.out.println("  " +count + ":  ERROR ");
			System.out.println("  ID must be a positive integer.");
			return false;
		}
		else if(u_id>0 && m_id > 0 && !u.user_id_exists(u_id)){
			System.out.println("  " +count + ":  ERROR ");
			System.out.println("  User with this ID does not exist.");
			return false;
		}
		else if(u_id > 0 && m_id > 0 && u.user_id_exists(u_id) && !message_exists(m_id)){
			System.out.println("  " +count + ":  ERROR ");
			System.out.println("  Message with this ID does not exist.");
			return  false;
		}
		
		else if(u_id > 0 && m_id > 0 && u.user_id_exists(u_id) && message_exists(m_id) && !access_message(u_id,m_id)){
			System.out.println("  " +count + ":  ERROR ");
			System.out.println("  User not authorized to access this message.");
			return false;
		}
		else if(u_id > 0 && m_id > 0 && u.user_id_exists(u_id) && message_exists(m_id) && access_message(u_id,m_id) && message_already_read(u_id,m_id) ){
			System.out.println("  " +count + ":  ERROR ");
			System.out.println("  Message has already been read. See `list_old_messages'.");
			return false;
		}
		else{//change oldms and newms
			System.out.println("  " +count + ":  OK");
			System.out.println("  Message for user [" + u_id + ", " +u.get_user_name(u_id) + "]: [" + m_id + ", \"" + get_message(m_id) + "\"]");
			edit_oldms(u_id , m_id);
			edit_newms(u_id , m_id);
			return true;
		}
	}
	
	
	void edit_oldms(int u_id , int m_id){
		if(oldms.containsKey(u_id)){
			ArrayList<Integer> list = oldms.get(u_id);
			list.add(m_id);
			Collections.sort(list);
			oldms.put(u_id, list);
		}
		else{//first timers
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(m_id);
			oldms.put(u_id, list);
		}
	}
	
	void edit_newms(int u_id , int m_id){ //remove id from list
		if(newms.containsKey(u_id)){
			ArrayList<Integer> list = newms.get(u_id);
			for(int i = 0; i<list.size() ; i++){
				if(list.get(i) == m_id){
					list.remove(i);
				}
			}
			Collections.sort(list);
			newms.put(u_id, list);
		}
	}
	boolean message_exists(int m_id){ //returns true if message with m_id exists
		if(allms.containsKey(m_id)){
			return true;
		}
		else{
			return false;
		}
	}
	
	void distribute_messages(int u_id , int g_id , int m_id ){
		ArrayList<Integer> users = r.find_people_in_group(u_id, g_id);
		for(int i=0; i< users.size() ; i++){
			if(newms.containsKey(users.get(i))){
				ArrayList<Integer> temp = newms.get(users.get(i));
				temp.add(m_id);
				Collections.sort(temp);
				newms.put(users.get(i), temp);
			}
			else{//first timers
				ArrayList<Integer> temp = new ArrayList<Integer>();
				temp.add(m_id);
				newms.put(users.get(i), temp);
			}
		}
	}
	
	boolean delete_message(int u_id , int m_id , int count){
		if(u_id <=0 || m_id <= 0){
			System.out.println("  " +count + ":  ERROR ");
			System.out.println("  ID must be a positive integer.");
			return false;
		}
		else if(u_id>0 && m_id > 0 && !u.user_id_exists(u_id)){
			System.out.println("  " +count + ":  ERROR ");
			System.out.println("  User with this ID does not exist.");
			return false;
		}
		else if(u_id > 0 && m_id > 0 && u.user_id_exists(u_id) && !message_exists(m_id)){
			System.out.println("  " +count + ":  ERROR ");
			System.out.println("  Message with this ID does not exist.");
			return  false;
		}
		else if(u_id > 0 && m_id > 0 && u.user_id_exists(u_id) && message_exists(m_id) && !message_already_read(u_id , m_id)){
			System.out.println("  " +count + ":  ERROR ");
			System.out.println("  Message with this ID not found in old/read messages.");
			return false;
		}
		else{//change oldms and newms
			System.out.println("  " +count + ":  OK");
			delete_oldms(u_id , m_id);
			return true;
		}
	}
	
	void delete_oldms(int u_id , int m_id){ //remove id from list
		if(oldms.containsKey(u_id)){
			ArrayList<Integer> list = oldms.get(u_id);
			for(int i = 0; i<list.size() ; i++){
				if(list.get(i) == m_id){
					list.remove(i);
				}
			}
			Collections.sort(list);
			oldms.put(u_id, list);
		}
	}
	
	void list_old_messages(int u_id , int preview , int count){
		if(u_id <=0 ){
			System.out.println("  " +count + ":  ERROR ");
			System.out.println("  ID must be a positive integer.");
		}
		else if(u_id>0 && !u.user_id_exists(u_id)){
			System.out.println("  " +count + ":  ERROR ");
			System.out.println("  User with this ID does not exist.");
		}
		else if(u_id>0 && u.user_id_exists(u_id) && !oldms_messages(u_id)){
			System.out.println("  " +count + ":  OK");
			System.out.println("  There are no old messages for this user.");
		}
		else{
			System.out.println("  " +count + ":  OK");
			System.out.println("  Old/read messages for user ["+ u_id +", " + u.get_user_name(u_id) + "]:");
			ArrayList<Integer> list = oldms.get(u_id);	
			
			for(int i = 0; i<list.size() ; i++){
				if(allms.get(list.get(i)).length() < preview) {
					System.out.println("      " + list.get(i) + "->"  + allms.get(list.get(i)).replaceAll("\\p{C}", ""));
					//System.out.println("      " + list.get(i) + "->"  + allms.get(list.get(i)));
				}
				else{
					System.out.println("      " + list.get(i) + "->"  + allms.get(list.get(i)).substring(0 , preview).replaceAll("\\p{C}", "") +" ...");
					//System.out.println("      " + list.get(i) + "->"  + allms.get(list.get(i)).substring(0 , preview) +" ...");
				}
			}
			
		}
	}
	
	void list_new_messages(int u_id , int preview , int count){
		list_new_messages(u_id , preview , count ,newms );
	}
	
	void list_new_messages(int u_id , int preview , int count ,HashMap<Integer , ArrayList<Integer>> map ){
		if(u_id <=0 ){
			System.out.println("  " +count + ":  ERROR ");
			System.out.println("  ID must be a positive integer.");
		}
		else if(u_id>0 && !u.user_id_exists(u_id)){
			System.out.println("  " +count + ":  ERROR ");
			System.out.println("  User with this ID does not exist.");
		}
		else if(u_id>0 && u.user_id_exists(u_id) && !new_messages(u_id)){
			System.out.println("  " +count + ":  OK");
			System.out.println("  There are no new messages for this user.");
		}
		else{
			System.out.println("  " +count + ":  OK");
			System.out.println("  New/unread messages for user ["+ u_id +", " + u.get_user_name(u_id) + "]:");
			ArrayList<Integer> list = map.get(u_id);
			for(int i = 0; i<list.size() ; i++){
				if(allms.get(list.get(i)).length() < preview) {
					System.out.println("      " + list.get(i) + "->"  + allms.get(list.get(i)).replaceAll("\\p{C}", ""));
					//System.out.println("      " + list.get(i) + "->"  + allms.get(list.get(i)));
				}
				else{
					System.out.println("      " + list.get(i) + "->"  + allms.get(list.get(i)).substring(0 , preview).replaceAll("\\p{C}", "") +" ...");
					//System.out.println("      " + list.get(i) + "->"  + allms.get(list.get(i)).substring(0 , preview) +" ...");
				}
			}
			
		}
	}
	void print_all_messages(int preview){ //allms
		Map<Integer, String> map = new TreeMap<Integer, String>(allms);
		Set<Entry<Integer, String>> u_set = map.entrySet();
		Iterator<Entry<Integer, String>> i = u_set.iterator();
		while(i.hasNext()){
			Map.Entry<Integer, String> token = i.next();
			if(token.getValue().length() < preview){
			System.out.print("      " + token.getKey() + "->" + token.getValue().replaceAll("[\\p{Cc}\\p{Cf}\\p{Co}\\p{Cn}]", "")  +"\n");
			//System.out.print("      " + token.getKey() + "->" + token.getValue() +"\n");
			}
			else{
				System.out.print("      " + token.getKey() + "->" + token.getValue().substring(0,preview).replaceAll("[\\p{Cc}\\p{Cf}\\p{Co}\\p{Cn}]", "")  +" ...\n");
				//System.out.print("      " + token.getKey() + "->" + token.getValue().substring(0,preview)  +" ...\n");
			}
		}
	}
	
	void print_new_messages(){ //newms
		print_new_or_old_messages(newms);
	}
	
	void print_old_messages(){
		print_new_or_old_messages(oldms);
	}
	
	void print_new_or_old_messages(HashMap<Integer, ArrayList<Integer>> target){
		Map<Integer, ArrayList<Integer>> map = new TreeMap<Integer, ArrayList<Integer>>(target);
		Set<Entry<Integer, ArrayList<Integer>>> u_set = map.entrySet();
		Iterator<Entry<Integer, ArrayList<Integer>>> i = u_set.iterator();
		while(i.hasNext()){
			Map.Entry<Integer, ArrayList<Integer>> token = i.next();
			int key = token.getKey();
			ArrayList<Integer> list = token.getValue();
			if(!list.isEmpty()){ 
			System.out.print("      [" + key + ", " + u.get_user_name(key) +"]->{");
			
			for(int index=0 ; index<list.size();index++){
				System.out.print(list.get(index));
				if(index != list.size()-1){
					System.out.print(", ");
				}
			}
			System.out.print("}\n");
			}
			//System.out.print("      " + token.getKey() + "->" + token.getValue() +"\n");
		}
	}
	
	//returns true if new messages exists for user
	boolean new_messages(int u_id){//newms
		if(newms.containsKey(u_id)){
			return true;
		}else{
			return false;
		}
	}
	
	boolean oldms_messages(int u_id){//newms
		if(oldms.containsKey(u_id)){
			return true;
		}else{
			return false;
		}
	}
	//message access - returns true if user have access to the message
	boolean access_message(int u_id , int m_id){
		if(newms.containsKey(u_id)){
			if(newms.get(u_id).contains(m_id)){
				return true;
			}
			else if(oldms.containsKey(u_id)){
				if(oldms.get(u_id).contains(m_id)){
					return true;
				}
				else{
					return false;
				}
			}
			else{
				return false;
			}
		}
		else if(!newms.containsKey(u_id) && oldms.containsKey(u_id)){
			if(oldms.get(u_id).contains(m_id)){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}
	
	//returns true if message is already read 
	boolean message_already_read(int u_id , int m_id){
		if(oldms.containsKey(u_id)){
			if(oldms.get(u_id).contains(m_id)){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}
	
	String get_message(int m_id){
		return allms.get(m_id);
	}
	void check_message_list(HashMap<Integer , ArrayList<Integer>> map , int u_id){
		if(map.containsKey(u_id)){
			if(map.get(u_id).isEmpty()){ //empty messages
				map.remove(u_id);
			}
		}
	}
}
