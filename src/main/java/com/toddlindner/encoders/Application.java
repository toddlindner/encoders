package com.toddlindner.encoders;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Application {

	public static void main(String[] args) {
		new Application().run();
	}

	private final String home = System.getProperty("user.home");
	private final Path input = Paths.get(home, "Videos\\Input");
	private final Path encodes = Paths.get(home, "Videos\\Encodes");

	private void run() {
		Arrays.stream(input.toFile().listFiles())
			.filter(f -> f.getName().endsWith(".mkv"))
			.forEach(this::process);
	}

	private void process(File f) {
		var name = f.getName();
		name = StringUtils.removeEndIgnoreCase(name, ".mkv");

		String sub;
		do {
			sub = StringUtils.substringBetween(name, "(", ")");
			if (sub != null && sub.length() != 4) {
				name = StringUtils.remove(name, "(" + sub + ")");
			} else {
				name = StringUtils.replace(name, "(" + sub + ")", "[" + sub + "]");
			}
		} while (sub != null);

		name = name.trim() + ".mp4";

		encodes.toFile().mkdirs();
		var dest = Path.of(encodes.toString(), name);

		Demux.builder()
			.input(f)
			.output(dest.toFile())
			.build()
			.run();
	}
}
