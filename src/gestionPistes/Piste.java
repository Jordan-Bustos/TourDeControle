/**
 * 
 */
package gestionPistes;


/**
 * @author jordanbustos
 * Piste d'attérissage.
 */
public class Piste 
{
	/** L'id de la piste. */
	private String id;

	/** Le nombre de pistes instanciées. */
	private static Integer nbPistes = 0;

	/**
	 * Constructeur.
	 */
	public Piste()
	{
		determinerId();
	}
		
	/**
	 * Permet de déterminer l'id.
	 */
	private void determinerId() 
	{
		synchronized (nbPistes) 
		{
			nbPistes = nbPistes + 1;
		}
		id = String.format("Piste%03d",nbPistes.intValue());
	}

	/**
	 * Permet de retourner l'id de la piste.
	 * @return L'id de la piste.
	 */
	public String getId() {
		return id;
	}

}
