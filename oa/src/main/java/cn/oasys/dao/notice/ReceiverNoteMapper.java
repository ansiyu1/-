package cn.oasys.dao.notice;

import cn.oasys.pojo.notice.ReceiverNote;
import lombok.Data;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface ReceiverNoteMapper extends Mapper<ReceiverNote> {
    @Select("select*from aoa_receiver_note nu where nu.note_id=#{noteId} and nu.user_id=#{realUserId}")
    ReceiverNote findUserId(@Param("noteId") Long noteId,@Param("realUserId") Long realUserId);

    @Delete("delete from aoa_receiver_note n where n.note_id=#{noteId}")
    int deleteNoteId(@Param("noteId") long noteId);

}
