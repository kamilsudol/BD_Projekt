/**
 * Klasa GetMiesiac
 * Statyczna klasa realizuaja ustalenie nazwy miesiaca
 * oraz liczby dni dla danego miesiaca uwzgledniajac
 * lata przestepne.
 */

public class GetMiesiac{
    static public String nazwa(int i){
        String name = "";
        switch(i){
            case 1:
                name = "Styczen";
                break;
            
            case 2:
                name = "Luty";
                break;
            
            case 3:
                name = "Marzec";
                break;
            
            case 4:
                name = "Kwiecien";
                break;
            
            case 5:
                name = "Maj";
                break;
            
            case 6:
                name = "Czerwiec";
                break;
            
            case 7:
                name = "Lipiec";
                break;
            
            case 8:
                name = "Sierpien";
                break;
            
            case 9:
                name = "Wrzesien";
                break;
            
            case 10:
                name = "Pazdziernik";
                break;
            
            case 11:
                name = "Listopad";
                break;
            
            case 12:
                name = "Grudzien";
                break;
            
        }
        return name;
    }

    static public int resolveDays(int i, int rok){
        int x = 0;
        switch(i){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                x = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                x = 30;
                break;
            case 2:
                if(((rok % 4 == 0) && !(rok % 100 == 0)) || (rok % 400 == 0)){
                    x = 29;
                }else{
                    x = 28;
                }
                break;
        }
        return x;
    }
}