package auto.mapping.exercise.service;


import auto.mapping.exercise.model.dto.GameAddDTO;

import java.math.BigDecimal;

public interface GameService {
    void addGame(GameAddDTO gameAddDTO);

    void editGame(Long gameId, BigDecimal price, Double size);

    void deleteGame(long gameId);

    void viewAllGames();

    void detailsGame(String gameTitle);


}
