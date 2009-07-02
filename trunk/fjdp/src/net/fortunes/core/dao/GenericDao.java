package net.fortunes.core.dao;

import java.io.Serializable;
import java.util.List;

import net.fortunes.exception.DeleteForeignConstrainException;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.support.TransactionTemplate;


/**
 * 一个泛型Dao,针对一个Entity提供一系列模板方法．
 * 第一个泛型参数为model类,第二个泛型参数为该model类为主键类型
 * @param <E>　Entity的类型
 * @author Neo
 */
public class GenericDao<E> extends BaseDao {
	
	private TransactionTemplate transactionTemplate;
	
	/**
	 * DetachedCriteria条件查询方法
	 * @param criteria 一个DetachedCriteria对象
	 */
	public List<E> findByCriteria(DetachedCriteria criteria) {
		return getHibernateTemplate().findByCriteria(criteria);
	}
		
	/**
	 * 条件+分页查询方法
	 * @param start 数据集起始位置
	 * @param limit 数据集容量
	 * @param criteria 一个DetachedCriteria对象
	 */
	public List<E> findByCriteria(DetachedCriteria criteria,int start,int limit){
		return getHibernateTemplate().findByCriteria(criteria,start,limit);
	}
	
	
	/**
	 * HQL查询
	 * @param queryString HQL查询字符串
	 * @return List
	 */
	public List findByQueryString(String queryString,Object...objects) {
		return getHibernateTemplate().find(queryString, objects);
	}
	
	/**
	 * HQL查询
	 * @param queryString HQL查询字符串
	 * @return List
	 */
	public List findByQueryString(String queryString) {
		return getHibernateTemplate().find(queryString);
	}
	
	/**
	 * example查询
	 * @param exampleEntity
	 * @return
	 */
	public List<E> findByExample(E exampleEntity) {
		return getHibernateTemplate().findByExample(exampleEntity);
	}

	public E getById(Class<E> clazz,Serializable id) {
		return (E)getHibernateTemplate().get(clazz,id);
	}

	public E loadById(Class<E> clazz,Serializable id) {
		return (E)getHibernateTemplate().load(clazz,id);
	}

	
	public E add(E entity) {
		getHibernateTemplate().save(entity);
		return entity;
	}

	public E update(E entity) {
		getHibernateTemplate().update(entity);
		return entity;
	}
	
	public E saveOrUpdate(E entity) {
		getHibernateTemplate().saveOrUpdate(entity);
		return entity;
	}
	
	public E refresh(E entity){
		getHibernateTemplate().refresh(entity);
		return entity;
	}

	public void del(E entity) throws DeleteForeignConstrainException{
		try {
			getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_EAGER);
			getHibernateTemplate().delete(entity);
		} catch (DataIntegrityViolationException e) {
			throw new DeleteForeignConstrainException();
		}
	}
	
	public int queryUpdate(String queryHql){
		return getHibernateTemplate().bulkUpdate(queryHql);
	}
	
	public int queryUpdate(String queryHql,Object...objects){
		return getHibernateTemplate().bulkUpdate(queryHql, objects);
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}

}
