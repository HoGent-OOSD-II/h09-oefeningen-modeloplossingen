package main;

import domein.DomeinController;
import ui.*;

public class StartUp {
    public static void main(String args[]) {
        DomeinController dc = new DomeinController();
        new Oefening2a(dc);
        new Oefening2b(dc);
        new Oefening3(dc);
        new Oefening4a(dc);
        new Oefening4b(dc);
        new Oefening5(dc);
    }
}