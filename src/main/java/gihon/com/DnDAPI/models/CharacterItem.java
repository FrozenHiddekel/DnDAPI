package gihon.com.DnDAPI.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "item")
public class CharacterItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    @Size(min = 1, max = 100, message = "The title must be between 1 and 100 characters")
    String title;

    @Column(name = "class_code")
    int classCode;

    @Column(name = "damage")
    String damage;

    @ManyToOne
    @JoinColumn(name = "game_character_id", referencedColumnName = "id")
    @ToString.Exclude
    @JsonIgnore
    private GameCharacter gameCharacter;

}
