package com.example.demojpacache.Listener;

import com.example.demojpacache.Entity.User;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
public class AuditTrailListener {


    /*
    * Trước khi lưu một entity mới – @PrePersist
        Sau khi lưu một entity mới – @PostPersist
        Trước khi một entity bị xoá – @PreRemove
        Sau khi một entity đã bị xoá – @PostRemove
        Trước khi cập nhật một entity – @PreUpdate
        Sau khi entity đã được cập nhật – @PostUpdate
        Sau khi một entity đã được tải – @PostLoad
    * */
    @PrePersist
    @PreUpdate
    @PreRemove
    private void beforeAnyUpdate(User user) {
        if (user.getId() == null) {
            log.info("[USER AUDIT] About to add a user");
        } else {
            log.info("[USER AUDIT] About to update/delete userId: " + user.getId());
        }
    }

    @PostPersist
    @PostUpdate
    @PostRemove
    private void afterAnyUpdate(User user) {
        log.info("[USER AUDIT] add/update/delete complete for userId: " + user.getId());
    }

    @PostLoad
    private void afterLoad(User user) {
        log.info("[USER AUDIT] user loaded from database ID: " + user.getId());
    }
}
