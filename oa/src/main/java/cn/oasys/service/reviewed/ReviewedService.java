package cn.oasys.service.reviewed;

import cn.oasys.pojo.Reviewed;
import cn.oasys.pojo.user.User;
import cn.oasys.pojo.user.UserAub;
import cn.oasys.service.BaseService;
import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewedService extends BaseService<Reviewed> {

    PageInfo<UserAub> findByUserIdOrderByStatusId(Long userId,int page,int size);

    PageInfo<UserAub> findProcessList(Long userId, String userName,int page,int size);

    PageInfo<UserAub> findByStatusProcessList(Long userId, Long statusId,int page,int size);

    PageInfo<UserAub> findByTypeNameProcessList(Long userId, String val,int page,int size);

    PageInfo<UserAub> findByProcessNameProcessList(Long userId, String val,int page,int size);

    Reviewed findByProIdAndUserId(Long processId, Long userId);

    List<Reviewed> findByReviewedTimeNotNullAndProId(Long processId);

    int insertReviewed(Reviewed re);
}
