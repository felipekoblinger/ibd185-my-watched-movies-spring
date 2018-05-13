package br.gov.sp.fatec.models;

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
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @SequenceGenerator(name = "accounts_id_seq",
            sequenceName = "accounts_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accounts_id_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "date",  nullable = false)
    private LocalDate date;

    /* Movie Databases IDs */
    @Column(name = "imdb_id", nullable = false)
    private String imdbId;

    @Column(name = "the_movie_database_id", nullable = false)
    private String theMovieDatabaseId;

    /* Timestamps */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /* Relationships */
    @Column(name = "account_id", nullable = false, insertable = false, updatable = false)
    private Long accountId;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
