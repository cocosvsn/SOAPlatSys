package com.soaplat.cmsmgr.util;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

public class SmbFileUtils {
	/**
     * The number of bytes in a kilobyte.
     */
    public static final long ONE_KB = 1024;

    /**
     * The number of bytes in a megabyte.
     */
    public static final long ONE_MB = ONE_KB * ONE_KB;

    /**
     * The number of bytes in a gigabyte.
     */
    public static final long ONE_GB = ONE_KB * ONE_MB;

    /**
     * An empty array of type <code>File</code>.
     */
    public static final File[] EMPTY_FILE_ARRAY = new File[0];

    /**
     * Returns the size of the specified file or directory. If the provided 
     * {@link File} is a regular file, then the file's length is returned.
     * If the argument is a directory, then the size of the directory is
     * calculated recursively. If a directory or subdirectory is security 
     * restricted, its size will not be included.
     * 
     * @param file the regular file or directory to return the size 
     *        of (must not be <code>null</code>).
     * 
     * @return the length of the file, or recursive size of the directory, 
     *         provided (in bytes).
     * @throws SmbException 
     * 
     * @throws NullPointerException if the file is <code>null</code>
     * @throws IllegalArgumentException if the file does not exist.
     *         
     * @since Commons IO 2.0
     */
    public static long sizeOf(SmbFile file) throws SmbException {

        if (!file.exists()) {
            String message = file + " does not exist";
            throw new IllegalArgumentException(message);
        }

        if (file.isDirectory()) {
            return sizeOfDirectory(file);
        } else {
            return file.length();
        }

    }

    /**
     * Counts the size of a directory recursively (sum of the length of all files).
     *
     * @param directory  directory to inspect, must not be <code>null</code>
     * @return size of directory in bytes, 0 if directory is security restricted
     * @throws SmbException 
     * @throws NullPointerException if the directory is <code>null</code>
     */
    private static long sizeOfDirectory(SmbFile directory) throws SmbException {
//        if (!directory.exists()) {
//            String message = directory + " does not exist";
//            throw new IllegalArgumentException(message);
//        }
//
//        if (!directory.isDirectory()) {
//            String message = directory + " is not a directory";
//            throw new IllegalArgumentException(message);
//        }

        long size = 0;

        SmbFile[] files = directory.listFiles();
        if (files == null) {  // null if security restricted
            return 0L;
        }
        for (SmbFile file : files) {
            size += sizeOf(file);
        }

        return size;
    }
    
    /**
     * Returns a human-readable version of the file size, where the input
     * represents a specific number of bytes.
     * 
     * If the size is over 1GB, the size is returned as the number of whole GB,
     * i.e. the size is rounded down to the nearest GB boundary.
     * 
     * Similarly for the 1MB and 1KB boundaries.
     *
     * @param size  the number of bytes
     * @return a human-readable display value (includes units - GB, MB, KB or bytes)
     */
    public static String byteCountToDisplaySize(long size) {
    	DecimalFormat decimalFormat;
        String displaySize;

        if (size / ONE_GB > 0) {
            decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setRoundingMode(RoundingMode.FLOOR);
            displaySize = decimalFormat.format(size * 1.0 / ONE_GB) + " GB";
        } else if (size / ONE_MB > 0) {
            decimalFormat = new DecimalFormat("#.#");
            decimalFormat.setRoundingMode(RoundingMode.FLOOR);
            displaySize = decimalFormat.format(size * 1.0 / ONE_MB) + " MB";
        } else if (size / ONE_KB > 0) {
            decimalFormat = new DecimalFormat("#");
            decimalFormat.setRoundingMode(RoundingMode.FLOOR);
            displaySize = decimalFormat.format(size * 1.0 / ONE_KB) + " KB";
        } else {
            displaySize = String.valueOf(size) + " bytes";
        }
        return displaySize;
    }
    
    public static void main(String[] args) throws IOException {
		SmbFile smbFile = new SmbFile("smb://administrator:123456@10.30.1.201/broadcast/Online/RMZIP/PRRN201107/PRRN20110705141310000733001001/000000001486/PRRN20110705141310000733001001/");
		long size = sizeOfDirectory(smbFile);
		System.out.println(SmbFileUtils.sizeOf(smbFile));
		System.out.println(size);
		System.out.println(byteCountToDisplaySize(size));
		
	}
}
