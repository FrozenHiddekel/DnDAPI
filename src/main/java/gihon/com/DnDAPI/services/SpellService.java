package gihon.com.DnDAPI.services;

import gihon.com.DnDAPI.models.Spell;
import gihon.com.DnDAPI.repositories.SpellRepository;
import gihon.com.DnDAPI.util.errors.SpellNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor

@Service
@Transactional(readOnly = true)
public class SpellService {
    private final SpellRepository spellRepository;

    public List<Spell> findAll(){
        return spellRepository.findAll();
    }

    public Spell findById(int id){
        Optional<Spell> foundSpell = spellRepository.findById(id);
        return foundSpell.orElseThrow(SpellNotFoundException::new);
    }

    @Transactional
    public void save(Spell spell) {
        spellRepository.save(spell);
    }

    @Transactional
    public void deleteById(int id){
        Optional<Spell> foundSpell = spellRepository.findById(id);
        spellRepository.delete(foundSpell.orElseThrow(SpellNotFoundException::new));
    }
}
