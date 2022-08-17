package cn.oasys.service.attends.impl;

import cn.oasys.dao.attends.AttendsListMapper;
import cn.oasys.pojo.AttendsList;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.attends.AttendsListService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("attendsListService")
public class AttendsListServiceImpl extends BaseServiceImpl<AttendsList> implements AttendsListService {
    @Autowired
    AttendsListMapper attendsListMapper;
    @Override
    public AttendsList CheckInRecord(String date, Long userId) {
        System.out.println(date+"和"+userId);
        return attendsListMapper.CheckInRecord(date, userId);
    }

    @Override
    public Integer attendsCount(String date, long userId) {
        return attendsListMapper.attendsCount(date,userId);
    }

    @Override
    public Long offWorkId(String date, Long userId) {
        return attendsListMapper.offWorkId(date,userId);
    }

    @Override
    public Integer updateTime(Date date, String hourMin, Long statusId, long attId) {
        return attendsListMapper.updateTime(date, hourMin, statusId, attId);
    }

    @Override
    public AttendsList findLastEst(String date, Long userId) {
        System.out.println("Aa");
        System.out.println(attendsListMapper.findLastEst(date, userId));
        System.out.println(attendsListMapper.findLastEst1(userId));
        return attendsListMapper.findLastEst(date, userId);
    }


    @Override
    public PageInfo<AttendsList> selectByExampleList(List<Long> user,String baseKey, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<AttendsList>(attendsListMapper.attendanceList(user,baseKey));
    }

    @Override
    public Integer countOffWork(String month, Long userId) {
        return attendsListMapper.countOffWork(month, userId);
    }

    @Override
    public Integer countToWork(String month, Long userId) {
        return attendsListMapper.countToWork(month, userId);
    }

    @Override
    public Integer countNum(String month, Long statusId, Long userId) {
        return attendsListMapper.countNum(month, statusId, userId);
    }

    @Override
    public Integer countOtherNum(String month, Long statusId, Long userId) {
        return attendsListMapper.countOtherNum(month, statusId, userId);
    }

    @Override
    public PageInfo<AttendsList> findOneMoHu(Long userId, String baseKey, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<AttendsList>(attendsListMapper.findOneMoHu(baseKey,userId));
    }

    @Override
    public int insertSign(AttendsList attendsList) {
        return attendsListMapper.insertSign(attendsList);
    }

}
