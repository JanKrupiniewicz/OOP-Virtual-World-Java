import java.io.IOException;
import java.util.Random;

public class SwiatKwadratowy extends Swiat {

    SwiatKwadratowy(int sizeX, int sizeY) {
        super(sizeX, sizeY, 'k');
    }
    public SwiatKwadratowy(String nazwaPliku) throws IOException {
        super(nazwaPliku);
        super.rodzajSwiata = 'k';
    }

    @Override
    protected Wsp ruchOrganizmu(Wsp wsp_o) {
        Wsp n_wsp;
        boolean niepoprawny_ruch = false;
        do {
            n_wsp = new Wsp(wsp_o.x, wsp_o.y);
            Random rand = new Random();
            int randKierunek = rand.nextInt(4);
            switch (randKierunek)
            {
                case 0:
                    n_wsp.y--;
                    break;
                case 1:
                    n_wsp.y++;
                    break;
                case 2:
                    n_wsp.x--;
                    break;
                case 3:
                    n_wsp.x++;
                    break;
            }
            niepoprawny_ruch = n_wsp.x < 0 || sizeX <= n_wsp.x || n_wsp.y < 0 || sizeY <= n_wsp.y;
        }while(niepoprawny_ruch);
        return n_wsp;
    }
    @Override
    public Wsp wspDoRozmnazania(Wsp wsp_o) {
        Wsp wsp_n = new Wsp(wsp_o.x, wsp_o.y);
        if(wsp_n.x != 0 && tablicaOrganizmow[wsp_n.y][wsp_n.x - 1] instanceof Ziemia)
        {
            wsp_n.x--;
            return wsp_n;
        }
        else if(wsp_n.x != sizeX - 1 && tablicaOrganizmow[wsp_n.y][wsp_n.x + 1] instanceof Ziemia)
        {
            wsp_n.x++;
            return wsp_n;
        }
        else if(wsp_n.y != 0 && tablicaOrganizmow[wsp_n.y - 1][wsp_n.x]  instanceof Ziemia)
        {
            wsp_n.y--;
            return wsp_n;
        }
        else if(wsp_n.y != sizeY - 1 && tablicaOrganizmow[wsp_n.y + 1][wsp_n.x] instanceof Ziemia)
        {
            wsp_n.y++;
            return wsp_n;
        }
        wsp_n.x = wsp_n.y = -1;
        return wsp_n;
    }
    @Override
    public void rysujSwiat() {
        System.out.println("\nTura: " + tura);
        System.out.print(".");
        for (int i = 0; i < sizeX; i++) {
            System.out.print("-");
        }
        System.out.print(".");
        System.out.println();
        for (int y = 0; y < sizeY; y++) {
            System.out.print("|");
            for (int x = 0; x < sizeX; x++) {
                System.out.print(tablicaOrganizmow[y][x].getSymbol());
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.print(".");
        for (int i = 0; i < sizeX; i++) {
            System.out.print("-");
        }
        System.out.print(".");
        System.out.println();
    }
}
