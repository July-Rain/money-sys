package com.lawschool.util.persistence.interceptor;

import com.lawschool.base.Page;
import com.lawschool.util.persistence.datasources.DynamicDataSource;
import com.lawschool.util.persistence.toolkit.Reflections;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.util.Properties;

/**
 * Mybatis数据库分页插件，拦截StatementHandler的prepare方法
 * @author xupeng
 * @version 2018-10-28
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class PreparePaginationInterceptor extends BaseInterceptor {
    
    private static final long serialVersionUID = 1L;

    @Autowired
    private DynamicDataSource dataSource;

    public PreparePaginationInterceptor() {
        super();
    }

    @Override
    public Object intercept(Invocation ivk) throws Throwable {
        Object o = ivk.getTarget();
        Class c = o.getClass();
        Boolean b = c.isAssignableFrom(RoutingStatementHandler.class);
        if (ivk.getTarget().getClass().isAssignableFrom(RoutingStatementHandler.class)) {

            //初始化参数
            initProperties(null);

            final RoutingStatementHandler statementHandler = (RoutingStatementHandler) ivk.getTarget();
            final BaseStatementHandler delegate = (BaseStatementHandler) Reflections.getFieldValue(statementHandler, DELEGATE);
            final MappedStatement mappedStatement = (MappedStatement) Reflections.getFieldValue(delegate, MAPPED_STATEMENT);

            BoundSql boundSql = delegate.getBoundSql();
            //分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
            Object parameterObject = boundSql.getParameterObject();
            Page<Object> page = convertParameter(parameterObject);
            //page不为空则代表要进行分页，对sql进行修改，添加相应方言的分页查询语句
            if(page != null && page.getPageSize() != -1){
                final Connection connection = dataSource.getConnection();//从动态数据源中获得一个新的连接
                final String sql = boundSql.getSql();
                //记录统计(得到记录总数),COUNT SQL
                final int count = SQLHelper.getCount(sql, connection, mappedStatement, parameterObject, boundSql, log);
                page.setCount(count);
                //分页sql的生成，PAGE SQL
                String pagingSql = SQLHelper.generatePageSql(sql, page, DIALECT);
                if (log.isDebugEnabled()) {
                    log.debug("PAGE SQL: " + pagingSql);
                }
                //将分页sql语句反射回BoundSql.
                Reflections.setFieldValue(boundSql, "sql", pagingSql);
            }

            if (boundSql.getSql() == null || "".equals(boundSql.getSql())){
                return null;
            }
        }
        return ivk.proceed();
    }


    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {
        initProperties(properties);
    }
}
