package fr.univavignon.pokedex.api;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class IPokemonTrainerFactoryTest {

    private IPokemonTrainerFactory pokemonTrainerFactory;
    private IPokedexFactory pokedexFactory;
    private IPokedex pokedex;

    @Before
    public void setUp() {
        // Initialisation des mocks
        pokedexFactory = mock(IPokedexFactory.class);
        pokemonTrainerFactory = mock(IPokemonTrainerFactory.class);
        pokedex = mock(IPokedex.class);
    }

    @Test
    public void testCreateTrainer() {
        // Configuration des données pour le test
        String trainerName = "Margot";
        Team team = Team.VALOR;

        // Création d'un objet Pokémon Trainer simulé
        PokemonTrainer expectedTrainer = new PokemonTrainer(trainerName, team, pokedex);

        // Définition du comportement du mock pour la méthode createTrainer
        when(pokemonTrainerFactory.createTrainer(trainerName, team, pokedexFactory)).thenReturn(expectedTrainer);

        // Appel de la méthode à tester
        PokemonTrainer actualTrainer = pokemonTrainerFactory.createTrainer(trainerName, team, pokedexFactory);

        // Vérifications sur les résultats
        assertNotNull("Le dresseur ne doit pas être null", actualTrainer);
        assertEquals("Le nom du dresseur est incorrect", trainerName, actualTrainer.getName());
        assertEquals("L'équipe du dresseur est incorrecte", team, actualTrainer.getTeam());
        assertNotNull("Le Pokédex du dresseur ne doit pas être null", actualTrainer.getPokedex());
    }
}
