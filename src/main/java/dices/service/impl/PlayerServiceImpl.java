package dices.service.impl;

import dices.model.Player;
import dices.repository.GameRepository;
import dices.repository.PlayerRepository;
import dices.service.IPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlayerServiceImpl implements IPlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameRepository gameRepository;

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
    //allows Anonymous as default
    public Player createPlayer(String name, String username, String password) {

        Player playerDB = new Player("name", "username", "password");
        if (nameDuplicatePost(name) == false) {
            if (name == "") {
                playerDB.setName("Anonymous");
            } else {
                playerDB.setName(name);
            }
        }
        if (usernameDuplicatePost(username) == false) {
            playerDB.setUsername(username);
        }
        playerDB.setPassword(passwordEncoder.encode(password));
        playerDB.setEntryDate(LocalDate.now());
        return playerRepository.save(playerDB);
    }

    //update player's name
    @Override
    public Player updatePlayer(Long playerId, String name, String username, String password) {

        //esta parte del optional player irá en el controller, el método comenzará definiendo
        //el playerUpdte como nuevo el playerDB
        Optional<Player> playerDB = playerRepository.findById(playerId);
        Player playerUpdate=new Player(name, username, password);

        if (playerDB.isPresent()) {
            playerUpdate = playerDB.get();
            if (nameDuplicatePut(playerId, name) == false) {
                if (name == "") { playerUpdate.setName("Anonymous");
                } else playerUpdate.setName(name);

                if(usernameDuplicatePut(playerId, username)){
                    if (username != "") playerUpdate.setUsername(username);
                }
                if (password != null)
                    playerUpdate.setPassword(passwordEncoder.encode(password));
            }
        }
        return playerRepository.save(playerUpdate);
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

    public boolean nameDuplicatePost(String name) {
        boolean nameDuplicate;
        if (!findAllPlayers().stream()
                .filter(Objects::nonNull)
                .map(Player::getName).collect(Collectors.toList()).contains(name)) {
            nameDuplicate = false;
        } else {
            nameDuplicate = true;
        }
        return nameDuplicate;
    }

    public boolean usernameDuplicatePost(String username) {
        boolean usernameDuplicate;
        if (!findAllPlayers().stream()
                .filter(Objects::nonNull)
                .map(Player::getUsername).collect(Collectors.toList()).contains(username)) {
            usernameDuplicate = false;
        } else {
            usernameDuplicate = true;
        }
        return usernameDuplicate;
    }

    public boolean nameDuplicatePut(Long playerId, String name) {
        boolean nameDuplicate;
        if (!findAllPlayers().stream()
                .filter(Objects::nonNull)
                .filter(player -> player.getId() != playerId)
                .map(Player::getName)
                .collect(Collectors.toList()).contains(name)) {
            nameDuplicate = false;
        } else {
            nameDuplicate = true;
        }
        return nameDuplicate;
    }

    public boolean usernameDuplicatePut(Long playerId, String username) {
        boolean usernameDuplicate;
        if (!findAllPlayers().stream()
                .filter(Objects::nonNull)
                .filter(player -> player.getId() != playerId)
                .map(Player::getUsername)
                .collect(Collectors.toList()).contains(username)) {
            usernameDuplicate = false;
        } else {
            usernameDuplicate = true;
        }
        return usernameDuplicate;
    }
}