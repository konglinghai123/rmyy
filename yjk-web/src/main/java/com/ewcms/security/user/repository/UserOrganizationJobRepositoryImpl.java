package com.ewcms.security.user.repository;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class UserOrganizationJobRepositoryImpl {

    @PersistenceContext
    private EntityManager em;


    @SuppressWarnings("unchecked")
	public List<Long> findUsers(Set<Long> organizationIds, Set<Long> departmentAttributeIds, Set<Long> prefessionIds, Set<Long> technicalTitleIds, Set<Long> appointmentIds){
        boolean hasOrganizationIds = organizationIds.size() > 0;
        boolean hasDepartmentAttributeIds = departmentAttributeIds.size() > 0;
        boolean hasPrefessionIds = prefessionIds.size() > 0;
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

        if (hasPrefessionIds) {
            hql.append(" and ");
            hql.append(" (o.user.profession.id in (:prefessionIds)) ");
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

        if (hasPrefessionIds) {
            q.setParameter("prefessionIds", prefessionIds);
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
