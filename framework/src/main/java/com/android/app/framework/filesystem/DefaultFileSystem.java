package com.android.app.framework.filesystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.android.app.framework.controller.AbstractChildSystem;
import com.android.app.framework.controller.IController;

/**
 * 默认java 文件操作
 * Created by frodo on 2015/7/4.
 */
public class DefaultFileSystem extends AbstractChildSystem implements FileSystem{

    private String rootDir;
    private String filePath;
    public DefaultFileSystem(IController controller, String rootDir, String filePath) {
        super(controller);
        this.rootDir = rootDir;
        this.filePath = filePath;
    }


    @Override
    public File createFile(String fileName) throws IOException {
        File file = new File(rootDir + fileName);
        file.createNewFile();
        return file;
    }

    @Override
    public boolean deleteFile(String fileName) {
        File file = new File(rootDir + fileName);
        if (!file.exists() || file.isDirectory()) {
            return false;
        }
        file.delete();
        return true;
    }

    @Override
    public File createDir(String dirName) {
        File dir = new File(rootDir + dirName);
        dir.mkdir();
        return dir;
    }

    @Override
    public boolean deleteDir(String dirName) {
        File dir = new File(rootDir + dirName);
        return deleteDir(dir);
    }

    @Override
    public boolean renameFile(String oldFileName, String newFileName) {
        File oleFile = new File(rootDir + oldFileName);
        File newFile = new File(rootDir + newFileName);
        return oleFile.renameTo(newFile);
    }

    @Override
    public boolean copyFileTo(String srcFileName, String destFileName) throws IOException {
        File srcFile = new File(rootDir + srcFileName);
        File destFile = new File(rootDir + destFileName);
        return copyFileTo(srcFile, destFile);
    }

    @Override
    public boolean copyFilesTo(String srcDirName, String destDirName) throws IOException {
        File srcDir = new File(rootDir + srcDirName);
        File destDir = new File(rootDir + destDirName);
        return copyFilesTo(srcDir, destDir);
    }

    @Override
    public boolean moveFileTo(String srcFileName, String destFileName)
            throws IOException {
        File srcFile = new File(rootDir + srcFileName);
        File destFile = new File(rootDir + destFileName);
        return moveFileTo(srcFile, destFile);
    }

    @Override
    public boolean moveFilesTo(String srcDirName, String destDirName)  throws IOException {
        File srcDir = new File(rootDir + srcDirName);
        File destDir = new File(rootDir + destDirName);
        return moveFilesTo(srcDir, destDir);
    }

    @Override
    public Output writeFile(String fileName) throws IOException {
        File file = new File(rootDir + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        return new Output(fos);
    }

    @Override
    public Output appendFile(String fileName) throws IOException {
        File file = new File(rootDir + fileName);
        FileOutputStream fos = new FileOutputStream(file, true);
        return new Output(fos);
    }

    @Override
    public Input readFile(String fileName) throws IOException {
        File file = new File(rootDir + fileName);
        FileInputStream fis = new FileInputStream(file);
        return new Input(fis);
    }

    @Override
    public File createDataFile(String fileName) throws IOException {
        File file = new File(filePath + fileName);
        file.createNewFile();
        return file;
    }

    @Override
    public File createDataDir(String dirName) {
        File dir = new File(filePath + dirName);
        dir.mkdir();
        return dir;
    }

    @Override
    public boolean deleteDataFile(String fileName) {
        File file = new File(filePath + fileName);
        return deleteDir(file);
    }

    @Override
    public boolean deleteDataDir(String dirName) {
        File file = new File(filePath + dirName);
        return deleteDir(file);
    }

    @Override
    public boolean renameDataFile(String oldName, String newName) {
        File oldFile = new File(filePath + oldName);
        File newFile = new File(filePath + newName);
        return oldFile.renameTo(newFile);
    }

    @Override
    public boolean copyDataFileTo(String srcFileName, String destFileName) throws IOException {
        File srcFile = new File(filePath + srcFileName);
        File destFile = new File(filePath + destFileName);
        return copyFileTo(srcFile, destFile);
    }

    @Override
    public boolean copyDataFilesTo(String srcDirName, String destDirName)  throws IOException {
        File srcDir = new File(filePath + srcDirName);
        File destDir = new File(filePath + destDirName);
        return copyFilesTo(srcDir, destDir);
    }

    @Override
    public boolean moveDataFileTo(String srcFileName, String destFileName) throws IOException {
        File srcFile = new File(filePath + srcFileName);
        File destFile = new File(filePath + destFileName);
        return moveFileTo(srcFile, destFile);
    }

    @Override
    public boolean moveDataFilesTo(String srcDirName, String destDirName) throws IOException {
        File srcDir = new File(filePath + srcDirName);
        File destDir = new File(filePath + destDirName);
        return moveFilesTo(srcDir, destDir);
    }

    @Override
    public boolean deleteFile(File file) {
        if (file.isDirectory()) {
            return false;
        }
        return file.delete();
    }

    @Override
    public boolean deleteDir(File dir) {
        if (dir == null || !dir.exists() || dir.isFile()) {
            return false;
        }
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                deleteDir(file);// 递归
            }
        }
        dir.delete();
        return true;
    }

    @Override
    public boolean copyFileTo(File srcFile, File destFile) throws IOException {
        if (srcFile.isDirectory() || destFile.isDirectory()) {
            return false;// 判断是否是文件
        }
        FileInputStream fis = new FileInputStream(srcFile);
        FileOutputStream fos = new FileOutputStream(destFile);
        int readLen = 0;
        byte[] buf = new byte[1024];
        while ((readLen = fis.read(buf)) != -1) {
            fos.write(buf, 0, readLen);
        }
        fos.flush();
        fos.close();
        fis.close();
        return true;
    }

    @Override
    public boolean copyFilesTo(File srcDir, File destDir) throws IOException {
        if (!srcDir.isDirectory() || !destDir.isDirectory()) {
            return false;// 判断是否是目录
        }
        if (!destDir.exists()) {
            return false;// 判断目标目录是否存在
        }
        File[] srcFiles = srcDir.listFiles();
        for (int i = 0; i < srcFiles.length; i++) {
            if (srcFiles[i].isFile()) {
                // 获得目标文件
                File destFile = new File(destDir.getPath() + "//"
                        + srcFiles[i].getName());
                copyFileTo(srcFiles[i], destFile);
            } else if (srcFiles[i].isDirectory()) {
                File theDestDir = new File(destDir.getPath() + "//"
                        + srcFiles[i].getName());
                copyFilesTo(srcFiles[i], theDestDir);
            }
        }
        return true;
    }

    @Override
    public boolean moveFileTo(File srcFile, File destFile) throws IOException {
        boolean isCopy = copyFileTo(srcFile, destFile);
        if (!isCopy) {
            return false;
        }
        deleteFile(srcFile);
        return true;
    }

    @Override
    public boolean moveFilesTo(File srcDir, File destDir) throws IOException {
        if (!srcDir.isDirectory() || !destDir.isDirectory()) {
            return false;
        }
        File[] srcDirFiles = srcDir.listFiles();
        for (int i = 0; i < srcDirFiles.length; i++) {
            if (srcDirFiles[i].isFile()) {
                File oneDestFile = new File(destDir.getPath() + "//"
                        + srcDirFiles[i].getName());
                moveFileTo(srcDirFiles[i], oneDestFile);
                deleteFile(srcDirFiles[i]);
            } else if (srcDirFiles[i].isDirectory()) {
                File oneDestFile = new File(destDir.getPath() + "//"
                        + srcDirFiles[i].getName());
                moveFilesTo(srcDirFiles[i], oneDestFile);
                deleteFile(srcDirFiles[i]);
            }

        }
        return true;
    }
}
