package gihon.com.DnDAPI.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import gihon.com.DnDAPI.models.CharacterItem;
import gihon.com.DnDAPI.services.CharacterItemService;
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

import java.util.List;

@AllArgsConstructor

@RestController
@RequestMapping("/item")
public class CharacterItemController {

    private final CharacterItemService characterItemService;

    @GetMapping
    public List<CharacterItem> getCharacterItem(){
        return characterItemService.findAll();
    }

    @GetMapping("/{id}")
    public CharacterItem getCharacterItem(@PathVariable("id")int id){
        return characterItemService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCharacterItem(@PathVariable("id") int id){
        characterItemService.deleteById(id);
    }

    @GetMapping("/{id}/damage")
    public String showDamage(@PathVariable("id") int id){
        CharacterItem characterItem = characterItemService.findById(id);
        String damage = characterItem.getDamage();
        if(damage==null)
            return "The item has no damage characteristic";
        try {
            return  Calculator.DamageShow(characterItem.getDamage()) +"\n" +Calculator.DamageCalculate(characterItem.getDamage()).toString();
        }catch (JsonProcessingException exe){
            return exe.getMessage();
        }
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid CharacterItem characterItem, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors){
                errorMsg.append(error.getField()).append(" — ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new CharacterItemNotCreatedException(errorMsg.toString());
        }
        characterItemService.save(characterItem);
        return ResponseEntity.ok(HttpStatus.OK);

    }

    @ExceptionHandler
    private ResponseEntity<CharacterErrorResponse> handleException(CharacterItemNotFoundException e){
        CharacterErrorResponse response = new CharacterErrorResponse(
                "item wasn't found"
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<CharacterErrorResponse> handleException(DamageNotCorrectException e){
        CharacterErrorResponse response = new CharacterErrorResponse(
                "damage is not correct"
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<CharacterErrorResponse> handleException(CharacterItemNotCreatedException e){
        CharacterErrorResponse response = new CharacterErrorResponse(
                e.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    private ResponseEntity<CharacterErrorResponse>  handleException(HttpMessageNotReadableException ex) {
        CharacterErrorResponse response = new CharacterErrorResponse(
                ex.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
