package com.appinfo.controller;
import com.appinfo.pojo.AppCategory;
import com.appinfo.pojo.AppInfo;
import com.appinfo.pojo.DataDictionary;
import com.appinfo.pojo.DevUser;
import com.appinfo.service.AppCategoryService;
import com.appinfo.service.AppInfoService;
import com.appinfo.service.DataDistionaryService;
import com.appinfo.util.PageBean;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping(value = "/appinfo")
public class AppInfoController {
        private static Logger logger=Logger.getLogger(AppInfoController.class);

        @Resource(name = "appInfoService")
        private AppInfoService appInfoService;

        @Resource(name ="dataDistionaryService")
        private DataDistionaryService dataDistionaryService;

        @Resource(name = "appCategoryService")
        private AppCategoryService appCategoryService;


        //查询页面
        @RequestMapping(value = "/appinfolist.html")
        public String appList(@RequestParam(value = "softwareName",required = false)String softwareName,
                              @RequestParam(value = "status",required = false)Integer status,
                              @RequestParam(value = "flatformId",required = false)Integer flatformId,
                              @RequestParam(value = "categoryLevel1",required = false)Integer categoryLevel1,
                              @RequestParam(value = "categoryLevel2",required = false)Integer categoryLevel2,
                              @RequestParam(value = "categoryLevel3",required = false)Integer categoryLevel3,
                              @RequestParam(value = "pageIndex",required = false,defaultValue = "1") Integer pageIndex,
                              @RequestParam(value = "pageSize",required = false,defaultValue = "5") Integer pageSize,
                              Model model,HttpSession session
                              ){

            //查询分页
            DevUser devUser = (DevUser) session.getAttribute("devUserSession");
            AppInfo appInfo=new AppInfo();
            appInfo.setDevId(devUser.getId());
            if(softwareName!=null){
                appInfo.setSoftwareName(softwareName);
            }
            if(status!=null){
                appInfo.setStatus(status);
            }
            if(flatformId!=null){
                appInfo.setFlatformId(flatformId);
            }
            if(categoryLevel1!=null){
                appInfo.setCategoryLevel1(categoryLevel1);
            }
            if(categoryLevel2!=null){
                appInfo.setCategoryLevel2(categoryLevel2);
            }
            if(categoryLevel3!=null){
                appInfo.setCategoryLevel3(categoryLevel3);
            }
            PageBean<AppInfo> appByLikePage = appInfoService.findAppByLikePage(appInfo, pageIndex, pageSize);
            model.addAttribute("pageBean",appByLikePage);

            //查询条件
            //App状态
            List<DataDictionary> appStatusList = dataDistionaryService.findStatusByAll("APP_STATUS");
            //所属平台
            List<DataDictionary> appFlatformList = dataDistionaryService.findStatusByAll("APP_FLATFORM");

            //一级分类
            List<AppCategory> categoryLevel1List = appCategoryService.findCategoryByParentId(null);

            //保存
            model.addAttribute("statusList",appStatusList);
            model.addAttribute("flatFormList",appFlatformList);
            model.addAttribute("categoryLevel1List",categoryLevel1List);

            return "developer/appinfolist";
        }


        //跳到增加页面
        @RequestMapping(value = "/add.html")
        public String add(Model model){

            //所属平台
            List<DataDictionary> appFlatformList = dataDistionaryService.findStatusByAll("APP_FLATFORM");
            //一级分类
            List<AppCategory> categoryLevel1List = appCategoryService.findCategoryByParentId(null);

            model.addAttribute("flatFormList",appFlatformList);
            model.addAttribute("categoryLevel1List",categoryLevel1List);

            return "developer/appinfoadd";

        }

        //增加app基础信息
        @RequestMapping(value = "/addapp.do",method = RequestMethod.POST)
        public String addDo(@Valid AppInfo appInfo, HttpSession session, Model model, @RequestParam(value = "a_logoPicPath",required = false) MultipartFile multipartFile){

           //1.获取项目文件夹的服务器绝对路径 保存用
            String realPath=session.getServletContext().getRealPath("statics"+File.separator+"uploadfiles");

            //2.判断上传非空
            if(!multipartFile.isEmpty()){
                //2.1获取全文件名
                String originalFilename = multipartFile.getOriginalFilename();

                //2.2获取全文件名的后缀
                String prifix = FilenameUtils.getExtension(originalFilename);

                //3.判断后缀是否正确
                if(prifix.equalsIgnoreCase("jpg")||
                        prifix.equalsIgnoreCase("png")||
                        prifix.equalsIgnoreCase("jpeg")||
                        prifix.equalsIgnoreCase("gif")||
                        prifix.equalsIgnoreCase("pneg")
                        ){
                    //4.判断文件大小是否符合限制 5M以下符合
                    if(multipartFile.getSize()<5000*1024){
                        //新的文件名
                        String newName=System.currentTimeMillis()+new Random().nextInt(10000)+"_Personal.jpg";

                        //创建文件夹
                        new File(realPath).mkdirs();
                        //创建文件io
                        File newFile=new File(realPath,newName);

                        //保存
                        try {
                            multipartFile.transferTo(newFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        String locPath=realPath+File.separator+newName;
                        appInfo.setLogoLocPath(locPath);
                        appInfo.setLogoPicPath(File.separator+"statics"+File.separator+"uploadfiles"+File.separator+newName);

                    }else {
                        model.addAttribute("msg", "文件不能大于5M");
                        return "developer/appinfoadd";
                    }

                }else {
                    model.addAttribute("msg","上传文件不支持该格式");
                    return "developer/appinfoadd";
                }
            }
            int i = appInfoService.addAppInfo(appInfo);
            if(i>0){
                return "redirect:/appinfo/appinfolist.html";
            }else {
                return "developer/appinfoadd";
            }
        }

        //跳到修改页面
        @RequestMapping(value = "/modify/{id}")
        public String modify(@PathVariable Integer id,Model model){
            AppInfo app = appInfoService.findAppById(id);
            model.addAttribute("appInfo",app);
            //所属平台
            List<DataDictionary> appFlatformList = dataDistionaryService.findStatusByAll("APP_FLATFORM");
            //一级分类
            List<AppCategory> categoryLevel1List = appCategoryService.findCategoryByParentId(null);

            model.addAttribute("flatFormList",appFlatformList);
            model.addAttribute("categoryLevel1List",categoryLevel1List);

            return "developer/appinfomodify";
        }


        //删除图片
        @RequestMapping(value = "/modify.do")
        public String modifyDo(@Valid AppInfo appInfo,@RequestParam(value = "attach",required = false) MultipartFile multipartFile,HttpSession session,Model model
        ){
            //判断非空
            if(!multipartFile.isEmpty()){
                //获取全文件名
                String originalFilename = multipartFile.getOriginalFilename();
                //获取后缀
                String extension = FilenameUtils.getExtension(originalFilename);

                //判断后缀是否正确
                if(extension.equalsIgnoreCase("jpg")||
                        extension.equalsIgnoreCase("png")||
                        extension.equalsIgnoreCase("jpeg")||
                        extension.equalsIgnoreCase("gif")||
                        extension.equalsIgnoreCase("pneg")
                        ){
                    //判断大小是否正确
                    if(multipartFile.getSize()<5000*1024){
                        //穿件新的文件名
                        String newName=System.currentTimeMillis()+new Random().nextInt(10000)+"_Personal.jpg";
                        //保存路径文件夹
                        String realPath=session.getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
                        //新建文件夹
                        new File(realPath).mkdirs();

                        //保存
                        File file=new File(realPath,newName);
                        try {
                            multipartFile.transferTo(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String locPath=realPath+File.separator+newName;
                        appInfo.setLogoLocPath(locPath);
                        appInfo.setLogoPicPath(File.separator+"statics"+File.separator+"uploadfiles"+File.separator+newName);
                    }else {
                        model.addAttribute("msg", "文件不能大于5M");
                        return "developer/appinfomodify";
                    }

                }else {
                    model.addAttribute("msg","上传文件不支持该格式");
                    return "developer/appinfomodify";
                }
            }
            AppInfo appById = appInfoService.findAppById(appInfo.getId());
            if(appById.getLogoPicPath()!=null&&!"".equals(appById.getLogoPicPath())){
                File file=new File(appById.getLogoLocPath());
                file.delete();
            }
            int i = appInfoService.updateApp(appInfo);
            if(i>0){
                return "redirect:/appinfo/appinfolist.html";
            }else {
                model.addAttribute("msg","更新失败");
                return "403";
            }
        }
}
