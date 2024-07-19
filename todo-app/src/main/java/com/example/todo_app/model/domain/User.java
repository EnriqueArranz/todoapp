package com.example.todo_app.model.domain;

import com.example.todo_app.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`Id`")
    private Integer id;
    @Column(name = "`Name`")
    private String name;
    @Column(name = "`Username`")
    private String username;
    @Column(name = "`Password`")
    private String password;
    @Column(name = "`Street`")
    private String street;
    @Column(name = "`City`")
    private String city;
    @Column(name = "`Zipcode`")
    private String zipcode;
    @Column(name = "`Country`")
    private String country;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Todo> todos;
    @Enumerated(EnumType.STRING)
    @Column(name = "`Role`")
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((role.name())));
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
}

