package com.coderscampus.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class SortingService {
	ExecutorService cachedService = Executors.newCachedThreadPool();

	List<CompletableFuture<List<Integer>>> numbers = new ArrayList<>();
	List<Integer> combinedList = new ArrayList<>();
	Map<Integer, AtomicInteger> numberCounts = new ConcurrentHashMap<>();

	Assignment8 assignment = new Assignment8();

	public void fetchAllNumbers() {
		for (int i = 0; i < 1000; i++) {
			CompletableFuture<List<Integer>> futuresList = CompletableFuture.supplyAsync(() -> assignment.getNumbers(), cachedService);
			numbers.add(futuresList);
		}

		while (numbers.stream().filter(CompletableFuture::isDone).count() < 1000) {}
		
		System.out.println("Number of completed threads: " + numbers.stream().filter(CompletableFuture::isDone).count());
		cachedService.shutdown();
		
	}

	
	public void combineListsAndFindNumberOfDuplicates() {
		combinedList = numbers.stream().map(CompletableFuture::join).flatMap(List::stream).toList();

		for (Integer number : combinedList) {
			numberCounts.putIfAbsent(number, new AtomicInteger(0));
			numberCounts.get(number).incrementAndGet();	
		}

		for (int i = 0; i < 15; i++) {
			Integer count = numberCounts.getOrDefault(i, new AtomicInteger(0)).get();
			System.out.println("The number " + i + " shows up " + count + " times!");
		}

	}

}
