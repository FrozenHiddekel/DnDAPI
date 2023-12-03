package gihon.com.DnDAPI.controllers;


import gihon.com.DnDAPI.models.CharacterSpell;
import gihon.com.DnDAPI.services.CharacterSpellService;
import gihon.com.DnDAPI.util.errors.CharacterErrorResponse;
import gihon.com.DnDAPI.util.errors.CharacterSpellNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor

@RestController
@RequestMapping("/book")
public class CharacterSpellController {
    private final CharacterSpellService characterSpellService;

    @GetMapping
    public List<CharacterSpell> getCharacterSpell() {
        return characterSpellService.findAll();
    }

    @GetMapping("/id")
    public CharacterSpell getCharacterSpell(@PathVariable("id") int id){
        return characterSpellService.findById(id);
    }

    @DeleteMapping
    public void deleteCharacterSpell(@PathVariable("id") int id){
        characterSpellService.deleteById(id);
    }

    @ExceptionHandler
    private ResponseEntity<CharacterErrorResponse> handleException(CharacterSpellNotFoundException e){
        CharacterErrorResponse response = new CharacterErrorResponse(
                "Character doesn't have this spell"
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
