import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import stev.kwikemart.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
 * (v4) [2-10] items CUP différents avec nombre articles < 5
 * (v5) [2-10] items CUP différents avec nombre articles >= 5
 * (v6) 11+ items
 * 
 */
public class RegisterTest {
	
	private static Random rand;
	private static Register register;
	
	// -------------------------------------------------------------------------------------------------
	
	@BeforeClass
	public static void setUpBeforeClass () {
		rand = new Random();
		// Initialisation de la seed
		// On aura toujours le même test avec la même seed
		Long seed = rand.nextLong();
		// seed = -6372292149749056015L;
		rand.setSeed(seed);
		System.out.println("starting tests with seed : " + seed);
	}
	
	@Before
	public void setUp() throws Exception {
		// Initialisation de la machine
		register = Register.getRegister();
		register.changePaper(PaperRoll.LARGE_ROLL); // Large roll par défaut
	}

	@After
	public void tearDown() throws Exception {}
	
	// -------------------------------------------------------------------------------------------------
	
	// On ne peut pas générer totalement aléatoirement l'upc, on pourrait retomber sur le même lors d'un test
	// On crée donc une base de milieux d'upc, obtenus avec la méthode rand et une vérification par nous-même
	// qu'il n'y ai pas de duplication. Le début et la fin est toujours 0, que l'on définit plus tard
	private int upcIndice = 0;
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
	 * Inutile en JUNIT si on ne fait pas des tests trop gros puisqu'on est censé utiliser
	 * une instance différente pour chaque test
	 */
	private void resetMiddleUpc() {
		upcIndice = 0;
	}
	
	/**
	 * Permet de générer des codes CUP "aléatoires" distincts
	 * @return un tableau de char
	 */
	private char[] getMiddleUpc() {
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
	private Item generateItem(String name, int a, int b, int c, int d, int e) {
		
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
			if (quantCent % PRECISION == 0)
				quantCent += 1; // enlève manuellement la chance d'obtenir un entier
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
	private Item generateSameItem(Item item, double newQuantity) {
		return new Item(
				item.getUpc(),
				item.getDescription(),
				newQuantity,
				item.getRetailPrice()
			);
	}

	
	// -------------------------------------------------------------------------------------------------
	// Taille 3+
	
	/**
	 * Test utilisant le petit rouleau avec plusieurs entrées ne devant pas générer d'exceptions
	 * (a1 b1 c1 d1 e1) article unique
	 * (a1 b2 c1 d2 e2) article quantité fractionnaire
	 * (a1 b1 c5 d1 e1) article unique prix max
	 * (a1 b1 c1 d1 e1) puis le même en quantité négatif (a1 b1 c1 d4 e1) : retrait d'article
	 * (a1 b3 c1 d1 e1) coupon
	 * (a1 b1 c1 d2 e1)	article quantité multiple entière
	 * (a1 b3 c1 d1 e1) 2e coupon différent après article
	 * (v5) (on ne teste pas l'apparition de la ligne de réduction)
	 */
	@Test
	public void Test_ManyGoodUses_noExceptions() {
		register.changePaper(PaperRoll.SMALL_ROLL);
		List<Item> grocery = new ArrayList<Item>();
		grocery.add(generateItem("Savon",		1, 1, 1, 1, 1));
		grocery.add(generateItem("Tomates",		1, 2, 1, 2, 2));
		grocery.add(generateItem("Truc cher",	1, 1, 5, 1, 1));
		Item toRemove = generateItem("Couches",	1, 1, 1, 1, 1);
		grocery.add(toRemove);
		grocery.add(generateSameItem(toRemove, (-1)*toRemove.getQuantity()));
		grocery.add(generateItem("Super reduc",	1, 3, 1, 1, 1));
		grocery.add(generateItem("Thon cons",	1, 1, 1, 2, 1));
		grocery.add(generateItem("Naze reduc",	1, 3, 1, 1, 1));
		String out = register.print(grocery);
	}
	
	
	// -------------------------------------------------------------------------------------------------
	// Tests sur la reduction spéciale
	
	/**
	 * Test d'une liste de 5 à 10 items qui devrait afficher la réduction spéciale
	 */
	@Test
	public void Test_SpecialReduction_5itemsOrMore() {
		List<Item> grocery = new ArrayList<Item>();
		// pour être sur d'avoir un total > 2
		grocery.add(generateItem("Truc cher", 1, 1, 5, 1, 1));
		// on ajoute jusqu'à 5 items
		for (int i=0; i < 4; i++) {
			grocery.add(generateItem("S"+i+"von", 1, 1, 1, 1, 1));
		}
		// On ajoute en plus entre 0 et 5 items
		for(int i=0; i < rand.nextInt(6); i++) {
			grocery.add(generateItem("S"+(i+4)+"von", 1, 1, 1, 1, 1));
		}
		String registerOut = register.print(grocery);
		// System.out.print(registerOut);
		assertTrue(registerOut.contains("Rebate for 5 items"));
	}
	
	/**
	 * Test d'une liste de 5 items -1 item qui ne devrait pas afficher de réduction spéciale
	 * (le client n'achète que 4 items distincts)
	 */
	@Test
	public void Test_NoSpecialReduction_5items_1removed() {
		Item maxPrice1  = generateItem("Truc cher", 1, 1, 5, 1, 1); // pour être sur d'être total > 2
		Item normal2    = generateItem("S1von",	    1, 1, 1, 1, 1);
		Item normal3    = generateItem("S2von",	    1, 1, 1, 1, 1);
		Item normal4    = generateItem("S3von",	    1, 1, 1, 1, 1);
		Item normal5    = generateItem("S4von",	    1, 1, 1, 1, 1);
		Item normal5Neg = generateSameItem(normal5, (-1)*normal5.getQuantity());
		List<Item> grocery = new ArrayList<Item>();
		grocery.add(maxPrice1);
		grocery.add(normal2);
		grocery.add(normal3);
		grocery.add(normal4);
		grocery.add(normal5);
		grocery.add(normal5Neg);
		String registerOut = register.print(grocery);
		// System.out.print(registerOut);
		assertFalse(registerOut.contains("Rebate for 5 items"));
	}
	
	/**
	 * Test d'une liste de 5 items dont le total est inférieur à 2$ 
	 */
	@Test
	public void Test_NoSpecialReduction_5item_veryLowPrice() {
		List<Item> grocery = new ArrayList<Item>();
		for (int i=0; i < 5; i++) {
			Item tmp = generateItem(i+"tem",1,1,1,1,1); // crée cup unique
			grocery.add(new Item(tmp.getUpc(), tmp.getDescription(), 1, 0.01));
		}
		String registerOut = register.print(grocery);
		// System.out.print(registerOut);
		assertFalse(registerOut.contains("Rebate for 5 items"));
	}
	
	/**
	 * Test d'une liste de 4 items -1 item qui ne devrait pas afficher de réduction spéciale
	 * (le client n'achète que 3 items distincts, mais 5 lignes d'articles apparaissent)
	 */
	@Test
	public void Test_NoSpecialReduction_4items_1removed() {
		Item item    = generateItem("S4von",	    1, 1, 1, 1, 1);
		Item itemNeg = generateSameItem(item, (-1)*item.getQuantity());
		List<Item> grocery = new ArrayList<Item>();
		grocery.add(generateItem("Truc cher", 1, 1, 5, 1, 1)); // pour être sur d'être total > 2
		grocery.add(generateItem("S1von",	    1, 1, 1, 1, 1));
		grocery.add(generateItem("S2von",	    1, 1, 1, 1, 1));
		grocery.add(item);
		grocery.add(itemNeg);
		String registerOut = register.print(grocery);
		// System.out.print(registerOut);
		assertFalse(registerOut.contains("Rebate for 5 items"));
	}
	
	// -------------------------------------------------------------------------------------------------
	//   Taille 0
	
	/**
	 * Test d'une liste vide : v0
	 */
	@Test(expected = RegisterException.EmptyGroceryListException.class)
	public void Test_EmptyList_Exception() {
		List<Item> grocery = new ArrayList<Item>();
		grocery.clear();
		register.print(grocery);
	}
	
	// -------------------------------------------------------------------------------------------------
	//   Taille 11+
	
	/**
	 * Test d'un liste de 11 items valides différents : 11*(a1, b1, c1, d1, e1) -> v6
	 */
	@Test(expected = RegisterException.TooManyItemsException.class)
	public void Test_11items_Exception() {
		List<Item> grocery = new ArrayList<Item>();
		// Rempli le tableau de 11 items en quantité uniques avec des cup différents
		for(int i=0; i < 11; i++) {
			grocery.add(generateItem("ITEM", 1, 1, 1, 1, 1));
		}
		register.print(grocery);
	}

	// -------------------------------------------------------------------------------------------------
	// Tests a2
	
	/**
	 * Item avec CUP invalide (a2, b1, c1, d2, e1) -> v1
	 */
	@Test (expected = InvalidUpcException.class) 
	public void TestInvalidUPC() {
		Item badOne = generateItem("InvalidUPC", 2, 1, 1, 2, 1);
		List<Item> list = new ArrayList<Item>();
		list.add(badOne);
		register.print(list);
	}
	
	/**
	 * Item avec CUP invalide commençant par 2 (a2, b2, c1, d2, e1) -> v1
	 */
	@Test (expected = InvalidUpcException.class)
	public void TestInvalidUPCStart2() {
		Item badOne = generateItem("InvalidUPC", 2, 2, 1, 2, 1);
		List<Item> list = new ArrayList<Item>();
		list.add(badOne);
		register.print(list);
	}
	
	/**
	 * Item avec CUP invalide commençant par 5 (a2, b3, c1, d2, e1) -> v1
	 */
	@Test (expected = InvalidUpcException.class)
	public void TestInvalidUPCStart5() {
		Item badOne = generateItem("InvalidUPC", 2, 3, 1, 2, 1);
		List<Item> list = new ArrayList<Item>();
		list.add(badOne);
		register.print(list);
	}
	
	// -------------------------------------------------------------------------------------------------
	// Tests c2
	
	/**
	 * Test d'ajout d'un item au prix négatif (a1, b1, c2, d1, e1) -> v1
	 */
	@Test (expected = AmountException.NegativeAmountException.class)
	public void TestNegativeItemCost() {
		Item badOne = generateItem("NegCost", 1, 1, 2, 1, 1);
		List<Item> list = new ArrayList<Item>();
		list.add(badOne);
		register.print(list);
	}
	
	/**
	 * Test d'ajout d'un item de quantité fractionnaire au prix négatif (a1, b2, c2, d1, e2) -> v1
	 */
	@Test (expected = AmountException.NegativeAmountException.class)
	public void TestNegativeFracItemCost() {
		Item badOne = generateItem("NegCost", 1, 2, 2, 1, 2);
		List<Item> list = new ArrayList<Item>();
		list.add(badOne);
		register.print(list);
	}
	
	/**
	 * Test d'un coupon à coût négatif (a1, b1, c1, d2, e1) + (a1, b3, c2, d1, e1) -> v3
	 */
	@Test (expected = AmountException.NegativeAmountException.class)
	public void TestNegativeVoucher() {
		Item entry = generateItem("Test", 1, 1, 1, 2, 1);
		Item badOne = generateItem("Coupon", 1, 3, 2, 1, 1);
		List<Item> list = new ArrayList<Item>();
		list.add(entry);
		list.add(badOne);
		register.print(list);
	}
	
	// -------------------------------------------------------------------------------------------------
	// Tests c3
	
	/**
	 * Test prix supérieur à 35$ (a1, b1, c3, d2, e1) -> v1
	 */
	@Test (expected = AmountException.AmountTooLargeException.class)
	public void TestTooHighPriceItem() {
		Item badOne = generateItem("TooExpensive", 1, 1, 3, 2, 1);
		List<Item> list = new ArrayList<Item>();
		list.add(badOne);
		register.print(list);
	}
	
	/**
	 * Test prix supérieur à 35$ pour objet à quantité fractionnaire 
	 * (a1, b2, c3, d2, e2) -> v1
	 */
	@Test (expected = AmountException.AmountTooLargeException.class)
	public void TestTooHighPriceItemFrac() {
		Item badOne = generateItem("TooExpensive", 1, 2, 3, 2, 2);
		List<Item> list = new ArrayList<Item>();
		list.add(badOne);
		register.print(list);
	}
	
	/**
	 * Test prix supérieur à 35$ pour un coupon 
	 * (a1, b3, c3, d1, e1) -> v1
	 */
	@Test (expected = AmountException.AmountTooLargeException.class)
	public void TestTooHighPriceVoucher() {
		Item badOne = generateItem("TooExpensive", 1, 3, 3, 1, 1);
		List<Item> list = new ArrayList<Item>();
		list.add(badOne);
		register.print(list);
	}
	
	// -------------------------------------------------------------------------------------------------
	// Tests v2
	
	/**
	 * Deux fois le même CUP en quantité positive 2*(a1, b1, c1, d1, e1) -> v2
	 */
	@Test (expected = Register.DuplicateItemException.class)
	public void TestSameUPCTwice() {
		Item entry = generateItem("Test", 1, 1, 1, 2, 1);
		Item copy = generateSameItem(entry, 2);
		List<Item> list = new ArrayList<Item>();
		list.add(entry);
		list.add(copy);
		register.print(list);
	}
	
	/**
	 * Test de quantité > 1 coupons (a1, b3, c1, d2, e1) -> v2
	 */
	@Test (expected = CouponException.class)
	public void TestSameCouponTwice() {
		Item entry = generateItem("Test", 1, 1, 1, 2, 1);
		Item coupon = generateItem("Coupon", 1, 3, 1, 2, 1);
		List<Item> list = new ArrayList<Item>();
		list.add(entry);
		list.add(coupon);
		register.print(list);
	}
	
	// -------------------------------------------------------------------------------------------------
	// Tests !b2 et e2
	
	/**
	 * Ajout d'un item à quantité fractionnaire avec un CUP ne commençant pas par 2 ou 5
	 * (a1, b1, c1, d3, e2) -> v1
	 */
	@Test (expected = InvalidQuantityException.class)
	public void TestFracFirstUPCNot2() {
		Item badOne = generateItem("Frac", 1, 1, 1, 3, 2);
		List<Item> list = new ArrayList<Item>();
		list.add(badOne);
		register.print(list);
	}
	
	/**
	 * Ajout d'un item à quantité fractionnaire avec un CUP commençant par 5
	 * (a1, b3, c1, d3, e2) -> v1
	 */
	@Test (expected = InvalidQuantityException.class)
	public void TestFracFirstUPC5() {
		Item badOne = generateItem("Frac", 1, 3, 1, 3, 2);
		List<Item> list = new ArrayList<Item>();
		list.add(badOne);
		register.print(list);
	}
	
	// -------------------------------------------------------------------------------------------------
	// Tests d4
	
	/**
	 * Ajout d'un item en quantité négative en premier
	 * (a1, b1, c1, d4, e1) -> v1
	 */
	@Test (expected = Register.NoSuchItemException.class)
	public void TestNegativeQuantityFirst() {
		Item badOne = generateItem("NegativeQuantity", 1, 1, 1, 4, 1);
		List<Item> list = new ArrayList<Item>();
		list.add(badOne);
		register.print(list);
	}
	
	/**
	 * Ajout d'un item fractionnaire en quantité négative en premier
	 * (a1, b2, c1, d4, e2) -> v1
	 */
	@Test (expected = Register.NoSuchItemException.class)
	public void TestFracNegativeQuantityFirst() {
		Item badOne = generateItem("FracNegativeQuantity", 1, 2, 1, 4, 2);
		List<Item> list = new ArrayList<Item>();
		list.add(badOne);
		register.print(list);
	}
	
	/**
	 * Ajout d'un coupon en quantité négative en premier
	 * (a1, b1, c5, d1, e1) + (a1, b3, c1, d4, e1) -> v4
	 */
	@Test (expected = Register.NoSuchItemException.class)
	public void TestVoucherNegativeQuantityFirst() {
		Item entry = generateItem("Test", 1, 1, 5, 1, 1);
		Item badOne = generateItem("VoucherNegativeQuantity", 1, 3, 1, 4, 1);
		List<Item> list = new ArrayList<Item>();
		list.add(entry);
		list.add(badOne);
		register.print(list);
	}
	
	// -------------------------------------------------------------------------------------------------
	// Tests b3
	
	/**
	 * Ajout d'un unique coupon valide (ne doit pas être pris en compte)
	 * (a1,b3,c1,d1,e1) -> v1
	 */
	@Test
	public void Test_uniqueVoucher() {
		List<Item> list = new ArrayList<Item>();
		list.add(generateItem("coupon",1,3,1,1,1));
		String registerOut = register.print(list);
		assertFalse(registerOut.contains("Coupon:"));		
	}
	
}



