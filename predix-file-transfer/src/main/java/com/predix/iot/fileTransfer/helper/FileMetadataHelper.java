package com.predix.iot.fileTransfer.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.predix.iot.fileTransfer.model.FileMetadata;

public class FileMetadataHelper {
	/**
	 * 
	 */
	public static List<FileMetadata> generateMetadata(File[] files, List<String> sentFileNames){
		List<File> unsentFiles = new ArrayList<File>();
		List<FileMetadata> metadataList = new ArrayList<FileMetadata>();
		
		for(File file : files){
			boolean unsent = true;
			for(String sentFileName : sentFileNames){
				if(sentFileName.equals(file.getName())){
					unsent = false;
				}
			}
			if(unsent){
				unsentFiles.add(file);
			}
		}	
		
		for(File file : unsentFiles){
			FileMetadata aMetadataFile = new FileMetadata();
			aMetadataFile = getFileMetadata(file);
			metadataList.add(aMetadataFile);
		}
		
		return metadataList;
	}
	
	private static FileMetadata getFileMetadata(File file) {
		
		FileMetadata fileMeta = new FileMetadata();
		fileMeta.setFileName(file.getName());
		fileMeta.setFilePath(file.getAbsolutePath());
		fileMeta.setFileSize(String.valueOf(file.length()));
		fileMeta.setLastModifiedTime(String.valueOf(file.lastModified()));
		
		return fileMeta;
	}
}
