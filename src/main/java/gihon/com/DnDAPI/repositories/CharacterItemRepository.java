package gihon.com.DnDAPI.repositories;

import gihon.com.DnDAPI.models.CharacterItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterItemRepository extends JpaRepository<CharacterItem, Integer> {
}
