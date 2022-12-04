package com.huce.doan.mxh.repository;

import com.huce.doan.mxh.constains.MessageStatusEnum;
import com.huce.doan.mxh.model.entity.NotificationsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface NotificationsRepository extends JpaRepository<NotificationsEntity, Long> {
    @Query("select n from NotificationsEntity n where n.receiverId=:receiverId and n.status = 1 order by n.sendTime desc")
    List<NotificationsEntity> getListNotSeen(@Param("receiverId") Long receiverId);

    @Query("select n from NotificationsEntity n where n.receiverId=:receiverId order by n.sendTime desc")
    Page<NotificationsEntity> getList(@Param("receiverId") Long receiverId, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update NotificationsEntity m set m.status = 0 where m.id in (:listId)")
    void updateStatus(@Param("listId") List<Long> listId);
}
