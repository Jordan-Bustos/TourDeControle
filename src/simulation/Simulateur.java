/**
 * 
 */
package simulation;

import java.util.Random;

import gestionPistes.Avion;
import gestionPistes.TourDeControle;

/**
 * @author jordanbustos
 * Simulateur.
 */
public class Simulateur implements Runnable {

	/** La tour de contrôle. */
	private TourDeControle tourDeControle;
	
	/** État du thread courant. */
	private boolean actif;
	
	/** Le thread courant. */
	private Thread threadCourant;
	
	/** Générateur de nombre aléatoire. */
	private static Random rand = new Random();

	/**
	 * Constructeur.
	 * @param tourDeControle La tour de contrôle.
	 */
	public Simulateur(TourDeControle tourDeControle)
	{
		setTourDeControle(tourDeControle);
		setActif(false);
	}
	
	/**
	 * Permet de modifier l'état du thread courant.
	 * @param actif actif ou pas. true ou false.
	 */
	private void setActif(boolean actif) {
		this.actif = actif;
	}

	/**
	 * Setteur de la tour de contrôle.
	 * @param tourDeControle La tour de contrôle.
	 */
	private void setTourDeControle(TourDeControle tourDeControle) 
	{
		this.tourDeControle = tourDeControle;
	}
	
	/**
	 * Permet de lancer le simulateur.
	 */
	public void start()
	{
		System.out.println("\n***** LANCEMENT DU SIMULATEUR *****\n");
		
		setActif(true);
		threadCourant = new Thread(this);
		threadCourant.start();
	}
	
	/**
	 * Permet de stoper le thread courant.
	 */
	public void stop()
	{
		System.out.println("\n******* ARRET DU SIMULATEUR *******\n");
		
		setActif(false);
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
		while (actif)
		{
			new Avion(tourDeControle, rand.nextInt(3)+1);
			try { Thread.sleep(1000); } catch (InterruptedException e) { }
		}
	}

}
