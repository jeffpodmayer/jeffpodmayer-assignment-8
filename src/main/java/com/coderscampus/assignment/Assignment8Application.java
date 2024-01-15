package com.coderscampus.assignment;

public class Assignment8Application {

	public static void main(String[] args) {
		Assignment8Service sort = new Assignment8Service();
		
		sort.fetchAllNumbers();
		sort.countNumberFrequency();
		sort.displayNumbersCountMap();

	}
}
