package classes;

public class EquipeBasket extends Equipe{

	public EquipeBasket() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EquipeBasket (String nom, String sport) {
		super(nom, sport);
		// TODO Auto-generated constructor stub
	}

	public EquipeBasket(String nom, int score, String sport) {
		super(nom, score, sport);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ajoutA() {
		// TODO Auto-generated method stub
		super.ajout(1);
	}

	@Override
	public void ajoutB() {
		// TODO Auto-generated method stub
		super.ajout(2);
	}

	@Override
	public void ajoutC() {
		// TODO Auto-generated method stub
		super.ajout(3);
	}

}


