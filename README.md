# Boggle
This project is to solve boggle boards.\
Boggle is a word game designed by Allan Turoff and distributed by Hasbro. It involves a board made up of 16 cubic dice, where each die has a letter printed on each of its 6 sides. At the beginning of the game, the 16 dice are shaken and randomly distributed into a 4-by-4 tray, with only the top sides of the dice visible. The players compete to accumulate points by building valid words from the dice, according to these rules:\
A valid word must be composed by following a sequence of adjacent dice—two dice are adjacent if they are horizontal, vertical, or diagonal neighbors.\
A valid word can use each die at most once.\
A valid word must contain at least 3 letters.\
A valid word must be in the dictionary (which typically does not contain proper nouns).\
\
It is implemented with DFS and trie.\
It features 1. 26 node Trie to save time (more memory); 2. DFS with cached nodes; 3. Skip Qx; 4. No set for words. Use UID to record when (if any) the word is output. 5. Record words in Trie, to avoid use StringBuilder.\
Week 4 assignment of Algorithms, Part II, by Robert Sedgewick in Princeton University.\
Score 100/100. Reference Ratio = 0.68.\
https://coursera.cs.princeton.edu/algs4/assignments/boggle/specification.php
