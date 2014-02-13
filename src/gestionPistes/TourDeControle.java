/**
 * 
 */
package gestionPistes;

import java.util.List;

import services.AtterissageEvent;
import services.Ordonnanceur;

/**
 * @author jordanbustos
 * La tour de contr�le.
 */
public class TourDeControle 
{
	/** Les pistes disponibles de l'a�roport. */
	private List<Piste> pistesDisponibles;
	
	/** L'ordonnanceur. */
	private Ordonnanceur ordonnanceur;

	/**
	 * Constructeur.
	 * @param pistes Les pistes de l'a�roport.
	 */
	public TourDeControle (List<Piste> pistes)
	{
		setPistes(pistes);
		ordonnanceur = new Ordonnanceur();
	}
	
	/**
	 * Permet de fixer les pistes.
	 * @param pistes les pistes.
	 */
	private void setPistes(List<Piste> pistes) 
	{
		pistesDisponibles = pistes;
	}

	/**
	 * Permet de renvoyer une piste d'att�rissage disponible.
	 * @param ae La demande d'att�rissage.
	 * @return une piste d'att�rissage disponible.
	 */
	public Piste demanderPermissionDAtterir(AtterissageEvent ae)
	{
		System.out.println(String.format("Demande du vol %s prise en cours de traitement.", ae.getAvion().getNumeroDeVol()));
		
		Piste pisteDispo = null;
		
		ordonnanceur.entrer(ae);
		synchronized (pistesDisponibles)
		{
			while (pistesDisponibles.isEmpty()) 
			{
				try { pistesDisponibles.wait(); } 
				catch (InterruptedException e) 
				{
					System.err.println("L'a�roport tout entier a �t� d�truit");
				}
			}
			pisteDispo = pistesDisponibles.remove(0);
		}		
		ordonnanceur.suivant();
		
		return pisteDispo;
	}

	/**
	 * Permet de lib�rer une piste d'att�rissage.
	 * @param pisteAtterissage La liste d'att�rissage � lib�rer.
	 */
	public void libererPiste(Piste pisteAtterissage) 
	{
		synchronized (pistesDisponibles) 
		{
			pistesDisponibles.add(pisteAtterissage);
			pistesDisponibles.notifyAll();
		}
		System.out.println(String.format("Piste %s lib�r�e.", pisteAtterissage.getId()));
	}
	
}
