package com.vimal.test.lca;

/**
 * Tree class with a Builder.
 * 
 * The Tree could have any number of children but for simplicity sake i am
 * considering only a Binary Tree.
 * 
 * @author vimal
 * 
 * 
 */
public class Tree {

	private int value;
	private Tree left;
	private Tree right;
	
	private int numnodes;

	// since we have a builder
	private Tree() {

	}

	public void print(StringBuilder sb) {
		sb.append('(');
		sb.append(value);
		if (left != null) {
			left.print(sb);
		} else {
			sb.append('.');
		}
		if (right != null) {
			right.print(sb);
		} else {
			sb.append('.');
		}
		sb.append(')');
	}

	public void print() {
		StringBuilder builder = new StringBuilder();
		print(builder);
		System.out.println(builder.toString());
	}
	
	public int getNumNodes() {
		return numnodes;
	}
	

	public int getValue() {
		return value;
	}

	public Tree getLeft() {
		return left;
	}

	public Tree getRight() {
		return right;
	}

	// The Tree Builder
	public static class B {
		private int val;
		private B left;
		private B right;
		
		// disable
		private B() {
			
		}

		public static B withValue(int v) {
			B b = new B();
			b.val = v;
			return b;
		}

		public B andLeft(B t) {
			if(left == null) {				
				left = t;
			} else {
				throw new IllegalArgumentException("already present");
			}
			return this;
		}

		public B andRight(B t) {
			if(right == null) {				
				right = t;
			} else {
				throw new IllegalArgumentException("already present");
			}
			return this;
		}

		public Tree build() {
			Tree tree = new Tree();
			tree.value = val;
			int numnodes = 1;
			if (left != null) {
				tree.left = left.build();
				numnodes += tree.left.numnodes;
			}
			if (right != null) {
				tree.right = right.build();
				numnodes += tree.right.numnodes;
			}
			tree.numnodes = numnodes;
			return tree;
		}
	}

	public static void main(String[] args) {
		Tree tree = Tree.B.withValue(1)
				.andLeft(Tree.B.withValue(2).andLeft(Tree.B.withValue(4)).andRight(Tree.B.withValue(5)))
				.andRight(Tree.B.withValue(3).andLeft(Tree.B.withValue(6)).andRight(Tree.B.withValue(7))).build();
		tree.print();
	}

}
