
public class Commands {
	Users u = new Users();
	Groups g = new Groups();
	Registrations r = new Registrations(u, g);
	Messages m = new Messages(r);
	int preview = 15;

	public Commands() {

	}

	void add_user(int u_id, String u_name, int count) {
		if (u.add_user(u_id, u_name, count) == true) {
			print_all();
		} else {

		}

	}

	void add_group(int g_id, String g_name, int count) {
		if (g.add_group(g_id, g_name, count) == true) {
			print_all();
		} else {

		}
	}

	void register_user(int u_id, int g_id, int count) {
		if (r.register_user(u_id, g_id, count) == true) {
			print_all();
		} else {

		}
	}

	void send_message(int u_id, int g_id, String text, int count) {
		if (m.send_message(u_id, g_id, text, count) == true) {
			print_all();
		} else {

		}
	}

	void read_message(int u_id, int m_id, int count) {
		if (m.read_message(u_id, m_id, count) == true) {
			print_all();
		} else {

		}
	}

	void delete_message(int u_id, int m_id, int count) {
		if (m.delete_message(u_id, m_id, count) == true) {
			print_all();
		} else {

		}
	}

	void list_users(int count) {
		//u.list_users(count);
		u.list_users(count);
	}

	void list_groups(int count) {
		g.list_groups(count);
	}

	void list_new_messages(int u_id, int count) {
		m.list_new_messages(u_id, preview, count);
	}

	void list_old_messages(int u_id, int count) {
		m.list_old_messages(u_id, preview, count);
	}

	void set_message_preview(int p, int count) {
		if (p == 0) {
			System.out.println("  " + count + ":  ERROR ");
			System.out.println("  Message length must be greater than zero.");
		} else {
			System.out.println("  " + count + ":  OK");
			preview = p;
			print_all();
		}
	}

	void print_all() {
		System.out.println("  Users:");
		u.print_users();
		System.out.println("  Groups:");
		g.print_groups();
		System.out.println("  Registrations:");
		r.print_registrations();
		System.out.println("  All messages:");
		m.print_all_messages(preview);
		System.out.println("  New messages:");
		m.print_new_messages();
		System.out.println("  Old/read messages:");
		m.print_old_messages();
	}

}
