package cn.oasys.service.director.impl;

import cn.oasys.dao.attach.AttachmentMapper;
import cn.oasys.dao.director.DirectorUserMapper;
import cn.oasys.pojo.director.DirectorUser;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.director.DirectorUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DirectorUserServiceImpl extends BaseServiceImpl<DirectorUser> implements DirectorUserService {
    @Autowired
    DirectorUserMapper directorUserMapper;

    @Autowired
    AttachmentMapper attachmentMapper;
    @Override
    public Set<String> findByUser(Long userId) {
        return directorUserMapper.findByUser(userId);
    }

    @Override
    public List<DirectorUser> findByUserAndShareUserNotNullAndHandle(Long userId, Long isHandle) {
        return directorUserMapper.findByUserAndShareUserNotNullAndHandle(userId,isHandle);
    }

    @Override
    public DirectorUser findByDirectorAndUser(Long directorId, Long userId) {
        return directorUserMapper.findByDirectorAndUser(directorId,userId);
    }

    @Override
    public List<Map<String, Object>> allDirector(Long userId, String alph, String outtype, String baseKey) {
        return directorUserMapper.allDirector(userId, alph, outtype, baseKey);
    }
}
