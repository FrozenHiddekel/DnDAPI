package gihon.com.DnDAPI.repositories;

import gihon.com.DnDAPI.models.GameCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameCharacterRepository extends JpaRepository<GameCharacter, Integer> {
}
