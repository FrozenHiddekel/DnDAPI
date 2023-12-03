package gihon.com.DnDAPI.services;

import gihon.com.DnDAPI.models.CharacterClassAccord;
import gihon.com.DnDAPI.repositories.CharacterClassAccordRepository;
import gihon.com.DnDAPI.util.errors.CharacterClassAccordNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CharacterClassAccordService {

    private final CharacterClassAccordRepository characterClassAccordRepository;

    public CharacterClassAccord findByName(String name){
        Optional<CharacterClassAccord> foundCharacterClassAccord = characterClassAccordRepository.findByName(name);
        return foundCharacterClassAccord.orElseThrow(CharacterClassAccordNotFoundException::new);
    }

    public CharacterClassAccord findById(int id){
        Optional<CharacterClassAccord> foundCharacterClassAccord = characterClassAccordRepository.findById(id);
        return foundCharacterClassAccord.orElseThrow(CharacterClassAccordNotFoundException::new);
    }
}
