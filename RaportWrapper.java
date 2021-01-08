import java.util.*;

/**
 * Klasa RaportWrapper
 * Pomocnicza klasa opakowujaca zmienne.
 */

public class RaportWrapper{
    public ArrayList<String> grupowalne;
    public ArrayList<ArrayList<String>> calosc;
    public ArrayList<String> atrybuty;

    public RaportWrapper(ArrayList<ArrayList<String>> a, ArrayList<String> b, ArrayList<String> c){
        calosc = a;
        atrybuty = b;
        grupowalne = c;
    }

    public RaportWrapper(){
        calosc = null;
        atrybuty = null;
        grupowalne = null;
    }
}