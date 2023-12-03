package gihon.com.DnDAPI.repositories;

import gihon.com.DnDAPI.models.CharacterSpell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterSpellRepository extends JpaRepository<CharacterSpell, Integer> {
}
