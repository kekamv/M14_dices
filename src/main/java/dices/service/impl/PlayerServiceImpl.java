package dices.service.impl;

import dices.model.Player;
import dices.repository.PlayerRepository;
import dices.service.IPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<Player> findAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    //allows null in attribute name for the set up of new players
    public Player createPlayer(Player player) {

        Player playerDB=new Player("name", "username", "password");
        /*
        //check name duplicates
        if (!findAllPlayers().stream().map(Player::getName)
                .collect(Collectors.toList()).contains(player.getName())){

            if(!findAllPlayers().stream().map(Player::getUsername)
                    .collect(Collectors.toList()).contains(player.getUsername())){

         */

                if(player.getName()==null){
                    playerDB.setName("Anomymous");
                }else{
                    playerDB.setName(player.getName());
                }
                playerDB.setUsername(player.getUsername());
                playerDB.setPassword(passwordEncoder.encode(player.getPassword()));
                playerDB.setEntryDate(player.getEntryDate());
                return playerRepository.save(playerDB);
                /*
            }
            else throw new IllegalArgumentException("A player already exits with this username");

        }
        else throw new IllegalArgumentException("A player already exists with this name");

                 */
    }


    //update player's name
    @Override
    public Player updatePlayer(Long playerId, Player player) {

        Optional<Player> playerDB=playerRepository.findById(playerId);
        if (playerDB.isPresent()){
            if (!findAllPlayers().stream().map(Player::getName).collect(
                    Collectors.toList()).contains(player.getName())) {

                if(!findAllPlayers().stream().map(Player::getUsername)
                        .collect(Collectors.toList()).contains(player.getUsername())){

                    Player playerUpdate = playerDB.get();
                    if(player.getName()==null){
                        playerUpdate.setName("Anomymous");

                    }else playerUpdate.setName(player.getName());

                    if(player.getUsername()!=null) playerUpdate.setUsername(player.getUsername());
                    if(player.getPassword()!= null)
                        playerUpdate.setPassword(passwordEncoder.encode(player.getPassword()));

                    return playerRepository.save(playerUpdate);
                }
                else throw new IllegalArgumentException("A player already exists with this username");
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
