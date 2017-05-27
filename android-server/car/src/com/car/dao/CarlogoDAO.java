package com.car.dao;

import com.car.entity.Carlogo;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * Carlogo entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.car.entity.Carlogo
 * @author MyEclipse Persistence Tools
 */

public class CarlogoDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(CarlogoDAO.class);
	// property constants
	public static final String NAMESPELL = "namespell";
	public static final String NAME = "name";
	public static final String CID = "cid";
	public static final String CARIMG = "carimg";

	public void save(Carlogo transientInstance) {
		log.debug("saving Carlogo instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Carlogo persistentInstance) {
		log.debug("deleting Carlogo instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Carlogo findById(java.lang.Integer id) {
		log.debug("getting Carlogo instance with id: " + id);
		try {
			Carlogo instance = (Carlogo) getSession().get(
					"com.car.entity.Carlogo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Carlogo instance) {
		log.debug("finding Carlogo instance by example");
		try {
			List results = getSession()
					.createCriteria("com.car.entity.Carlogo").add(
							Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Carlogo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Carlogo as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByNamespell(Object namespell) {
		return findByProperty(NAMESPELL, namespell);
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findByCid(Object cid) {
		return findByProperty(CID, cid);
	}

	public List findByCarimg(Object carimg) {
		return findByProperty(CARIMG, carimg);
	}

	public List findAll() {
		log.debug("finding all Carlogo instances");
		try {
			String queryString = "from Carlogo";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Carlogo merge(Carlogo detachedInstance) {
		log.debug("merging Carlogo instance");
		try {
			Carlogo result = (Carlogo) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Carlogo instance) {
		log.debug("attaching dirty Carlogo instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Carlogo instance) {
		log.debug("attaching clean Carlogo instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}