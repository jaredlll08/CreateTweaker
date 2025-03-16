package com.blamejared.createtweaker.recipe.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredientWithAmount;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.createtweaker.recipe.manager.base.IProcessingRecipeManager;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @docParam this <recipetype:create:pressing>
 */
@ZenRegister
@ZenCodeType.Name("mods.createtweaker.PressingManager")
@Document("mods/CreateTweaker/PressingManager")
public class PressingManager implements IProcessingRecipeManager<PressingRecipe> {
    
    /**
     * Adds a pressing recipe.
     *
     * @param name     The name of the recipe.
     * @param outputs  The outputs of the recipe.
     * @param input    The input of the recipe.
     * @param duration The duration of the recipe (default 100 ticks).
     *
     * @docParam name "pressed"
     * @docParam outputs [<item:minecraft:diamond> % 50, <item:minecraft:apple>, (<item:minecraft:dirt> * 2) % 12]
     * @docParam input <item:minecraft:dirt>
     * @docParam duration 200
     */
    @ZenCodeType.Method
    public void addRecipe(String name, Percentaged<IItemStack>[] outputs, IIngredientWithAmount input, @ZenCodeType.OptionalInt(100) int duration) {
        
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        ProcessingRecipeBuilder<PressingRecipe> builder = new ProcessingRecipeBuilder<>(getSerializer().getFactory(), resourceLocation);
        builder.withItemOutputs(Arrays.stream(outputs)
                .map(mcWeightedItemStack -> new ProcessingOutput(mcWeightedItemStack.getData()
                        .getInternal(), (float) mcWeightedItemStack.getPercentage()))
                .toArray(ProcessingOutput[]::new));
        
        List<Ingredient> ingredients = new ArrayList<>();
        for(int i = 0; i < input.getAmount(); i++) {
            ingredients.add(input.getIngredient()
                    .asVanillaIngredient());
        }
        builder.withItemIngredients(ingredients.toArray(new Ingredient[0]));
        
        builder.duration(duration);
        PressingRecipe recipe = builder.build();
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, recipe));
        
    }
    
    @Override
    public AllRecipeTypes getCreateRecipeType() {
        
        return AllRecipeTypes.PRESSING;
    }
    
}
