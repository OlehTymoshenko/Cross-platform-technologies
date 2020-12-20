package cases;

import abstraction.Component;
import cases.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class Fridge extends Component
{
    private List<Recipe> recipesBook;
    private int chosenRecipe;

    public Fridge(int id, String name)
    {
        super(id, name);

        super.on();
        recipesBook = new ArrayList<Recipe>(3);
        recipesBook.add(new Recipe("Fried potatoes", "Ingredients: 1 kg potato, oil\n" +
                "Peel potato. Fry the potatoes for 20 minutes."));
        recipesBook.add(new Recipe("Oatmeal with milk", "Ingredients: 100 gr oatmeal, 100 ml milk, bananas, honey or smth else\n" +
                "Place all the ingredients into a medium microwave safe bowl and stir together.\n Heat in the microwave on high for 2 minutes."));
        recipesBook.add(new Recipe("Baked Brie Cranberry in Bread Bowl",
                "Ingredients: Bread boule, Cooking spray, Brie, Cranberry sauce, Thyme\n" +
                "Recipe: First, it’s important to get a round bread boule that is bigger than a standard brie wheel.\n" +
                        "and then a lot of text))"));

        chosenRecipe = 0; // chosen recipe index
    }

    public Recipe getChosenRecipe() { return recipesBook.get(chosenRecipe);}

    public int getChosenRecipeId()
    {
        return chosenRecipe;
    }

    public String getChosenRecipeName()
    {
        return recipesBook.get(chosenRecipe).name;
    }

    public String getRecipes()
    {
        if (!isTurnedOn()) return "";

        StringBuilder allRecipes = new StringBuilder();

        for (int i = 0; i < recipesBook.size(); i++)
        {
            allRecipes.append("id:").append(i).append(" | Recipe: ").append(recipesBook.get(i)).append("\n\n");
        }

        return allRecipes.toString();
    }

    public void chooseRecipe(int id)
    {
        if (!isTurnedOn()) return;

        if(id >= 0 && id < recipesBook.size()) {
            chosenRecipe = id;
        }
    }

    public String toString()
    {
        String selfDescription = "";
        if (isTurnedOn())
        {
            selfDescription = String.format("\nChosen recipe\n\t id: %d | name: %s\n\t",
                    getChosenRecipeId(), getChosenRecipeName());
        }

        return super.toString() + selfDescription;
    }

}
