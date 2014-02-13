/**
 * 
 */
package services;

/**
 * @author jordanbustos
 * Interface d'oronnancement.
 */
public interface Ordonnable 
{
	/** 
	 * Permet de comparer deux ordonnable.
	 * @param ordonnable L'ordonnable � comparer.
	 * @return true si il pr�c�de, false sinon.
	 */
	public boolean precede(Ordonnable ordonnable);
}
