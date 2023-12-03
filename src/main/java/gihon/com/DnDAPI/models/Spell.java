package gihon.com.DnDAPI.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


@Entity
@Table(name = "spell")

public class Spell {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    @Size(min = 2, max = 100, message = "The title must be greater than 2 and less than 100")
    private String title;

    @Column(name = "class_code")
    private int classCode;

    @Column(name = "damage")
    private String damage;

    @Column(name = "description")
    @Size(max = 10000 )
    private String description;

    @Column(name = "level")
    @Min(0)
    @Max(9)
    @NotNull(message = "level should not be null")
    private int level;

    @OneToMany(mappedBy = "spell")
    @JsonIgnore
    @ToString.Exclude
    private List<CharacterSpell> characterSpells;
}
