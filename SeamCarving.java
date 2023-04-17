public class SeamCarving {
    
    public SeamCarving() {
        
    }
    public static double[][] energy;
    public static double[][] bestWeight;
    public static int[][] backtracking;
    public static int[] seam;

    /**
     * @return the seam's weight
     */
    public double run(int[][][] image) {
        //calculate energy for each pixel
    	energy = new double [image.length][image[0].length];
    	
    	//top left corner [0][0]
    	double diff1 = Math.sqrt((Math.pow(image[0][1][0] - image[0][0][0], 2)) + (Math.pow(image[0][1][1] - image[0][0][1], 2)) + (Math.pow(image[0][1][2] - image[0][0][2], 2)));
    	double diff2 = Math.sqrt((Math.pow(image[1][0][0] - image[0][0][0], 2)) + (Math.pow(image[1][0][1] - image[0][0][1], 2)) + (Math.pow(image[1][0][2] - image[0][0][2], 2)));
    	double diff3 = Math.sqrt((Math.pow(image[1][1][0] - image[0][0][0], 2)) + (Math.pow(image[1][1][1] - image[0][0][1], 2)) + (Math.pow(image[1][1][2] - image[0][0][2], 2)));
    	energy[0][0] = (diff1 + diff2 + diff3) / 3;
    	    	
    	//top right corner [0][image[0].length - 1]
    	double dif1 = Math.sqrt(Math.pow(image[0][(image[0].length)-2][0] - image[0][(image[0].length)-1][0], 2) +
    				Math.pow(image[0][(image[0].length)-2][1] - image[0][(image[0].length)-1][1], 2) +
    				Math.pow(image[0][(image[0].length)-2][2] - image[0][(image[0].length)-1][2], 2));
    	double dif2 = Math.sqrt(Math.pow(image[1][(image[0].length)-2][0] - image[0][(image[0].length)-1][0], 2) +
    				Math.pow(image[1][(image[0].length)-2][1] - image[0][(image[0].length)-1][1], 2) +
    				Math.pow(image[1][(image[0].length)-2][2] - image[0][(image[0].length)-1][2], 2));
    	double dif3 = Math.sqrt(Math.pow(image[1][(image[0].length)-1][0] - image[0][(image[0].length)-1][0], 2) +
					Math.pow(image[1][(image[0].length)-1][1] - image[0][(image[0].length)-1][1], 2) +
					Math.pow(image[1][(image[0].length)-1][2] - image[0][(image[0].length)-1][2], 2));
    	energy[0][(image[0].length)-1] = (dif1 + dif2 + dif3) / 3;
    	
    	//bottom left corner [image.length - 1][0]
    	double di1 = Math.sqrt(Math.pow(image[(image.length)-1][1][0] - image[image.length-1][0][0], 2) + 
    				Math.pow(image[(image.length)-1][1][1] - image[image.length-1][0][1], 2) +
    				Math.pow(image[(image.length)-1][1][2] - image[image.length-1][0][2], 2));
    	double di2 = Math.sqrt(Math.pow(image[(image.length)-2][1][0] - image[image.length-1][0][0], 2) + 
					Math.pow(image[(image.length)-2][1][1] - image[image.length-1][0][1], 2) +
					Math.pow(image[(image.length)-2][1][2] - image[image.length-1][0][2], 2));
    	double di3 = Math.sqrt(Math.pow(image[(image.length)-2][0][0] - image[image.length-1][0][0], 2) + 
					Math.pow(image[(image.length)-2][0][1] - image[image.length-1][0][1], 2) +
					Math.pow(image[(image.length)-2][0][2] - image[image.length-1][0][2], 2));
    	energy[image.length-1][0] = (di1 + di2 + di3) / 3;
    	    	
    	//bottom right corner [image.length - 1][image[0].length - 1]
    	double d1 = Math.sqrt(Math.pow(image[image.length-1][(image[0].length)-2][0] - image[image.length-1][(image[0].length)-1][0], 2) +
    				Math.pow(image[image.length-1][(image[0].length)-2][1] - image[image.length-1][(image[0].length)-1][1], 2) + 
    				Math.pow(image[image.length-1][(image[0].length)-2][2] - image[image.length-1][(image[0].length)-1][2], 2));
    	double d2 = Math.sqrt(Math.pow(image[image.length-2][(image[0].length)-2][0] - image[image.length-1][(image[0].length)-1][0], 2) +
					Math.pow(image[image.length-2][(image[0].length)-2][1] - image[image.length-1][(image[0].length)-1][1], 2) + 
					Math.pow(image[image.length-2][(image[0].length)-2][2] - image[image.length-1][(image[0].length)-1][2], 2));
    	double d3 = Math.sqrt(Math.pow(image[image.length-2][(image[0].length)-1][0] - image[image.length-1][(image[0].length)-1][0], 2) +
					Math.pow(image[image.length-2][(image[0].length)-1][1] - image[image.length-1][(image[0].length)-1][1], 2) + 
					Math.pow(image[image.length-2][(image[0].length)-1][2] - image[image.length-1][(image[0].length)-1][2], 2));
    	energy[image.length-1][image[0].length - 1] = (d1 + d2 + d3) / 3;
    	    	
    	for (int i = 1; i < image.length-1; i++) {
    		//left edge
    		double differ1 = Math.sqrt(Math.pow(image[i-1][0][0] - image[i][0][0], 2) + 
    						Math.pow(image[i-1][0][1] - image[i][0][1], 2) +
    						Math.pow(image[i-1][0][2] - image[i][0][2], 2));
    		double differ2 = Math.sqrt(Math.pow(image[i][1][0] - image[i][0][0], 2) + 
    						Math.pow(image[i][1][1] - image[i][0][1], 2) +
    						Math.pow(image[i][1][2] - image[i][0][2], 2));
    		double differ3 = Math.sqrt(Math.pow(image[i+1][0][0] - image[i][0][0], 2) + 
					Math.pow(image[i+1][0][1] - image[i][0][1], 2) +
					Math.pow(image[i+1][0][2] - image[i][0][2], 2));
    		double differ4 = Math.sqrt(Math.pow(image[i-1][1][0] - image[i][0][0], 2) + 
					Math.pow(image[i-1][1][1] - image[i][0][1], 2) +
					Math.pow(image[i-1][1][2] - image[i][0][2], 2));
    		double differ5 = Math.sqrt(Math.pow(image[i+1][1][0] - image[i][0][0], 2) + 
					Math.pow(image[i+1][1][1] - image[i][0][1], 2) +
					Math.pow(image[i+1][1][2] - image[i][0][2], 2));
    		energy[i][0] = (differ1 + differ2 + differ3 + differ4 + differ5) / 5;
    		    		
    		//right edge
    		double differe1 = Math.sqrt(Math.pow(image[i-1][image[0].length-1][0] - image[i][image[0].length-1][0], 2) +
    				Math.pow(image[i-1][image[0].length-1][1] - image[i][image[0].length-1][1], 2) +
    				Math.pow(image[i-1][image[0].length-1][2] - image[i][image[0].length-1][2], 2));
    		double differe2 = Math.sqrt(Math.pow(image[i+1][image[0].length-1][0] - image[i][image[0].length-1][0], 2) +
    				Math.pow(image[i+1][image[0].length-1][1] - image[i][image[0].length-1][1], 2) +
    				Math.pow(image[i+1][image[0].length-1][2] - image[i][image[0].length-1][2], 2));
    		double differe3 = Math.sqrt(Math.pow(image[i][image[0].length-2][0] - image[i][image[0].length-1][0], 2) +
    				Math.pow(image[i][image[0].length-2][1] - image[i][image[0].length-1][1], 2) +
    				Math.pow(image[i][image[0].length-2][2] - image[i][image[0].length-1][2], 2));
    		double differe4 = Math.sqrt(Math.pow(image[i-1][image[0].length-2][0] - image[i][image[0].length-1][0], 2) +
    				Math.pow(image[i-1][image[0].length-2][1] - image[i][image[0].length-1][1], 2) +
    				Math.pow(image[i-1][image[0].length-2][2] - image[i][image[0].length-1][2], 2));
    		double differe5 = Math.sqrt(Math.pow(image[i+1][image[0].length-2][0] - image[i][image[0].length-1][0], 2) +
    				Math.pow(image[i+1][image[0].length-2][1] - image[i][image[0].length-1][1], 2) +
    				Math.pow(image[i+1][image[0].length-2][2] - image[i][image[0].length-1][2], 2));
    		energy[i][image[0].length-1] = (differe1 + differe2 + differe3 + differe4 + differe5) / 5;   		    		

    	}
    	
    	for (int i = 1; i < image[0].length-1; i++) {
    		//bottom edge
    		double difference1 = Math.sqrt(Math.pow(image[image.length-1][i-1][0] - image[image.length-1][i][0], 2) +
    				Math.pow(image[image.length-1][i-1][1] - image[image.length-1][i][1], 2) +
    				Math.pow(image[image.length-1][i-1][2] - image[image.length-1][i][2], 2));
    		double difference2 = Math.sqrt(Math.pow(image[image.length-1][i+1][0] - image[image.length-1][i][0], 2) +
    				Math.pow(image[image.length-1][i+1][1] - image[image.length-1][i][1], 2) +
    				Math.pow(image[image.length-1][i+1][2] - image[image.length-1][i][2], 2));
    		double difference3 = Math.sqrt(Math.pow(image[image.length-2][i+1][0] - image[image.length-1][i][0], 2) +
    				Math.pow(image[image.length-2][i+1][1] - image[image.length-1][i][1], 2) +
    				Math.pow(image[image.length-2][i+1][2] - image[image.length-1][i][2], 2));
    		double difference4 = Math.sqrt(Math.pow(image[image.length-2][i][0] - image[image.length-1][i][0], 2) +
    				Math.pow(image[image.length-2][i][1] - image[image.length-1][i][1], 2) +
    				Math.pow(image[image.length-2][i][2] - image[image.length-1][i][2], 2));
    		double difference5 = Math.sqrt(Math.pow(image[image.length-2][i-1][0] - image[image.length-1][i][0], 2) +
    				Math.pow(image[image.length-2][i-1][1] - image[image.length-1][i][1], 2) +
    				Math.pow(image[image.length-2][i-1][2] - image[image.length-1][i][2], 2));
    		energy[image.length-1][i] = (difference1 + difference2 + difference3 + difference4 + difference5) / 5;
    		
    		//top edge
    		double diffe1 = Math.sqrt(Math.pow(image[0][i-1][0] - image[0][i][0], 2) + 
					Math.pow(image[0][i-1][1] - image[0][i][1], 2) +
					Math.pow(image[0][i-1][2] - image[0][i][2], 2));
    		double diffe2 = Math.sqrt(Math.pow(image[0][i+1][0] - image[0][i][0], 2) + 
					Math.pow(image[0][i+1][1] - image[0][i][1], 2) +
					Math.pow(image[0][i+1][2] - image[0][i][2], 2));
    		double diffe3 = Math.sqrt(Math.pow(image[1][i][0] - image[0][i][0], 2) + 
					Math.pow(image[1][i][1] - image[0][i][1], 2) +
					Math.pow(image[1][i][2] - image[0][i][2], 2));
    		double diffe4 = Math.sqrt(Math.pow(image[1][i-1][0] - image[0][i][0], 2) + 
					Math.pow(image[1][i-1][1] - image[0][i][1], 2) +
					Math.pow(image[1][i-1][2] - image[0][i][2], 2));
    		double diffe5 = Math.sqrt(Math.pow(image[1][i+1][0] - image[0][i][0], 2) + 
					Math.pow(image[1][i+1][1] - image[0][i][1], 2) +
					Math.pow(image[1][i+1][2] - image[0][i][2], 2));
    		energy[0][i] = (diffe1 + diffe2 + diffe3 + diffe4 + diffe5) / 5;
    	}
    	    	
    	//middle
    	for (int i = 1; i < image.length - 1; i++) {
    		for (int j = 1; j < image[0].length - 1; j++) {    			
    			double one = Math.sqrt(Math.pow(image[i-1][j-1][0] - image[i][j][0], 2) + 
    					Math.pow(image[i-1][j-1][1] - image[i][j][1], 2) + 
    					Math.pow(image[i-1][j-1][2] - image[i][j][2], 2));
    			double two = Math.sqrt(Math.pow(image[i-1][j][0] - image[i][j][0], 2) + 
    					Math.pow(image[i-1][j][1] - image[i][j][1], 2) + 
    					Math.pow(image[i-1][j][2] - image[i][j][2], 2));
    			double three = Math.sqrt(Math.pow(image[i-1][j+1][0] - image[i][j][0], 2) + 
    					Math.pow(image[i-1][j+1][1] - image[i][j][1], 2) + 
    					Math.pow(image[i-1][j+1][2] - image[i][j][2], 2));
    			double four = Math.sqrt(Math.pow(image[i][j-1][0] - image[i][j][0], 2) + 
    					Math.pow(image[i][j-1][1] - image[i][j][1], 2) + 
    					Math.pow(image[i][j-1][2] - image[i][j][2], 2));
    			double five = Math.sqrt(Math.pow(image[i][j+1][0] - image[i][j][0], 2) + 
    					Math.pow(image[i][j+1][1] - image[i][j][1], 2) + 
    					Math.pow(image[i][j+1][2] - image[i][j][2], 2));
    			double six = Math.sqrt(Math.pow(image[i+1][j-1][0] - image[i][j][0], 2) + 
    					Math.pow(image[i+1][j-1][1] - image[i][j][1], 2) + 
    					Math.pow(image[i+1][j-1][2] - image[i][j][2], 2));
    			double seven = Math.sqrt(Math.pow(image[i+1][j][0] - image[i][j][0], 2) + 
    					Math.pow(image[i+1][j][1] - image[i][j][1], 2) + 
    					Math.pow(image[i+1][j][2] - image[i][j][2], 2));
    			double eight = Math.sqrt(Math.pow(image[i+1][j+1][0] - image[i][j][0], 2) + 
    					Math.pow(image[i+1][j+1][1] - image[i][j][1], 2) + 
    					Math.pow(image[i+1][j+1][2] - image[i][j][2], 2));
    			energy[i][j] = (one + two + three + four + five + six + seven + eight) / 8;	
    		}
    	}

    	//memory that stores the best weight in each row, bottom up
    	bestWeight = new double[image.length][image[0].length];
    	
    	//an int array to backtrack our seam
    	backtracking = new int[image.length][image[0].length];
    	
    	//minimum weight for the last (bottom) row, which is just the energy of each pixel --> base case
    	for (int i = 0; i < image[0].length; i++) {
    		bestWeight[image.length-1][i] = energy[image.length-1][i];
    	}
    	
    	int row = image.length - 2;
    	while (row >= 0) {
    		for (int col = 0; col < image[0].length; col++) {
    			double a = Double.MAX_VALUE;
    			double c = Double.MAX_VALUE;
    			double smallest = 0;
    			int backtrack = 0;
    			
    			if (col > 0) {
    				a = bestWeight[row+1][col-1];
    			}
    			double b = bestWeight[row+1][col];
    			
    			if (col < image[0].length - 1) {
    				c = bestWeight[row+1][col+1];
    			}
    			
    			//find the smallest up until the row below it
    			if (a < b) {
    				if (c < a) {
    					smallest = c;
    					backtrack = col + 1;
    				}
    				else {
    					smallest = a;
    					backtrack = col - 1;
    				}
    			}
    			else {
    				if (b < c) {
    					smallest = b;
    					backtrack = col;
    				}
    				else {
    					smallest = c;
    					backtrack = col + 1;
    				}
    			}
    		
    			bestWeight[row][col] = smallest + energy[row][col];	
    			backtracking[row][col] = backtrack;
    		}
    		row--;
    	}
    	
    	//initialize seam, an int array that stores the seam (column number, top to bottom)
    	seam = new int[image.length];
    	
    	double result = Double.MAX_VALUE;
    	for (int i = 0; i < image[0].length; i++) {
    		if (bestWeight[0][i] < result) {
    			result = bestWeight[0][i];
    			seam[0] = i;
    		}
    	}
    	
    	return result;
    }
    
    /**
     * Get the seam, in order from top to bottom, where the top-left corner of the
     * image is denoted (0,0).
     * 
     * Since the y-coordinate (row) is determined by the order, only return the x-coordinate
     * 
     * @return the ordered list of x-coordinates (column number) of each pixel in the seam
     */
    public int[] getSeam() {
    	for (int i = 1; i < seam.length; i++) {
    		seam[i] = backtracking[i-1][seam[i - 1]];
    	}  	
    	return seam;
    }
    
}
