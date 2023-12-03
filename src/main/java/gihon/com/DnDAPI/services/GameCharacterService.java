package gihon.com.DnDAPI.services;

import gihon.com.DnDAPI.util.errors.GameCharacterNotFoundException;
import lombok.AllArgsConstructor;
import gihon.com.DnDAPI.models.GameCharacter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import gihon.com.DnDAPI.repositories.GameCharacterRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor

@Service
@Transactional(readOnly = true)
public class GameCharacterService {
    private final GameCharacterRepository gameCharacterRepository;

    public List<GameCharacter> findAll(){
        return gameCharacterRepository.findAll();
    }


    public GameCharacter findById(int id){
        Optional<GameCharacter> foundGameCharacter = gameCharacterRepository.findById(id);
        return foundGameCharacter.orElseThrow(GameCharacterNotFoundException::new);
    }

    @Transactional
    public void save(GameCharacter gameCharacter){
        gameCharacterRepository.save(gameCharacter);
    }


    @Transactional
    public void delete(int id){
        Optional<GameCharacter> foundGameCharacter = gameCharacterRepository.findById(id);
        gameCharacterRepository.delete(foundGameCharacter.orElseThrow(GameCharacterNotFoundException::new));

    }
}
