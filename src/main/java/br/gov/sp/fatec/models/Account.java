package br.gov.sp.fatec.models;

import br.gov.sp.fatec.enums.Gender;
import br.gov.sp.fatec.views.View;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    /* Database */
    @Id
    @SequenceGenerator(name = "accounts_id_seq",
            sequenceName = "accounts_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accounts_id_seq")
    @Column(name = "id", nullable = false)
    /* Validations */
    @NotNull
    private Long id;

    /* Database */
    @Column(name = "uuid", nullable = false, unique = true)
    /* Validations */
    @NotEmpty
    private String uuid = System.currentTimeMillis() + "-" + UUID.randomUUID().toString();

    /* Database */
    @Column(name = "username", unique = true, nullable = false, length = 100)
    /* Json */
    @JsonView(View.Common.class)
    /* Validations */
    @NotNull
    @Size(min = 3, max = 100)
    private String username;

    /* Database */
    @Column(name = "email", unique = true, nullable = false)
    /* Json */
    @JsonView(View.Common.class)
    /* Validations */
    @NotEmpty
    @Email
    private String email;

    /* Database */
    @Column(name = "password", nullable = false)
    /* Validations */
    @NotEmpty
    private String password;

    /* Database */
    @Column(name = "name", nullable = false, length = 100)
    /* Json */
    @JsonView(View.Common.class)
    /* Validations */
    @NotNull
    @Size(min = 3, max = 100)
    private String name;

    /* Database */
    @Column(name = "gender", nullable = false)
    /* Json */
    @JsonView(View.Common.class)
    /* Validations */
    @NotNull
    private Gender gender;

    /* Database */
    @Column(name = "birthday", nullable = false)
    /* Json */
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @JsonView(View.Common.class)
    /* Validations */
    @NotNull
    @Past
    private LocalDate birthday;

    /* Database */
    @Column(name = "last_password_reset_date")
    /* Json */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date lastPasswordResetDate;

    /* Relationships */
    @OneToMany(mappedBy = "account")
    private List<Movie> movies;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "account_authorities",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    /* Validations */
    @NotNull
    @Size(min = 1, max = 1)
    private List<Authority> authorities;
}
