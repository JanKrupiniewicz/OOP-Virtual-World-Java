import java.awt.*;

public class Lis extends Zwierze {
    public Lis(Swiat mojaGra, int x, int y, int wiek) {
        super(mojaGra, 3, 7, x, y, wiek);
    }

    public Lis(Swiat mojaGra, int x, int y, int wiek, int sila, int inicjatywa) {
        super(mojaGra, sila, inicjatywa, x, y, wiek);
    }

    public String getSymbol() {
        return "L";
    }

    public String getImie() {
        return "Lis";
    }

    public void akcja() {
        if (wiek > 0) {
            Wsp wsp_n = mojaGra.ruchOrganizmu(this.wsp_o);

            if (mojaGra.getOrganizm(wsp_n.y, wsp_n.x) instanceof Ziemia) {
                mojaGra.ustawNaPlanszy(new Ziemia(mojaGra, wsp_o.x, wsp_o.y, 0), wsp_o.x, wsp_o.y);
                mojaGra.ustawNaPlanszy(this, wsp_n.x, wsp_n.y);
                wsp_o = wsp_n;
            }
            else {
                if (mojaGra.getOrganizm(wsp_n.y, wsp_n.x).getSila() <= sila) {
                    Organizm innyOrganizm = mojaGra.getOrganizm(wsp_n.y, wsp_n.x);
                    kolizja(innyOrganizm);
                }
            }
        }
        wiek++;
    }

    @Override
    public Color Color() {
        return new Color(0xF81C21);
    }

    public Organizm duplikujOrg(Wsp wsp_n) {
        return new Lis(mojaGra, wsp_n.x, wsp_n.y, 1);
    }
}
