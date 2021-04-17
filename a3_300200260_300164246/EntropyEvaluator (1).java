/**
 * This class enables calculating (weighted-average) entropy values for a set of
 * datasets
 * 
 * @author Mehrdad Sabetzadeh, University of Ottawa
 * @author Guy-Vincent Jourdan, University of Ottawa
 *
 */
public class EntropyEvaluator {

	/**
	 * A static method that calculates the weighted-average entropy of a given set
	 * (array) of datasets. The assignment description provides a detailed
	 * explanation of this calculation. In particular, note that all logarithms are
	 * to base 2. For your convenience, we provide a log2 method. You can use this
	 * method wherever you need to take logarithms in this assignment.
	 * 
	 * @param partitions is the array of datasets to compute the entropy of
	 * @return Shannon's logarithmic entropy (to base 2) for the partitions
	 */
	public static double evaluate(DataSet[] partitions) {

        double final_sum = 0.0; 

		//checks to see if the array(partitions) given to us is empty or if its length is a invalid length
        if (partitions == null || partitions.length == 0) { 
            return -1.0; 
        }

        double totalpartlen = 0.0; 

		//gets the total length of all of the partitions
        for (int i = 0; i < partitions.length; i++) { 
            totalpartlen += partitions[i].numRows; 
        }

        String tmp = ""; // used to get the first element to compare the (yes / no)

        for (int j = 0; j < partitions.length; j++) { 
            double num_entropy = 0.0; 
            double first_value = 0.0; 
            double last_value = 0.0; 

            if (partitions[j].numRows > 0) { 
                if (tmp.isEmpty()) { 
                    tmp = partitions[j].getValueAt(0, partitions[0].numAttributes - 1);
                }

                for (int k = 0; k < partitions[j].numRows; k++) { 
                    if (partitions[j].getValueAt(k, partitions[0].numAttributes - 1).equals(tmp)) { 
                        first_value++; 
                    } else { 
                        last_value++; 
                    }
                }
		
                if ((first_value / (first_value + last_value)) == 1.0 && (last_value / (first_value + last_value)) == 0.0 || ((first_value / (first_value + last_value)) == 0.0 && (last_value / (first_value + last_value)) == 1.0)) { 
                    num_entropy = 0.0; 
                } else { 
                    num_entropy = -((first_value / (first_value + last_value)) * EntropyEvaluator.log2(first_value / (first_value + last_value))) - ((last_value / (first_value + last_value)) * EntropyEvaluator.log2(last_value / (first_value + last_value)));
                }

                final_sum = final_sum + ((((first_value + last_value)) / totalpartlen) * num_entropy); 
            }
        }
        
        return final_sum; 

	}

	/**
	 * Calculate base-2 logarithm for a given number
	 * 
	 * @param x is the number to take the logarithm of
	 * @return base-2 logarithm for x
	 */
	public static double log2(double x) {
		return (Math.log(x) / Math.log(2));
	}
}
