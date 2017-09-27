package com.predix.iot.fileTransfer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.predix.iot.fileTransfer.helper.FileMetadataHelper;
import com.predix.iot.fileTransfer.helper.SortFilesHelper;
import com.predix.iot.fileTransfer.model.FileMetadata;
import com.predix.iot.fileTransfer.model.ProcessOrder;

/**
 * @author Eric Huynh
 * @version 1.0
 *
 */
@Component
public class FileCollector {
	
	private static final Logger logger = LoggerFactory.getLogger(FileCollector.class);
	
	private final String path = "E:\\TEST_ADAPTOR\\test_folder";
	
	private final String sent_log_local = "E:\\TEST_ADAPTOR\\test_sent_log\\sentLog.txt";
	private final String sent_log_sftp = "";
	private final String sent_log_ftp = "";
	
	private String orderCode = "n";
	private String directoryType = "LOCAL";
	
	@Scheduled(fixedDelayString = "${scheduler.time}")
	public void pickupFile() {
		logger.info("Start pickup file");
		
		List<FileMetadata> metaList = new ArrayList<FileMetadata>();		
		List<String> sentFileNames = loadSentFileLogs();
		
		File directory = new File(path);
		File[] files = directory.listFiles();
		
		if(null != files && files.length > 0){
			if(orderCode.equals(ProcessOrder.BY_NAME.orderCode())){
				SortFilesHelper.sortFilesByName(files);
			}else if(orderCode.equals(ProcessOrder.BY_SIZE.orderCode())){
				SortFilesHelper.sortFilesBySize(files);
			}else if(orderCode.equals(ProcessOrder.BY_LAST_MODIFIED_DATE.orderCode())){
				SortFilesHelper.sortFilesByDate(files);
			}
			
			metaList = FileMetadataHelper.generateMetadata(files, sentFileNames);
		}

	}
	
	private List<String> loadSentFileLogs(){		
        try {
        	List<String> sentFileNames = new ArrayList<String>();
        	
        	String sent_log_path = "";
			if(directoryType.equalsIgnoreCase("LOCAL")){
				sent_log_path = sent_log_local;      
			} else if(directoryType.equalsIgnoreCase("SFTP")){
				sent_log_path = sent_log_sftp;	         
			} else if(directoryType.equalsIgnoreCase("FTP")){
				sent_log_path = sent_log_ftp;
			}	
        	
            File sentLogs = new File(sent_log_path);
            BufferedReader b = new BufferedReader(new FileReader(sentLogs));

            String readLine = "";

            logger.info("Reading sent log file");
            
            while (null != (readLine = b.readLine())) {
            	sentFileNames.add(readLine);
            }
            
            b.close();
            
            return sentFileNames;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
	}
}
