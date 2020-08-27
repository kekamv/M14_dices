package dices.service.impl;

import dices.model.Player;
import dices.repository.PlayerRepository;
import dices.service.IPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlayerServiceImpl implements IPlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public List<Player> findAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    //allows null in attribute name for the set up of new players
    public Player createPlayer(Player player) {

        Player playerDB=new Player("name");
        //check name duplicates
        if (!findAllPlayers().stream().map(Player::getName).collect(Collectors.toList()).contains(player.getName())){
            if(player.getName()==null){
                playerDB.setName("Anomymous");
            }else{
                playerDB.setName(player.getName());
            }

            playerDB.setEntryDate(player.getEntryDate());
            return playerRepository.save(playerDB);

           }else{
                throw new IllegalArgumentException("A player already exists with this name");
            }
    }
    //update player's name
    @Override
    public Player updatePlayer(Long playerId, Player player) {

        Optional<Player> playerDB=playerRepository.findById(playerId);
        if (playerDB.isPresent()){
            if (!findAllPlayers().stream().map(Player::getName).collect(Collectors.toList()).contains(player.getName())) {

                Player playerUpdate = playerDB.get();
                if(player.getName()==null){
                    playerUpdate.setName("Anomymous");
                }else{
                    playerUpdate.setName(player.getName());
                }
                return playerRepository.save(playerUpdate);
            }
            else throw new IllegalArgumentException("A player already exists with this name");
        }
        else throw new ResourceNotFoundException("Player with id: "+playerId+" does not exist");
    }

    @Override
    public String findTotalAverageRanking() {
        return "Average success rate: " + playerRepository.findAverageSuccessRate();
    }

}
