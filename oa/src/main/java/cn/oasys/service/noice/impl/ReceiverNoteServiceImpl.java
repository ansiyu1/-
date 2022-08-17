package cn.oasys.service.noice.impl;

import cn.oasys.dao.notice.ReceiverNoteMapper;
import cn.oasys.pojo.notice.ReceiverNote;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.noice.ReceiverNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceiverNoteServiceImpl extends BaseServiceImpl<ReceiverNote> implements ReceiverNoteService {
    @Autowired
    ReceiverNoteMapper receiverNoteMapper;
    @Override
    public ReceiverNote findUserId(Long noteId, Long realUserId) {
        return receiverNoteMapper.findUserId(noteId,realUserId);
    }
}
