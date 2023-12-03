package gihon.com.DnDAPI.dao;

import gihon.com.DnDAPI.models.CharacterClass;
import gihon.com.DnDAPI.models.CharacterClassAccord;
import gihon.com.DnDAPI.models.GameCharacter;
import gihon.com.DnDAPI.repositories.CharacterClassRepository;
import gihon.com.DnDAPI.repositories.GameCharacterRepository;
import gihon.com.DnDAPI.services.CharacterClassAccordService;
import gihon.com.DnDAPI.services.GameCharacterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
@AllArgsConstructor
public class GameCharacterDAO {
    private final GameCharacterService gameCharacterService;
    private final CharacterClassAccordService characterClassAccordService;

    private final CharacterClassRepository characterClassRepository;
    private final GameCharacterRepository gameCharacterRepository;

    public void addLevel(int characterId, String name){
        CharacterClassAccord characterClassAccord = characterClassAccordService.findByName(name);
        GameCharacter gameCharacter = gameCharacterService.findById(characterId);
        int oldCode = gameCharacter.getDndClass();
        int newCode = oldCode|(characterClassAccord.getCode());
        if(oldCode==newCode){
            CharacterClass characterClass = characterClassRepository.findByGameCharacterAndCharacterClassName(gameCharacter, name);
            characterClass.setCount(characterClass.getCount()+1);
        }
        else{
            gameCharacter.setDndClass(newCode);
            characterClassRepository.save(new CharacterClass(name, gameCharacter));
        }
    }

    public String loseLevel(int characterId, String name){
        CharacterClassAccord characterClassAccord = characterClassAccordService.findByName(name);
        GameCharacter gameCharacter = gameCharacterService.findById(characterId);
        int oldCode = gameCharacter.getDndClass();
        int inputCode = characterClassAccord.getCode();
        int newCode = oldCode|(inputCode);
        if(oldCode==newCode){
            CharacterClass characterClass = characterClassRepository.findByGameCharacterAndCharacterClassName(gameCharacter, name);
            int count = characterClass.getCount();
            if (count>1){
                count--;
                characterClass.setCount(count);
            }
            else {
                characterClassRepository.delete(characterClass);
                gameCharacter.setDndClass(~(~oldCode|inputCode));
                gameCharacterRepository.flush();
            }
            return "current levels: " + gameCharacter.getLevels().toString() + " with a magic level equal to " + magicLevelCalculate(gameCharacter);
        }
        else {
            return "The character does not have the " + name + " class";
        }
    }

    public int magicLevelCalculate(GameCharacter gameCharacter){
        int magicLevel=0;
        List<CharacterClass> levels=gameCharacter.getLevels();
        for (CharacterClass level:
             levels) {
            int magicPower = characterClassAccordService
                    .findByName(level.getCharacterClassName())
                    .getMagicPower();
            if (magicPower!=0) {
                magicLevel += level.getCount() / magicPower;
            }
        }
        return magicLevel;
    }


}
