import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class Swiat {
    protected final int sizeX;
    protected final int sizeY;
    protected char rodzajSwiata;
    private boolean czlowiekZyje;
    protected int tura = 1;
    protected boolean statusGry;
    protected final List<Organizm> organizmy;
    protected final List<String> komentarze;
    protected final Organizm[][] tablicaOrganizmow;

    abstract protected Wsp ruchOrganizmu(Wsp wsp_o);
    abstract Wsp wspDoRozmnazania(Wsp wsp_o);
    abstract public void rysujSwiat();
    Swiat(int sizeX, int sizeY, char rodzajSwiata) {
        this.statusGry = true;
        czlowiekZyje = true;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.rodzajSwiata = rodzajSwiata;
        this.organizmy = new LinkedList<Organizm>();
        this.komentarze = new LinkedList<String>();
        tablicaOrganizmow = new Organizm[sizeY][sizeX];
        for(int i = 0 ; i < sizeY ; i++)
        {
            for(int j = 0 ; j < sizeX ; j++)
            {
                tablicaOrganizmow[i][j] = new Ziemia(this, j, i, 1);
            }
        }

        Random rand = new Random();
        new Czlowiek(this, rand.nextInt(sizeX), rand.nextInt(sizeY), 0);
        new Wilk(this, rand.nextInt(sizeX), rand.nextInt(sizeY), 0);
        new Wilk(this, rand.nextInt(sizeX), rand.nextInt(sizeY), 0);
        new Owca(this, rand.nextInt(sizeX), rand.nextInt(sizeY), 0);
        new Owca(this, rand.nextInt(sizeX), rand.nextInt(sizeY), 0);
        new Owca(this, rand.nextInt(sizeX), rand.nextInt(sizeY), 0);
        new Owca(this, rand.nextInt(sizeX), rand.nextInt(sizeY), 0);
        new Guarana(this, rand.nextInt(sizeX), rand.nextInt(sizeY), 0);
        new Lis(this, rand.nextInt(sizeX), rand.nextInt(sizeY), 0);
        new Zolw(this, rand.nextInt(sizeX), rand.nextInt(sizeY), 0);
        new Antylopa(this, rand.nextInt(sizeX), rand.nextInt(sizeY), 0);
        new WilczeJagody(this, rand.nextInt(sizeX), rand.nextInt(sizeY), 0);
        new Trawa(this, rand.nextInt(sizeX), rand.nextInt(sizeY), 0);
        new Zolw(this, rand.nextInt(sizeX), rand.nextInt(sizeY), 0);
        Collections.sort(this.organizmy);
    }

    public Organizm getOrganizm(int x, int y) {
        return tablicaOrganizmow[x][y];
    }

    public boolean jestNaPlanszy(Wsp n_wsp) {
        return n_wsp.x < sizeX && n_wsp.x >= 0 && n_wsp.y < sizeY && n_wsp.y >= 0;
    }

    public void ustawOrganizm(Organizm org) {
        tablicaOrganizmow[org.getWsp().y][org.getWsp().x] = org;
        if(!(org instanceof Ziemia))
            organizmy.add(org);
    }
    public void wykonajTure(char KierunekRuchu) {
        for(int i = 0 ; i < organizmy.size() ; i++)
        {
            if(organizmy.get(i) instanceof Czlowiek) ((Czlowiek) organizmy.get(i)).akcja(KierunekRuchu);
            else organizmy.get(i).akcja();
        }
        tura++;
    }
    public void zapiszDoPliku(String nazwa_pliku) {
        String zawartosc = zapiszZawartosc();
        try {
            String[] dane = zawartosc.split("\n");
            File plik = new File(nazwa_pliku);
            plik.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(nazwa_pliku));
            for(String str : dane)
            {
                writer.write(str);
                writer.newLine();
            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String zapiszZawartosc() {
        String save = "";
        save+=(this.rodzajSwiata + " " + sizeX + " " + sizeY + " "+ organizmy.size()+ "\n");
        for(int i = 0 ; i < organizmy.size() ; i++)
        {
            Organizm organizm = organizmy.get(i);
            save+=(organizm.getSymbol() + " ");
            save+=(organizm.wsp_o.x + " ");
            save+=(organizm.wsp_o.y + " ");
            save+=(organizm.wiek + " ");
            save+=(organizm.sila + " ");
            save+=(organizm.getInicjatywa() + " ");
            if(organizm instanceof Czlowiek){
                save+=(((Czlowiek) organizm).getAktywacja() + " ");
            }
            save+="\n";
        }
        return save;
    }

    public void setCzlowiekZyje(boolean czlowiekZyje) {
        this.czlowiekZyje = czlowiekZyje;
    }
    public boolean getCzlowiekZyje() {
        return czlowiekZyje;
    }
    public char getRodzajSwiata() {
        return rodzajSwiata;
    }

    public static char rodzajSwiata(String nazwaPliku) throws IOException {
        char rodzajSwiata = '\0';
        FileReader fr = new FileReader(nazwaPliku);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        if (line != null && line.length() > 0) {
            char firstChar = line.charAt(0);
            if (firstChar == 'k') {
                rodzajSwiata = 'k';
            }
            else if (firstChar == 's') {
                rodzajSwiata = 's';
            }
        }
        br.close();
        fr.close();
        return rodzajSwiata;
    }

    public Swiat(String nazwaPliku) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(nazwaPliku));
        czlowiekZyje = true;
        String wiersz = reader.readLine();
        String[] dane = wiersz.split(" ");
        sizeX = Integer.parseInt(dane[1]);
        sizeY = Integer.parseInt(dane[2]);
        int iloscOrganizmow = Integer.parseInt(dane[3]);

        this.organizmy = new LinkedList<Organizm>();
        this.komentarze = new LinkedList<String>();
        tablicaOrganizmow = new Organizm[sizeY][sizeX];
        for(int i = 0 ; i < sizeY ; i++)
        {
            for(int j = 0 ; j < sizeX ; j++)
            {
                tablicaOrganizmow[i][j] = new Ziemia(this, j, i, 1);
            }
        }
        for(int i = 0 ; i < iloscOrganizmow ; i++)
        {
            wiersz = reader.readLine();
            dane = wiersz.split(" ");
            switch (dane[0]) {
                case "C" -> this.ustawOrganizm(new Czlowiek(this,
                        Integer.parseInt(dane[1]),
                        Integer.parseInt(dane[2]),
                        Integer.parseInt(dane[3]),
                        Integer.parseInt(dane[4]),
                        Integer.parseInt(dane[5]),
                        Integer.parseInt(dane[6])));
                case "A" -> this.ustawOrganizm(new Antylopa(this,
                        Integer.parseInt(dane[1]),
                        Integer.parseInt(dane[2]),
                        Integer.parseInt(dane[3]),
                        Integer.parseInt(dane[4]),
                        Integer.parseInt(dane[5])));
                case "b" -> this.ustawOrganizm(new BarszczSosnowskiego(this,
                        Integer.parseInt(dane[1]),
                        Integer.parseInt(dane[2]),
                        Integer.parseInt(dane[3])));
                case "g" -> this.ustawOrganizm(new Guarana(this,
                        Integer.parseInt(dane[1]),
                        Integer.parseInt(dane[2]),
                        Integer.parseInt(dane[3])));
                case "L" -> this.ustawOrganizm(new Lis(this,
                        Integer.parseInt(dane[1]),
                        Integer.parseInt(dane[2]),
                        Integer.parseInt(dane[3]),
                        Integer.parseInt(dane[4]),
                        Integer.parseInt(dane[5])));
                case "m" -> this.ustawOrganizm(new Mlecz(this,
                        Integer.parseInt(dane[1]),
                        Integer.parseInt(dane[2]),
                        Integer.parseInt(dane[3])));
                case "O" -> this.ustawOrganizm(new Owca(this,
                        Integer.parseInt(dane[1]),
                        Integer.parseInt(dane[2]),
                        Integer.parseInt(dane[3]),
                        Integer.parseInt(dane[4]),
                        Integer.parseInt(dane[5])));
                case "t" -> this.ustawOrganizm(new Trawa(this,
                        Integer.parseInt(dane[1]),
                        Integer.parseInt(dane[2]),
                        Integer.parseInt(dane[3])));
                case "j" -> this.ustawOrganizm(new WilczeJagody(this,
                        Integer.parseInt(dane[1]),
                        Integer.parseInt(dane[2]),
                        Integer.parseInt(dane[3])));
                case "W" -> this.ustawOrganizm(new Wilk(this,
                        Integer.parseInt(dane[1]),
                        Integer.parseInt(dane[2]),
                        Integer.parseInt(dane[3]),
                        Integer.parseInt(dane[4]),
                        Integer.parseInt(dane[5])));
                case "Z" -> this.ustawOrganizm(new Zolw(this,
                        Integer.parseInt(dane[1]),
                        Integer.parseInt(dane[2]),
                        Integer.parseInt(dane[3]),
                        Integer.parseInt(dane[4]),
                        Integer.parseInt(dane[5])));
            }
        }
    }
    public void dodajKomenatarz(String str) {
        komentarze.add(str);
    }
    public int getTura() {
        return tura;
    }
    public String getKomentarz(int i) {
        return komentarze.get(i);
    }
    public void czyscKomentarze() {
        komentarze.clear();
    }
    public int getRozmKomentarzy() {
        return komentarze.size();
    }
    public void usunOrganizm(Organizm org) {
        organizmy.remove(org);
    }
    public int getSizeX() {
        return sizeX;
    }
    public int getSizeY() {
        return sizeY;
    }
    public boolean getStatusGry() {
        return statusGry;
    }
    public void setStatusGry(boolean nowyStatus) {
        this.statusGry = nowyStatus;
    }
    public void ustawNaPlanszy(Organizm org, int x, int y) {
        tablicaOrganizmow[y][x] = org;
    }
}
