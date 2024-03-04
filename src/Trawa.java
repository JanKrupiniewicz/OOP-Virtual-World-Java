import java.awt.*;

public class Trawa extends Roslina {
    public Trawa(Swiat mojaGra, int x, int y, int wiek)
    {
        super(mojaGra, 1, x, y, wiek);
    }

    public Trawa(Swiat mojaGra, int x, int y, int wiek, int sila) {
        super(mojaGra, sila, x, y, wiek);
    }

    public String getSymbol() {
        return "t";
    }
    public String getImie() {
        return "Trawa";
    }

    @Override
    public Color Color() {
        return new Color(0x99FF90);
    }

    public Organizm duplikujOrg(Wsp wsp_n) {
        return new Trawa(mojaGra, wsp_n.x, wsp_n.y, 0);
    }
}
