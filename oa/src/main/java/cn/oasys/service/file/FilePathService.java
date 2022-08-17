package cn.oasys.service.file;

import cn.oasys.pojo.file.FilePath;
import cn.oasys.service.BaseService;

import java.util.List;

public interface FilePathService extends BaseService<FilePath> {
    FilePath findByPathName(String userName);

    List<FilePath> selectPath(Long pathId);

    void findAllParent(FilePath filepath, List<FilePath> allParentPaths);

    void trashpath(List<Long> pathids,Long setistrashhaomany,boolean isfirst);

    List<FilePath> findByUserAndContentType(Long userId, Long isTrash);
}
