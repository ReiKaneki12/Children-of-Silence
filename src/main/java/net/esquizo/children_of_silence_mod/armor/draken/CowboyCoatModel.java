package net.esquizo.children_of_silence_mod.armor.draken;

import net.esquizo.children_of_silence_mod.ChildrenOfSilence;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class CowboyCoatModel extends GeoModel<CowboyCoatItem> {
    @Override
    public ResourceLocation getModelResource(CowboyCoatItem animatable) {
        return new ResourceLocation(ChildrenOfSilence.MOD_ID, "geo/draken_coat.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CowboyCoatItem animatable) {
        return new ResourceLocation(ChildrenOfSilence.MOD_ID, "textures/armor/draken_coat.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CowboyCoatItem animatable) {
        return new ResourceLocation(ChildrenOfSilence.MOD_ID, "animations/draken_coat.animation.json");
    }
}
