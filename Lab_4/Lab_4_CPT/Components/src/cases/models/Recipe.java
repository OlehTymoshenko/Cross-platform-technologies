package cases.models;

public class Recipe {
    public String name;

    public String recipeText;

    public Recipe(String name, String recipeText) {
        this.name = name;
        this.recipeText = recipeText;
    }

    @Override
    public String toString() {
        return "Recipe - " + name + "\n" + recipeText;
    }
}
