package cn.oasys.dao.notice;


import cn.oasys.pojo.notice.NoticeList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface NoticeListMapper extends Mapper<NoticeList> {
    @Select("SELECT n.*,u.* FROM aoa_notice_list AS n LEFT JOIN aoa_notice_user_relation AS u ON n.notice_id=u.relatin_notice_id WHERE u.relatin_user_id=#{userId} ORDER BY n.is_top DESC,u.is_read ASC ,n.modify_time DESC LIMIT 5")
    List<Map<String,Object>> myNotice(@Param("userId") Long userId);

}
