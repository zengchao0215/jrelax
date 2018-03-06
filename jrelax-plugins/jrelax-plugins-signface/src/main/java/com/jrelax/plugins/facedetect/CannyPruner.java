package com.jrelax.plugins.facedetect;

public class CannyPruner
{
	/** Compute the Canny Edge detector of an image (cf Wikipedia for the algorithm). 
	 * @param grayImage The grayscale original image.
	 * @return The image of edges detected (as an integral image to speed up further computations).*/
	public static int[][] getIntegralCanny(int[][] grayImage)
	{
		/* Convolution of the image by a gaussian filter to reduce noise.*/
		int[][] canny = new int[grayImage.length][grayImage[0].length];
		for(int i=2;i<canny.length-2;i++)
			for(int j=2;j<canny[0].length-2;j++)
			{
				int sum =0;
				sum+=2*grayImage[i-2][j-2];
				                      sum+=4*grayImage[i-2][j-1];
				                      sum+=5*grayImage[i-2][j+0];
				                      sum+=4*grayImage[i-2][j+1];
				                      sum+=2*grayImage[i-2][j+2];
				                      sum+=4*grayImage[i-1][j-2];
				                      sum+=9*grayImage[i-1][j-1];
				                      sum+=12*grayImage[i-1][j+0];
				                      sum+=9*grayImage[i-1][j+1];
				                      sum+=4*grayImage[i-1][j+2];
				                      sum+=5*grayImage[i+0][j-2];
				                      sum+=12*grayImage[i+0][j-1];
				                      sum+=15*grayImage[i+0][j+0];
				                      sum+=12*grayImage[i+0][j+1];
				                      sum+=5*grayImage[i+0][j+2];
				                      sum+=4*grayImage[i+1][j-2];
				                      sum+=9*grayImage[i+1][j-1];
				                      sum+=12*grayImage[i+1][j+0];
				                      sum+=9*grayImage[i+1][j+1];
				                      sum+=4*grayImage[i+1][j+2];
				                      sum+=2*grayImage[i+2][j-2];
				                      sum+=4*grayImage[i+2][j-1];
				                      sum+=5*grayImage[i+2][j+0];
				                      sum+=4*grayImage[i+2][j+1];
				                      sum+=2*grayImage[i+2][j+2];

				      				canny[i][j]=sum/159;
			}
		
		/*Computation of the discrete gradient of the image.*/
		int[][] grad = new int[grayImage.length][grayImage[0].length];
		for(int i=1;i<canny.length-1;i++)
			for(int j=1;j<canny[0].length-1;j++)
			{
				int grad_x =-canny[i-1][j-1]+canny[i+1][j-1]-2*canny[i-1][j]+2*canny[i+1][j]-canny[i-1][j+1]+canny[i+1][j+1];
				int grad_y = canny[i-1][j-1]+2*canny[i][j-1]+canny[i+1][j-1]-canny[i-1][j+1]-2*canny[i][j+1]-canny[i+1][j+1];
				grad[i][j]=Math.abs(grad_x)+Math.abs(grad_y);
			}
		
		/* Suppression of non-maxima of the gradient and computation of the integral Canny image. */
				for(int i=0;i<canny.length;i++)
				{
					int col=0;
					for(int j=0;j<canny[0].length;j++)
					{
						int value= grad[i][j];
						canny[i][j]=(i>0?canny[i-1][j]:0)+col+value;
						col+=value;
					}
				}
		return canny;
	}
}