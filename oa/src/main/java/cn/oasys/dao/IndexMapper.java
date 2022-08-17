package cn.oasys.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IndexMapper {
    // 1、上移下移按钮先改变其他的排序值
    //@Query("update SystemMenu menu set menu.sortId=(:sortId) where menu.parentId = :parentId and menu.sortId=(:sortId - :arithNum)")
    int changeSortId(@Param("menuId") Long menuId);

    // 2、上移下移按钮先改变自己的排序值
    //@Query("update SystemMenu menu set menu.sortId=(:sortId -:arithNum) where menu.menuId= :menuId")
    @Modifying
    int changeSortId2(@Param("sortId") Integer sortId, @Param("arithNum") Integer arithNum,
                      @Param("menuId") Long menuId);
}
