package factories;

import br.gov.sp.fatec.dtos.MovieCreationDTO;
import br.gov.sp.fatec.enums.MovieType;

import java.time.LocalDate;

public class MovieCreationDTOFactory {
    public static MovieCreationDTO validResource() {
        MovieCreationDTO movieCreationDTO = new MovieCreationDTO();
        movieCreationDTO.setTitle("Fight Club");
        movieCreationDTO.setDate(LocalDate.of(2017, 5, 24));
        movieCreationDTO.setRating(5);
        movieCreationDTO.setType(MovieType.SUBTITLED);
        movieCreationDTO.setPosterPath("/anything.jpg");
        movieCreationDTO.setTheMovieDatabaseId("550");

        movieCreationDTO.setOverview("A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \\\"fight clubs\\\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.");
        movieCreationDTO.setGenres("18");
        movieCreationDTO.setReleaseDate(LocalDate.of(1999, 10, 15));

        movieCreationDTO.setAccount(AccountFactory.validResource());
        return movieCreationDTO;
    }
}
