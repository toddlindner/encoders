package com.toddlindner.encoders;

import lombok.Builder;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.File;

// http://avidemux.sourceforge.net/doc/en/command.xml.html
@Builder
public class Demux {

	@NonNull
	private File input;
	@NonNull
	private File output;

	private String command() {
		return "C:\\Program Files\\Avidemux 2.7 VC++ 64bits\\avidemux_cli.exe\" --load \"" + input.getAbsolutePath() + "\" --output-format MP4 --video-process --video-codec Copy --save \"" + output.getAbsolutePath() + "\" --quit";
	}

	@SneakyThrows
	public int run() {
		var c = command();
		System.out.println(c);
		return new ProcessBuilder().command(c).inheritIO().start().waitFor();
	}
}
