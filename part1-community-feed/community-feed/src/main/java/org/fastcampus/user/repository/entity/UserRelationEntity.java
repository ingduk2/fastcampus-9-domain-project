package org.fastcampus.user.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.fastcampus.common.repository.TimeBaseEntity;

@Entity
@Table(name = "community_user_relation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@IdClass(UserRelationId.class)
public class UserRelationEntity extends TimeBaseEntity {

    @Id
    private Long followingUserId;

    @Id
    private Long followerUserId;
}
