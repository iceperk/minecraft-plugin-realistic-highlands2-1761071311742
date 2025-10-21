
package com.realistichighlands2;

import org.bukkit.util.noise.SimplexOctaveGenerator;

import java.util.Random;

public class NoiseGenerator {

    private SimplexOctaveGenerator generator;
    private int octaves = 8;
    private double scale = 0.008D;
    private double amplitude = 0.8D;
    private double lacunarity = 2.0D;
    private double persistence = 0.5D;

    public NoiseGenerator(long seed) {
        this.generator = new SimplexOctaveGenerator(new Random(seed), octaves);
        this.generator.setScale(scale);
    }

    public double getNoise(double x, double z) {
        double total = 0;
        double frequency = 1;
        double currentAmplitude = this.amplitude;

        for (int i = 0; i < octaves; i++) {
            total += generator.noise(x * frequency, z * frequency, currentAmplitude, persistence);
            currentAmplitude *= persistence;
            frequency *= lacunarity;
        }
        return total;
    }
}
