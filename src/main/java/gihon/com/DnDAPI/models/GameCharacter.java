package gihon.com.DnDAPI.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "game_character")
public class GameCharacter {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "person_id")
    private int personId;

    @Column(name = "class")
    private int dndClass;

    @Column(name = "dexterity")
    @Positive(message = "Dexterity should be greater than 0")
    private int dexterity;

    @Column(name = "strength")
    @Positive(message = "Strength should be greater than 0")
    private int strength;

    @Column(name = "constitution")
    @Positive(message = "Constitution should be greater than 0")
    private int constitution;

    @Column(name = "intelligence")
    @Positive(message = "Intelligence should be greater than 0")
    private int intelligence;

    @Column(name = "charisma")
    @Positive(message = "Charisma should be greater than 0")
    private int charisma;

    @Column(name = "wisdom")
    @Positive(message = "Wisdom should be greater than 0")
    private int wisdom;

    @Column(name = "race")
    private String race;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "gameCharacter")
    private List<CharacterClass> levels;

    @OneToMany(mappedBy = "gameCharacter")
    private List<CharacterItem> items;

    @OneToMany(mappedBy = "gameCharacter")
    private List<CharacterSpell> spells;

}
