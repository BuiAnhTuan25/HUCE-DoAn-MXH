//package com.huce.doan.mxh.repository.custom.impl;
//
//import com.huce.doan.mxh.model.dto.FriendResponse;
//import com.huce.doan.mxh.repository.custom.CustomFriendsRepository;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.util.List;
//
//public class CustomFriendsRepositoryImpl implements CustomFriendsRepository{
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Override
//    public List<FriendResponse> getListFriendByMeId(Long meId, Pageable pageable) {
//        return entityManager.createNativeQuery(
//                " select new com.huce.doan.mxh.model.dto.FriendResponse(f.id,f.friendId,f.meId,f.friendCode,f.friendStatus,p.name,p.avatarUrl) " +
//                        " from FriendsEntity f join ProfilesEntity p on f.friendId=p.id where f.meId=:meId")
//                .setParameter("meId", meId)
//                .getResultList();
//    }
//}
