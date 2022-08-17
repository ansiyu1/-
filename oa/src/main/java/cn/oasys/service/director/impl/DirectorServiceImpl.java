package cn.oasys.service.director.impl;

import cn.oasys.dao.attach.AttachmentMapper;
import cn.oasys.dao.director.DirectorMapper;
import cn.oasys.pojo.director.Director;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.director.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("directorService")
public class DirectorServiceImpl extends BaseServiceImpl<Director> implements DirectorService {
    @Autowired
    DirectorMapper directorMapper;

    @Override
    public int directCount(Long userId) {
        return directorMapper.directCount(userId);
    }

    @Override
    public Director selectDirector(Long did) {
        return directorMapper.selectDirector(did);
    }
}
