package com.pl.premierstats.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Clase que conecta la base de datos con la entidad Player
 */
@Repository
public interface PlayerRepository extends JpaRepository<Player, String> {
    /**
     * Busca un jugador por su nombre y lo borra si está presente.
     * @param playerName
     */
    void deleteByName(String playerName);

    /**
     * Busca a un jugador por su nombre
     *
     * @param playerName
     * @return <code>Optional&ltPlayer&gt</code>
     */
    //Optional is used to handle cases where the player might not be found in the database
    //Note: The parameter must exactly match the players name, it will not match if name is not complete
    Optional<Player> findByName(String playerName);

    /**
     * Busca al top 5 de jugadores con más goles y los ordena de forma descendente.
     *
     * @return <code>List&ltPlayer&gt</code>
     */
    List<Player> findTop5ByOrderByGlsDesc();

    /**
     * Busca a un jugador por su nombre ignorando mayúsculas y minuscules.
     * <br/>
     * Regresa un objeto de tipo Optional para evitar errores en runtime,
     * si no se encuentra el jugador este objeto estará vacío.
     *
     * @param name
     * @return <code>Optional&ltPlayer&gt</code>
     */
    Optional<Player> findTop1ByNameContainingIgnoreCase(String name);
}
