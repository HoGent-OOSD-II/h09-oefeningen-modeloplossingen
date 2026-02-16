package domein;

import java.util.Collection;

public class DomeinController {
	private SpelerRepository spelerRepo;

	public DomeinController() {
		spelerRepo = new SpelerRepository();
	}

	public void serialiseerObjectPerObject(String[][] gegevens) {
		spelerRepo.serialiseerObjectPerObject(gegevens);
	}

	public void serialiseerVolledigeLijst(String[][] gegevens) {
		spelerRepo.serialiseerVolledigeLijst(gegevens);
	}

	public String[] deSerialiseerObjectPerObject(String naamBestand) {
		Collection<Speler> spelers = spelerRepo.deSerialiseerObjectPerObject(naamBestand);
		return omzettenNaarTekst(spelers);
	}

	public String[] deSerialiseerVolledigeLijst(String naamBestand) {
		Collection<Speler> spelers = spelerRepo.deSerialiseerVolledigeLijst(naamBestand);
		return omzettenNaarTekst(spelers);
	}

	public void schrijfNaarTekstBestand(String[][] gegevens) {
		spelerRepo.schrijfNaarTekstBestand(gegevens);
	}

	public String[] leesTekstBestand(String naamBestand) {
		Collection<Speler> spelers = spelerRepo.leesTekstBestand(naamBestand);
		return omzettenNaarTekst(spelers);
	}

	public String[] omzettenNaarTekst(Collection<Speler> spelers) {
		return spelers.stream().map(s -> s.toString()).toArray(String[]::new);

	}

}
