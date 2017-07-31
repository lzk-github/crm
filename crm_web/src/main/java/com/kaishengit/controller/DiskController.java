package com.kaishengit.controller;

import com.kaishengit.controller.exception.NotFoundException;
import com.kaishengit.dto.AjaxResult;
import com.kaishengit.entity.Disk;
import com.kaishengit.service.DiskService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequestMapping("/disk")
public class DiskController {

    @Autowired
    private DiskService diskService;

    /**
     * 访问公司网盘页面
     *
     * @return
     */
    @GetMapping("/list")
    public String diskList(@RequestParam(required = false, defaultValue = "0") Integer pid,
                           Model model) {

        //根据前端传来的pId查找对应pId下的文件和文件夹,封装到list集合中
        List<Disk> diskList = diskService.findDisksByPId(pid);
        model.addAttribute("diskList", diskList);

        //如果Pid != 0, 那么访问的就不是根目录,把pid对应的那个id所属的Disk对象传到前端
        //这个Disk对象就是上面Disk列表的父目录
        if (!new Integer("0").equals(pid)) {
            Disk parentDisk = diskService.findDiskById(pid);
            model.addAttribute("parentDisk", parentDisk);
        }

        return "disk/disk-list";
    }

    /**
     * 新建文件夹的异步请求
     *
     * @param accountId  当前员工id
     * @param parId      新文件夹的父路径,即pid
     * @param folderName 新文件夹的名字
     * @return
     */
    @PostMapping("/new/folder")
    @ResponseBody
    public AjaxResult newFolder(Integer accountId, Integer parId, String folderName) {
        //将前端传的值封装到Disk对象中
        Disk disk = new Disk();
        disk.setAccountId(accountId);
        disk.setPid(parId);
        disk.setName(folderName);
        disk.setType("dir");

        //新增文件夹
        diskService.saveNewFolder(disk);

        //查出网盘文件列表,并传值到前端
        List<Disk> diskList = diskService.findDisksByPId(parId);

        return AjaxResult.getSuccessInstance(diskList);
        //TODO disk-list 页面,新建文件夹ajax请求后,table表格刷新出来的 三个点 的下拉选项还没加href
    }

    /**
     * 文件上传
     * @param file 上传的文件对象
     * @param parId 当前文件夹的父目录id
     * @param accountId 当前登录员工id
     * @return 返回执行结果
     * @throws IOException
     */
    @PostMapping("/upload")
    @ResponseBody
    public AjaxResult upload(MultipartFile file, Integer parId, Integer accountId) throws IOException {
        String name = file.getOriginalFilename();
        String fileSize = FileUtils.byteCountToDisplaySize(file.getSize());

        Disk disk = new Disk();
        disk.setName(name);
        disk.setAccountId(accountId);
        disk.setPid(parId);
        disk.setType("file");
        disk.setFileSize(fileSize);
        //保存并上传文件
        diskService.saveNewFile(disk, file.getInputStream());

        //查找文件列表,传值至前端
        List<Disk> diskList = diskService.findDisksByPId(parId);
        return AjaxResult.getSuccessInstance(diskList);
    }

    /**
     * 重命名文件名字
     * @return
     */
    @PostMapping("/rename")
    @ResponseBody
    public AjaxResult rename(Integer id,String newName) {

        Disk disk = diskService.findDiskById(id);
        disk.setName(newName);

        diskService.updateDisk(disk);

        //查找文件列表,传值至前端
        List<Disk> diskList = diskService.findDisksByPId(disk.getPid());
        return AjaxResult.getSuccessInstance(diskList);
    }

    /**
     * 文件删除
     * @param id
     * @return
     */
    @PostMapping("/del")
    @ResponseBody
    public AjaxResult del(Integer id) {
        Disk disk = diskService.findDiskById(id);


        AjaxResult ajaxResult = new AjaxResult();
        if(disk == null) {
            ajaxResult.setState("error");
            ajaxResult.setMessage("要删除的文件不存在");
        } else{
            //执行删除
            diskService.recurseDel(disk);

            ajaxResult.setState("success");
            //查找文件列表,传值至前端
            List<Disk> diskList = diskService.findDisksByPId(disk.getPid());
            ajaxResult.setData(diskList);
        }
        return ajaxResult;
    }

    /**
     * 文件下载
     * @param id
     * @param response
     * @param session
     * @throws IOException
     */
    @GetMapping("/download")
    public void download(@RequestParam(required = true,name = "id") Integer id,
                         HttpServletResponse response,HttpSession session) throws IOException {

        Disk disk = diskService.findDiskById(id);
        if(disk == null) {
            throw new NotFoundException("文件不存在");
        }
        if(!disk.getType().equals("file")) {
            throw new NotFoundException("文件类型错误");
        }

        String fileName = disk.getName();
        fileName = new String(fileName.getBytes("UTF-8"),"ISO8859-1");
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition","attachment; filename=\""+fileName+"\"");
        diskService.downloadFile(response.getOutputStream(),disk);

    }

}
