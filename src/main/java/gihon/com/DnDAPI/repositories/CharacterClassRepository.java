package gihon.com.DnDAPI.repositories;

import gihon.com.DnDAPI.models.CharacterClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterClassRepository extends JpaRepository<CharacterClass, Integer> {
    @Override
    void delete(CharacterClass characterClass);
}
