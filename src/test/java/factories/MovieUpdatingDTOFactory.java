package factories;

import br.gov.sp.fatec.dtos.MovieUpdatingDTO;
import br.gov.sp.fatec.enums.MovieType;

import java.time.LocalDate;

public class MovieUpdatingDTOFactory {
    public static MovieUpdatingDTO validResource() {
        MovieUpdatingDTO movieUpdatingDTO = new MovieUpdatingDTO();
        movieUpdatingDTO.setTitle("Fight Club");
        movieUpdatingDTO.setDate(LocalDate.of(2017, 5, 24));
        movieUpdatingDTO.setRating(5);
        movieUpdatingDTO.setType(MovieType.SUBTITLED);
        movieUpdatingDTO.setImdbId("tt0137523");
        movieUpdatingDTO.setTheMovieDatabaseId("550");
        movieUpdatingDTO.setAccount(AccountFactory.validResource());
        return movieUpdatingDTO;
    }
}
