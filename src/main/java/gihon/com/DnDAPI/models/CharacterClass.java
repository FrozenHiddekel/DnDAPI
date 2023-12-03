package gihon.com.DnDAPI.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "character_class")
public class CharacterClass {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "character_class_name")
    private String characterClassName;

    @Column(name = "count")
    private int count;

    @ManyToOne
    @JoinColumn(name = "game_character_id", referencedColumnName = "id")
    @ToString.Exclude
    @JsonIgnore
    private GameCharacter gameCharacter;

    public CharacterClass(String characterClassName, int count, GameCharacter gameCharacter) {
        this.characterClassName = characterClassName;
        this.count = count;
        this.gameCharacter = gameCharacter;
    }

    public CharacterClass(String characterClassName, GameCharacter gameCharacter) {
        this.characterClassName = characterClassName;
        this.count = 1;
        this.gameCharacter = gameCharacter;
    }

}
