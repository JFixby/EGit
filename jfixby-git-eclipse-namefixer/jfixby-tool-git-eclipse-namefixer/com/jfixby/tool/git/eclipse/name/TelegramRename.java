
package com.jfixby.tool.git.eclipse.name;

import java.io.IOException;

import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.desktop.DesktopSetup;
import com.jfixby.scarabei.api.file.ChildrenList;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.FileConverter;
import com.jfixby.scarabei.api.file.FolderConverter;
import com.jfixby.scarabei.api.file.LocalFileSystem;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.util.JUtils;

public class TelegramRename {

	static boolean WRITE_MODE = true;

	public static void main (final String[] args) throws IOException {
		DesktopSetup.deploy();

		final Map<String, String> termsMapping = Collections.newMap();
		termsMapping.put("tinto", "telecam");
		termsMapping.put("Tinto", "Telecam");

		final String java_input_path = "D:\\[DEV]\\[GIT]\\Tinto";
		final File input_folder = LocalFileSystem.newFile(java_input_path);

		final String java_output_path = "D:\\[DEV]\\[GIT]\\Telecam";
		final File ouput_folder = LocalFileSystem.newFile(java_output_path);

		final ChildrenList files_to_delete = ouput_folder.listDirectChildren(file -> {
			if (file.getName().equals(".git")) {
				return false;
			}
			if (file.getName().equals("README.md")) {
				return false;
			}
			return true;
		});

		files_to_delete.print("to delete");
//
		files_to_delete.deleteAll();
// Sys.exit();
		final FileConverter fileConverter = (inputFile, outputFile) -> {
			if (inputFile.getName().equals("README.md")) {
				return false;
			}
			if (inputFile.extensionIs("java")) {
				convertJava(inputFile, outputFile, termsMapping);
				L.d("convert file", inputFile + " -> " + outputFile);
			} else if (inputFile.extensionIs("project")) {
				convertWinText(inputFile, outputFile, termsMapping);
				L.d("convert file", inputFile + " -> " + outputFile);
			} else if (inputFile.extensionIs("classpath")) {
				convertWinText(inputFile, outputFile, termsMapping);
				L.d("convert file", inputFile + " -> " + outputFile);
			} else {
				outputFile.getFileSystem().copyFileToFile(inputFile, outputFile);
// L.d("copy file", inputFile + " -> " + outputFile);
			}
			final String oldName = outputFile.getName();
			final String newName = JUtils.replaceAll(oldName, termsMapping);
			outputFile.rename(newName);

			return false;
		};

		final FolderConverter folderConverter = (inputFolder, outputFolder) -> {
			if (inputFolder.getName().equals("bin")) {
				return false;
			}
			if (inputFolder.getName().equals(".git")) {
				return false;
			}
			outputFolder.makeFolder();
			final String oldName = outputFolder.getName();
			final String newName = JUtils.replaceAll(oldName, termsMapping);
			if (!oldName.equals(newName)) {
				outputFolder.rename(newName);
			}
			return true;

		};

		ouput_folder.getFileSystem().convertFolderToFolder(input_folder, ouput_folder, folderConverter, fileConverter);

	}

	private static void convertWinText (final File inputFile, final File outputFile, final Map<String, String> termsMapping)
		throws IOException {
		final String dataString = inputFile.readToString("windows-1251");
		final String newDataString = JUtils.replaceAll(dataString, termsMapping);
		outputFile.writeString(newDataString);
	}

	private static void convertJava (final File inputFile, final File outputFile, final Map<String, String> termsMapping)
		throws IOException {

		final String dataString = inputFile.readToString();
		final String newDataString = JUtils.replaceAll(dataString, termsMapping);
		outputFile.writeString(newDataString);

	}

}
