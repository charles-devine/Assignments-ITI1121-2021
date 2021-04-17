/**
 * This class is used for representing an actual dataset, that is, a dataset
 * that holds a data matrix
 * 
 * @author Mehrdad Sabetzadeh, University of Ottawa
 * @author Guy-Vincent Jourdan, University of Ottawa
 *
 */
public class ActualDataSet extends DataSet {
	/**
	 * The data matrix
	 */
	private String[][] matrix;

	/**
	 * The source identifier for the data. When the data source is a file, sourceId
	 * will be the name and location of the source file
	 */
	private String dataSourceId;

	/**
	 * Constructor for ActualDataSet. In addition to initializing dataSourceId,
	 * numAttributes, numRows and matrix, the constructor needs to create an array of
	 * attributes (instance of the Attribute class) and initialize the "attributes"
	 * instance variable of DataSet.
	 * 
	 * 
	 * @param reader is the DataReader instance to read data from.
	 */
	public ActualDataSet(DataReader reader) {
		// WRITE YOUR CODE HERE!

		this.numRows = reader.getNumberOfDataRows(); 
		this.numAttributes = reader.getNumberOfColumns(); 

		this.dataSourceId = reader.getSourceId(); 
		this.matrix = reader.getData(); 

		String[] copyAttributeName = reader.getAttributeNames();
		this.attributes = new Attribute[this.numAttributes]; 

		// this loop creates the matrix of uniquevalues
		// as well as retrieves the names of the called attribute
		// as well as retrieves its type (through the condition)
		// and finally appends it to a new Attribute

		for (int i = 0; i < reader.getNumberOfColumns(); i++) { 

			String[] uniqueArray = getUniqueAttributeValues(i); 
			
			AttributeType valueType; 
			if (Util.isArrayNumeric(uniqueArray)) { 
				valueType = AttributeType.NUMERIC; 
			} else { 
				valueType = AttributeType.NOMINAL; 
			}

			Attribute resultArray = new Attribute(copyAttributeName[i], i, valueType, uniqueArray);
			attributes[i] = resultArray; 
		}
	}

	/**
	 * Implementation of DataSet's abstract getValueAt method for an actual dataset
	 */
	public String getValueAt(int row, int attributeIndex) {
		// WRITE YOUR CODE HERE!

		// checks for bounds on both row and attributeIndex
		
		if (row < 0 || row > (numRows - 1)) { 
			return null; 
		} 
		if (attributeIndex < 0 || attributeIndex > (numAttributes - 1)) { 
			return null; 
		}

		return matrix[row][attributeIndex];
	}

	/**
	 * @return the sourceId of the dataset.
	 */
	public String getSourceId() {
		// WRITE YOUR CODE HERE!
		return dataSourceId; 
	}

	/**
	 * Returns a virtual dataset over this (actual) dataset
	 * 
	 * @return a virtual dataset spanning the entire data in this (actual) dataset
	 */
	public VirtualDataSet toVirtual() {
		// WRITE YOUR CODE HERE!
		
		int[] newUniqueRows = new int[numRows]; 

		for (int i = 0; i < numRows; i++) { 
			newUniqueRows[i] = i;
		}

		return new VirtualDataSet(this, newUniqueRows, this.attributes); 
	}

	/**
	 * Override of toString() in DataSet
	 * 
	 * @return a string representation of this (actual) dataset.
	 */
	@Override
	public String toString() {
		// WRITE YOUR CODE HERE!
		
		//Remove the following line when this method has been implemented
		System.out.println("Actual dataset " + getSourceId() + " with " + getNumberOfAttributes() + " attribute(s) and " + getNumberOfDatapoints() + " row(s)");
		return super.toString(); 
	}
}