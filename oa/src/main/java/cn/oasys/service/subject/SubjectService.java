package cn.oasys.service.subject;

import cn.oasys.pojo.subject.Subject;
import cn.oasys.service.BaseService;

import java.util.List;

public interface SubjectService extends BaseService<Subject> {
    List<Subject> findParentId(Long parentId);
    List<Subject> findNotParentId(Long parentId);
}
