/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpletaskmanager.statistics;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author taina
 *
 *
 * HUOMHUOMHUOM!!!
 * taalla on vain esimerkkikoodia - ei viela liity tahan projektiin
 *
 */
public class Stats {

    /*
    private List<String> rivit;

    public List<String> paikat() {
        List<String> paikat = Arrays.stream(rivit.get(0).split(";")).collect(Collectors.toList());
        return paikat.subList(1, paikat.size());
    }

    public Map<String, Integer> pyorailijoitaKuukausittain(String paikka) {
        List<String> paikat = paikat();
        if (paikat.indexOf(paikka) < 0) {
            return new HashMap<>();
        }

        Map<String, List<Integer>> mittaArvotKuukausittain = new TreeMap<>();

        int indeksi = paikat.indexOf(paikka) + 1;
        rivit.stream().map(merkkijono -> merkkijono.split(";"))
                .filter(taulukko -> taulukko.length > 10)
                .forEach(taulukko -> {
                    String[] pvmTaulukko = taulukko[0].split(" ");
                    if (pvmTaulukko.length < 3) {
                        return;
                    }

                    String kuukausi = pvmTaulukko[3] ; // + " / " + merkkijonoKuukaudenNumeroksi(pvmTaulukko[2]);

                    mittaArvotKuukausittain.putIfAbsent(kuukausi, new ArrayList<>());

                    int maara = 0;
                    if (!taulukko[indeksi].isEmpty()) {
                        maara = Integer.parseInt(taulukko[indeksi]);
                    }

                    mittaArvotKuukausittain.get(kuukausi).add(maara);
                });

        Map<String, Integer> pyorailijoidenLukumaarat = new TreeMap<>();
        mittaArvotKuukausittain.keySet().stream().forEach(arvo -> {
            pyorailijoidenLukumaarat.put(arvo, mittaArvotKuukausittain.get(arvo).stream().mapToInt(a -> a).sum());
        });

        return pyorailijoidenLukumaarat;
    }
*/
    



}
