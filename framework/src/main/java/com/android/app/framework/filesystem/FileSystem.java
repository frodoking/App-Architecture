package com.android.app.framework.filesystem;

import java.io.File;
import java.io.IOException;

import com.android.app.framework.controller.ChildSystem;

/**
 * 文件系统
 * Created by frodo on 2015/6/20.
 */
public interface FileSystem extends ChildSystem {

    String getRootDir();

    String getFilePath();

    /**
     * 在FS上创建目录
     *
     * @param dirName
     */
    File createDir(String dirName);

    /**
     * 删除SD卡上的目录
     *
     * @param dirName
     */
    boolean deleteDir(String dirName);

    /**
     * 在FS上创建文件
     *
     * @throws IOException
     */
    File createFile(String fileName) throws IOException;

    /**
     * 删除SD卡上的文件
     *
     * @param fileName
     */
    boolean deleteFile(String fileName);

    /**
     * 修改FS的文件或目录名
     *
     * @param oldFileName
     * @param newFileName
     *
     * @return
     */
    boolean renameFile(String oldFileName, String newFileName);

    /**
     * 拷贝FS的单个文件
     *
     * @param srcFileName
     * @param destFileName
     *
     * @return
     *
     * @throws IOException
     */
    boolean copyFileTo(String srcFileName, String destFileName) throws IOException;

    /**
     * 拷贝FS指定目录的所有文件
     *
     * @param srcDirName
     * @param destDirName
     *
     * @return
     *
     * @throws IOException
     */
    boolean copyFilesTo(String srcDirName, String destDirName) throws IOException;

    /**
     * 移动FS上的单个文件
     *
     * @param srcFileName
     * @param destFileName
     *
     * @return
     *
     * @throws IOException
     */
    boolean moveFileTo(String srcFileName, String destFileName) throws IOException;

    /**
     * 移动FS上的指定目录的所有文件
     *
     * @param srcDirName
     * @param destDirName
     *
     * @return
     *
     * @throws IOException
     */
    boolean moveFilesTo(String srcDirName, String destDirName) throws IOException;

    /**
     * 将文件写入FS。如:writeFile("test.txt");
     *
     * @param fileName
     *
     * @return
     *
     * @throws IOException
     */
    Output writeFile(String fileName) throws IOException;

    /**
     * 在原有文件上继续写文件。如:appendSDFile("test.txt");
     *
     * @param fileName
     *
     * @return
     *
     * @throws IOException
     */
    Output appendFile(String fileName) throws IOException;

    /**
     * 从FS读取文件。如:readFile("test.txt");
     *
     * @param fileName
     *
     * @return
     *
     * @throws IOException
     */
    Input readFile(String fileName) throws IOException;

    /**
     * 建立私有文件
     *
     * @param fileName
     *
     * @return
     *
     * @throws IOException
     */
    File createDataFile(String fileName) throws IOException;

    /**
     * 建立私有目录
     *
     * @param dirName
     *
     * @return
     */
    File createDataDir(String dirName);

    /**
     * 删除私有文件
     *
     * @param fileName
     *
     * @return
     */
    boolean deleteDataFile(String fileName);

    /**
     * 删除私有目录
     *
     * @param dirName
     *
     * @return
     */
    boolean deleteDataDir(String dirName);

    /**
     * 更改私有文件名
     *
     * @param oldName
     * @param newName
     *
     * @return
     */
    boolean renameDataFile(String oldName, String newName);

    /**
     * 在私有目录下进行文件复制
     *
     * @param srcFileName  ： 包含路径及文件名
     * @param destFileName
     *
     * @return
     *
     * @throws IOException
     */
    boolean copyDataFileTo(String srcFileName, String destFileName) throws IOException;

    /**
     * 复制私有目录里指定目录的所有文件
     *
     * @param srcDirName
     * @param destDirName
     *
     * @return
     *
     * @throws IOException
     */
    boolean copyDataFilesTo(String srcDirName, String destDirName) throws IOException;

    /**
     * 移动私有目录下的单个文件
     *
     * @param srcFileName
     * @param destFileName
     *
     * @return
     *
     * @throws IOException
     */
    boolean moveDataFileTo(String srcFileName, String destFileName) throws IOException;

    /**
     * 移动私有目录下的指定目录下的所有文件
     *
     * @param srcDirName
     * @param destDirName
     *
     * @return
     *
     * @throws IOException
     */
    boolean moveDataFilesTo(String srcDirName, String destDirName) throws IOException;

    /**
     * 删除一个文件
     *
     * @param file
     *
     * @return
     */
    boolean deleteFile(File file);

    /**
     * 删除一个目录（可以是非空目录）
     *
     * @param dir
     */
    boolean deleteDir(File dir);

    /**
     * 拷贝一个文件,srcFile源文件，destFile目标文件
     *
     * @param srcFile
     * @param destFile
     *
     * @return
     *
     * @throws IOException
     */
    boolean copyFileTo(File srcFile, File destFile) throws IOException;

    /**
     * 拷贝目录下的所有文件到指定目录
     *
     * @param srcDir
     * @param destDir
     *
     * @return
     *
     * @throws IOException
     */
    boolean copyFilesTo(File srcDir, File destDir) throws IOException;

    /**
     * 移动一个文件
     *
     * @param srcFile
     * @param destFile
     *
     * @return
     *
     * @throws IOException
     */
    boolean moveFileTo(File srcFile, File destFile) throws IOException;

    /**
     * 移动目录下的所有文件到指定目录
     *
     * @param srcDir
     * @param destDir
     *
     * @return
     *
     * @throws IOException
     */
    boolean moveFilesTo(File srcDir, File destDir) throws IOException;

    boolean write(String text, File file);
}
