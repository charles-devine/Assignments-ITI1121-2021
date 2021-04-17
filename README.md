###### Assignments-ITI1121-2021

Site link : https://www.site.uottawa.ca/~gvj/Courses/ITI1121/index.html

# Assignment 1

## Learning objectives
```
• Edit, compile and run Java programs
• Utilize arrays to store information
• Apply basic object-oriented programming concepts
• Understand the university policies for academic integrity
```
# Introduction

This year, we are going to implement, through a succession of assignments, a simplified version of a useful machine learning technique, called decision tree classification. If
you are not familiar with decision trees and are curious to know what they are, you may wish to have a quick look at the following Wikipedia page: https://en.wikipedia.
org/wiki/Decision_tree_learning. For Assignment 1, however, you are not going to do anything that is specific to decision trees; you can complete Assignment 1 without any knowledge of decision trees! We will get to decision trees only in Assignments 2 and 3. If you find the above Wikipedia page overwhelming, fear not! As we go along, we will provide you with simple and accessible material to read on decision tree classification. Ultimately, the version of decision tree classification that you implement, despite still being extremely useful, has many of the complexities of the more advanced implementations removed (for example, handling “unknown” values in your training data). As far as the current assignment – Assignment 1 – is concerned, we have modest goals: we would like to read an input file, which will (in future assignments) constitute the training data for our learning algorithm, and perform some basic tasks that are prerequisites to virtually any type of machine learning. Specifically, you will be implementing the following tasks in Assignment 1:

• Task 1. Parsing comma-separated values (CSV) from a given data file and populating appropriate data structures in memory
• Task 2. Extracting certain summary data (metadata) about the characteristics of the input data; this metadata

will come handy for the construction of decision trees in future assignments. These two tasks are best illustrated with a simple example. Suppose we have a CSV file named weather.csv with the content shown in Figure 1. 1 The data is simply a table. The first (non-empty) row in the file provides the names of the table columns in a comma-separated format. Each column represents an attribute (also called a feature). The remaining (non-empty) rows are the datapoints. In our example, each datapoint is a historical observation about weather conditions (in terms of outlook, temperature in fahrenheit, humidity and wind), and whether it has been possible to “play” a certain tournament (for example, cricket) outside. What a machine learning algorithm can do here is to “learn from examples” and help decide / predict whether one can play a tournament on a given day according to the weather conditions on that day. Now, going backing to Task 1 and Task 2, below is what each of these tasks would do with the data in Figure 1
