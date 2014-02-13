/**
 * 
 */
package services;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jordanbustos
 * L'ordonnaneur.
 */
public class Ordonnanceur {

	/** Les requêtes en attentes de traitement. */
	private List<AtterissageEvent> requetesEnAttentes;

	/** Les processus en attentes de traitement. */
	private List<Thread> processusEnAttentes;

	/** Le processus en cours de traitement. */
	private Thread processusEnCoursDeTraitement;


	/**
	 * Constructeur.
	 */
	public Ordonnanceur()
	{
		requetesEnAttentes = new ArrayList<>();
		processusEnAttentes = new ArrayList<>();
	}

	/**
	 * Permet de prendre en compte une demande d'attérissage.
	 * @param ae La demande à prendre en compte.
	 */
	public void entrer(AtterissageEvent ae) 
	{
		Thread thisProcessus = Thread.currentThread();
		synchronized (this) 
		{
			if (processusEnCoursDeTraitement == null)
				processusEnCoursDeTraitement = thisProcessus;
			else
			{
				int position = placeDe(ae);
				requetesEnAttentes.add(position,ae);
				processusEnAttentes.add(position,thisProcessus);
			}
		}		
		synchronized (thisProcessus) 
		{
			while (processusEnCoursDeTraitement != thisProcessus)
			{
				try { thisProcessus.wait(); } catch (InterruptedException e) { }
			}
		}
	}

	/**
	 * Permet de libérer le processus en cours de traitement.
	 */
	public void suivant()
	throws IllegalStateException
	{
		if (Thread.currentThread() != processusEnCoursDeTraitement)
			throw new IllegalStateException() ;
		
		synchronized (this)
		{	
			if (processusEnAttentes.size() != 0)
			{
				requetesEnAttentes.remove(0);
				processusEnCoursDeTraitement = processusEnAttentes.remove(0);
				synchronized (processusEnCoursDeTraitement) {
					processusEnCoursDeTraitement.notifyAll();
				}
			}
			else
				processusEnCoursDeTraitement = null;
		}
	}

	/**
	 * Permet de récupérer la place de la requête.
	 * @param ae La requête.
	 * @return La place de la requête.
	 */
	private int placeDe(AtterissageEvent ae) 
	{
		int i = 0;
		for( ; i<requetesEnAttentes.size(); i++)
			if (requetesEnAttentes.get(i).precede(ae))
				break;
		return i;
	}

}
