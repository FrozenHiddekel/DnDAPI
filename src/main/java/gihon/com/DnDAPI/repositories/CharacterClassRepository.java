package gihon.com.DnDAPI.repositories;

import gihon.com.DnDAPI.models.CharacterClass;
import gihon.com.DnDAPI.models.GameCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterClassRepository extends JpaRepository<CharacterClass, Integer> {


    CharacterClass findByGameCharacterAndCharacterClassName(GameCharacter gameCharacter, String CharacterClassName);

    @Override
    void delete(CharacterClass characterClass);
}
