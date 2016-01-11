package com.frodo.app.framework.filesystem;

import com.frodo.app.framework.controller.ChildSystem;

import java.io.File;

/**
 * 文件系统
 * Created by frodo on 2015/6/20.
 */
public interface FileSystem extends ChildSystem {

    String getRootDir();

    String getFilePath();

    File createFile(String fileName);

    File createDirectory(String dirName);

    void writeToFile(File file, String fileContent);

    String readFileContent(File file);

    boolean exists(File file);

    void clearDirectory(File directory);
}
