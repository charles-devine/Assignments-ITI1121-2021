###### Assignments-ITI1121-2021

Site link : https://www.site.uottawa.ca/~gvj/Courses/ITI1121/index.html

## Assignment 1

# Learning objectives
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
```
• Task 1. Parsing comma-separated values (CSV) from a given data file and populating appropriate data structures in memory
• Task 2. Extracting certain summary data (metadata) about the characteristics of the input data; this metadata
```
will come handy for the construction of decision trees in future assignments. These two tasks are best illustrated with a simple example. Suppose we have a CSV file named weather.csv with the content shown in Figure 1. 1 The data is simply a table. The first (non-empty) row in the file provides the names of the table columns in a comma-separated format. Each column represents an attribute (also called a feature). The remaining (non-empty) rows are the datapoints. In our example, each datapoint is a historical observation about weather conditions (in terms of outlook, temperature in fahrenheit, humidity and wind), and whether it has been possible to “play” a certain tournament (for example, cricket) outside. What a machine learning algorithm can do here is to “learn from examples” and help decide / predict whether one can play a tournament on a given day according to the weather conditions on that day. Now, going backing to Task 1 and Task 2, below is what each of these tasks would do with the data in Figure 1

## Assignment 2 & 3

# Learning objectives
```
• Inheritance
• Interfaces
• Abstract Methods
• Polymorphism
• Experimentation with Lists
```
# Introduction

In Assignments 2 and 3, we will take one step further towards building decision trees. Since the two assignments are closely related, we provide a combined description of the two. Towards the end of this description, we specify what needs be submitted for each of the two assignments. Please note that Assignment 2 and Assignment 3 have
different deadlines (February 26 and March 19, respectively). In these two assignments, we are going to consider all possible “splits” of the input data that we already read into a matrix (Assignment 1) and determine which split yields the best results. Before we explain what splitting means and how to measure the quality of a split, let us see an example of a decision tree and make our way from there. Consider the weather-nominal dataset shown in Figure 1. 

Our goal is to eventually (that is, in Assignment 4) be able to build decision trees like the one shown in Figure 2. A decision tree uses a tree model to explain possible consequences or provide predictions, given a set of known parameters. For instance, in our weather example, we want our decision tree to take outlook, temperature, humidity, and whether it is windy or not as parameters and predict the “class” attribute. This attribute indicates whether a certain sports tournament (say, football or tennis) is feasible to play, given the weather conditions of the day. Obviously, we want our prediction to be more accurate than a coin toss! For this, we need to train a model – in our context, a decision tree – based on the data that we have observed previously. In our weather example, the previously observed data would be what is shown in Figure 1. The reason why the last column of the data in Figure 1 is called “class” is because we are dealing with a classification problem, with the possible outcomes being yes or no. Since there are two outcomes only, the problem is a binary classification problem. In the assignments for this course, we are concerned exclusively with binary classification. Furthermore, we assume that the “class” attribute is always the last attribute, irrespective of what the attribute is actually named. For example, in the weather-numeric dataset, shown in Figure 3, the last attribute is named “play”. For this dataset, we take “play” (the last column) to have exactly the same role as “class”.

Semantically, the decision tree of Figure 2 is equivalent to the if-else block shown in Figure 4. The nice thing about our decision tree (and the corresponding if-else block) is that it is predictive and can project an outcome for weather conditions that have not been observed in the past. For example, our model has learned that “if the outlook is overcast, no matter what the other conditions are, we are good to play”. Interestingly, there are several combinations that have not been seen in historical observations. For example, the historical data does not tell us what would happen if the outlook is overcast, the temperature is hot, the humidity is normal, and it is windy. Indeed, if the outcome for all possible combinations were known, learning would be useless; all we would need to do was looking up the outcome in a table! In contrast, the model of Figure 2 makes an informed guess, without necessarily having seen the exact conditions int the past. This is the magic of machine learning: extrapolating from existing (training) data and projecting conclusions about situations that have not been seen before.

## Assignment 4

# Learning objectives
```
• Working with linked structures
• Working with generics
• Recursive programming
• Exception handling
```
# Introduction

We have already laid most of the groundwork for building decision trees and, at this point, we are only one wellcrafted (recursive) algorithm away from building these trees! The description for Assignment 4 is shorter than those for the previous assignments, since we have covered most of what we need in these previous assignment descriptions. The amount of coding involved in Assignment 4 is also going to be less than in the previous assignments. However, the smaller implementation does not mean that you should postpone it! Assignment 4 requires familiarity with both (simple) tree-based linked structures as well as recursive programming. These concepts need time to digest. We therefore ask that you start working on the assignment as soon as you receive this description. You will be implementing the following three tasks in Assignment 4. The tasks marked with a * will take more time to complete.
