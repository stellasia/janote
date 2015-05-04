import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import com.janote.controller.MainController;
import com.janote.model.entities_new.Exam;
import com.janote.model.entities_new.Student;
import com.janote.view.console.QuickTests;
import com.janote.view.gui.MainWindow;

/**
 * Janote class
 * Contains the main method.
 * 
 * @author Estelle Scifo
 * @version 1.0
 */
public class Janote {

	/**
	 * Main function of the application. 
	 * 
	 * @param args
	 */

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {

		try {
//			UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
//			UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
//			 UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
//			 UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");  

			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
				
			}
			
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look and feel.
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}


		QuickTests qt = new QuickTests(); // no need to create an instance that will not be used and will issue a WARNING
		qt.run();
		
		// Create the controler
		//MainController controller = new MainController();
		// Create the main window and link it to the controller
		//MainWindow window = 
		//new MainWindow(controller);

	}


	/*
	public static void tests() {

		// Instance of Student
		Student anonymous = new Student();
		System.out.println(anonymous.toString());	

		// Instance of Exam
		// First create the Date object
		Calendar cal = Calendar.getInstance(); 
		cal.set(2013, 10, 21); // months begin at 0, days at 1
		Date date = cal.getTime();
		Exam ds1 = new Exam(1, 1, "Premier DS", date, 1);
		System.out.println(ds1.toString());	// print the correct month number !

		cal.set(2013, 8, 25); // months begin at 0, days at 1
		date = cal.getTime();
		Exam ds2 = new Exam(2, 1, "Deuxième DS", date, 1);
		System.out.println(ds2.toString());	// print the correct month number !


	}
*/
	
	/*
	public static void tutorial () {
     	// Print sans retour à la ligne		
		System.out.print("Hello World !");	
		System.out.println(" Hello lonesome cowboy.");	

		// types de variables 
		byte temperature; // entier 1 octets
		short vitesseMax; // entier 2 octets
		int temperatureSoleil; // entier 4 octets
		long anneeLumiere; // entier 8 octets
		float pi; // 4 octets
		double division; // 8 octets
		char caractere; //  " "
		boolean question; // true, false

		// Les chaines de caracteres 
		//Première méthode de déclaration
		String phrase = "Titi et Grosminet";
		System.out.println(phrase);	

		//Deuxième méthode de déclaration
		String str = new String();
		str = "Une autre chaîne de caractères";
		System.out.println(str);	

		//Troisième méthode de déclaration
		String string = "Une autre chaîne";
		System.out.println(string);	

		//Quatrième méthode de déclaration
		String chaine = new String("Et une de plus !");
		System.out.println(chaine);	

		// Les méthodes 
		String chaine2 = chaine.toLowerCase(); 
		// OU bien chaine2 = chaine.toUpperCase();   //Donne "COUCOU COUCOU"
		int longueur = chaine.length();   //Renvoie 9

		// Test d'égalité de deux chaines de caractères
		if (chaine.equals("Et une de plus !"))
			System.out.println("Les deux chaines sont égales");

		// Concaténation
		double nbre1 = 10, nbre2 = 3;
		int resultat = (int)(nbre1 / nbre2);
		System.out.println("Le résultat est = " + resultat);

		// int to string (fonctionne aussi avec les autres types numériques)
		int i = 12;
		String i2 = new String();
		i2 = i2.valueOf(i);
		// String to int
		int k = Integer.valueOf(i2).intValue();

		// Inputs
		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez saisir un mot :");
		String text = sc.nextLine();
		System.out.println("Vous avez saisi : " + text);
		System.out.println("Veuillez saisir un nombre :");
		int nbr = sc.nextInt();
		System.out.println("Vous avez saisi le nombre : " + nbr);
		sc.close();

		// If 
		if (nbr>10) 	
			System.out.println(nbr + " est un numbre gagnant, bravo." );
		else if (nbr==10)
			System.out.println(nbr + " est un numbre frontiere, je ne sais pas quoi dire." );
		else
			System.out.println(nbr + " est un numbre perdant, désolée." );


		// Loops (syntax idem C++)
		// ... 

		// Tables
		int tableauEntier[] = {0,1,2,3,4,5,6,7,8,9};

		// Initialisation tableau vide de 6 entiers
		int tableauEntier1[] = new int[6];
		//Ou encore
		int[] tableauEntier2 = new int[6];

		// Parcourt tableau classique 
		for(k = 0; k < tableauEntier.length; k++)
		{
		  System.out.println("À l'emplacement " + k +" du tableau nous avons = " + tableauEntier[k]);
		}

		// Parcourt tableau nouveau
		String tab[] = {"toto", "titi", "tutu", "tete", "tata"};

		for(String s : tab)
		  System.out.println(s);

		String table[][]={{"toto", "titi", "tutu", "tete", "tata"}, {"1", "2", "3", "4"}};
		i = 0;
		int j = 0;

		for(String sousTab[] : table)
		{
		  i = 0;
		  for(String s : sousTab)
		  {     
//		    System.out.println("La valeur de la nouvelle boucle est  : " + s);
		    System.out.println("La valeur du tableau à l'indice ["+j+"]["+i+"] est : " + table[j][i]);
		    i++;
		  }
		  j++;
		}
	} // end tutorial function
	 */

}
