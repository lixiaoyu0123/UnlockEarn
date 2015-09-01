package com.chinazmob.unlockearn.base;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

/**
 * Created by 晓宇 on 2015/3/25.
 */
public class ThreadManager {
    private static ThreadManager INSTANCE = null;
    private ExecutorService mfixedThreadPool = null;
    private int mcoreNum = 4;

    public static synchronized ThreadManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ThreadManager();
        }
        return INSTANCE;
    }

    public ThreadManager() {
        int cores = getNumCores();
        if(cores > 0){
            mcoreNum = cores * 2;
        }
        mfixedThreadPool = Executors.newFixedThreadPool(mcoreNum);
    }

    public ExecutorService getThreadPool(){
        return mfixedThreadPool;
    }

    private int getNumCores() {
        //Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                //Check if filename is "cpu", followed by a single digit number
                if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
            //Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            //Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            //Return the number of cores (virtual CPU devices)
            return files.length;
        } catch (Exception e) {
            e.printStackTrace();
            //Default to return 1 core
        }
        return 1;
    }
}
