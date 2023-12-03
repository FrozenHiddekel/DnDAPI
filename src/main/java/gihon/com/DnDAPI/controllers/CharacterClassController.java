package gihon.com.DnDAPI.controllers;


import gihon.com.DnDAPI.models.CharacterClass;
import gihon.com.DnDAPI.services.CharacterClassService;
import gihon.com.DnDAPI.util.errors.CharacterClassNotFoundException;
import gihon.com.DnDAPI.util.errors.CharacterErrorResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/class")
@AllArgsConstructor
public class CharacterClassController {

    private final CharacterClassService characterClassService;

    @DeleteMapping("/{id}")
    public void deleteCharacterClass(@PathVariable("id") int id){
        characterClassService.deleteById(id);
    }

    @GetMapping
    public List<CharacterClass> getCharacterClass(){
        return characterClassService.findAll();
    }

    @GetMapping("/{id}")
    public CharacterClass getCharacterClass(@PathVariable("id") int id){
        return characterClassService.findById(id);
    }


    @ExceptionHandler
    private ResponseEntity<CharacterErrorResponse> handleException(CharacterClassNotFoundException e){
        CharacterErrorResponse response = new CharacterErrorResponse(
                "Class wasn't found"
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
