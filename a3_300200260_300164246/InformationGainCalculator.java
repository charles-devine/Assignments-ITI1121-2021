import org.w3c.dom.Attr;

/**
 * This class enables the calculation and sorting of information gain values
 * 
 * @author Mehrdad Sabetzadeh, University of Ottawa
 * @author Guy-Vincent Jourdan, University of Ottawa
 *
 */
public class InformationGainCalculator {

	/**
	 * @param dataset is the dataset whose attributes we want to analyze and sort
	 *                according to information gain
	 * @return an array of GainInfoItem instances sorted in descending order of gain
	 *         value
	 */
	public static GainInfoItem[] calculateAndSortInformationGains(VirtualDataSet dataset) {
		// WRITE YOUR CODE HERE!

		// loop over all the attributes
		// perform splits for each attribute
		// calculate entropy for each partition
		// return: an array of gains sorted in decreasing order
		double gainValue = 0.0;
		GainInfoItem[] gainsArr;
		DataSet[] partitions;
		Attribute[] attributes = dataset.attributes;
		double datasetEntropy;

		partitions = new DataSet[1];
		partitions[0] = dataset;
		datasetEntropy = EntropyEvaluator.evaluate(partitions);
		gainsArr = new GainInfoItem[dataset.numAttributes - 1];

		for (int i = 0; i < dataset.numAttributes - 1; i++) {
			if (attributes[i].getType() == AttributeType.NOMINAL) {
				partitions = dataset.partitionByNominallAttribute(i);
				gainValue = datasetEntropy - EntropyEvaluator.evaluate(partitions);
				gainsArr[i] = new GainInfoItem(attributes[i].getName(), AttributeType.NOMINAL, gainValue, null);

			} else {
				gainsArr[i] = numericAttributeGain(i, dataset, datasetEntropy);
			}
		}
		GainInfoItem.reverseSort(gainsArr);
		return gainsArr;
	}

	private static GainInfoItem numericAttributeGain(int attributeIndex, VirtualDataSet dataset,
			double datasetEntropy) {

		double gainValue = 0.0;
		GainInfoItem highestGain = null;
		DataSet[] partitions;
		Attribute[] attributes = dataset.attributes;

		for (int j = 0; j < attributes[attributeIndex].getValues().length; j++) {

			partitions = dataset.partitionByNumericAttribute(attributeIndex, j);

			gainValue = datasetEntropy - EntropyEvaluator.evaluate(partitions);

			if (highestGain == null || highestGain.getGainValue() <= gainValue) {
				highestGain = new GainInfoItem(attributes[attributeIndex].getName(), AttributeType.NUMERIC, gainValue,
						"" + attributes[attributeIndex].getValues()[j]);
			}

		}

		return highestGain;

	}

	public static void main(String[] args) throws Exception {

		StudentInfo.display();

		if (args == null || args.length == 0) {
			System.out.println("Expected a file name as argument!");
			System.out.println("Usage: java InformationGainCalculator <file name>");
			return;
		}

		String strFilename = args[0];

		ActualDataSet actual = new ActualDataSet(new CSVReader(strFilename));

		// System.out.println(actual);

		VirtualDataSet virtual = actual.toVirtual();

		// System.out.println(virtual);

		GainInfoItem[] items = calculateAndSortInformationGains(virtual);

		// Print out the output
		System.out.println(
				" *** items represent (attribute name, information gain) in descending order of gain value ***");
		System.out.println();

		for (int i = 0; i < items.length; i++) {
			System.out.println(items[i]);
		}
	}
}
