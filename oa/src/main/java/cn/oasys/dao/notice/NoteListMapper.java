package cn.oasys.dao.notice;


import cn.oasys.pojo.notice.NoteList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface NoteListMapper extends Mapper<NoteList> {
    @Select("SELECT n.*,u.* FROM aoa_notice_list AS n LEFT JOIN aoa_notice_user_relation AS u ON n.notice_id=u.relatin_notice_id WHERE u.relatin_user_id=#{userId} ORDER BY n.is_top DESC,u.is_read ASC ,n.modify_time DESC LIMIT 5")
    List<Map<String, Object>> myNotice(@Param("userId") Long userId);

    //四种模糊查找
    @Select("SELECT * FROM  aoa_note_list where (aoa_note_list.status_id in (SELECT aoa_status_list.status_id from aoa_status_list where aoa_status_list.status_name like \"%${baseKey}%\") or DATE_format(aoa_note_list.create_time,'%Y-%m-%d') like \"%${baseKey}%\" or aoa_note_list.type_id in (SELECT aoa_type_list.type_id from aoa_type_list  where aoa_type_list.type_name like \"%${baseKey}%\") or aoa_note_list.title like \"%${baseKey}%\") and aoa_note_list.note_id in (SELECT aoa_receiver_note.note_id from aoa_receiver_note  where aoa_receiver_note.user_id=#{userId})")
    List<NoteList> selectByTitleOrderByCreateTimeDesc(@Param("baseKey") String baseKey, @Param("userId") Long userId);


    //在目录里面进行查询
    @Select("SELECT * FROM  aoa_note_list where (aoa_note_list.status_id in (SELECT aoa_status_list.status_id from aoa_status_list where aoa_status_list.status_name like \"%${baseKey}%\") or DATE_format(aoa_note_list.create_time,'%Y-%m-%d') like \"%${baseKey}%\" or aoa_note_list.type_id in (SELECT aoa_type_list.type_id from aoa_type_list  where aoa_type_list.type_name like \"%${baseKey}%\") or aoa_note_list.title like \"%${baseKey}%\") and aoa_note_list.note_id in (SELECT aoa_receiver_note.note_id from aoa_receiver_note  where aoa_receiver_note.user_id=#{userId}) and aoa_note_list.catalog_id=#{catalogId}")
    List<NoteList> selectByTitleAndCatalogId(@Param("baseKey") String baseKey, @Param("userId") Long userId, @Param("catalogId") Long catalogId);

    //通过目录查找是否已经收藏
    @Select("SELECT * FROM aoa_note_list  where aoa_note_list.is_collected=#{isCollected} and aoa_note_list.catalog_id=#{catalogId} and aoa_note_list.note_id in (SELECT aoa_receiver_note.note_id from aoa_receiver_note where aoa_receiver_note.user_id=#{userId})")
    List<NoteList> selectByIsCollectedAndCatalogIdOrderByCreateTimeDesc(@Param("isCollected") Long isCollected, @Param("catalogId") Long catalogId, @Param("userId") Long userId);

    //单纯查找是否已经收藏
    @Select("SELECT * FROM aoa_note_list where aoa_note_list.is_collected=#{isCollected} and aoa_note_list.note_id in (SELECT aoa_receiver_note.note_id from aoa_receiver_note where aoa_receiver_note.user_id=#{userId})")
    List<NoteList> selectByIsCollectedOrderByCreateTimeDesc(@Param("isCollected") Long isCollected, @Param("userId") Long userId);

    //排序
    @Select("SELECT * FROM aoa_note_list where aoa_note_list.type_id=#{typeId} and aoa_note_list.catalog_id=#{catalogId} and aoa_note_list.note_id in (SELECT aoa_receiver_note.note_id from aoa_receiver_note where aoa_receiver_note.user_id=#{userId}) ORDER BY aoa_note_list.type_id DESC")
    List<NoteList> selectByTypeIdAndCatalogIdOrderByTypeIdDesc(@Param("typeId") Long typeId, @Param("catalogId") Long catalogId, @Param("userId") Long userId);

    @Select("SELECT * FROM aoa_note_list where aoa_note_list.type_id=#{typeId} and aoa_note_list.catalog_id=#{catalogId} and aoa_note_list.note_id in (SELECT aoa_receiver_note.note_id from aoa_receiver_note where aoa_receiver_note.user_id=#{userId}) ORDER BY aoa_note_list.type_id ASC")
    List<NoteList> selectByTypeIdAndCatalogIdOrderByTypeIdAsc(@Param("typeId") Long typeId, @Param("catalogId") Long catalogId, @Param("userId") Long userId);

    @Select("SELECT * FROM aoa_note_list where aoa_note_list.type_id=#{typeId} and aoa_note_list.catalog_id=#{catalogId} and aoa_note_list.note_id in (SELECT aoa_receiver_note.note_id from aoa_receiver_note where aoa_receiver_note.user_id=#{userId}) ORDER BY aoa_note_list.status_id DESC")
    List<NoteList> selectByTypeIdAndCatalogIdOrderByStatusIdDesc(@Param("typeId") Long typeId, @Param("catalogId") Long catalogId, @Param("userId") Long userId);

    @Select("SELECT * FROM aoa_note_list where aoa_note_list.type_id=#{typeId} and aoa_note_list.catalog_id=#{catalogId} and aoa_note_list.note_id in (SELECT aoa_receiver_note.note_id from aoa_receiver_note where aoa_receiver_note.user_id=#{userId}) ORDER BY aoa_note_list.status_id ASC")
    List<NoteList> selectByTypeIdAndCatalogIdOrderByStatusIdAsc(@Param("typeId") Long typeId, @Param("catalogId") Long catalogId, @Param("userId") Long userId);

    @Select("SELECT * FROM aoa_note_list where aoa_note_list.type_id=#{typeId} and aoa_note_list.catalog_id=#{catalogId} and aoa_note_list.note_id in (SELECT aoa_receiver_note.note_id from aoa_receiver_note where aoa_receiver_note.user_id=#{userId}) ORDER BY aoa_note_list.create_time DESC")
    List<NoteList> selectByTypeIdAndCatalogIdOrderByCreateTimeAsc(@Param("typeId") Long typeId, @Param("catalogId") Long catalogId, @Param("userId") Long userId);

    @Select("SELECT * FROM aoa_note_list where aoa_note_list.type_id=#{typeId} and aoa_note_list.note_id in (SELECT aoa_receiver_note.note_id from aoa_receiver_note where aoa_receiver_note.user_id=#{userId}) ORDER BY aoa_note_list.type_id DESC")
    List<NoteList> selectByTypeIdOrderByTypeIdDesc(@Param("typeId") Long typeId, @Param("userId") Long userId);

    @Select("SELECT * FROM aoa_note_list where aoa_note_list.type_id=#{typeId} and aoa_note_list.note_id in (SELECT aoa_receiver_note.note_id from aoa_receiver_note where aoa_receiver_note.user_id=#{userId}) ORDER BY aoa_note_list.type_id ASC")
    List<NoteList> selectByTypeIdOrderByTypeIdAsc(@Param("typeId") Long typeId, @Param("userId") Long userId);

    @Select("SELECT * FROM aoa_note_list where aoa_note_list.type_id=#{typeId} and aoa_note_list.note_id in (SELECT aoa_receiver_note.note_id from aoa_receiver_note where aoa_receiver_note.user_id=#{userId}) ORDER BY aoa_note_list.status_id DESC")
    List<NoteList> selectByTypeIdOrderByStatusIdDesc(@Param("typeId") Long typeId, @Param("userId") Long userId);

    @Select("SELECT * FROM aoa_note_list where aoa_note_list.type_id=#{typeId} and aoa_note_list.note_id in (SELECT aoa_receiver_note.note_id from aoa_receiver_note where aoa_receiver_note.user_id=#{userId}) ORDER BY aoa_note_list.status_id ASC")
    List<NoteList> selectByTypeIdOrderByStatusIdAsc(@Param("typeId") Long typeId, @Param("userId") Long userId);

    @Select("SELECT * FROM aoa_note_list where aoa_note_list.type_id=#{typeId} and aoa_note_list.note_id in (SELECT aoa_receiver_note.note_id from aoa_receiver_note where aoa_receiver_note.user_id=#{userId}) ORDER BY aoa_note_list.create_time DESC")
    List<NoteList> selectByTypeIdOrderByCreateTimeAsc(@Param("typeId") Long typeId, @Param("userId") Long userId);

    @Select("SELECT * FROM aoa_note_list where aoa_note_list.catalog_id=#{catalogId} and aoa_note_list.note_id in (SELECT aoa_receiver_note.note_id from aoa_receiver_note where aoa_receiver_note.user_id=#{userId}) ORDER BY aoa_note_list.type_id DESC")
    List<NoteList> selectByCatalogIdOrderByTypeIdDesc(@Param("catalogId") Long catalogId, @Param("userId") Long userId);

    @Select("SELECT * FROM aoa_note_list where aoa_note_list.catalog_id=#{catalogId} and aoa_note_list.note_id in (SELECT aoa_receiver_note.note_id from aoa_receiver_note where aoa_receiver_note.user_id=#{userId}) ORDER BY aoa_note_list.type_id ASC")
    List<NoteList> selectByCatalogIdOrderByTypeIdAsc(@Param("catalogId") Long catalogId, @Param("userId") Long userId);

    @Select("SELECT * FROM aoa_note_list where aoa_note_list.catalog_id=#{catalogId} and aoa_note_list.note_id in (SELECT aoa_receiver_note.note_id from aoa_receiver_note where aoa_receiver_note.user_id=#{userId}) ORDER BY aoa_note_list.status_id DESC")
    List<NoteList> selectByCatalogIdOrderByStatusIdDesc(@Param("catalogId") Long catalogId, @Param("userId") Long userId);

    @Select("SELECT * FROM aoa_note_list where aoa_note_list.catalog_id=#{catalogId} and aoa_note_list.note_id in (SELECT aoa_receiver_note.note_id from aoa_receiver_note where aoa_receiver_note.user_id=#{userId}) ORDER BY aoa_note_list.status_id ASC")
    List<NoteList> selectByCatalogIdOrderByStatusIdAsc(@Param("catalogId") Long catalogId, @Param("userId") Long userId);

    @Select("SELECT * FROM aoa_note_list where aoa_note_list.catalog_id=#{catalogId} and aoa_note_list.note_id in (SELECT aoa_receiver_note.note_id from aoa_receiver_note where aoa_receiver_note.user_id=#{userId}) ORDER BY aoa_note_list.create_time DESC")
    List<NoteList> selectByCatalogIdOrderByCreateTimeDesc(@Param("catalogId") Long catalogId, @Param("userId") Long userId);

    @Select("SELECT * FROM aoa_note_list where aoa_note_list.catalog_id=#{catalogId} and aoa_note_list.note_id in (SELECT aoa_receiver_note.note_id from aoa_receiver_note where aoa_receiver_note.user_id=#{userId}) ORDER BY aoa_note_list.create_time ASC")
    List<NoteList> selectByCatalogIdOrderByCreateTimeAsc(@Param("catalogId") Long catalogId, @Param("userId") Long userId);

    //查找目录再查找类型
    @Select("SELECT * FROM aoa_note_list where aoa_note_list.type_id=#{typeId} and aoa_note_list.catalog_id=#{catalogId} and aoa_note_list.note_id in (SELECT aoa_receiver_note.note_id from aoa_receiver_note where aoa_receiver_note.user_id=#{userId}) ")
    List<NoteList> selectByTypeIdAndCatalogIdOrderByCreateTimeDesc(@Param("typeId") Long typeId, @Param("catalogId") Long catalogId, @Param("userId") Long userId);

    //直接查找类型
    @Select("SELECT * FROM aoa_note_list where aoa_note_list.type_id=#{typeId} and aoa_note_list.note_id in (SELECT aoa_receiver_note.note_id from aoa_receiver_note where aoa_receiver_note.user_id=#{userId}) ")
    List<NoteList> selectByTypeIdOrderByCreateTimeDesc(@Param("typeId") Long typeId, @Param("userId") Long userId);

    //通过类型降序排序
    @Select("SELECT * FROM aoa_note_list where aoa_note_list.note_id in (SELECT aoa_receiver_note.note_id from aoa_receiver_note where aoa_receiver_note.user_id=#{userId}) ORDER BY aoa_note_list.type_id DESC")
    List<NoteList> selectByUsersOrderByTypeIdDesc(@Param("userId") Long userId);

    //通过类型升序排序
    @Select("SELECT * FROM aoa_note_list where aoa_note_list.note_id in (SELECT aoa_receiver_note.note_id from aoa_receiver_note where aoa_receiver_note.user_id=#{userId}) ORDER BY aoa_note_list.type_id ASC")
    List<NoteList> selectByUsersOrderByTypeIdAsc(@Param("userId") Long userId);

    //通过状态降序排序
    @Select("SELECT * FROM aoa_note_list where aoa_note_list.note_id in (SELECT aoa_receiver_note.note_id from aoa_receiver_note where aoa_receiver_note.user_id=#{userId}) ORDER BY aoa_note_list.status_id DESC")
    List<NoteList> selectByUsersOrderByStatusIdDesc(@Param("userId") Long userId);

    //通过状态升序排序
    @Select("SELECT * FROM aoa_note_list where aoa_note_list.note_id in (SELECT aoa_receiver_note.note_id from aoa_receiver_note where aoa_receiver_note.user_id=#{userId}) ORDER BY aoa_note_list.status_id ASC")
    List<NoteList> selectByUsersOrderByStatusIdAsc(@Param("userId") Long userId);

    //时间升序
    @Select("SELECT * FROM aoa_note_list where aoa_note_list.note_id in (SELECT aoa_receiver_note.note_id from aoa_receiver_note where aoa_receiver_note.user_id=#{userId}) ORDER BY aoa_note_list.create_time DESC")
    List<NoteList> selectByUsersOrderByCreateTimeDesc(@Param("userId") Long userId);

    //时间降序
    @Select("SELECT * FROM aoa_note_list where aoa_note_list.note_id in (SELECT aoa_receiver_note.note_id from aoa_receiver_note where aoa_receiver_note.user_id=#{userId}) ORDER BY aoa_note_list.create_time ASC")
    List<NoteList> selectByUsersOrderByCreateTimeAsc(@Param("userId") Long userId);

    @Select("SELECT * FROM aoa_note_list where aoa_note_list.note_id in (SELECT aoa_receiver_note.note_id from aoa_receiver_note where aoa_receiver_note.user_id=#{userId})")
    List<NoteList> selectByUsers(@Param("userId") Long userId);

    @Update("update aoa_note_list n set n.catalog_id=#{catalogId},n.type_id=#{typeId},n.status_id=#{statusId},n.title=#{title},n.content=#{content} where n.note_id=#{noteId}")
    int updateCollect(@Param("catalogId")Long catalogId, @Param("typeId")Long typeId, @Param("statusId")Long statusId, @Param("title")String title, @Param("content")String content,@Param("noteId") Long noteId);

    @Select("SELECT note_id from aoa_note_list where aoa_note_list.note_id = (SELECT max(note_id) FROM aoa_note_list)")
    Long selectMaxId();
}