/**
 * 
 */
package services;

import gestionPistes.Avion;

/**
 * @author jordanbustos
 * La requ�te d'att�rissage.
 */
public class AtterissageEvent implements Ordonnable
{
	/** L'avion qui � fait la demande.*/
	private Avion avion;
	
	/** L'heure de prise en compte de la demande. */
	private long time;

	/**
	 * Constructeur.
	 * @param avion L'avion qui � fait la demande.
	 */
	public AtterissageEvent(Avion avion) 
	{
		setAvion(avion);
		setTime();
	}

	/**
	 * Setteur de l'heure de la requ�te.
	 */
	private void setTime() 
	{
		this.time = System.currentTimeMillis();
	}

	/**
	 * Permet de r�cup�rer l'avion qui a effectu� la demande.
	 * @return L'avion qui a effectu� la demande.
	 */
	public Avion getAvion()
	{
		return avion;
	}
	
	/**
	 * Setteur de l'avion qui � fait la demande.
	 * @param avion L'avion � fixer.
	 */
	private void setAvion(Avion avion) 
	{
		this.avion = avion;
	}

	@Override
	public boolean precede(Ordonnable ordonnable) 
	{
		Boolean precede = false ;
	
		AtterissageEvent other = (AtterissageEvent) ordonnable;
		if (time == other.time)
			precede = avion.getPriorite() < other.avion.getPriorite();
		else
			precede = time < other.time;
		
		return precede;
	}

}
