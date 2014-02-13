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

	/** Les requ�tes en attentes de traitement. */
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
	 * Permet de prendre en compte une demande d'att�rissage.
	 * @param ae La demande � prendre en compte.
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
	 * Permet de lib�rer le processus en cours de traitement.
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
	 * Permet de r�cup�rer la place de la requ�te.
	 * @param ae La requ�te.
	 * @return La place de la requ�te.
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
