package com.pl.premierstats.player;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

@Component
public class PlayerService {
    private final PlayerRepository playerRepository;

    /**
     * Constructor inyecta automáticamente <code>playerRepository</code>
     *
     * @param playerRepository
     */
    //Inject dependency
    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    /**
     * Obtiene todos los jugadores almacenados en la base de datos.
     *
     * @return <code>List&ltPlayer&gt</code>
     */
    public List<Player> getPlayers() {
        return playerRepository.findAll();
    }

    /**
     * Busca jugadores y filtra por el nombre de equipo.
     * <br/>
     * Para que se tome como una coincidencia válida la cadena proporcionada
     * debe coincidir exactamente con el nombre del equipo.
     *
     * @param team String
     * @return <code>List&ltPlayer&gt</code>
     */
    public List<Player> getPlayersByTeam(String team) {
        return playerRepository.findAll().stream()
                .filter(player -> team.equals(player.getTeam())).
                collect(Collectors.toList());
    }

    /**
     * Obtiene jugadores filtrando por nombre.
     * <br/>
     * Busca jugadores y revisa si el nombre del jugador contiene a la cadena proporcionada
     * ignorando mayúsculas y minúsculas.
     *
     * @param searchText String
     * @return <code>List&ltPlayer&gt</code>
     */
    public List<Player> getPlayersByName(String searchText) {
        return playerRepository.findAll().stream()
                .filter(player -> player.getName().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene jugadores filtrando por posición.
     * <br/>
     * Busca jugadores y revisa si la posición del jugador contiene a la cadena proporcionada
     * ignorando mayúsculas y minúsculas.
     * @param position String
     * @return <code>List&ltPlayer&gt</code>
     */
    public List<Player> getPlayersByPosition(String position) {
        return playerRepository.findAll().stream()
                .filter(player->player.getPos().toLowerCase().contains(position.toLowerCase()))
                .collect(Collectors.toList());
    }


    /**
     * Obtiene jugadores filtrando por país.
     * <br/>
     * Busca jugadores y revisa si el país de nacimiento del jugador contiene a la cadena proporcionada
     * ignorando mayúsculas y minúsculas.
     * @param nation String
     * @return <code>List&ltPlayer&gt</code>
     */
    public List<Player> getPlayersByNation(String nation){
        return playerRepository.findAll().stream()
                .filter(player->{
                    if (player.getNation() == null) return false;
                    return player.getNation().toLowerCase().contains(nation.toLowerCase());
                })
                .collect(Collectors.toList());
    }

    /**
     * Busca jugadores filtrando por equipo y posición.
     * <br/>
     * Regresa los jugadores que coinciden con el equipo y la posición proporcionada.
     * La posición y el nombre del equipo deben coincidir exactamente para poder ser
     * considerado como una coincidencia.
     *
     * @param team String
     * @param position String
     * @return <code>List&ltPlayer&gt</code>
     */
    public List<Player> getPlayersByTeamAndPosition(String team, String position) {
        return playerRepository.findAll().stream()
                .filter(player -> team.equals(player.getTeam())
                        && position.equals(player.getPos()))
                .collect(Collectors.toList());
    }

    /**
     * Añade un jugador a la base de datos.
     *
     * @param player Instancia de la clase <code>Player</code>
     * @return <code>Player</code>
     */
    public Player addPlayer(Player player) {
        playerRepository.save(player);
        return player;
    }

    /**
     * Actualiza un jugador presente con los datos proporcionados.
     * <br/>
     * La función extrae el nombre del jugador de los datos proporcionados y lo
     * busca en la base de datos, si el jugador está presente los cambios se guardan.
     * @param updatedPlayer Instancia de la clase <code>Player</code>
     * @return <code>Player</code>
     */
    public Player updatePlayer(Player updatedPlayer) {
        Optional<Player> existingPlayer = playerRepository.findByName(updatedPlayer.getName());

        if (existingPlayer.isPresent()) {
            Player playerToUpdate = existingPlayer.get();
            playerToUpdate.setName(updatedPlayer.getName());
            playerToUpdate.setNation(updatedPlayer.getNation());
            playerToUpdate.setTeam(updatedPlayer.getTeam());
            playerToUpdate.setPos(updatedPlayer.getPos());
            playerToUpdate.setAge(updatedPlayer.getAge());


            playerRepository.save(playerToUpdate);
            return playerToUpdate;
        }

        return null;
    }

    /**
     *
     * Busca a los 5 jugadores con más goles en orden descendente.
     *
     * @return <code>List&ltPlayer&gt</code>
     */
    public List<Player> getTopScorers(){
        return playerRepository.findTop5ByOrderByGlsDesc();
    }

    /**
     * Elimina un jugador buscando por su nombre.
     *
     * @param playerName String
     */
    @Transactional
    public void removePlayer(String playerName) {
        playerRepository.deleteByName(playerName);
    }

    /**
     * Regresa a los jugadores con una <code>playMakerScore</code> más alta.
     * <br/>
     * Esta función asigna pesos a las diferentes estadisticas relacionadas a la creación de juego
     * y en base a estas calcula un valor <code>playMakerScore</code> usado para comparar
     * jugadores en base a sus capacidades para creación de juego.<br/>
     *
     * Una vez calculado este valor se obtienen los 5 jugadores con el valor más alto para esta
     * estadística.
     *
     * @return <code>List&ltPlayer&gt</code>
     */
    public List<Player> getTopPlayMakers(){
        double xAgWeight = 0.3;
        double prgpWeight = 0.4;
        double asstWeight = 0.3;

        PriorityQueue<Player> players = new PriorityQueue<>(new PlayerPlayMakerComparator());

        for(Player player : getPlayers()){

            Double playmakerScore = safe(player.getzXag()) * xAgWeight + safe(player.getzProgressivePasses()) * prgpWeight
                    + safe(player.getzAssists()) * asstWeight;

            player.setPlayMakerScore(playmakerScore);

            players.add(player);

            if(players.size() > 5){
                players.poll();
            }
        }

        return players.stream().toList();
    }

    /**
     * Función auxiliar para evitar operaciones con valores nulos.
     *
     * @param value Double
     * @return <code>double</code>
     */
    private double safe(Double value){
        return value != null ? value : 0.0;
    }
}
