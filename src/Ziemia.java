import java.awt.*;

public class Ziemia extends Roslina {
    public Ziemia(Swiat mojaGra, int x, int y, int wiek)
    {
        super(mojaGra, 1, x, y, wiek);
    }

    public String getSymbol() {
        return " ";
    }
    public String getImie() {
        return "Ziemia";
    }
    public Organizm duplikujOrg(Wsp wsp_n) {
        return new Ziemia(mojaGra, wsp_n.x, wsp_n.y, 0);
    }

    @Override
    public Color Color() {
        return new Color(0xEADDD7);
    }

    public void akcja() {
        return;
    }
}
