public abstract class Roslina extends Organizm {

    public Roslina(Swiat mojaGra, int sila, int x, int y, int wiek)
    {
        super(mojaGra, sila, 0, x, y, wiek);
    }
    @Override
    public void akcja() {
        wiek++;
        int roz = (int) (Math.random() * 10) + 1;
        if (roz >= 9) {
            Wsp wsp_n = mojaGra.wspDoRozmnazania(this.wsp_o);
            if (wsp_n.x != -1 && wsp_n.y != -1 && mojaGra.jestNaPlanszy(wsp_n)) {
                Organizm newOrganizm = duplikujOrg(wsp_n);
                String str = "Rozsiewanie: " + getImie() + "(" + wsp_o.x + ", " + wsp_o.y + ") udane.";
                mojaGra.dodajKomenatarz(str);
            }
        }
    }

}
