package gihon.com.DnDAPI.util.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CharacterErrorResponse {
    private String message;

    private String time;

    public CharacterErrorResponse(String message) {
        this.message = message;
        java.util.Date date = new java.util.Date();
        this.time = date.toString();
    }


    //private long timestamp;
}
