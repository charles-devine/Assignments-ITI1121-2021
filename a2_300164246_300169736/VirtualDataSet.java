// You are allowed to use LinkedList or other Collection classes in A2 and A3
import java.util.LinkedList;

/**
 * This class is used for representing a virtual dataset, that is, a dataset
 * that is a view over an actual dataset. A virtual dataset has no data matrix
 * of its own.
 * 
 * @author Mehrdad Sabetzadeh, University of Ottawa
 * @author Guy-Vincent Jourdan, University of Ottawa
 *
 */
public class VirtualDataSet extends DataSet {

	/**
	 * reference to the source dataset (instance of ActualDataSet)
	 */
	private ActualDataSet source;

	/**
	 * array of integers mapping the rows of this virtual dataset to the rows of its
	 * source (actual) dataset
	 */
	private int[] map;

	/**
	 * Constructor for VirtualDataSet. There are two important considerations here:
	 * (1) Make sure that you keep COPIES of the "rows" and "attributes" passed as
	 * formal parameters. Do not, for example, say this.map = rows. Instead, create
	 * a copy of rows before assigning that copy to this.map. (2) Prune the value
	 * sets of the attributes. Since a virtual dataset is only a subset of an actual
	 * dataset, it is likely that some or all of its attributes may have smaller
	 * value sets.
	 * 
	 * @param source     is the source dataset (always an instance of ActualDataSet)
	 * @param rows       is the set of rows from the source dataset that belong to
	 *                   this virtual dataset
	 * @param attributes is the set of attributes belonging to this virtual dataset.
	 *                   IMPORTANT: you need to recalculate the unique value sets
	 *                   for these attributes according to the rows. Why? Because
	 *                   this virtual set is only a subset of the source dataset and
	 *                   its attributes potentially have fewer unique values.
	 */
	public VirtualDataSet(ActualDataSet source, int[] rows, Attribute[] attributes) {
		// WRITE YOUR CODE HERE!

		// initializer 

		this.source = source; 
		this.map = rows.clone(); 

		this.numAttributes = attributes.length; 
		this.numRows = rows.length; 

		// create new Attribute array

		Attribute[] newAttribute = new Attribute[attributes.length]; 

		// Append copy of attribtues to the new attribute array

		for (int i = 0; i < attributes.length; i++) { 
			Attribute attributeArray = attributes[i].clone(); 
			newAttribute[i] = attributeArray; 
		}

		// initialize before the 2nd loop so it doesnt return null
		
		this.attributes = newAttribute; 

		// 2nd loop to get the values (from the index matrix) and 
		// set them through the uniqueAttribute method to retrieve 
		// solely the unique values of that specific index (ex: all the values for sunny)

		for (int j = 0; j < newAttribute.length; j++) { 
			String[] attributeValues = getUniqueAttributeValues(j); 
			newAttribute[j].replaceValues(attributeValues); 
			this.attributes[j] = newAttribute[j]; 
		}
	}

	/**
	 * String representation of the virtual dataset.
	 */
	@Override
	public String toString() {
		// WRITE YOUR CODE HERE!

		String tmp = "";

		String datasetString = "Virtual dataset with " + attributes.length + " attribute(s) and " + this.map.length + " row(s)\n";
		String sourceString = " - Dataset is a view over " + source.getSourceId() + "\n";
		String mapString = " - Row indices in this dataset (w.r.t. its source dataset): " + Util.intArrayToString(this.map) + "\n";
		tmp += datasetString + sourceString + mapString + super.toString(); 

		return tmp;
	}

	/**
	 * Implementation of DataSet's getValueAt abstract method for virtual datasets.
	 * Hint: You need to call source.getValueAt(...). What you need to figure out is
	 * with what parameter values that method needs to be called.
	 */
	public String getValueAt(int row, int attributeIndex) {
		// WRITE YOUR CODE HERE!

		return source.getValueAt(map[row], attributes[attributeIndex].getAbsoluteIndex()); 
	}

	/**
	 * @return reference to source dataset
	 */
	public ActualDataSet getSourceDataSet() {
		// WRITE YOUR CODE HERE!
		return this.source; 
	}


	/**
	 * This method splits the virtual dataset over a nominal attribute. This process
	 * has been discussed and exemplified in detail in the assignment description.
	 * 
	 * @param attributeIndex is the index of the nominal attribute over which we
	 *                       want to split.
	 * @return a set (array) of partitions resulting from the split. The partitions
	 *         will no longer contain the attribute over which we performed the
	 *         split.
	 */
	public VirtualDataSet[] partitionByNominallAttribute(int attributeIndex) {
		// WRITE YOUR CODE HERE!
		
		LinkedList<String> uniqueAttributeNames = new LinkedList<>();
		LinkedList<LinkedList<Integer>> uniqueAttributeIndex = new LinkedList<>(); 

		// uniqueAttributeNames = ["sunny", "overcast", "rainy"]
		// uniqueAttributeIndex = [[0, 1, 7, 8, 10], [2, 6, 11, 12], [3, 4, 5, 9, 13]]

		// loop that appends sunny overcast and rainy to the first linked list 
		// as well as creates the 2d linked list of size 3

		for (int n = 0; n < attributes[attributeIndex].getValues().length; n++) { 
			uniqueAttributeNames.add(attributes[attributeIndex].getValues()[n]); 
			uniqueAttributeIndex.add(new LinkedList<>());
		} 

		// loop that appends the specific index to each linked list within the 2d linked list

		for (int i = 0; i < numRows; i++) { 
			int var = uniqueAttributeNames.indexOf(getValueAt(i, attributeIndex)); 
			uniqueAttributeIndex.get(var).add(map[i]); 
		}

		// this handles the removal of the attribute line of the called attributeIndex (ex: "outlook", {"sunny", "overcast", "rainy"} in this case

		int tmp = 0; 
		Attribute[] arr = new Attribute[attributes.length - 1]; 
        for (int k = 0; k < numAttributes; k++) { 
            if (!attributes[k].getName().equals(attributes[attributeIndex].getName()) ) { 
                arr[tmp++] = attributes[k].clone(); 
            }
        }

		// create a new virtual dataset array of size 3

		VirtualDataSet[] something= new VirtualDataSet[attributes[attributeIndex].getValues().length];

		// loop that converts our linked list to an int array
		// it will then create another VirtualDataSet for each partition
		// finally it appends each created partition to the outer main VirtualDataSet
		// returns the VirtualDataSet Array with 3 VirtualDataSets
		
		for (int j = 0; j < attributes[attributeIndex].getValues().length; j++) { 

			int[] array = uniqueAttributeIndex.get(j).stream().mapToInt(i->i).toArray();

			// inspiration : https://www.geeksforgeeks.org/stream-maptoint-java-examples/

			VirtualDataSet something2 = new VirtualDataSet(this.source, array, arr); 
			
			something[j] = something2; 

		} 

		return something;  

	}

	/**
	 * This method splits the virtual dataset over a given numeric attribute at a
	 * specific value from the value set of that attribute. This process has been
	 * discussed and exemplified in detail in the assignment description.
	 * 
	 * @param attributeIndex is the index of the numeric attribute over which we
	 *                       want to split.
	 * @param valueIndex     is the index of the value (in the value set of the
	 *                       attribute of interest) to use for splitting
	 * @return a pair of partitions (VirtualDataSet array of length two) resulting
	 *         from the two-way split. Note that the partitions will retain the
	 *         attribute over which we perform the split. This is in contrast to
	 *         splitting over a nominal, where the split attribute disappears from
	 *         the partitions.
	 */
	public VirtualDataSet[] partitionByNumericAttribute(int attributeIndex, int valueIndex) {
        // WRITE YOUR CODE HERE!

		// Initialize 2 linkedlists, one for low values the other for high

        LinkedList<Integer> lowUniqueValueAttributeNames = new LinkedList<>();
        LinkedList<Integer> highUniqueValueAttributeNames = new LinkedList<>();

		// the value at which we will do the split

        String splitValue = attributes[attributeIndex].getValues()[valueIndex];

		// for loop with conditional statement that will append specifically each value to a higher or lower list
	
        for (int i = 0; i < numRows; i++) { 
            if ((Double.parseDouble(getValueAt(i, attributeIndex)) > Double.parseDouble(splitValue))) { 
                highUniqueValueAttributeNames.add(i); 
            } else { 
                lowUniqueValueAttributeNames.add(i);
            }
        }

		// create a new VirtualDataSet array of size 2

        VirtualDataSet[] newArr = new VirtualDataSet[2]; 

		// convert your lists to int arrays

        int[] lowRows = lowUniqueValueAttributeNames.stream().mapToInt(i->i).toArray();
        int[] highRows = highUniqueValueAttributeNames.stream().mapToInt(i->i).toArray();

		// https://www.geeksforgeeks.org/stream-maptoint-java-examples/

		// append the indexes of the VritualDataSet array to the low one first and then the high one 

        newArr[0] = new VirtualDataSet(this.source, lowRows, this.attributes);
        newArr[1] = new VirtualDataSet(this.source, highRows, this.attributes);

		// return the VirtualDataSet array

        return newArr;

    }

	// main

	public static void main(String[] args) throws Exception {

		StudentInfo.display();

		System.out.println("============================================");
		System.out.println("THE WEATHER-NOMINAL DATASET:");
		System.out.println();

		ActualDataSet figure5Actual = new ActualDataSet(new CSVReader("weather-nominal.csv"));

		System.out.println(figure5Actual);

		VirtualDataSet figure5Virtual = figure5Actual.toVirtual();

		System.out.println("JAVA IMPLEMENTATION OF THE SPLIT IN FIGURE 5:");
		System.out.println();

		VirtualDataSet[] figure5Partitions = figure5Virtual
				.partitionByNominallAttribute(figure5Virtual.getAttributeIndex("outlook"));

		for (int i = 0; i < figure5Partitions.length; i++)
			System.out.println("Partition " + i + ": " + figure5Partitions[i]);

		System.out.println("============================================");
		System.out.println("THE WEATHER-NUMERIC DATASET:");
		System.out.println();

		ActualDataSet figure9Actual = new ActualDataSet(new CSVReader("weather-numeric.csv"));

		System.out.println(figure9Actual);

		VirtualDataSet figure9Virtual = figure9Actual.toVirtual();

		// Now let's figure out what is the index for humidity in figure9Virtual and
		// what is the index for "80" in the value set of humidity!

		int indexForHumidity = figure9Virtual.getAttributeIndex("humidity");

		Attribute humidity = figure9Virtual.getAttribute(indexForHumidity);

		String[] values = humidity.getValues();

		int indexFor80 = -1;

		for (int i = 0; i < values.length; i++) {
			if (values[i].equals("80")) {
				indexFor80 = i;
				break;
			}
		}

		if (indexFor80 == -1) {
			System.out.println("Houston, we have a problem!");
			return;
		}

		VirtualDataSet[] figure9Partitions = figure9Virtual.partitionByNumericAttribute(indexForHumidity, indexFor80);

		System.out.println("JAVA IMPLEMENTATION OF THE SPLIT IN FIGURE 9:");
		System.out.println();

		for (int i = 0; i < figure9Partitions.length; i++)
			System.out.println("Partition " + i + ": " + figure9Partitions[i]);

	}
}