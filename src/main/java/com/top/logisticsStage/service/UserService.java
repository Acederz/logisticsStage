package com.top.logisticsStage.service;

import com.top.logisticsStage.domain.Authority;
import com.top.logisticsStage.domain.Role;
import com.top.logisticsStage.domain.User;
import com.top.logisticsStage.domain.UserAndAuthority;
import com.top.logisticsStage.repository.AuthorityRepository;
import com.top.logisticsStage.repository.UserAndAuthorityRepository;
import com.top.logisticsStage.repository.UserRepository;
import com.top.logisticsStage.service.dto.UserAuthListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAndAuthorityRepository userAndAuthorityRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    public User getUserByAccount(String account) {
        return userRepository.findFirstByAccount(account);
    }

    public List<Role> getRolesByAccount(String account) {
        User user = userRepository.findFirstByAccount(account);
        Set<Role> roleSet = user.getRoles();
        return new ArrayList<>(roleSet);
    }

//    public List<Authority> getAuthorityByAccount(String account) {
//        List<UserAndAuthority> list = userAndAuthorityRepository.findAllByUserAccount(account);
//        List<Authority> authorities = list.stream().map(UserAndAuthority::getAuthority).collect(Collectors.toList());
//        return authorities;
//    }

    public List<UserAuthListDTO> getAuthorityByAccount(String account) {
        List<UserAndAuthority> list = userAndAuthorityRepository.findAllByUserAccount(account);
        List<Authority> authorities = list.stream().map(UserAndAuthority::getAuthority).collect(Collectors.toList());
        List<UserAuthListDTO> dto = new ArrayList<>();
        authorities.forEach(e->{
            if(e.getParent()==null) {
                UserAuthListDTO u = new UserAuthListDTO();
                u.setId(e.getId());
                u.setTitle(e.getDescribe());
                u.setPath(e.getUrl());
                Long pid = e.getId();
                List<Authority> l = authorityRepository.findAuthoritiesByParentIdOrderById(pid);
                if(l!=null&&l.size()>0) {
                    List<UserAuthListDTO> child = new ArrayList<>();
                    l.forEach(c -> {
                        UserAuthListDTO d = new UserAuthListDTO();
                        d.setId(c.getId());
                        d.setTitle(c.getDescribe());
                        d.setPath(c.getUrl());
                        child.add(d);
                    });
                    u.setChild(child);
                }
                dto.add(u);
            }
        });
        return dto.stream().sorted(Comparator.comparing(UserAuthListDTO::getId)).collect(Collectors.toList());
    }
}
