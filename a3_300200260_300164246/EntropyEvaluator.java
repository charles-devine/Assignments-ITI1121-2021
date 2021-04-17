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
		// Loop over every partition
		// In each partition, the last attribute will be of interest
		// In each partition count how many Yes's and No's there are
		// Calculate weighted entropy for each partition
		// Add up all the weighted entropies for each partition

		if (partitions == null || partitions.length == 0) {
			return -1.0;
		}

		double weightedAverageSum = 0.0;
		double entropy;
		double prob1; // probability that you get value 1
		double prob2; // probability that you don't get value 1 ( you get value 2)
		double numVal1; // number of rows that have value 1
		double numVal2; // number of rows that have value 2
		double numElements = 0.0; // total number of elements
		String status = ""; // will take the first value it gets (eg. either a yes or no)
		int attributesLen = partitions[0].numAttributes;

		for (int i = 0; i < partitions.length; i++) {
			numElements += partitions[i].numRows;
		}

		for (int i = 0; i < partitions.length; i++) {
			entropy = 0.0;
			numVal1 = 0.0;
			numVal2 = 0.0;
			if (partitions[i].numRows > 0) {
				if (status.isEmpty()) {
					status = partitions[i].getValueAt(0, attributesLen - 1);
				}
				for (int r = 0; r < partitions[i].numRows; r++) {
					if (partitions[i].getValueAt(r, attributesLen - 1).equals(status)) {
						numVal1++;
					} else {
						numVal2++;
					}
				}

				prob1 = numVal1 / (numVal1 + numVal2);
				prob2 = numVal2 / (numVal1 + numVal2);
				if ((prob1 == 1.0 && prob2 == 0.0) || (prob1 == 0.0 && prob2 == 1.0)) {
					entropy = 0;
				} else {
					entropy = -prob1 * EntropyEvaluator.log2(prob1) - prob2 * EntropyEvaluator.log2(prob2);
				}
				weightedAverageSum += ((numVal1 + numVal2) / numElements) * entropy;
			}
		}
		return weightedAverageSum;
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
