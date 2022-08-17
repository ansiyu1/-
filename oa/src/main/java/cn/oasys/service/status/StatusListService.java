package cn.oasys.service.status;

import cn.oasys.pojo.StatusList;
import cn.oasys.service.BaseService;

import java.util.List;

public interface StatusListService extends BaseService<StatusList> {
    List<StatusList> findByStatusModel(String statusModel);
    StatusList selectStatusId(String statusModel,String statusName);
    StatusList selectO(Long statusId);

    List<StatusList> selectName(String name);
}
