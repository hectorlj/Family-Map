package services;

/**
 * Created by Hector Lopez on 10/27/2017.
 */

class fillService {

    public int calculateItems (int Generations){
        int total = 1;
        for(int i = 1; i <= Generations; i++){
            total += (int)Math.pow(2,i);
        }
        return total;
    }
}
