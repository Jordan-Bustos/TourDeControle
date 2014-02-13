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
	 * @param ordonnable L'ordonnable à comparer.
	 * @return true si il précède, false sinon.
	 */
	public boolean precede(Ordonnable ordonnable);
}
