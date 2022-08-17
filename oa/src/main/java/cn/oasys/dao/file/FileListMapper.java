package cn.oasys.dao.file;

import cn.oasys.pojo.file.FileList;
import cn.oasys.pojo.file.FilePath;
import cn.oasys.pojo.user.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface FileListMapper extends Mapper<FileList> {
    @Select("SELECT COUNT(*) FROM aoa_file_list WHERE aoa_file_list.file_user_id =#{userId}")
    int fileCount(@Param("userId") Long userId);


    List<FileList> findByPathAndFileIsTrash(@Param("pathId")Long pathId, @Param("isTrash") Long isTrash);

    FileList findByFileNameAndPath(@Param("name")String name, @Param("pathId")Long pathId);

    int insertFileList(FileList filelist);

    List<FileList> findByPath(@Param("pathId")Long pathId);

    FileList findByFileList(@Param("mcFileId")Long mcFileId);

    List<FileList> findByUserAndContentType(@Param("userId") Long userId, @Param("contentType")String contentType,@Param("isTrash") Long isTrash);

    List<FileList> findDocument(@Param("userId")Long userId);

    List<FileList> selectByFileIsShareAndFileIsTrash(@Param("isShare") Long isShare,@Param("isTrash") Long isTrash);
}
