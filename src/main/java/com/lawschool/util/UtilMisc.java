package com.lawschool.util;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.controller.AbstractController;
import org.apache.commons.io.IOUtils;
import org.springframework.util.ResourceUtils;
import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 常用工具
 *
 * @author LiCheng
 * @data 2018.3.12
 */
@SuppressWarnings("all")
public class UtilMisc extends AbstractController {

   // private SysUserEntity user;
//    private SysDeptService sysUserService = SpringContextUtils.getBean("sysUserService", SysDeptService.class);
//    private SysDeptService sysDeptService = SpringContextUtils.getBean("sysDeptService", SysDeptService.class);
//    private SysDictService sysDictService = SpringContextUtils.getBean("sysDictService", SysDictService.class);
//    private AccessoryService accessoryService = SpringContextUtils.getBean("accessoryService", AccessoryService.class);


   /* public UtilMisc() {
        user = getUser();
    }*/

    /**
     * 生成各种编号 如立案编号....
     *
     * @param head 编号头
     * @return
     */
   /* public String getNumber(String head) {
        SysDeptService sysDeptService = SpringContextUtils.getBean("sysDeptService", SysDeptService.class);

        //强制转大写
        head = head.toUpperCase();

        //获取部门id
        Long deptId = user.getDeptId();
        SysDeptEntity sysDeptEntity = sysDeptService.selectOne(new EntityWrapper<SysDeptEntity>()
                .setSqlSelect("parent_id", "dept_code")
                .eq("dept_id", deptId));


        if (UtilValidate.isNotEmpty(sysDeptEntity)) {
            //准备临时集合
            ArrayList<String> tempList = new ArrayList<>();

            //获取code值
            String deptCode = sysDeptEntity.getDeptCode();

            //拼装对象初始化
            StringBuilder sb = new StringBuilder();
            //拼接头
            sb.append(head);
            sb.append(deptCode);
            sb.append("00");

            String substring = sb.substring(0, 8);
            sb = new StringBuilder(substring);

            //拼接部门代码
*//*            for (String s : tempList) {
                sb.append(s);
            }*//*

    *//*        //TODO 在此处改子单位编号
            sb.append("0000");*//*

            //拼装流水号
            sb.append(getStringDate()).append((int) (Math.random() * 9000) + 1000);

            return sb.toString();
        } else
            throw new RuntimeException("部门数据获取失败!");

    }*/

    /**
     * 抽取元数据 设置创建信息
     *
     * @param object      目标对象
     * @param idFeildName 主键字段名
     */
   /* public void setCreateInfo(Object object, String idFeildName) {
        //获取当前用户登录信息
        SysUserEntity user = getUser();

        BeanInfo beanInfo = null;
        try {
            //获取元数据
            beanInfo = Introspector.getBeanInfo(object.getClass());
            PropertyDescriptor[] pdp = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : pdp) {
                String name = descriptor.getName();
                //设置主键
                if (name.equals(idFeildName)) {
                    Method updWriteMethod = descriptor.getWriteMethod();
                    updWriteMethod.invoke(object, (IdWorker.getId() + ""));
                }
                //设置权限信息
                if (name.equals("deptId")) {
                    Method updWriteMethod = descriptor.getWriteMethod();
           *//*         if(object instanceof Long ){
                        updWriteMethod.invoke(object, user.getDeptId());
                    }else if(object instanceof String ){
                        updWriteMethod.invoke(object, String.valueOf(user.getDeptId()));
                    }*//*
                    try {
                        updWriteMethod.invoke(object, user.getDeptId());
                    } catch (Exception e) {
                        updWriteMethod.invoke(object, String.valueOf(user.getDeptId()));
                    }
                }

                if (name.equals("deptCode")) {
                    Method updWriteMethod = descriptor.getWriteMethod();
           *//*         if(object instanceof Long ){
                        updWriteMethod.invoke(object, user.getDeptId());
                    }else if(object instanceof String ){
                        updWriteMethod.invoke(object, String.valueOf(user.getDeptId()));
                    }*//*
                    try {
                        updWriteMethod.invoke(object, user.getDeptCode());
                    } catch (Exception e) {
                        updWriteMethod.invoke(object, String.valueOf(user.getDeptId()));
                    }
                }
                //更新
                if (name.equals("lastUpdTime")) {
                    Method updWriteMethod = descriptor.getWriteMethod();
                    updWriteMethod.invoke(object, new Date());
                }
                if (name.equals("lastUpdPersonId")) {
                    Method updWriteMethod = descriptor.getWriteMethod();
                   *//* if(object instanceof Long ){
                        updWriteMethod.invoke(object, user.getUserId());
                    }else if(object instanceof String ){
                        updWriteMethod.invoke(object, String.valueOf(user.getUserId()));
                    }*//*
                    try {
                        updWriteMethod.invoke(object, Long.parseLong(user.getUserId()));
                    } catch (Exception e) {
                        updWriteMethod.invoke(object, String.valueOf(user.getUserId()));
                    }
                }

                if (name.equals("lastUpdPersonName")) {
                    Method updWriteMethod = descriptor.getWriteMethod();
                    updWriteMethod.invoke(object, user.getNickName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    /**
     * 抽取元数据 更新实体修改信息
     *
     * @param object 目标对象
     */
    /*public void setUpdateInfo(Object object) {
        //获取当前用户登录信息
        SysUserEntity user = getUser();

        BeanInfo beanInfo = null;
        try {
            //获取元数据
            beanInfo = Introspector.getBeanInfo(object.getClass());
            PropertyDescriptor[] pdp = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : pdp) {
                //更新
                if (descriptor.getName().equals("deptId")) {
                    Method updWriteMethod = descriptor.getWriteMethod();
                    try {
                        updWriteMethod.invoke(object, user.getDeptId());
                    } catch (Exception e) {
                        updWriteMethod.invoke(object, String.valueOf(user.getDeptId()));
                    }
                }

                if (descriptor.getName().equals("lastUpdTime")) {
                    Method updWriteMethod = descriptor.getWriteMethod();
                    updWriteMethod.invoke(object, new Date());
                }
                if (descriptor.getName().equals("lastUpdPersonId")) {
                    Method updWriteMethod = descriptor.getWriteMethod();
                   *//* if (object instanceof Long) {
                        updWriteMethod.invoke(object, user.getUserId());
                    } else if (object instanceof String) {
                        updWriteMethod.invoke(object, String.valueOf(user.getUserId()));
                    }*//*
                    try {
                        updWriteMethod.invoke(object, user.getUserId());
                    } catch (Exception e) {
                        updWriteMethod.invoke(object, String.valueOf(user.getUserId()));
                    }
                }
                if (descriptor.getName().equals("lastUpdPersonName")) {
                    Method updWriteMethod = descriptor.getWriteMethod();
                    updWriteMethod.invoke(object, user.getNickName());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    /**
     * 获取现在时间
     *
     * @return返回字符串格式yyyyMMddHHmmss
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }



    //list去重
    public   static   List  removeDuplicate(List list)  {
        for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )  {
            for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )  {
                if  (list.get(j).equals(list.get(i)))  {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    public static Date getDate() {
        Date day = new Date();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            day = df.parse(df.format(day));
        } catch (ParseException e) {

        }

        return day;
    }
    public static Date getDateByHHMMSS() {
        Date day = new Date();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            day = df.parse(df.format(day));
        } catch (ParseException e) {

        }

        return day;
    }
    public static int getMonth() {
        Calendar cale = null;
        cale = Calendar.getInstance();
        int month = cale.get(Calendar.MONTH) + 1;
        return month;
    }

    public static String getYear() {
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        return String.valueOf(year);
    }

    /**
     * 抽取元数据 查询字典数据获取value
     *
     * @param object
     * @param codeJavaFiledName code所在的字段名 java的Set方法名 首字母小写
     */


    /**
     * 　　* @Description: 获取时间戳--暂时用于生成编号
     * 　　* @param
     * 　　* @return
     * 　　* @throws
     * 　　* @author MengyuWu
     * 　　* @date 2018/3/26 20:32
     */
    public static String getTimestamp() {
        Random jjj = new Random();
        Date currentTime = new Date();

        String randomNum = "";

        // 获取当前时间
        SimpleDateFormat formatter = new SimpleDateFormat("YYYYMMddHHmmss");
        String dateString = formatter.format(currentTime);

        // 取6位随机数
        for (int k = 0; k < 6; k++) {
            randomNum = randomNum + jjj.nextInt(9);
        }

        return dateString + randomNum;
    }

    /**
     * 公共文件上传
     *
     * @param request
     * @param fkName  外键名称
     * @param entity  对应实体类
     * @return
     */
    /*public Result upload(MultipartHttpServletRequest request) {

        AccessoryService accessoryService = SpringContextUtils.getBean("accessoryService", AccessoryService.class);
        List<MultipartFile> importfile = request.getFiles("importfile");
        if (UtilValidate.isEmpty(importfile)) {
            throw new RuntimeException("上传失败：文件为空");
        }

        //新建附件对象
        AccessoryEntity accessoryEntity = new AccessoryEntity();
        //建立附件id记录集合
        StringBuilder sb = new StringBuilder();

        for (MultipartFile multipartFile : importfile) {
            //获取文件名
            String originalFilename = multipartFile.getOriginalFilename();
            if (originalFilename.contains(",")) {
                originalFilename = originalFilename.replaceAll(",", "");
            }
            //设置文件类型
            String filetype = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            accessoryEntity.setAccessoryType("." + filetype);
            try {
                //设置附件内容
                byte[] bytes = multipartFile.getBytes();
                accessoryEntity.setAccessoryContent(bytes);
            } catch (IOException e) {
                return Result.error("上传失败: 附件内容异常!");
            }

            //设置附件id
            String id = IdWorker.getIdStr();
            accessoryEntity.setAccessoryId(id);
            //附件名称
            accessoryEntity.setAccessoryName(originalFilename);

            //记录更新数据
            accessoryEntity.setLastUpdTime(UtilMisc.getDate());
            accessoryEntity.setLastUpdPersonId(getUserId() + "");
            accessoryEntity.setLastUpdPersonName(getUser().getNickName());

            //插入到数据库
            try {
                accessoryService.insert(accessoryEntity);
            } catch (Exception e) {
                return Result.error("文件过大,上传失败!");
            }
            //插入成功后记录id
            sb.append(id).append(",");
        }

        //最后处理
        String substring = sb.substring(0, sb.length() - 1);

        return Result.ok().put("data", substring);
    }*/


    /**
     * 　　* @Description: 上传的PDF转成图片
     * 　　* @param
     * 　　* @return
     * 　　* @throws
     * 　　* @author MengyuWu
     * 　　* @date 2018/4/13 16:26
     */

    /**
     * 　　* @Description: 把byte数组转成相应的文件
     * 　　* @param
     * 　　* @return
     * 　　* @throws
     * 　　* @author MengyuWu
     * 　　* @date 2018/4/13 16:32
     */
    public File getFileFromBytes(byte[] b) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:demo-images.pdf");
            IOUtils.toByteArray(new FileInputStream(file));
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * service中获取当前登录用户信息的方法
     *
     * @return
     */


//往前推2天
    public static String getLastDayOfMonthTwo(int year,int month)
    {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //当前月份最后一天减两天
        cal.set(Calendar.DAY_OF_MONTH, lastDay-2);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());

        return lastDayOfMonth;
    }

    //
    public static String getCurrDayTwo()
    {
        Calendar cal = Calendar.getInstance();
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置当前时间+2
        cal.set(Calendar.DAY_OF_MONTH+2, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currDayTwo = sdf.format(cal.getTime());

        return currDayTwo;
    }

    public static String getCurrDayTwoMonthOne ()
    {
//        String str="2012-1-5";
//        SimpleDateFormat sdf;
//        Date date;
//        Calendar calendar = Calendar.getInstance();
//        try {
//            sdf= new SimpleDateFormat("yyyy-MM-dd");
//            date =sdf.parse(str);
//            calendar.setTime(date);
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//
//        calendar.add(Calendar.MONTH, -1);// 先退 月份减一  然后在 减去 日期 2天
//        calendar.add(Calendar.DATE, - 2);  //天数退2天
//        Date m = calendar.getTime();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        String mon = format.format(m);


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);// 先退 月份减一  然后在 减去 日期 2天
        calendar.add(Calendar.DATE, - 2);  //天数退2天
        Date m = calendar.getTime();
        String riqi = format.format(m);
        return riqi;
    }

//当前时间往后2天
    public static String getnowdatetwoday ()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,  2);  //天数前2天
        Date m = calendar.getTime();
        String riqi = format.format(m);
        return riqi;
    }

    //当前时间往后2天
    public static String getnowdatetwoday2 ()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,  2);  //天数前2天
        Date m = calendar.getTime();
        String riqi = format.format(m);
        return riqi;
    }

   /* public static String getConfig(String key){
        String value="";
        SysConfigService sysConfigService = SpringContextUtils.getBean("sysConfigService", SysConfigService.class);
        SysConfigEntity configEntity=sysConfigService.selectOne(new EntityWrapper<SysConfigEntity>().eq("key",key));
        if(UtilValidate.isNotEmpty(configEntity)){
            value=configEntity.getValue();
        }
        return value;
    }*/






    public static String getCityId(String cityName)
    {
        String cityId="";
        switch(cityName){
            case "泰州市":cityId="192dc1d76bf04885ad163878c02cf548"; break;
            case "宿迁市":cityId="565157a02b63459d83a870d7aa7f6e05"; break;
            case "盐城市":cityId="eb2ce442e06743cb8b7be9eaf678709b"; break;
            case "无锡市":cityId="fafd3c1344a849e993dabf98c6403285"; break;
            case "镇江市":cityId="fafd3c1344a849e993dabf98c6490787"; break;
            case "南通市":cityId="fafd3c1344a849e993dabf98c6554029"; break;
            case "连云港市":cityId="fafd3c1344a849e993dabf98c6674311"; break;
            case "苏州市":cityId="fafd3c1344a849e993dabf98c6680626"; break;
            case "淮安市":cityId="fafd3c1344a849e993dabf98c6709469"; break;
            case "扬州市":cityId="fafd3c1344a849e993dabf98c6762886"; break;
            case "徐州市":cityId="fafd3c1344a849e993dabf98c6774548"; break;
            case "常州市":cityId="fafd3c1344a849e993dabf98c6965278"; break;
            case "南京市":cityId="fafd3c1344a849e993dabf98c6966615"; break;
            default:
                System.out.println("没有找到.");
        }
        return cityId;
    }


    public static String createData(int length) {
        StringBuilder sb=new StringBuilder();
        Random rand=new Random();
        for(int i=0;i<length;i++)
        {
            sb.append(rand.nextInt(10));
        }
        String data=sb.toString();

        return data;
    }
    public static String getdisposalResultsName(String code)
    {
        String Name="";
        switch(code){
            case "1035447283221342049":Name="行政处罚"; break;
            case "1025444283322260569":Name="撤销"; break;
            case "1033447283224342549":Name="移送"; break;
            case "1035447283324362549":Name="不予行政处罚"; break;
            default:
                System.out.println("没有找到.");
        }
        return Name;
    }
}
