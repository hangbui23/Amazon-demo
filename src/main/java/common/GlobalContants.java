package common;

public class GlobalContants {
	public static final long LONG_TIMEOUT=30;
	public static final long SHORT_TIMEOUT=3;
	public static final String URL="https://www.amazon.com/";
	public static final String PROJECTFOLDER = System.getProperty("user.dir");
	public static final String OSNAME = System.getProperty("os.name");
	public static final String BROWSER_LOG_FOLDER = PROJECTFOLDER + getDirectorySlash("browserLog");
	public static final String DOWNLOAD_FOLDER = PROJECTFOLDER + getDirectorySlash("downloadFile");
	public static final String UPLOAD_FOLDER = PROJECTFOLDER + getDirectorySlash("uploadFile");
	public static final String AUTOIT_FOLDER = PROJECTFOLDER + getDirectorySlash("autoITFolder");
	public static final String DB_URL="jdbc://h";
	public static String getDirectorySlash(String folderName) {
		if (isMac() || isUnix() || isSolaris()) {
			folderName = "/" + folderName + "/";
		} else {
			folderName = "\\" + folderName + "\\";
		}
		return folderName;
	}

	public static boolean isWindows() {
		return (OSNAME.toLowerCase().indexOf("win") >= 0);
	}

	public static boolean isMac() {
		return (OSNAME.toLowerCase().indexOf("mac") >= 0);
	}

	public static boolean isUnix() {
		return (OSNAME.toLowerCase().indexOf("nix") >= 0 || OSNAME.toLowerCase().indexOf("nux") >= 0 || OSNAME.toLowerCase().indexOf("aix") > 0);
	}

	public static boolean isSolaris() {
		return (OSNAME.toLowerCase().indexOf("sunos") >= 0);
	}
}
