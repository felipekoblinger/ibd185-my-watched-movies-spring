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
        movieCreationDTO.setAccount(AccountFactory.validResource());
        return movieCreationDTO;
    }
}
