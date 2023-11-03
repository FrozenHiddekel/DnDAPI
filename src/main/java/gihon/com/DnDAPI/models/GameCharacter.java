package gihon.com.DnDAPI.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "game_character")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameCharacter {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "person_id")
    private int personId;

    @Column(name = "class")
    private String dndClass;

    @Column(name = "dexterity")
    @Min(value = 0, message = "Dexterity should be greater than 0")
    private int dexterity;

    @Column(name = "strength")
    @Min(value = 0, message = "Strength should be greater than 0")
    private int strength;

    @Column(name = "constitution")
    @Min(value = 0, message = "Constitution should be greater than 0")
    private int constitution;

    @Column(name = "intelligence")
    @Min(value = 0, message = "Intelligence should be greater than 0")
    private int intelligence;

    @Column(name = "charisma")
    @Min(value = 0, message = "Charisma should be greater than 0")
    private int charisma;

    @Column(name = "wisdom")
    @Min(value = 0, message = "Wisdom should be greater than 0")
    private int wisdom;

    @Column(name = "race")
    private String race;

    @Column(name = "description")
    private String description;


    @OneToMany(mappedBy = "gameCharacter")
    //@ToString.Exclude
    //@JsonIgnore
    private List<CharacterClass> levels;


}
