package ui;

import java.util.Arrays;

import domein.DomeinController;

public class Oefening4a {
	public Oefening4a(DomeinController dc) {
		System.out.println(Arrays.toString(dc.deSerialiseerObjectPerObject("oef2a.ser")));
	}

}
