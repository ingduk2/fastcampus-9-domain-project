package org.fastcampus.user.repository.jpa;

import org.fastcampus.user.repository.entity.UserRelationEntity;
import org.fastcampus.user.repository.entity.UserRelationIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaUserRelationRepository extends JpaRepository<UserRelationEntity, UserRelationIdEntity> {
    @Query("SELECT u.followingUserId FROM UserRelationEntity u WHERE u.followerUserId = :userId")
    List<Long> findFollowers(Long userId);
}
