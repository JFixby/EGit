
package com.jfixby.tool.git.eclipse.name;

import java.io.IOException;

import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.Map;
import com.jfixby.cmns.api.desktop.DesktopSetup;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.FileConverter;
import com.jfixby.cmns.api.file.FolderConverter;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.util.JUtils;

public class FindTinto {

	static boolean WRITE_MODE = true;

	public static void main (final String[] args) throws IOException {
		DesktopSetup.deploy();

		final Map<String, String> termsMapping = Collections.newMap();
		termsMapping.put("tinto", "telecam");
		termsMapping.put("Tinto", "Telecam");

		final String java_output_path = "D:\\[DEV]\\[GIT]\\Telecam";
		final File target_folder = LocalFileSystem.newFile(java_output_path);

// Sys.exit();
		final FileConverter fileConverter = (inputFile, outputFile) -> {
			if (inputFile.extensionIs("descriptor")) {
				return false;
			}
			if (inputFile.getName().endsWith(".turbo.txt")) {
				return false;
			}

			final String dataString = inputFile.readToString("windows-1251").toLowerCase();

			if (dataString.contains("tinto")) {
				L.d("found", inputFile);

				if (inputFile.extensionIs("xml")) {
					convertWinText(inputFile, outputFile, termsMapping);
				}
			}

			return false;
		};

		final FolderConverter folderConverter = (inputFolder, outputFolder) -> {
			if (inputFolder.getName().equals("bin")) {
				return false;
			}
			if (inputFolder.getName().equals("bank-telecam")) {
				return false;
			}

			return true;

		};

		target_folder.getFileSystem().convertFolderToFolder(target_folder, target_folder, folderConverter, fileConverter);

	}

	private static void convertWinText (final File inputFile, final File outputFile, final Map<String, String> termsMapping)
		throws IOException {
		inputFile.getFileSystem().copyFileToFile(inputFile, inputFile.parent().child(inputFile.getName() + ".turbo.txt"));
		final String dataString = inputFile.readToString();// "windows-1251"
		final String newDataString = JUtils.replaceAll(dataString, termsMapping);
		L.d(newDataString);
// outputFile.writeString(newDataString);
	}

}
