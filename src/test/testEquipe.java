package test;

import java.util.Scanner;

import classes.Equipe;
import classes.EquipeBasket;
import classes.EquipeFoot;
import classes.EquipeRugby;

public class testEquipe {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Equipe aqa;
		Scanner sc = new Scanner(System.in);
		String eq;
		String sport;
		String relance = "";


		do {
			System.out.println("Quelle est votre Equipe ?");
			eq = sc.nextLine();
			System.out.println("Dans quel sport joue-t-elle ?");
			sport = sc.nextLine();

			if (sport.equals("foot")) {
				aqa = (Equipe) new EquipeFoot (eq, "Football");

			} else if (sport.equals("rugby")) {
				aqa = (Equipe) new EquipeRugby (eq, "Rubgy");

			} else {
				aqa = (Equipe) new EquipeBasket (eq, "Basketball");
			}

			aqa.ajoutC();
			System.out.println(aqa.getScore());
			aqa.ajoutB();
			System.out.println(aqa.getScore());
			aqa.ajoutA();
			System.out.println(aqa.getScore());
			
			System.out.println("Souhaitez-vous relancer le test ?");
			relance = sc.nextLine();
		}
		while (relance.equals("oui"));

		sc.close();



	}

}
