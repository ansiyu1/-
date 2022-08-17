package cn.oasys.service.subject.impl;

import cn.oasys.dao.subject.SubjectMapper;
import cn.oasys.pojo.subject.Subject;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.subject.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("subjectService")
public class SubjectServiceImpl extends BaseServiceImpl<Subject> implements SubjectService {
    @Autowired
    SubjectMapper subjectMapper;
    @Override
    public List<Subject> findParentId(Long parentId) {
        return subjectMapper.findParentId(parentId);
    }

    @Override
    public List<Subject> findNotParentId(Long parentId) {
        return subjectMapper.findNotParentId(parentId);
    }
}
