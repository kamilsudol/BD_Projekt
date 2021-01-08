import java.util.*;

/**
 * Klasa RaportWrapper
 * Pomocnicza klasa opakowujaca zmienne.
 */

public class RaportWrapper{
    public ArrayList<String> countable;
    public ArrayList<ArrayList<String>> calosc;
    public ArrayList<String> atrybuty;
    public ArrayList<String> agregaty;

    public RaportWrapper(ArrayList<ArrayList<String>> a, ArrayList<String> b, ArrayList<String> c, ArrayList<String> d){
        calosc = a;
        atrybuty = b;
        countable = c;
        agregaty = d;
    }

    public RaportWrapper(){
        calosc = null;
        atrybuty = null;
        countable = null;
        agregaty = null;
    }
}