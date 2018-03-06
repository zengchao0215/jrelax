package com.jrelax.plugins.facedetect;


import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

/** A feature of the detector. A feature is located at a node of a tree, and returns LEFT or RIGHT depending on the comparison
 * of the computed value and a threshold. The value is computed as follows : the feature is constituted of weighted rectangles, and the value
 * is the sum of the pixels inside each rectangle, weighted by the rectangle's weight.
 */
public class Feature {

	Rect[] rects;
	int nb_rects;
	float threshold;
	float left_val;
	float right_val;
	Point size;
	int left_node;
	int right_node;
	boolean has_left_val;
	boolean has_right_val;
	
	public Feature(	float threshold,float left_val,	int left_node,boolean has_left_val,
			float right_val,int right_node,boolean has_right_val,Point size) {
		nb_rects = 0;
		rects=new Rect[3];
		this.threshold=threshold;
		this.left_val=left_val;
		this.left_node = left_node;
		this.has_left_val = has_left_val;
		this.right_val=right_val;
		this.right_node=right_node;
		this.has_right_val=has_right_val;
		this.size = size;
	}

	public int getLeftOrRight(int[][] grayImage, int[][] squares, int i, int j, float scale) {
		/* Compute the area of the window.*/
		int w=(int) (scale*size.x);
		int h=(int)(scale*size.y);
		double inv_area=1./(w*h);
		
		/* Compute the sum (and squared sum) of the pixel values in the window, and get the mean and variance of pixel values
		 * in the window. */
		int total_x=grayImage[i+w][j+h]+grayImage[i][j]-grayImage[i][j+h]-grayImage[i+w][j];
		int total_x2=squares[i+w][j+h]+squares[i][j]-squares[i][j+h]-squares[i+w][j];
		double moy=total_x*inv_area;
		double vnorm=total_x2*inv_area-moy*moy;
		vnorm=(vnorm>1)?Math.sqrt(vnorm):1;

				int rect_sum=0;
				/* For each rectangle in the feature. */
				for(int k=0;k<nb_rects;k++)
				{
					Rect r = rects[k];
					/* Scale the rectangle according to the window size. */
					int rx1=i+(int) (scale*r.x1);
					int rx2=i+(int) (scale*(r.x1+r.y1));
					int ry1=j+(int) (scale*r.x2);
					int ry2=j+(int) (scale*(r.x2+r.y2));
					/* Add the sum of pixel values in the rectangles (weighted by the rectangle's weight) to the total sum */
					rect_sum+=(int)((grayImage[rx2][ry2]-grayImage[rx1][ry2]-grayImage[rx2][ry1]+grayImage[rx1][ry1])*r.weight);
				}
				double rect_sum2=rect_sum*inv_area;

				/* Return LEFT or RIGHT depending on how the total sum compares to the threshold. */
				return (rect_sum2<threshold*vnorm)?Tree.LEFT:Tree.RIGHT;

	}

	public void add(Rect r) {
		rects[nb_rects++]=r;
	}
}
