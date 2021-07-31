package com.blamejared.createtweaker.managers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredientWithAmount;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.item.MCWeightedItemStack;
import com.blamejared.createtweaker.managers.base.IProcessingRecipeManager;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.components.press.PressingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingOutput;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ZenRegister
@ZenCodeType.Name("mods.create.PressingManager")
public class PressingManager implements IProcessingRecipeManager<PressingRecipe> {
    
    @ZenCodeType.Method
    public ProcessingRecipeBuilder.ProcessingRecipeFactory factory() {
        
        return processingRecipeParams -> getSerializer().getFactory().create(processingRecipeParams);
    }
    
    @ZenCodeType.Method
    public void addRecipe(String name, MCWeightedItemStack[] output, IIngredientWithAmount input, @ZenCodeType.OptionalInt(100) int duration) {
        
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        ProcessingRecipeBuilder<PressingRecipe> builder = new ProcessingRecipeBuilder<>(((ProcessingRecipeSerializer<PressingRecipe>) AllRecipeTypes.PRESSING.serializer)
                .getFactory(), resourceLocation);
        builder.withItemOutputs(Arrays.stream(output)
                .map(mcWeightedItemStack -> new ProcessingOutput(mcWeightedItemStack.getItemStack()
                        .getInternal(), (float) mcWeightedItemStack.getWeight()))
                .toArray(ProcessingOutput[]::new));
        
        List<Ingredient> ingredients = new ArrayList<>();
        for(int i = 0; i < input.getAmount(); i++) {
            ingredients.add(input.getIngredient()
                    .asVanillaIngredient());
        }
        builder.withItemIngredients(ingredients.toArray(new Ingredient[0]));
        
        builder.duration(duration);
        PressingRecipe recipe = builder.build();
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe, ""));
        
    }
    
    @Override
    public AllRecipeTypes getCreateRecipeType() {
        
        return AllRecipeTypes.PRESSING;
    }
    
}
