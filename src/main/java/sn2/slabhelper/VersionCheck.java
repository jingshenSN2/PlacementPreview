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
			SlabHelper.LOGGER.info("Fail to check update! Please check your network connection.");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
		if (!version.equals(SlabHelper.VERSION)) {
			SlabHelper.LOGGER.info("Update " + version + " found! Download the latest version at https://www.curseforge.com/minecraft/mc-mods/slab-helper");
		} 
	}
}
