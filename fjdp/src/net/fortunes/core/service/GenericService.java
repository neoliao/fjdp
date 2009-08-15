package net.fortunes.core.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.fortunes.core.ListData;
import net.fortunes.core.dao.GenericDao;
import net.fortunes.core.log.annotation.LoggerMethod;
import net.fortunes.util.GenericsUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Component;


/**
 * 一个泛型Service,针对一个Entity提供一系列模板方法．
 * @param <E>　Entity的类型
 * @author Neo
 */
public abstract class GenericService<E> extends BaseService{
	
	@Resource
	private GenericDao<E> defDao;
	
	private Class<E> entityClass;
	
	/**
	 * 子类初始时根据子类的泛型参数决定Entity的类型,这个构造函数不能直接调用
	 */
	protected GenericService(){
		this.entityClass = GenericsUtil.getGenericClass(getClass());
	}
	
	@LoggerMethod(operateName = "新增")
	public E add(E entity){
		return defDao.add(entity);
	}
	
	@LoggerMethod(operateName = "删除")
	public void del(E entity) throws Exception{
		defDao.del(entity);
	}
	
	@LoggerMethod(operateName = "修改")
	public E update(E entity){
		return defDao.update(entity);
	}
	
	public E get(String id){
		return StringUtils.isEmpty(id) ? null : defDao.getById(entityClass,getPk(id));
	}
	
	public List<E> getAll(){
		return defDao.findByCriteria(DetachedCriteria.forClass(this.entityClass));
	}
	
	public ListData<E> getListData(String query,Map<String,String> queryMap,int start,int limit){
		ListData<E> listData;
		//不分页
		if(limit == 0){
			if(StringUtils.isNotEmpty(query) || (queryMap != null && !queryMap.isEmpty())){
				setFilter(query,queryMap);
			}
			listData = getListData();
		//分页
		}else{
			if(StringUtils.isNotEmpty(query) || (queryMap != null && !queryMap.isEmpty())){
				setFilter(query,queryMap);
			}
			listData = getListData(start,limit);
		}
		
		return listData;
	}
	
	/**
	 * override设置filter用来过滤数据
	 * @param query 查询关键字
	 * @param queryMap 查询关键字map
	 */
	protected void setFilter(String query, Map<String, String> queryMap) {
		
	}
	
	/**
	 * override以改变数据集的默认排序方式
	 * @return
	 */
	protected Order getOrder(){
		return Order.desc("id");
	}
	
	/**
	 * 分页查询方法
	 * @param start 数据集起始位置
	 * @param limit 数据集容量
	 */
	public ListData<E> getListData(int start,int limit){
		List<E> list = defDao.findByCriteria(getDefaultCriteria(),start, limit);
		int total = getTotal();
		return new ListData<E>(list,total);
	}
	
	/**
	 * 实体的所有数据
	 */
	public ListData<E> getListData(){
		List<E> list = getAll();
		int total = list.size();
		return new ListData<E>(list,total);
	}
	
	private DetachedCriteria getDefaultCriteria(){
		return DetachedCriteria.forClass(this.entityClass).addOrder(getOrder());
	}
	
	/**
	 * 属性条件查询 例如 age > 30,name = lisa
	 * @param propertyName 属性名
	 * @param operator 操作符
	 * @param value 属性值
	 */
	public List<E> findByProperty(String propertyName,String operator,Object value) {
		String queryString = "from " + this.entityClass.getSimpleName()
				+ " as e where e." + propertyName +" "+ operator +" ? ";
		return defDao.findByQueryString(queryString,value);
	}
	
	/**
	 * 属性相等条件查询 例如 name = lisa
	 * @param propertyName 属性名
	 * @param value 属性值
	 * @return List<E>
	 */
	public List<E> findByProperty(String propertyName, Object value) {
		return findByProperty(propertyName,"=", value);
	}
	
	/**
	 * 属性相等条件查询,得到的是一个唯一值
	 * @param propertyName 属性名
	 * @param value 属性值
	 * @return E
	 */
	public E findUniqueByProperty(String propertyName, Object value) {
		List<E> list = findByProperty(propertyName,value);
		if(list != null && list.size() >=1)
			return list.get(0);
		else
			return null;
	}
	
	public E getRoot(){
		return (E)defDao.findByQueryString(
				"from "+entityClass.getSimpleName()+" as e where e.parent is null").get(0);
	}
	
	public int delAll(){
		return defDao.queryUpdate("delete from "+entityClass.getSimpleName());
	}
	
	public int getTotal() {
		return ((Long) defDao.getHibernateTemplate().iterate(
				"select count(id) from " + entityClass.getSimpleName()).next()).intValue();
	}
	
	
	/**
	 * 转换为主键的值，这种实现会有问题
	 * @param id
	 * @return 实体List
	 */
	private Serializable getPk(String id) {
		try {
			long longPk = Long.valueOf(id);
			return longPk;
		} catch (NumberFormatException e) {
			return id;
		}
	}	

	public Class<E> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	public void setDefDao(GenericDao<E> defDao) {
		this.defDao = defDao;
	}
	
	public GenericDao<E> getDefDao() {
		return defDao;
	}
}
