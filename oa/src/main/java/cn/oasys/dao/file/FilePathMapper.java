package cn.oasys.dao.file;

import cn.oasys.pojo.file.FilePath;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface FilePathMapper extends Mapper<FilePath> {
    @Select("SELECT * FROM aoa_file_path WHERE aoa_file_path.path_name=#{userName}")
    FilePath findByPathName(@Param("userName") String userName);

    List<FilePath> selectPath(@Param("pathId")Long pathId);

    @Select("SELECT * FROM aoa_file_path WHERE aoa_file_path.path_id=#{pathId} and aoa_file_path.path_name=#{name}")
    FilePath findByPathNameAndParentId(@Param("name")String name, @Param("pathId")Long pathId);

    @Select("SELECT * FROM aoa_file_path WHERE aoa_file_path.path_id=#{pathId} and aoa_file_path.path_istrash=#{isTrash}")
    List<FilePath> findByParentIdAndPathIsTrash(@Param("pathId")Long pathId, @Param("isTrash") Long isTrash);

    @Insert("INSERT INTO aoa_file_path ( parent_id,path_name,path_istrash,path_user_id ) VALUES( #{parentId},#{pathName},#{pathIstrash},#{pathUserId} )")
    int insertFilePath(FilePath filepath);

    @Select("SELECT * FROM aoa_file_path WHERE aoa_file_path.path_user_id=#{userId} and aoa_file_path.path_istrash=#{isTrash}")
    List<FilePath> findByUserAndContentType(@Param("userId") Long userId,@Param("isTrash") Long isTrash);
}
