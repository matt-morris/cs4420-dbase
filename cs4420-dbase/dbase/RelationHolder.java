package dbase;

/**
 * @author gtg471h
 * 
 * This is a singleton class for holding the relation instances 
 * used by the different parts of the DBMS.
 *
 */

import java.util.*;

public class RelationHolder {
	
	/** The List of Relations */
	
	private ArrayList<Relation> relations;
	
	/** */
	private static RelationHolder me;
	
	
	/**
	 * Standard Constructor
	 *
	 */
	private RelationHolder() {
		if (relations == null)relations = new ArrayList<Relation>();
	}
	
	/**
	 * The static get method, alays used.
	 * @return The static RelationHolder.
	 */
	public synchronized static RelationHolder getRelationHolder() {
		if (me == null) {
			me = new RelationHolder();
			return me;
		} else {
			return me;
		}
	}
	
	/**
	 * Gets a relation based on the Internal relation ID as defined in RELATION_CATALOG
	 * @param ID The Internal ID
	 * @return The denoted Relation
	 */
	
	public Relation getRelation(long ID) {
		for (int i = 0; i < relations.size(); i++) {
			if (relations.get(i).getID() == ID) {
				return relations.get(i);
			}
		}
		//TODO Possibly add code to load this Relation if not already done, we'll see.
		return null;
	}
	
	/**
	 * Adds a relation to the list.
	 * @param newrelation The new Relation to add.
	 * @return A boolean of whether or not the Relation was successfully added.
	 */
	public boolean addRelation(Relation newrelation) {
		//System.out.println("Adding relation " + newrelation);
		return relations.add(newrelation);
	}
	
	/**
	 * This method is used by SystemCatalog to assign IDs
	 * @return the smallest unused relation ID
	 */
	public int getSmallestUnusedID(){
		int highest = 0;
		
		//Find the lowest value among all of the relations
		for (int index = 0; index < relations.size(); index++) {
			
			Relation currentRelation = relations.get(index);
			if (currentRelation.getID() > highest) {
				highest = currentRelation.getID();
			}
		}
		
		return highest + 1;
	}
	
	/**
	 * 
	 * @param name The name of the relation to get
	 * @return the relation ID, or -1 if relation not found
	 */
	public int getRelationByName(String name){
		for (int j=0; j<relations.size(); j++){
			if (relations.get(j).getName().equalsIgnoreCase(name)){
				return relations.get(j).getID();
			}
		}
		return -1;
	}
	
	public ArrayList<Relation> getRelations(){
		return relations;
	}
	
	public String toString(){
		return relations.toString();
	}
	
	

}
