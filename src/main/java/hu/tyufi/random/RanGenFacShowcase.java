package hu.tyufi.random;

import java.util.random.RandomGeneratorFactory;

public class RanGenFacShowcase {

    public static void main(String[] args) {
        RandomGeneratorFactory
                .of("L32X64MixRandom")
                .create()
                .ints(3, 0,100)
                .forEach(System.out::println);
    }
}
