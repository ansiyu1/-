package cn.oasys.service;

import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BaseService<T> {
	int insert(T entity);

	int deleteById(Long id);

	int update(T entity);

	T getById(Integer id);

	T selectByPrimaryKey(Object entity);

	List<T> selectAll();

	List<T> selectByExample(Object example);

	PageInfo<T> selectByExample(Object example, int pageNum, int pageSize);

	int selectCount(T t);

	T selectOne(T entity);

	T select(Object entity);

}
