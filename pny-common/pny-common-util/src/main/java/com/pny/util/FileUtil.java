package com.pny.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static byte[] getBytes(InputStream is) throws Exception {
        byte[] data = null;

        Collection chunks = new ArrayList();
        byte[] buffer = new byte[1024 * 1000];
        int read = -1;
        int size = 0;

        while ((read = is.read(buffer)) != -1) {
            if (read > 0) {
                byte[] chunk = new byte[read];
                System.arraycopy(buffer, 0, chunk, 0, read);
                chunks.add(chunk);
                size += chunk.length;
            }
        }

        if (size > 0) {
            ByteArrayOutputStream bos = null;
            try {
                bos = new ByteArrayOutputStream(size);
                for (Iterator itr = chunks.iterator(); itr.hasNext();) {
                    byte[] chunk = (byte[]) itr.next();
                    bos.write(chunk);
                }
                data = bos.toByteArray();
            } finally {
                if (bos != null) {
                    bos.close();
                }
            }
        }
        return data;
    }

    public static String saveTemFile(String str, String suffix)
            throws IOException {
        String suffix1 = (suffix == null) ? ".tem" : suffix;

        java.io.File temfile = java.io.File
                .createTempFile("tem", suffix1, null);
        String fileName = temfile.getAbsolutePath();
        saveToFile(str, fileName);
        return fileName;

    }

    public static void saveToFile(byte[] content, String fileName)
            throws IOException {
        FileOutputStream out = null;
        try {
            File outFile = new File(fileName);
            if (!outFile.exists()) {
                outFile.createNewFile();
            }
            out = new FileOutputStream(outFile);
            out.write(content);
            out.flush();
        } catch (IOException e) {
            throw e;
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e1) {
                }
            }
        }
    }

    public static void saveToFile(String str, String filename)
            throws IOException {
        saveToFile(str, filename, "UTF-8");
    }

    public static void saveToFile(String str, String filename, String encode)
            throws IOException {
        if (str == null)
            throw new IllegalArgumentException("string is null");
        java.io.File f = new java.io.File(filename);
        if (!f.exists())
            f.createNewFile();

        FileOutputStream fos = new FileOutputStream(filename);

        Writer out = new OutputStreamWriter(fos, encode);

        out.write(str);

        out.close();
    }

    public static void delFile(String fileName) {
        java.io.File f = new java.io.File(fileName);
        f.deleteOnExit();
    }

    public static boolean delDirectory(String filename) {
        deleteFileAndFolder(new File(filename.replace('\\', '/')));
        return true;
    }

    private static boolean deleteFileAndFolder(File filename) {
        File file = filename;
        if (file.isFile()) {
            file.delete();
            return true;
        } else {
            File[] allfiles = file.listFiles();
            for (int i = 0; i < allfiles.length; i++) {
                deleteFileAndFolder(allfiles[i]);
            }
            file.delete();
            return true;
        }
    }

    public static void copyFile(String src, String dest) throws IOException {
        FileInputStream in = new FileInputStream(src);
        FileOutputStream out = new FileOutputStream(dest);
        byte buffer[] = new byte[1024];
        int read = -1;
        while ((read = in.read(buffer, 0, 1024)) != -1) {
            out.write(buffer, 0, read);
        }
        out.flush();
        out.close();
        in.close();
    }

    public static String readToString(String fileName) throws IOException {
        StringBuffer tem = new StringBuffer();
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
        String Line;
        try {
            Line = br.readLine();
            while (Line != null) {
                tem.append(Line);
                Line = br.readLine();
            }
        } finally {
            br.close();
            fr.close();
        }

        return tem.toString();
    }

    public static byte[] readBytes(String fileName) throws IOException {
        return readBytes(fromFilename(fileName));
    }

    @SuppressWarnings("deprecation")
    private static URL fromFilename(String filename) {
        if (filename == null)
            return null;
        File file = new File(filename);
        URL url = null;

        try {
            if (file.exists())
                url = file.toURL();
        } catch (java.net.MalformedURLException e) {
            e.printStackTrace();
            url = null;
        }
        return url;
    }

    public static byte[] readBytes(URL file) throws IOException {
        if (file == null)
            return new byte[0];
        byte[] data = null;
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = file.openStream();
            out = new ByteArrayOutputStream();
            int len = 0;
            byte[] tmp = new byte[65535];
            while ((len = in.read(tmp)) > 0) {
                out.write(tmp, 0, len);
            }
            data = out.toByteArray();
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                in.close();
            } catch (Exception e1) {
            }
            try {
                out.close();
            } catch (Exception e2) {
            }
        }
        return data;
    }

    public static String readFile(String input) throws Exception {
        return readFile(input, "UTF-8");
    }

    public static String readFile(String input, String coding)
            throws Exception {
        char[] buffer = new char[4096];
        int len = 0;
        StringBuffer content = new StringBuffer(4096);

        try {
            InputStreamReader fr = new InputStreamReader(new FileInputStream(
                    input), coding);

            BufferedReader br = new BufferedReader(fr);
            while ((len = br.read(buffer)) > -1) {
                content.append(buffer, 0, len);
            }

            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public static void move(String input, String output) throws Exception {
        File inputFile = new File(input);
        File outputFile = new File(output);
        try {
            inputFile.renameTo(outputFile);
        } catch (Exception ex) {
            throw new Exception("Can not mv" + input + " to " + output
                    + ex.getMessage());
        }
    }

    public static void makeDir(String home) throws Exception {
        File homedir = new File(home);
        if (!homedir.exists()) {
            try {
                homedir.mkdirs();
            } catch (Exception ex) {
                throw new Exception("Can not mkdir :" + home
                        + " Maybe include special charactor!");
            }
        }
    }

    public static void CopyDir(String sourcedir, String destdir)
            throws Exception {
        File dest = new File(destdir);
        File source = new File(sourcedir);

        String[] files = source.list();
        try {
            makeDir(destdir);
        } catch (Exception ex) {
            throw new Exception("CopyDir:" + ex.getMessage());
        }

        for (int i = 0; i < files.length; i++) {
            String sourcefile = source + File.separator + files[i];
            String destfile = dest + File.separator + files[i];
            File temp = new File(sourcefile);
            if (temp.isFile()) {
                try {
                    copyFile(sourcefile, destfile);
                } catch (Exception ex) {
                    throw new Exception("CopyDir:" + ex.getMessage());
                }
            }
        }
    }

    public static void recursiveRemoveDir(File directory) throws Exception {
        if (!directory.exists())
            throw new IOException(directory.toString() + " do not exist!");

        String[] filelist = directory.list();
        File tmpFile = null;
        for (int i = 0; i < filelist.length; i++) {
            tmpFile = new File(directory.getAbsolutePath(), filelist[i]);
            if (tmpFile.isDirectory()) {
                recursiveRemoveDir(tmpFile);
            } else if (tmpFile.isFile()) {
                try {
                    tmpFile.delete();
                } catch (Exception ex) {
                    throw new Exception(tmpFile.toString()
                            + " can not be deleted " + ex.getMessage());
                }
            }
        }
        try {
            directory.delete();
        } catch (Exception ex) {
            throw new Exception(directory.toString() + " can not be deleted "
                    + ex.getMessage());
        } finally {
            filelist = null;
        }
    }

    private static char hexChar[] = { '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public static String getFileMD5(String filename) {
        String str = "";
        try {
            str = getHash(filename, "MD5");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getFileMD5(InputStream fis) {
        String str = "";
        try {
            str = getHash(fis, "MD5");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getFileMD5WithoutClose(InputStream fis) {
        String str = "";
        try {
            str = getHashWithoutClose(fis, "MD5");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getFileSHA1(String filename) {
        String str = "";
        try {
            str = getHash(filename, "SHA1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getFileSHA256(String filename) {
        String str = "";
        try {
            str = getHash(filename, "SHA-256");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getFileSHA384(String filename) {
        String str = "";
        try {
            str = getHash(filename, "SHA-384");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getFileSHA512(String filename) {
        String str = "";
        try {
            str = getHash(filename, "SHA-512");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    private static String getHash(InputStream fis, String hashType)
            throws Exception {
        if (fis == null)
            return null;
        byte buffer[] = new byte[1024];
        MessageDigest md5 = MessageDigest.getInstance(hashType);
        for (int numRead = 0; (numRead = fis.read(buffer)) > 0;) {
            md5.update(buffer, 0, numRead);
        }
        fis.close();
        return toHexString(md5.digest());
    }

    private static String getHashWithoutClose(InputStream fis, String hashType)
            throws Exception {
        if (fis == null)
            return null;
        byte buffer[] = new byte[1024];
        MessageDigest md5 = MessageDigest.getInstance(hashType);
        for (int numRead = 0; (numRead = fis.read(buffer)) > 0;) {
            md5.update(buffer, 0, numRead);
        }
        // fis.close();
        return toHexString(md5.digest());
    }

    private static String getHash(String fileName, String hashType)
            throws Exception {
        InputStream fis = new FileInputStream(fileName);
        byte buffer[] = new byte[1024];
        MessageDigest md5 = MessageDigest.getInstance(hashType);
        for (int numRead = 0; (numRead = fis.read(buffer)) > 0;) {
            md5.update(buffer, 0, numRead);
        }
        fis.close();
        return toHexString(md5.digest());
    }

    private static String toHexString(byte b[]) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
            sb.append(hexChar[b[i] & 0xf]);
        }
        return sb.toString();
    }

    /**
     * 默认获取SHA1
     * 
     * @param fileName
     * @return
     */
    public static String getHash(String fileName) {
        return getFileSHA1(fileName);
    }

    public static void copy(String srcPath, String descPath)
            throws IOException {
        copy(new File(srcPath), new File(descPath));
    }

    public static void copy(File srcFile, File descFile) throws IOException {
        if (srcFile.isFile()) { // 文件

            File parent = descFile.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs(); // 创建文件夹

            }
            if (srcFile.getName().endsWith(".java")) {
                copyJava(srcFile, descFile);
            } else {
                copyFile(srcFile, descFile);
            }
        } else { // 文件夹

            for (File file : srcFile.listFiles()) {
                // 相对路径

                String srcPath = file.getAbsolutePath().substring(
                        srcFile.getAbsolutePath().length());

                copy(file, new File(descFile.getAbsolutePath() + srcPath));
            }
        }
    }

    private static void copyJava(File srcFile, File descFile)
            throws IOException {

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(descFile)));
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(srcFile)));
        String line;
        while ((line = br.readLine()) != null) {
            bw.write(line.replaceFirst(".*/\\*(.*)\\*/", "")); // 注意这里，如果不行，要适当修改
            bw.write("\n");
        }
        br.close();
        bw.close();
    }

    private static void copyFile(File srcFile, File descFile)
            throws IOException {
        OutputStream output = new FileOutputStream(descFile);
        InputStream input = new FileInputStream(srcFile);
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = input.read(buffer)) != -1) {
            output.write(buffer, 0, n);
        }
        input.close();
        output.close();
    }

    public static String readStringByNIO(String fileName) {
        return readStringByNIO(fileName, "UTF-8");
    }

    public static String readStringByNIO(String fileName, String coding) {
        try {
            return new String(readByNIO(fileName), coding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] readByNIO(String fileName) {
        // 第一步 获取通道
        FileInputStream fis = null;
        FileChannel fc = null;
        byte[] result = null;
        try {
            fis = new FileInputStream(fileName);
            fc = fis.getChannel();
            result = new byte[(int) fc.size()];
            // 第二步 指定缓冲区
            ByteBuffer bf = ByteBuffer.allocate(1024);
            // 第三步 将通道中的数据读取到缓冲区中
            // fc.read(bf);
            int len = 0;
            int total = 0;
            while ((len = fc.read(bf)) != -1) {
                bf.flip();
                while (bf.hasRemaining()) {
                    bf.get(result, total, len);
                    total = total + len;
                }
                bf.clear();
            }
            bf = null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fc.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static boolean writeByNIO(String fileName, String data) {
        if (data == null)
            return false;
        try {
            return writeByNIO(fileName, data.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 利用NIO将内容输出到文件中
     * 
     * @param file
     */
    public static boolean writeByNIO(String fileName, byte[] data) {
        FileOutputStream fos = null;
        FileChannel fc = null;
        ByteBuffer buffer = null;
        boolean flag = false;
        try {
            fos = new FileOutputStream(fileName);
            // 第一步 获取一个通道
            fc = fos.getChannel();
            // buffer=ByteBuffer.allocate(1024);
            // 第二步 定义缓冲区
            buffer = ByteBuffer.wrap(data);
            // 将内容写到缓冲区
            fos.flush();
            fc.write(buffer);
            buffer.clear();
            buffer = null;
            flag = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fc.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * 根据文件流读取图片文件真实类型
     * 
     * @param is
     * @return
     */
    public static String getTypeByStream(FileInputStream is) {
        byte[] b = new byte[4];
        String type = null;
        try {
            is.read(b, 0, b.length);
            type = toHexString(b).toUpperCase();
            if (type == null)
                return type;
            if (type.contains("FFD8FF")) {
                type = "jpg";
            } else if (type.contains("89504E47")) {
                type = "png";
            } else if (type.contains("47494638")) {
                type = "gif";
            } else if (type.contains("49492A00")) {
                type = "tif";
            } else if (type.contains("424D")) {
                type = "bmp";
            } else if (type.contains("25504446")
                    || type.contains("255044462D312E")) {
                type = "pdf";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return type;
    }

    public static String getSavePath() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DATE);
        String savePath = "/" + year + "/"
                + (month < 10 ? "0" + String.valueOf(month) : String.valueOf(month))
                + "/" + (date < 10 ? "0" + date : date);
        return savePath;
    }

    public static long SaveFileFromInputStream(InputStream stream,
            String outFile)
            throws IOException {
        long size = 0;

        String filePath = outFile.substring(0, outFile.lastIndexOf("/"));
        File f = new File(filePath);
        if (!f.exists()) {
            f.mkdirs();
        }
        FileOutputStream fs = new FileOutputStream(outFile);
        byte[] buffer = new byte[1024 * 1024];
        int byteread = 0;
        while ((byteread = stream.read(buffer)) != -1) {
            fs.write(buffer, 0, byteread);
            fs.flush();
            size += byteread;
        }
        fs.close();
        stream.close();

        return size;
    }

    /**
     * 从url中下载文件
     * 
     * @param httpUrl
     * @param outFile
     * @return
     */
    public static long httpDownload(String httpUrl, String outFile) {
        URL url = null;
        long size = 0;
        try {
            url = new URL(httpUrl);

            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();

            size = SaveFileFromInputStream(inStream, outFile);
            return size;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return size;
        } catch (IOException e) {
            e.printStackTrace();
            return size;
        }
    }

    /**
     * 功能:压缩多个文件成一个zip文件
     * 
     * @param srcfile
     *        ：源文件列表
     * @param zipfile
     *        ：压缩后的文件
     * @throws IOException
     */
    public static void zipFiles(List<File> srcfileList, File zipfile) throws IOException {
        byte[] buf = new byte[1024];
        // ZipOutputStream类：完成文件或文件夹的压缩
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
        for (int i = 0; i < srcfileList.size(); i++) {
            FileInputStream in = new FileInputStream(srcfileList.get(i));
            out.putNextEntry(new ZipEntry(srcfileList.get(i).getName()));
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.closeEntry();
            in.close();
        }
        out.close();
    }

    /*public static void main(String[] args) throws IOException {
        File file1 = new File("E:/2017/05/22/video/201705224308536962.file");
        File file2 = new File("E:/2017/05/22/video/201705225640709788.file");
        File file3 = new File("E:/2017/05/22/video/201705229146773801.file");
        File file4 = new File("E:/2017/05/22/video/201705229670152908.mp4");
        // 创建临时文件
        File temp = File.createTempFile("Json", ".txt");

        // 在程序退出时删除临时文件
        temp.deleteOnExit();

        // 向临时文件中写入内容
        BufferedWriter out = new BufferedWriter(new FileWriter(temp));
        out.write("Json");
        out.close();
        List<File> fileList = new ArrayList<File>() {
            private static final long serialVersionUID = 1L;
            {
                add(file1);
                add(file2);
                add(file3);
                add(file4);
                add(temp);
            }
        };
        zipFiles(fileList, new File("E:/2017/05/22/video/xxx.zip"));
        System.out.println("ok");
    }
*/
}
