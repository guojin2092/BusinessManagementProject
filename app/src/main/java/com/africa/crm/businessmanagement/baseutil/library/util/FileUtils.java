package com.africa.crm.businessmanagement.baseutil.library.util;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.util.EncodingUtils;

/**
 * 文件夹操作类
 *
 * @author Administrator
 */
public class FileUtils {

    /**
     * CreatFile()
     *
     * @param path
     * @param name
     * @throws IOException
     **/
    public static File CreatFile(final String path, final String name)
            throws IOException {
        CreatPath(path);

        File file = new File(path + name);
        file.createNewFile();

        return file;
    }

    public static boolean IsSDcardExist() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    public static boolean CreatFile(final String path) {
        boolean success = false;
        File file = new File(path);
        try {
            success = file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * CreatPath()
     *
     * @param path
     **/
    public static boolean CreatPath(final String path) {
        File filePath = new File(path);
        if (!filePath.exists()) {
            return filePath.mkdirs();
        }

        return true;
    }

    public static boolean deletePath(final String path) {
        File filePath = new File(path);
        if (!filePath.exists()) {
            return false;
        }
        return filePath.delete();
    }

    public static void deleteAllFileInFolder(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                deleteAllFileInFolder(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);// 再删除空文件夹
            }
        }
    }

    public static void delFolder(String folderPath) {
        try {
            deleteAllFileInFolder(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); // 删除空文件夹

        } catch (Exception e) {
//            MyLog.e(FileUtils.class, "删除文件夹操作出错");
            e.printStackTrace();
        }
    }

    /**
     * IsPathExist()
     *
     * @param path
     **/
    public static boolean IsPathExist(final String path) {
        if (TextUtils.isEmpty(path))
            return false;
        File filePath = new File(path);
        if (filePath.exists()) {
            return true;
        }
        return false;
    }

    public static String getStringFromFile(String filePath) {
        String result = null;

        try {
            FileInputStream fin = new FileInputStream(filePath);

            // OutputStream fOut = new OutputStream()

            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            result = EncodingUtils.getString(buffer, "UTF-8");
            fin.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static InputStream getInputStreamFromFile(String filePath) {
        InputStream result = null;

        try {
            result = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * GetFileNameFromUrl()
     *
     * @param url
     * @return
     **/
    public static String getFileNameFromUrl(String url) {
        int lastPosition = url.lastIndexOf(File.separator);
        String filename = url.substring(lastPosition + 1);

        Pattern pattern = Pattern.compile("\\s*|\t|\r|\n");
        Matcher matcher = pattern.matcher(filename);
        filename = matcher.replaceAll("");

        return filename;
    }

    /**
     * getAllFilesNameInAFolder:get all files' names in a folder
     *
     * @param folderPath
     * @return files' names array
     */
    public static String[] getAllFilesNameInAFolder(String folderPath) {
        File file = new File(folderPath);
        if (!file.exists()) {
            return null;
        }
        if (!file.isDirectory()) {
            return null;
        }
        String[] nameList = file.list();
        return nameList;
    }

    public static boolean saveStringToFile(String fileName, String filePath,
                                           String content) {

        boolean result = false;
        try {
            CreatFile(filePath, fileName);

            // String filePath = Environment.getExternalStorageDirectory() +
            // File.separator +fileName;
            try {
                // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
                FileWriter writer = new FileWriter(filePath + fileName, false);
                writer.write(content);
                writer.close();
                result = true;

            } catch (IOException e) {

            }

        } catch (IOException e1) {
            // TODO Auto-generated catch block
        }
        return result;
    }

    public static boolean saveStringToFile(String filePath, String content) {

        boolean result = false;

        CreatFile(filePath);

        // String filePath = Environment.getExternalStorageDirectory() +
        // File.separator +fileName;
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(filePath, false);
            writer.write(content);
            writer.close();
            result = true;

        } catch (IOException e) {

        }

        return result;
    }

    public static boolean saveInputStreamToFile(InputStream is, String filePath) {
        boolean success = false;
        int count;
        FileUtils.CreatFile(filePath);
        OutputStream output;
        try {
            output = new FileOutputStream(filePath);
            byte data[] = new byte[1024];
            long total = 0;

            while ((count = is.read(data)) != -1) {
                total += count;
                output.write(data, 0, count);
                output.flush();
            }
            output.close();
            is.close();
            success = true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return success;
    }

    public static boolean rename(String oldPath, String newPath) {
        boolean result = false;

        File file = new File(oldPath);
        File newFile = new File(newPath);
        if (file.exists()) {
            result = file.renameTo(newFile);
        }
        return result;
    }

    /**
     * copyFileTo copy a file
     *
     * @param srcFilePath source file absolute path
     * @param tarFilePath target file absolute path
     * @return if copy file successfully,return true,else return false
     */
    public static boolean copyFileTo(String srcFilePath, String tarFilePath) {
        boolean result = false;

        File srcFile = new File(srcFilePath);
        File tarFile = new File(tarFilePath);

        if (srcFile.isDirectory() || tarFile.isDirectory()) {
            return false;// 判断是否是文件
        }

        FileInputStream fis;
        try {
            fis = new FileInputStream(srcFile);
            FileOutputStream fos = new FileOutputStream(tarFile);
            int readLen = 0;
            byte[] buf = new byte[1024];
            while ((readLen = fis.read(buf)) != -1) {
                fos.write(buf, 0, readLen);
            }
            fos.flush();
            fos.close();
            fis.close();
            result = true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { // 文件存在时
                InputStream inStream = new FileInputStream(oldPath); // 读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((byteread = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
//            MyLog.e(FileUtils.class, "复制单个文件操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 复制整个文件夹内容
     *
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public void copyFolder(String oldPath, String newPath) {

        try {
            (new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath
                            + "/" + (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {// 如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // =====================================spare code,please don't
    // delete!=======================================================//
    // /**
    // * 在SD卡上创建文件
    // *
    // * @throws IOException
    // */
    // public File creatSDFile(String fileName) throws IOException {
    // File file = new File(SDPATH + fileName);
    // file.createNewFile();
    // return file;
    // }
    //
    // /**
    // * 删除SD卡上的文件
    // *
    // * @param fileName
    // */
    // public boolean delSDFile(String fileName) {
    // File file = new File(SDPATH + fileName);
    // if (file == null || !file.exists() || file.isDirectory())
    // return false;
    // file.delete();
    // return true;
    // }
    //
    // /**
    // * 在SD卡上创建目录
    // *
    // * @param dirName
    // */
    // public File creatSDDir(String dirName) {
    // File dir = new File(SDPATH + dirName);
    // dir.mkdir();
    // return dir;
    // }
    //
    // /**
    // * 删除SD卡上的目录
    // *
    // * @param dirName
    // */
    // public boolean delSDDir(String dirName) {
    // File dir = new File(SDPATH + dirName);
    // return delDir(dir);
    // }
    //
    // /**
    // * 修改SD卡上的文件或目录名
    // *
    // * @param fileName
    // */
    // public boolean renameSDFile(String oldfileName, String newFileName) {
    // File oleFile = new File(SDPATH + oldfileName);
    // File newFile = new File(SDPATH + newFileName);
    // return oleFile.renameTo(newFile);
    // }
    //
    // /**
    // * 拷贝SD卡上的单个文件
    // *
    // * @param path
    // * @throws IOException
    // */
    // public boolean copySDFileTo(String srcFileName, String destFileName)
    // throws IOException {
    // File srcFile = new File(SDPATH + srcFileName);
    // File destFile = new File(SDPATH + destFileName);
    // return copyFileTo(srcFile, destFile);
    // }
    //
    // /**
    // * 拷贝SD卡上指定目录的所有文件
    // *
    // * @param srcDirName
    // * @param destDirName
    // * @return
    // * @throws IOException
    // */
    // public boolean copySDFilesTo(String srcDirName, String destDirName)
    // throws IOException {
    // File srcDir = new File(SDPATH + srcDirName);
    // File destDir = new File(SDPATH + destDirName);
    // return copyFilesTo(srcDir, destDir);
    // }
    //
    // /**
    // * 移动SD卡上的单个文件
    // *
    // * @param srcFileName
    // * @param destFileName
    // * @return
    // * @throws IOException
    // */
    // public boolean moveSDFileTo(String srcFileName, String destFileName)
    // throws IOException {
    // File srcFile = new File(SDPATH + srcFileName);
    // File destFile = new File(SDPATH + destFileName);
    // return moveFileTo(srcFile, destFile);
    // }
    //
    // /**
    // * 移动SD卡上的指定目录的所有文件
    // *
    // * @param srcDirName
    // * @param destDirName
    // * @return
    // * @throws IOException
    // */
    // public boolean moveSDFilesTo(String srcDirName, String destDirName)
    // throws IOException {
    // File srcDir = new File(SDPATH + srcDirName);
    // File destDir = new File(SDPATH + destDirName);
    // return moveFilesTo(srcDir, destDir);
    // }
    //
    //
    // /*
    // * 将文件写入sd卡。如:writeSDFile("test.txt");
    // */
    // public Output writeSDFile(String fileName) throws IOException {
    // File file = new File(SDPATH + fileName);
    // FileOutputStream fos = new FileOutputStream(file);
    // return new Output(fos);
    // }
    //
    // /*
    // * 在原有文件上继续写文件。如:appendSDFile("test.txt");
    // */
    // public Output appendSDFile(String fileName) throws IOException {
    // File file = new File(SDPATH + fileName);
    // FileOutputStream fos = new FileOutputStream(file, true);
    // return new Output(fos);
    // }
    //
    // /*
    // * 从SD卡读取文件。如:readSDFile("test.txt");
    // */
    // public Input readSDFile(String fileName) throws IOException {
    // File file = new File(SDPATH + fileName);
    // FileInputStream fis = new FileInputStream(file);
    // return new Input(fis);
    // }
    //
    //
    // /**
    // * 建立私有文件
    // *
    // * @param fileName
    // * @return
    // * @throws IOException
    // */
    // public File creatDataFile(String fileName) throws IOException {
    // File file = new File(FILESPATH + fileName);
    // file.createNewFile();
    // return file;
    // }
    //
    // /**
    // * 建立私有目录
    // *
    // * @param dirName
    // * @return
    // */
    // public File creatDataDir(String dirName) {
    // File dir = new File(FILESPATH + dirName);
    // dir.mkdir();
    // return dir;
    // }
    //
    // /**
    // * 删除私有文件
    // *
    // * @param fileName
    // * @return
    // */
    // public boolean delDataFile(String fileName) {
    // File file = new File(FILESPATH + fileName);
    // return delFile(file);
    // }
    //
    // /**
    // * 删除私有目录
    // *
    // * @param dirName
    // * @return
    // */
    // public boolean delDataDir(String dirName) {
    // File file = new File(FILESPATH + dirName);
    // return delDir(file);
    // }
    //
    // /**
    // * 更改私有文件名
    // *
    // * @param oldName
    // * @param newName
    // * @return
    // */
    // public boolean renameDataFile(String oldName, String newName) {
    // File oldFile = new File(FILESPATH + oldName);
    // File newFile = new File(FILESPATH + newName);
    // return oldFile.renameTo(newFile);
    // }
    //
    // /**
    // * 在私有目录下进行文件复制
    // *
    // * @param srcFileName
    // * ： 包含路径及文件名
    // * @param destFileName
    // * @return
    // * @throws IOException
    // */
    // public boolean copyDataFileTo(String srcFileName, String destFileName)
    // throws IOException {
    // File srcFile = new File(FILESPATH + srcFileName);
    // File destFile = new File(FILESPATH + destFileName);
    // return copyFileTo(srcFile, destFile);
    // }
    //
    // /**
    // * 复制私有目录里指定目录的所有文件
    // *
    // * @param srcDirName
    // * @param destDirName
    // * @return
    // * @throws IOException
    // */
    // public boolean copyDataFilesTo(String srcDirName, String destDirName)
    // throws IOException {
    // File srcDir = new File(FILESPATH + srcDirName);
    // File destDir = new File(FILESPATH + destDirName);
    // return copyFilesTo(srcDir, destDir);
    // }
    //
    // /**
    // * 移动私有目录下的单个文件
    // *
    // * @param srcFileName
    // * @param destFileName
    // * @return
    // * @throws IOException
    // */
    // public boolean moveDataFileTo(String srcFileName, String destFileName)
    // throws IOException {
    // File srcFile = new File(FILESPATH + srcFileName);
    // File destFile = new File(FILESPATH + destFileName);
    // return moveFileTo(srcFile, destFile);
    // }
    //
    // /**
    // * 移动私有目录下的指定目录下的所有文件
    // *
    // * @param srcDirName
    // * @param destDirName
    // * @return
    // * @throws IOException
    // */
    // public boolean moveDataFilesTo(String srcDirName, String destDirName)
    // throws IOException {
    // File srcDir = new File(FILESPATH + srcDirName);
    // File destDir = new File(FILESPATH + destDirName);
    // return moveFilesTo(srcDir, destDir);
    // }
    //
    // /*
    // * 将文件写入应用私有的files目录。如:writeFile("test.txt");
    // */
    // public Output wirteFile(String fileName) throws IOException {
    // OutputStream os = context.openFileOutput(fileName,
    // Context.MODE_WORLD_WRITEABLE);
    // return new Output(os);
    // }
    //
    // /*
    // * 在原有文件上继续写文件。如:appendFile("test.txt");
    // */
    // public Output appendFile(String fileName) throws IOException {
    // OutputStream os = context.openFileOutput(fileName, Context.MODE_APPEND);
    // return new Output(os);
    // }
    //
    // /*
    // * 从应用的私有目录files读取文件。如:readFile("test.txt");
    // */
    // public Input readFile(String fileName) throws IOException {
    // InputStream is = context.openFileInput(fileName);
    // return new Input(is);
    // }
    //
    //
    //
    // /**
    // * 删除一个文件
    // *
    // * @param file
    // * @return
    // */
    // public boolean delFile(File file) {
    // if (file.isDirectory())
    // return false;
    // return file.delete();
    // }
    //
    // /**
    // * 删除一个目录（可以是非空目录）
    // *
    // * @param dir
    // */
    // public boolean delDir(File dir) {
    // if (dir == null || !dir.exists() || dir.isFile()) {
    // return false;
    // }
    // for (File file : dir.listFiles()) {
    // if (file.isFile()) {
    // file.delete();
    // } else if (file.isDirectory()) {
    // delDir(file);// 递归
    // }
    // }
    // dir.delete();
    // return true;
    // }
    //
    // /**
    // * 拷贝一个文件,srcFile源文件，destFile目标文件
    // *
    // * @param path
    // * @throws IOException
    // */
    // public boolean copyFileTo(File srcFile, File destFile) throws IOException
    // {
    // if (srcFile.isDirectory() || destFile.isDirectory())
    // return false;// 判断是否是文件
    // FileInputStream fis = new FileInputStream(srcFile);
    // FileOutputStream fos = new FileOutputStream(destFile);
    // int readLen = 0;
    // byte[] buf = new byte[1024];
    // while ((readLen = fis.read(buf)) != -1) {
    // fos.write(buf, 0, readLen);
    // }
    // fos.flush();
    // fos.close();
    // fis.close();
    // return true;
    // }
    //
    // /**
    // * 拷贝目录下的所有文件到指定目录
    // *
    // * @param srcDir
    // * @param destDir
    // * @return
    // * @throws IOException
    // */
    // public boolean copyFilesTo(File srcDir, File destDir) throws IOException
    // {
    // if (!srcDir.isDirectory() || !destDir.isDirectory())
    // return false;// 判断是否是目录
    // if (!destDir.exists())
    // return false;// 判断目标目录是否存在
    // File[] srcFiles = srcDir.listFiles();
    // for (int i = 0; i < srcFiles.length; i++) {
    // if (srcFiles[i].isFile()) {
    // // 获得目标文件
    // File destFile = new File(destDir.getPath() + "//"
    // + srcFiles[i].getName());
    // copyFileTo(srcFiles[i], destFile);
    // } else if (srcFiles[i].isDirectory()) {
    // File theDestDir = new File(destDir.getPath() + "//"
    // + srcFiles[i].getName());
    // copyFilesTo(srcFiles[i], theDestDir);
    // }
    // }
    // return true;
    // }
    //
    // /**
    // * 移动一个文件
    // *
    // * @param srcFile
    // * @param destFile
    // * @return
    // * @throws IOException
    // */
    // public boolean moveFileTo(File srcFile, File destFile) throws IOException
    // {
    // boolean iscopy = copyFileTo(srcFile, destFile);
    // if (!iscopy)
    // return false;
    // delFile(srcFile);
    // return true;
    // }
    //
    // /**
    // * 移动目录下的所有文件到指定目录
    // *
    // * @param srcDir
    // * @param destDir
    // * @return
    // * @throws IOException
    // */
    // public boolean moveFilesTo(File srcDir, File destDir) throws IOException
    // {
    // if (!srcDir.isDirectory() || !destDir.isDirectory()) {
    // return false;
    // }
    // File[] srcDirFiles = srcDir.listFiles();
    // for (int i = 0; i < srcDirFiles.length; i++) {
    // if (srcDirFiles[i].isFile()) {
    // File oneDestFile = new File(destDir.getPath() + "//"
    // + srcDirFiles[i].getName());
    // moveFileTo(srcDirFiles[i], oneDestFile);
    // delFile(srcDirFiles[i]);
    // } else if (srcDirFiles[i].isDirectory()) {
    // File oneDestFile = new File(destDir.getPath() + "//"
    // + srcDirFiles[i].getName());
    // moveFilesTo(srcDirFiles[i], oneDestFile);
    // delDir(srcDirFiles[i]);
    // }
    //
    // }
    // return true;
    // }
    // }

}
