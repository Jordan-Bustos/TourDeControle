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
	/** Requ�te d'att�rissage. */
	private AtterissageEvent ae;
	
	/** La tour de contr�le de l'avion. */
	private TourDeControle tourDeControle;
	
	/** La priorit� de l'avion. */
	private int priorite;
	
	/** Num�ro de vol. */
	private String numeroDeVol;
	
	/** Le nom de l'avion. */
	private String nom;
	
	/** Le nombre d'avions instanci�s. */
	private static Integer nbAvions = 0;
	
	/** G�n�rateur de nombre al�atoire. */
	private static Random rand = new Random();
	
	/**
	 * Constructeur d'un avion.
	 * @param tourDeControle Sa tour de contr�le.
	 * @param priorite Sa priorit�.
	 * @throws IllegalArgumentException Si le priorit� est mal renseign�e.
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
	 * Permet de r�cup�rer le num�ro de vol.
	 * @return Le num�ro de vol.
	 */
	public String getNumeroDeVol()
	{
		return numeroDeVol;
	}
	
	/**
	 * Permet de g�n�rer un num�ro de vol unique.
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
	 * Permet de recup�rer la priorit� de l'avion.
	 * @return La priorit� de l'avion.
	 */
	public int getPriorite()
	{
		return priorite;
	}
	
	/**
	 * Permet de modifier la priorit� d'un avion en cours de vol.
	 * @param priorite La priorit� a assigner.
	 * @throws IllegalArgumentException Si la priorit� est mal renseign�e.
	 */
	public void modifierPriorite(int priorite)
	throws IllegalArgumentException
	{
		setPriorite(priorite);
	}
	
	/**
	 * Setteur de la priorit�.
	 * @param priorite La priorit� � fixer.
	 * @throws IllegalArgumentException si la priorit� est mal renseign�e. Doit �tre comprise entre 1 et 3.
	 */
	private void setPriorite(int priorite)
	throws IllegalArgumentException
	{
		if (priorite < 0 || priorite > 3)
			throw new IllegalArgumentException("La priorit�e doit �tre comprise en 1 et 3");
		this.priorite = priorite;
	}
	
	/**
	 * Setteur de la tour de contr�le. 
	 * @param tourDeControle La tour de contr�le � fixer.
	 */
	private void setTourDeControle(TourDeControle tourDeControle)
	{
		this.tourDeControle = tourDeControle;
	}

	/**
	 * Permet � l'avion d'informer sa demande d'att�rissage � la tour de contr�le.
	 * @return La piste sur laquelle il doit att�rir.
	 */
	private Piste phaseDApproche() 
	{
		System.out.println(String.format("L'avion %s du vol %s entre en phase d'approche.", nom, numeroDeVol));
		ae = new AtterissageEvent(this);
		return tourDeControle.demanderPermissionDAtterir(ae);
	}
	
	/**
	 * Permet � l'avion d'att�rir.
	 * @param pisteAtterissage La piste d'att�rissage.
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
		// 1- L'avion d�cole et vol pendant un temps al�atoire.
		System.out.println(String.format("L'avion %s va d�colle (Vol num�ro %s).", nom, numeroDeVol));
		try { Thread.sleep(rand.nextInt(3000)+300); }
		catch (InterruptedException e) 
		{
			System.err.println(String.format("L'avion %s s'est crach�, aucun rescap�s ... (Vol num�ro %s).", nom, numeroDeVol));
		}
		
		//2- L'avion entre en phase d'approche.
		Piste pisteAtterissage = phaseDApproche();
		
		//3- L'avion se pose.
		atterrir(pisteAtterissage);
		
		//4- Il fait sortir les passagers puis lib�re la piste.
		System.out.println(String.format("Vol num�ro %s termin�, vous pouvez sortir.", numeroDeVol));
		try { Thread.sleep(rand.nextInt(3000)+300); }
		catch (InterruptedException e) 
		{
			System.err.println(String.format("L'avion %s explose pendant que les passagers d�scendent ...", nom));
		}
		tourDeControle.libererPiste(pisteAtterissage);
	}
}
