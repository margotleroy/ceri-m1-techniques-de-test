package fr.univavignon.pokedex.api;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import org.mockito.Mockito;

public class IPokemonTrainerFactoryTest {
    @Test
    public void testCreateTrainer() {
        IPokemonTrainerFactory trainerFactory = Mockito.mock(IPokemonTrainerFactory.class);
        IPokedexFactory pokedexFactory = Mockito.mock(IPokedexFactory.class);
        IPokedex pokedex = Mockito.mock(IPokedex.class);

        // Crée un entraîneur en utilisant le mock
        PokemonTrainer trainer = trainerFactory.createTrainer("Ash", Team.MYSTIC, pokedexFactory);
        Mockito.when(trainerFactory.createTrainer(Mockito.anyString(), Mockito.any(Team.class), Mockito.any(IPokedexFactory.class)))
                .thenReturn(trainer);

        // Vérifie que l'entraîneur créé n'est pas nul
        assertNotNull(trainer);

        // Vérifie que les informations de l'entraîneur créé sont correctes
        assertEquals("Ash", trainer.getName());
        assertEquals(Team.MYSTIC, trainer.getTeam());
        assertNotNull(trainer.getPokedex()); // Vérifie que le Pokédex associé n'est pas nul
    }
}
