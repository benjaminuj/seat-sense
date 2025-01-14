package project.seatsence.src.user.domain;

import javax.persistence.*;
import lombok.*;
import project.seatsence.global.entity.BaseEntity;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity
@Table(
        name = "user",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"email", "state"})})
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String birthDate;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserSex sex;

    @Column(nullable = false)
    private Boolean consentToMarketing;

    @Column(nullable = false)
    private Boolean consentToTermsOfUser;

    @Builder
    public User(
            String email,
            String password,
            UserRole role,
            String name,
            String birthDate,
            String nickname,
            UserSex sex,
            Boolean consentToMarketing,
            Boolean consentToTermsOfUser) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.name = name;
        this.birthDate = birthDate;
        this.nickname = nickname;
        this.sex = sex;
        this.consentToMarketing = consentToMarketing;
        this.consentToTermsOfUser = consentToTermsOfUser;
    }
}
