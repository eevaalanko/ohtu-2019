package ohtu.ohtuvarasto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VarastoTest {

    private Varasto varasto;
    private double vertailuTarkkuus = 0.0001;

    @Before
    public void setUp() {
        varasto = new Varasto(10);
    }

    @Test
    public void konstruktoriLuoTyhjanVaraston() {
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void konstruktoriNollaaVirheellisenVaraston() {
        Varasto virhVarasto = new Varasto(-1.111);
        assertEquals(0, virhVarasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void kuormitettuKonstruktoriNollaaVirheellisenVaraston() {
        Varasto kuormVarasto = new Varasto(-11.11, 50);
        assertEquals(0, kuormVarasto.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void kuormitettuKonstruktoriNollaaMiinussaldon() {
        Varasto kuormVarasto = new Varasto(20, -1000);
        assertEquals(0, kuormVarasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void kuormitettuKonstruktoriLuoTyhjanVaraston() {
        Varasto kuormVarasto = new Varasto(50, 50);
        assertEquals(50, kuormVarasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void kuormitettuKonstruktoriTayttaaVaraston() {
        Varasto kuormVarasto = new Varasto(100, 10);
        assertEquals(10, kuormVarasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void kuormitettuKonstruktoritayttaaVarastonYlijaamalla() {
        Varasto virhVarasto = new Varasto(1, 50);
        assertEquals(1, virhVarasto.getSaldo(), vertailuTarkkuus);
    } // täyteen ja ylimäärä hukkaan!

    @Test
    public void uudellaVarastollaOikeaTilavuus() {
        assertEquals(10, varasto.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void virheellinenlisaysKeskeytyy() {
        varasto.lisaaVarastoon(-1000);

        // maara ei saa olla negatiivinen
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void ylijaamaLisaysHukkaan() {  // täyteen ja ylimäärä hukkaan!
        varasto.lisaaVarastoon(1000);
        assertEquals(10, varasto.getSaldo(), vertailuTarkkuus);
    } // täyteen ja ylimäärä hukkaan!

    @Test
    public void lisaysLisaaSaldoa() {
        varasto.lisaaVarastoon(8);

        // saldon pitäisi olla sama kun lisätty määrä
        assertEquals(8, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaPienentaaVapaataTilaa() {
        varasto.lisaaVarastoon(8);

        // vapaata tilaa pitäisi vielä olla tilavuus-lisättävä määrä eli 2
        assertEquals(2, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void ottaminenPalauttaaOikeanMaaran() {
        varasto.lisaaVarastoon(8);

        double saatuMaara = varasto.otaVarastosta(2);

        assertEquals(2, saatuMaara, vertailuTarkkuus);
    }

    @Test
    public void ottaminenLisääTilaa() {
        varasto.lisaaVarastoon(8);

        varasto.otaVarastosta(2);

        // varastossa pitäisi olla tilaa 10 - 8 + 2 eli 4
        assertEquals(4, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void virheellinenOttaminenKeskeytyy() {
        varasto.lisaaVarastoon(5);

        varasto.otaVarastosta(-2);          // maara ei saa olla negatiivinen

        assertEquals(5, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void saldoaIsompiOttaminenTyhjentaaVaraston() {
        varasto.lisaaVarastoon(5);

        varasto.otaVarastosta(1000);


        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void toStringKertooSaldonJaVapaanTilan() {
        System.out.println(varasto.otaVarastosta(1000));

        assertEquals("saldo = 0.0, vielä tilaa 10.0", varasto.toString());
    }
}
