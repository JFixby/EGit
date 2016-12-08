package com.jfixby.tool.git.eclipse.name;

import java.io.IOException;

import com.jfixby.cmns.api.desktop.DesktopSetup;
import com.jfixby.cmns.api.file.ChildrenList;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.log.L;

public class AddGitIgnore {

	static boolean WRITE_MODE = true;

	public static void main(String[] args) throws IOException {
		DesktopSetup.deploy();

		String java_path = "D:\\[DEV]\\[GIT-2]";
		File git_folder = LocalFileSystem.newFile(java_path);
		scanFolder(git_folder);

	}

	private static void scanFolder(File folder) throws IOException {
		ChildrenList children = folder.listDirectChildren();

		File git_ignore = children.findChild(".gitignore");

		if (git_ignore != null) {
			L.d("delete", git_ignore);
			 git_ignore.delete();
		}

		File project_file = children.findChild(".project");
		if (project_file != null) {
			// L.d("found", project_file);
			check(folder, project_file);
			return;
		}

		for (int i = 0; i < children.size(); i++) {
			File child = children.getElementAt(i);
			if (child.isFolder()) {
				scanFolder(child);
				continue;
			}
			if (child.isFile()) {
				continue;
			}
		}
	}

	private static void check(File folder, File project_file) throws IOException {
		File git_ignore = project_file.parent().child(".gitignore");
		// if (!git_ignore.exists()) {
		// return;
		// }
		// L.d("found git ignore", git_ignore);
		// String data = git_ignore.readToString();
		// L.d("data", data);
		git_ignore.writeString("/bin/");
		L.d("write", git_ignore);

	}
}
