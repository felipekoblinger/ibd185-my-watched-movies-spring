package br.gov.sp.fatec.models;

import br.gov.sp.fatec.views.View;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {
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
    /* Json */
    @JsonView(View.Common.class)
    /* Validations */
    @NotEmpty
    private String uuid = System.currentTimeMillis() + "-" + UUID.randomUUID().toString();

    /* Database */
    @Column(name = "title", nullable = false)
    /* Json */
    @JsonView(View.Common.class)
    /* Validations */
    @NotEmpty
    private String title;

    /* Database */
    @Column(name = "date", nullable = false)
    /* Json */
    @JsonView(View.Common.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    /* Validations */
    @NotNull
    @PastOrPresent
    private LocalDate date;

    /* Database */
    @Column(name = "rating", nullable = false)
    /* Json */
    @JsonView(View.Common.class)
    /* Validations */
    @NotNull
    @Max(5)
    private Integer rating;

    /* Database */
    @Column(name = "imdb_id", nullable = false)
    /* Json */
    @JsonView(View.Common.class)
    /* Validations */
    @NotEmpty
    private String imdbId;

    /* Database */
    @Column(name = "the_movie_database_id", nullable = false)
    /* Json */
    @JsonView(View.Common.class)
    /* Validations */
    @NotEmpty
    private String theMovieDatabaseId;

    /* Database */
    @Column(name = "created_at", nullable = false)
    /* Json */
    @JsonView(View.Common.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    /* Validations */
    @NotNull
    private LocalDateTime createdAt;

    /* Database */
    @Column(name = "updated_at", nullable = false)
    /* Json */
    @JsonView(View.Common.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    /* Validations */
    @NotNull
    private LocalDateTime updatedAt;

    /* Relationship */
    @Column(name = "account_id", nullable = false, insertable = false, updatable = false)
    private Long accountId;

    /* Relationship */
    @ManyToOne
    @JoinColumn(name = "account_id")
    /* Validation */
    @NotNull
    private Account account;
}
