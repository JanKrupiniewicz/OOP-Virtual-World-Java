import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Scanner;

public class Czlowiek extends Zwierze {
    private boolean niesmiertelny;
    private int aktywacja;


    public Czlowiek(Swiat mojaGra, int x, int y, int wiek)
    {
        super(mojaGra, 5, 4, x, y, wiek);
        niesmiertelny = false;
        aktywacja = 0;
    }

    public Czlowiek(Swiat mojaGra, int x, int y, int wiek, int sila, int inicjatywa, int aktywacja)
    {
        super(mojaGra, sila, inicjatywa, x, y, wiek);
        niesmiertelny = false;
        this.aktywacja = aktywacja;
    }

    public String getSymbol() {
        return "C";
    }

    public String getImie() {
        return "Czlowiek";
    }

    public Organizm duplikujOrg(Wsp wsp_n) {
        return new Czlowiek(mojaGra, wsp_n.x, wsp_n.y, 0);
    }

    public void akcja(char kierunekRuchu) {
        Wsp wsp_n = new Wsp(wsp_o.x, wsp_o.y);
        if (aktywacja > 0) {
            aktywacja--;
        }
        if (wiek > 0) {
            switch (kierunekRuchu) {
                case 'u':
                    if (aktywacja == 0) {
                        String str = "Umiejetnosc aktywowana!";
                        mojaGra.dodajKomenatarz(str);
                        niesmiertelny = true;
                        aktywacja = 10;
                    } else if (aktywacja < 5) {
                        String str = "Umiejetnosc niedostepna. Tura przepada.";
                        mojaGra.dodajKomenatarz(str);
                    } else {
                        String str = "Umiejetnosc aktywna. Tura przepada.";
                        mojaGra.dodajKomenatarz(str);
                    }
                    break;
                case 'w':
                    wsp_n.y--;
                    System.out.println("UP");
                    break;
                case 'a':
                    wsp_n.x--;
                    System.out.println("LEFT");
                    break;
                case 's':
                    wsp_n.y++;
                    System.out.println("DOWN");
                    break;
                case 'd':
                    wsp_n.x++;
                    System.out.println("RIGHT");
                    break;
                default:
                    String str = "Niepoprawy ruch. Tura przepada.";
                    mojaGra.dodajKomenatarz(str);
                    return;
            }
            if (0 <= wsp_n.x && wsp_n.x < mojaGra.getSizeX() && 0 <= wsp_n.y && wsp_n.y < mojaGra.getSizeY()) {
                if (mojaGra.getOrganizm(wsp_n.y, wsp_n.x) instanceof Ziemia) {
                    mojaGra.ustawNaPlanszy(new Ziemia(mojaGra, wsp_o.x, wsp_o.y, 1), wsp_o.x, wsp_o.y);
                    mojaGra.ustawNaPlanszy(this, wsp_n.x, wsp_n.y);
                    wsp_o = wsp_n;
                } else {
                    Organizm innyOrganizm = mojaGra.getOrganizm(wsp_n.y, wsp_n.x);
                    kolizja(innyOrganizm);
                }
            }
            else {
                String str = "Niepoprawy ruch. Tura przepada.";
                mojaGra.dodajKomenatarz(str);
            }
        }
        wiek++;
    }

    @Override
    public Color Color() {
        return Color.MAGENTA;
    }

    public void kolizja(Organizm innyOrganizm) {
        if (zgodneGatunki(innyOrganizm)) {
            return;
        }
        else {
            if (!innyOrganizm.czyOdbilAtak(this)) {
                mojaGra.ustawNaPlanszy(new Ziemia(mojaGra, wsp_o.x, wsp_o.y, 1), wsp_o.x, wsp_o.y);
                if (walka(innyOrganizm)) {
                    if (innyOrganizm.getImie().equals("Guarana")) {
                        String str = this.getImie() + " zwieksza sile: " + (this.getSila() + 3) + " (" + wsp_o.x + ", " + wsp_o.y + ").";
                        mojaGra.dodajKomenatarz(str);
                        int sila = this.getSila();
                        this.setSila(sila + 3);
                    }
                    else {
                        String str = "WALKA: " + this.getImie() + " wygral z " + innyOrganizm.getImie() + " (" + wsp_o.x + ", " + wsp_o.y + ").";
                        mojaGra.dodajKomenatarz(str);
                    }
                    wsp_o = innyOrganizm.getWsp();
                    mojaGra.ustawNaPlanszy(this, wsp_o.x, wsp_o.y);
                    mojaGra.usunOrganizm(innyOrganizm);
                }
                else {
                    if (niesmiertelny) {
                        String str = "WALKA - specjalna umiejetnosc. Czlowiek przezyl.";
                        mojaGra.dodajKomenatarz(str);
                        Wsp wsp_usk = mojaGra.ruchOrganizmu(this.wsp_o);
                        wsp_o = wsp_usk;
                        mojaGra.ustawNaPlanszy(new Ziemia(mojaGra, wsp_o.x, wsp_o.y, 1), wsp_o.x, wsp_o.y);
                        mojaGra.ustawNaPlanszy(this, wsp_usk.x, wsp_usk.y);
                    }
                    else {
                        String str = "WALKA: " + this.getImie() + " przegral z " + innyOrganizm.getImie() + " (" + wsp_o.x + ", " + wsp_o.y + ").";
                        mojaGra.dodajKomenatarz(str);
                        mojaGra.ustawNaPlanszy(new Ziemia(mojaGra, wsp_o.x, wsp_o.y, 1), wsp_o.x, wsp_o.y);
                        mojaGra.usunOrganizm(this);
                        mojaGra.setCzlowiekZyje(false);
                    }
                }
            }
            else {
                String str = "Zolw odparl atak " + this.getImie() + " (" + wsp_o.x + ", " + wsp_o.y + ").";
                mojaGra.dodajKomenatarz(str);
            }
        }
    }
    public int getAktywacja() {
        return aktywacja;
    }
    public boolean getNiesmiertelny() {
        return niesmiertelny;
    }
}
