package queries;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import dbase.RelationHolder;

public abstract class Operation {
	
	/**This method will make a query table from the Operation (node) given,
	 * down to the bottom.
	 * @param node The node to make the table from.
	 * @return A table.
	 */
	public static String generateQueryTable(final Operation node) {
		
		String table = "";
		
		//Start with the tableOne
		if (node.getTableOne() != null) {
			table += generateQueryTable(node.getTableOne());
		} if (node.getTableTwo() != null) {
			table += generateQueryTable(node.getTableTwo());
		}
		
		table += node.toString();
		
		return table;
	}
	
	/**This method will make an Operation from the literal represenation
	 * of that operation.
	 * @param operation The literal represenation of the operation.
	 * @return The Operation formed from the literal.
	 */
	public static Operation makeOperation(final String operation) {
		
		//Remove the spaces, open parens and make this thing upper case
		String upperCase = operation.toUpperCase();
		String noParens = upperCase.replace("(", " ");
		noParens = noParens.trim();
		
		//See what kind of operation this is
		//String [] split = noParens.split("\"");
		String [] split = noParens.split(" ");
		String first = split[0];
	
		//System.out.println("OPERATION: " + split[0]);
		if (first.equalsIgnoreCase(QueryParser.PROJECT)) {
			return new Project(upperCase);
		} else if (first.equalsIgnoreCase(QueryParser.CROSSJOIN)) {
			return new CrossJoin(upperCase);
		} else if (first.equalsIgnoreCase(QueryParser.SELECT)) {
			return new Select(upperCase);
		} else {
			return new TableOperation(upperCase);
		}
	}
	
	protected int executionOrder;
	
	protected Operation parent;
	
	protected int queryID;
	
	protected int resultTableID;
	
	/**The Operation from whence this one gets its data, a child.*/
	protected Operation tableOne;
	
	protected int tableOneAccess;
	
	protected Operation tableTwo;
	
	protected int tableTwoAccess;
	
	protected String type;

	public abstract long calculateCost();
	
	/**This does something I don't understand.
	 * @return Stuff.
	 */
	public Enumeration children() {
		return null;
	}
	
	/**
	 * Does this Operation have the attribute?
	 * @param att the attribute we're testing for
	 * @return true if the Operation's result table, or one of its children, contain the attribute
	 */
	 public boolean containsAttribute(String att){
		if (tableTwo!= null){
			return (tableOne.containsAttribute(att) || tableTwo.containsAttribute(att));
		} else if (tableOne != null){
			return (tableOne.containsAttribute(att));
		} else {
			return (RelationHolder.getRelationHolder().getRelation(resultTableID).getAttributeByName(att) != null);
		}
	}
	
	
	/**
	 * Executes the operation
	 * @return true if it executes properly
	 */
	public boolean execute(){
		return false;
	}	
	
	/**This method tells the operation to generate the temporary table that
	  * defines it's results.
	  */
	 public abstract void generateTemporaryTable();

	/**
	 * 
	 * @return its attributes
	 * 
	 */public abstract ArrayList < String > getAttributes();


	/**Gets a child at an index.  Don't know how that works.
	 * @param index Index of the child.
	 * @return nothing.
	 */
	public TreeNode getChildAt(final int index) {
		return null;
	}
	
	public abstract int getChildCount();


	/**This method returns the value of executionOrder.
	 * @return the executionOrder
	 */
	public int getExecutionOrder() {
		return executionOrder;
	}
	
	/**The index of a Child.  Don't know how that works.
	 * @param child The child to find the index of
	 * @return nothing.
	 */
	public int getIndex(final TreeNode child) {
		return 0;
	}
	
	


	/**This returns the parent of this Select, probably another operation, but
	 * return it as a <code>TreeNode</code> because of the interface.
	 * @return The Operation that owns this one.
	 */
	public Operation getParent() {
		return this.parent;
	}


	/**This method will get the attributes required by operations that 
	  * exists higher up in the tree.
	  * @return A list of attributes from higher up in the tree.
	  */
	 public abstract ArrayList < String > getParentAttributes();


	/**This method returns the post order represenation of this node an
	  * those below it.
	  * @return The tree below this in post-order.
	  */
	 public ArrayList < Operation > getPostOrder() {
		 
		 ArrayList < Operation > postOrder = new ArrayList < Operation > ();
		 
		 //Get the left and right sub-trees in post-order
		 if (tableOne != null) {
			 postOrder.addAll(tableOne.getPostOrder());
		 }
		 if (tableTwo != null) {
			 postOrder.addAll(tableTwo.getPostOrder());
		 }
		 
		 //Add this after the sub trees have been added.
		 postOrder.add(this);
		 
		 return postOrder;
	 }


	/**This method returns the value of queryID.
	 * @return the queryID
	 */
	public int getQueryID() {
		return queryID;
	}


	public abstract ArrayList < String > getRelations();


	/**This method returns the value of resultTableID.
	 * @return the resultTableID
	 */
	public int getResultTableID() {
		return resultTableID;
	}


	/**This method returns the value of tableOne.
	 * @return the tableOne
	 */
	public Operation getTableOne() {
		return tableOne;
	}


	/**This method returns the value of tableOneAccess.
	 * @return the tableOneAccess
	 */
	public int getTableOneAccess() {
		return tableOneAccess;
	}


	/**This method returns the value of tableTwo.
	 * @return the tableTwo
	 */
	public Operation getTableTwo() {
		return tableTwo;
	}
	
	public abstract ArrayList < String > getTreeAttributes();
	
	/**Call to get all of the SimpleConditions at or below this node 
	 * in the tree.
	 * @return The SimpleConditions at or below this Operation.
	 */
	public abstract ArrayList < SimpleCondition > getTreeConditions();


	/**This method returns the value of type.
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**This method will set the value of executionOrder.
	 * @param newExecutionOrder The new value of executionOrder.
	 */
	public void setExecutionOrder(final int newExecutionOrder) {
		this.executionOrder = newExecutionOrder;
	}

	/**This method will set the value of parent.
	 * @param newParent The new value of parent.
	 */
	public void setParent(final Operation newParent) {
		this.parent = newParent;
	}

	/**This method will set the value of queryID.
	 * @param newQueryID The new value of queryID.
	 */
	public void setQueryID(final int newQueryID) {
		this.queryID = newQueryID;
	}
	
	/**This method will set the value of resultTableID.
	 * @param newResultTableID The new value of resultTableID.
	 */
	public void setResultTableID(final int newResultTableID) {
		this.resultTableID = newResultTableID;
	}
	
	/**This method will set the value of tableOne.
	 * @param newTableOne The new value of tableOne.
	 */
	public void setTableOne(final Operation newTableOne) {
		this.tableOne = newTableOne;
	}
	
	/**This method will set the value of tableOneAccess.
	 * @param newTableOneAccess The new value of tableOneAccess.
	 */
	public void setTableOneAccess(final int newTableOneAccess) {
		this.tableOneAccess = newTableOneAccess;
	}
	 
	 /**This method will set the value of tableTwo.
	 * @param newTableTwo The new value of tableTwo.
	 */
	public void setTableTwo(final Operation newTableTwo) {
		this.tableTwo = newTableTwo;
	}
	 
	 /**
	 * Setter
	 * @param newType what we're setting it to
	 */public void setType(final String newType) {
		this.type = newType;
	}
	 
	 /**
	 * How many unique vals does this attribute have?
	 * @param att the attribute name we are checking
	 * @return the # of unique vals
	 */public long uniqueVals(String att){
		if (tableOne != null){
			if (tableTwo != null){  //binary?
				
				return tableOne.uniqueVals(att);
			} else {
				return (tableTwo.uniqueVals(att)*tableOne.uniqueVals(att));
			}
			
		} else{
			return 1;
		}
	}
}
