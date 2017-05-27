package com.car.dao;

import com.car.entity.Newshead;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * Newshead entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.car.entity.Newshead
 * @author MyEclipse Persistence Tools
 */

public class NewsheadDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(NewsheadDAO.class);
	// property constants
	public static final String NID = "nid";
	public static final String CONVERIMAGE = "converimage";
	public static final String TITLE = "title";
	public static final String TYPE = "type";

	public void save(Newshead transientInstance) {
		log.debug("saving Newshead instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Newshead persistentInstance) {
		log.debug("deleting Newshead instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Newshead findById(java.lang.Integer id) {
		log.debug("getting Newshead instance with id: " + id);
		try {
			Newshead instance = (Newshead) getSession().get(
					"com.car.entity.Newshead", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Newshead instance) {
		log.debug("finding Newshead instance by example");
		try {
			List results = getSession().createCriteria(
					"com.car.entity.Newshead").add(Example.create(instance))
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
		log.debug("finding Newshead instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Newshead as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByNid(Object nid) {
		return findByProperty(NID, nid);
	}

	public List findByConverimage(Object converimage) {
		return findByProperty(CONVERIMAGE, converimage);
	}

	public List findByTitle(Object title) {
		return findByProperty(TITLE, title);
	}

	public List findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List findAll() {
		log.debug("finding all Newshead instances");
		try {
			String queryString = "from Newshead";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Newshead merge(Newshead detachedInstance) {
		log.debug("merging Newshead instance");
		try {
			Newshead result = (Newshead) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Newshead instance) {
		log.debug("attaching dirty Newshead instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Newshead instance) {
		log.debug("attaching clean Newshead instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}