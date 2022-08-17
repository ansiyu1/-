package cn.oasys.service.noice;

import cn.oasys.pojo.notice.ReceiverNote;
import cn.oasys.service.BaseService;

public interface ReceiverNoteService extends BaseService<ReceiverNote> {
    ReceiverNote findUserId(Long noteId, Long realUserId);
}
