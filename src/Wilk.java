import java.awt.*;

public class Wilk extends Zwierze {
    public Wilk(Swiat mojaGra, int x, int y, int wiek) {
        super(mojaGra, 9, 5, x, y, wiek);
    }
    public Wilk(Swiat mojaGra, int x, int y, int wiek, int sila, int inicjatywa) {
        super(mojaGra, sila, inicjatywa, x, y, wiek);
    }

    @Override
    public Color Color() {
        return new Color(0x5D1414);
    }

    @Override
    public String getSymbol() {
        return "W";
    }
    @Override
    public String getImie() {
        return "Wilk";
    }

    @Override
    public Organizm duplikujOrg(Wsp wsp_n) {
        return new Wilk(mojaGra, wsp_n.x, wsp_n.y, 0);
    }

}
