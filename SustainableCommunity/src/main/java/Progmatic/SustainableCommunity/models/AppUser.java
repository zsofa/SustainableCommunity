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

    @Column(length = 100, unique = true, nullable = false)
    private String username; //email
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    private LocalDateTime regTime;
    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public AppUser() {

    this.userRole = UserRole.CUSTOMER;
    }

    public AppUser(String username, String email, String password) {
        this();
        this.username = username;
        this.email = email;
        this.password = password;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
        /*
          List<SimpleGrantedAuthority> list = new ArrayList<>();

        for (UserAuth auth : authority.AUTHS) {
            list.add(new SimpleGrantedAuthority(auth.toString()));
        }

        return list;
        * */
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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