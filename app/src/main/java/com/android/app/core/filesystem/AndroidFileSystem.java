package com.android.app.core.filesystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.android.app.framework.controller.AbstractChildSystem;
import com.android.app.framework.controller.IController;
import com.android.app.framework.filesystem.FileSystem;

import android.content.Context;
import android.os.Environment;

/**
 * Android 文件管理系统
 * Created by frodo on 2015/6/20.
 */
public class AndroidFileSystem extends AbstractChildSystem implements FileSystem {

    private String sdcardPath; // SD卡路径
    private String filePath; //文件路径
    private Context context;

    public AndroidFileSystem(IController controller) {
        super(controller);
        this.context = (Context) controller.getContext();
        sdcardPath = Environment.getExternalStorageDirectory().getPath() + "//";
        filePath = this.context.getFilesDir().getPath() + "//";
    }

    /**
     * 在SD卡上创建文件
     *
     * @throws IOException
     */
    public File creatSDFile(String fileName) throws IOException {
        File file = new File(sdcardPath + fileName);
        file.createNewFile();
        return file;
    }

    /**
     * 删除SD卡上的文件
     *
     * @param fileName
     */
    public boolean delSDFile(String fileName) {
        File file = new File(sdcardPath + fileName);
        if (!file.exists() || file.isDirectory()) {
            return false;
        }
        file.delete();
        return true;
    }

    /**
     * 在SD卡上创建目录
     *
     * @param dirName
     */
    public File creatSDDir(String dirName) {
        File dir = new File(sdcardPath + dirName);
        dir.mkdir();
        return dir;
    }

    /**
     * 删除SD卡上的目录
     *
     * @param dirName
     */
    public boolean delSDDir(String dirName) {
        File dir = new File(sdcardPath + dirName);
        return delDir(dir);
    }

    /**
     * 修改SD卡上的文件或目录名
     *
     * @param oldfileName
     * @param newFileName
     *
     * @return
     */
    public boolean renameSDFile(String oldfileName, String newFileName) {
        File oleFile = new File(sdcardPath + oldfileName);
        File newFile = new File(sdcardPath + newFileName);
        return oleFile.renameTo(newFile);
    }

    /**
     * 拷贝SD卡上的单个文件
     *
     * @param srcFileName
     * @param destFileName
     *
     * @return
     *
     * @throws IOException
     */
    public boolean copySDFileTo(String srcFileName, String destFileName)
            throws IOException {
        File srcFile = new File(sdcardPath + srcFileName);
        File destFile = new File(sdcardPath + destFileName);
        return copyFileTo(srcFile, destFile);
    }

    /**
     * 拷贝SD卡上指定目录的所有文件
     *
     * @param srcDirName
     * @param destDirName
     *
     * @return
     *
     * @throws IOException
     */
    public boolean copySDFilesTo(String srcDirName, String destDirName)
            throws IOException {
        File srcDir = new File(sdcardPath + srcDirName);
        File destDir = new File(sdcardPath + destDirName);
        return copyFilesTo(srcDir, destDir);
    }

    /**
     * 移动SD卡上的单个文件
     *
     * @param srcFileName
     * @param destFileName
     *
     * @return
     *
     * @throws IOException
     */
    public boolean moveSDFileTo(String srcFileName, String destFileName)
            throws IOException {
        File srcFile = new File(sdcardPath + srcFileName);
        File destFile = new File(sdcardPath + destFileName);
        return moveFileTo(srcFile, destFile);
    }

    /**
     * 移动SD卡上的指定目录的所有文件
     *
     * @param srcDirName
     * @param destDirName
     *
     * @return
     *
     * @throws IOException
     */
    public boolean moveSDFilesTo(String srcDirName, String destDirName)
            throws IOException {
        File srcDir = new File(sdcardPath + srcDirName);
        File destDir = new File(sdcardPath + destDirName);
        return moveFilesTo(srcDir, destDir);
    }

    /**
     * 将文件写入sd卡。如:writeSDFile("test.txt");
     *
     * @param fileName
     *
     * @return
     *
     * @throws IOException
     */
    public Output writeSDFile(String fileName) throws IOException {
        File file = new File(sdcardPath + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        return new Output(fos);
    }

    /**
     * 在原有文件上继续写文件。如:appendSDFile("test.txt");
     *
     * @param fileName
     *
     * @return
     *
     * @throws IOException
     */
    public Output appendSDFile(String fileName) throws IOException {
        File file = new File(sdcardPath + fileName);
        FileOutputStream fos = new FileOutputStream(file, true);
        return new Output(fos);
    }

    /**
     * 从SD卡读取文件。如:readSDFile("test.txt");
     *
     * @param fileName
     *
     * @return
     *
     * @throws IOException
     */
    public Input readSDFile(String fileName) throws IOException {
        File file = new File(sdcardPath + fileName);
        FileInputStream fis = new FileInputStream(file);
        return new Input(fis);
    }

    /**
     * 建立私有文件
     *
     * @param fileName
     *
     * @return
     *
     * @throws IOException
     */
    public File creatDataFile(String fileName) throws IOException {
        File file = new File(filePath + fileName);
        file.createNewFile();
        return file;
    }

    /**
     * 建立私有目录
     *
     * @param dirName
     *
     * @return
     */
    public File creatDataDir(String dirName) {
        File dir = new File(filePath + dirName);
        dir.mkdir();
        return dir;
    }

    /**
     * 删除私有文件
     *
     * @param fileName
     *
     * @return
     */
    public boolean delDataFile(String fileName) {
        File file = new File(filePath + fileName);
        return delFile(file);
    }

    /**
     * 删除私有目录
     *
     * @param dirName
     *
     * @return
     */
    public boolean delDataDir(String dirName) {
        File file = new File(filePath + dirName);
        return delDir(file);
    }

    /**
     * 更改私有文件名
     *
     * @param oldName
     * @param newName
     *
     * @return
     */
    public boolean renameDataFile(String oldName, String newName) {
        File oldFile = new File(filePath + oldName);
        File newFile = new File(filePath + newName);
        return oldFile.renameTo(newFile);
    }

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
    public boolean copyDataFileTo(String srcFileName, String destFileName)
            throws IOException {
        File srcFile = new File(filePath + srcFileName);
        File destFile = new File(filePath + destFileName);
        return copyFileTo(srcFile, destFile);
    }

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
    public boolean copyDataFilesTo(String srcDirName, String destDirName)
            throws IOException {
        File srcDir = new File(filePath + srcDirName);
        File destDir = new File(filePath + destDirName);
        return copyFilesTo(srcDir, destDir);
    }

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
    public boolean moveDataFileTo(String srcFileName, String destFileName)
            throws IOException {
        File srcFile = new File(filePath + srcFileName);
        File destFile = new File(filePath + destFileName);
        return moveFileTo(srcFile, destFile);
    }

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
    public boolean moveDataFilesTo(String srcDirName, String destDirName)
            throws IOException {
        File srcDir = new File(filePath + srcDirName);
        File destDir = new File(filePath + destDirName);
        return moveFilesTo(srcDir, destDir);
    }

    /**
     * 将文件写入应用私有的files目录。如:writeFile("test.txt");
     *
     * @param fileName
     *
     * @return
     *
     * @throws IOException
     */
    public Output wirteFile(String fileName) throws IOException {
        OutputStream os = context.openFileOutput(fileName,
                Context.MODE_WORLD_WRITEABLE);
        return new Output(os);
    }

    /**
     * 在原有文件上继续写文件。如:appendFile("test.txt");
     *
     * @param fileName
     *
     * @return
     *
     * @throws IOException
     */
    public Output appendFile(String fileName) throws IOException {
        OutputStream os = context.openFileOutput(fileName, Context.MODE_APPEND);
        return new Output(os);
    }

    /**
     * 从应用的私有目录files读取文件。如:readFile("test.txt");
     *
     * @param fileName
     *
     * @return
     *
     * @throws IOException
     */
    public Input readFile(String fileName) throws IOException {
        InputStream is = context.openFileInput(fileName);
        return new Input(is);
    }

    /**
     * 删除一个文件
     *
     * @param file
     *
     * @return
     */
    public boolean delFile(File file) {
        if (file.isDirectory()) {
            return false;
        }
        return file.delete();
    }

    /**
     * 删除一个目录（可以是非空目录）
     *
     * @param dir
     */
    public boolean delDir(File dir) {
        if (dir == null || !dir.exists() || dir.isFile()) {
            return false;
        }
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                delDir(file);// 递归
            }
        }
        dir.delete();
        return true;
    }

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
    public boolean moveFileTo(File srcFile, File destFile) throws IOException {
        boolean iscopy = copyFileTo(srcFile, destFile);
        if (!iscopy) {
            return false;
        }
        delFile(srcFile);
        return true;
    }

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
                delFile(srcDirFiles[i]);
            } else if (srcDirFiles[i].isDirectory()) {
                File oneDestFile = new File(destDir.getPath() + "//"
                        + srcDirFiles[i].getName());
                moveFilesTo(srcDirFiles[i], oneDestFile);
                delDir(srcDirFiles[i]);
            }

        }
        return true;
    }
}
