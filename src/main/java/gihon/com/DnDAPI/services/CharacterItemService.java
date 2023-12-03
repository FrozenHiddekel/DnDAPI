package gihon.com.DnDAPI.services;

import gihon.com.DnDAPI.models.CharacterItem;
import gihon.com.DnDAPI.repositories.CharacterItemRepository;
import gihon.com.DnDAPI.util.errors.CharacterItemNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class CharacterItemService {
    private final CharacterItemRepository characterItemRepository;

    public List<CharacterItem> findAll(){
        return characterItemRepository.findAll();
    }

    public CharacterItem findById(int id){
        Optional<CharacterItem> foundItem = characterItemRepository.findById(id);
        return foundItem.orElseThrow(CharacterItemNotFoundException::new);
    }

    @Transactional
    public void save(CharacterItem characterItem){
        characterItemRepository.save(characterItem);
    }

    @Transactional
    public void deleteById(int id){
        Optional<CharacterItem> foundItem = characterItemRepository.findById(id);
        characterItemRepository.delete(foundItem.orElseThrow(CharacterItemNotFoundException::new));
    }
}

