package queries;

import java.util.ArrayList;

public class Project extends Operation {

	protected ArrayList < String > attributes;
	
	public long calculateCost() {
		return 1000;
	}
	
	/**This will create a new instance of Project.  It will find what to 
	 * project and from where from the statement passed.
	 * @param statement The literal of the project statement.
	 */
	public Project (final String statement) {
		
		setType(QueryParser.PROJECT);
		
		ArrayList < String > parts = QueryParser.parseStatementParts(statement);
		
		//TODO Find the list of attributes to project
		
		//Find the table its coming from
		tableOne = Operation.makeOperation(
			parts.get(QueryParser.PROJECT_FROM_INDEX));
	}
	
	public String toString() {
		
		String string = ""; 
		
		string += this.queryID + "\t";
		string += this.executionOrder + "\t";
		string += this.type + "\t";
		string += tableOneAccess +"\t\t";
		string += "\t";
		string += resultTableID;
		
		return string;
	}
	
	/**This method will return whether or not the Project allows children as
	 * per the <code>TreeNode</code> interface.
	 * @return <code><b>true</b></code> becase a Project must always 
	 * have children.
	 */
	public boolean getAllowsChildren() {
		return true;
	}
	
	/**This method will return the number of children that this Project has
	 * as per the TreeNode interface.
	 * @return The number of children of this node.
	 */
	public int getChildCount() {
		int childCount = 1 + tableOne.getChildCount();
		return childCount;
	}
	
	/**Says whether or not the Projection is a Leaf.  
	 * Always false, a Project is never a Leaf in a query tree.
	 * @return <code><b>false</b></code> because Project statements
	 * aren't leaves.
	 */
	public boolean isLeaf() {
		return false;
	}
	
}
