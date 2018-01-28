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

public class NewDirectoryTest {
	File dirName = new File("C:\\testdir\\TestReports");
	@Test
	public void createDir() {
		if (dirName.exists()) {

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
			System.out.println("New Directory " + dirName.getName() + " is created successfully on Today's date ");
		} else {
			dirName.mkdir();
		}
	}

}
