package com.pl.premierstats.team;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Clase repository que conecta la base de datos con la clase Team
 */
public interface TeamRepository extends JpaRepository<Team, String> {

    /**
     * Borra un equipo por su nombre.
     * <br/>
     * Es una operacón transaccional, para asegurar la integridad de los datos.
     *
     * @param name String
     */
    @Transactional
    void deleteByName(String name);
    /**
     * Encuentra un equipo por su nombre.
     * <br/>
     * Busca a un equipo que concida con el nombre proporcionado.
     * Si no se encuentra la instancia de Optional no contendrá nada.
     *
     * @param teamName String
     * @return <code>Optional&ltTeam&gt</code>
     */
    Optional<Team> findByName(String teamName);

    /**
     * Encuentra un equipo por su nombre ignorando mayusculas y minusculas.
     * <br/>
     * Busca un equipo por su nombre y se considera un match si la cadena proporcionada
     * está contenida dentro del nombre del equipo sin importar si es mayúscula o minúscula.
     *
     * @param teamName String
     * @return <code>Optional&ltTeam&gt</code>
     */
    Optional<Team> findTop1ByNameIgnoreCaseContaining(String teamName);
}
