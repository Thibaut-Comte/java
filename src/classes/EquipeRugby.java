package classes;

public class EquipeRugby extends Equipe{

	public EquipeRugby() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EquipeRugby (String nom, String sport) {
		super(nom, sport);
		// TODO Auto-generated constructor stub
	}

	public EquipeRugby(String nom, int score, String sport) {
		super(nom, score, sport);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ajoutA() {
		// TODO Auto-generated method stub
		super.ajout(2);
		
	}

	@Override
	public void ajoutB() {
		// TODO Auto-generated method stub
		super.ajout(3);
	}

	@Override
	public void ajoutC() {
		// TODO Auto-generated method stub
		super.ajout(5);
	}

	

}
