package domein;

import java.util.Map;

public class Veld {
    
    private Type type;

    private Map<Character, Type> typeMap = Map.of(
            '#', Type.MUUR,
            '.', Type.DOEL,
            ' ', Type.LEEG,
            'M', Type.MAN,
            'x', Type.KIST,
            'D', Type.MANDOEL,
            'S', Type.KISTDOEL
    );


    public Veld(char teken) {
        stelTypeIn(teken);
    }

    private void stelTypeIn(char teken) {
        if(typeMap.containsKey(teken)) {
            this.type = typeMap.get(teken);
        } else {
            throw new IllegalArgumentException("foutiefSpelbord");
        }
    }

    public Type getType() {
        return type;
    }
}
