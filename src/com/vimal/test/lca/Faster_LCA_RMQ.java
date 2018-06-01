package com.vimal.test.lca;

import java.util.Arrays;


/**
 * 
 * This is an implementation of LCA with RMQ as in the paper "LCA Problem
 * Revisited" https://www.ics.uci.edu/~eppstein/261/BenFar-LCA-00.pdf The LCA
 * problem is converted to a RMQ problem
 * 
 * RMQ (Range Minimum Queries) are computed for lengths which are powers of 2
 * 
 * Complexity : < O(nlogn) , O(1) >
 * 
 * @author vimal
 */
public class Faster_LCA_RMQ {

	public Tree t;

	public int[] values;
	public int[] levels;
	// matrix[i][j] contains the index of the min element in a range sized j from
	// index i in levels array
	public int[][] matrix;

	/**
	 * The euler traversal is performed and values/levels stored.
	 */
	public void euler() {
		// number of items in euler traversal of a tree with n nodes is 2*n-1;
		int m = 2 * t.getNumNodes() - 1;
		values = new int[m];
		levels = new int[m];
		int index = 0;
		// do the euler traversal and populate the levels array
		recur(t, index, 1);
	}

	/**
	 * Compute RMQ for lengths which are powers of 2
	 */
	private void fasterRMQ() {
		// TODO 
		throw new RuntimeException("Not Implemented");
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

	public void test(int i1, int i2, int expectedLca) {
		if (i1 < i2) {
			System.out.println("LCA of " + values[i1] + " and " + values[i2] + " is " + values[matrix[i1][i2 - i1]]
					+ ". Expected : " + expectedLca);
		}
	}

	public static void main(String[] args) {
		Tree tree = Tree.B.withValue(1)
				.andLeft(Tree.B.withValue(2).andLeft(Tree.B.withValue(4)).andRight(Tree.B.withValue(5)))
				.andRight(Tree.B.withValue(3).andLeft(Tree.B.withValue(6)).andRight(Tree.B.withValue(7))).build();
		tree.print();
		int m = 2 * tree.getNumNodes() - 1;

		Faster_LCA_RMQ lca = new Faster_LCA_RMQ();
		lca.t = tree;
		lca.euler();
		System.out.print("indexs : ");
		for (int i = 0; i < m; i++) {
			System.out.print(" " + i + ",");
		}
		System.out.println();
		System.out.println("values : " + Arrays.toString(lca.values));
		System.out.println("levels : " + Arrays.toString(lca.levels));
		lca.fasterRMQ();
		System.out.println("------------------------------");
		for (int i = 0; i < m; i++) {
			System.out.println(Arrays.toString(lca.matrix[i]));
		}
		lca.test(8, 10, 3);
		lca.test(2, 10, 1);
		lca.test(1, 7, 1);
	}

}
