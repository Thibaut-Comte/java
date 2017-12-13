package classes;

public abstract class Equipe {
	private String nom;
	private int score;
	private String sport;
	
	public Equipe(String nom, int score, String sport) {
		this.nom = nom;
		this.score = score;
		this.sport = sport;
	}
	
	public Equipe(String nom, String sport) {
		super();
		this.nom = nom;
		this.sport = sport;
	}

	public Equipe() {
		this.nom = "";
		this.score = 0;
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}
	
	public void ajout (int score) {
		this.score += score;
	}

	public abstract void ajoutA();
	public abstract void ajoutB();
	public abstract void ajoutC();
}
