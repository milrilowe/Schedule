package schedule;

public class Day {

    final int OPEN_CLOSE = 17;
    final int OPERATING_HOURS = 11;

    int[] grill1;
    int[] grill2;
    int[] dish;
    int[] lobby;
    int[] tortilla;
    int[] salsa;
    int[] cash;
    int[] prep;

    public Day() {
        grill1 = new int [OPEN_CLOSE];
        grill2 = new int [OPEN_CLOSE];
        dish = new int [OPEN_CLOSE];
        lobby = new int [OPEN_CLOSE];
        tortilla = new int [OPEN_CLOSE];
        salsa = new int [OPEN_CLOSE];
        cash = new int [OPERATING_HOURS];
        prep = new int [OPERATING_HOURS];
        
    }
}
