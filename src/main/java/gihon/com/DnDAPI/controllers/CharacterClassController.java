package gihon.com.DnDAPI.controllers;


import gihon.com.DnDAPI.models.CharacterClass;
import gihon.com.DnDAPI.services.CharacterClassService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/class")
@AllArgsConstructor
public class CharacterClassController {

    private final CharacterClassService characterClassService;


    @DeleteMapping("/{id}")
    public void deleteCharacterClass(@PathVariable("id") int id){
        characterClassService.delete(id);
    }

    @GetMapping
    public List<CharacterClass> getCharacterClass(){
        return characterClassService.findAll();
    }

    @GetMapping("/{id}")
    public CharacterClass getCharacterClass(@PathVariable("id") int id){
        return characterClassService.findOne(id);
    }


}
