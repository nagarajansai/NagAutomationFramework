package com.automation.support;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.Test;

public class CreateDirectoryTest {
	File dirName = new File("C:\\testdir\\TestReports");
	String fileTime, dateTime, todayDate;
	SimpleDateFormat createDateFormat = new SimpleDateFormat("dd/MM/yyyy");
	Date date = new Date();

	@Test
	public void createDir() {
		todayDate = getTodayDate();
		fileTime = getStringFromFileTime();
		if (dirName.exists() && !todayDate.equals(fileTime)) {
			System.out.println(todayDate);
			System.out.println(fileTime);

			File[] files = dirName.listFiles();
			for (File subDir : files) {
				if (subDir.exists() && subDir.isDirectory()) {
					File[] subfiles = subDir.listFiles();
					for (File f : subfiles) {
						System.out.println("Old File " + "\"" + f.getName() + "\"" + " in sub directory " + "\""
								+ subDir.getName() + "\"" + " is deleted successfully ");
						f.delete();
					}
				}
				if (subDir.isDirectory()) {
					System.out.println(
							"Old sub directory " + "\"" + subDir.getName() + "\"" + " is also deleted successfully");
				} else {
					System.out.println("Old file " + "\"" + subDir.getName() + "\"" + "\"" + " in main directory "
							+ "\"" + dirName.getName() + "\"" + " is also deleted successfully");
				}

				subDir.delete();
			}
			dirName.delete();
			dirName.mkdir();

			System.out.println(
					"New Directory " + dirName.getName() + " is created successfully on Today's date " + todayDate);
		} else if (!dirName.exists()) {
			dirName.mkdir();
			System.out.println(
					"New Directory " + dirName.getName() + " is created successfully on Today's date " + todayDate);
		} else {
			System.out.println("Directory " + dirName.getName() + " is existing already on Today's date " + fileTime);
		}
	}

	private String getStringFromFileTime() {
		Path path = dirName.toPath();
		BasicFileAttributes attributes = null;

		try {
			attributes = Files.readAttributes(path, BasicFileAttributes.class);
		} catch (NoSuchFileException e) {
			dateTime = null;
			return dateTime;
		} catch (IOException e) {

			e.printStackTrace();
		}
		FileTime time = attributes.creationTime();
		long milliseconds = time.toMillis();
		date.setTime(milliseconds);
		dateTime = createDateFormat.format(date);
		return dateTime;

	}

	private String getTodayDate() {
		todayDate = createDateFormat.format(date);
		return todayDate;

	}

}
