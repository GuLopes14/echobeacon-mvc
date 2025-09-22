package br.com.echobeacon.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Table(name = "echobeacon_user")
public class User extends DefaultOAuth2User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String picture;

    public User(OAuth2User principal) {
        super(
                List.of(new SimpleGrantedAuthority("USER")),
                principal.getAttributes(),
                "name"
        );
        this.name = principal.getAttribute("name");
        this.email = principal.getAttribute("email");
        this.picture = principal.getAttribute("picture") != null ?
                principal.getAttribute("picture").toString() : null;
    }

    public User() {
        super(
                List.of(new SimpleGrantedAuthority("USER")),
                Map.of("name", "unknown"),
                "name"
        );
    }
}
