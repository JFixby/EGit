
package com.jfixby.tool.git.eclipse.name;

import java.io.IOException;

import com.jfixby.scarabei.api.desktop.DesktopSetup;
import com.jfixby.scarabei.api.file.ChildrenList;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.LocalFileSystem;

public class TelegramclassPath {

	static boolean WRITE_MODE = !true;

	public static void main (final String[] args) throws IOException {
		DesktopSetup.deploy();

		final String java_path = "D:\\[DEV]\\[GIT]\\Telegram";
		final File tele_folder = LocalFileSystem.newFile(java_path);
		scanFolder(tele_folder);

	}

	private static void scanFolder (final File folder) throws IOException {
		final ChildrenList children = folder.listDirectChildren();
		final File project_file = children.findChild(".project");
		if (project_file != null) {
			// L.d("found", project_file);
			check(folder, project_file);
			return;
		}

		for (int i = 0; i < children.size(); i++) {
			final File child = children.getElementAt(i);
			if (child.isFolder()) {
				scanFolder(child);
				continue;
			}
			if (child.isFile()) {
				continue;
			}
		}
	}

	private static void check (final File folder, final File project_file) throws IOException {
		final String folder_name = folder.getName();
		final String data = project_file.readToString();
		final String NAME_OPEN = "<name>";
		final String NAME_CLOSE = "</name>";
		final int open = data.indexOf(NAME_OPEN) + NAME_OPEN.length();
		final int close = data.indexOf(NAME_CLOSE);
		final String project_name = data.substring(open, close);
		final File classpath = project_file.parent().child(".classpath");

// if (!project_name.equals(folder_new_name)) {
// L.d("renaming", project_name);
// L.d(" from", project_name);
// L.d(" to", folder_new_name);
// if (WRITE_MODE) {
// data = data.replaceAll(">" + project_name + "<", ">" + folder_new_name + "<");
// project_file.writeString(data);
// folder.rename(folder_new_name);
// }
// L.d();
// }
	}
}
