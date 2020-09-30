package dices.service.impl;

import dices.model.Player;
import dices.repository.PlayerRepository;
import dices.service.IPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PlayerServiceImpl implements IPlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Optional<Player> findPlayerById(Long playerId) {
        return playerRepository.findById(playerId);
    }

    @Override
    public List<Player> findAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    //allows null in attribute name for the set up of new players
    public Player createPlayer(Player player) {

        Player playerDB=new Player("name", "username", "password");

                if(player.getName()==null){
                    playerDB.setName("Anomymous");
                }else{
                    playerDB.setName(player.getName());
                }
                playerDB.setUsername(player.getUsername());
                playerDB.setPassword(passwordEncoder.encode(player.getPassword()));
                playerDB.setEntryDate(player.getEntryDate());
                return playerRepository.save(playerDB);

    }


    //update player's name
    @Override
    public Player updatePlayer(Long playerId, Player player) {

        Optional<Player> playerDB=playerRepository.findById(playerId);

        Player playerUpdate = playerDB.get();

        if(player.getName()==null){
            playerUpdate.setName("Anomymous");
        }else playerUpdate.setName(player.getName());

        if(player.getUsername()!=null) playerUpdate.setUsername(player.getUsername());
        if(player.getPassword()!= null)
            playerUpdate.setPassword(passwordEncoder.encode(player.getPassword()));
        return playerRepository.save(playerUpdate);
    }

    @Override
    public String findTotalAverageRanking() {
        return "Average success rate: " + playerRepository.findAverageSuccessRate();
    }

}
