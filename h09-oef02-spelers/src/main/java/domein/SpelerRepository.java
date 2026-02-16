package domein;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import persistentie.SpelerMapper;

public class SpelerRepository {
	private List<Speler> spelerslijst;
	private final SpelerMapper spelerMapper;

	public SpelerRepository() {
		spelerslijst = new ArrayList<>();
		spelerMapper = new SpelerMapper();
	}

	public void vulSpelerlijst(String gegevens[][]) {
		spelerslijst = Stream.of(gegevens).map(details -> {
			Wapen[] wapens = Stream.of(details).skip(2).map(w -> new Wapen(w)).toArray(Wapen[]::new);
			return new Speler(Integer.parseInt(details[0]), details[1], wapens);
		}).toList();
	}

	public void schrijfNaarTekstBestand(String[][] gegevens) {
		this.vulSpelerlijst(gegevens);
		this.spelerMapper.schrijfNaarTekstBestand(spelerslijst, "oef3.txt");
	}

	public Collection<Speler> leesTekstBestand(String naamBestand) {
		return this.spelerMapper.leesTekstBestand(naamBestand);
	}

	public void serialiseerObjectPerObject(String[][] gegevens) {
		vulSpelerlijst(gegevens);
		spelerMapper.serialiseerObjectPerObject(spelerslijst, "oef2a.ser");
	}

	public void serialiseerVolledigeLijst(String[][] gegevens) {
		vulSpelerlijst(gegevens);
		spelerMapper.serialiseerVolledigeLijst(spelerslijst, "oef2b.ser");
	}

	public Collection<Speler> deSerialiseerObjectPerObject(String naamBestand) {
		return this.spelerMapper.deSerialiseerObjectPerObject(naamBestand);
	}

	public Collection<Speler> deSerialiseerVolledigeLijst(String naamBestand) {
		return this.spelerMapper.deSerialiseerVolledigeLijst(naamBestand);
	}

}
