/**
 * Caractéristiques possibles d'un item (article ou coupon) :
 * 
 * -- CUP --
 * 
 * (a1) Valide
 * (a2) Invalide
 * 
 * (b1) 1,3,4,6,7,8,9,0 au début
 * (b2) 2 au début
 * (b3) 5 au début
 
 * 
 * -- Prix unitaire (p) --
 * 
 * (c1) p in ]0, 35[
 * (c2) p < 0
 * (c3) p > 35
 * (c4) p = 0
 * (c5) p = 35
 * 
 * -- Quantité (q) --
 * 
 * (d1) q = 1
 * (d2) q > 1
 * (d3) q in ]0,1[
 * (d4) q < 0
 * (d5) q = 0
 *
 * 
 * (e1) q entière
 * (e2) q fractionnaire
 * 
 * 
 * Caractéristiques possibles d'un liste :
 * 
 * (v0) vide
 * (v1) 1 item
 * (v2) 2 items CUP similaires
 * (v3) [3,10] items CUP similaires
 * (v4) [2-4]  articles CUP différents
 * (v5) [5-10] articles CUP différents
 * (v6) [6-10] coupons CUP différents
 * (v7) 11+ items
 * 
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import stev.kwikemart.*;


public class OldRegisterTest {
	
	private static Random rand = new Random(); // TODO add a seed
	public static Register register;
	
	// -------------------------------------------------------------------------------------------------
	
	// On ne peut pas générer aléatoirement l'upc, on pourrait retomber sur le même lors d'un test
	// On crée donc une base de milieux d'upc, obtenus avec la méthode rand et une vérification par nous-même
	// qu'il n'y ai pas de duplication. Le début et la fin est toujours 0, que l'on définit plus tard
	private static int upcIndice = 0;
	private static char[][] middleUpc = new char[][]{ // [11], [12]
			{'0','5','4','2','5','4','4','7','7','2','8','0',},
			{'0','4','6','5','6','1','4','6','1','9','5','0',},
			{'0','0','9','7','0','1','1','9','4','7','5','0',},
			{'0','9','6','2','6','2','2','3','8','9','0','0',},
			{'0','1','1','5','6','1','4','2','1','3','2','0',},
			{'0','7','2','4','8','4','3','4','8','6','3','0',},
			{'0','0','5','1','9','7','5','0','4','8','7','0',},
			{'0','5','3','6','8','1','7','7','4','6','6','0',},
			{'0','3','5','6','3','1','9','7','4','3','3','0',},
			{'0','3','2','1','9','5','2','4','2','0','0','0',},
			{'0','2','7','0','1','6','2','8','2','2','3','0',}
		};
	
	// Code simpliste utilisé dans un main (avec un Random()) pour la génération du middleUpc
	// (sortie à vérifier puis à copier dans le tableau)
	/* 
	System.out.println("{");
	for (int i=0; i < 11; i++) {
		int[] upcTab = new int[12];
		upcTab[0] = 0;
		upcTab[11] = 0;
		for (int j=1; j < 11; j++) {
			upcTab[j] = rand.nextInt(10); // [0,9]
		}
		System.out.print("{");
		for (int j=0; j < 12; j++) {
			System.out.print("'" + upcTab[j] + "',");
		}
		System.out.print("}");
		if (i < 10) System.out.println(",");
		else System.out.println("");
	}
	System.out.println("}");
	*/
	
		
	/**
	 * Remet à 0 le générateur de cup
	 * Appeler cette méthode à la fin de la création d'une liste
	 */
	private static void resetMiddleUpc() {
		upcIndice = 0;
	}
	
	/**
	 * Permet de générer des codes CUP "aléatoires" distincts
	 * @return un tableau de char
	 */
	private static char[] getMiddleUpc() {
		return middleUpc[upcIndice++];
	}
	
	// -------------------------------------------------------------------------------------------------
	
	
	/**
	 * Générateur aléatoire d'items en fonction des classes d'équivalences définies
	 * @param name Le nom de l'objet (arbitraire)
	 * @param a Validité du CUP
	 * @param b Début du CUP
	 * @param c Prix
	 * @param d Quantité numérique
	 * @param e Type de quantité
	 * @return Un {@item} aléatoire correspondant, null s'il y a une erreur dans les paramètres
	 */
	private static Item generateItem(String name, int a, int b, int c, int d, int e) {
		
		// --------- Création de l'UPC ---------
		char[] upcTab = new char[12];
		
		// Génération du 1er caractère
		int upc0 = 0;
		switch (b) {
			case 1:
				upc0 = rand.nextInt(8); // [0,7]
				if (upc0 >= 2) upc0 += 1;
				if (upc0 >= 5) upc0 += 1; // [0,1,3,4,6,7,8,9]
				break;
			case 2:
				upc0 = 2;
				break;
			case 3:
				upc0 = 5;
				break;
			default:
				System.out.println("generateItem paramter 'b' used wrong (" + b +")");
				return null;
		}
		upcTab[0] = (char) (upc0 + '0');
		
		// Génération des 10 caractères du milieu TODO : create random cup manually pour le cas où on a pas de bol
		char[] temp = getMiddleUpc();
		for(int i=1; i < 11; i++) {
			upcTab[i] = temp[i];
		}
		
		// Génération du 12e caractère (la méthode getCheckDigit ignore les caractères après 11)
		int upc11 = Upc.getCheckDigit(new String(upcTab));
		switch (a) {
			case 1:
				// nothing to do
				break;
			case 2:
				int randomInt = rand.nextInt(9) + 1; // [1,9]
				upc11 = (upc11 + randomInt) % 10; // un autre chiffre que lui-même
				break;
			default:
				System.out.println("generateItem paramter 'a' used wrong (" + a +")");
				return null;
		}
		upcTab[11] = (char) (upc11 + '0');
		
		// CUP final
		String upc = new String(upcTab);
		
		// --------- Création du prix ---------
		// (arbitraire : on ne dépasse pas 99.99$ par item)
		int MAX_PRICE = 9999; // (en cent)
		int cent = 0;
		switch (c) {
			case 1:
				cent = rand.nextInt(3499) + 1; // [1,3499]
				break;
			case 2:
				cent = (-1) * (rand.nextInt(MAX_PRICE) + 1); // [-max, -1]
				break;
			case 3:
				cent = rand.nextInt(MAX_PRICE-3501) + 3501; // [3501, max]
				break;
			case 4:
				cent = 0;
				break;
			case 5:
				cent = 3500;
				break;
			default:
				System.out.println("generateItem paramter 'c' used wrong (" + c +")");
				return null;
		}
		// Prix final
		double price = cent/(double)100;
		
		// --------- Création du prix ---------
		// (arbitraire : on ne dépasse pas 10 objets (ou 10* en fraction), on considère une précision de 1/1000 en fractionnaire)
		int MAX_QUANT = 10; // (jamais atteinte)
		int PRECISION = 1000;
		double quantity = 0;
		if (e == 1) {
			switch (d) {
				case 1:
					// Rien à faire pour l'instant
					break;
				case 2:
					quantity = rand.nextInt(MAX_QUANT - 2) + 2; // [2, max[
					break;
				case 3:
					System.out.println("generateItem you can't be integer and inside ]0,1[");
					return null;
				case 4:
					quantity = (-1)*rand.nextInt(MAX_QUANT - 1) + 1; // ]-max, -1]
					break;
				case 5:
					// Rien à faire pour l'instant
					break;
				default:
					System.out.println("generateItem paramter 'd' used wrong (" + d +")");
					return null;
			}
		}
		else if (e == 2) {
			int quantCent = 0;
			switch (d) {
				case 1:
					// Rien à faire pour l'instant
					break;
				case 2:
					quantCent = rand.nextInt(MAX_QUANT*PRECISION - PRECISION - 1) + PRECISION + 1; // ]1, max[
					break;
				case 3:
					quantCent = rand.nextInt(PRECISION-1) + 1; // ]0,1[
					break;
				case 4:
					quantCent = (-1)*(rand.nextInt(MAX_QUANT*PRECISION -1) + 1); // ]-max, -1]
					break;
				case 5:
					// Rien à faire pour l'instant
					break;
				default:
					System.out.println("generateItem paramter 'd' used wrong (" + d +")");
					return null;
			}
			quantity = (quantCent/(double)PRECISION);
		}
		else {
			System.out.println("generateItem paramter 'e' used wrong (" + e +")");
			return null;
		}
		// Valeurs entières de d
		if (d == 1) {
			quantity = 1;
		}
		else if (d == 5) {
			quantity = 0;
		}
		
		// Création de l'objet final
		Item retItem = new Item(upc, name, quantity, price);
		return retItem;
	}
	
	
	/**
	 * Permet de créer le même item en changeant la quantité
	 * @param item L'objet à copier
	 * @param newQuantity La nouvelle quantité
	 * @return Un {@item} similaire
	 */
	private static Item generateSameItem(Item item, double newQuantity) {
		return new Item(
				item.getUpc(),
				item.getDescription(),
				newQuantity,
				item.getRetailPrice()
			);
	}
	
	// -------------------------------------------------------------------------------------------------
	
	/**
	 * Test utilisant le petit rouleau utilisant toutes les classes ne devant pas générer d'exceptions
	 */
	public static void AllOkTest() {
		List<Item> grocery = new ArrayList<Item>();
		// item unique
		grocery.add(generateItem("Savon",		1, 1, 1, 1, 1));
		// item quantité fractionnaire
		grocery.add(generateItem("Tomates",		1, 2, 1, 2, 2));
		// item prix max
		grocery.add(generateItem("Truc cher",	1, 1, 5, 1, 1));
		// ajout item pour retrait
		Item couches = generateItem("Couches",	1, 1, 1, 1, 1);
		grocery.add(couches);
		grocery.add(generateSameItem(couches, (-1)*couches.getQuantity()));
		// ajout d'un coupon
		grocery.add(generateItem("Super reduc",	1, 3, 1, 1, 1));
		// item quantité multiple entière
		grocery.add(generateItem("Thon cons",	1, 1, 1, 2, 1));
		// ajout d'un autre coupon
		grocery.add(generateItem("Naze reduc",	1, 3, 1, 1, 1));
		// resultat : pas d'erreurs attendues
		System.out.println(register.print(grocery));	
		resetMiddleUpc();
	}
	

	public static void main(String[] args) {
		// Initialisation de la seed
		long seed = rand.nextLong();
		// Décommenter et changer cette ligne si on veut reproduire le même test
		// seed = 962738903477909273L;
		rand.setSeed(seed);
		System.out.println("starting tests with seed : " + seed);
		// Initialisation de la machine
		register = Register.getRegister();
		// Tests
		register.changePaper(PaperRoll.SMALL_ROLL);
		AllOkTest();
		register.changePaper(PaperRoll.LARGE_ROLL);
		// etc...
	
	}

}
