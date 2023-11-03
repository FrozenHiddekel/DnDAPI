package gihon.com.DnDAPI.services;


import gihon.com.DnDAPI.models.CharacterClass;
import gihon.com.DnDAPI.models.GameCharacter;
import gihon.com.DnDAPI.repositories.CharacterClassRepository;
import gihon.com.DnDAPI.util.errors.CharacterClassNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class CharacterClassService {

    private final CharacterClassRepository characterClassRepository;

    public List<CharacterClass> findAll(){

        return characterClassRepository.findAll();
    }

    public CharacterClass findOne(int id){
        System.out.println("sssssssssssssssadadasdasssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
        Optional<CharacterClass> foundCharacterClass = characterClassRepository.findById(id);
        return foundCharacterClass.orElseThrow(CharacterClassNotFoundException::new);
    }

    @Transactional
    public void save(CharacterClass characterClass){
        characterClassRepository.save(characterClass);
    }

    //вероятно функция снизу должна принимать энтити
    @Transactional
    public void delete(int id){
        Optional<CharacterClass> foundCharacterClass = characterClassRepository.findById(id);
        characterClassRepository.delete(foundCharacterClass.orElseThrow(CharacterClassNotFoundException::new));}

}
