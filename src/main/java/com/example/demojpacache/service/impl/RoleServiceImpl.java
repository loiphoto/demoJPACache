package com.example.demojpacache.service.impl;

import com.example.demojpacache.Entity.Role;
import com.example.demojpacache.exception.RoleNotFoundException;
import com.example.demojpacache.repository.RoleRepository;
import com.example.demojpacache.service.base.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Override
    public void createRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role findById(Long id) throws RoleNotFoundException {
        return roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException("Role not found!"));
    }
}
