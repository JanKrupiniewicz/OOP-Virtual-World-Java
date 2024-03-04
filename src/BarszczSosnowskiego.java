import java.awt.*;

public class BarszczSosnowskiego extends Roslina {

    //nowyOrganizm = new BarszczSosnowskiego(mojaGra, wsp_o.x, wsp_o.y, wiek, sila, inicjatywa);
    public BarszczSosnowskiego(Swiat mojaGra, int x, int y, int wiek)
    {
        super(mojaGra, 99, x, y, wiek);
    }
    public BarszczSosnowskiego(Swiat mojaGra, int x, int y, int wiek, int sila)
    {
        super(mojaGra, sila, x, y, wiek);
    }

    public String getSymbol() {
        return "b";
    }

    public String getImie() {
        return "Barszcz Sosnowskiego";
    }

    public Organizm duplikujOrg(Wsp wsp_n) {
        return new BarszczSosnowskiego(mojaGra, wsp_n.x, wsp_n.y, 0);
    }

    @Override
    public Color Color() {
        return new Color(0x000000);
    }
    public boolean zabijZwierze(int x, int y, boolean b_akcja) {
        Organizm innyOrganizm = mojaGra.getOrganizm(x, y);
        if (innyOrganizm instanceof Zwierze)
        {
            if(innyOrganizm instanceof Czlowiek czlowiek) {
                if(czlowiek.getNiesmiertelny()) {
                    return b_akcja;
                }
                else {
                    mojaGra.setCzlowiekZyje(false);
                }
            }
            b_akcja = true;
            mojaGra.ustawNaPlanszy(new Ziemia(this.mojaGra, x, y, 1), x, y);
            mojaGra.usunOrganizm(innyOrganizm);
        }
        return b_akcja;
    }
    public void akcja() {
        boolean b_akcja = false;
        Wsp wsp_n = new Wsp(this.getWsp().x , this.getWsp().y);

        if (wsp_n.x != 0 && !(mojaGra.getOrganizm(wsp_n.y, wsp_n.x - 1) instanceof Ziemia)) {
            b_akcja = zabijZwierze(wsp_n.x - 1, wsp_n.y, b_akcja);
        }
        if (wsp_n.x != mojaGra.getSizeX() - 1 && !(mojaGra.getOrganizm(wsp_n.y, wsp_n.x + 1) instanceof Ziemia)) {
            b_akcja = zabijZwierze(wsp_n.x + 1, wsp_n.y, b_akcja);
        }
        if (wsp_n.y != 0 && !(mojaGra.getOrganizm(wsp_n.y - 1, wsp_n.x) instanceof Ziemia)) {
            b_akcja = zabijZwierze(wsp_n.x, wsp_n.y - 1, b_akcja);
        }
        if (wsp_n.y != mojaGra.getSizeY() - 1 && !(mojaGra.getOrganizm(wsp_n.y + 1, wsp_n.x) instanceof Ziemia)) {
            b_akcja = zabijZwierze(wsp_n.x, wsp_n.y + 1, b_akcja);
        }
        if (wsp_n.y != mojaGra.getSizeY() - 1 && wsp_n.x != mojaGra.getSizeX() - 1 && !(mojaGra.getOrganizm(wsp_n.y + 1, wsp_n.x + 1) instanceof Ziemia)) {
            b_akcja = zabijZwierze(wsp_n.x + 1, wsp_n.y + 1, b_akcja);
        }
        if (wsp_n.y != mojaGra.getSizeY() - 1 && wsp_n.x != 0 && !(mojaGra.getOrganizm(wsp_n.y + 1, wsp_n.x - 1) instanceof Ziemia)) {
            b_akcja = zabijZwierze(wsp_n.x - 1, wsp_n.y + 1, b_akcja);
        }
        if (wsp_n.y != 0 && wsp_n.x != mojaGra.getSizeX() - 1 && !(mojaGra.getOrganizm(wsp_n.y - 1, wsp_n.x + 1) instanceof Ziemia)) {
            b_akcja = zabijZwierze(wsp_n.x + 1, wsp_n.y - 1, b_akcja);
        }
        if (wsp_n.y != 0 && wsp_n.x != 0 && !(mojaGra.getOrganizm(wsp_n.y - 1, wsp_n.x - 1) instanceof Ziemia)) {
            b_akcja = zabijZwierze(wsp_n.x - 1, wsp_n.y - 1, b_akcja);
        }
        if (b_akcja) {
            String str = "Barszcz Sosnowskiego (" + this.getWsp().x + ", " + this.getWsp().y + ") zabil zwierze.";
            mojaGra.dodajKomenatarz(str);
        }
        super.akcja();
    }
}
