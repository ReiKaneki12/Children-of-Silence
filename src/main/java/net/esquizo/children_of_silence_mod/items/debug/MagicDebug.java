package net.esquizo.children_of_silence_mod.items.debug;

import net.esquizo.children_of_silence_mod.utils.MagicUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MagicDebug extends Item {
    public MagicDebug(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(!pLevel.isClientSide){
            if(pPlayer.isShiftKeyDown()){
                if(MagicUtils.getManaRegenRate(pPlayer) < 50) {
                    MagicUtils.setManaRegenRate(pPlayer, MagicUtils.getManaRegenRate(pPlayer) + 5);
                }else{
                    MagicUtils.setManaRegenRate(pPlayer, 5);
                }
                pPlayer.displayClientMessage(Component.literal("Mana regen set to " + MagicUtils.getManaRegenRate(pPlayer)), false);
            }else{
                MagicUtils.useMana(pPlayer, 5f);
                pPlayer.displayClientMessage(Component.literal("Mana decreased by 5"), false);
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
