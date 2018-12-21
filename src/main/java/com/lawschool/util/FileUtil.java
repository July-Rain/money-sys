package com.lawschool.util;

import com.lawschool.beans.accessory.AccessoryEntity;
import com.lawschool.service.accessory.AccessoryService;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletOutputStream;
import java.io.*;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FileUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
    //ftp服务器端口号默认为21
    public static Integer port = 21 ;
    /**
     * 附件的路径
     */
    private static String  ftpPath="192.168.0.208";
    /**
     * 登录名
     */
    private static String  username="admin";

    /**
     * 密码
     */
    private static String  password="sinorock123";


    @Value("${ftp.ftpPath}")
    public static void setFtpPath(String ftpPath) {
        FileUtil.ftpPath = ftpPath;
    }
    @Value("${ftp.username}")
    public static void setUsername(String username) {
        FileUtil.username = username;
    }
    @Value("${ftp.password}")
    public static void setPassword(String password) {
        FileUtil.password = password;
    }

    public static FTPClient ftpClient = null;


    /**
     * @Author MengyuWu
     * @Description 初始化ftp服务器
     * @Date 19:41 2018-12-19
     * @Param []
     * @return void
     **/
    
    public static void initFtpClient() {
        ftpClient = new FTPClient();
        ftpClient.setControlEncoding("utf-8");
        try {
            System.out.println("connecting...ftp服务器:"+ftpPath+":"+port);
            ftpClient.connect(ftpPath, port); //连接ftp服务器
            ftpClient.login(username, password); //登录ftp服务器
            int replyCode = ftpClient.getReplyCode(); //是否成功登录服务器
            if(!FTPReply.isPositiveCompletion(replyCode)){
                System.out.println("connect failed...ftp服务器:"+ftpPath+":"+port);
            }
            System.out.println("connect successfu...ftp服务器:"+ftpPath+":"+port);
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author MengyuWu
     * @Description 上传文件
     * @Date 19:23 2018-12-19
     * @Param [filename, input]
     * @return com.lawschool.util.Result
     **/

    public static Result uploadToFTPServer( String filename,InputStream input){
        Result result = new Result();
        try{
            //根据文件名截取相关的文件类型
            int start =filename.lastIndexOf(".");
            String type=filename.substring(start + 1, filename.length());//后缀名
            //获取附件servcie
            //AccessoryService accessoryService = SpringContextUtils.getBean("accessoryService", AccessoryService.class);
            AccessoryService accessoryService =(AccessoryService)ContextLoaderListener.getCurrentWebApplicationContext().getBean ("accessoryService");

            AccessoryEntity accessoryEntity = new AccessoryEntity();
            accessoryEntity.setId(GetUUID.getUUIDs("AE"));//设置id
            accessoryEntity.setAccessoryName(filename);//设置文件名
            accessoryEntity.setAccessoryType(type);//设置文件类型

            System.out.println("开始上传文件");
            //inputStream = new FileInputStream(new File(originfilename));
            initFtpClient();
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
            String curDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
            //CreateDirecroty(curDate);
            ftpClient.makeDirectory(curDate);
            ftpClient.changeWorkingDirectory(curDate);
            ftpClient.storeFile(accessoryEntity.getId()+"."+type, input);
            input.close();
            ftpClient.logout();
            String filePath=curDate;
            accessoryEntity.setFilePath(filePath);//设置文件路径
            accessoryService.insert(accessoryEntity);//保存文件信息
            result.put("accessoryId",accessoryEntity.getId());
            System.out.println("上传文件成功");


        }catch (Exception e) {
            System.out.println("上传文件失败");
            e.printStackTrace();
            return Result.error("上传文件失败");
        }finally{
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            if(null != input){
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
     * @Author MengyuWu
     * @Description 改变目录路径
     * @Date 19:41 2018-12-19
     * @Param [directory]
     * @return boolean
     **/
    
    public static boolean changeWorkingDirectory(String directory) {
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
     * @Author MengyuWu
     * @Description 创建多层目录文件，如果有ftp服务器已存在该文件，则不创建，如果无，则创建
     * @Date 19:42 2018-12-19
     * @Param 
     * @return 
     **/

    public static boolean CreateDirecroty(String remote) throws IOException {
        boolean success = true;
        String directory = remote + "/";
        // 如果远程目录不存在，则递归创建远程服务器目录
        if (!directory.equalsIgnoreCase("/") && !changeWorkingDirectory(new String(directory))) {
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
                if (!existFile(path)) {
                    if (makeDirectory(subDirectory)) {
                        changeWorkingDirectory(subDirectory);
                    } else {
                        System.out.println("创建目录[" + subDirectory + "]失败");
                        changeWorkingDirectory(subDirectory);
                    }
                } else {
                    changeWorkingDirectory(subDirectory);
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
     * @Author MengyuWu
     * @Description 判断ftp服务器文件是否存在
     * @Date 19:42 2018-12-19
     * @Param [path]
     * @return boolean
     **/
    
    public static boolean existFile(String path) throws IOException {
        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        if (ftpFileArr.length > 0) {
            flag = true;
        }
        return flag;
    }
    /**
     * @Author MengyuWu
     * @Description 创建目录
     * @Date 19:42 2018-12-19
     * @Param [dir]
     * @return boolean
     **/
    
    public static boolean makeDirectory(String dir) {
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
     * @Author MengyuWu
     * @Description 下载文件
     * @Date 19:42 2018-12-19
     * @Param [accessoryId, outputStream]
     * @return void
     **/
    
    public  static void downloadFromFileServer(String accessoryId,ServletOutputStream outputStream){
        //获取附件servcie
        AccessoryService accessoryService = SpringContextUtils.getBean("accessoryService", AccessoryService.class);

        //根据附件id下载相关的附件
        AccessoryEntity accessoryEntity = accessoryService.selectById(accessoryId);
        if(UtilValidate.isEmpty(accessoryEntity)){
            logger.info("文件不存在");
        }
        try {
            System.out.println("开始下载文件");
            initFtpClient();
            //切换FTP主目录
            //ftpClient.changeWorkingDirectory("/");
            //切换FTP对应的文件目录
            boolean test = ftpClient.changeWorkingDirectory(accessoryEntity.getFilePath());
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for(FTPFile file : ftpFiles){
                if(file.getName().equals(accessoryEntity.getId()+"."+accessoryEntity.getAccessoryType())){
                    ftpClient.retrieveFile(file.getName(), outputStream);
                    outputStream.close();
                }
            }
            ftpClient.logout();
            System.out.println("下载文件成功");
        } catch (Exception e) {
            System.out.println("下载文件失败");
            e.printStackTrace();
        } finally{
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            if(null != outputStream){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
        try {
            System.out.println("开始删除文件");
            initFtpClient();
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            ftpClient.dele(filename);
            ftpClient.logout();
            flag = true;
            System.out.println("删除文件成功");
        } catch (Exception e) {
            System.out.println("删除文件失败");
            e.printStackTrace();
        } finally {
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }


}

