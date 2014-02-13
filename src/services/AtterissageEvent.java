/**
 * 
 */
package services;

import gestionPistes.Avion;

/**
 * @author jordanbustos
 * La requête d'attérissage.
 */
public class AtterissageEvent implements Ordonnable
{
	/** L'avion qui à fait la demande.*/
	private Avion avion;
	
	/** L'heure de prise en compte de la demande. */
	private long time;

	/**
	 * Constructeur.
	 * @param avion L'avion qui à fait la demande.
	 */
	public AtterissageEvent(Avion avion) 
	{
		setAvion(avion);
		setTime();
	}

	/**
	 * Setteur de l'heure de la requête.
	 */
	private void setTime() 
	{
		this.time = System.currentTimeMillis();
	}

	/**
	 * Permet de récupérer l'avion qui a effectué la demande.
	 * @return L'avion qui a effectué la demande.
	 */
	public Avion getAvion()
	{
		return avion;
	}
	
	/**
	 * Setteur de l'avion qui à fait la demande.
	 * @param avion L'avion à fixer.
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
