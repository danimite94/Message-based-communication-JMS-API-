package JPA;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.JMSRuntimeException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class PRINCIPAL implements MessageListener {

	private static void showGUI() {
		System.out.println("**********************");
		System.out.println("Choose the number that corresponds to what you want to do:");
		System.out.println(" 0) Exit");
		System.out.println(" 1) Register");
		System.out.println(" 2) Login");
		System.out.println("**********************");
	}

	private static void showGUI2() {
		System.out.println("**********************");
		System.out.println("Choose the number that corresponds to what you want to do:");
		System.out.println(" 0) Exit");
		System.out.println(" 1) Vizualize the title of every publication in the system");
		System.out.println(" 2) Vizualize detailed information about a publication");
		System.out.println(" 3) Add a publication");
		System.out.println(" 4) Edit a publication");
		System.out.println(" 5) Remove a publication");
		System.out.println(" 6) Follow a publication");
		System.out.println(" 7) Unfollow a publication");
		System.out.println(" 8) Vizualize all your favorite publications");
		System.out.println("**********************");
	}

	private static ConnectionFactory connectionFactory;
	private static Destination destination;
	private static String u = null;
	private static String r = null;
	private static int l = 0;
	private static Destination aplicacao;
	private static Destination to;
	private static Destination ui;
	private Destination toll;
	private static Destination numero;
	private static Destination nome;
	private static Destination aplico;
	private static ArrayList<Publicationoficial> c = null;
	private static Destination destination2;
	private String x = null;
	private Publicationoficial z = null;
	private List<String> e = null;
	public PRINCIPAL() throws NamingException {

		this.connectionFactory = InitialContext.doLookup("jms/RemoteConnectionFactory");
		this.destination = InitialContext.doLookup("jms/queue/REGISTOS");
		this.ui = InitialContext.doLookup("jms/queue/INST2");
		this.aplicacao = InitialContext.doLookup("jms/queue/APLICACAOS");
		this.aplico = InitialContext.doLookup("jms/queue/APLICACAOS2");
		this.numero = InitialContext.doLookup("jms/queue/PlayQueu");
		this.nome = InitialContext.doLookup("jms/queue/NAMES");
		this.to = InitialContext.doLookup("jms/topic/recebertopicoo");
		this.toll = InitialContext.doLookup("jms/queue/receberqueue");
		this.destination2 = InitialContext.doLookup("jms/queue/publicacaos");
	}

	@Override
	public void onMessage(Message msg) {
		TextMessage textMsg = (TextMessage) msg;
		try {
			String k[] = textMsg.getText().split("&");

			if (k[0].equals("3")) {
				System.out.println("**********************");
				System.out.println("Warning: A new publication named '" + k[1] + "' has been put on the database!");
				System.out.println("**********************");
			}
			if (k[0].equals("5")) {
				System.out.println("**********************");
				System.out.println("Warning: A publication named '" + k[1] + "' has been removed on the database!");
				System.out.println("**********************");
			}
			if (k[0].equals("4")) {
				c = send3(r, "8");
				if (c == null || c.size() <= 0) {
				} else {
					for (int j = 0; j < c.size(); j++) {
						if (c.get(j).gettitle().equals(k[1])) {
							System.out.println("**********************");
							System.out.println("Warning: A publication named '" + k[1] + "' has been edited !");
							System.out.println("**********************");
						}
					}
				}
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public String send(Perfil perfil) throws JMSException {
		try (JMSContext context = connectionFactory.createContext("projecto3", "projecto3pass!");) {
			JMSProducer messageProducer = context.createProducer();
			TextMessage numer = context.createTextMessage();
			numer.setText("1");
			messageProducer.send(toll, numer);
			ObjectMessage objMessage = context.createObjectMessage();
			objMessage.setObject(perfil);
			messageProducer.send(destination, objMessage);
			JMSConsumer cons1 = context.createConsumer(aplicacao);
			u = cons1.receiveBody(String.class);
		} catch (Exception re) {
			re.printStackTrace();
		}
		return u;
	}
	public List<String> recebertit() throws JMSException {

		try (JMSContext context = connectionFactory.createContext("projecto3", "projecto3pass!");) {
			JMSProducer messageProducer = context.createProducer();
			messageProducer.send(toll, "9");
			JMSConsumer cons = context.createConsumer(aplico);
			Object  msg = cons.receiveBody(Object.class);
			@SuppressWarnings("unchecked")
			List<String> b = (List<String>) msg;
			e=b;
		
		} catch (Exception re) {
			re.printStackTrace();
		}
		return e;
	}
	public Publicationoficial sending(String o) throws JMSException,NamingException {

		try (JMSContext context = connectionFactory.createContext("projecto3", "projecto3pass!");) {
			JMSProducer messageProducer = context.createProducer();
			TextMessage numer1 = context.createTextMessage();
			numer1.setText("10");
			messageProducer.send(toll, numer1);
			TextMessage numer = context.createTextMessage();
			numer.setText(o);
			messageProducer.send(ui, numer);
			JMSConsumer cons = context.createConsumer(aplico);
			Object  msg = cons.receiveBody(Object.class);
			@SuppressWarnings("unchecked")
			Publicationoficial b = (Publicationoficial) msg;
			z=b;
		
		} catch (Exception re) {
			re.printStackTrace();
		}
		return z;
	}
	public String sendlogin(Perfil perfil) throws JMSException {

		try (JMSContext context = connectionFactory.createContext("projecto3", "projecto3pass!");) {
			JMSProducer messageProducer = context.createProducer();
			messageProducer.send(toll, "2");
			ObjectMessage objMessage = context.createObjectMessage();
	        objMessage.setObject(perfil);
			messageProducer.send(ui, objMessage);
			JMSConsumer cons = context.createConsumer(aplico);
			x = cons.receiveBody(String.class);
		} catch (Exception re) {
			re.printStackTrace();
		}
		return x;
	}
	
	public String send2(String j, Publicationoficial perfil, String g) throws JMSException {

		try (JMSContext context = connectionFactory.createContext("projecto3", "projecto3pass!");) {
			JMSProducer messageProducer = context.createProducer();
			TextMessage numer = context.createTextMessage();
			numer.setText(g);
			messageProducer.send(toll, numer);
			ObjectMessage objMessage = context.createObjectMessage();
			objMessage.setObject(perfil);
			messageProducer.send(ui, objMessage);
			TextMessage numer1 = context.createTextMessage();
			numer1.setText(j);
			messageProducer.send(nome, numer1);
			JMSConsumer cons1 = context.createConsumer(aplico);
			u = cons1.receiveBody(String.class);
		} catch (Exception re) {
			re.printStackTrace();
		}
		return u;
	}

	public String sendto(String j, Publicationoficial perfil, String g) throws JMSException {

		try (JMSContext context = connectionFactory.createContext("projecto3", "projecto3pass!");) {
			JMSProducer messageProducer = context.createProducer();
			TextMessage numer = context.createTextMessage();
			numer.setText(g);
			messageProducer.send(toll, numer);
			Destination tmp = context.createTemporaryQueue();
			ObjectMessage objMessage = context.createObjectMessage();
			objMessage.setObject(perfil);
			messageProducer.send(destination2, objMessage);
			TextMessage numer1 = context.createTextMessage();
			numer1.setText(j);
			numer1.setJMSReplyTo(tmp);
			messageProducer.send(numero, numer1);
			JMSConsumer cons1 = context.createConsumer(tmp);
			u = cons1.receiveBody(String.class);
		} catch (Exception re) {
			re.printStackTrace();
		}
		return u;
	}

	public String send4(String j, String y, String g) throws JMSException {

		try (JMSContext context = connectionFactory.createContext("projecto3", "projecto3pass!");) {
			JMSProducer messageProducer = context.createProducer();
			TextMessage numer = context.createTextMessage();
			numer.setText(g);
			messageProducer.send(toll, numer);
			TextMessage numerr = context.createTextMessage();
			numerr.setText(y);
			Destination tmp = context.createTemporaryQueue();
			messageProducer.send(destination2, numerr);
			TextMessage numer1 = context.createTextMessage();
			numer1.setText(j);
			numer1.setJMSReplyTo(tmp);
			messageProducer.send(numero, numer1);
			JMSConsumer cons1 = context.createConsumer(tmp);
			u = cons1.receiveBody(String.class);
		} catch (Exception re) {
			re.printStackTrace();
		}
		return u;
	}

	public ArrayList<Publicationoficial> send3(String h, String m) throws JMSException {
		try (JMSContext context = connectionFactory.createContext("projecto3", "projecto3pass!");) {
			JMSProducer messageProducer = context.createProducer();
			TextMessage numer = context.createTextMessage();
			numer.setText(m);
			messageProducer.send(toll, numer);
			TextMessage numer1 = context.createTextMessage();
			numer1.setText(h);
			messageProducer.send(nome, numer1);
			JMSConsumer consumer = context.createConsumer(aplico);
			Object msg1 = consumer.receiveBody(Object.class);
			@SuppressWarnings("unchecked")
			ArrayList<Publicationoficial> d = (ArrayList<Publicationoficial>) msg1;
			c = d;
		} catch (Exception re) {
			re.printStackTrace();
		}
		return c;
	}

	public void init() throws Exception {

	

			int choice = 5;
			Scanner sc = new Scanner(System.in);
			boolean h = false;
			
			while (h == false) {

				showGUI();

				choice = 5;

				boolean m = false;

				while (choice != 1 && choice != 2 && choice != 0) {

					while (m == false) {

						while (!sc.hasNextInt()) {
							System.out.println("Invalid input!");
							showGUI();
							sc.next();
						}
						choice = sc.nextInt();
						sc.nextLine();
						if (choice == 1 || choice == 2 || choice == 0) {
							m = true;
						} else {
							showGUI();
						}
					}
				}
				if (choice == 1) {
					System.out.println("**********************");
					System.out.println("Write user name:");
					String user = sc.next();
					sc.nextLine();
					System.out.println("Write password:");
					System.out.println("**********************");
					String pass = sc.next();
					sc.nextLine();

					Perfil c = new Perfil();
					c.setusername(user);
					c.setpass(pass);

					String x = send(c);

					if (x.equals("1")) {

						System.out.println("You are now registered!");
						r = user;
						h = true;
					}
					if (x.equals("2")) {

						System.out.println("Couldn´t register!");
					}
				}
				if (choice == 2) {
					System.out.println("**********************");
					System.out.println("Write user name:");
					String user = sc.next();
					sc.nextLine();
					System.out.println("Write password:");
					System.out.println("**********************");
					String pass = sc.next();
					sc.nextLine();

					Perfil c = new Perfil();
					c.setusername(user);
					c.setpass(pass);


					String x = sendlogin(c);

					if (x.equals("1")) {

						System.out.println("Login correct!");
						r = user;
						h = true;
					}
					if (x.equals("2")) {

						System.out.println("Login denied!");
					}
				}
				if (choice == 0) {
					System.out.println("Exiting...");
					h = true;
				}
			}

			try (JMSContext context = connectionFactory.createContext("projecto3", "projecto3pass!");) {
				context.setClientID(r);
				JMSConsumer cons = context.createDurableConsumer((Topic) to, "mySubscription");
				cons.setMessageListener(this);
			
			while (choice != 0) {
				showGUI2();
				choice = 11;
				boolean m = false;

				while (choice != 1 && choice != 2 && choice != 3 && choice != 4 && choice != 5 && choice != 6
						&& choice != 7 && choice != 8 && choice != 0) {

					while (m == false) {

						while (!sc.hasNextInt()) {
							System.out.println("Invalid input!");
							showGUI2();
							sc.next();
						}
						choice = sc.nextInt();
						sc.nextLine();
						if (choice == 1 || choice == 2 || choice == 3 || choice == 4 || choice == 5 || choice == 6
								|| choice == 7 || choice == 8 || choice == 0) {
							m = true;
						} else {
							showGUI();
						}
					}
				}

				if (choice == 0) {
					System.out.println("Exiting");
				}
				if (choice == 1) {

					

					List<String> h1 = recebertit();

					if (h1.isEmpty()) {

						System.out.println("No publications in the system");
					}

					else {
						for (int j = 0; j < h1.size(); j++) {

							System.out.println("Publication nº" + (j + 1));
							System.out.println("Title=" + h1.get(j));
							System.out.println();
							System.out.println();
						}
					}
				}
				if (choice == 2) {

					System.out.println("**********************");
					System.out.println("Write title of the publication:");
					System.out.println("**********************");
					String w = sc.nextLine();
					

					Publicationoficial h1 = sending(w);

					if (h1 == null) {

						System.out.println("That publication isn´t in the system");
					}
					else {

						System.out.println("Title=" + h1.gettitle());
						System.out.println("Date=" + h1.getpubdate());
						System.out.println("Authors=" + h1.getauthors());
						System.out.println("**********************");
					}
				}
				if (choice == 3) {
					
					System.out.println("**********************");
					System.out.println("Write title of the publication:");
					String w = sc.nextLine();
					System.out.println("Write date of the publication:");
					String ww = sc.nextLine();
					System.out.println("Write authors of the publication:");
					System.out.println("**********************");
					String www = sc.nextLine();

					Publicationoficial a = new Publicationoficial();

					a.settitle(w);
					a.setauthors(www);
					a.setpubdate(ww);

					

					Publicationoficial h1 = sending(w);

					if (h1 == null) {

						String t = sendto(r, a, "3");

						if (t.equals("1")) {

							System.out.println("Publication has been added to the system!");
						}
						if (t.equals("2")) {
							System.out.println("Admin didn´t allowed the publication to be added to the system!");
						}
					}
					else {

						System.out.println("There's already a publication with that title in the system!");
					}
				}
				if (choice == 4) {

					System.out.println("**********************");
					System.out.println("Write title of the publication:");
					String w = sc.nextLine();
					System.out.println("Write date of the publication:");
					String ww = sc.nextLine();
					System.out.println("Write authors of the publication:");
					System.out.println("**********************");
					String www = sc.nextLine();

					Publicationoficial a = new Publicationoficial();

					a.settitle(w);
					a.setauthors(www);
					a.setpubdate(ww);

					

					Publicationoficial h1 = sending(w);

					if (h1 == null) {

						System.out.println("That publication isn´t in the system");
					}

					else {
						String t = sendto(r, a, "4");

						if (t.equals("1")) {

							System.out.println("Publication has been edited to the system!");

						}
						if (t.equals("2")) {
							System.out.println("Admin didn´t allowed the publication to be edited!");
						}
					}
				}
				if (choice == 5) {

					System.out.println("**********************");
					System.out.println("Write title of the publication:");
					System.out.println("**********************");
					String w = sc.nextLine();

					

					Publicationoficial h1 = sending(w);

					if (h1 == null) {

						System.out.println("That publication isn´t in the system");
					}
					else {
						String t = send4(r, w, "5");

						if (t.equals("1")) {

							System.out.println("Publication has been removed to the system!");

						}
						if (t.equals("2")) {
							System.out.println("Admin didn´t allowed the publication to be removed of the system!");
						}
					}
				}
				if (choice == 6) {

					System.out.println("**********************");
					System.out.println("Write title of the publication:");
					System.out.println("**********************");
					String w = sc.nextLine();

					

					Publicationoficial h1 = sending(w);

					if (h1 == null) {

						System.out.println("That publication isn´t in the system");
					}
					else {

						String t = send2(r, h1, "6");
						if (t.equals("1")) {

							System.out.println("You now follow that publication!");

						}
						if (t.equals("2")) {

							System.out.println("This publication is already on your favorite list!");
						}
					}
				}
				if (choice == 7) {

					System.out.println("**********************");
					System.out.println("Write title of the publication:");
					System.out.println("**********************");
					String w = sc.nextLine();


					Publicationoficial h1 = sending(w);

					if (h1 == null) {

						System.out.println("That publication isn´t in the system");
					}
					else {

						String t = send2(r, h1, "7");

						if (t.equals("1")) {

							System.out.println("You don´t follow that publication anymore!");
						}
						if (t.equals("2")) {

							System.out.println("This publication isn't on your favorite list!");
						}
					}
				}
				if (choice == 8) {
					c = send3(r, "8");

					if (c == null || c.size() <= 0) {

						System.out.println("There isn´t any publication in your favorites");
					}

					else {
						System.out.println("**********************");
						for (int j = 0; j < c.size(); j++) {

							System.out.println("Title=" + c.get(j));

						}
						System.out.println("**********************");
					}
				}
			}
			sc.close();
			context.unsubscribe(r);
		} catch (JMSRuntimeException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws Exception {

		PRINCIPAL y = new PRINCIPAL();
		y.init();
	}

}
