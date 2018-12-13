package com.lawschool.util;

import com.lawschool.beans.accessory.AccessoryEntity;
import com.lawschool.dao.accessory.AccessoryDao;
import com.lawschool.service.accessory.AccessoryService;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import java.io.*;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FileUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

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
    /*public static void main(String[] args)  throws Exception{
        String src = "D:\\test.doc"; // 本地文件名
       // String dst = "/home"; // 目标文件名
        //File f = new File(src);
        //InputStream in = new FileInputStream(f);
       // uploadToFTPServer("test",in);
       // downloadFromFileServer("20181207","test.doc");
    }*/

    /**
     * @Author MengyuWu
     * @Description 上传文件到文件服务器 windows
     * @Date 11:33 2018-12-6
     * @Param [path, filename, input]
     * @return boolean
     **/
    
    public static Result uploadToFTPServer(String filename,InputStream input)
    {
        //根据文件名截取相关的文件类型
        int start =filename.lastIndexOf(".");
        String type=filename.substring(start + 1, filename.length());//后缀名
        //获取附件servcie
        AccessoryService accessoryService = SpringContextUtils.getBean("accessoryService", AccessoryService.class);

        AccessoryEntity accessoryEntity = new AccessoryEntity();
        accessoryEntity.setId(GetUUID.getUUIDs("AE"));//设置id
        accessoryEntity.setAccessoryName(filename);//设置文件名
        accessoryEntity.setAccessoryType(type);//设置文件类型
        Result result = new Result();
        FTPClient ftp = new FTPClient(); //创建一个客户端实例
        try {
            int reply;
            ftp.connect(ftpPath);//连接FTP服务器
            //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftp.login(username, password);//登录
            reply = ftp.getReplyCode(); //获得返回的代码，来判断连接状态

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result.error("连接错误");
            }
            String curDate = new SimpleDateFormat("yyyyMMdd").format(new Date());

            ftp.makeDirectory(curDate);//创建文件目录
            ftp.changeWorkingDirectory(curDate);
            ftp.setFileType(FTP.BINARY_FILE_TYPE);//设置文件以二进制的形式上传，防止文件内容乱码出现
            //设置文件名字的编码格式为iso-8859-1，因为FTP上传的时候默认编码为iso-8859-1，解决文件名字乱码的问题
           // filename = new String(filename.getBytes("GBK"),"iso-8859-1"); accessoryEntity.getId()
            // 附件的id作为文件的名称  唯一标识
            ftp.storeFile(accessoryEntity.getId(), input); //开始上传文件
            input.close();//关闭文件输入流
            ftp.logout();//断开和ftp服务器之间的连接
            String filePath=ftpPath+"/"+curDate;
            accessoryEntity.setFilePath(filePath);//设置文件路径
            accessoryService.insert(accessoryEntity);//保存文件信息
            result.put("accessoryId",accessoryEntity.getId());
            return result.ok();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                }
                catch (IOException ioe) {
                }
            }
        }
        return result.error("未知异常");

    }


    /**
     * @Author MengyuWu
     * @Description 从ftp文件服务器上下载文件 windows
     * @Date 14:14 2018-12-6
     * @Param [path, filename, outputStream]
     * @return void
     **/
    
    public static void downloadFromFileServer(  String accessoryId,ServletOutputStream outputStream) throws SocketException, IOException
    {
        //获取附件servcie
        AccessoryService accessoryService = SpringContextUtils.getBean("accessoryService", AccessoryService.class);

        //根据附件id下载相关的附件
        AccessoryEntity accessoryEntity = accessoryService.selectById(accessoryId);
        if(UtilValidate.isEmpty(accessoryEntity)){
            logger.info("文件不存在");
        }
        FTPClient ftp = new FTPClient();  //创建一个客户端实例

        ftp.connect(ftpPath);//连接FTP服务器
        //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
        ftp.login(username, password);//登录
        int reply = ftp.getReplyCode(); //获得返回的代码，来判断连接状态
        ftp.changeWorkingDirectory(accessoryEntity.getFilePath());//转移到FTP服务器目录
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
        }


        FTPFile[] fs = ftp.listFiles();

        for(FTPFile ff:fs){
            if(ff.getName().equals(accessoryEntity.getId())){//如果找到abc.txt则进行下载 ，可以自己设置要下载的文件名称
                ftp.retrieveFile(ff.getName(), outputStream); //开始下载文件
                //设置要下载到的目录
                /*File localFile = new File("D:\\12345678.doc");
                //得到输出流
                OutputStream is = new FileOutputStream(localFile);
                ftp.retrieveFile(ff.getName(), is); //开始下载文件
                is.close();*/
            }
        }

        ftp.logout();
    }

}

