package cn.oasys.service.type.impl;

import cn.oasys.dao.type.TypeListMapper;
import cn.oasys.pojo.TypeList;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.type.TypeListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("typeListService")
public class TypeListServiceImpl extends BaseServiceImpl<TypeList> implements TypeListService {
    @Autowired
    TypeListMapper typeListMapper;
    @Override
    public List<TypeList> findByTypeModel(String typeModel) {
        return typeListMapper.findByTypeModel(typeModel);
    }

    @Override
    public String findName(Long typeId) {
        return typeListMapper.findName(typeId);
    }

    @Override
    public TypeList select(Long typeId) {
        return typeListMapper.selectId(typeId);
    }

    @Override
    public TypeList findByTypeModelAndTypeName(String typeModel, String val) {
        System.out.println(typeModel);
        return typeListMapper.findByTypeModelAndTypeName(typeModel,val);
    }

    @Override
    public List<TypeList> selectAuthor(String name) {
        return typeListMapper.selectAuthor(name);
    }
}
