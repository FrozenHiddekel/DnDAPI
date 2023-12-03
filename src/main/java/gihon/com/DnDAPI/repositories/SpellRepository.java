package gihon.com.DnDAPI.repositories;

import gihon.com.DnDAPI.models.Spell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpellRepository extends JpaRepository<Spell, Integer> {
}
