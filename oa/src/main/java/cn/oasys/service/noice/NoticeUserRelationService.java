package cn.oasys.service.noice;

import cn.oasys.pojo.notice.NoticeUserRelation;
import cn.oasys.pojo.notice.ReceiverNote;
import cn.oasys.pojo.user.User;
import cn.oasys.service.BaseService;

import java.util.List;

public interface NoticeUserRelationService extends BaseService<NoticeUserRelation> {
    List<NoticeUserRelation> unread(Long read, Long userId);

}
