package cn.oasys.service.noice.impl;

import cn.oasys.dao.notice.NoteListMapper;
import cn.oasys.dao.notice.ReceiverNoteMapper;
import cn.oasys.pojo.notice.NoteList;
import cn.oasys.pojo.notice.ReceiverNote;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.noice.NoteListService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
@Service("noteListService")
public class NoteListServiceImpl extends BaseServiceImpl<NoteList> implements NoteListService {
    @Autowired
    NoteListMapper noteListMapper;
    @Autowired
    ReceiverNoteMapper receiverNoteMapper;
    @Override
    public List<Map<String, Object>> myNotice(Long userId) {
        return noteListMapper.myNotice(userId);
    }

    @Override
    public PageInfo<NoteList> sortPage(int page, int size, String baseKey, Long userId, Long isCollected, Long catalogId, Long typeId, Object type, Object status, Object time) {
        PageHelper.startPage(page,size);
        System.out.println("进来了"+baseKey+";目录"+catalogId);
        if(!StringUtils.isEmpty(baseKey)&&StringUtils.isEmpty(catalogId)){
            return new PageInfo<NoteList>(noteListMapper.selectByTitleOrderByCreateTimeDesc(baseKey, userId));
        }
        if(!StringUtils.isEmpty(baseKey)&&!StringUtils.isEmpty(catalogId)){

            return new PageInfo<NoteList>(noteListMapper.selectByTitleAndCatalogId(baseKey, userId, catalogId));
        }//0为降序 1为升序
        if(!StringUtils.isEmpty(isCollected)){

            if(!StringUtils.isEmpty(isCollected)&&!StringUtils.isEmpty(catalogId)){

                return new PageInfo<NoteList>(noteListMapper.selectByIsCollectedAndCatalogIdOrderByCreateTimeDesc(isCollected, catalogId, userId));
            }
            if(!StringUtils.isEmpty(isCollected)&&StringUtils.isEmpty(catalogId)){
                return new PageInfo<NoteList>(noteListMapper.selectByIsCollectedOrderByCreateTimeDesc(isCollected, userId));
            }
        }

        if(!StringUtils.isEmpty(typeId)){
            if(!StringUtils.isEmpty(typeId)&&!StringUtils.isEmpty(catalogId)){
                System.out.println("目录类型");

                //根据目录 然后再根据类型查找
                if(!StringUtils.isEmpty(type)){
                    if(type.toString().equals("0")) return new PageInfo<NoteList>(noteListMapper.selectByTypeIdAndCatalogIdOrderByTypeIdDesc(typeId,catalogId, userId));
                    if(type.toString().equals("1")) return new PageInfo<NoteList>(noteListMapper.selectByTypeIdAndCatalogIdOrderByTypeIdAsc(typeId, catalogId, userId));
                }
                if(!StringUtils.isEmpty(status)){
                    if(status.toString().equals("0")) return new PageInfo<NoteList>(noteListMapper.selectByTypeIdAndCatalogIdOrderByStatusIdDesc(typeId, catalogId, userId));
                    if(status.toString().equals("1")) return new PageInfo<NoteList>(noteListMapper.selectByTypeIdAndCatalogIdOrderByStatusIdAsc(typeId, catalogId, userId));
                }
                if(!StringUtils.isEmpty(time)){

                    if(time.toString().equals("0")) return new PageInfo<NoteList>(noteListMapper.selectByTypeIdAndCatalogIdOrderByCreateTimeDesc(typeId, catalogId, userId));
                    if(time.toString().equals("1")) return new PageInfo<NoteList>(noteListMapper.selectByTypeIdAndCatalogIdOrderByCreateTimeAsc(typeId, catalogId, userId));
                }
                else return new PageInfo<NoteList>(noteListMapper.selectByTypeIdAndCatalogIdOrderByCreateTimeDesc(typeId,catalogId, userId));
            }
            if(!StringUtils.isEmpty(typeId)&&StringUtils.isEmpty(catalogId)){
                System.out.println("单纯类型");
                //为空就直接按照类型查找

                if(!StringUtils.isEmpty(type)){
                    if(type.toString().equals("0")) return new PageInfo<NoteList>(noteListMapper.selectByTypeIdOrderByTypeIdDesc(typeId,userId));
                    if(type.toString().equals("1")) return new PageInfo<NoteList>(noteListMapper.selectByTypeIdOrderByTypeIdAsc(typeId,userId));
                }
                if(!StringUtils.isEmpty(status)){
                    if(status.toString().equals("0")) return new PageInfo<NoteList>(noteListMapper.selectByTypeIdOrderByStatusIdDesc(typeId, userId));
                    if(status.toString().equals("1")) return new PageInfo<NoteList>(noteListMapper.selectByTypeIdOrderByStatusIdAsc(typeId, userId));
                }
                if(!StringUtils.isEmpty(time)){

                    if(time.toString().equals("0")) return new PageInfo<NoteList>(noteListMapper.selectByTypeIdOrderByCreateTimeDesc(typeId, userId));
                    if(time.toString().equals("1")) return new PageInfo<NoteList>(noteListMapper.selectByTypeIdOrderByCreateTimeAsc(typeId, userId));
                }
                else return new PageInfo<NoteList>(noteListMapper.selectByTypeIdOrderByCreateTimeDesc(typeId, userId));
            }}


        if(!StringUtils.isEmpty(catalogId)&&(StringUtils.isEmpty(typeId))&&(StringUtils.isEmpty(isCollected)))
        {//单纯查找目录
            if(!StringUtils.isEmpty(type)){
                if(type.toString().equals("0")) return new PageInfo<NoteList>(noteListMapper.selectByCatalogIdOrderByTypeIdDesc(catalogId, userId));
                if(type.toString().equals("1")) return new PageInfo<NoteList>(noteListMapper.selectByCatalogIdOrderByTypeIdAsc(catalogId, userId));
            }if(!StringUtils.isEmpty(status)){
            if(status.toString().equals("0")) return new PageInfo<NoteList>(noteListMapper.selectByCatalogIdOrderByStatusIdDesc(catalogId, userId));
            if(status.toString().equals("1")) return new PageInfo<NoteList>(noteListMapper.selectByCatalogIdOrderByStatusIdAsc(catalogId, userId));
        }if(!StringUtils.isEmpty(time)){
            if(time.toString().equals("0")) return new PageInfo<NoteList>(noteListMapper.selectByCatalogIdOrderByCreateTimeDesc(catalogId, userId));
            if(time.toString().equals("1")) return new PageInfo<NoteList>(noteListMapper.selectByCatalogIdOrderByCreateTimeAsc(catalogId, userId));
        }
        else	return new PageInfo<NoteList>(noteListMapper.selectByCatalogIdOrderByCreateTimeDesc(catalogId, userId));
        }



        if(StringUtils.isEmpty(isCollected)&&StringUtils.isEmpty(typeId)&&StringUtils.isEmpty(catalogId)){
            if (!StringUtils.isEmpty(type)) {
                if(type.toString().equals("0")){
                    //降序
                    return new PageInfo<NoteList>(noteListMapper.selectByUsersOrderByTypeIdDesc(userId));
                }else{
                    //升序
                    return new PageInfo<NoteList>(noteListMapper.selectByUsersOrderByTypeIdAsc(userId));
                }
            }
            if (!StringUtils.isEmpty(status)) {
                if(status.toString().equals("0")){

                    return new PageInfo<NoteList>(noteListMapper.selectByUsersOrderByStatusIdDesc(userId));
                }else{
                    return new PageInfo<NoteList>(noteListMapper.selectByUsersOrderByStatusIdAsc(userId));
                }
            }
            if (!StringUtils.isEmpty(time)) {
                if(time.toString().equals("0")){
                    System.out.println("时间"+time);
                    return new PageInfo<NoteList>(noteListMapper.selectByUsersOrderByCreateTimeDesc(userId));
                }else{
                    return new PageInfo<NoteList>(noteListMapper.selectByUsersOrderByCreateTimeAsc(userId));
                }
            } }
        if(!StringUtils.isEmpty(userId)){
            return new PageInfo<NoteList>(noteListMapper.selectByUsersOrderByCreateTimeDesc(userId));
        }
        else {
            System.out.println("what");
            // 第几页 以及页里面数据的条数
            return new PageInfo<NoteList>(noteListMapper.selectByUsers(userId));
        }
    }

    @Override
    public int updateNote(Long catalogId, Long typeId, Long statusId, String title, String content, Long noteId) {
        return noteListMapper.updateCollect(catalogId, typeId, statusId, title, content, noteId);
    }


    @Override
    @Transactional
    public int delete(long noteId) {
        receiverNoteMapper.deleteNoteId(noteId);
        noteListMapper.deleteByPrimaryKey(noteId);

        return 0;
    }

    @Override
    public Long selectMaxId() {
        return noteListMapper.selectMaxId();
    }
}
