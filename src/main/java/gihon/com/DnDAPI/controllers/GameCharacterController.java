package gihon.com.DnDAPI.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import gihon.com.DnDAPI.dao.GameCharacterDAO;
import gihon.com.DnDAPI.models.GameCharacter;
import gihon.com.DnDAPI.models.Spell;
import gihon.com.DnDAPI.services.CharacterSpellService;
import gihon.com.DnDAPI.services.GameCharacterService;
import gihon.com.DnDAPI.services.SpellService;
import gihon.com.DnDAPI.util.DnD.Calculator;
import gihon.com.DnDAPI.util.errors.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/character")
@AllArgsConstructor
public class GameCharacterController {
    private final GameCharacterService gameCharacterService;
    private final GameCharacterDAO gameCharacterDAO;
    private final SpellService spellService;
    private final CharacterSpellService characterSpellService;

    @DeleteMapping("/{id}")
    public String deleteGameCharacter(@PathVariable("id") int id){
        gameCharacterService.delete(id);
        return "OK";
    }

    @PostMapping("/{id}/addLevel")
    public void addLevel(@PathVariable("id") int id, @RequestParam("className") String className){
        gameCharacterDAO.addLevel(id, className);
    }

    @DeleteMapping("{id}/loseLevel")
    public String loseLevel(@PathVariable("id") int id, @RequestParam("className") String className){
        return gameCharacterDAO.loseLevel(id, className);
    }


    @GetMapping("/{id}/cast/{spellId}/{spellCastLevel}")
    public String castSpell(@PathVariable("id")int id, @PathVariable("spellId")int spellId, @PathVariable("spellCastLevel")int spellCastLevel){
        GameCharacter character = gameCharacterService.findById(id);
        Spell spell = characterSpellService.findById(spellId).getSpell();
        if(Calculator.getMaxSpellSlot(gameCharacterDAO.magicLevelCalculate(character))<spellCastLevel){
            return "The character does not have such a spell slot";
        }
        int extraLevel = spellCastLevel-spell.getLevel();
        if(extraLevel<0){
            return "The spell's casting level is too low";
        }
        try {
            HashMap<String,Object> map =
                    new ObjectMapper().readValue(spellService.findById(spellId).getDamage(), HashMap.class);
            return  Calculator.spellDamageCalculate(map, extraLevel).toString();
        } catch (Exception exe){
            return exe.getMessage();
        }
    }

    @GetMapping("/sayHello")
    public String sayHello(){
        return "Hello";
    }

    @GetMapping
    public List<GameCharacter> getGameCharacter(){
        return gameCharacterService.findAll();
    }

    @GetMapping("/{id}")
    public GameCharacter getGameCharacter(@PathVariable("id") int id){
        return gameCharacterService.findById(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid GameCharacter gameCharacter, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors){
                errorMsg.append(error.getField()).append(" — ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new GameCharacterNotCreatedException(errorMsg.toString());
        }

        if(isCorrect(gameCharacter)) {
            gameCharacterService.save(gameCharacter);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        else throw new CharacteristicsNotCorrectException();
    }

    @ExceptionHandler
    private ResponseEntity<CharacterErrorResponse> handleException(GameCharacterNotFoundException e){
        CharacterErrorResponse response = new CharacterErrorResponse(
                "Character wasn't found"
        );
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<CharacterErrorResponse> handleException(GameCharacterNotCreatedException e){
        CharacterErrorResponse response = new CharacterErrorResponse(
                e.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<CharacterErrorResponse> handleException(CharacterClassAccordNotFoundException e){
        CharacterErrorResponse response = new CharacterErrorResponse(
                "Character class was not found"
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<CharacterErrorResponse> handleException(CharacteristicsNotCorrectException e){
        CharacterErrorResponse response = new CharacterErrorResponse(
                "Character characteristics isn't correct"
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    public boolean isCorrect(GameCharacter gameCharacter){
        List<Integer> characteristics = Arrays.asList(
                gameCharacter.getDexterity(),
                gameCharacter.getStrength(),
                gameCharacter.getConstitution(),
                gameCharacter.getIntelligence(),
                gameCharacter.getCharisma(),
                gameCharacter.getWisdom());
        int points=characteristics.stream().map(this::convertPoints).mapToInt(Integer::intValue).sum();
        return points==27;
    }

    private int convertPoints(int i){
        if(i>=14){
            return (i-8)+(i-13);
        }
        else return i-8;
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    private ResponseEntity<CharacterErrorResponse>  handleException(HttpMessageNotReadableException ex) {
        CharacterErrorResponse response = new CharacterErrorResponse(
                ex.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }
}
