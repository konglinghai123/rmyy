package com.ewcms.security.user.repository;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.google.common.collect.Lists;

public class UserOrganizationJobRepositoryImpl {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Long> findDeclareUsers(Set<Long> organizationIds, Set<Long> departmentAttributeIds,
			Set<Long> professionIds, Set<Long> technicalTitleIds, Set<Long> appointmentIds) {
		boolean hasOrganizationIds = organizationIds.size() > 0;
		boolean hasDepartmentAttributeIds = departmentAttributeIds.size() > 0;
		boolean hasprofessionIds = professionIds.size() > 0;
		boolean hasTechnicalTitleIds = technicalTitleIds.size() > 0;
		boolean hasAppointmentIds = appointmentIds.size() > 0;

		StringBuilder hql = new StringBuilder("select o.user.id from UserOrganizationJob o ");
		hql.append(" where o.user.status = 'blocked' and o.user.deleted = false ");

		if (hasOrganizationIds) {
			hql.append(" and ");
			hql.append(" (o.organizationId in (:organizationIds)) ");
		}

		if (hasDepartmentAttributeIds) {
			hql.append(" and ");
			hql.append(" (o.user.departmentAttribute.id in (:departmentAttributeIds)) ");
		}

		if (hasprofessionIds) {
			hql.append(" and ");
			hql.append(" (o.user.profession.id in (:professionIds)) ");
		}
		if (hasTechnicalTitleIds) {
			hql.append(" and ");
			hql.append(" (o.user.technicalTitle.id in (:technicalTitleIds)) ");
		}
		if (hasAppointmentIds) {
			hql.append(" and ");
			hql.append(" (o.user.appointment.id in (:appointmentIds)) ");
		}

		Query q = em.createQuery(hql.toString());

		if (hasOrganizationIds) {
			q.setParameter("organizationIds", organizationIds);
		}

		if (hasDepartmentAttributeIds) {
			q.setParameter("departmentAttributeIds", departmentAttributeIds);
		}

		if (hasprofessionIds) {
			q.setParameter("professionIds", professionIds);
		}
		if (hasTechnicalTitleIds) {
			q.setParameter("technicalTitleIds", technicalTitleIds);
		}
		if (hasAppointmentIds) {
			q.setParameter("appointmentIds", appointmentIds);
		}

		return (List<Long>) q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Long> findReviewUsers(Boolean director, Boolean secondDirector, Boolean pharmacy, Boolean science,
			Boolean antibiosis, Set<Long> organizationIds, Set<Long> departmentAttributeIds, Set<Long> professionIds,
			Set<Long> technicalTitleIds, Set<Long> appointmentIds, List<Long> userIds) {
		boolean hasOrganizationIds = organizationIds.size() > 0;
		boolean hasDepartmentAttributeIds = departmentAttributeIds.size() > 0;
		boolean hasprofessionIds = professionIds.size() > 0;
		boolean hasTechnicalTitleIds = technicalTitleIds.size() > 0;
		boolean hasAppointmentIds = appointmentIds.size() > 0;
		boolean hasUserIds = userIds.size() > 0;

		if (!hasUserIds) return Lists.newArrayList();
		
		StringBuilder hql = new StringBuilder("select o.user.id from UserOrganizationJob o ");
		hql.append(" where o.user.deleted = false ");
		hql.append(" and o.user.id in :userIds ");
		hql.append(" and o.user.director = :director ");
		hql.append(" and o.user.secondDirector = :secondDirector ");
		hql.append(" and o.user.pharmacy = :pharmacy ");
		hql.append(" and o.user.science = :science ");
		hql.append(" and o.user.antibiosis = :antibiosis ");
		

		if (hasOrganizationIds) {
			hql.append(" and ");
			hql.append(" (o.organizationId in (:organizationIds)) ");
		}

		if (hasDepartmentAttributeIds) {
			hql.append(" and ");
			hql.append(" (o.user.departmentAttribute.id in (:departmentAttributeIds)) ");
		}

		if (hasprofessionIds) {
			hql.append(" and ");
			hql.append(" (o.user.profession.id in (:professionIds)) ");
		}
		if (hasTechnicalTitleIds) {
			hql.append(" and ");
			hql.append(" (o.user.technicalTitle.id in (:technicalTitleIds)) ");
		}
		if (hasAppointmentIds) {
			hql.append(" and ");
			hql.append(" (o.user.appointment.id in (:appointmentIds)) ");
		}

		Query q = em.createQuery(hql.toString());

		q.setParameter("userIds", userIds);
		q.setParameter("director", director);
		q.setParameter("secondDirector", secondDirector);
		q.setParameter("pharmacy", pharmacy);
		q.setParameter("science", science);
		q.setParameter("antibiosis", antibiosis);
		
		if (hasOrganizationIds) {
			q.setParameter("organizationIds", organizationIds);
		}

		if (hasDepartmentAttributeIds) {
			q.setParameter("departmentAttributeIds", departmentAttributeIds);
		}

		if (hasprofessionIds) {
			q.setParameter("professionIds", professionIds);
		}
		if (hasTechnicalTitleIds) {
			q.setParameter("technicalTitleIds", technicalTitleIds);
		}
		if (hasAppointmentIds) {
			q.setParameter("appointmentIds", appointmentIds);
		}

		return (List<Long>) q.getResultList();
	}

}
