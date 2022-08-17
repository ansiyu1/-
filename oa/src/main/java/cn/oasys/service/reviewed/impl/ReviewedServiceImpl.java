package cn.oasys.service.reviewed.impl;

import cn.oasys.dao.reviewed.ReviewedMapper;
import cn.oasys.pojo.Reviewed;
import cn.oasys.pojo.user.User;
import cn.oasys.pojo.user.UserAub;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.reviewed.ReviewedService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("reviewedService")
public class ReviewedServiceImpl extends BaseServiceImpl<Reviewed> implements ReviewedService {
    @Autowired
    ReviewedMapper reviewedMapper;


    @Override
    public PageInfo<UserAub> findByUserIdOrderByStatusId(Long userId,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<UserAub>(reviewedMapper.findByUserIdOrderByStatusId(userId));
    }

    @Override
    public PageInfo<UserAub> findProcessList(Long userId, String userName,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<UserAub>(reviewedMapper.findProcessList(userId,userName));
    }

    @Override
    public PageInfo<UserAub> findByStatusProcessList(Long userId, Long statusId,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<UserAub>(reviewedMapper.findByStatusProcessList(userId,statusId));
    }

    @Override
    public PageInfo<UserAub> findByTypeNameProcessList(Long userId, String val,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<UserAub>(reviewedMapper.findByTypeNameProcessList(userId,val));
    }

    @Override
    public PageInfo<UserAub> findByProcessNameProcessList(Long userId, String val,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<UserAub>(reviewedMapper.findByProcessNameProcessList(userId,val));
    }

    @Override
    public Reviewed findByProIdAndUserId(Long processId, Long userId) {
        return reviewedMapper.findByProIdAndUserId(processId,userId);
    }

    @Override
    public List<Reviewed> findByReviewedTimeNotNullAndProId(Long processId) {
        return reviewedMapper.findByReviewedTimeNotNullAndProId(processId);
    }

    @Override
    public int insertReviewed(Reviewed re) {
        return reviewedMapper.insertReviewed(re);
    }
}
