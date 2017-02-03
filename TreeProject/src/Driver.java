/*
 * Author: Don Landrum
 * Date Created: January 11, 2017
 * Date Last Modified: February 3, 2017
 * 
 * Notes:
 * This file is the driver for a tree that will store nodes, assign edges, add
 * weights, and traverse between any two nodes.
 * For file input and output, the name of the input file is hard-coded into the 
 * program. To change the input file, simply change the value of the static
 * final String "IN_FILE_NAME" to the name of your file, provided that your file
 * is located in the same project folder as this file. Similarly, the name of 
 * the output file is hard-coded into the program. To change the name of the 
 * output file, simply change the value of the static final String 
 * "OUT_FILE_NAME". If no such file exists, this program will create one in the
 * project folder.
 */

import java.util.Scanner;
import java.io.*;
public class Driver {
	static final String IN_FILE_NAME = "input.txt";
	static final String OUT_FILE_NAME = "output.txt";
	public static void main(String[] args) {
		//The "time" variable is used the measure how long the program takes.
		long time = (System.currentTimeMillis());
		try {
			PrintWriter out = new PrintWriter(OUT_FILE_NAME);
			Scanner in = new Scanner(new File(IN_FILE_NAME));
			int n = in.nextInt();
			//This line constructs a tree with n nodes.
			Tree tree = new Tree(n); 
			for (int i = 0; i < (n-1); ++i) {
				int x = in.nextInt();
				int y = in.nextInt();
				//This line adds an appropriate edge in the tree.
				tree.addEdge(x,y); 
			}
			int q = in.nextInt();
			for (int i = 0; i < q; ++i) {
				String instruction = in.next();
				if (instruction.equalsIgnoreCase("add")) {
					int t = in.nextInt();
					int value = in.nextInt();
					//This line adds "value" to the appropriate nodes.
					tree.addValue(t, value); 
				}
				else if (instruction.equalsIgnoreCase("max")) {
					int a = in.nextInt();
					int b = in.nextInt();
					//This line finds the maximum value along the path.
					int max = tree.max(a,b); 
					out.println(max);
					//This line salvages output in case the program crashes.
					out.flush(); 
				}
				else {
					out.println("Error! Incorrect instruction.");
					System.exit(0);
				}
			}
			out.close();
			in.close();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		//The number of seconds that the program took is printed out.
		double total = ((double)(System.currentTimeMillis() - time)/1000);
		System.out.println(total+" seconds"); 
	}
}