package utils;

import domein.OrderRecord;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Stream;

public class OrderRecordVerwerker {
    private static final String INNAAM = "order.txt";
    private static final String UITNAAM = "korting.txt";

    public static void genereerOverzichtKortingen() {
        Path inPad = geefInputPad(INNAAM);
        Path uitPad = geefOutputPad(UITNAAM);

        try (Stream<String> lines = Files.lines(inPad);
             Formatter output = new Formatter(Files.newOutputStream(uitPad, StandardOpenOption.CREATE, StandardOpenOption.WRITE))) {
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

    // Voor INPUT (moet bestaan)
    private static Path geefInputPad(String bestandsnaam) {
        URL url = OrderRecordVerwerker.class.getResource("/bestanden/" + bestandsnaam);
        if (url == null) exitApplication("Input mist: " + bestandsnaam);
        try {
            return Path.of(url.toURI());
        } catch (URISyntaxException e) {
            exitApplication(String.format("Kan bestand %s niet maken", bestandsnaam));
        }
        return null;
    }

    // Voor OUTPUT (mag nieuw zijn)
    private static Path geefOutputPad(String bestandsnaam) {
        Path pad = Path.of("target", "classes", "bestanden", bestandsnaam);
        return pad;
    }

    private static void exitApplication(String message) {
        System.err.println(message);
        System.exit(1);
    }
}