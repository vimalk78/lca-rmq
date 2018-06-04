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
	
	public Faster_LCA_RMQ(Tree _t) {
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
	 * Compute RMQ for lengths which are powers of 2
	 */
	private void fasterRMQ() {
		int m = 2 * t.getNumNodes() - 1;
		// lets make the RMQ matrix
		matrix = new int[m][m];
		for(int k = 0, j = 0; k < m; j+=1) {
			for(int i = 0; i + k < m; i++) {
				// System.out.print(i + ","+ k + "," + j + " ");
				if(j == 0) {
					matrix[i][j] = i;
				} else {
					// dynamic programming step.
					// get the min from overlapping ranges from previous iteration
					if(levels[matrix[i][j-1]] < levels[matrix[i+k/2][j-1]]) {
						matrix[i][j] = matrix[i][j-1];
					} else {
						matrix[i][j] = matrix[i+k/2][j-1];
					}					
				}
			}
			if(k == 0) {
				k++;
			} else {
				k*=2;
			}
			//System.out.println("\n------------");
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
		fasterRMQ();
		System.out.println("------ query distance as power of 2 --> ");
		System.out.println("|");
		System.out.println("|  starting index");
		System.out.println("V");
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < m; j++) {
				System.out.print(String.format("%4d,", matrix[i][j]));
			}
			System.out.println();
			// System.out.println(Arrays.toString(matrix[i]));
		}
		System.out.println("------------------------------");
	}
	
	public void query(int i, int j, int expectedLca) {
		if(i < j) {
			int k = (int) (Math.log(j-i)/Math.log(2));
			//System.out.println(k);
			int k2 = (int) Math.pow(2, k);
			//System.out.println(i + ","+ k2 + "," + (k+1) + " ");
			//System.out.println((j-k2+1-1) + ","+ k2 + "," + (k+1) + " ");
			if(levels[matrix[i][k+1]] < levels[matrix[j-k2][k+1]]) {				
				System.out.println("LCA of " + values[i] + " and " + values[j] + " is " + values[matrix[i][k+1]] + ". Expected : " + expectedLca);
			} else {
				System.out.println("LCA of " + values[i] + " and " + values[j] + " is " + values[matrix[j-k2][k+1]] + ". Expected : " + expectedLca);
			}
		} else if(i == j) {
			System.out.println("LCA of " + values[i] + " and " + values[j] + " is " + values[matrix[i][0]] + ". Expected : " + expectedLca);
		}else {
			System.err.println("query: incorrect input");
		}
	}	

	public static void testmain(String[] args) {
		Tree tree = Tree.B.withValue(1)
				.andLeft(Tree.B.withValue(2).andLeft(Tree.B.withValue(4)).andRight(Tree.B.withValue(5)))
				.andRight(Tree.B.withValue(3).andLeft(Tree.B.withValue(6)).andRight(Tree.B.withValue(7))).build();
		tree.print();
		
		Faster_LCA_RMQ lca = new Faster_LCA_RMQ(tree);
		
		lca.preProcess();

		lca.query(8, 10, 3);
		lca.query(2, 10, 1);
		lca.query(1, 7, 1);
		lca.query(1, 1, 2);
		lca.query(0, 0, 1);
	}
	public static void main(String[] args) {
		//testIndices();
		testmain(args);
	}
	
	public static void testIndices() {
		int n = 12;
		for(int k = 0, j = 0; k < n; j+=1) {
			for(int i = 0; i + k < n; i++) {
				System.out.print(i + ","+ k + "," + j + " ");
			}
			if(k == 0) {
				k++;
			} else {
				k*=2;
			}
			System.out.println("\n------------");
		}
		int i = 0, j = 11;
		int k = (int) (Math.log(j-i)/Math.log(2));
		System.out.println(k);
		int k2 = (int) Math.pow(2, k);
		System.out.println(i + ","+ k2 + "," + (k+1) + " ");
		System.out.println((j-k2+1-1) + ","+ k2 + "," + (k+1) + " ");
	}
}
