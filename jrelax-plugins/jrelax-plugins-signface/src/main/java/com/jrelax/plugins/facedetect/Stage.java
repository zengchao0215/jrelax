package com.jrelax.plugins.facedetect;


import java.util.LinkedList;
import java.util.List;

/** A stage of the detector. Each stage consists of several trees and a threshold. When using the detector on a window, each tree returns a
 * value. If the sum of these values exceeds the threshold, the stage succeeds, else it fails (and the window is not the object looked for).
 *
 */
public class Stage {
	List<Tree> trees;
float threshold;
	public Stage(float threshold) {
this.threshold=threshold;
trees=new LinkedList<Tree>();
//features = new LinkedList<Feature>();
	}
	
	public void addTree(Tree t)
	{
		trees.add(t);
	}
	
	public boolean pass(int[][] grayImage, int[][] squares, int i, int j, float scale) {
		float sum=0;
		/* Compute the sum of values returned by each tree of the stage. */
		for(Tree t : trees)
		{
			sum+=t.getVal(grayImage, squares,i, j, scale);
		}
		/* The stage succeeds if the sum exceeds the stage threshold, and fails otherwise.*/
		return sum>threshold;
	}

}
