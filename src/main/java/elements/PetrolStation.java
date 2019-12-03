package elements;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PetrolStation{

    private int x;
    private int y;
    private long id;
    private int speed; //between 50 and 100
    private boolean status = true;

}