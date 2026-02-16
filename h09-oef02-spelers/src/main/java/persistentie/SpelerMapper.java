package persistentie;

import domein.Speler;
import domein.Wapen;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SpelerMapper {

    public void serialiseerObjectPerObject(Collection<Speler> spelerslijst, String naamBestand) {
        try (ObjectOutputStream output = new ObjectOutputStream(Files.newOutputStream(geefResourcePad(naamBestand)))) {
            for (Speler s : spelerslijst) {
                output.writeObject(s);
            }
        } catch (InvalidPathException ie) {
            exitApplication("Ongeldig pad.");
        } catch (IOException io) {
            exitApplication("Kan bestand niet opnenen.");
        }
    }

    public void serialiseerVolledigeLijst(Collection<Speler> spelerslijst, String naamBestand) {
        try (ObjectOutputStream output = new ObjectOutputStream(Files.newOutputStream(geefResourcePad(naamBestand)))) {
            output.writeObject(spelerslijst);
        } catch (InvalidPathException ie) {
            exitApplication("Ongeldig pad.");
        } catch (IOException io) {
            exitApplication("Kan bestand niet opnenen.");
        }
    }

    public Collection<Speler> deSerialiseerObjectPerObject(String naamBestand) {
        Speler speler = null;
        List<Speler> spelers = new ArrayList<>();
        try (ObjectInputStream input = new ObjectInputStream(Files.newInputStream(geefResourcePad(naamBestand)))) {
            while (true) {
                speler = (Speler) input.readObject();
                spelers.add(speler);
            }
        } catch (EOFException eof) {
        } catch (InvalidPathException ie) {
            exitApplication("Ongeldig pad.");
        } catch (IOException io) {
            exitApplication("Kan bestand niet opnenen.");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return spelers;
    }

    public Collection<Speler> deSerialiseerVolledigeLijst(String naamBestand) {
        Collection<Speler> spelers = null;
        try (ObjectInputStream input = new ObjectInputStream(Files.newInputStream(geefResourcePad(naamBestand)))) {
            spelers = (Collection<Speler>) input.readObject();
        } catch (InvalidPathException ie) {
            exitApplication("Ongeldig pad.");
        } catch (IOException io) {
            exitApplication("Kan bestand niet opnenen.");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return spelers;
    }

    public void schrijfNaarTekstBestand(Collection<Speler> spelerslijst, String naamBestand) {
        try (Formatter output = new Formatter(Files.newOutputStream(geefResourcePad(naamBestand)))) {
            spelerslijst.stream().forEach(s -> {
                String wapens = Arrays.stream(s.getWapens()).map(w -> w.getSoort()).collect(Collectors.joining("__"));
                output.format("%s#%s#%s%n", Integer.toString(s.getKracht()), s.getType(), wapens);
            });
        } catch (InvalidPathException ie) {
            exitApplication("Ongeldig pad.");
        } catch (IOException io) {
            exitApplication("Kan bestand niet opnenen.");
        }
    }

    public Collection<Speler> leesTekstBestand(String naamBestand) {
        List<Speler> spelers = null;
        try (Stream<String> lines = Files.lines(geefResourcePad(naamBestand))) {
            spelers = lines.map(l -> {
                String[] data = l.split("#");
                Wapen[] wapens = Arrays.stream(data[2].split("__")).map(w -> new Wapen(w)).toArray(Wapen[]::new);//
                return new Speler(Integer.parseInt(data[0]), data[1], wapens);
            }).toList();
        } catch (InvalidPathException ie) {
            exitApplication("Ongeldig pad.");
        } catch (InputMismatchException e) {
            exitApplication("Type gegevens klopt niet!");
        } catch (NoSuchElementException e) {
            exitApplication("Er ontbreken gegevens!");
        } catch (IllegalArgumentException e) {
            exitApplication(e.getMessage());
        } catch (IOException io) {
            exitApplication("Kan bestand niet opnenen.");
        }
        return spelers;
    }

    private Path geefResourcePad(String bestandsnaam) {

        try {
            var url = SpelerMapper.class.getResource("/bestanden/" + bestandsnaam);
            if (url == null) {
                exitApplication("Bestand niet gevonden: " + bestandsnaam);
            }
            return Path.of(url.toURI());
        } catch (Exception e) {
            exitApplication("Fout bij laden bestand: " + bestandsnaam);
            return null;
        }
    }

    private void exitApplication(String message) {
        System.err.println(message);
        System.exit(1);
    }
}