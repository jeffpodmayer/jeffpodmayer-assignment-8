package com.coderscampus.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Assignment8Application {
	
	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(6);
		ExecutorService getNumbersService = Executors.newCachedThreadPool();
		
		List<CompletableFuture<List<Integer>>> numbers = new ArrayList<>();
		   
		for (int j=0; j<1000; j++) {
			CompletableFuture<List<Integer>> numberList = 
					CompletableFuture.supplyAsync(() -> new Assignment8(), service)
							 		 .thenApplyAsync(a8 -> a8.getNumbers(), getNumbersService);			
						
			numbers.add(numberList);
			
			}
		
			
		//Extract into its own method.
		List<Integer> combinedList = numbers.stream()
											.map(CompletableFuture::join)
											.flatMap(List::stream)
											.collect(Collectors.toList());
		
		//Extract into its own method
		for(int i = 0; i < 15; i++) {
			int numMatch = i;
			long count = combinedList.stream()
				   .filter(number -> number.equals(numMatch))
				   .count();
			
			System.out.println("Number " + i + " shows up " + count + " times!");
		}
			
		service.shutdown();
		getNumbersService.shutdown();
	}
}

