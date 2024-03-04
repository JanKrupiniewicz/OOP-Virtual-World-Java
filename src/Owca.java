import java.awt.*;

public class Owca extends Zwierze {
    public Owca(Swiat mojaGra, int x, int y, int wiek) {
        super(mojaGra, 4, 4, x, y, wiek);
    }

    public Owca(Swiat mojaGra, int x, int y, int wiek, int sila, int inicjatywa) {
        super(mojaGra, sila, inicjatywa, x, y, wiek);
    }

    @Override
    public Color Color() {
        return new Color(0xECEC84);
    }

    public String getSymbol() {
        return "O";
    }

    public String getImie() {
        return "Owca";
    }

    public Organizm duplikujOrg(Wsp wsp_n) {
        return new Owca(mojaGra, wsp_n.x, wsp_n.y, 0);
    }

}
