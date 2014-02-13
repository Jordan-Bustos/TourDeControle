/**
 * 
 */
package gestionPistes;

import java.util.List;

import services.AtterissageEvent;
import services.Ordonnanceur;

/**
 * @author jordanbustos
 * La tour de contrôle.
 */
public class TourDeControle 
{
	/** Les pistes disponibles de l'aéroport. */
	private List<Piste> pistesDisponibles;
	
	/** L'ordonnanceur. */
	private Ordonnanceur ordonnanceur;

	/**
	 * Constructeur.
	 * @param pistes Les pistes de l'aéroport.
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
	 * Permet de renvoyer une piste d'attérissage disponible.
	 * @param ae La demande d'attérissage.
	 * @return une piste d'attérissage disponible.
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
					System.err.println("L'aéroport tout entier a été détruit");
				}
			}
			pisteDispo = pistesDisponibles.remove(0);
		}		
		ordonnanceur.suivant();
		
		return pisteDispo;
	}

	/**
	 * Permet de libérer une piste d'attérissage.
	 * @param pisteAtterissage La liste d'attérissage à libérer.
	 */
	public void libererPiste(Piste pisteAtterissage) 
	{
		synchronized (pistesDisponibles) 
		{
			pistesDisponibles.add(pisteAtterissage);
			pistesDisponibles.notifyAll();
		}
		System.out.println(String.format("Piste %s libérée.", pisteAtterissage.getId()));
	}
	
}
