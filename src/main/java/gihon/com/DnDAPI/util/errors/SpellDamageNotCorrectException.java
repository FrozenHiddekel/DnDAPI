package gihon.com.DnDAPI.util.errors;

public class SpellDamageNotCorrectException extends RuntimeException{
    @Override
    public String getMessage() {
        return "damage is not correct";
    }
}
