package com.lawschool.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.jms.Session;
import javax.servlet.ServletOutputStream;
import java.io.*;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

@Component
public class FileUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 附件的路径
     */
    private static String  ftpPath;
    /**
     * 登录名
     */
    private static String  username;

    /**
     * 密码
     */
    private static String  password;

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


    /**
     * @Author MengyuWu
     * @Description 上传文件到文件服务器 windows
     * @Date 11:33 2018-12-6
     * @Param [path, filename, input]
     * @return boolean
     **/
    
    public static Result uploadToFTPServer(String filename,InputStream input)
    {
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
            filename = new String(filename.getBytes("GBK"),"iso-8859-1");
            ftp.storeFile(filename, input); //开始上传文件
            input.close();//关闭文件输入流
            ftp.logout();//断开和ftp服务器之间的连接
            String filePath=ftpPath+curDate;
            result.put("filePath",filePath);
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
    
    public static void downloadFromFileServer(  String path,String filename,ServletOutputStream outputStream) throws SocketException, IOException
    {
        FTPClient ftp = new FTPClient();  //创建一个客户端实例

        ftp.connect(ftpPath);//连接FTP服务器
        //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
        ftp.login(username, password);//登录
        int reply = ftp.getReplyCode(); //获得返回的代码，来判断连接状态
        ftp.changeWorkingDirectory(path);//转移到FTP服务器目录
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
        }


        FTPFile[] fs = ftp.listFiles();

        for(FTPFile ff:fs){
            if(ff.getName().equals(filename)){//如果找到abc.txt则进行下载 ，可以自己设置要下载的文件名称
                ftp.retrieveFile(ff.getName(), outputStream); //开始下载文件
            }
        }

        ftp.logout();
    }
   /* public ChannelSftp connect(String host, int port, String username,
                               String password) {
        ChannelSftp sftp = null;
        try {
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            Session sshSession = jsch.getSession(username, host, port);
            System.out.println("Session created.");
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            System.out.println("Session connected.");
            System.out.println("Opening Channel.");
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            System.out.println("Connected to " + host + ".");
        } catch (Exception e) {

        }
        return sftp;
    }*/

    /**
     * 上传文件
     * @param directory 上传的目录
     * @param uploadFile 要上传的文件
     * @param sftp
     */
    /*public void upload(String directory, String uploadFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            File file=new File(uploadFile);
            sftp.put(new FileInputStream(file), file.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 下载文件
     * @param directory 下载目录
     * @param downloadFile 下载的文件
     * @param saveFile 存在本地的路径
     * @param sftp
     */
    /*public void download(String directory, String downloadFile,String saveFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            File file=new File(saveFile);
            sftp.get(downloadFile, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

}

