package com.kaiqi.osprey.common.mybatis.service;

import com.kaiqi.osprey.common.mybatis.data.SelectRepository;
import com.kaiqi.osprey.common.mybatis.pager.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @param <Dao>
 * @param <Po>
 * @param <Example>
 * @param <Type>    Key字段数据类型(Integer,Long,String等)
 * @author wangs
 * @date 2017/12/09
 */
public abstract class AbstractGetService<Dao extends SelectRepository<Po, Example, Type>, Po, Example, Type>
        implements GetService<Po, Example, Type> {

    @Autowired
    protected Dao dao;

    @Override
    public boolean exists(final Example example) {
        return this.dao.countByExample(example) > 0;
    }

    @Override
    public Po getById(final Type id) {
        return this.dao.selectById(id);
    }

    @Override
    public List<Po> getByExample(final Example example) {
        return this.dao.selectByExample(example);
    }

    @Override
    public List<Po> getAll() {
        return this.dao.selectByExample(null);
    }

    @Override
    public Po getOneByExample(final Example example) {
        return this.dao.selectOneByExample(example);
    }

    @Override
    public List<Po> getIn(final List<Po> records) {
        return this.dao.selectIn(records);
    }

    @Override
    public List<Po> getByPage(final PageInfo pageInfo) {
        return this.getByPage(pageInfo, "", "");
    }

    @Override
    public List<Po> getByPage(final PageInfo pageInfo, final String fieldName, final String keyword) {
        if (StringUtils.isEmpty(fieldName)) {
            return this.getByPage(pageInfo, null);
        }
        return this.getByPage(pageInfo, this.getPageExample(fieldName, keyword));
    }

    @Override
    public List<Po> getByPage(final PageInfo pageInfo, final Example example) {
        pageInfo.setTotals(this.dao.countByPager(pageInfo, example));
        return this.dao.selectByPager(pageInfo, example);
    }

    protected abstract Example getPageExample(String fieldName, String keyword);
}
