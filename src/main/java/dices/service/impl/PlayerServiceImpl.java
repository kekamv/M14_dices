package dices.service.impl;

import dices.model.Player;
import dices.repository.GameRepository;
import dices.repository.PlayerRepository;
import dices.service.IPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
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
    public Optional<Player> findPlayerByUsername(String username) {
        return playerRepository.findByUsername(username);
    }

    @Override
    public List<Player> findAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    //allows Anonymous as default
    public Player createPlayer(String name, String username, String password) {

        Player playerDB = new Player("name", "username", "password");
            if (name == "") {
                playerDB.setName("Anonymous");
            } else {
                playerDB.setName(name);
            }
            playerDB.setUsername(username);

        playerDB.setPassword(passwordEncoder.encode(password));
        playerDB.setEntryDate(LocalDate.now());
        return playerRepository.save(playerDB);
    }

    //update player's name
    @Override
    public Player updatePlayer(Long playerId, String name, String username, String password) {

        Player playerUpdate = findPlayerById(playerId).get();

        if (name == "") { playerUpdate.setName("Anonymous");
        } else playerUpdate.setName(name);

        if (username != "") playerUpdate.setUsername(username);

        if (password != "")
            playerUpdate.setPassword(passwordEncoder.encode(password));

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


        return findAllPlayers()
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
        return findAllPlayers()
                .stream()
                .filter(player -> player.getGames().size() > 0)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        Player::getSuccessRate,
                        TreeMap::new,
                        Collectors.toList()
                ))
                .firstEntry()
                .getValue();
    }

    @Override
    public boolean nameIsDuplicatePost(String name) {
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

    @Override
    public boolean usernameIsDuplicatePost(String username) {
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

    @Override
    public boolean nameIsDuplicatePut(Long playerId, String name) {
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

    @Override
    public boolean usernameIsDuplicatePut(Long playerId, String username) {
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