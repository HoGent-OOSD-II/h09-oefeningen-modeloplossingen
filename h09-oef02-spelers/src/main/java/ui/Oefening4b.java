package ui;

import java.util.Arrays;

import domein.DomeinController;

public class Oefening4b {
	public Oefening4b(DomeinController dc) {
		System.out.println(Arrays.toString(dc.deSerialiseerVolledigeLijst("oef2b.ser")));
	}
}
