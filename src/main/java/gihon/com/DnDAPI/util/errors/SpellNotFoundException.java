package gihon.com.DnDAPI.util.errors;

public class SpellNotFoundException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Spell not found";
    }
}
