package gameUC;

import java.util.ArrayList;

public class Level {

	ArrayList<Building> building;
	private int freeSlots;
	private int numbSlots;

	/** Konstruktor der die Anzahl der Freien Slots festlegt*/
	Level(int numFreeSlots){
		freeSlots = numFreeSlots;
		numbSlots = numFreeSlots;
		building = new ArrayList<Building>();
	}

	public int getNumbSlots() {
		return numbSlots;
	}

	/** F�gt ein Bauwerk am Ende der Liste ein. */
	public void insertBuilding(Building b){
		/** Jedes Bauwerk verbraucht Slots
		 * d.h. die Anzahl der freien Slots muss sinken 
		 * 
		 * Sind gen�gend Slots vorhanden?
		 * 
		 */

		if (freeSlots >= b.needSlots()){
			building.add(b);
			freeSlots -= b.needSlots();
		}

	}

	public boolean destroyBuilding(){

		if (building.isEmpty()) {
			System.out.println("Kein Geb�ude Vorhanden");
			return false;
		}  
		int index = building.size() - 1;

		Building b  = building.get(index);
		building.remove(index);
		
		int lose = 0;
		if (b instanceof Apartment){
			lose += ((Apartment) b).getPopulation();
			System.out.println("Die Ebene hat " +lose+ " Einwohner verloren");
			
		}
		
		freeSlots += b.needSlots(); 
		return true;
		
		
		
	}

	public void destroyEverything(){

		// Alle Geb�ude einer Ebene sollen gel�scht werden
		building.clear();
		freeSlots = Structure.smallLevelSlot;
	}

	/** wie viele Slots sind frei */
	public int getFreeSlots(){
		return freeSlots;
	}

	/** F�hrt genau eine Spielrunde f�r diese Ebene aus */
	public void round(){

		int life = getLifequality();
		for(Building b : building){
			if (b instanceof Apartment){
				((Apartment) b).round(life);
			} else 
				if (b instanceof Hotel){
					((Hotel) b).round(life);
				} else 
					if (b instanceof Infrastructure){
						((Infrastructure) b).round(life);
					}

		}
		/** Lebensqualit�t berechnen
		 * ruft f�r jedes Bauwerk der Ebene die Methode round(...) mit diesem Wert auf	*/
	}

	/**  Zeichnet die Bauwerke auf den einzelnen Slots*/
	public String drawSlots(){
		String cain = new String();

		for(Building b : building){
			cain += b.drawing();
		}
		System.out.print(cain);	
		return cain;
	}

	/** Gibt Informationen der Ebene aus (Anz Einwohner, Lebenquali, Einnahmen und Ausgaben) */
	public String drawInfo(){
		int pop = getPopulation();
		int life = getLifequality();
		int income = getIncome();
		int expen  = getExpenditure();
		int profit = income - expen;
		
		String s = " Einwohner: " + pop + " Einnahmen: " + income + " Kosten: " + expen + "Profit" + profit + " Lebenqualit�t: " + life ;
		System.out.print( s + "\n" );
		return s;
	}
	
	/**Soll f�r alle Ebenen einen String ausgeben mit der GesammtWohnerzahl etc*/
	public String masterDrawInfo(){
		int pop  = 0;
		int life = 0;
		int income = 0;
		int expen  = 0;
		int profit = 0;
		
		for(Building b : building){
			pop 	+= b.getPopulation();
			life 	+= b.getLifequality();
			income 	+= b.getIncome();
			expen 	+= b.getExpenditure();
			profit	+= income - expen;
		}
		
		String s = " Einwohner: " + pop + " Einnahmen: " + income + " Kosten: " + expen + "Profit" + profit + " Lebenqualit�t: " + life ;
		System.out.print( s + "\n" );
		return s;
	}

	/** Anzahl der Einwohner der Ebene */
	public int getPopulation(){
		int pop = 0;

		for (Building b : building){

			if ( b instanceof Apartment ) {
				/** Springt in Klasse: Apartment Methode: getIncome() */
				pop += ((Apartment) b).getPopulation();
			} else if ( b instanceof Hotel ) {
				/** Springt in Klasse: Hotel Methode: getPoplutaion() */
				pop += ((Hotel) b).getPopulation();
			} else {
				/** Springt in Klasse: Building Methode: getIncome() */
				pop += b.getPopulation();
			}
		}
		/** Anzahl der Einahmen der Ebene pro Spielrunde */
		return pop;

	}

	/** Anzahl der Einahmen der Ebene pro Spielrunde */
	public int getIncome(){
		int income = 0;

		for (Building b : building){

			if ( b instanceof Apartment ) {
				/** Springt in Klasse: Apartment Methode: getIncome()*/
				income +=  b.getIncome();
			} else if ( b instanceof Hotel ) {
				/** Springt in Klasse: Apartment Methode: getIncome()*/
				income += ((Hotel) b).getIncome();
			}else {
				if (b instanceof Infrastructure){
					/** Springt in Klasse: Building Methode: getIncome()*/
					income +=  b.getIncome();
				}
				else
					income += b.getIncome();
			} 

		}

		return income;
	}

	/** Anzahl der Ausgaben der Ebene pro Spielrunde */
	public int getExpenditure(){
		int expen = 0;

		/** f�r jedes Bauwerk in einer Ebene 
		 * in Klasse: City Methode: round() f�r alle Ebenen*/
		for (Building b : building){

			if ( b instanceof Apartment ) {
				/** Springt in Klasse: Apartment Methode: getIncome() */
				expen += b.getExpenditure();
			} else if ( b instanceof Hotel) {
				/** Springt in Klasse: Hotel Methode: getIncome() */
				expen += b.getExpenditure();
			}
			else 
				expen += b.getExpenditure();
		}
		/** Anzahl der Einahmen der Ebene pro Spielrunde*/
		return expen;

	}

	/** Lebensquali innerhalb der Ebene */
	public int getLifequality(){
		int life = 0;

		for (Building b : building){
			if ( b instanceof Infrastructure ) {
				/** Springt in Klasse: Apartment Methode: getLifequality() */
				life += ((Infrastructure) b).getLifequality();
			} 
			else {
				/** Springt in Klasse: Building Methode: getLifequality() */
				life += b.getLifequality();
			}
		}
		return life;
	}
}
