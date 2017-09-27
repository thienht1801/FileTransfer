package com.predix.iot.fileTransfer.helper;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class SortFilesHelper {

	public static void sortFilesByName(File[] files) {
		Arrays.sort(files, new Comparator<File>() { 
		    public int compare(File f1, File f2) {
		        return (f1.getName()).compareTo(f2.getName());
		    }
		});	
	}
	
	public static void sortFilesBySize(File[] files) {
		Arrays.sort(files, new Comparator<File>() { 
		    public int compare(File f1, File f2) {
		    	if(f1.length() > f2.length()){
		    		return 1;
		    	}else{
		    		return 0;
		    	}
		    }
		});
	}
	
	public static void sortFilesByDate(File[] files) {
		Arrays.sort(files, new Comparator<File>() { 
		    public int compare(File f1, File f2) {
		    	return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
		    }
		});
	}
}
