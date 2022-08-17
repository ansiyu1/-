package cn.oasys.service.status.impl;

import cn.oasys.dao.StatusListMapper;
import cn.oasys.pojo.StatusList;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.status.StatusListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("statusListService")
public class StatusListServiceImpl extends BaseServiceImpl<StatusList> implements StatusListService {
    @Autowired
    StatusListMapper statusListMapper;
    @Override
    public List<StatusList> findByStatusModel(String statusModel) {
        return statusListMapper.findByStatusModel(statusModel);
    }

    @Override
    public StatusList selectStatusId(String statusModel, String statusName) {
        return statusListMapper.selectStatusId(statusModel, statusName);
    }

    @Override
    public StatusList selectO(Long statusId) {
        return statusListMapper.selectO(statusId);
    }


    @Override
    public List<StatusList> selectName(String name) {
        return statusListMapper.selectName(name);
    }

}
