package net.esquizo.children_of_silence_mod.magic.spells.spells;

import net.esquizo.children_of_silence_mod.magic.spells.Spell;
import net.esquizo.children_of_silence_mod.utils.MagicUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FireBall extends Spell {
    public FireBall() {
        setValues(1, 5, 100);
    }

    @Override
    public void useSpell(Player player) {
        Level level = player.level();
        if(level instanceof ServerLevel serverLevel) {
            if (canUse(player)) {
                MagicUtils.useMana(player, getManaCost());
                MagicUtils.setCooldown(player, this);

                Vec3 look = player.getLookAngle();

                LargeFireball fireball = new LargeFireball(serverLevel, player, look.x, look.y, look.z, 1);

                double x = player.getX() + look.x * 1.5;
                double y = player.getX() + player.getEyeHeight() - 0.1;
                double z = player.getX() + look.x * 1.5;
                fireball.setPos(player.getX(), player.getY(), player.getZ());

                serverLevel.addFreshEntity(fireball);
            }
        }
    }
}
