package dices.service.impl;

import dices.model.Player;
import dices.repository.GameRepository;
import dices.repository.PlayerRepository;
import dices.service.IPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlayerServiceImpl implements IPlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameRepository gameRepository;

    @Override
    public List<Player> findAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    //allows null in attribute name setting up new players
    public Player createPlayer(String name) {

        Player playerDB=new Player("name");
        //check name duplicates
        if (!findAllPlayers().stream()
                .filter(Objects::nonNull)
                .map(Player::getName).collect(Collectors.toList()).contains(name)){
            if(name==""){
                playerDB.setName("Anomymous");
            }else{
                playerDB.setName(name);
            }

            //playerDB.setEntryDate();
            return playerRepository.save(playerDB);

        }else{
            throw new IllegalArgumentException("A player already exists with this name");
        }
    }
    //update player's name
    @Override
    public Player updatePlayer(Long playerId, String name) {

        Optional<Player> playerDB=playerRepository.findById(playerId);
        if (playerDB.isPresent()){
            if (!findAllPlayers().stream()
                    .filter(Objects::nonNull)
                    .filter(player -> player.getId()!=playerId)
                    .map(Player::getName)
                    .collect(Collectors.toList()).contains(name)) {

                Player playerUpdate = playerDB.get();
                if(name==""){
                    playerUpdate.setName("Anomymous");
                }else{
                    playerUpdate.setName(name);
                }
                return playerRepository.save(playerUpdate);
            }
            else throw new IllegalArgumentException("A player already exists with this name");
        }
        else throw new ResourceNotFoundException("Player with id: "+playerId+" does not exist");
    }

    @Override
    public Map<String, String> findTotalAverageRanking() {

        Map<String, String> resultsMap = new HashMap<>();

        if(gameRepository.findAll().size()==0) {
            resultsMap.put("Average success rate:", "0%");

        }else{
            String value = playerRepository.findAverageSuccessRate().toString()+" %";
            resultsMap.put("Average success rate:",value);
        }
        return resultsMap;
    }

    @Override
    public List<Player> findRankingWinner() {

        return playerRepository.findAll()
                .stream().filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        Player::getSuccessRate,
                        TreeMap::new,
                        Collectors.toList()
                ))
                .lastEntry()
                .getValue();
    }

    @Override
    public List<Player> findRankingLoser() {
        return playerRepository.findAll()
                .stream()
                .filter(Objects::nonNull)
                .filter(player -> player.getGames().size() > 0)
                .collect(Collectors.groupingBy(
                        Player::getSuccessRate,
                        TreeMap::new,
                        Collectors.toList()
                ))
                .firstEntry()
                .getValue();
    }

}
