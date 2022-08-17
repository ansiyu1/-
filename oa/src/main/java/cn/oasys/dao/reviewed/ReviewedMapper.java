package cn.oasys.dao.reviewed;

import cn.oasys.pojo.Reviewed;
import cn.oasys.pojo.user.User;
import cn.oasys.pojo.user.UserAub;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ReviewedMapper extends Mapper<Reviewed> {
    @Select("select *from aoa_process_list ,aoa_reviewed,aoa_user  where aoa_reviewed.pro_id=aoa_process_list.process_id and aoa_reviewed.user_id=aoa_user.user_id and aoa_reviewed.user_id=#{userId} and aoa_reviewed.del=0 order by aoa_reviewed.status_id")
    //根据审核人查找流程
    List<UserAub> findByUserIdOrderByStatusId(@Param("userId") Long userId);

    //根据申请人和审核人查找流程
    @Select("select *from aoa_process_list ,aoa_reviewed,aoa_user  where aoa_reviewed.pro_id=aoa_process_list.process_id and aoa_reviewed.user_id=aoa_user.user_id and aoa_reviewed.user_id=#{userId} and aoa_user.user_name=#{userName} and aoa_reviewed.del=0 order by aoa_reviewed.status_id")
    List<UserAub> findProcessList(@Param("userId") Long userId, @Param("userName") String userName);

    //根据状态和审核人查找流程
    @Select("select *from aoa_process_list ,aoa_reviewed,aoa_user  where aoa_reviewed.pro_id=aoa_process_list.process_id and aoa_reviewed.user_id=aoa_user.user_id and aoa_reviewed.user_id=#{userId} and aoa_process_list.status_id=#{statusId} and aoa_reviewed.del=0 order by aoa_reviewed.status_id")
    List<UserAub> findByStatusProcessList(@Param("userId")Long userId, @Param("statusId")Long statusId);

    //根据类型名和审核人查找流程
    @Select("select *from aoa_process_list ,aoa_reviewed,aoa_user  where aoa_reviewed.pro_id=aoa_process_list.process_id and aoa_reviewed.user_id=aoa_user.user_id and aoa_reviewed.user_id=#{userId} and aoa_process_list.type_name=#{val} and aoa_reviewed.del=0 order by aoa_reviewed.status_id")
    List<UserAub> findByTypeNameProcessList(@Param("userId")Long userId, @Param("val")String val);

    //根据标题和审核人查找流程
    @Select("select *from aoa_process_list ,aoa_reviewed,aoa_user  where aoa_reviewed.pro_id=aoa_process_list.process_id and aoa_reviewed.user_id=aoa_user.user_id and aoa_reviewed.user_id=#{userId} and aoa_process_list.process_name like \"%${val}%\" and aoa_reviewed.del=0 order by aoa_reviewed.status_id")
    List<UserAub> findByProcessNameProcessList(@Param("userId")Long userId, @Param("val")String val);


    Reviewed findByProIdAndUserId(@Param("processId") Long processId,@Param("userId") Long userId);

    List<Reviewed> findByReviewedTimeNotNullAndProId(Long processId);

    int insertReviewed(Reviewed re);
}
