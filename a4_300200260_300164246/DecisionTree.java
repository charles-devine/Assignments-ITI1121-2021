
/**
 * This class enables the construction of a decision tree
 * 
 * @author Mehrdad Sabetzadeh, University of Ottawa
 * @author Guy-Vincent Jourdan, University of Ottawa
 *
 */

public class DecisionTree {

	private static class Node<E> {
		E data;
		Node<E>[] children;

		Node(E data) {
			this.data = data;
		}
	}

	Node<VirtualDataSet> root;

	/**
	 * @param data is the training set (instance of ActualDataSet) over which a
	 *             decision tree is to be built
	 */
	public DecisionTree(ActualDataSet data) {
		root = new Node<VirtualDataSet>(data.toVirtual());
		build(root);
	}

	/**
	 * The recursive tree building function
	 * 
	 * @param node is the tree node for which a (sub)tree is to be built
	 */
	@SuppressWarnings("unchecked")
	private void build(Node<VirtualDataSet> node) {

		// check edge cases
		if (node == null) {
			throw new NullPointerException("Null parameter");
		} else if (node.data == null) {
			throw new IllegalStateException("Empty dataset");
		} else if (node.data.numAttributes < 1) {
			throw new IllegalStateException("No attributes exist");
		} else if (node.data.numRows < 1) {
			throw new IllegalStateException("No datapoints exist");
		}

		int numOfAttr = node.data.numAttributes;
		Attribute lastAttribute = node.data.getAttribute(numOfAttr - 1);
		boolean anySplitLeft = false;
		// checks if node.data has only 1 attribute
		if (numOfAttr == 1) {
			return;
		}
		// checks if the last attribute has only 1 unique value
		if (lastAttribute.getValues().length == 1) {
			return;
		}
		// will check if all attributes (except the last) have 1 unique value
		// if they do nothing will be done in this method call
		for (int i = 0; i < numOfAttr - 1; i++) {
			Attribute currAttribute = node.data.getAttribute(i);
			if (currAttribute.getValues().length > 1) {
				anySplitLeft = true;
			}
		}
		if (!anySplitLeft) {
			return;
		}

		// recursive case
		GainInfoItem[] gainItems = InformationGainCalculator.calculateAndSortInformationGains(node.data);
		String a_maxName = gainItems[0].getAttributeName();
		Attribute a_max = node.data.getAttribute(a_maxName);

		VirtualDataSet[] partitions;
		if (a_max.getType() == AttributeType.NOMINAL) {
			partitions = node.data.partitionByNominallAttribute(node.data.getAttributeIndex(a_maxName));
		} else {
			String[] values = a_max.getValues();

			int index = -1;
			String splitAtVal = gainItems[0].getSplitAt();
			for (int i = 0; i < values.length; i++) {
				if (values[i].equals(splitAtVal)) {
					index = i;
					break;
				}
			}
			partitions = node.data.partitionByNumericAttribute(node.data.getAttributeIndex(a_maxName), index);
		}
		node.children = (Node<VirtualDataSet>[]) new Node[partitions.length];
		// System.out.println("------- NODE DATA ------- \n" + node.data);
		for (int i = 0; i < partitions.length; i++) {
			Node<VirtualDataSet> newElem = new Node<VirtualDataSet>(partitions[i]);
			node.children[i] = newElem;
			// System.out.println("-------CHILD #" + i + " ------- \n" +
			// node.children[i].data);
		}
		for (int i = 0; i < node.children.length; i++) {
			build(node.children[i]);
		}
	}

	@Override
	public String toString() {
		return toString(root, 0);
	}

	/**
	 * The recursive toString function
	 * 
	 * @param node        is the tree node for which an if-else representation is to
	 *                    be derived
	 * @param indentDepth is the number of indenting spaces to be added to the
	 *                    representation
	 * @return an if-else representation of node
	 */
	private String toString(Node<VirtualDataSet> node, int indentDepth) {
		StringBuffer result = new StringBuffer("");
		String nl = System.lineSeparator();

		int numOfAttr = node.data.numAttributes;
		Attribute lastAttribute = node.data.getAttribute(numOfAttr - 1);
		String[] values = lastAttribute.getValues();

		if (node.children == null) {
			result.append(createIndent(2 * indentDepth) + lastAttribute.getName() + " = " + values[0] + nl);
			return result.toString();
		} else {
			for (int i = 0; i < node.children.length; i++) {
				if (i == 0) {
					result.append(createIndent(2 * indentDepth) + "if " + "(" + (node.children[i]).data.getCondition()
							+ ") " + "{" + nl);
				} else {
					result.append(createIndent(2 * indentDepth) + "else if " + "("
							+ (node.children[i]).data.getCondition() + ") " + "{" + nl);
				}
				result.append(toString(node.children[i], indentDepth + 1));
				if (indentDepth == 0 && i == node.children.length - 1) {
					result.append(createIndent(2 * indentDepth) + "}");
				} else {
					result.append(createIndent(2 * indentDepth) + "}" + nl);
				}

			}
		}

		return result.toString();

	}

	/**
	 * @param indentDepth is the depth of the indentation
	 * @return a string containing indentDepth spaces; the returned string (composed
	 *         of only spaces) will be used as a prefix by the recursive toString
	 *         method
	 */
	private static String createIndent(int indentDepth) {
		if (indentDepth <= 0) {
			throw new IllegalArgumentException("param is less or equal to 0");
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < indentDepth; i++) {
			buffer.append(' ');
		}
		return buffer.toString();
	}

	public static void main(String[] args) throws Exception {

		StudentInfo.display();

		if (args == null || args.length == 0) {
			System.out.println("Expected a file name as argument!");
			System.out.println("Usage: java DecisionTree <file name>");
			return;
		}

		String strFilename = args[0];

		ActualDataSet data = new ActualDataSet(new CSVReader(strFilename));

		DecisionTree dtree = new DecisionTree(data);

		System.out.println(dtree);
	}
}