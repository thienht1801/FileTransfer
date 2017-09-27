/*
 * 
 */
package com.predix.iot.fileTransfer.service;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/*
Implements a blob store, which stores the files on to Predix BLOB service
 */

/**
 * The Class BlobFileStoreServiceImpl.
 */
@Service
@Profile("dev")
public class FileStoreService {
    
    /** The log. */
    Log log = LogFactory.getLog(FileStoreService.class);

    /** The blobstore service. */
    @Autowired
    private BlobstoreService blobstoreService;

	public void uploadFile(String fileName, byte[] fileDataToUpload) {
        try {
            S3Object obj = new S3Object();
            String key = fileName;
            obj.setKey(key);
            obj.setObjectContent(new ByteArrayInputStream(fileDataToUpload));
            blobstoreService.put(obj);
        } catch (Exception e) {
            log.error("handleFileUpload();: Exception occurred : " + e.getMessage());
        }
    }

	public byte[] getFileData(final String fileUniqueId) {
        if(null == fileUniqueId || 0 == fileUniqueId.trim().length()) {
            throw new IllegalArgumentException("Invalid file"); //TODO : error code
        }

        try {
            InputStream is = blobstoreService.get(fileUniqueId);

            return IOUtils.toByteArray(is);
        } catch (Exception e) {
            log.error("handleFileUpload();: Exception occurred : " + e.getMessage());
            return new byte[0];
        }
	}
	
    @Transactional
    public void deleteFile(String fileUniqueId) {
        if(null == fileUniqueId || 0 == fileUniqueId.trim().length()) {
            throw new IllegalArgumentException("Invalid file"); //TODO : error code
        }
        blobstoreService.delete(fileUniqueId);
    }	
}
