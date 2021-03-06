package com.lawschool.util;

import com.lawschool.beans.accessory.AccessoryEntity;
import com.lawschool.service.accessory.AccessoryService;
import com.lawschool.util.ftp.FTPClientPool;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FileUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * @return com.lawschool.util.Result
     * @Author MengyuWu
     * @Description 上传文件
     * @Date 19:23 2018-12-19
     * @Param [filename, input]
     **/

    public static Result uploadToFTPServer(String filename, InputStream input) {
        Result result = new Result();
        FTPClientPool ftpClientPool = (FTPClientPool) SpringContextUtils.getBean("ftpClientPool");
        FTPClient ftpClient = null;
        try {
            ftpClient = ftpClientPool.borrowObject();
            //根据文件名截取相关的文件类型
            int start = filename.lastIndexOf(".");
            String type = filename.substring(start + 1, filename.length());//后缀名
            //获取附件servcie
            AccessoryService accessoryService = SpringContextUtils.getBean("accessoryService", AccessoryService.class);
            //AccessoryService accessoryService =(AccessoryService)ContextLoaderListener.getCurrentWebApplicationContext().getBean ("accessoryService");

            AccessoryEntity accessoryEntity = new AccessoryEntity();
            accessoryEntity.setId(GetUUID.getUUIDs("AE"));//设置id
            accessoryEntity.setAccessoryName(filename);//设置文件名
            accessoryEntity.setAccessoryType(type);//设置文件类型

            System.out.println("开始上传文件");
            //inputStream = new FileInputStream(new File(originfilename));
            //initFtpClient();
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
            String curDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
            //CreateDirecroty(curDate);
            ftpClient.makeDirectory(curDate);
            ftpClient.changeWorkingDirectory(curDate);
            ftpClient.storeFile(accessoryEntity.getId() + "." + type, input);
            input.close();
            //不需要退出  要不然返回到ftp连接池就会报错啦
            //ftpClient.logout();
            String filePath = curDate;
            accessoryEntity.setFilePath(filePath);//设置文件路径
            accessoryService.insert(accessoryEntity);//保存文件信息
            result.put("accessoryId", accessoryEntity.getId());
            System.out.println("上传文件成功");


        } catch (Exception e) {
            System.out.println("上传文件失败");
            e.printStackTrace();
            return Result.error("上传文件失败");
        } finally {
            ftpClientPool.returnObject(ftpClient);
            if (null != input) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * @return boolean
     * @Author MengyuWu
     * @Description 改变目录路径
     * @Date 19:41 2018-12-19
     * @Param [directory]
     **/

    public static boolean changeWorkingDirectory(String directory, FTPClient ftpClient) {
        boolean flag = true;
        try {
            flag = ftpClient.changeWorkingDirectory(directory);
            if (flag) {
                System.out.println("进入文件夹" + directory + " 成功！");

            } else {
                System.out.println("进入文件夹" + directory + " 失败！开始创建文件夹");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return flag;
    }

    /**
     * @return
     * @Author MengyuWu
     * @Description 创建多层目录文件，如果有ftp服务器已存在该文件，则不创建，如果无，则创建
     * @Date 19:42 2018-12-19
     * @Param
     **/

    public static boolean CreateDirecroty(String remote, FTPClient ftpClient) throws IOException {
        boolean success = true;
        String directory = remote + "/";
        // 如果远程目录不存在，则递归创建远程服务器目录
        if (!directory.equalsIgnoreCase("/") && !changeWorkingDirectory(new String(directory), ftpClient)) {
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf("/", start);
            String path = "";
            String paths = "";
            while (true) {
                String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), "iso-8859-1");
                path = path + "/" + subDirectory;
                if (!existFile(path, ftpClient)) {
                    if (makeDirectory(subDirectory, ftpClient)) {
                        changeWorkingDirectory(subDirectory, ftpClient);
                    } else {
                        System.out.println("创建目录[" + subDirectory + "]失败");
                        changeWorkingDirectory(subDirectory, ftpClient);
                    }
                } else {
                    changeWorkingDirectory(subDirectory, ftpClient);
                }

                paths = paths + "/" + subDirectory;
                start = end + 1;
                end = directory.indexOf("/", start);
                // 检查所有目录是否创建完毕
                if (end <= start) {
                    break;
                }
            }
        }
        return success;
    }

    /**
     * @return boolean
     * @Author MengyuWu
     * @Description 判断ftp服务器文件是否存在
     * @Date 19:42 2018-12-19
     * @Param [path]
     **/

    public static boolean existFile(String path, FTPClient ftpClient) throws IOException {
        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        if (ftpFileArr.length > 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * @return boolean
     * @Author MengyuWu
     * @Description 创建目录
     * @Date 19:42 2018-12-19
     * @Param [dir]
     **/

    public static boolean makeDirectory(String dir, FTPClient ftpClient) {
        boolean flag = true;
        try {
            flag = ftpClient.makeDirectory(dir);
            if (flag) {
                System.out.println("创建文件夹" + dir + " 成功！");

            } else {
                System.out.println("创建文件夹" + dir + " 失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * @return void
     * @Author MengyuWu
     * @Description 下载文件
     * @Date 19:42 2018-12-19
     * @Param [accessoryId, outputStream]
     **/

   /* public static void downloadFromFileServer(String accessoryId, ServletOutputStream outputStream) {
        //获取附件servcie
        AccessoryService accessoryService = SpringContextUtils.getBean("accessoryService", AccessoryService.class);

        //根据附件id下载相关的附件
        AccessoryEntity accessoryEntity = accessoryService.selectById(accessoryId);
        if (UtilValidate.isEmpty(accessoryEntity)) {
            logger.info("文件不存在");
        }
        FTPClientPool ftpClientPool = (FTPClientPool) SpringContextUtils.getBean("ftpClientPool");
        FTPClient ftpClient = null;
        try {
            ftpClient = ftpClientPool.borrowObject();
            System.out.println("开始下载文件");
            //切换FTP对应的文件目录
            boolean test = ftpClient.changeWorkingDirectory(accessoryEntity.getFilePath());
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for (FTPFile file : ftpFiles) {
                if (file.getName().equals(accessoryEntity.getId() + "." + accessoryEntity.getAccessoryType())) {
                    ftpClient.retrieveFile(file.getName(), outputStream);
                    outputStream.close();
                }
            }
            ftpClient.logout();
            System.out.println("下载文件成功");
        } catch (Exception e) {
            System.out.println("下载文件失败");
            e.printStackTrace();
        } finally {
            ftpClientPool.returnObject(ftpClient);
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

    public static OutputStream downloadFromFileServer(String accessoryId, OutputStream outputStream) {
        //获取附件servcie
        AccessoryService accessoryService = SpringContextUtils.getBean("accessoryService", AccessoryService.class);

        //根据附件id下载相关的附件
        AccessoryEntity accessoryEntity = accessoryService.selectById(accessoryId);
        if (UtilValidate.isEmpty(accessoryEntity)) {
            logger.info("文件不存在");
        }
        FTPClientPool ftpClientPool = (FTPClientPool) SpringContextUtils.getBean("ftpClientPool");
        FTPClient ftpClient = null;
        try {
            ftpClient = ftpClientPool.borrowObject();
            System.out.println("开始下载文件");
            //切换FTP对应的文件目录
            boolean test = ftpClient.changeWorkingDirectory(accessoryEntity.getFilePath());
            //每次数据连接之前，ftp client告诉ftp server开通一个端口来传输数据。
            ftpClient.enterLocalPassiveMode();
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for (FTPFile file : ftpFiles) {
                if (file.getName().equals(accessoryEntity.getId() + "." + accessoryEntity.getAccessoryType())) {
                    ftpClient.retrieveFile(file.getName(), outputStream);
                    outputStream.close();
                }
            }
            //不需要退出  要不然返回到ftp连接池就会报错啦
            //ftpClient.logout();
            System.out.println("下载文件成功");
        } catch (Exception e) {
            System.out.println("下载文件失败");
            e.printStackTrace();
        } finally {
            ftpClientPool.returnObject(ftpClient);
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  outputStream;
    }

    /**
     * @Author MengyuWu
     * @Description 删除文件
     * @Date 19:43 2018-12-19
     * @Param [pathname, filename]
     * @return boolean
     **/
    
    public boolean deleteFile(String pathname, String filename){
        boolean flag = false;
        FTPClientPool ftpClientPool = (FTPClientPool) SpringContextUtils.getBean("ftpClientPool");
        FTPClient ftpClient = null;

        try {
            System.out.println("开始删除文件");
            ftpClient = ftpClientPool.borrowObject();
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            ftpClient.dele(filename);
            //ftpClient.logout();
            flag = true;
            System.out.println("删除文件成功");
        } catch (Exception e) {
            System.out.println("删除文件失败");
            e.printStackTrace();
        } finally {
            ftpClientPool.returnObject(ftpClient);
        }
        return flag;
    }


}

