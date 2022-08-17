package cn.oasys.dao.attends;

import cn.oasys.pojo.AttendsList;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

public interface AttendsListMapper extends Mapper<AttendsList> {
    @Select("SELECT * from aoa_attends_list a WHERE DATE_format(a.attends_time,'%Y-%m-%d') like '%'#{date}'%' and a.attends_user_id=#{userId} ORDER  BY a.attends_time DESC  LIMIT 1")
    AttendsList CheckInRecord(@Param("date") String date, @Param("userId") Long userId);

    @Select("SELECT COUNT(*) from aoa_attends_list a WHERE DATE_format(a.attends_time,'%Y-%m-%d') like '%'#{date}'%' and a.attends_user_id=#{userId} ")
    Integer attendsCount(@Param("date") String date, @Param("userId") Long userId);

    @Select("select a.attends_id from aoa_attends_list a WHERE DATE_format(a.attends_time,'%Y-%m-%d') like '%'#{date}'%' and a.attends_user_id=#{userId} and a.type_id=9")
    Long offWorkId(@Param("date") String date, @Param("userId") Long userId);

    @Update("update Attends a set a.attendsTime=#{date} ,a.attendHmtime=#{hourMin} ,a.statusId=#{statusId} where a.attendsId=#{attId}")
    Integer updateTime(Date date, String hourMin, Long statusId, long attId);

    @Select("SELECT * from aoa_attends_list a WHERE DATE_format(a.attends_time,'%Y-%m-%d') like #{date} and a.attends_user_id=#{userId} ORDER  BY a.attends_time DESC  LIMIT 1")
    AttendsList findLastEst(@Param("date") String date, @Param("userId") long userId);

    @Select("SELECT * from aoa_attends_list a WHERE a.attends_user_id=#{userId} ORDER  BY a.attends_time DESC  LIMIT 1")
    AttendsList findLastEst1(@Param("userId")Long userId);


    List<AttendsList> attendanceList(@Param("user") List<Long> user, @Param("baseKey")String baseKey);

    @Select("SELECT count(*) from aoa_attends_list,aoa_user where DATE_FORMAT(aoa_attends_list.attends_time,'%Y-%m') like \"%${month}%\" AND aoa_attends_list.attends_user_id=aoa_user.user_id and aoa_user.user_id=#{userId} and aoa_attends_list.type_id=9")
    Integer countOffWork(@Param("month") String month,@Param("userId") Long userId);

    @Select("SELECT count(*) from aoa_attends_list,aoa_user where DATE_FORMAT(aoa_attends_list.attends_time,'%Y-%m') like \"%${month}%\" AND aoa_attends_list.attends_user_id=aoa_user.user_id and aoa_user.user_id=#{userId} and aoa_attends_list.type_id=8")
    Integer countToWork(@Param("month") String month,@Param("userId") Long userId);

    @Select("SELECT count(*) from aoa_attends_list,aoa_user where DATE_FORMAT(aoa_attends_list.attends_time,'%Y-%m') like \"%${month}%\" AND aoa_attends_list.status_id=#{statusId} and aoa_attends_list.attends_user_id=aoa_user.user_id and aoa_user.user_id=#{userId}")
    Integer countNum(@Param("month")String month,@Param("statusId")Long statusId,@Param("userId")Long userId);

    @Select("SELECT sum(aoa_attends_list.holiday_days) from aoa_attends_list,aoa_user where DATE_FORMAT(aoa_attends_list.holiday_days,'%Y-%m') like \"%${month}%\" and aoa_attends_list.status_id=#{statusId} and aoa_attends_list.attends_user_id=aoa_user.user_id and aoa_user.user_id=#{userId}")
    Integer countOtherNum(@Param("month")String month,@Param("statusId")Long statusId,@Param("userId")Long userId);

    @Select({"<script>",
            "SELECT *from aoa_attends_list,aoa_user where" ,
            "aoa_attends_list.attends_user_id=aoa_user.user_id",
            "<if test='baseKey!=null'>",
            "AND  (aoa_attends_list.attends_remark like \"%${baseKey}%\" or DATE_format(aoa_attends_list.attends_time,'%Y-%m-%d') like \"%${baseKey}%\"or aoa_user.user_name like \"%${baseKey}%\" or aoa_attends_list.type_id in (select aoa_type_list.type_id from aoa_type_list where aoa_type_list.type_name like \"%${baseKey}%\") or aoa_attends_list.status_id in (select aoa_status_list.status_id from aoa_status_list where aoa_status_list.status_name like \"%${baseKey}%\"))",
            "</if> ",
            "AND  aoa_attends_list.attends_user_id=#{userId}",
            "</script>"})
    List<AttendsList> findOneMoHu(@Param("baseKey")String baseKey,@Param("userId") Long userId);

    int insertSign(AttendsList attendsList);
}
