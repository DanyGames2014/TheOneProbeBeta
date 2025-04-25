package net.danygames2014.whatsthis.apiimpl.providers.entity;

import net.danygames2014.whatsthis.Util;
import net.danygames2014.whatsthis.WhatsThis;
import net.danygames2014.whatsthis.api.*;
import net.danygames2014.whatsthis.config.ConfigSetup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import java.text.DecimalFormat;

import static net.danygames2014.whatsthis.api.TextStyleClass.*;

public class DefaultProbeInfoEntityProvider implements IProbeInfoEntityProvider {

    @Override
    public String getID() {
        return WhatsThis.NAMESPACE.id("entity_default").toString();
    }

    private static DecimalFormat dfCommas = new DecimalFormat("##.#");
    private static final String[] SHEEP_COLORS = new String[]{"White", "Orange", "Magenta", "Light Blue", "Yellow", "Lime", "Pink", "Gray", "Light Gray", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black"};

    @Override
    public void addProbeEntityInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world, Entity entity, IProbeHitEntityData data) {
        IProbeConfig config = ConfigSetup.getProbeConfig();

        boolean handled = false;
        for (IEntityDisplayOverride override : WhatsThis.theOneProbeImp.getEntityOverrides()) {
            if (override.overrideStandardInfo(mode, probeInfo, player, world, entity, data)) {
                handled = true;
                break;
            }
        }

        if (!handled) {
            showStandardInfo(mode, probeInfo, entity, config);
        }

        if (entity instanceof LivingEntity livingEntity) {
            if (Util.show(mode, config.getShowMobHealth())) {
                int health = livingEntity.health;
                int maxHealth = livingEntity.maxHealth;

                probeInfo.progress(health, maxHealth, probeInfo.defaultProgressStyle().lifeBar(true).showText(false).width(150).height(10));

                if (mode == ProbeMode.EXTENDED) {
                    probeInfo.text(LABEL + "Health: " + INFOIMP + health + " / " + maxHealth);
                }

                if (entity instanceof PlayerEntity playerEntity) {
                    if (playerEntity.inventory != null) {
                        int armor = playerEntity.inventory.getTotalArmorDurability();

                        if (armor > 0) {
                            probeInfo.progress(armor, armor, probeInfo.defaultProgressStyle().armorBar(true).showText(false).width(80).height(10));
                        }
                    }
                }
            }

            // TODO: Effects API Integration
//            if (Tools.show(mode, config.getShowMobPotionEffects())) {
//                Collection<PotionEffect> effects = livingBase.getActivePotionEffects();
//                if (!effects.isEmpty()) {
//                    IProbeInfo vertical = probeInfo.vertical(probeInfo.defaultLayoutStyle().borderColor(0xffffffff));
//                    float durationFactor = 1.0f;
//                    for (PotionEffect effect : effects) {
//                        String s1 = STARTLOC + effect.getEffectName() + ENDLOC;
//                        Potion potion = effect.getPotion();
//                        if (effect.getAmplifier() > 0) {
//                            s1 = s1 + " " + STARTLOC + "potion.potency." + effect.getAmplifier() + ENDLOC;
//                        }
//
//                        if (effect.getDuration() > 20) {
//                            s1 = s1 + " (" + getPotionDurationString(effect, durationFactor) + ")";
//                        }
//
//                        if (potion.isBadEffect()) {
//                            vertical.text(ERROR + s1);
//                        } else {
//                            vertical.text(OK + s1);
//                        }
//                    }
//                }
//            }

        }

        if (entity instanceof PaintingEntity painting) {
            probeInfo.text(LABEL + "Variant: " + INFO + painting.variant.id);
        }

        if (entity instanceof SheepEntity sheep) {
            int sheepColor = sheep.getColor();

            if (sheepColor + 1 <= SHEEP_COLORS.length) {
                probeInfo.text(LABEL + "Wool Color: " + INFO + SHEEP_COLORS[sheepColor]);
            }
        }

        if (Util.show(mode, config.getAnimalOwnerSetting())) {
            if (entity instanceof WolfEntity wolf) {

                if (wolf.isTamed()) {
                    String ownerName = wolf.getOwnerName();

                    if (ownerName == null || ownerName.isEmpty()) {
                        probeInfo.text(WARNING + "Unknown owner");
                    } else {
                        probeInfo.text(LABEL + "Owned by: " + INFO + ownerName);
                    }
                } else {
                    probeInfo.text(LABEL + "Tameable");
                }

            }
        }
    }
    
//    public static String getPotionDurationString(PotionEffect effect, float factor) {
//        if (effect.getDuration() == 32767) {
//            return "**:**";
//        } else {
//            int i = MathHelper.floor(effect.getDuration() * factor);
//            return ticksToElapsedTime(i);
//        }
//    }

    public static String ticksToElapsedTime(int ticks) {
        int i = ticks / 20;
        int j = i / 60;
        i = i % 60;
        return i < 10 ? j + ":0" + i : j + ":" + i;
    }


    public static void showStandardInfo(ProbeMode mode, IProbeInfo probeInfo, Entity entity, IProbeConfig config) {
        String modid = Util.getModName(entity);

        if (Util.show(mode, config.getShowModName())) {
            probeInfo.horizontal()
                    .entity(entity)
                    .vertical()
                    .text(NAME + EntityRegistry.getId(entity))
                    // TODO : Proper Entity Names
                    .text(MODNAME + modid);
        } else {
            probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
                    .entity(entity)
                    .text(NAME + EntityRegistry.getId(entity));
        }
    }
}
