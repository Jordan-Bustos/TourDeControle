/**
 * 
 */
package gestionPistes;

import java.util.Random;

import services.AtterissageEvent;

/**
 * @author jordanbustos
 * La classe Avion.
 */
public class Avion implements Runnable 
{	
	/** Requête d'attérissage. */
	private AtterissageEvent ae;
	
	/** La tour de contrôle de l'avion. */
	private TourDeControle tourDeControle;
	
	/** La priorité de l'avion. */
	private int priorite;
	
	/** Numéro de vol. */
	private String numeroDeVol;
	
	/** Le nom de l'avion. */
	private String nom;
	
	/** Le nombre d'avions instanciés. */
	private static Integer nbAvions = 0;
	
	/** Générateur de nombre aléatoire. */
	private static Random rand = new Random();
	
	/**
	 * Constructeur d'un avion.
	 * @param tourDeControle Sa tour de contrôle.
	 * @param priorite Sa priorité.
	 * @throws IllegalArgumentException Si le priorité est mal renseignée.
	 */
	public Avion (TourDeControle tourDeControle, int priorite)
	throws IllegalArgumentException
	{
		setTourDeControle(tourDeControle);
		setPriorite(priorite);
		definirNumeroDeVol();
		
		Thread threadCourant = new Thread(this);
		threadCourant.setDaemon(true);
		threadCourant.start();
	}
	
	/**
	 * Permet de récupérer le numéro de vol.
	 * @return Le numéro de vol.
	 */
	public String getNumeroDeVol()
	{
		return numeroDeVol;
	}
	
	/**
	 * Permet de générer un numéro de vol unique.
	 */
	private void definirNumeroDeVol() 
	{
		synchronized (nbAvions) 
		{
			nbAvions = nbAvions + 1;
		}
		nom = String.format("AV%03dB", nbAvions.intValue());
		numeroDeVol = String.format("%sV%03dB", nom, nbAvions.intValue());
	}

	/**
	 * Permet de recupérer la priorité de l'avion.
	 * @return La priorité de l'avion.
	 */
	public int getPriorite()
	{
		return priorite;
	}
	
	/**
	 * Permet de modifier la priorité d'un avion en cours de vol.
	 * @param priorite La priorité a assigner.
	 * @throws IllegalArgumentException Si la priorité est mal renseignée.
	 */
	public void modifierPriorite(int priorite)
	throws IllegalArgumentException
	{
		setPriorite(priorite);
	}
	
	/**
	 * Setteur de la priorité.
	 * @param priorite La priorité à fixer.
	 * @throws IllegalArgumentException si la priorité est mal renseignée. Doit être comprise entre 1 et 3.
	 */
	private void setPriorite(int priorite)
	throws IllegalArgumentException
	{
		if (priorite < 0 || priorite > 3)
			throw new IllegalArgumentException("La prioritée doit être comprise en 1 et 3");
		this.priorite = priorite;
	}
	
	/**
	 * Setteur de la tour de contrôle. 
	 * @param tourDeControle La tour de contrôle à fixer.
	 */
	private void setTourDeControle(TourDeControle tourDeControle)
	{
		this.tourDeControle = tourDeControle;
	}

	/**
	 * Permet à l'avion d'informer sa demande d'attérissage à la tour de contrôle.
	 * @return La piste sur laquelle il doit attérir.
	 */
	private Piste phaseDApproche() 
	{
		System.out.println(String.format("L'avion %s du vol %s entre en phase d'approche.", nom, numeroDeVol));
		ae = new AtterissageEvent(this);
		return tourDeControle.demanderPermissionDAtterir(ae);
	}
	
	/**
	 * Permet à l'avion d'attérir.
	 * @param pisteAtterissage La piste d'attérissage.
	 */
	private void atterrir(Piste pisteAtterissage) 
	{
		System.out.println(String.format("L'avion %s du vol %s se pose sur la piste %s",
				nom, numeroDeVol, pisteAtterissage.getId()));
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() 
	{
		// 1- L'avion décole et vol pendant un temps aléatoire.
		System.out.println(String.format("L'avion %s va décolle (Vol numéro %s).", nom, numeroDeVol));
		try { Thread.sleep(rand.nextInt(3000)+300); }
		catch (InterruptedException e) 
		{
			System.err.println(String.format("L'avion %s s'est craché, aucun rescapés ... (Vol numéro %s).", nom, numeroDeVol));
		}
		
		//2- L'avion entre en phase d'approche.
		Piste pisteAtterissage = phaseDApproche();
		
		//3- L'avion se pose.
		atterrir(pisteAtterissage);
		
		//4- Il fait sortir les passagers puis libère la piste.
		System.out.println(String.format("Vol numéro %s terminé, vous pouvez sortir.", numeroDeVol));
		try { Thread.sleep(rand.nextInt(3000)+300); }
		catch (InterruptedException e) 
		{
			System.err.println(String.format("L'avion %s explose pendant que les passagers déscendent ...", nom));
		}
		tourDeControle.libererPiste(pisteAtterissage);
	}
}
