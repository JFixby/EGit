package com.jfixby.tool.git.eclipse.name;

import java.io.IOException;

import com.jfixby.cmns.api.desktop.DesktopSetup;
import com.jfixby.cmns.api.file.ChildrenList;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.log.L;

public class ResetSettings {

    static boolean WRITE_MODE = true;

    public static void main(String[] args) throws IOException {
	DesktopSetup.deploy();

	String java_path = "D:\\[DEV]\\[GIT]";
	File git_folder = LocalFileSystem.newFile(java_path);
	scanFolder(git_folder);

    }

    private static void scanFolder(File folder) throws IOException {
	ChildrenList children = folder.listDirectChildren();
	File settings_file = children.findChild(".settings");
	if (settings_file != null) {
	    // L.d("found", project_file);
	    check(folder, settings_file);
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

    private static void check(File folder, File settings_file) throws IOException {
	L.d("deleting", settings_file);
	L.d("    from", folder.getName());
	if (WRITE_MODE) {
	    settings_file.delete();
	}
	L.d();

	// String folder_name = folder.getName();
	// String data = project_file.readToString();
	// String NAME_OPEN = "<name>";
	// String NAME_CLOSE = "</name>";
	// int open = data.indexOf(NAME_OPEN) + NAME_OPEN.length();
	// int close = data.indexOf(NAME_CLOSE);
	// String project_name = data.substring(open, close);
	// if (!project_name.equals(folder_name)) {
	// L.d("renaming", folder);
	// L.d(" from", folder_name);
	// L.d(" to", project_name);
	// if (WRITE_MODE) {
	// folder.rename(project_name);
	// }
	// L.d();
	// }
    }
}
