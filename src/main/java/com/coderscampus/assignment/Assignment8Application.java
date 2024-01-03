package com.coderscampus.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Assignment8Application {

	public static void main(String[] args) {
		List<CompletableFuture<List<Integer>>> numbers = new ArrayList<>();

		ExecutorService fixedService = Executors.newFixedThreadPool(6);
		ExecutorService cachedService = Executors.newCachedThreadPool();

		for (int i = 0; i < 1000; i++) {
			CompletableFuture<List<Integer>> numbersList = 
					CompletableFuture.supplyAsync(() -> new Assignment8(),fixedService)
								     .thenApplyAsync(a8 -> a8.getNumbers(),cachedService);

			numbers.add(numbersList);

		}

		List<Integer> combinedList = numbers.stream()
											.map(CompletableFuture::join)
											.flatMap(List::stream)
											.toList();

		for (int i = 0; i < 15; i++) {
			int numMatch = i;
			Long count = combinedList.stream()
							         .filter(number -> number.equals(numMatch))
							         .count();

			System.out.println("The number " + i + " shows up " + count + " times!");
		}

		cachedService.shutdown();
		fixedService.shutdown();
	}

}
