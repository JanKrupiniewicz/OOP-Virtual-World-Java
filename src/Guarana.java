import java.awt.*;

public class Guarana extends Roslina {
    public Guarana(Swiat mojaGra, int x, int y, int wiek)
    {
        super(mojaGra, 1, x, y, wiek);
    }

    public Guarana(Swiat mojaGra, int x, int y, int wiek, int sila) {
        super(mojaGra, sila, x, y, wiek);
    }

    public String getSymbol() {
        return "g";
    }

    public String getImie() {
        return "Guarana";
    }

    @Override
    public Color Color() {
        return new Color(0x16EEEE);
    }

    public Organizm duplikujOrg(Wsp wsp_n) {
        return new Guarana(mojaGra, wsp_n.x, wsp_n.y, 0);
    }
}
