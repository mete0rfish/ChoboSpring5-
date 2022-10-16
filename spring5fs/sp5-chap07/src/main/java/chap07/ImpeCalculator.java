package chap07;

public class ImpeCalculator implements Calculator {

    @Override
    public long factorial(long num) {

        long result = 0;
        for(long i = 1;i<num;i++){
            result *= i;
        }
        return result;
    }
}
