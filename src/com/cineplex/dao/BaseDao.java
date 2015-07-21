package com.cineplex.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDao {
	/** * Autowired �Զ�װ�� �൱��get() set() */
	@Autowired
	protected SessionFactory sessionFactory;

	/** * gerCurrentSession ���Զ��ر�session��ʹ�õ��ǵ�ǰ��session���� * * @return */
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/** * openSession ��Ҫ�ֶ��ر�session ��˼�Ǵ�һ���µ�session * * @return */
	public Session getNewSession() {
		return sessionFactory.openSession();
	}

	public void flush() {
		getSession().flush();
	}

	public void clear() {
		getSession().clear();
	}

	/** * ���� id ��ѯ��Ϣ * * @param id * @return */
	@SuppressWarnings("rawtypes")
	public Object load(Class c, String id) {
		Session session = getSession();
		return session.get(c, id);
	}

	/** * ��ȡ������Ϣ * * @param c * * @return */

	public List getAllList(Class c) {
		String hql = "from " + c.getName();
		Session session = getSession();
		return session.createQuery(hql).list();

	}

	public boolean ifContain(Class c, String column, String value) {

		String hql = "from " + c.getName() + " where " + column + "='" + value
				+ "' ";
		Session session = getSession();
		List list = session.createQuery(hql).list();
		if (!list.isEmpty()) {
			System.out.println("no empty");
			return true;
		}

		return false;
	}

	public List find(Class c, String column, String value) {

		String hql = "from " + c.getName() + " where " + column + "='" + value
				+ "' ";
		Session session = getSession();
		List list = session.createQuery(hql).list();
		
		return list;
	}

	/** * ��ȡ������ * * @param c * @return */

	public Long getTotalCount(Class c) {
		Session session = getNewSession();
		String hql = "select count(*) from " + c.getName();
		Long count = (Long) session.createQuery(hql).uniqueResult();
		session.close();
		return count != null ? count.longValue() : 0;
	}

	/** * ���� * * @param bean * */
	public void save(Object bean) {
		try {
			Session session = getNewSession();
			session.save(bean);
			session.flush();
			session.clear();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** * ���� * * @param bean * */
	public void update(Object bean) {
		Session session = getNewSession();
		session.update(bean);
		session.flush();
		session.clear();
		session.close();
	}

	/** * ɾ�� * * @param bean * */
	public void delete(Object bean) {

		Session session = getNewSession();
		session.delete(bean);
		session.flush();
		session.clear();
		session.close();
	}

	/** * ����IDɾ�� * * @param c �� * * @param id ID * */
	@SuppressWarnings({ "rawtypes" })
	public void delete(Class c, String id) {
		Session session = getNewSession();
		Object obj = session.get(c, id);
		session.delete(obj);
		flush();
		clear();
	}

	/** * ����ɾ�� * * @param c �� * * @param ids ID ���� * */
	@SuppressWarnings({ "rawtypes" })
	public void delete(Class c, String[] ids) {
		for (String id : ids) {
			Object obj = getSession().get(c, id);
			if (obj != null) {
				getSession().delete(obj);
			}
		}
	}

	public List executeQuery(String hql) {
		System.out.println("��baseDaoImpl���ˣ�");
		Session session = getSession();
		System.out.println("getsession�ˣ���");
		List list = session.createQuery(hql).list();

		return list;

	}

	
	public List doInHibernate(String hql,Class c) {
		return getSession().createQuery(hql).setResultTransformer(new AliasToBeanResultTransformer(c)).list();
	}
	public List dosqlInHibernate(String hql,Class c) {
		return getSession().createSQLQuery(hql).setResultTransformer(new AliasToBeanResultTransformer(c)).list();
	}
	public List dosqlInHibernate2(String hql) {
		return getSession().createSQLQuery(hql).list();
	}

}
