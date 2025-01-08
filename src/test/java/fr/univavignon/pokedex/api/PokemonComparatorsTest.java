package fr.univavignon.pokedex.api;

import org.junit.Test;

import java.util.List;

public class PokemonComparatorsTest {

    PokemonLoader loader = new PokemonLoader();
    List<Pokemon> pokemons = loader.loadPokemons("pokedexfile");
    @Test
    public void testCompare() {
        Pokemon bulbizarre = pokemons.get(0);
        Pokemon herbizarre = pokemons.get(1);
        int comparaison1 = PokemonComparators.NAME.compare(bulbizarre, herbizarre);
        int comparaison2 = PokemonComparators.INDEX.compare(bulbizarre, herbizarre);
        int comparaison3 = PokemonComparators.CP.compare(bulbizarre, herbizarre);
        assert(comparaison1<0);
        assert(comparaison2<0);
        assert(comparaison3<0);
    }
}
