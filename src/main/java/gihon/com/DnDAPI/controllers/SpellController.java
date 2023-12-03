package gihon.com.DnDAPI.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gihon.com.DnDAPI.models.Spell;
import gihon.com.DnDAPI.services.SpellService;
import gihon.com.DnDAPI.util.DnD.Calculator;
import gihon.com.DnDAPI.util.errors.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor

@RestController
@RequestMapping("/spell")
public class SpellController {

    private final SpellService spellService;

    @GetMapping
    public List<Spell> getSpell(){
        return spellService.findAll();
    }

    @GetMapping("/{id}")
    public Spell getSpell(@PathVariable("id") int id){
        return spellService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteSpell(@PathVariable("id") int id){
        spellService.deleteById(id);
    }

    @GetMapping("/calculate/{id}")
    public String spellDamageCalculate(@PathVariable("id") int id, @RequestParam("castLevel") int castLevel) {
        int extraLevel = castLevel-spellService.findById(id).getLevel();
        if(extraLevel<0){
            return "The spell's casting level is too low";
        }
        try {
            HashMap<String,Object> map =
                    new ObjectMapper().readValue(spellService.findById(id).getDamage(), HashMap.class);
            return Calculator.spellDamageCalculate(map, extraLevel).toString();
        } catch (Exception exe){
            return exe.getMessage();
        }
    }

    @ExceptionHandler
    private ResponseEntity<CharacterErrorResponse> handleException(SpellNotFoundException e){
        CharacterErrorResponse response = new CharacterErrorResponse(
                "Spell wasn't found"
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<CharacterErrorResponse> handleException(SpellNotCreatedException e){
        CharacterErrorResponse response = new CharacterErrorResponse(
                e.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }




}
