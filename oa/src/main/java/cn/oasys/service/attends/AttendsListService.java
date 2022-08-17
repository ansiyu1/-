package cn.oasys.service.attends;

import cn.oasys.pojo.AttendsList;
import cn.oasys.pojo.user.User;
import cn.oasys.service.BaseService;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

public interface AttendsListService extends BaseService<AttendsList> {
    AttendsList CheckInRecord(String date,Long userId);
    Integer attendsCount(String date,long userId);
    Long offWorkId(String date,Long userId);
    Integer updateTime(Date date, String hourMin, Long statusId, long attId);
    AttendsList findLastEst(String date,Long userId);
    PageInfo<AttendsList> selectByExampleList(List<Long> user,String baseKey, int pageNum, int pageSize);
    Integer countOffWork(String month, Long userId);
    Integer countToWork(String month,Long userId);
    Integer countNum(String month,Long statusId,Long userId);
    Integer countOtherNum(String month,Long statusId,Long userId);
    PageInfo<AttendsList> findOneMoHu(Long userId,String baseKey, int pageNum, int pageSize);

    int insertSign(AttendsList attendsList);
}
