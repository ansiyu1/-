package cn.oasys.service.type;

import cn.oasys.pojo.TypeList;
import cn.oasys.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TypeListService extends BaseService<TypeList> {
    List<TypeList> findByTypeModel(String typeModel);
    String findName(Long typeId);
    TypeList select(Long typeId);
    TypeList findByTypeModelAndTypeName(String typeModel, String val);

    List<TypeList> selectAuthor(String name);
}
