package gihon.com.DnDAPI.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "character_spell")
public class CharacterSpell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "is_forced")
    private boolean isForced;

    @ManyToOne
    @JoinColumn(name = "spell_id", referencedColumnName = "id")
    private Spell spell;

    @ManyToOne
    @JoinColumn(name = "game_character_id", referencedColumnName = "id")
    @ToString.Exclude
    @JsonIgnore
    private GameCharacter gameCharacter;
}
