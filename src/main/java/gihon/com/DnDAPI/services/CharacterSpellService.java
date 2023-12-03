package gihon.com.DnDAPI.services;


import gihon.com.DnDAPI.models.CharacterSpell;
import gihon.com.DnDAPI.repositories.CharacterSpellRepository;
import gihon.com.DnDAPI.util.errors.CharacterSpellNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor

@Service
@Transactional(readOnly = true)
public class CharacterSpellService {
    private final CharacterSpellRepository characterSpellRepository;

    public List<CharacterSpell> findAll(){
        return characterSpellRepository.findAll();
    }

    public CharacterSpell findById(int id){
        Optional<CharacterSpell> foundCharacterSpell = characterSpellRepository.findById(id);
        return foundCharacterSpell.orElseThrow(CharacterSpellNotFoundException::new);
    }

    @Transactional
    public void deleteById(int id){
        Optional<CharacterSpell> foundCharacterSpell = characterSpellRepository.findById(id);
        characterSpellRepository.delete(foundCharacterSpell.orElseThrow(CharacterSpellNotFoundException::new));
    }
}
