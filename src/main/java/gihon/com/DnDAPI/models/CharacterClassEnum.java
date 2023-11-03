package gihon.com.DnDAPI.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CharacterClassEnum {

    WARRIOR(0b1), WIZARD(0b10), ROGUE(0b100);

    private int id;

}
