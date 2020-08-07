package sn2.slabhelper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class VersionCheck {
	public static String check() {
		URL updateURL = null;
		BufferedReader in = null;
		String version = null;
		try {
			updateURL = new URL("https://raw.githubusercontent.com/jingshenSN2/SlabHelper/master/VERSION");
			in = new BufferedReader(new InputStreamReader(updateURL.openStream()));
			version = in.readLine();
		} catch (Exception e) {
			// fail to connect
			SlabHelper.LOGGER.info("Fail to check update! Please check your network connection.");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
		// need update
		if (version != null && !version.equals(SlabHelper.VERSION)) {
			String update = "Update " + version
					+ " found! Download the latest version at https://www.curseforge.com/minecraft/mc-mods/slab-helper";
			SlabHelper.LOGGER.info(update);
			return version;
		}
		return null;
	}

}
