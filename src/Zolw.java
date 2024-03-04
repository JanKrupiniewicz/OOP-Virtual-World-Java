import java.awt.*;

public class Zolw extends Zwierze {
    public Zolw(Swiat mojaGra, int x, int y, int wiek)
    {
        super(mojaGra,2, 1, x, y, wiek);
    }
    public Zolw(Swiat mojaGra, int x, int y, int wiek, int sila, int inicjatywa) {
        super(mojaGra, sila, inicjatywa, x, y, wiek);
    }

    public String getSymbol() {
        return "Z";
    }
    public void akcja() {
        int wyk_ruch = (int)(Math.random() * 4) + 1;
        if (wyk_ruch > 3) {
            super.akcja();
        }
    }
    public String getImie() {
        return "Zolw";
    }

    @Override
    public Color Color() {
        return new Color(0xFC9E48);
    }

    public Organizm duplikujOrg(Wsp wsp_n) {
        return new Zolw(mojaGra, wsp_n.x, wsp_n.y, 0);
    }
    @Override
    public boolean czyOdbilAtak(Organizm o1) {
        return (o1.getSila() < 5);
    }

}
