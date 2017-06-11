package gameUC;

import java.util.ArrayList;

public class Level {

	ArrayList<Building> building;
	private int freeSlots;

	/** Konstruktor der die Anzahl der Freien Slots festlegt*/
	Level(int numFreeSlots){
		freeSlots = numFreeSlots;
		building = new ArrayList<Building>();
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
				if (b instanceof Infrastructure){
				((Infrastructure) b).round(life);
				
			}
			
		}
		/** Lebensqualit�t berechnen
		* ruft f�r jedes Bauwerk der Ebene die Methode round(...) mit diesem Wert auf	*/
	}

	/**  Zeichnet die Bauwerke auf den einzelnen Slots*/
	public void drawSlots(){
		String cain = new String();

		for(Building b : building){
			cain += b.drawing();
		}
		System.out.print(cain);	
	}

	/** Gibt Informationen der Ebene aus (Anz Einwohner, Lebenquali, Einnahmen und Ausgaben) */
	public void drawInfo(){
		int pop = getPopulation();
		int life = getLifequality();
		int income = getIncome();
		int expen  = getExpenditure();

		System.out.print(" Einwohner: " + pop + " Einnahmen: " + income + " Kosten: " + expen +  " Lebenqualit�t: " + life + "\n" );
	}

	/** Anzahl der Einwohner der Ebene */
	public int getPopulation(){
		int pop = 0;

		for (Building b : building){

			if ( b instanceof Apartment ) {
				/** Springt in Klasse: Apartment Methode: getIncome() */
				pop += ((Apartment) b).getPopulation();
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
				income += ((Apartment) b).getIncome();
			} 
				/** Springt in Klasse: Building Methode: getIncome()*/
				income += b.getIncome();
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
			}
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