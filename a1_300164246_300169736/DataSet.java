import java.io.File;
import java.util.Scanner;

/**
 * The class enables loading a dataset from a file (CSV format) and deriving
 * some important characteristics of the data
 * 
 * @author Mehrdad Sabetzadeh, University of Ottawa
 * @author Guy-Vincent Jourdan, University of Ottawa
 *
 */
public class DataSet {

	/**
	 * The delimiter that separates attribute names and attribute values
	 */
	private static final char DELIMITER = ',';
	
	/**
	 * Character allowing escape sequences containing the delimiter
	 */
	private static final char QUOTE_MARK = '\'';

	/** 
	 
	 * Instance variable for storing our regex expression

		Each parenthesis is a level of capturing group that try to capture the regex results in order. 
		In our case, the expression checks commas then it checks for quotes (QUOTE_MARK) in between ?= and $. 

		Terminology: 
		?= The begining (of sorts) that matches the group without matching itself 
		$ = End sign that matches the end of the string (or line, in our case the end of the QUOTE_MARK)
		^ = Start of string (or line)
		\ = Used to escape the following character (in our case QUOTE_MARK)
		QUOTE_MARK = \' incorporates the escape + the char we want to escape

		Background: 

		https://www.w3schools.com/java/java_regex.asp

		https://docs.microsoft.com/en-us/dotnet/standard/base-types/regular-expression-language-quick-reference

		https://docs.python.org/3/library/re.html

		
	 */
	private String regex_expression = ",(?=([^" + QUOTE_MARK + "]*" + QUOTE_MARK + "[^" + QUOTE_MARK + "]*" + QUOTE_MARK + ")*[^" + QUOTE_MARK + "]*$)";

	/**
	 * Instance variable for storing the number of attributes (columns)
	 */
	private int numColumns;

	/**
	 * Instance variable for storing the number of datapoints (data rows)
	 */
	private int numRows;

	/**
	 * Instance variable for storing attribute names
	 */
	private String[] attributeNames;

	/**
	 * Instance variable for storing datapoints
	 */
	private String[][] matrix;

	/**
	 * Constructs a dataset by loading a CSV file
	 * 
	 * @param strFilename is the name of the file
	 */
	public DataSet(String strFilename) throws Exception {

		// WRITE YOUR CODE HERE!

		calculateDimensions(strFilename);
		instantiateFromFile(strFilename); 
		
		//These lines just call their methods 

	}

	/**
	 * Returns the name of the attribute at a given column index
	 * 
	 * @param column is the column index
	 * @return attribute name at index (null if the index is out of range)
	 */
	public String getAttributeName(int column) {
		// WRITE YOUR CODE HERE!
		// Note: Remember to handle out-of-range values!
		
		// Remove the following null return after you implement this method
		
		String tmp = "";//declares an empty string

		if (attributeNames.length <= column) {
			tmp = null; // if the length of attributenames is less than or = to the column the temporary variable is set to null

		} else { 
			tmp = attributeNames[column].trim(); // otherwise the temp variable is set to the name of the attribute at the index the column is currently at 
			
		}

		return tmp; //returns the temporary variable

	}

	/**
	 * Returns the value of a given column for a given row (datapoint)
	 * 
	 * @param row    is the row (datapoint) index
	 * @param column is the column index
	 * @return the value of the attribute at column for the datapoint at row (null
	 *         if either row or column are out of range)
	 */
	public String getAttributeValue(int row, int column) {
		// WRITE YOUR CODE HERE!
		// Note: Remember to handle out-of-range values!
		
		// Remove the following null return after you implement this method

		String tmp = ""; //declares a temporary variable

		if (numRows <= row) { //if statement that sets the temporary variable to null when the number of rows is less than or =  to the current row number
			tmp = null;
		} 
		else if (numColumns <= column) { // if the first if statement is not true then it checks if the number of columns is less than or equal to the current column number
			tmp = null;
		}
		
		else { 
			tmp = matrix[row][column].trim(); //if the first two if/elseif statements are not true then it sets the temp variable to the number in a certain location then removes the whitespace with .trim()
		}

		return tmp; 

	}


	/**
	 * Returns the number of attributes
	 * 
	 * @return number of attributes
	 */
	public int getNumberOfAttributes() {
		return numColumns;
	}

	/**
	 * Returns the number of datapoints
	 * 
	 * @return number of datapoints
	 */
	public int getNumberOfDatapoints() {
		return numRows;
	}

	// done

	/**
	 * Returns a reference to an array containing the unique values that an
	 * attribute can assume in the dataset
	 * 
	 * @param attributeName is the name of the attribute whose unique values must be
	 *                      returned
	 * @return String[] reference to the unique values of the the attribute with the
	 *         given name
	 */
	public String[] getUniqueAttributeValues(String attributeName) {

		// WRITE YOUR CODE HERE!
		
		// Hint: You can first implement getUniqueAttributeValues(int column), below, 
		// and then make use of that private method here!
		
	    // Remove the following null return after you implement this method and
		// return an appropriate array reference instead

		for (int i = 0; i < attributeNames.length; i++) { 
			if (attributeName.equals(attributeNames[i])) { 
				return getUniqueAttributeValues(i);
			}
		}

		return null;
	}

	/**
	 * Returns a reference to an array containing the unique values that the
	 * attribute at a certain column can assume in the dataset
	 * 
	 * @param column is the index (staring from zero) for the attribute whose unique
	 *               values must be returned
	 * @return String[] reference to the unique values of the attribute at the given
	 *         column
	 */
	private String[] getUniqueAttributeValues(int column) {
    
        // WRITE YOUR CODE HERE!

        String[] tmp = new String[numRows];
        int num = 0;

        for (int i = 0; i < numRows; i++) { 
            boolean flag = false; 
            for (int j = 0; j < numRows; j++) { 
                if (matrix[i][column].equals(tmp[j])) { // loops through the number of rows (coicidentaly the size of the tmp array) and checks if the index is the same as the element in the matrix. 
                    flag = true; 
                    break; 
                }
            }

            if (!flag) { // if not flag then append the values to the array
                tmp[num] = matrix[i][column] ;
              	num++; 
            }
        }
     
        String[] newArray = new String[num];
        for(int i = 0; i < num; i++) { // loop that appends only the unique values to the correct-sized array. 
        	newArray[i] = tmp[i];
        }

        return newArray; 

    }
	
	/**
	 * Returns in the form of an explanatory string some important characteristics
	 * of the dataset. These characteristics are: the number of attributes, the
	 * number of datapoints and the unique values that each attribute can assume
	 * 
	 * @return String containing the characteristics (metadata)
	 */
	public String metadataToString() {

		// Hint: You can combine multiple lines by appending
		// a (platform-dependent) separator to the end of each line.
		// To obtain the (platform-dependent) separator, you can use 
		// the following command.
		String separator = System.getProperty("line.separator");

		// WRITE YOUR CODE HERE!
		
		// Hint: You need to call getUniqueAttributeValues() for
		// each attribute (via either attribute name or attribute column) and
		// then concatenate the string representations of the arrays returned by
		// getUniqueAttributeValues(). To get the string representations for 
		// these arrays, you can use the methods provided in the Util class.
		// For nominal attributes use: Util.nominalArrayToString()
		// For numeric attributes use: Util.numericArrayToString()

		// Remove the following null return after you implement this method	

		System.out.println("Number of attributes: " + numColumns);
		System.out.println("Number of datapoints: " + numRows);
		System.out.println();
		System.out.println("* * * Attribute value sets * * *");

		String clearedString = ""; 

		for (int i = 0; i < numColumns; i++) { // for loop checks to see if the attribute variables are numeric or nominal. 
			String[] attributes = getUniqueAttributeValues(attributeNames[i]); // declares the list 


			if (Util.isArrayNumeric(attributes)) { 
				clearedString += (i + 1) + ") " + attributeNames[i] + "(numeric): " + Util.numericArrayToString(attributes) + separator; 

			} else {
				clearedString += (i + 1) + ") " + attributeNames[i] + "(nominal): " + Util.nominalArrayToString(attributes) + separator;
			}
		}
		return clearedString;
	}

	// not done
	
	/**
	 * <b>main</b> of the application. The method first reads from the standard
	 * input the name of the CSV file to process. Next, it creates an instance of
	 * DataSet. Finally, it prints to the standard output the metadata of the
	 * instance of the DataSet just created.
	 * 
	 * @param args command lines parameters (not used in the body of the method)
	 */
	public static void main(String[] args) throws Exception {

		/* 
		First, it reads from the standard input the name of the CSV file
		to process. Next, it creates an instance of DataSet; the constructor 
		of DataSet will process the input file and populate attributeNames and 
		matrix (explained earlier). Finally, the main method prints to the 
		standard output the metadata of the instance of the DataSet that was 
		just created. Doing so requires calling the metadataToString() instance method 
		*/

        StudentInfo.display();

		System.out.print("Please enter the name of the CSV file to read: ");

		Scanner scanner = new Scanner(System.in);

		String strFilename = scanner.nextLine();

		DataSet dataset = new DataSet(strFilename);

		System.out.print(dataset.metadataToString());

	}

	// done

	/**
	 * This method should set the numColumns and numRows instance variables
	 * The method is incomplete; you need to complete it.
	 * @param strFilename is the name of the dataset file
	 */
	private void calculateDimensions(String strFilename) throws Exception {

		Scanner scanner = new Scanner(new File(strFilename));

		int tmp = 0;
		String str = scanner.nextLine();
		String[] strArray = str.split(regex_expression, -1);

		if (str.length() < 0) {
			scanner.nextLine();
		}
		else { 
			scanner.next();
		}

		while (scanner.hasNextLine()) { 
			scanner.nextLine();
			tmp += 1;

		} 

		numRows = tmp; 		
		numColumns = strArray.length;

		scanner.close();

	}

	/**
	 * This method should load the attribute names into the attributeNames
	 * instance variable and load the datapoints into the matrix instance variable.
	 * The method is incomplete; you need to complete it.
	 * @param strFilename is the name of the file to read
	 */
	private void instantiateFromFile(String strFilename) throws Exception {

		Scanner scanner = new Scanner(new File(strFilename));
		scanner.nextLine();

		int i = numRows; 
		int j = numColumns;
		int tmp = 0; 

		String[][] arrMatrix = new String[i][j];
		String[] element; 
		String[] attName;

		// This method utilizes a regular expression to sort csv files as well as replace missing values with the string "MISSING"

        while (scanner.hasNext()) { 
			element = scanner.nextLine().split(regex_expression, -1); 
			if (element.length > 1) { 

				for (int k = 0; k < element.length; k++) { 
					element[k] = element[k].strip(); // deals with large spaces (weather-with-spaces.csv)
					element[k] = element[k].replace("'", ""); 
					if (element[k].equals("")) {  // deals with missing values (missing-values.csv)
						element[k] = "MISSING";
					}
				}
				arrMatrix[tmp] = element; // this method appends those values to the matrix
				tmp++; 
			}
		}

		scanner.close();

		Scanner attScanner = new Scanner(new File(strFilename));
		String attStr = attScanner.nextLine();

		attName = attStr.split(regex_expression, -1); // immeditately sorts the first line using the same regex (for words in quotation marks)

		for (int l = 0; l < numColumns; l++) { 
			attName[l] = attName[l].strip();
			attName[l] = attName[l].replace("'", ""); 
		}

		attributeNames = attName; // private var
		matrix = arrMatrix;	// private var

		attScanner.close();
	
	}
	
}
	