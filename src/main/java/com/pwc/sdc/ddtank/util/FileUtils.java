package com.pwc.sdc.ddtank.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
public class FileUtils {
    /**
     * 将对象写入到file中
     *
     * @param object
     * @param file
     * @return
     */
    public synchronized static boolean writeObject(Object object, File file) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(file.toPath()));
            oos.writeObject(object);
            oos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("将对象{}写入到[{}]失败，错误原因：{}", object, file.getAbsolutePath(), e.getMessage());
            return false;
        }
    }

    /**
     * 从file中读取对象
     *
     * @param file
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public synchronized static Object readSeriaizedObject(File file) throws IOException, ClassNotFoundException {
        if (!file.exists()) {
            return null;
        }
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(Files.newInputStream(file.toPath()));
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw e;
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    ois = null;
                }
            }
        }
    }

    /**
     * 将文件释放到指定目录
     */
    public static void putAttachment(String filename, File dir) {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = FileUtils.class.getResourceAsStream("/" + filename);
            if(is == null) {
                throw new RuntimeException(filename + "文件不存在");
            }
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    log.error("将文件释放到指定目录失败：指定目录[{}]不存在，且自动创建失败！", dir.getAbsolutePath());
                }
            } else {
                try {
                    FileCopyUtils.copy(is, Files.newOutputStream(new File(dir, filename.replace("/", "-").replace("\\", "-")).toPath()));
                    log.info("成功将附件[{}]放入到目录[{}]中", filename, dir.getAbsolutePath());
                } catch (IOException e) {
                    log.error("将文件释放到指定目录失败：复制过程中出现了异常[{}]", e.toString());
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            log.error("将文件释放到指定目录失败{}", filename);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void putAttachment(String srcFolder, String distFolder) {
        Path srcPath = Paths.get(URI.create(srcFolder));
        Path distPath = Paths.get(URI.create(distFolder));
        try {
            // 如果目标文件夹不存在，则创建
            if (Files.notExists(srcPath)) {
                Files.createDirectories(srcPath);
            }
            // 获取源文件夹的内容
            Files.list(srcPath).forEach(sourcePath -> {
                // 获取目标路径
                Path targetPath = distPath.resolve(srcPath.relativize(sourcePath));
                try {
                    // 如果是文件夹，则递归复制；如果是文件，则直接复制
                    if (Files.isDirectory(sourcePath)) {
                        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    } else {
                        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            log.error("复制文件夹失败", e);
        }


    }
}
