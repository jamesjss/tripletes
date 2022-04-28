package com.example.tripletes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class TripletesApplication {

	private static final Scanner scanner = new Scanner(System.in);

	private static List<String> finalCombinations =new ArrayList<>();

	private static List<String> loadMuestra() {
		List<String> muestra =new ArrayList<>();
		muestra.add("5");
		muestra.add("1 2 b");
		muestra.add("2 3 r");
		muestra.add("3 4 r");
		muestra.add("4 5 b");
		return  muestra;
	}


	static boolean arePermutation(String str1, String str2)
	{
		// Obtain strings
		int n1 = str1.length();
		int n2 = str2.length();

		// Check lengh
		/*if (n1 != n2)
			return false;*/
		char ch1[] = str1.toCharArray();
		char ch2[] = str2.toCharArray();

		// Sort both strings
		Arrays.sort(ch1);
		Arrays.sort(ch2);

		// Compare sorted strings
		for (int i = 0; i < n1; i++)
			if (ch1[i] != ch2[i])
				return false;

		return true;
	}

	private static List<String> obtainCombinations(List<String> muestra) {
		List<String> combinations =new ArrayList<>();
		List<String> combinationsWithouthPermutations =new ArrayList<>();


		int numberCombinations = Integer.valueOf(muestra.get(0));
		for (int i = 1; i <= numberCombinations - 2; ++i) {
			for (int j = 2; j <= numberCombinations - 1; ++j) {
				for (int z = 3; z <= numberCombinations; ++z) {
					if (i!=j && j!=z && z!=i ) {
						//TODO: Quitar permutas repetidas
						//System.out.println(String.format("%s %s %s", i, j, z));
						combinations.add(String.format("%s %s %s", i, j, z));
					}
				}

			}
		}


		return combinations;
	}


	private static void recorreCombinaciones(Map<Integer, String> map, List<String> combinations) {


		/*
			1 2 3
			1 2 4
			1 2 5
			1 3 4
			1 3 5
			1 4 3
			1 4 5
			2 3 4
			2 3 5
			2 4 3
			2 4 5
			3 2 4
			3 2 5
			3 4 5
		 */

		boolean combinacion1Ok = false;
		boolean combinacion2Ok = false;
		boolean combinacion3Ok = false;

		for (final String combination : combinations) {
			String[] numbersOfCombinations = combination.split(" ");
			//Como son tripletes solo habrán 3 números
			int numberInt1 = Integer.valueOf(numbersOfCombinations[0]);
			int numberInt2 = Integer.valueOf(numbersOfCombinations[1]);
			int numberInt3 = Integer.valueOf(numbersOfCombinations[2]);
			//Combinación de posición 1 a posición 2
			for (int i = numberInt1; i <= numberInt2-1 ; ++i) {
				String color = map.get(i);
				if (color.equals("r")) {
					combinacion1Ok=true;
				}
			}
			//Combinación de posición 1 a posición 3
			for (int i = numberInt1; i <= numberInt3-1 ; ++i) {
				String color = map.get(i);
				if (color.equals("r")) {
					combinacion2Ok=true;
				}
			}
			//Combinación de posición 2 a posición 3
			for (int i = numberInt2; i <= numberInt3-1 ; ++i) {
				String color = map.get(i);
				if (color.equals("r")) {
					combinacion3Ok=true;
				}
			}
			if (combinacion1Ok && combinacion2Ok && combinacion3Ok) {
				finalCombinations.add(combination);
			}
		}



	}

	public static void main(String[] args) throws IOException {

		SpringApplication.run(TripletesApplication.class, args);

		Map<Integer, String> map = new HashMap<Integer, String>();

		//Cargamos la muestra
		List<String> items = loadMuestra();






		//Insertamos nuestra muesta en un map, para obtenerlo de forma eficiente
		AtomicInteger idx = new AtomicInteger();
		items.stream().forEach(item -> {
			if (idx.get() !=0) {
				String[] parts = item.split(" ");
				map.put(Integer.valueOf(parts[0]), parts[2]);
			}
			idx.getAndIncrement();
		});


		//System.out.println(map.toString());



		List<String> combinations = obtainCombinations(items);


		recorreCombinaciones(map, combinations);

		System.out.println(String.format("Array de permutas: %s", finalCombinations.toString()));
		System.out.println(String.format("Salida de muestra sin quitar permutas repetidas: %s", finalCombinations.size()));


	}


}
