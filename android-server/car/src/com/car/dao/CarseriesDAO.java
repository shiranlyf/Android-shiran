package com.car.dao;

import com.car.entity.Carseries;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * Carseries entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.car.entity.Carseries
 * @author MyEclipse Persistence Tools
 */

public class CarseriesDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(CarseriesDAO.class);
	// property constants
	public static final String ID = "id";
	public static final String DEALER_PRICE = "dealerPrice";
	public static final String NAME = "name";
	public static final String ALIAS_IMG = "aliasImg";
	public static final String ALIAS_NAME = "aliasName";

	public void save(Carseries transientInstance) {
		log.debug("saving Carseries instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Carseries persistentInstance) {
		log.debug("deleting Carseries instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Carseries findById(java.lang.Integer id) {
		log.debug("getting Carseries instance with id: " + id);
		try {
			Carseries instance = (Carseries) getSession().get(
					"com.car.entity.Carseries", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Carseries instance) {
		log.debug("finding Carseries instance by example");
		try {
			List results = getSession().createCriteria(
					"com.car.entity.Carseries").add(Example.create(instance))
					.list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Carseries instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Carseries as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findById(Object id) {
		return findByProperty(ID, id);
	}

	public List findByDealerPrice(Object dealerPrice) {
		return findByProperty(DEALER_PRICE, dealerPrice);
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findByAliasImg(Object aliasImg) {
		return findByProperty(ALIAS_IMG, aliasImg);
	}

	public List findByAliasName(Object aliasName) {
		return findByProperty(ALIAS_NAME, aliasName);
	}

	public List findAll() {
		log.debug("finding all Carseries instances");
		try {
			String queryString = "from Carseries";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Carseries merge(Carseries detachedInstance) {
		log.debug("merging Carseries instance");
		try {
			Carseries result = (Carseries) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Carseries instance) {
		log.debug("attaching dirty Carseries instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Carseries instance) {
		log.debug("attaching clean Carseries instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}