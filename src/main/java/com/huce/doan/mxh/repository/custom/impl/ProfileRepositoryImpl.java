package com.huce.doan.mxh.repository.custom.impl;

import com.google.api.client.util.SecurityUtils;
import com.huce.doan.mxh.model.dto.ProfileSearchRequest;
import com.huce.doan.mxh.model.entity.ProfilesEntity;
import com.huce.doan.mxh.model.response.Response;
import com.huce.doan.mxh.repository.custom.ProfilesRepositoryCustom;
import com.huce.doan.mxh.service.ProfilesService;
import lombok.AllArgsConstructor;
import org.springframework.security.web.context.support.SecurityWebApplicationContextUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@AllArgsConstructor
public class ProfileRepositoryImpl implements ProfilesRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ProfilesEntity> search(ProfileSearchRequest request, int page, int pageSize) {

        Map<String, Object> values = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p FROM ProfilesEntity p ");
        sql.append(createWhereQuery(request, values));
        sql.append(createOrderQuery(request));
        Query query = entityManager.createQuery(sql.toString(), ProfilesEntity.class);
        values.forEach(query::setParameter);
        query.setFirstResult(page * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    StringBuilder createWhereQuery(ProfileSearchRequest request, Map<String, Object> values) {
        StringBuilder sql = new StringBuilder();
        sql.append(" WHERE p.status = 1 ");
        if (Objects.nonNull(request.getIdMe())) {
            sql.append(" and p.id <>:idMe ");
            values.put("idMe", request.getIdMe());
        }
        if (Objects.nonNull(request.getName())) {
            sql.append(" and p.name like '%'||:name||'%' ");
            values.put("name", request.getName());
        }
        if (Objects.nonNull(request.getPhoneNumber())) {
            sql.append(" and p.phoneNumber like '%'||:phoneNumber||'%' ");
            values.put("phoneNumber", request.getPhoneNumber());
        }
        if (Objects.nonNull(request.getAddress())) {
            sql.append(" and p.address like '%'||:address||'%' ");
            values.put("address", request.getAddress());
        }
        if (Objects.nonNull(request.getGender())) {
            sql.append(" and p.gender = :gender ");
            values.put("gender", request.getGender());
        }
        return sql;
    }

    StringBuilder createOrderQuery(ProfileSearchRequest request) {
        StringBuilder sql = new StringBuilder(" ");
        if (StringUtils.hasLength(request.getSortBy())) {
            sql.append(" order by p.").append(request.getSortBy().replace(".", " "));
        }
        return sql;
    }

    @Override
    public Long count(ProfileSearchRequest request) {
        try {
            Map<String, Object> values = new HashMap<>();
            String sql = "SELECT COUNT(p) FROM ProfilesEntity p " +
                    this.createWhereQuery(request, values);
            Query query = this.entityManager.createQuery(sql);
            values.forEach(query::setParameter);
            return (Long) query.getSingleResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return 0L;
    }
}
