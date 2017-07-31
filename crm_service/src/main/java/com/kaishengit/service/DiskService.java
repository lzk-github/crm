package com.kaishengit.service;

import com.kaishengit.entity.Disk;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface DiskService {
    List<Disk> findDisksByPId(Integer pId);

    Disk findDiskById(Integer id);

    void saveNewFolder(Disk disk);

    void saveNewFile(Disk disk, InputStream inputStream);

    void updateDisk(Disk disk);

    void recurseDel(Disk disk);

    void downloadFile(OutputStream out, Disk disk);
}
