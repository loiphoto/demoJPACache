package com.example.demojpacache.service.base;

import com.example.demojpacache.Entity.Role;
import com.example.demojpacache.exception.RoleNotFoundException;

public interface RoleService {
    void createRole(Role role);

    Role findById(Long id) throws RoleNotFoundException;

}
