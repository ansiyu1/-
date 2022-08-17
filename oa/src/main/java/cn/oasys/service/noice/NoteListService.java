package cn.oasys.service.noice;

import cn.oasys.pojo.notice.NoteList;
import cn.oasys.service.BaseService;
import com.github.pagehelper.PageInfo;


import java.util.List;
import java.util.Map;

public interface NoteListService extends BaseService<NoteList> {
    List<Map<String,Object>> myNotice(Long userId);
    PageInfo<NoteList> sortPage(int page, int size, String baseKey, Long userId, Long isCollected, Long catalogId, Long typeId, Object type, Object status, Object time);

    int updateNote(Long catalogId, Long typeId, Long statusId, String title, String content, Long noteId);

    int delete(long noteId);

    Long selectMaxId();
}
