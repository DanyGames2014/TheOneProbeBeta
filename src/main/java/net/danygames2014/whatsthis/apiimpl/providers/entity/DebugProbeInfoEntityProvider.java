package net.danygames2014.whatsthis.apiimpl.providers.entity;

import net.danygames2014.whatsthis.WhatsThis;
import net.danygames2014.whatsthis.api.IProbeHitEntityData;
import net.danygames2014.whatsthis.api.IProbeInfo;
import net.danygames2014.whatsthis.api.IProbeInfoEntityProvider;
import net.danygames2014.whatsthis.api.ProbeMode;
import net.danygames2014.whatsthis.apiimpl.styles.LayoutStyle;
import net.danygames2014.whatsthis.config.Config;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import static net.danygames2014.whatsthis.api.TextStyleClass.INFO;
import static net.danygames2014.whatsthis.api.TextStyleClass.LABEL;


public class DebugProbeInfoEntityProvider implements IProbeInfoEntityProvider {

    @Override
    public String getID() {
        return WhatsThis.NAMESPACE.id("entity_debug").toString();
    }

    @Override
    public void addProbeEntityInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world, Entity entity, IProbeHitEntityData data) {
        if (mode == ProbeMode.DEBUG && Config.MAIN_CONFIG.showDebugInfo) {
            IProbeInfo vertical = probeInfo.vertical(new LayoutStyle().borderColor(0xffff4444).spacing(2));

            // Entity ID Info
            String registryId = EntityRegistry.getId(entity);
            int entityId = entity.id;

            vertical
                    .text(LABEL + "Registry ID: " + INFO + registryId)
                    .text(LABEL + "Entity ID: " + INFO + entityId)
            ;

            // Living Entity Info
            if (entity instanceof LivingEntity livingEntity) {
                int health = livingEntity.health;
                int maxHealth = livingEntity.maxHealth;
                float damageAmount = livingEntity.damageAmount;

                vertical
                        .text(LABEL + "Health: " + health + " / " + maxHealth)
                        .text(LABEL + "Damage: " + damageAmount)
                ;
            }

            // Other Info
            int age = entity.age;
            float width = entity.width;
            float height = entity.height;
            int fireTicks = entity.fireTicks;
            int air = entity.air;
            int maxAir = entity.maxAir;

            vertical
                    .text(LABEL + "Age: " + INFO + age)
                    .text(LABEL + "Width: " + INFO + width)
                    .text(LABEL + "Height: " + INFO + height)
                    .text(LABEL + "FireTicks: " + INFO + fireTicks)
                    .text(LABEL + "Air: " + INFO + air + " / " + maxAir)
            ;
        }
    }
}
