package com.java8888.java9999.utils;

import android.content.Context;

import java.io.File;

public class FileUtil {


    // 递归删除目录及其内容的自定义函数
    public static void deleteRecursive(Context context) {
        deleteFile(context);
        // 获取应用数据目录
        String appDataDir = context.getApplicationInfo().dataDir;
        // 创建一个 File 对象来表示 SharedPreferences 目录
        File sharedPreferencesDir = new File(appDataDir, "shared_prefs");
        // 检查 SharedPreferences 目录是否存在
        if (sharedPreferencesDir.exists() && sharedPreferencesDir.isDirectory()) {
            File[] sharedPrefsFiles = sharedPreferencesDir.listFiles();
            if (sharedPrefsFiles != null) {
                for (File sharedPrefsFile : sharedPrefsFiles) {
                    if (sharedPrefsFile.isFile() && !sharedPrefsFile.getName().equals("firstsp.xml")) {
                        // 删除非 firstsp.xml 文件
                        sharedPrefsFile.delete();
                    }
                }
            }
        }
    }

    public static void deleteFile(Context context) {
        String appDataDir = context.getApplicationInfo().dataDir;

// 创建一个 File 对象来表示应用数据目录
        File appDataDirFile = new File(appDataDir);

// 检查目录是否存在，然后递归删除目录内的非 SharedPreferences 目录
        if (appDataDirFile.exists() && appDataDirFile.isDirectory()) {
            deleteNonSharedPreferencesDirectories(appDataDirFile);
        }
    }


    // 递归删除目录内的非 SharedPreferences 目录的自定义函数
    public static void deleteNonSharedPreferencesDirectories(File directory) {
        File[] subdirectories = directory.listFiles();
        if (subdirectories != null) {
            for (File subdirectory : subdirectories) {
                if (subdirectory.isDirectory() && !subdirectory.getName().equals("shared_prefs")) {
                    // 删除非 SharedPreferences 目录
                    deleteRecursive(subdirectory);
                }
            }
        }
    }

    // 递归删除目录及其内容的自定义函数（已经在前面提到过）
    public static boolean deleteRecursive(File file) {
        if (file.isDirectory()) {
            File[] contents = file.listFiles();
            if (contents != null) {
                for (File content : contents) {
                    deleteRecursive(content);
                }
            }
        }
        return file.delete();
    }
}
