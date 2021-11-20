package Progmatic.SustainableCommunity.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Getter @Setter
@Entity
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 100, unique = true)
    private String username; //email
    private String email;
    private String password;

    @CreationTimestamp
    private LocalDateTime regTime;
    private String firstName;
    private String lastName;

    @Enumerated
    private UserRole userRole;

    public AppUser() {}

    public AppUser(Long userId, String username, String email, String password, LocalDateTime regTime, String firstName, String lastName, UserRole userRole) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.regTime = regTime;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRole = userRole;
    }

        public AppUser(String username, String email, String password, UserRole userRole) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}