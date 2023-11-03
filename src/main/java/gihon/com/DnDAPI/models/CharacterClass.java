package gihon.com.DnDAPI.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "character_class")
@NoArgsConstructor
@AllArgsConstructor
public class CharacterClass {



    @Transient
    @JsonIgnore
    private CharacterClassEnum characterClassEnum;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "character_class_name")
    private String characterClassName;

    @Column(name = "count")
    private int count;

    //баг из-за тустринга который выводит бесконечно много хуйни в цикле
    //или сохранения в цикле
    @ManyToOne
    @JoinColumn(name = "game_character_id", referencedColumnName = "id")
    @ToString.Exclude
    @JsonIgnore
    private GameCharacter gameCharacter;

    public CharacterClass(String characterClassName, int count) {

//        HashMap<String, Integer> characterClassMap = new HashMap<>();
//        characterClassMap.put("WARRIOR", 1);
//        characterClassMap.put("WIZARD", 2);
//        characterClassMap.put("ROGUE", 4);


        this.count = count;
        //https://ru.stackoverflow.com/questions/160807/Как-по-индексу-получить-значение-перечисления

        //проверить как это работает а потом сделать глобальным, переведя список в public
        //CharacterClassEnum[] characterClassEnums = CharacterClassEnum.values();
        //this.characterClassEnum = characterClassEnums[characterClassCode];
        this.characterClassEnum = CharacterClassEnum.valueOf(characterClassName);
    }
}
