package com.hcl.projects.new_project;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * Hello world!
 *
 */
public class App 
{
	final static String FOLDER = "C:\\LMFiles";

	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		showWelcomeScreen();
		showMainMenu();

	}

	private static void showWelcomeScreen() {
		System.out.println("\n****************************************\n");
		System.out.println("\t Welcome to LockedMe! \n");
		System.out.println("\tDeveloper: Briana Lerma\n");
		System.out.println("****************************************");
	}

	private static void showMainMenu() {
		System.out.println("\n---------------------------------------");
		System.out.println("\t  MAIN MENU\n");
		System.out.println("1.) View files in ascending order");
		System.out.println("2.) Perform file operations");
		System.out.println("3.) Exit LockedMe");
		System.out.println("\n---------------------------------------");
		collectMainMenuOption();
	}

	private static void collectMainMenuOption() {
		System.out.println("\nPlease make your selection. Enter 1, 2 or 3:\n");
		String mainOption = scanner.nextLine();
		switch (mainOption) {
		case "1":
			showFilesAscending();
			break;
		case "2":
			showFileOperations();
		case "3":
			System.out.println("\nThanks for using LockedMe.com. Goodbye!");
			System.exit(0);
			break;
		default:
			System.out.println("Invalid input provided. Please enter 1, 2 or 3.\n");
		}
		showMainMenu();
	}

	private static void showFilesAscending() {
		System.out.println("---------------------------------------");
		System.out.println("Your files: \n");
		File[] files = new File(FOLDER).listFiles();
		Set<String> sorted = new TreeSet<>();
		for (File file : files) {
			if (!file.isFile()) {
				continue;
			}
			sorted.add(file.getName());
		}
		sorted.forEach(System.out::println);
		System.out.println("---------------------------------------");
	}

	private static void showFileOperations() {
		System.out.println("---------------------------------------");
		System.out.println("\tFILE OPERATIONS\n");
		System.out.println("1.) Return to main menu");
		System.out.println("2.) Add a file");
		System.out.println("3.) Delete a file");
		System.out.println("4.) Search for a file");
		System.out.println("5.) Exit LockedMe");
		System.out.println("\n---------------------------------------");
		collectFileOperation();
	}

	private static void collectFileOperation() {
		System.out.println("Please enter 1, 2, 3, 4, or 5:\n");
		String option = scanner.nextLine();
		switch (option) {
		case "1":
			showMainMenu();
			break;
		case "2":
			addFile();
			break;
		case "3":
			deleteFile();
			break;
		case "4":
			searchFile();
			break;
		case "5":
			System.out.println("\n**************************************");
			System.out.println("\nThank you for using LockedMe, Goodbye!");
			System.out.println("\n**************************************");
			System.exit(0);
			break;
		}
		showFileOperations();
	}

	private static void addFile() throws InvalidPathException {
		System.out.println("Please provide a file path:\n");
		String filePath = scanner.nextLine();
		Path path = Paths.get(filePath);

		if (!Files.exists(path)) {
			System.out.println("\n\t****ERROR: Invalid file path****\n");
			return;
		}

		String newFilePath = FOLDER + "/" + path.getFileName();
		int inc = 0;
		while (Files.exists(Paths.get(newFilePath))) {
			inc++;
			newFilePath = FOLDER + "/" + inc + "_" + path.getFileName();
		}
		try {
			Files.copy(path, Paths.get(newFilePath));
			System.out.println("\n\t****File Added to " + FOLDER + "****\n");
		} catch (IOException e) {
			System.out.println("Unable to copy file to " + newFilePath);
		}

	}

	private static void deleteFile() {
		System.out.println("\nEnter the file you wish to delete: ");
		String file = scanner.nextLine();
		String fileToDelete = FOLDER + "/" + file.replace(":", "");
		Path deleteFile = Paths.get(fileToDelete);
		try {
			boolean deleted = Files.deleteIfExists(deleteFile);

			if (deleted) {
				System.out.println("\n\t****File deleted.****\n");
			} else {
				System.out.println("\n\t****ERROR: File not found****\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void searchFile() {
		System.out.println("\nEnter the name of file you wish to search for: \n");
		String file = scanner.nextLine();
		String fileToSearch = FOLDER + "/" + file;
		Path searchFile = Paths.get(fileToSearch);
		if (Files.exists(searchFile)) {
			System.out.println("\n****" + file + " was found in " + FOLDER + "****\n");
		} else {
			System.out.println("\n\t****ERROR: File does not exist.****\n");
		}
	}

}
