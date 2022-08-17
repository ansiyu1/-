package cn.oasys.dao.notice;

import cn.oasys.pojo.notice.Notepaper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface NotepaperMapper extends Mapper<Notepaper> {
    @Select("SELECT * from aoa_notepaper n where n.notepaper_user_id=#{userId} ORDER BY n.create_time DESC LIMIT 0,5")
    List<Notepaper> notepaperBy(Long userId);

    @Insert("INSERT INTO aoa_notepaper (title,concent,notepaper_user_id,create_time ) VALUES( #{title},#{concent},#{notepaperUserId},#{createTime} )")
    int insertNotepaper(Notepaper npaPer);
}
