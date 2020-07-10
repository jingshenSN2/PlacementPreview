package sn2.slabhelper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class VersionCheck {
	public static void check() {
		URL updateURL = null;
		BufferedReader in = null;
		String version = "";
		try {
			updateURL = new URL("https://raw.githubusercontent.com/jingshenSN2/SlabHelper/master/VERSION");
			in = new BufferedReader(new InputStreamReader(updateURL.openStream()));
			version = in.readLine();
		} catch (Exception e) {
			System.out.println("Fail to check update! Please check your network connection.");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
		if (!version.equals(SlabHelper.VERSION)) {
			System.out.println("Update " + version + " found!");
		} 
	}
}
