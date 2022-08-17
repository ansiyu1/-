package cn.oasys.service.noice.impl;

import cn.oasys.dao.notice.CatalogMapper;
import cn.oasys.pojo.notice.Catalog;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.noice.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogServiceImpl extends BaseServiceImpl<Catalog> implements CatalogService {
    @Autowired
    CatalogMapper catalogMapper;

    @Override
    public List<Catalog> selectCatalogUser(Long userId) {
        return catalogMapper.selectCatalogUser(userId);
    }

    @Override
    public long findByCatalogName(String catalogName) {
        return catalogMapper.findByCatalogName(catalogName);
    }

    @Override
    public List<String> selectCatalogName(Long userId) {
            return catalogMapper.selectCatalogName(userId);
    }

    @Override
    public int insertCatalog(String catalogName, Long userId) {
        return catalogMapper.insertCatalog(catalogName,userId);
    }
}
