package com.blamejared.createtweaker.managers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.itemAssembly.SequencedAssemblyRecipe;
import com.simibubi.create.content.contraptions.itemAssembly.SequencedAssemblyRecipeBuilder;
import com.simibubi.create.content.contraptions.itemAssembly.SequencedAssemblyRecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

/**
 * @docParam this <recipetype:create:sequenced_assembly>
 */
@ZenRegister
@ZenCodeType.Name("mods.create.SequencedAssemblyManager")
@Document("mods/createtweaker/SequencedAssemblyManager")
public class SequencedAssemblyManager implements IRecipeManager<SequencedAssemblyRecipe> {
    
    /**
     * Gets a sequenced assembly recipe builder.
     *
     * @param name The name of the recipe.
     *
     * @return A builder used to make sequenced assembly recipes.
     *
     * @docParam name "sequenced"
     */
    @ZenCodeType.Method
    public SequencedAssemblyRecipeBuilder builder(String name) {
        
        return new SequencedAssemblyRecipeBuilder(new ResourceLocation("crafttweaker", name));
    }
    
    /**
     * Registers a recipe with the given name and is built by the consumer.
     *
     * @param name          The name of the recipe.
     * @param recipeBuilder A consumer that builds the recipe.
     *
     * @docParam name "build_sequence"
     * @docParam recipeBuilder (rb) => {
     * rb.transitionTo(<item:minecraft:glass>);
     * rb.require(<item:minecraft:arrow>);
     * rb.loops(2);
     * rb.addOutput(<item:minecraft:diamond>, 1);
     * rb.addOutput(<item:minecraft:apple>, 1);
     * rb.addStep<mods.createtweaker.CuttingRecipe>((rb1) => rb1.duration(50));
     * rb.addStep<mods.createtweaker.PressingRecipe>((rb1) => rb1.duration(500));
     * rb.addStep<mods.createtweaker.DeployerApplicationRecipe>((rb1) => rb1.require(<item:minecraft:dirt>));
     * rb.addStep<mods.createtweaker.FillingRecipe>((rb1) => rb.require(<fluid:minecraft:water> * 50))
     * }
     */
    @ZenCodeType.Method
    public void registerRecipe(String name, Consumer<SequencedAssemblyRecipeBuilder> recipeBuilder) {
        
        name = fixRecipeName(name);
        ResourceLocation recipeId = new ResourceLocation("crafttweaker", name);
        SequencedAssemblyRecipeBuilder builder = new SequencedAssemblyRecipeBuilder(recipeId);
        recipeBuilder.accept(builder);
        addInternal(builder);
    }
    
    
    /**
     * Adds the recipe that the builder built.
     *
     * @param builder The buidler that defines the recipe.
     *
     * @docParam builder <recipetype:create:sequenced_assembly>.builder("seq_blast_brick")
     *                                                      .transitionTo(<item:create:incomplete_large_cogwheel>)
     *                                                      .require(<item:create:andesite_alloy>)
     *                                                      .loops(3)
     *                                                      .addOutput(<item:create:large_cogwheel> * 6, 32)
     *                                                      .addOutput(<item:create:andesite_alloy>, 2)
     *                                                      .addOutput(<item:minecraft:andesite>, 1)
     *                                                      .addOutput(<item:create:cogwheel>, 1)
     *                                                      .addOutput(<item:minecraft:stick>, 1)
     *                                                      .addOutput(<item:minecraft:iron_nugget>, 1)
     *                                                      .addStep<mods.createtweaker.DeployerApplicationRecipe>((rb) => rb.require(<tag:items:minecraft:planks>))
     *                                                      .addStep<mods.createtweaker.DeployerApplicationRecipe>((rb) => rb.require(<tag:items:minecraft:wooden_buttons>))
     *                                                      .addStep<mods.createtweaker.CuttingRecipe>((rb) => rb.duration(50))
     *                                                      .addStep<mods.createtweaker.FillingRecipe>((rb) => rb.require(<fluid:minecraft:water> * 50))
     */
    @ZenCodeType.Method
    public void addRecipe(SequencedAssemblyRecipeBuilder builder) {
        
        addInternal(builder);
    }
    
    private void addInternal(SequencedAssemblyRecipeBuilder builder) {
        
        SequencedAssemblyRecipe recipe = builder.build();
        precheck(recipe);
        builder.build(iFinishedRecipe -> {
            SequencedAssemblyRecipe seqRecipe = (SequencedAssemblyRecipe) iFinishedRecipe.getType()
                    .fromJson(new ResourceLocation("crafttweaker", iFinishedRecipe.getId()
                            .getPath()), iFinishedRecipe.serializeRecipe());
            
            CraftTweakerAPI.apply(new ActionAddRecipe<>(this, seqRecipe));
        });
    }
    
    private void precheck(SequencedAssemblyRecipe recipe) {
        
        if(recipe.getTransitionalItem().isEmpty()) {
            throw new IllegalArgumentException("Error while adding Sequenced Assembly Recipe! The `transitionTo` item is not provided or is air! transitionTo: " + recipe.getTransitionalItem());
        }
        try {
            if(recipe.getResultItem().isEmpty()) {
                throw new IllegalArgumentException("Error while adding Sequenced Assembly Recipe! The `output` is not provided or is air! output: " + recipe.getTransitionalItem());
            }
        } catch(IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Error while adding Sequenced Assembly Recipe! The `output` is not provided or is air! output: " + recipe.getTransitionalItem());
        }
        if(recipe.getIngredient() == null) {
            throw new IllegalArgumentException("Error while adding Sequenced Assembly Recipe! The `inputs` is not provided! inputs: " + recipe.getIngredient());
        }
        if(recipe.getSequence().isEmpty()) {
            throw new IllegalArgumentException("Error while adding Sequenced Assembly Recipe! No Steps have been provided!");
        }
    }
    
    @Override
    public RecipeType<SequencedAssemblyRecipe> getRecipeType() {
        
        return AllRecipeTypes.SEQUENCED_ASSEMBLY.getType();
    }
    
    
    public SequencedAssemblyRecipeSerializer getSerializer() {
        
        return AllRecipeTypes.SEQUENCED_ASSEMBLY.getSerializer();
    }
    
}
