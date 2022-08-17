package cn.oasys.service.noice;

import cn.oasys.pojo.notice.NoticeList;
import cn.oasys.service.BaseService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface NoticeListService extends BaseService<NoticeList> {
    List<Map<String,Object>> myNotice(Long userId);
}
