package net.esquizo.children_of_silence_mod.events;

import net.esquizo.children_of_silence_mod.ChildrenOfSilence;
import net.esquizo.children_of_silence_mod.Init.ItemInit;
import net.esquizo.children_of_silence_mod.items.draken.StingOfSacrament;
import net.esquizo.children_of_silence_mod.utils.MagicUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChildrenOfSilence.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event){
        Entity attacker = event.getSource().getEntity();
        LivingEntity attacked = event.getEntity();
        float damage = event.getAmount();

        if (attacker != null){
            if (attacker instanceof LivingEntity livingAttacker){
                if (livingAttacker.getItemInHand(InteractionHand.MAIN_HAND).getItem() == ItemInit.STING_OF_SACRAMENT.get()){
                    event.setAmount(StingOfSacrament.stingDamage(damage, attacked, livingAttacker));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        Player player = event.player;
        Level level = player.level();
        if(!level.isClientSide) {
            if (event.phase != TickEvent.Phase.END) return;
            MagicUtils.manaTick(player);
        }
    }
}