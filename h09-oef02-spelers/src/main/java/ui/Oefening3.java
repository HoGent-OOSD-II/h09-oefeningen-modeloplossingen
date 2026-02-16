package ui;

import domein.DomeinController;

public class Oefening3 {
	public Oefening3(DomeinController dc) {
		String spelers[][] = { { "50", "elf", "zwaard", "stof", "bovennatuurlijke onuitputtelijke wijsheid" },
				{ "200", "trol", "aks", "blote handen" },
				{ "120", "tovenaar", "vloek", "onzichtbaar", "toverstaf met slangenkop", "giftige energie" } };
		dc.schrijfNaarTekstBestand(spelers);
	}
}
