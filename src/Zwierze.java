import java.util.Objects;

public abstract class Zwierze extends Organizm {
    protected Zwierze(Swiat mojaGra, int sila, int inicjatywa, int x, int y, int wiek) {
        super(mojaGra, sila, inicjatywa, x, y, wiek);
    }
    public abstract String getSymbol();
    public abstract String getImie();
    public abstract Organizm duplikujOrg(Wsp wsp_n);
    @Override
    public void akcja() {
        if (this.wiek > 0) {
            Wsp wsp_n = mojaGra.ruchOrganizmu(this.wsp_o);
            if (mojaGra.getOrganizm(wsp_n.y, wsp_n.x) instanceof Ziemia) {
                mojaGra.ustawNaPlanszy(new Ziemia(mojaGra, wsp_o.x, wsp_o.y, 1), wsp_o.x, wsp_o.y);
                mojaGra.ustawNaPlanszy(this, wsp_n.x, wsp_n.y);
                wsp_o = wsp_n;
            }
            else {
                Organizm innyOrganizm = mojaGra.getOrganizm(wsp_n.y, wsp_n.x);
                kolizja(innyOrganizm);
            }
        }
        wiek++;
    }

    public void kolizja(Organizm innyOrganizm) {
        if (zgodneGatunki(innyOrganizm)) {
            rozmnazanie();
        }
        else {
            if (!innyOrganizm.czyOdbilAtak(this)) {
                mojaGra.ustawNaPlanszy(new Ziemia(mojaGra, wsp_o.x, wsp_o.y, 1), wsp_o.x, wsp_o.y);
                if (walka(innyOrganizm)) {
                    if (innyOrganizm.getImie().equals("Guarana"))
                    {
                        String str = this.getImie() + " zwieksza sile: " + (this.getSila() + 3) + " (" + wsp_o.x + ", " + wsp_o.y + ").";
                        mojaGra.dodajKomenatarz(str);
                        int sila = this.getSila();
                        this.setSila(sila + 3);
                    }
                    else
                    {
                        String str = "WALKA: " + this.getImie() + " wygral z " + innyOrganizm.getImie() + " (" + wsp_o.x + ", " + wsp_o.y + ").";
                        mojaGra.dodajKomenatarz(str);
                        if(innyOrganizm instanceof Czlowiek) {
                            mojaGra.setCzlowiekZyje(false);
                        }
                    }
                    wsp_o = innyOrganizm.getWsp();
                    mojaGra.ustawNaPlanszy(this, wsp_o.x, wsp_o.y);
                    mojaGra.usunOrganizm(innyOrganizm);
                }
                else
                {
                    String str = "WALKA: " + this.getImie() + " przegral z " + innyOrganizm.getImie() + " (" + wsp_o.x + ", " + wsp_o.y + ").";
                    mojaGra.dodajKomenatarz(str);
                    mojaGra.ustawNaPlanszy(new Ziemia(mojaGra, wsp_o.x, wsp_o.y, 1), wsp_o.x, wsp_o.y);
                    mojaGra.usunOrganizm(this);
                }
            }
            else {
                String str = "Zolw odparl atak " + this.getImie() + " (" + wsp_o.x + ", " + wsp_o.y + ").";
                mojaGra.dodajKomenatarz(str);
            }
        }
    }
    protected boolean walka(Organizm innyOrganizm) {
        return this.sila >= innyOrganizm.sila;
    }
    protected void rozmnazanie() {
        Wsp wsp_n = mojaGra.wspDoRozmnazania(this.wsp_o);
        if(wsp_n.x != - 1 && wsp_n.y != -1 && mojaGra.jestNaPlanszy(wsp_n))
        {
            String str = "Rozmnazanie udane";
            mojaGra.dodajKomenatarz(str);
            duplikujOrg(wsp_n);
        }
    }
    public boolean zgodneGatunki(Organizm innyOrganizm) {
        return Objects.equals(innyOrganizm.getImie(), this.getImie());
    }
}
