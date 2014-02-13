/**
 * 
 */
package services;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @author jordanbustos
 * Classe permettant de récupérer la sortie.
 */
public class PersistanceFile 
{
	/**
	 * Permet d'ouvrir la sortie.
	 * @param nomFichier Le nom du fichier à utiliser.
	 */
	public static void ouvrirSortie(String nomFichier)
	{
		File fich = new File(nomFichier) ;
		fich.getParentFile().mkdirs() ;
		try
		{
			System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(fich))));
			System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(fich))));
		} 
		catch (FileNotFoundException e)
		{
			System.err.println("Fichier " + nomFichier + " non trouvé.");
		}
	}	


}
