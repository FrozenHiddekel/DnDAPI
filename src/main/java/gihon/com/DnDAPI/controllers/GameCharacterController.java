package gihon.com.DnDAPI.controllers;


import gihon.com.DnDAPI.models.GameCharacter;
import gihon.com.DnDAPI.services.GameCharacterService;
import gihon.com.DnDAPI.util.errors.CharacteristicsNotCorrectException;
import gihon.com.DnDAPI.util.errors.GameCharacterErrorResponse;
import gihon.com.DnDAPI.util.errors.GameCharacterNotCreatedException;
import gihon.com.DnDAPI.util.errors.GameCharacterNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/character")
@AllArgsConstructor
public class GameCharacterController {
    private final GameCharacterService gameCharacterService;



    @DeleteMapping("/{id}")
    public void deleteGameCharacter(@PathVariable("id") int id){
        System.out.println("test");
        System.out.println(id);
        gameCharacterService.delete(id);
    }



    @GetMapping("/sayHello")
    public String sayHello(){
        return "Hello";
    }






    @GetMapping
    public List<GameCharacter> getGameCharacter(){
        System.out.println(gameCharacterService.findAll().get(0).toString());
        return gameCharacterService.findAll();
    }

    @GetMapping("/{id}")
    public GameCharacter getGameCharacter(@PathVariable("id") int id){
        return gameCharacterService.findOne(id);
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
    private ResponseEntity<GameCharacterErrorResponse> handleException(GameCharacterNotFoundException e){
        GameCharacterErrorResponse response = new GameCharacterErrorResponse(
                "Character wasn't found",
                System.currentTimeMillis()
        );
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<GameCharacterErrorResponse> handleException(GameCharacterNotCreatedException e){
        GameCharacterErrorResponse response = new GameCharacterErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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

        System.out.println(points);
        return points==27;
    }

    private int convertPoints(int i){
        if(i>=14){
            return (i-8)+(i-13);
        }
        else return i-8;
    }

}
