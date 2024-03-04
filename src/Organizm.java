import java.awt.*;
import java.util.Random;

public abstract class Organizm implements Comparable<Organizm> {
    protected int wiek;
    protected int sila;
    private final int inicjatywa;
    protected Swiat mojaGra;
    protected Wsp wsp_o;

    abstract public String getSymbol();
    abstract public Color Color();
    abstract public void akcja();
    abstract public Organizm duplikujOrg(Wsp wsp_n);
    abstract public String getImie();

    public Organizm(Swiat mojaGra, int sila, int inicjatywa, int x, int y, int wiek) {
        wsp_o = new Wsp(x, y);
        this.mojaGra = mojaGra;
        this.sila = sila;
        this.wiek = wiek;
        this.inicjatywa = inicjatywa;
        mojaGra.ustawOrganizm(this);
    }

    public Wsp getWsp() {
        return wsp_o;
    }
    public void setWsp(int x , int y) {
        wsp_o.x = x;
        wsp_o.y = y;
    }

    public boolean czyOdbilAtak(Organizm o1) {
        return false;
    }
    public int getSila(){
        return sila;
    }
    public void setSila(int n_sila) {
        sila = n_sila;
    }
    public int getWiek() {
        return wiek;
    }
    public int getInicjatywa() {
        return inicjatywa;
    }
    public int compareTo(Organizm o) {
        return -Integer.compare(this.inicjatywa, o.inicjatywa);
    }
}
