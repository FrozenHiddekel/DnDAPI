package gihon.com.DnDAPI.util.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GameCharacterErrorResponse {
    private String message;
    private long timestamp;
}
