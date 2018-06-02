package com.vimal.test.lca;

import java.util.Arrays;

/**
 * 
 * This is an implementation of LCA with RMQ as in the paper "LCA Problem Revisited"
 * https://www.ics.uci.edu/~eppstein/261/BenFar-LCA-00.pdf
 * The LCA problem is converted to a RMQ problem
 * 
 * RMQ (Range Minimum Queries) are computed for all possible lengths
 * 
 * Complexity : < O(n^2) , O(1) >
 * 
 * @author vimal 
 * 
 */
public class Naive_LCA_RMQ {
	
	public Naive_LCA_RMQ(Tree _t) {
		t = _t;
	}

	private Tree t;

	private int[] values;
	private int[] levels;
	// matrix[i][j] contains the index of the min element in a range sized j from
	// index i in levels array
	private int[][] matrix;

	/**
	 * The euler traversal is performed and values/levels stored.
	 */
	private void euler() {
		// number of items in euler traversal of a tree with n nodes is 2*n-1;
		int m = 2 * t.getNumNodes() - 1;
		values = new int[m];
		levels = new int[m];
		int index = 0;
		// do the euler traversal and populate the levels array
		recur(t, index, 1);
	}

	
	/**
	 * Compute RMQ for each possible length. 
	 */
	private void naiveRMQ() {
		int m = 2 * t.getNumNodes() - 1;
		// lets make the RMQ matrix
		matrix = new int[m][m];
		// j => size of range
		// size goes from 0 to max range possible
		for(int j = 0; j < m; j++) {
			// i => starting index of range
			// index goes from beginning to end in the traversal array
			for (int i = 0; i+j <m ; i++) {
				if(j == 0) {
					matrix[i][j] = i;
				} else {
					// dynamic programming step.
					// get the min from overlapping ranges from previous iteration
					if(levels[matrix[i][j-1]] < levels[matrix[i+1][j-1]]) {
						matrix[i][j] = matrix[i][j-1];
					} else {
						matrix[i][j] = matrix[i+1][j-1];
					}
				}
			}
		}

	}

	private int recur(Tree t, int index, int level) {
		values[index] = t.getValue();
		levels[index] = level;
		if (t.getLeft() != null) {
			index = recur(t.getLeft(), index + 1, level + 1);
		}
		values[index] = t.getValue();
		levels[index] = level;
		if (t.getRight() != null) {
			index = recur(t.getRight(), index + 1, level + 1);
		}
		values[index] = t.getValue();
		levels[index] = level;
		return index + 1;
	}
	
	public void preProcess() {
		int m = 2*t.getNumNodes()-1;

		euler();
		System.out.print("indexs : ");
		for (int i = 0; i < m; i++) {
			System.out.print(" " +i+",");
		}
		System.out.println();
		System.out.println("values : " + Arrays.toString(values));
		System.out.println("levels : " + Arrays.toString(levels));
		naiveRMQ();
		System.out.println("------------------------------");
		for (int i = 0; i < m; i++) {
			System.out.println(Arrays.toString(matrix[i]));
		}
	}
	
	public void query(int i1, int i2, int expectedLca) {
		if(i1 <= i2) {			
			System.out.println("LCA of " + values[i1] + " and " + values[i2] + " is " + values[matrix[i1][i2-i1]] + ". Expected : " + expectedLca);
		}
	}

	public static void main(String[] args) {
		Tree tree = Tree.B.withValue(1)
				.andLeft(Tree.B.withValue(2).andLeft(Tree.B.withValue(4)).andRight(Tree.B.withValue(5)))
				.andRight(Tree.B.withValue(3).andLeft(Tree.B.withValue(6)).andRight(Tree.B.withValue(7))).build();
		tree.print();
		
		Naive_LCA_RMQ lca = new Naive_LCA_RMQ(tree);
		
		lca.preProcess();

		lca.query(8, 10, 3);
		lca.query(2, 10, 1);
		lca.query(1, 7, 1);
		lca.query(1, 1, 2);
		lca.query(0, 0, 1);
	}
}
