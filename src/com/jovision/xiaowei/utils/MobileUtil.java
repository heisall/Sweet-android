
package com.jovision.xiaowei.utils;

import android.os.Environment;
import android.os.StatFs;

import com.jovision.AppConsts;

import java.io.File;

public class MobileUtil {

    /**
     * 创建手机软件所需要的文件夹
     */
    public static void creatAllFolder() {
        createDirectory(AppConsts.LOG_CLOUD_PATH);
        createDirectory(AppConsts.LOG_ACCOUNT_PATH);
        createDirectory(AppConsts.CAPTURE_PATH);
        createDirectory(AppConsts.VIDEO_PATH);
        createDirectory(AppConsts.DOWNLOAD_VIDEO_PATH);
        createDirectory(AppConsts.BUG_PATH);
        createDirectory(AppConsts.HEAD_PATH);
        createDirectory(AppConsts.WELCOME_IMG_PATH);
        createDirectory(AppConsts.SCENE_PATH);
        createDirectory(AppConsts.CLOUDVIDEO_PATH);
        createDirectory(AppConsts.ALARM_IMG_PATH);
        createDirectory(AppConsts.ALARM_VIDEO_PATH);

    }

    /**
     * 递归创建文件目录
     * 
     * @author
     * @param path 要创建的目录路径
     */
    public static void createDirectory(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return;
        }
        File parentFile = file.getParentFile();
        if (null != file && parentFile.exists()) {
            if (parentFile.isDirectory()) {
            } else {
                parentFile.delete();
                boolean res = parentFile.mkdir();
                if (!res) {
                    parentFile.delete();
                }
            }

            boolean res = file.mkdir();
            if (!res) {
                file.delete();
            }

        } else {
            createDirectory(file.getParentFile().getAbsolutePath());
            boolean res = file.mkdir();
            if (!res) {
                file.delete();
            }
        }
    }

    /**
     * 递归删除文件和文件夹,清空文件夹
     * 
     * @param file 要删除的根目录
     */
    public static void deleteFile(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                deleteFile(f);
            }
            file.delete();
        }
    }

    /**
     * 获取剩余sdk卡空间
     * 
     * @return
     */
    public static long getSDFreeSize() {
        // 取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        // 获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        // 空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        // 返回SD卡空闲大小
        // return freeBlocks * blockSize; //单位Byte
        // return (freeBlocks * blockSize)/1024; //单位KB
        return (freeBlocks * blockSize) / 1024 / 1024; // 单位MB
    }
}
