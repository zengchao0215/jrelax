package com.jrelax.plugins.facedetect;

import java.util.ArrayList;
import java.util.List;

/** A binary tree for detection. At each node of the tree, a test (feature) is done on the window. Depending on the result,
 *  either the left or right child of the node is selected and so on. When the process reaches a leaf, the corresponding value is returned.
 */
public class Tree {
final static int LEFT = 0;
final static int RIGHT=1;

List<Feature> features;

public Tree()
{
	features = new ArrayList<Feature>();
}
public void addFeature(Feature f)
{
	features.add(f);
}

public float getVal(int[][] grayImage, int[][] squares, int i, int j, float scale) {
	Feature cur_node = features.get(0);
	while(true)
	{
		/* Compute the feature to see if we should go to the left or right child on the node.*/
		int where = cur_node.getLeftOrRight(grayImage, squares, i, j, scale);
		if(where==LEFT)
		{
			/* If the left child has a value, return it.*/
			if(cur_node.has_left_val)
			{
				return cur_node.left_val;
			}
			else
			{
				/* Else move to the left child node. */
				cur_node = features.get(cur_node.left_node);
			}
		}
		else
		{
			/* If the right child has a value, return it.*/
			if(cur_node.has_right_val)
			{
				return cur_node.right_val;
			}
			else
			{
				/* Else move to the right child node. */
				cur_node = features.get(cur_node.right_node);
			}
		}
	}
}

}
