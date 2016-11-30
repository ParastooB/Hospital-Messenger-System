import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class messenger {

	public static void main(String[] args) throws FileNotFoundException {
		File input = null;
		String batch = "-b";
		int count = 0;
		if (args.length != 2) {
			throw new IllegalArgumentException("Incorrect Number of Arguments");
		} else if (!batch.equals(args[0])) {
			System.out.println("Incorrect Argument");
		} else {
			input = new File(args[1]);
		}

		// Scan file line by line
		Commands m = new Commands();
		Scanner sc = new Scanner(input).useDelimiter("\n");
		System.out.println("  " + count + ":  OK");
		while (sc.hasNextLine()) {
			String command = sc.next();

			String[] tokens = command.split("_"); // split string 1st time by
													// "_"
			System.out.print("\n");

			// add
			if (tokens[0].trim().equals("add")) {
				// split it further to check if its add_user or add_group
				String[] split_1 = tokens[1].split("\\(");
				if (split_1[0].trim().equals("user")) {// add_user(u_id,u_name)
					count++;
					String[] split_2 = split_1[1].split(",");
					int u_id = Integer.parseInt(split_2[0].trim());
					String u_name = split_2[1].split("\\)")[0].trim().replaceAll("^\"|\"$", "");
					System.out.println("->add_user(" + u_id + ",\"" + u_name + "\")");

					if (u_name.equals("")) {
						u_name = " ";
					}
					// System.out.println(u_name);
					m.add_user(u_id, u_name, count);
				} else { // add_group(u_id,u_name)
					count++;
					String[] split_2 = split_1[1].split(",");
					int g_id = Integer.parseInt(split_2[0].trim());
					String g_name = split_2[1].split("\\)")[0].trim().replaceAll("^\"|\"$", "");
					System.out.println("->add_group(" + g_id + ",\"" + g_name + "\")");

					if (g_name.equals("")) {
						g_name = " ";
					}
					// System.out.println(g_id > 0 &&
					// !g_name.substring(0,1).matches("\\s") &&
					// !g_name.substring(0,1).matches("\\d"));
					m.add_group(g_id, g_name, count);

				}
			}

			// register_user(u_id,g_id)
			if (tokens[0].trim().equals("register")) {
				count++;
				int u_id = Integer.parseInt(tokens[1].split("\\(")[1].split(",")[0].trim());
				int g_id = Integer.parseInt(tokens[1].split("\\(")[1].split(",")[1].split("\\)")[0].trim());
				System.out.println("->register_user(" + u_id + "," + g_id + ")");
				m.register_user(u_id, g_id, count);
			}

			// list
			if (tokens[0].trim().equals("list")) {
				if (tokens.length == 2) {
					if (tokens[1].substring(0, 5).equals("users")) { // list_users
						count++;
						System.out.println("->list_users");
						m.list_users(count);
					} else { // list_groups
						count++;
						System.out.println("->list_groups");
						m.list_groups(count);
					}
				} else {
					if (tokens[1].trim().equals("old")) {// list_old_messages(u_id)
						count++;
						int u_id = Integer.parseInt(tokens[2].split("\\(")[1].split("\\)")[0].trim());
						System.out.println("->list_old_messages(" + u_id + ")");
						m.list_old_messages(u_id, count);
					} else {
						count++;
						int u_id = Integer.parseInt(tokens[2].split("\\(")[1].split("\\)")[0].trim());
						System.out.println("->list_new_messages(" + u_id + ")");
						m.list_new_messages(u_id, count);
					}
				}
			}

			// send_message(u_id,g_id,text)
			if (tokens[0].trim().equals("send")) {
				count++;
				int u_id = Integer.parseInt(tokens[1].split("\\(")[1].split(",")[0].trim());
				int g_id = Integer.parseInt(tokens[1].split("\\(")[1].split(",")[1].trim());
				// String text =
				// tokens[1].split("\\(")[1].split(",")[2].split("\\)")[0].trim();
				int beginindex = command.indexOf("\"");
				int endindex = command.indexOf("\")");
				String s = command.substring(beginindex + 1, endindex);
				// System.out.println(s + ":");
				System.out.println("->send_message(" + u_id + "," + g_id + ",\"" + s + "\")");
				m.send_message(u_id, g_id, s, count);
			}

			// read_message(u_id,m_id)
			if (tokens[0].trim().equals("read")) {
				count++;
				int u_id = Integer.parseInt(tokens[1].split("\\(")[1].split(",")[0].trim());
				int m_id = Integer.parseInt(tokens[1].split("\\(")[1].split(",")[1].split("\\)")[0].trim());
				System.out.println("->read_message(" + u_id + "," + m_id + ")");
				m.read_message(u_id, m_id, count);
			}

			// delete_message(u_id,m_id)
			if (tokens[0].trim().equals("delete")) {
				count++;
				int u_id = Integer.parseInt(tokens[1].split("\\(")[1].split(",")[0].trim());
				int m_id = Integer.parseInt(tokens[1].split("\\(")[1].split(",")[1].split("\\)")[0].trim());
				System.out.println("->delete_message(" + u_id + "," + m_id + ")");
				m.delete_message(u_id, m_id, count);
			}

			// set_message_preview(n)
			if (tokens[0].trim().equals("set")) {// list_old_messages(u_id)
				count++;
				int n = Integer.parseInt(tokens[2].split("\\(")[1].split("\\)")[0].trim());
				System.out.println("->set_message_preview(" + n + ")");
				m.set_message_preview(n, count);
			}

		}
		sc.close();

	}
}
