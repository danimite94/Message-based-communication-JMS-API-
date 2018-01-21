package ADMIN;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import JPA.Perfil;
import JPA.Publicationoficial;

public class ADMINPRINCIPAL implements MessageListener {

	private static ConnectionFactory connectionFactory;
	private static List<String> h = null;
	private static List<String> y = null;
	private static Publicationoficial p;
	private static Destination destination;
	private static Destination destination2;
	private static Destination numero;
	private static Destination nome;
	private static String l = null;
	private static Perfil b = null;
	private static Destination to;
	private static Destination toll;
	private static Destination aplicacao;
	private static Destination aplico;
	private static Destination ui;
	private static HashMap<String, ArrayList<Publicationoficial>> cache = new HashMap<String, ArrayList<Publicationoficial>>();
	private static ArrayList<Publicationoficial> arraylist = new ArrayList<Publicationoficial>();
	private static ArrayList<String> lista = new ArrayList<String>();
	private static ArrayList<String> pendentes = new ArrayList<String>();

	public ADMINPRINCIPAL() throws NamingException {

		this.connectionFactory = InitialContext.doLookup("jms/RemoteConnectionFactory");
		this.destination = InitialContext.doLookup("jms/queue/REGISTOS");
		this.aplicacao = InitialContext.doLookup("jms/queue/APLICACAOS");
		this.numero = InitialContext.doLookup("jms/queue/PlayQueu");
		this.nome = InitialContext.doLookup("jms/queue/NAMES");
		this.to = InitialContext.doLookup("jms/topic/recebertopicoo");
		this.aplico = InitialContext.doLookup("jms/queue/APLICACAOS2");
		this.toll = InitialContext.doLookup("jms/queue/receberqueue");
		this.ui = InitialContext.doLookup("jms/queue/INST2");
		this.destination2 = InitialContext.doLookup("jms/queue/publicacaos");
	}
	@Override
	public void onMessage(Message msg) {
		TextMessage textMsg = (TextMessage) msg;
		try {
			if (textMsg.getText().equals("1")) {
				lista.add("registration");
				pendentes.add("1");
			}
			if (textMsg.getText().equals("4")) {
				lista.add("updating pub");
				pendentes.add("4");
			}
			if (textMsg.getText().equals("5")) {
				pendentes.add("5");
				lista.add("remove pub");		
			}
			if (textMsg.getText().equals("3")) {
				lista.add("adding pub");
				pendentes.add("3");
			}
			if (textMsg.getText().equals("2")) {
				objecto1();
			}
			if (textMsg.getText().equals("9")) {
				objecto3();
			}
			if (textMsg.getText().equals("10")) {
				objecto2();
			}
			if (textMsg.getText().equals("6")) {
				follow();
			}
			if (textMsg.getText().equals("7")) {
				unfollow();
			}
			if (textMsg.getText().equals("8")) {
				returnlista();
			}

		} catch (JMSException | NamingException e) {
			e.printStackTrace();
		}
	}
	public Publicationoficial publi(String msg) throws JMSException {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPersistence");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		String queryone = "SELECT e FROM Publicationoficial e WHERE e.title= '" + msg + "'";
		try {
			Publicationoficial result = (Publicationoficial) em.createQuery(queryone).getSingleResult();
			p = result;
			tx.commit();
			em.close();
			emf.close();

		} catch (NoResultException e) {

			try {
				Publicationoficial result2 = null;

				p = result2;

			} catch (JMSRuntimeException i) {
				i.printStackTrace();
			}
		}
		return p;
	}
	public List<String> titulos() throws JMSException {

		try (JMSContext context = connectionFactory.createContext("projecto3", "projecto3pass!");) {

			EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPersistence");
			EntityManager em = emf.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			String queryone = "SELECT s.title FROM Publicationoficial s";
			try {
				@SuppressWarnings("unchecked")
				List<String> resultList = em.createQuery(queryone).getResultList();
				h = resultList;
				tx.commit();
				em.close();
				emf.close();

			} catch (NoResultException e) {
				try {
				List<String> resultList = null;
					h = resultList;
				} catch (JMSRuntimeException i) {
					i.printStackTrace();
				}
			} catch (JMSRuntimeException i) {
				i.printStackTrace();
			}
		}
		return h;
	}
	public List<String> usernamess() {	
		try (JMSContext context = connectionFactory.createContext("projecto3", "projecto3pass!");) {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPersistence");
			EntityManager em = emf.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			String queryone = "SELECT s.username FROM Perfil s";
			try {
				@SuppressWarnings("unchecked")
				List<String> resultList = em.createQuery(queryone).getResultList();
				y=resultList;
				tx.commit();
				em.close();
				emf.close();

			} catch (NoResultException e) {
				try {
					List<String> resultList = null;
					y=resultList;
				} catch (JMSRuntimeException i) {
					i.printStackTrace();
				}

			} catch (JMSRuntimeException i) {
				i.printStackTrace();
			}
		}
		return y;
	}

	public void objecto3() throws JMSException {
		try (JMSContext context = connectionFactory.createContext("projecto3", "projecto3pass!");) {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPersistence");
			EntityManager em = emf.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			String queryone = "SELECT s.title FROM Publicationoficial s";
			try {
				@SuppressWarnings("unchecked")
				List<String> resultList = em.createQuery(queryone).getResultList();
				JMSProducer messageProducer = context.createProducer();
				ObjectMessage objMessage = context.createObjectMessage();
				objMessage.setObject((Serializable) resultList);
				messageProducer.send(aplico, objMessage);

				tx.commit();
				em.close();
				emf.close();

			} catch (NoResultException e) {
				try {
					List<String> resultList = null;
					JMSProducer messageProducer1 = context.createProducer();
					ObjectMessage objMessage = context.createObjectMessage();
					objMessage.setObject((Serializable) resultList);
					messageProducer1.send(aplico, objMessage);

				} catch (JMSRuntimeException i) {
					i.printStackTrace();
				}

			} catch (JMSRuntimeException i) {
				i.printStackTrace();
			}
		}
	}

	public void objecto2() throws JMSException, NamingException {
		try (JMSContext context = connectionFactory.createContext("projecto3", "projecto3pass!");) {
			JMSConsumer consumer = context.createConsumer(ui);
			String msg = consumer.receiveBody(String.class);
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPersistence");
			EntityManager em = emf.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
		
			String queryone = "SELECT e FROM Publicationoficial e WHERE e.title= '" + msg + "'";

			try {
				Publicationoficial result = (Publicationoficial) em.createQuery(queryone).getSingleResult();
				JMSProducer messageProducer = context.createProducer();
				ObjectMessage objMessage = context.createObjectMessage();
				objMessage.setObject((Serializable) result);
				messageProducer.send(aplico, objMessage);

				tx.commit();
				em.close();
				emf.close();

			} catch (NoResultException e) {
				try {
					Publicationoficial result2 = null;
					JMSProducer messageProducer1 = context.createProducer();
					ObjectMessage objMessage = context.createObjectMessage();
					objMessage.setObject((Serializable) result2);
					messageProducer1.send(aplico, objMessage);
				} catch (JMSRuntimeException i) {
					i.printStackTrace();
				}
			} catch (JMSRuntimeException i) {
				i.printStackTrace();
			}
		}
	}
	public void objecto1() throws JMSException, NamingException {
		try (JMSContext context = connectionFactory.createContext("projecto3", "projecto3pass!");) {
			JMSConsumer consumer = context.createConsumer(ui);
			Object msg = consumer.receiveBody(Object.class);
			Perfil a = (Perfil) msg;
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPersistence");
			EntityManager em = emf.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			Perfil result = null;
			String query = "SELECT e FROM Perfil e WHERE e.username= '" + a.getusername() + "'";

			try {
				result = (Perfil) em.createQuery(query).getSingleResult();

				if (result.getpass().equals(a.getpass())) {

					JMSProducer messageProducer = context.createProducer();
					messageProducer.send(aplico, "1");
				}
				else {
					try {
						JMSProducer messageProducer = context.createProducer();
						messageProducer.send(aplico, "2");

					} catch (JMSRuntimeException i) {
						i.printStackTrace();
					}
				}
				tx.commit();
				em.close();
				emf.close();
			} catch (NoResultException e) {
				try {
					JMSProducer messageProducer = context.createProducer();
					messageProducer.send(aplico, "2");

				} catch (JMSRuntimeException i) {
					i.printStackTrace();
				}
			}

		} catch (JMSRuntimeException i) {
			i.printStackTrace();
		}
	}

	public void follow() throws JMSException, NamingException {
		try (JMSContext context = connectionFactory.createContext("projecto3", "projecto3pass!");) {
			JMSConsumer consumer = context.createConsumer(ui);
			Object msg1 = consumer.receiveBody(Object.class);
			Publicationoficial t = (Publicationoficial) msg1;
			String nomedele = rec2();
			try {
			arraylist = cache.get(nomedele);
				
				if (arraylist.contains(t)) {
					JMSProducer messageProducer = context.createProducer();
					messageProducer.send(aplico, "2");
				} else {
					arraylist.add(t);
					cache.put(nomedele, arraylist);
					JMSProducer messageProducer = context.createProducer();
					messageProducer.send(aplico, "1");
				}
			} catch (NullPointerException e) {

				ArrayList<Publicationoficial> l = new ArrayList<Publicationoficial>();
				l.add(t);
				cache.put(nomedele, l);
				JMSProducer messageProducer = context.createProducer();
				messageProducer.send(aplico, "1");
			}
		}
		catch (JMSRuntimeException i) {
			i.printStackTrace();
		}
	}

	public void unfollow() throws JMSException, NamingException {
		try (JMSContext context = connectionFactory.createContext("projecto3", "projecto3pass!");) {
			JMSConsumer consumer = context.createConsumer(ui);
			Object msg1 = consumer.receiveBody(Object.class);
			Publicationoficial t = (Publicationoficial) msg1;

			String nomedele = rec2();
			try {
				arraylist = cache.get(nomedele);
				if (arraylist.contains(t)) {
					arraylist.remove(t);
						cache.put(nomedele, arraylist);
						JMSProducer messageProducer = context.createProducer();
						messageProducer.send(aplico, "1");
			}
				else {
					JMSProducer messageProducer = context.createProducer();
					messageProducer.send(aplico, "2");

				}
			} catch (NullPointerException e) {

				System.out.println("nothing");

				JMSProducer messageProducer = context.createProducer();
				messageProducer.send(aplico, "2");
			}
		} catch (JMSRuntimeException i) {
			i.printStackTrace();
		}
	}
	public void returnlista() throws JMSException, NamingException {

		try (JMSContext context = connectionFactory.createContext("projecto3", "projecto3pass!");) {

			String nomedele = rec2();

			arraylist = cache.get(nomedele);

			JMSProducer messageProducer = context.createProducer();
			ObjectMessage objMessage = context.createObjectMessage();
			objMessage.setObject(arraylist);
			messageProducer.send(aplico, objMessage);

		} catch (JMSRuntimeException i) {
			i.printStackTrace();
		}
	}
	private static void showGUI() {
		System.out.println("**********************");
		System.out.println("Escolha o número que corresponde aquilo que pretende fazer:");
		System.out.println(" 1) List all users");
		System.out.println(" 2) List all pending tasks");
		System.out.println(" 3) Approve or reject a pending task");
		System.out.println(" 4) List all publication titles in the system");
		System.out.println(" 5) Show detailed information regarding a publication.");
		System.out.println("**********************");
	}

	public String rec2() throws JMSException, NamingException {
		try (JMSContext context = connectionFactory.createContext("projecto3", "projecto3pass!");) {
			JMSConsumer consumer = context.createConsumer(nome);
			TextMessage msg = (TextMessage) consumer.receive();
			l = msg.getText();
		} catch (JMSRuntimeException e) {
			e.printStackTrace();
		}
		return l;
	}
	public void unit() throws NamingException, JMSException{
		Scanner sc = new Scanner(System.in);
		boolean j = false;	
			try (JMSContext context = connectionFactory.createContext("projecto3", "projecto3pass!");) {
				JMSConsumer mc = context.createConsumer(toll);
				mc.setMessageListener(this);

		while(j==false){
			showGUI();
			
			int choice = 11;
			boolean m = false;

			while (choice != 1 && choice != 2 && choice != 3 && choice != 4 && choice != 5 ) {
				while (m == false) {
					while (!sc.hasNextInt()) {
						System.out.println("Invalid input!");
						showGUI();
						sc.next();
					}
					choice = sc.nextInt();
					sc.nextLine();
					if (choice == 1 || choice == 2 || choice == 3 || choice == 4 || choice == 5) {
						m = true;
					} else {
						showGUI();
					}
				}
			}
			if(choice==1){
				
				List<String> lista1 = usernamess();
				System.out.println("**********************");
				System.out.println("Usernames:");
				System.out.println("**********************");			
				if (lista1.size()>0){
					
				for(int g=0;g<lista1.size();g++){
					
					System.out.println(lista1.get(g));
				}
			
			}		
				else{
					
					System.out.println("None!");				
				}
			}
			if(choice==2){
				System.out.println("**********************");
				System.out.println("Pending tasks:");
				System.out.println("**********************");
				
				if (lista.size()>0){
					
				for(int g=0;g<lista.size();g++){
					
					System.out.println("nº"+(g+1)+"-"+lista.get(g));
				}
			}			
				else{
					System.out.println("Nothing!");			
				}
			}
			if(choice==3){
				
				try{
			String y = pendentes.get(0);
			
			pendentes.remove(0);
			lista.remove(0);
			if (y.equals("1")) {

				try (JMSContext context1 = connectionFactory.createContext("projecto3", "projecto3pass!");) {

					JMSConsumer consumer = context1.createConsumer(destination);
					Object msg1 = consumer.receiveBody(Object.class);
					b = (Perfil) msg1;
					Perfil result = null;

					EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPersistence");
					EntityManager em = emf.createEntityManager();
					EntityTransaction tx = em.getTransaction();
					tx.begin();
					String query = "SELECT e FROM Perfil e WHERE e.username= '" + b.getusername() + "'";
					try {

						result = (Perfil) em.createQuery(query).getSingleResult();

						tx.commit();
						em.close();
						emf.close();
						if (result != null) {
							System.out.println("**********************");
							System.out.println("The user "+result.getusername()+" exists already!");
							System.out.println("Do you still accept it?");
							System.out.println("Write number 1 for yes or number 2 for no!");				
							System.out.println("**********************");

							int choice1 = 4;
							boolean m1 = false;

							while (choice1 != 1 && choice1 != 2) {
								while (m1 == false) {
									while (!sc.hasNextInt()) {
										System.out.println("Invalid input!");
										System.out.println("**********************");
										System.out.println("The user "+result.getusername()+" exists already!");
										System.out.println("Do you still accept it?");
										System.out.println("Write number 1 for yes or number 2 for no!");				
										System.out.println("**********************");
										sc.next();
									}
									choice1 = sc.nextInt();
									sc.nextLine();
									if (choice1 == 1 || choice1 == 2) {
										m1 = true;
									}
								}
							}
							if (choice1 == 1) {

								em.persist(b);
								tx.commit();
								em.close();
								emf.close();
								JMSProducer messageProducer = context1.createProducer();
								messageProducer.send(aplicacao, "1");

							} else {

								JMSProducer messageProducer = context1.createProducer();
								messageProducer.send(aplicacao, "2");
							}
						}
					} catch (NoResultException e) {
						System.out.println("**********************");
						System.out.println("The user "+b.getusername()+" dosen´t exists already!");
						System.out.println("Do you accept it?");
						System.out.println("Write number 1 for yes or number 2 for no!");				
						System.out.println("**********************");
						int choice1 = 4;
						boolean m1 = false;

						while (choice1 != 1 && choice1 != 2) {
							while (m1 == false) {
								while (!sc.hasNextInt()) {
									System.out.println("Invalid input!");
									System.out.println("**********************");
									System.out.println("The user "+result.getusername()+" dosen´t exists already!");
									System.out.println("Do you accept it?");
									System.out.println("Write number 1 for yes or number 2 for no!");				
									System.out.println("**********************");
									sc.next();
								}
								choice1 = sc.nextInt();
								sc.nextLine();
								if (choice1 == 1 || choice1 == 2) {
									m1 = true;
								}
							}
						}
						if (choice1 == 1) {
							em.persist(b);
							tx.commit();
							em.close();
							emf.close();
							JMSProducer messageProducer = context1.createProducer();
							messageProducer.send(aplicacao, "1");
						} else {
							JMSProducer messageProducer = context1.createProducer();
							messageProducer.send(aplicacao, "2");
						}
					}

				} catch (JMSRuntimeException i) {
					i.printStackTrace();
				}
				}
			
			if (y.equals("3")) {

				Publicationoficial t = null;
				
				try (JMSContext context1 = connectionFactory.createContext("projecto3", "projecto3pass!");) {

					JMSConsumer consumer = context1.createConsumer(destination2);
					Object msg1 = consumer.receiveBody(Object.class);
					t = (Publicationoficial) msg1;		
				}
				catch (JMSRuntimeException i) {
					i.printStackTrace();
				}
					try (JMSContext context11 = connectionFactory.createContext("projecto3", "projecto3pass!");) {
						JMSConsumer consumer1 = context11.createConsumer(numero);
						TextMessage msg = (TextMessage) consumer1.receive();
						String nomedele = msg.getText();
					System.out.println("**********************");
					System.out.println("Do you accept that user " + nomedele + " adds the following publication:");
					System.out.println("Publication Title = " + t.gettitle());
					System.out.println("Publication date = " + t.getpubdate());
					System.out.println("Publication authors = " + t.getauthors());
					System.out.println("Write number 1 for yes or number 2 for no!");				
					System.out.println("**********************");
					int choice1 = 4;
					boolean m1 = false;
					while (choice1 != 1 && choice1 != 2) {
						while (m1 == false) {
							while (!sc.hasNextInt()) {
								System.out.println("Invalid input!");
								System.out.println("**********************");
								System.out.println("Do you accept that user " + nomedele + " adds the following publication:");
								System.out.println("Publication Title = " + t.gettitle());
								System.out.println("Publication date = " + t.getpubdate());
								System.out.println("Publication authors = " + t.getauthors());
								System.out.println("Write number 1 for yes or number 2 for no!");				
								System.out.println("**********************");
								sc.next();
							}
							choice1 = sc.nextInt();
							sc.nextLine();
							if (choice1 == 1 || choice1 == 2) {
								m1 = true;
							}
						}
					}
					if (choice1 == 1) {
						EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPersistence");
						EntityManager em = emf.createEntityManager();
						EntityTransaction tx = em.getTransaction();
						tx.begin();
						em.persist(t);
						tx.commit();
						em.close();
						emf.close();
						JMSProducer messageProducer = context11.createProducer();
						messageProducer.send(msg.getJMSReplyTo(),"1");
						TextMessage numerrr = context11.createTextMessage();
						numerrr.setText("3&"+t.gettitle());	
						messageProducer.send(to, numerrr);
					} else {

						JMSProducer messageProducer = context11.createProducer();
						messageProducer.send(msg.getJMSReplyTo(),"2");			
					}			
					} catch (JMSRuntimeException e) {
						e.printStackTrace();
					}
			}
			if (y.equals("4")) {		
				Publicationoficial l =null;
				try (JMSContext context1 = connectionFactory.createContext("projecto3", "projecto3pass!");) {

					JMSConsumer consumer = context1.createConsumer(destination2);
					Object msg1 = consumer.receiveBody(Object.class);
					l = (Publicationoficial) msg1;			
				}
				catch (JMSRuntimeException i) {
					i.printStackTrace();
				}
				try (JMSContext context1 = connectionFactory.createContext("projecto3", "projecto3pass!");) {
					JMSConsumer consumer = context1.createConsumer(numero);
					TextMessage msg = (TextMessage) consumer.receive();
					String nomedele = msg.getText();
					System.out.println("**********************");
					System.out.println("Do you accept that user " + nomedele + " edits the publication named '"
							+ l.gettitle() + "' ?");
					System.out.println("Write number 1 for yes or number 2 for no!");				
					System.out.println("**********************");
					int choice1 = 4;
					boolean m1 = false;
					while (choice1 != 1 && choice1 != 2) {
						while (m1 == false) {
						while (!sc.hasNextInt()) {
								System.out.println("Invalid input!");
								System.out.println("**********************");
								System.out.println("Do you accept that user " + nomedele + " edit the publication named '"
										+ l.gettitle() + "' ?");
								System.out.println("Write number 1 for yes or number 2 for no!");				
								System.out.println("**********************");
								sc.next();
							}
							choice1 = sc.nextInt();
							sc.nextLine();
							if (choice1 == 1 || choice1 == 2) {
								m1 = true;
							}
						}
					}
					if (choice1 == 1) {
						EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPersistence");
						EntityManager em = emf.createEntityManager();
						EntityTransaction tx = em.getTransaction();
						String query = "SELECT e FROM Publicationoficial e WHERE e.title= '" + l.gettitle() + "'";
						Publicationoficial f = (Publicationoficial) em.createQuery(query).getSingleResult();
						Publicationoficial a = em.find(Publicationoficial.class, f.getId());

						tx.begin();
						em.remove(a);
						em.persist(l);
						tx.commit();
						em.close();
						emf.close();
						JMSProducer messageProducer = context1.createProducer();
						messageProducer.send(msg.getJMSReplyTo(),"1");
						TextMessage numerrr = context1.createTextMessage();
						numerrr.setText("4&"+a.gettitle());			
						messageProducer.send(to, numerrr);

					} else {

						JMSProducer messageProducer = context1.createProducer();
						messageProducer.send(msg.getJMSReplyTo(),"2");
					}
					
				} catch (JMSRuntimeException e) {
					e.printStackTrace();
				}	
			}
			if (y.equals("5")) {
				try (JMSContext context1 = connectionFactory.createContext("projecto3", "projecto3pass!");) {
					JMSConsumer consumer = context1.createConsumer(destination2);
					TextMessage msg1 = (TextMessage) consumer.receive();		
					try {
						JMSConsumer consumer1 = context1.createConsumer(numero);
						TextMessage msg = (TextMessage) consumer1.receive();
						String nomedele = msg.getText();					
						System.out.println("**********************");
						System.out.println("Do you accept that user " + nomedele + " removes the publication named '"
								+ msg1.getText() + "' ?");
						System.out.println("Write number 1 for yes or number 2 for no!");				
						System.out.println("**********************");

					int choice1 = 4;
					boolean m1 = false;
					while (choice1 != 1 && choice1 != 2) {
						while (m1 == false) {
							while (!sc.hasNextInt()) {
								System.out.println("Invalid input!");
								System.out.println("**********************");
								System.out.println("Do you accept that user " + nomedele + " removes the publication named '"
										+ msg1.getText() + "' ?");
								System.out.println("Write number 1 for yes or number 2 for no!");				
								System.out.println("**********************");
								sc.next();
							}
							choice1 = sc.nextInt();
							sc.nextLine();
							if (choice1 == 1 || choice1 == 2) {
								m1 = true;
							}
						}
					}
					if (choice1 == 1) {
						EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPersistence");
						EntityManager em = emf.createEntityManager();
						EntityTransaction tx = em.getTransaction();
						String query = "SELECT e FROM Publicationoficial e WHERE e.title= '" + msg1.getText() + "'";
						Publicationoficial f = (Publicationoficial) em.createQuery(query).getSingleResult();
						Publicationoficial a = em.find(Publicationoficial.class, f.getId());
						tx.begin();

						em.remove(a);
						tx.commit();
						em.close();
						emf.close();
						JMSProducer messageProducer = context1.createProducer();
						messageProducer.send(msg.getJMSReplyTo(),"1");
						TextMessage numerrr = context1.createTextMessage();
						numerrr.setText("5&"+a.gettitle());			
						messageProducer.send(to, numerrr);
						
					for(int m11=0;m11<cache.size();m11++){				
							for (String key: cache.keySet()) {
								@SuppressWarnings("unused")
								ArrayList<Publicationoficial> arr = cache.get(key);														
							try{
								if(arr.contains(f)){
									
									arr.remove(f);
									cache.put(key, arr);
								}							
								} catch (NullPointerException e) {
								}
							}			
						}

					} else {

						JMSProducer messageProducer = context1.createProducer();
						messageProducer.send(msg.getJMSReplyTo(),"2");
					}

					} catch (JMSRuntimeException e) {
						e.printStackTrace();
					}
				} catch (JMSRuntimeException e) {
					e.printStackTrace();
				}
			}		
				}			
				 catch (IndexOutOfBoundsException e) {
					System.out.println("None!");
				}		
			}
			if(choice==4){				
				List<String> h1 = titulos();			
				if (h1.isEmpty()) {

					System.out.println("There aren´t publications in the system");
				}
				else {
					for (int j1 = 0; j1 < h1.size(); j1++) {

						System.out.println("Publication nº" + (j1 + 1));
						System.out.println("Title=" + h1.get(j1));
						System.out.println();
						System.out.println();

					}
				}
			}
			if(choice==5){			
				System.out.println("**********************");
				System.out.println("Write title of the publication:");
				String w = sc.nextLine();
				Publicationoficial h1 = publi(w);			
				if (h1 == null) {
					System.out.println("That publication isn´t in the database");
				}

				else {
					System.out.println();
					System.out.println("Publication Title = " + h1.gettitle());
					System.out.println("Publication date = " + h1.getpubdate());
					System.out.println("Publication authors = " + h1.getauthors());
					System.out.println();
					System.out.println();
				}		
			}	
		}		
	}	
	 catch (JMSRuntimeException e) {
		e.printStackTrace();
	}
	}

	public static void main(String[] args)
			throws NamingException, JMSException, IOException, ParserConfigurationException, SAXException {

		ADMINPRINCIPAL n = new ADMINPRINCIPAL();
		n.unit();
	}
}
