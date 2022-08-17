package cn.oasys.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public class BaseServiceImpl<T> implements BaseService<T> {
	@Autowired
	private Mapper<T> mapper;

	@Override
	public int insert(T entity) {
		return mapper.insertSelective(entity);
	}

	@Override
	public int deleteById(Long id) {
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int update(T entity) {
		return mapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public T getById(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public int selectCount(T t) {
		return mapper.selectCount(t);
	}

	@Override
	public List<T> selectAll() {
		return mapper.selectAll();
	}

	@Override
	public List<T> selectByExample(Object example) {
		return mapper.selectByExample(example);
	}

	@Override
	public PageInfo<T> selectByExample(Object example, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<T>(mapper.selectByExample(example));
	}

	@Override
	public T selectByPrimaryKey(Object entity) {
		return mapper.selectByPrimaryKey(entity);
	}
	
	@Override
	public T selectOne(T entity) {
		return mapper.selectOne(entity);
	}


	@Override
	public T select(Object entity) {
		return mapper.selectByPrimaryKey(entity);
	}
	
}
