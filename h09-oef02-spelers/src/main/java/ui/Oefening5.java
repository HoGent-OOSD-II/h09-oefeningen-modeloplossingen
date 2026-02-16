package ui;

import java.util.Arrays;

import domein.DomeinController;

public class Oefening5 {
	public Oefening5(DomeinController dc) {
		System.out.println(Arrays.toString(dc.leesTekstBestand("oef3.txt")));
	}

}
