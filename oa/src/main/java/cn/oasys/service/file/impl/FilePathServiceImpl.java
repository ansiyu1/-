package cn.oasys.service.file.impl;

import cn.oasys.dao.file.FileListMapper;
import cn.oasys.dao.file.FilePathMapper;
import cn.oasys.pojo.file.FileList;
import cn.oasys.pojo.file.FilePath;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.file.FileListService;
import cn.oasys.service.file.FilePathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilePathServiceImpl extends BaseServiceImpl<FilePath> implements FilePathService {
    @Autowired
    FilePathMapper filePathMapper;
    @Autowired
    FileListMapper fileListMapper;
    @Autowired
    FileListService fileListService;

    @Override
    public FilePath findByPathName(String userName) {
        return filePathMapper.findByPathName(userName);
    }

    @Override
    public List<FilePath> selectPath(Long pathId) {
        return filePathMapper.selectPath(pathId);
    }

    @Override
    public void findAllParent(FilePath filepath, List<FilePath> allParentPaths) {
        if (filepath.getParentId() != 1L) {
            FilePath filepath1 = filePathMapper.selectByPrimaryKey(filepath.getParentId());
            allParentPaths.add(filepath1);
            findAllParent(filepath1,allParentPaths);
        }
    }

    /**
     * 根据文件夹id 批量放入回收战
     * @param pathids
     */
    @Override
    public void trashpath(List<Long> pathids,Long setistrashhaomany,boolean isfirst){
        for (Long pathid : pathids) {
            System.out.println(pathids);
            FilePath filepath = filePathMapper.selectByPrimaryKey(pathid);
            System.out.println("第一个文件夹："+filepath);

            //首先将此文件夹下的文件放入回收战
            List<FileList> files = fileListMapper.findByPath(filepath.getPathId());
            if(!files.isEmpty()){
                //	System.out.println("找到第一个文件夹下的文件不为空！~~~");
                //System.out.println(files);
                List<Long> fileids= new ArrayList<>();
                for (FileList filelist : files) {
                    fileids.add(filelist.getFileId());
                }
                fileListService.trashfile(fileids,2L,null);
            }
//			System.out.println("此文件夹内的文件修改成功");
            //然后将此文件夹下的文件夹放入回收战
            List<FilePath> filepaths = filePathMapper.selectPath(pathid);
            if(!filepaths.isEmpty()){
//				System.out.println("此文件夹下还有文件夹");
                List<Long> pathids2 = new ArrayList<>();
                for (FilePath filePath : filepaths) {
                    pathids2.add(filePath.getPathId());
                }
//				System.out.println("pathids2"+pathids2);
//				System.out.println("接下来尽心递归调用");
                trashpath(pathids2,2L,false);
            }
//			System.out.println("此文件下下再无文件夹");
            if (isfirst) {
                filepath.setParentId(0L);
            }
            filepath.setPathIstrash(setistrashhaomany);
            filePathMapper.deleteByPrimaryKey(filepath.getPathId());
        }
    }

    @Override
    public List<FilePath> findByUserAndContentType(Long userId, Long isTrash) {
        return filePathMapper.findByUserAndContentType(userId,isTrash);
    }
}
