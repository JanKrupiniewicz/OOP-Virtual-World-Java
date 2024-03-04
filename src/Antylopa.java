import java.awt.*;
import java.util.Random;

public class Antylopa extends Zwierze {
    public Antylopa(Swiat mojaGra, int x, int y, int wiek) {
        super(mojaGra, 4, 4, x, y, wiek);
    }
    public Antylopa(Swiat mojaGra, int x, int y, int wiek, int sila, int inicjatywa) {
        super(mojaGra, sila, inicjatywa, x, y, wiek);
    }
    @Override
    public Color Color() {
        return new Color(0xFD6E21);
    }

    @Override
    public String getSymbol() {
        return "A";
    }
    private Wsp ruchAntylopy() {
        Random rand = new Random();
        Wsp n_wsp = new Wsp(wsp_o.x, wsp_o.y);
        int losuj_pole;
        boolean niepoprawy_ruch;
        do {
            losuj_pole = rand.nextInt(8);
            switch (losuj_pole) {
                case 0:
                    n_wsp.y -= 2;
                    break;
                case 1:
                    n_wsp.y += 2;
                    break;
                case 2:
                    n_wsp.x -= 2;
                    break;
                case 3:
                    n_wsp.x += 2;
                    break;
                case 4:
                    n_wsp.y--;
                    break;
                case 5:
                    n_wsp.y++;
                    break;
                case 6:
                    n_wsp.x--;
                    break;
                case 7:
                    n_wsp.x++;
                    break;
            }
            niepoprawy_ruch = n_wsp.x < 0 || mojaGra.getSizeX() <= n_wsp.x || n_wsp.y < 0 || mojaGra.getSizeY() <= n_wsp.y;
        } while (niepoprawy_ruch);
        return n_wsp;
    }
    public void kolizja(Organizm innyOrganizm) {
        if (zgodneGatunki(innyOrganizm)) {
            rozmnazanie();
        }
        else {
            if (!(innyOrganizm.getImie().equals("Zolw") && sila < 5)) {
                mojaGra.ustawNaPlanszy(new Ziemia(mojaGra, wsp_o.x, wsp_o.y, 1), wsp_o.x, wsp_o.y);
                if (walka(innyOrganizm)) {
                    if (innyOrganizm.getImie().equals("Guarana")) {
                        String str = getImie() + " zwieksza sile: " + (getSila() + 3) + " (" + wsp_o.x + ", " + wsp_o.y + ").";
                        mojaGra.dodajKomenatarz(str);
                        setSila(getSila() + 3);
                    }
                    else {
                        String str = "WALKA: " + getImie() + " wygral z " + innyOrganizm.getImie() + " (" + wsp_o.x + ", " + wsp_o.y + ").";
                        mojaGra.dodajKomenatarz(str);
                    }
                    Wsp wsp_n = new Wsp(innyOrganizm.getWsp().x , innyOrganizm.getWsp().y);
                    mojaGra.ustawNaPlanszy(this, wsp_n.x, wsp_n.y);
                    mojaGra.usunOrganizm(innyOrganizm);
                    if(innyOrganizm instanceof Czlowiek) {
                        mojaGra.setCzlowiekZyje(false);
                    }
                }
                else {
                    Random rand = new Random();
                    int ucieczka = rand.nextInt(2);
                    if (ucieczka == 1) {
                        String str = "Antylopa uciekla przed: " + innyOrganizm.getImie() + ".";
                        mojaGra.dodajKomenatarz(str);
                    } else {
                        String str = "WALKA: " + getImie() + " przegral z " + innyOrganizm.getImie() + " (" + wsp_o.x + ", " + wsp_o.y + ").";
                        mojaGra.dodajKomenatarz(str);
                        mojaGra.ustawNaPlanszy(new Ziemia(mojaGra, wsp_o.x, wsp_o.y, 1), wsp_o.x, wsp_o.y);
                        mojaGra.usunOrganizm(this);
                    }
                }
            }
            else {
                String str = "Zolw odparl atak " + getImie() + " (" + wsp_o.x + ", " + wsp_o.y + ").";
                mojaGra.dodajKomenatarz(str);
            }
        }
    }

    public void akcja() {
        if (wiek > 0)
        {
            Wsp wsp_n = ruchAntylopy();
            if (mojaGra.getOrganizm(wsp_n.y,wsp_n.x) instanceof Ziemia)
            {
                mojaGra.ustawNaPlanszy(new Ziemia(mojaGra, wsp_o.x, wsp_o.y, 1), wsp_o.x, wsp_o.y);
                mojaGra.ustawNaPlanszy(this, wsp_n.x, wsp_n.y);
                wsp_o = wsp_n;
            }
            else
            {
                Organizm innyOrganizm = mojaGra.getOrganizm(wsp_n.y,wsp_n.x);
                kolizja(innyOrganizm);
            }
        }
        wiek++;
    }
    public String getImie() {
        return "Antylopa";
    }
    public Organizm duplikujOrg(Wsp wsp_n) {
        return new Antylopa(mojaGra, wsp_n.x, wsp_n.y, 0);
    }
}
