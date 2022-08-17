package cn.oasys.dao.notice;

import cn.oasys.pojo.notice.Catalog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CatalogMapper extends Mapper<Catalog> {
    List<Catalog> selectCatalogUser(@Param("userId") Long userId);

    @Select("select aoa_catalog.catalog_id from aoa_catalog where aoa_catalog.catalog_name=#{catalogName}")
    long findByCatalogName(@Param("catalogName") String catalogName);


    List<String> selectCatalogName(@Param("userId")Long userId);

    int insertCatalog(@Param("catalogName")String catalogName, @Param("userId")Long userId);
}
