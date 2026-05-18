package com.example.pokedex.service;

import com.example.pokedex.entity.Type;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class TypeEffectivenessService {
    private static final Map<Type, Map<Type, Double>> chart = new HashMap<>();

    static {
            // NORMAL
            chart.put(Type.NORMAL, Map.ofEntries(
                    Map.entry(Type.FIGHTING, 2.0),
                    Map.entry(Type.GHOST, 0.0)
            ));

            // FIRE
            chart.put(Type.FIRE, Map.ofEntries(
                    Map.entry(Type.WATER, 2.0),
                    Map.entry(Type.GROUND, 2.0),
                    Map.entry(Type.ROCK, 2.0),

                    Map.entry(Type.FIRE, 0.5),
                    Map.entry(Type.GRASS, 0.5),
                    Map.entry(Type.ICE, 0.5),
                    Map.entry(Type.BUG, 0.5),
                    Map.entry(Type.STEEL, 0.5),
                    Map.entry(Type.FAIRY, 0.5)
            ));

            // WATER
            chart.put(Type.WATER, Map.ofEntries(
                    Map.entry(Type.ELECTRIC, 2.0),
                    Map.entry(Type.GRASS, 2.0),

                    Map.entry(Type.FIRE, 0.5),
                    Map.entry(Type.WATER, 0.5),
                    Map.entry(Type.ICE, 0.5),
                    Map.entry(Type.STEEL, 0.5)
            ));

            // GRASS
            chart.put(Type.GRASS, Map.ofEntries(
                    Map.entry(Type.FIRE, 2.0),
                    Map.entry(Type.ICE, 2.0),
                    Map.entry(Type.POISON, 2.0),
                    Map.entry(Type.FLYING, 2.0),
                    Map.entry(Type.BUG, 2.0),

                    Map.entry(Type.WATER, 0.5),
                    Map.entry(Type.ELECTRIC, 0.5),
                    Map.entry(Type.GRASS, 0.5),
                    Map.entry(Type.GROUND, 0.5)
            ));

            // ELECTRIC
            chart.put(Type.ELECTRIC, Map.ofEntries(
                    Map.entry(Type.GROUND, 2.0),

                    Map.entry(Type.ELECTRIC, 0.5),
                    Map.entry(Type.FLYING, 0.5),
                    Map.entry(Type.STEEL, 0.5)
            ));

            // ICE
            chart.put(Type.ICE, Map.ofEntries(
                    Map.entry(Type.FIRE, 2.0),
                    Map.entry(Type.FIGHTING, 2.0),
                    Map.entry(Type.ROCK, 2.0),
                    Map.entry(Type.STEEL, 2.0),

                    Map.entry(Type.ICE, 0.5)
            ));

            // FIGHTING
            chart.put(Type.FIGHTING, Map.ofEntries(
                    Map.entry(Type.FLYING, 2.0),
                    Map.entry(Type.PSYCHIC, 2.0),
                    Map.entry(Type.FAIRY, 2.0),

                    Map.entry(Type.BUG, 0.5),
                    Map.entry(Type.ROCK, 0.5),
                    Map.entry(Type.DARK, 0.5)
            ));

            // POISON
            chart.put(Type.POISON, Map.ofEntries(
                    Map.entry(Type.GROUND, 2.0),
                    Map.entry(Type.PSYCHIC, 2.0),

                    Map.entry(Type.GRASS, 0.5),
                    Map.entry(Type.FIGHTING, 0.5),
                    Map.entry(Type.POISON, 0.5),
                    Map.entry(Type.BUG, 0.5),
                    Map.entry(Type.FAIRY, 0.5)
            ));

            // GROUND
            chart.put(Type.GROUND, Map.ofEntries(
                    Map.entry(Type.WATER, 2.0),
                    Map.entry(Type.GRASS, 2.0),
                    Map.entry(Type.ICE, 2.0),

                    Map.entry(Type.POISON, 0.5),
                    Map.entry(Type.ROCK, 0.5),

                    Map.entry(Type.ELECTRIC, 0.0)
            ));

            // FLYING
            chart.put(Type.FLYING, Map.ofEntries(
                    Map.entry(Type.ELECTRIC, 2.0),
                    Map.entry(Type.ICE, 2.0),
                    Map.entry(Type.ROCK, 2.0),

                    Map.entry(Type.GRASS, 0.5),
                    Map.entry(Type.FIGHTING, 0.5),
                    Map.entry(Type.BUG, 0.5),

                    Map.entry(Type.GROUND, 0.0)
            ));

            // PSYCHIC
            chart.put(Type.PSYCHIC, Map.ofEntries(
                    Map.entry(Type.BUG, 2.0),
                    Map.entry(Type.GHOST, 2.0),
                    Map.entry(Type.DARK, 2.0),

                    Map.entry(Type.FIGHTING, 0.5),
                    Map.entry(Type.PSYCHIC, 0.5)
            ));

            // BUG
            chart.put(Type.BUG, Map.ofEntries(
                    Map.entry(Type.FIRE, 2.0),
                    Map.entry(Type.FLYING, 2.0),
                    Map.entry(Type.ROCK, 2.0),

                    Map.entry(Type.GRASS, 0.5),
                    Map.entry(Type.FIGHTING, 0.5),
                    Map.entry(Type.GROUND, 0.5)
            ));

            // ROCK
            chart.put(Type.ROCK, Map.ofEntries(
                    Map.entry(Type.WATER, 2.0),
                    Map.entry(Type.GRASS, 2.0),
                    Map.entry(Type.FIGHTING, 2.0),
                    Map.entry(Type.GROUND, 2.0),
                    Map.entry(Type.STEEL, 2.0),

                    Map.entry(Type.NORMAL, 0.5),
                    Map.entry(Type.FIRE, 0.5),
                    Map.entry(Type.POISON, 0.5),
                    Map.entry(Type.FLYING, 0.5)
            ));

            // GHOST
            chart.put(Type.GHOST, Map.ofEntries(
                    Map.entry(Type.GHOST, 2.0),
                    Map.entry(Type.DARK, 2.0),

                    Map.entry(Type.POISON, 0.5),
                    Map.entry(Type.BUG, 0.5),

                    Map.entry(Type.NORMAL, 0.0),
                    Map.entry(Type.FIGHTING, 0.0)
            ));

            // DRAGON
            chart.put(Type.DRAGON, Map.ofEntries(
                    Map.entry(Type.ICE, 2.0),
                    Map.entry(Type.DRAGON, 2.0),
                    Map.entry(Type.FAIRY, 2.0),

                    Map.entry(Type.FIRE, 0.5),
                    Map.entry(Type.WATER, 0.5),
                    Map.entry(Type.ELECTRIC, 0.5),
                    Map.entry(Type.GRASS, 0.5)
            ));

            // DARK
            chart.put(Type.DARK, Map.ofEntries(
                    Map.entry(Type.FIGHTING, 2.0),
                    Map.entry(Type.BUG, 2.0),
                    Map.entry(Type.FAIRY, 2.0),

                    Map.entry(Type.GHOST, 0.5),
                    Map.entry(Type.DARK, 0.5),

                    Map.entry(Type.PSYCHIC, 0.0)
            ));

            // STEEL
            chart.put(Type.STEEL, Map.ofEntries(
                    Map.entry(Type.FIRE, 2.0),
                    Map.entry(Type.FIGHTING, 2.0),
                    Map.entry(Type.GROUND, 2.0),

                    Map.entry(Type.NORMAL, 0.5),
                    Map.entry(Type.GRASS, 0.5),
                    Map.entry(Type.ICE, 0.5),
                    Map.entry(Type.FLYING, 0.5),
                    Map.entry(Type.PSYCHIC, 0.5),
                    Map.entry(Type.BUG, 0.5),
                    Map.entry(Type.ROCK, 0.5),
                    Map.entry(Type.DRAGON, 0.5),
                    Map.entry(Type.STEEL, 0.5),
                    Map.entry(Type.FAIRY, 0.5),

                    Map.entry(Type.POISON, 0.0)
            ));

            // FAIRY
            chart.put(Type.FAIRY, Map.ofEntries(
                    Map.entry(Type.POISON, 2.0),
                    Map.entry(Type.STEEL, 2.0),

                    Map.entry(Type.FIGHTING, 0.5),
                    Map.entry(Type.BUG, 0.5),
                    Map.entry(Type.DARK, 0.5),

                    Map.entry(Type.DRAGON, 0.0)
            ));
        }

    // calcular fraquezas
    public Set<Type> calculateWeaknesses(Set<Type> pokemonTypes) {
        // map para guardar os resultados
        Map<Type, Double> result = new HashMap<>();
        // percorrer todos os tipos do enum
        for (Type t : Type.values()){
            result.put(t, 1.0); // inicializar todos com o multiplicador 1
        }

        // percorrer os tipos do pokemon passado pelo parametro
        for (Type defensiveType : pokemonTypes){
            // pega a tabela de fraquezas para os tipos do pokemon
            Map<Type, Double> effects = chart.getOrDefault(defensiveType, Map.of()); // se não existir o tipo, retorna um map vazio

            // precorre toda a lista de fraquezas para aqueles 2 tipos ou so 1
            for (Map.Entry<Type, Double> entry : effects.entrySet()){
                Type attacker = entry.getKey(); // pega o tipo atacante, no caso a chave
                Double multiplier = entry.getValue(); // e o valor do multiplicador, no caso o valor

                result.put(attacker, // pega o valpr atual que é 1, e mulitplica pelo o valor do mulitplicador
                        result.get(attacker) * multiplier
                );

            }

        }
        // filtra apenas fraquezas reais (>= 2x)
        return result.entrySet().stream()
                .filter(e -> e.getValue() >= 2.0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}