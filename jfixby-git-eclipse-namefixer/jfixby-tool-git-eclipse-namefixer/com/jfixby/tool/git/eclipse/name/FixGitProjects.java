
package com.jfixby.tool.git.eclipse.name;

import java.io.IOException;

import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;
import com.jfixby.scarabei.api.file.FilesList;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.LocalFileSystem;
import com.jfixby.scarabei.api.log.L;

public class FixGitProjects {

	static boolean WRITE_MODE = true;

	public static void main (final String[] args) throws IOException {
		ScarabeiDesktop.deploy();

		final String java_path = "D:\\[DEV]\\[GIT]";
		final File git_folder = LocalFileSystem.newFile(java_path);
		scanFolder(git_folder);

	}

	private static void scanFolder (final File folder) throws IOException {
		final FilesList children = folder.listDirectChildren();
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
		if (!project_name.equals(folder_name)) {
			L.d("renaming", folder);
			L.d("    from", folder_name);
			L.d("      to", project_name);
			if (WRITE_MODE) {
				folder.rename(project_name);
			}
			L.d();
		}
	}
}
