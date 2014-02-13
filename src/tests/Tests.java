/**
 * 
 */
package tests;

import gestionPistes.Piste;
import gestionPistes.TourDeControle;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import services.PersistanceFile;
import simulation.Simulateur;

/**
 * @author jordanbustos
 * Classe de test.
 */
public class Tests {

	/** La tour de contrôle de test. */
	private static TourDeControle tourDeControle;

	/** Les pistes de test. */
	private static List<Piste> pistes = new ArrayList<>();

	/**
	 * Main de l'application.
	 * @param args Les arguments.
	 */
	public static void main(String[] args) 
	{
		Tests tests = new Tests();
		tests.initialiserDonnees();
		tests.testSimulateur();
	}

	/**
	 * Permet d'initialiser les données.
	 */
	private void initialiserDonnees() 
	{
		for (int i=1; i<4; i++)
			pistes.add(new Piste());
		
		tourDeControle = new TourDeControle(pistes);
	}

	/**
	 * Permet de tester le fonctionnement du simulateur.
	 */
	private void testSimulateur() 
	{
	    //PersistanceFile.ouvrirSortie("./resultats/result.txt");
		
		Simulateur simulateur = new Simulateur(tourDeControle);
		
		simulateur.start();
		try { Thread.sleep(5000); } catch (InterruptedException e) { }
		simulateur.stop();
		
		try { Thread.sleep(1000); } catch (InterruptedException e) { }
		
		simulateur.start();
		try { Thread.sleep(5000); } catch (InterruptedException e) { }
		simulateur.stop();
	}

}
