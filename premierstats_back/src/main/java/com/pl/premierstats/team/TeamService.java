package com.pl.premierstats.team;

import com.pl.premierstats.compare.TeamNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Capa de servicio para la entidad Team
 */
@Component
public class TeamService {
    private final TeamRepository teamRepository;

    /**
     * Constructor inyecta automáticamente la dependencia TeamRepository.
     *
     * @param teamRepository Instancia de la clase TeamRepository
     */
    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    /**
     * Obtiene la lista completa de equipos.
     *
     * @return <code>List&ltTeam&gt</code>
     */
    public List<Team> getTeams(){
        return teamRepository.findAll();
    }

    /**
     * Busca equipos por su nombre.
     * <br/>
     * Encuentra los equipos cuyo nombre contenga la cadena proporcionada
     * ignorando la capitalización.
     *
     * @param teamName String
     * @return <code>List&ltTeam&gt</code>
     */
    public List<Team> getTeamByName(String teamName){
        return teamRepository.findAll().stream()
                .filter(team -> team.getName().toLowerCase().contains(teamName.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Guarda un equipo en la base de datos.
     *
     * @param team Instancia de la clase <code>Team</code>
     * @return <code>Team</code>
     */
    public Team addTeam(Team team){
        teamRepository.save(team);
        return team;
    }

    /**
     * Actualiza un equipo.
     * <br/>
     * Recibe un objeto de tipo Team, se busca una coincidencia
     * con el nombre del equipo proporcionado y si se encuentra
     * un equipo con el mismo nombre los datos se actualizan.
     * De lo contrario se regresa null.
     *
     * @param team Instancia de la clase <code>Team</code>
     * @return <code>Team</code>
     */
    public Team updateTeam(Team team){

        //Get Team from repository, using optional to handle not found case
        Optional<Team> existingTeam = teamRepository.findByName(team.getName());
        if(existingTeam.isPresent()){
            Team updatedTeam = existingTeam.get();
            updatedTeam.setName(team.getName());
            updatedTeam.setAge(team.getAge());
            updatedTeam.setGls(team.getGls());
            updatedTeam.setAst(team.getAst());
            teamRepository.save(updatedTeam);
            return updatedTeam;
        }

        return null;
    }

    /**
     * Elimina a un equipo buscando por nombre.
     * <br/>
     * Si el equipo no es encontrado se lanza una TeamNotFoundException que es
     * manejada en la capa controller para mostrar un mensaje not found.
     * Si el nombre coincide el equipo se elimina.
     *
     * @param teamName String
     * @throws TeamNotFoundException Si el nombre proporcionado no coincide con ningún equipo.
     */
    @Transactional
    public void removeTeam(String teamName) throws TeamNotFoundException {
        Team team = teamRepository.findByName(teamName).orElseThrow(() -> new TeamNotFoundException(teamName));

        teamRepository.delete(team);
    }
}
