package gihon.com.DnDAPI.repositories;

import gihon.com.DnDAPI.models.CharacterClassAccord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharacterClassAccordRepository extends JpaRepository<CharacterClassAccord, Integer> {
    Optional<CharacterClassAccord> findByName(String name);

}
