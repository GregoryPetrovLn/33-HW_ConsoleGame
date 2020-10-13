package telran.utils;

import java.io.*;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ConsoleGame {
	FileSystem fs = FileSystems.getDefault();
	private final int DIGITS_AMOUNT = 4;
	private final String pathToFolder = "./src/lastGameResults/";
	ArrayList<String> listResults = new ArrayList<>();
	int cowsAndBulls[] = new int[2]; // pos 0 - cow /// pos 1 - bull

	public void game() throws IOException {
		int compNumber; 
		int userNumber; 
		

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			String input = reader.readLine();

			compNumber = numGenerator();
			userNumber = Integer.parseInt(input.length() > 4 ? "0000": input); // for not failing when number more than 4 digits
			//but it still can fall when input contain character

			int[] moveResult = checkNumbers(compNumber, userNumber);
			
			if (moveResult[0] == 0 && moveResult[1] == 4) {
				String fileName = getFileName();
				System.out.println("\n\tFinish!");

				try (PrintWriter pw = new PrintWriter(pathToFolder + fileName)) {
					for(String line : listResults) {
						pw.println(line);
					}
					pw.println("\n\t Result ---> cows : " + cowsAndBulls[0] + " bulls : " + cowsAndBulls[1]);
				}

				break;
			}

			listResults.add(listResults.size() + 1 + ": " + "cows - " +
			 moveResult[0] + " bulls - " + moveResult[1] +
			" ====> user : " + userNumber + " computer : " + compNumber);
			
			
			cowsAndBulls[0] += moveResult[0];
			cowsAndBulls[1] += moveResult[1];
			
			System.out.println(listResults.get(listResults.size() - 1) + "\n\t\t\t\t current result ---> cows : " + cowsAndBulls[0] + " bulls : " + cowsAndBulls[1]);

		}

	}

	/**
	 * 
	 * @return
	 */
	private String getFileName() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now).toString() + "_" + listResults.size() + ".txt";
	}

	/**
	 * 
	 * @param cowNumber
	 * @param bullNumber
	 * @return
	 */
	public int[] checkNumbers(int cowNumber, int bullNumber) {
		int res[] = new int[2];// pos 0 - cow /// pos 1 - bull

		ArrayList<Integer> cowDigitsList = new ArrayList<>();

		int cowDigits[] = getDigits(cowNumber);
		int bullDigits[] = getDigits(bullNumber);

		Arrays.stream(cowDigits).forEach(digit -> cowDigitsList.add(digit));


		for (int i = 0; i < bullDigits.length; i++) {
			if (cowDigitsList.contains(bullDigits[i])) {
				if (cowDigitsList.indexOf(bullDigits[i]) == i) {
					++res[1];
				} else {
					++res[0];
				}
			}
		}

		return res;
	}

	/**
	 * 
	 * @param number
	 * @return
	 */
	public int[] getDigits(int number) {
		int res[] = new int[DIGITS_AMOUNT];

		for (int i = DIGITS_AMOUNT - 1; i >= 0; i--) {
			res[i] = number % 10;
			number /= 10;
		}

		return res;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	private int numGenerator() {
		int number = 0;
		ArrayList<Integer> digits = new ArrayList<>();

		while (digits.size() != DIGITS_AMOUNT) {
			int randomDigit = randomDigit();
			if (!digits.contains(randomDigit))
				digits.add(randomDigit);

		}

		for (int i = 0; i < digits.size(); i++) {
			number += (int) digits.get(i);
			if (i != DIGITS_AMOUNT - 1)
				number *= 10;
		}

		return number;
	}

	private int randomDigit() {
		int min = 1;
		int max = 9;
		return min + (int) (Math.random() * ((max + 1) - min));
	}

}
