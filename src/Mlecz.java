import java.awt.*;

public class Mlecz extends Roslina {
    public Mlecz(Swiat mojaGra, int x, int y, int wiek)
    {
        super(mojaGra, 1, x, y, wiek);
    }

    public Mlecz(Swiat mojaGra, int x, int y, int wiek, int sila) {
        super(mojaGra, sila, x, y, wiek);
    }

    public String getSymbol() {
        return "m";
    }
    public String getImie() {
        return "Mlecz";
    }
    public Organizm duplikujOrg(Wsp wsp_n) {
        return new Mlecz(mojaGra, wsp_n.x, wsp_n.y, 0);
    }

    @Override
    public Color Color() {
        return new Color(0x0CE88B);
    }

    public void akcja() {
        super.akcja();
        super.akcja();
        super.akcja();
    }
}
