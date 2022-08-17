package cn.oasys.dao.notice;

import cn.oasys.pojo.notice.NoticeUserRelation;
import cn.oasys.pojo.notice.ReceiverNote;
import cn.oasys.pojo.user.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface NoticeUserRelationMapper extends Mapper<NoticeUserRelation> {
    @Select("SELECT * FROM aoa_notice_user_relation,aoa_user WHERE aoa_notice_user_relation.relatin_user_id = aoa_user.user_id AND aoa_notice_user_relation.relatin_user_id = #{userId} AND aoa_notice_user_relation.is_read = #{read}")
    List<NoticeUserRelation> unread(@Param("read") Long read, @Param("userId") Long userId);

    ReceiverNote findUserId( @Param("noteId")Long noteId, @Param("realUserId") Long realUserId);
}
