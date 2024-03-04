import java.awt.*;

public class WilczeJagody extends Roslina {
    public WilczeJagody(Swiat mojaGra, int x, int y, int wiek) {
        super(mojaGra, 99, x, y, wiek);
    }

    public WilczeJagody(Swiat mojaGra, int x, int y, int wiek, int sila) {
        super(mojaGra, sila, x, y, wiek);
    }

    public String getSymbol() {
        return "j";
    }

    public String getImie() {
        return "Wilcze Jagody";
    }

    @Override
    public Color Color() {return new Color(0x043A04);}

    public Organizm duplikujOrg(Wsp wsp_n) {
        return new WilczeJagody(mojaGra, wsp_n.x, wsp_n.y, 0);
    }

}
