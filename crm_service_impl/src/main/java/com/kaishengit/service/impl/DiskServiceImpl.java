package com.kaishengit.service.impl;

import com.kaishengit.entity.Disk;
import com.kaishengit.entity.DiskExample;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.mapper.DiskMapper;
import com.kaishengit.service.DiskService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class DiskServiceImpl implements DiskService {

    @Value("${parent.path}")
    private String parentPath;

    @Autowired
    private DiskMapper diskMapper;

    /**
     * 根据pId(父目录的id)查找对应的文件[夹]集合
     *
     * @param pId
     * @return 网盘文件集合
     */
    @Override
    public List<Disk> findDisksByPId(Integer pId) {
        DiskExample diskExample = new DiskExample();
        diskExample.createCriteria().andPidEqualTo(pId);
        return diskMapper.selectByExample(diskExample);
    }

    /**
     * 根据id查找Disk对象
     *
     * @param id
     * @return
     */
    @Override
    public Disk findDiskById(Integer id) {
        return diskMapper.selectByPrimaryKey(id);
    }

    /**
     * 新增文件
     *
     * @param disk
     */
    @Override
    public void saveNewFolder(Disk disk) {
        disk.setUpdateTime(new Date());
        diskMapper.insertSelective(disk);
    }

    /**
     * 保存新文件
     *
     * @param disk 文件对象
     * @param in   上传输入流
     */
    @Override
    @Transactional
    public void saveNewFile(Disk disk, InputStream in) {
        //1.保存disk对象的信息到数据库中
        disk.setUpdateTime(new Date());
        String saveName = UUID.randomUUID().toString() + disk.getName().substring(disk.getName().lastIndexOf("."));
        disk.setSaveName(saveName);
        disk.setDownloadCount(0);
        disk.setType("file");
        diskMapper.insertSelective(disk);

        //2.将上传文件输出到服务器硬盘上
        try {
            OutputStream out = new FileOutputStream(new File(parentPath, saveName));
            IOUtils.copy(in, out);
            out.flush();
            out.close();
            in.close();
        } catch (IOException e) {
            throw new ServiceException("上传文件异常");
        }
    }

    /**
     * 更新Disk对象
     *
     * @param disk
     */
    @Override
    public void updateDisk(Disk disk) {
        diskMapper.updateByPrimaryKeySelective(disk);
    }

    /**
     * 递归删除一个文件及其子文件
     *
     * @param disk 原文件对象
     */
    @Override
    @Transactional
    public void recurseDel(Disk disk) {
        //需求,删除disk文件
        //1.如果disk文件下没有子文件,则直接删除
        //2.如果disk文件下有子文件,则把每一个子文件删除
        //3.如果子文件下还有文件,则继续删除

        List<Disk> list = diskMapper.selectByPid(disk.getId());

        if (list != null && list.size() != 0) {
            for (Disk d : list) {
                recurseDel(d);
            }
        }
        diskMapper.deleteByPrimaryKey(disk.getId());
        if(disk.getType().equals("file")) {
            File file = new File(parentPath,disk.getSaveName());
            file.delete();
        }
    }

    /**
     * 文件下载
     * @param out
     * @param disk
     */
    @Override
    @Transactional
    public void downloadFile(OutputStream out, Disk disk) {
        String saveName = disk.getSaveName();
        File file = new File(parentPath,saveName);
        try {
            if(file.exists()) {
                InputStream in = new FileInputStream(file);
                IOUtils.copy(in,out);
                out.flush();
                out.close();
                in.close();
                disk.setDownloadCount(disk.getDownloadCount() + 1);
                updateDisk(disk);
            } else {
                throw new ServiceException("文件已经不存在服务器硬盘上了");
            }
        } catch (IOException e) {
            throw new ServiceException("下载文件出错");
        }
    }
}
