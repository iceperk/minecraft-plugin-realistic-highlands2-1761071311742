
package com.realistichighlands2;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import java.util.Random;

public class HighlandsChunkGenerator extends ChunkGenerator {

    private static final int SEA_LEVEL = 63;
    private NoiseGenerator noiseGenerator;

    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
        if (noiseGenerator == null) {
            noiseGenerator = new NoiseGenerator(world.getSeed());
        }

        ChunkData chunk = createChunkData(world);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = chunkX * 16 + x;
                int worldZ = chunkZ * 16 + z;

                double noise = noiseGenerator.getNoise(worldX, worldZ);

                // Scale noise to an appropriate height range
                // The terrain will range from below sea level to very high mountains
                int height = (int) (noise * 60) + SEA_LEVEL + 20; // Adjusted for higher mountains

                for (int y = 0; y < height; y++) {
                    if (y < SEA_LEVEL) {
                        chunk.setBlock(x, y, z, Material.WATER);
                    } else if (y == height - 1) {
                        chunk.setBlock(x, y, z, Material.GRASS_BLOCK);
                    } else if (y > height - 5) {
                        chunk.setBlock(x, y, z, Material.DIRT);
                    } else {
                        chunk.setBlock(x, y, z, Material.STONE);
                    }
                }

                // Simple river/lake generation (can be improved with more complex noise)
                if (noise < -0.4 && height > SEA_LEVEL - 5 && height < SEA_LEVEL + 5) { // If it's a low area near sea level
                    for (int y = height; y <= SEA_LEVEL; y++) {
                        chunk.setBlock(x, y, z, Material.WATER);
                    }
                }

                // Basic biome setting (can be improved with more complex biome generation)
                if (height > SEA_LEVEL + 80) {
                    biome.setBiome(x, z, org.bukkit.block.Biome.SNOWY_SLOPES);
                } else if (height > SEA_LEVEL + 40) {
                    biome.setBiome(x, z, org.bukkit.block.Biome.MOUNTAIN_MEADOW);
                } else if (height > SEA_LEVEL + 10) {
                    biome.setBiome(x, z, org.bukkit.block.Biome.PLAINS);
                } else {
                    biome.setBiome(x, z, org.bukkit.block.Biome.RIVER);
                }
            }
        }
        return chunk;
    }
}
