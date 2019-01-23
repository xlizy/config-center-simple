package com.xlizy.middleware.cc.server.controller;

import com.xlizy.middleware.cc.server.annotations.OperationLog;
import com.xlizy.middleware.cc.server.common.base.BaseController;
import com.xlizy.middleware.cc.server.common.base.Results;
import com.xlizy.middleware.cc.server.common.utils.EnumComboUtil;
import com.xlizy.middleware.cc.server.common.utils.RequestUtils;
import com.xlizy.middleware.cc.server.common.utils.CoreUtil;
import com.xlizy.middleware.cc.server.entity.CcApp;
import com.xlizy.middleware.cc.server.entity.CcEnv;
import com.xlizy.middleware.cc.server.entity.CcProperties;
import com.xlizy.middleware.cc.server.enums.Enable;
import com.xlizy.middleware.cc.server.enums.OPType;
import com.xlizy.middleware.cc.server.service.AppService;
import com.xlizy.middleware.cc.server.service.EnvService;
import com.xlizy.middleware.cc.server.service.PropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.URLEncoder;

/**
 * 配置相关
 * @author xlizy
 * @date 2018/5/19
 */
@RestController
@RequestMapping("properties")
public class PropertiesController extends BaseController {

    @Autowired
    PropertiesService propertiesService;

    @Autowired
    EnvService envService;

    @Autowired
    AppService appService;

    /**
     * 获取启用状态枚举列表
     * */
    @GetMapping("getEnables")
    @OperationLog(bizType = OPType.GET_ENABLE_LIST)
    public String getEnables(){
        return Results.dataResult(EnumComboUtil.getComboArray(Enable.getEnables()));
    }

    /**
     * 根据环境ID，及其他查询条件获取配置列表
     * @param request 里面包含了分页信息
     * @param envId 环境ID
     * @param properties 配置对象
     * */
    @GetMapping("list/{envId}")
    @OperationLog(bizType = OPType.GET_PROPERTIES_LIST_FOR_PAGE)
    public String getPropertiess(HttpServletRequest request, @PathVariable("envId")Integer envId, CcProperties properties){
        if(properties == null){
            properties = new CcProperties();
        }
        properties.setEnvId(envId);
        int[] limits = RequestUtils.getPageLimit(request);
        return propertiesService.getPropertiess(properties,limits[0], limits[1]);
    }

    /**
     * 添加配置
     * @param properties 配置对象
     * */
    @PostMapping
    @OperationLog(bizType = OPType.ADD_PROPERTIES)
    public Results addProperties(CcProperties properties){
        return propertiesService.addProperties(properties);
    }

    /**
     * 编辑对象
     * @param properties 配置对象
     * */
    @PutMapping
    @OperationLog(bizType = OPType.EDIT_PROPERTIES)
    public Results editProperties(CcProperties properties) {
        return propertiesService.editProperties(properties);
    }

    /**
     * 删除配置
     * @param envId 环境ID
     * @param ids 配置ID集合
     * */
    @DeleteMapping("{ids}")
    @OperationLog(bizType = OPType.DEL_PROPERTIES)
    public Results deleteProperties(Integer envId,@PathVariable("ids") String ids) {
        return propertiesService.deleteProperties(envId,CoreUtil.strArray2List(ids));
    }

    /**
     * 设置配置启用状态
     * @param envId 环境ID
     * @param ids 配置ID集合
     * @param enable 启用状态
     * */
    @PostMapping("setEnable")
    @OperationLog(bizType = OPType.EDIT_PROPERTIES_ENABLE)
    public Results setEnable(Integer envId,String ids,Enable enable){
        return propertiesService.setEnable(envId,CoreUtil.strArray2List(ids),enable);
    }

    /**
     * 根据主键获取配置信息
     * @param id 配置主键
     * */
    @GetMapping("{id}")
    @OperationLog(bizType = OPType.GET_PROPERTIES)
    public String getProperties(@PathVariable("id") Integer id){
        return Results.dataResult(propertiesService.getProperties(id));
    }

    /**
     * 下载配置文件
     * @param ids 配置ID集合
     * @param envId 环境ID
     * */
    @PostMapping("/downloadProperties")
    @OperationLog(bizType = OPType.DOWNLOAD_PROPERTIES_DATA)
    public void downloadProperties(HttpServletResponse response, @RequestParam(value = "ids",required = false ) String ids, Integer envId) throws Exception {
        String properties = propertiesService.downloadProperties(ids,envId);
        CcEnv env = envService.getEnv(envId);

        String fileName = "";
        if(env != null){
            CcApp app = appService.getApp(env.getAppId());
            fileName += app.getName() + "_";
            fileName += env.getEnv() + "_";
            fileName += env.getVersion() + "_";
            fileName += env.getCluster();
        }
        fileName = URLEncoder.encode(fileName + ".properties","UTF-8");
        response.setContentType("text/plain;charset=UTF-8");
        response.addHeader("Content-Disposition",
                "attachment;filename=" + fileName);
        try (PrintWriter out = response.getWriter()) {
            out.write(properties);
        }
    }

    /**
     * 上传配置文件
     * @param envId 环境ID
     * @param file 文件对象
     * */
    @PostMapping("/uploadProperties")
    @OperationLog(bizType = OPType.UPLOAD_PROPERTIES_DATA)
    public Results uploadProperties(Integer envId,@RequestParam(value = "properties") MultipartFile file) throws Exception {
        return propertiesService.uploadProperties(envId,file.getInputStream());
    }
}
