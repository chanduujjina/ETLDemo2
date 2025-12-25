package com.demo.connector;

import org.apache.beam.sdk.io.Compression;
import org.apache.beam.sdk.io.FileIO.Write.FileNaming;
import org.apache.beam.sdk.transforms.windowing.BoundedWindow;
import org.apache.beam.sdk.transforms.windowing.PaneInfo;

public class FileNameUtil implements FileNaming {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileNameUtil(String prfix, String suffix) {
		super();
		this.prfix = prfix;
		this.suffix = suffix;
	}

	private String prfix;
	
	private String suffix;

	@Override
	public String getFilename(BoundedWindow window, PaneInfo pane, int numShards, int shardIndex,
			Compression compression) {
		
		
		return String.format("%s-%05d-of-%05d%s", prfix,numShards,shardIndex,suffix);
	}

}
