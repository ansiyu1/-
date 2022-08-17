package cn.oasys.service.noice;

import cn.oasys.pojo.notice.Catalog;
import cn.oasys.service.BaseService;

import java.util.List;

public interface CatalogService extends BaseService<Catalog> {
    List<Catalog> selectCatalogUser(Long userId);

    long findByCatalogName(String catalogName);

    List<String> selectCatalogName(Long userId);

    int insertCatalog(String catalogName, Long userId);
}
