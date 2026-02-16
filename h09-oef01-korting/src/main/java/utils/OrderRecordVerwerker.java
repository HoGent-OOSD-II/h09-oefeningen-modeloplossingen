package utils;

import domein.OrderRecord;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class OrderRecordVerwerker {
    private static final String INNAAM = "order.txt";
    private static final String UITNAAM = "korting.txt";

    public static void genereerOverzichtKortingen() {
        Path inPad = geefResourcePad(INNAAM);
        Path uitPad = geefResourcePad(UITNAAM);

        try (Stream<String> lines = Files.lines(inPad);
             Formatter output = new Formatter(Files.newOutputStream(uitPad))) {
            List<OrderRecord> alleOrderRecords = lines.map(l -> {
                String[] data = l.split(" ");
                return new OrderRecord(data[0], data[1], Integer.parseInt(data[2]), Double.parseDouble(data[3]));
            }).toList();
            alleOrderRecords.stream().filter(or -> or.heeftRechtOpKorting()).forEach(or -> {
                output.format("%s %s %s %s%n", or.getNaam(), or.getProduct(), Integer.toString(or.getAantal()),
                        or.geefPrijsMetKorting());
            });
        } catch (InputMismatchException ime) {
            exitApplication(String.format("<%s> bevat ongeldige gegevens.", INNAAM));
        } catch (FormatterClosedException fce) {
            exitApplication(String.format("Fout bij schrijven naar <%s>%n", UITNAAM));
        } catch (IllegalStateException se) {
            exitApplication(String.format("Fout:bij lezen van <%s>.%n", INNAAM));
        } catch (NoSuchElementException nse) {
            exitApplication(String.format("Er ontbreken gegevens in <%s>.%n", INNAAM));
        } catch (IOException e) {
            exitApplication("Kan bestand niet openen.");
        }
    }

    private static Path geefResourcePad(String bestandsnaam) {
        try {
            URL url = OrderRecordVerwerker.class.getResource("/bestanden/" + bestandsnaam);
            if (url == null) {
                exitApplication("Bestand niet gevonden: bestanden/" + bestandsnaam);
            }
            return Path.of(url.toURI());
        } catch (Exception e) {
            exitApplication("Fout bij laden: " + bestandsnaam);
            return null; // unreachable
        }
    }

    private static void exitApplication(String message) {
        System.err.println(message);
        System.exit(1);
    }
}