package factories;

import br.gov.sp.fatec.enums.MovieType;
import br.gov.sp.fatec.models.Movie;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MovieFactory {
    public static Movie validResource() {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setUuid("22d9aa0a-35ed-4d3f-906b-d7313de6864a");
        movie.setTitle("Fight Club");
        movie.setDate(LocalDate.of(2017, 5, 24));
        movie.setRating(5);
        movie.setPosterPath("/anything.jpg");
        movie.setType(MovieType.SUBTITLED);
        movie.setTheMovieDatabaseId("550");
        movie.setUpdatedAt(LocalDateTime.now());
        movie.setCreatedAt(LocalDateTime.now());
        movie.setAccount(AccountFactory.validResource());
        return movie;
    }
}
