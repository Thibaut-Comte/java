package inter;

import java.awt.EventQueue;

//Import pour bdd
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.mysql.jdbc.Driver;

import classes.Equipe;
import classes.EquipeBasket;
import classes.EquipeFoot;
import classes.EquipeRugby;

import javax.swing.JComboBox;
import javax.management.Query;
import javax.management.QueryEval;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class panneauControle {

	private JFrame frame;
	private ArrayList<Equipe> equipes = new ArrayList<Equipe>();	
	private Connection con;
	private Statement stmt;
	private ResultSet rs1;
	private ResultSet rs2;
	ArrayList<String> sports = new ArrayList<String>();



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					panneauControle window = new panneauControle();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public panneauControle() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		//Driver
		Class c;
		try {
			c = Class.forName("com.mysql.jdbc.Driver");
			Driver pilote = (Driver)c.newInstance();
			//Enregistrement du pilote auprès de driver manager
			DriverManager.registerDriver(pilote);
		} catch (ClassNotFoundException e) {
			//Gestion de l'erreur
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Connexion
		try {
			//première méthode
			// Protocole de connexion
			String protocole = "jdbc:mysql:" ;
			// Adresse IP de l’hôte de la base et port
			String ip = "localhost" ; // dépend du contexte
			String port = "3306" ; // port MySQL par défaut
			// Nom de la base ;
			String nomBase = "panneaucontrole" ; // dépend du contexte
			// Chaîne de connexion
			String conString = protocole + "//" + ip + ":" + port + "/" + nomBase ;
			// Identifiants de connexion et mot de passe
			String nomConnexion = "root" ; 
			String motDePasse = "root" ; 
			// Connexion
			con = DriverManager.getConnection(conString, nomConnexion, motDePasse) ;

			System.out.println("connexion reussie");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String req1 = "SELECT sport.id, equipe.nom, sport.nom FROM sport, equipe WHERE equipe.sport = sport.id;";
		String req2 = "SELECT nom FROM sport;";

		try {
			stmt = con.createStatement();
			rs1 = stmt.executeQuery(req1);

			rs1.first();
			do  {
				if (rs1.getInt(1)==1) {
					Equipe equipe = (Equipe) new EquipeFoot(rs1.getString(2), rs1.getString(3));
					equipes.add(equipe);
				} else if (rs1.getInt(1)==2) {
					Equipe equipe = (Equipe) new EquipeRugby(rs1.getString(2), rs1.getString(3));
					equipes.add(equipe);
				} else if (rs1.getInt(1)==3) {
					Equipe equipe = (Equipe) new EquipeBasket(rs1.getString(2), rs1.getString(3));
					equipes.add(equipe);
				}
			} while (rs1.next());
			rs1.close();


		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblDomicile = new JLabel("Domicile");
		lblDomicile.setBounds(33, 11, 58, 14);
		frame.getContentPane().add(lblDomicile);

		JLabel lblExterieur = new JLabel("Exterieur");
		lblExterieur.setBounds(294, 11, 65, 14);
		frame.getContentPane().add(lblExterieur);

		JLabel lblScoreDom = new JLabel("Score dom");
		lblScoreDom.setBounds(79, 87, 73, 14);
		frame.getContentPane().add(lblScoreDom);

		JLabel lblScoreExt = new JLabel("Score ext");
		lblScoreExt.setBounds(355, 87, 58, 14);
		frame.getContentPane().add(lblScoreExt);

		JComboBox sport = new JComboBox();
		sport.setBounds(149, 70, 135, 20);
		frame.getContentPane().add(sport);
		try {
			rs2 = stmt.executeQuery(req2);
			while (rs2.next()) {
				sport.addItem(rs2.getString(1));
				sports.add(rs2.getString(1));
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JComboBox doms = new JComboBox();
		doms.setBounds(33, 36, 119, 20);
		frame.getContentPane().add(doms);
		for (int i=0; i<equipes.size(); i++) {
			//Affiche par sport
			if (equipes.get(i).getSport().equals(sport.getSelectedItem().toString())) {
				doms.addItem(equipes.get(i).getNom());
			}
		}

		JComboBox exts = new JComboBox();
		exts.setBounds(294, 36, 119, 20);
		frame.getContentPane().add(exts);
		for (int i=0; i<equipes.size(); i++) {
			//Affiche par sport
			if (equipes.get(i).getSport().equals(sport.getSelectedItem().toString())) {
				exts.addItem(equipes.get(i).getNom());
			}
		}

		//Gère le changement d'items sélectionner dans les sports
		sport.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				doms.removeAllItems();
				exts.removeAllItems();
				for (int i=0; i<equipes.size(); i++) {
					//Affiche par sport
					if (equipes.get(i).getSport().equals(sport.getSelectedItem().toString())) {
						doms.addItem(equipes.get(i).getNom());
						exts.addItem(equipes.get(i).getNom());
					}
				}
			}
		});

		//Création des boutons d'ajout de score
		JButton plus1Dom = new JButton("+1");
		plus1Dom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Utilisation de la méthode ajoutA pour ajouter le score à l'équipe à domicile
				for (int i =0; i<equipes.size(); i++) {
					if (doms.getSelectedItem().toString().equals(equipes.get(i).getNom())) {
						equipes.get(i).ajoutA();
						lblScoreDom.setText(Integer.toString(equipes.get(i).getScore()));
					}
				}
			}
		});
		plus1Dom.setBounds(149, 153, 51, 23);
		frame.getContentPane().add(plus1Dom);

		JButton plus1Ext = new JButton("+1");
		plus1Ext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Utilisation de la méthode ajoutA pour ajouter le score à l'équipe extérieur
				for (int i =0; i<equipes.size(); i++) {
					if (exts.getSelectedItem().toString().equals(equipes.get(i).getNom())) {
						equipes.get(i).ajoutA();
						lblScoreExt.setText(Integer.toString(equipes.get(i).getScore()));
					}
				}
			}
		});
		plus1Ext.setBounds(233, 153, 51, 23);
		frame.getContentPane().add(plus1Ext);

		JButton plus2Dom = new JButton("+2");
		plus2Dom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				for (int i =0; i<equipes.size(); i++) {
					if (doms.getSelectedItem().toString().equals(equipes.get(i).getNom())) {
						equipes.get(i).ajoutB();
						lblScoreDom.setText(Integer.toString(equipes.get(i).getScore()));
					}
				}
			}
		});
		plus2Dom.setBounds(149, 187, 51, 23);
		frame.getContentPane().add(plus2Dom);

		JButton plus2Ext = new JButton("+2");
		plus2Ext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				for (int i =0; i<equipes.size(); i++) {
					if (exts.getSelectedItem().toString().equals(equipes.get(i).getNom())) {
						equipes.get(i).ajoutB();
						lblScoreExt.setText(Integer.toString(equipes.get(i).getScore()));
					}
				}
			}
		});
		plus2Ext.setBounds(233, 187, 51, 23);
		frame.getContentPane().add(plus2Ext);

		JButton plus3Dom = new JButton("+3");
		plus3Dom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				for (int i =0; i<equipes.size(); i++) {
					if (doms.getSelectedItem().toString().equals(equipes.get(i).getNom())) {
						equipes.get(i).ajoutC();
						lblScoreDom.setText(Integer.toString(equipes.get(i).getScore()));
					}
				}
			}
		});
		plus3Dom.setBounds(149, 221, 51, 23);
		frame.getContentPane().add(plus3Dom);

		JButton plus3Ext = new JButton("+3");
		plus3Ext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Utilisation de la méthode ajoutC pour ajouter le score à l'équipe extérieur
				for (int i =0; i<equipes.size(); i++) {
					if (exts.getSelectedItem().toString().equals(equipes.get(i).getNom())) {
						equipes.get(i).ajoutC();
						lblScoreExt.setText(Integer.toString(equipes.get(i).getScore()));
					}
				}
			}
		});
		plus3Ext.setBounds(233, 221, 51, 23);
		frame.getContentPane().add(plus3Ext);

		JButton pause = new JButton("Pause");
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		pause.setBounds(149, 119, 135, 23);
		frame.getContentPane().add(pause);

		//Gestion d'une nouvelle partie
		JButton btnNouvellePartie = new JButton("Nouvelle Partie");
		btnNouvellePartie.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//Scores à zéro
				equipes.get(doms.getSelectedIndex()).setScore(0);
				equipes.get(exts.getSelectedIndex()).setScore(0);
				//Gestion du sport et evenement
				if (sport.getSelectedItem().equals(sports.get(0))) {
					lblScoreDom.setText(Integer.toString(equipes.get(doms.getSelectedIndex()).getScore()));
					lblScoreExt.setText(Integer.toString(equipes.get(exts.getSelectedIndex()).getScore()));
					plus1Dom.setText("1");
					plus1Ext.setText("1");
					plus2Dom.setVisible(false);
					plus2Ext.setVisible(false);
					plus3Dom.setVisible(false);
					plus3Ext.setVisible(false);
				} else if (sport.getSelectedItem().equals(sports.get(1))) {
					lblScoreDom.setText(Integer.toString(equipes.get(doms.getSelectedIndex()).getScore()));
					lblScoreExt.setText(Integer.toString(equipes.get(exts.getSelectedIndex()).getScore()));
					plus2Dom.setVisible(true);
					plus2Ext.setVisible(true);
					plus3Dom.setVisible(true);
					plus3Ext.setVisible(true);
					plus1Dom.setText("2");
					plus1Ext.setText("2");
					plus2Dom.setText("3");
					plus2Ext.setText("3");
					plus3Dom.setText("5");
					plus3Ext.setText("5");
				} else if (sport.getSelectedItem().equals(sports.get(2))) {
					lblScoreDom.setText(Integer.toString(equipes.get(doms.getSelectedIndex()).getScore()));
					lblScoreExt.setText(Integer.toString(equipes.get(exts.getSelectedIndex()).getScore()));
					plus2Dom.setVisible(true);
					plus2Ext.setVisible(true);
					plus3Dom.setVisible(true);
					plus3Ext.setVisible(true);
					plus1Dom.setText("1");
					plus1Ext.setText("1");
					plus2Dom.setText("2");
					plus2Ext.setText("2");
					plus3Dom.setText("3");
					plus3Ext.setText("3");
				}

			}
		});
		btnNouvellePartie.setBounds(149, 95, 135, 23);
		frame.getContentPane().add(btnNouvellePartie);

	}
}