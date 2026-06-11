package dasturlash.uz.config;

import dasturlash.uz.entities.ProfileRoleEntity;
import dasturlash.uz.enums.Role;
import dasturlash.uz.enums.Status;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private Integer id;
    private String username;
    private String password;
    private Status status;
    private List<SimpleGrantedAuthority> roles;

    public CustomUserDetails(Integer id, String username, String password, Status status, List<ProfileRoleEntity> roleList) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.status = status;
        List<SimpleGrantedAuthority> roles = new LinkedList<>();
        roleList.forEach(role -> roles.add(new SimpleGrantedAuthority(role.getRoles().name())));
        this.roles = roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<SimpleGrantedAuthority> list = new ArrayList<>();
//        list.add(new SimpleGrantedAuthority(Role.USER.name()));
        return roles;
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Integer getId(){
        return id;
    }
}
