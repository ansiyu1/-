package cn.oasys.service.details.impl;

import cn.oasys.dao.details.DetailsBursaMapper;
import cn.oasys.pojo.details.DetailsBurse;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.details.DetailsBurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("detailsBurseService")
public class DetailsBurseServiceImpl extends BaseServiceImpl<DetailsBurse> implements DetailsBurseService {
    @Autowired
    DetailsBursaMapper detailsBursaMapper;
    @Override
    public List<DetailsBurse> selectBurs(Long burSeMeNtId) {
        return detailsBursaMapper.selectBurs(burSeMeNtId);
    }
}
