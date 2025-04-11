package net.mcjty.whatsthis.items;

import net.mcjty.whatsthis.WhatsThis;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class ModItems {
//    public static CreativeProbe creativeProbe;
//    public static Probe probe;
//    public static Item diamondHelmetProbe;
//    public static Item goldHelmetProbe;
//    public static Item ironHelmetProbe;
//    public static Item probeGoggles;
//    public static ProbeNote probeNote;

    public static String PROBETAG = "theoneprobe";
    public static String PROBETAG_HAND = "theoneprobe_hand";

//    public static void init() {
//        probe = new Probe();
//        creativeProbe = new CreativeProbe();
//
//        ItemArmor.ArmorMaterial materialDiamondHelmet = EnumHelper.addArmorMaterial("diamond_helmet_probe", TheOneProbe.MODID + ":probe_diamond",
//                33, new int[]{3, 6, 8, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F);
//        ItemArmor.ArmorMaterial materialGoldHelmet = EnumHelper.addArmorMaterial("gold_helmet_probe", TheOneProbe.MODID + ":probe_gold",
//                7, new int[]{1, 3, 5, 2}, 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0F);
//        ItemArmor.ArmorMaterial materialIronHelmet = EnumHelper.addArmorMaterial("iron_helmet_probe", TheOneProbe.MODID + ":probe_iron",
//                15, new int[]{2, 5, 6, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
//
//        diamondHelmetProbe = makeHelmet(materialDiamondHelmet, 3, "diamond_helmet_probe");
//        goldHelmetProbe = makeHelmet(materialGoldHelmet, 4, "gold_helmet_probe");
//        ironHelmetProbe = makeHelmet(materialIronHelmet, 2, "iron_helmet_probe");
//
//        probeNote = new ProbeNote();
//
//        if (ModSetup.baubles) {
//            probeGoggles = BaubleTools.initProbeGoggle();
//        }
//    }
//
//    private static Item makeHelmet(ItemArmor.ArmorMaterial material, int renderIndex, String name) {
//        Item item = new ItemArmor(material, renderIndex, EntityEquipmentSlot.HEAD) {
//            @Override
//            public boolean getHasSubtypes() {
//                return true;
//            }
//
//            @Override
//            public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
//                if (this.isInCreativeTab(tab)) {
//                    ItemStack stack = new ItemStack(this);
//                    NBTTagCompound tag = new NBTTagCompound();
//                    tag.setInteger(PROBETAG, 1);
//                    stack.setTagCompound(tag);
//                    subItems.add(stack);
//                }
//            }
//        };
//        item.setUnlocalizedName(TheOneProbe.MODID + "." + name);
//        item.setRegistryName(name);
//        item.setCreativeTab(TheOneProbe.tabProbe);
//        return item;
//    }
//
//    @SideOnly(Side.CLIENT)
//    public static void initClient() {
//        probe.initModel();
//        creativeProbe.initModel();
//
//        ModelLoader.setCustomModelResourceLocation(diamondHelmetProbe, 0, new ModelResourceLocation(diamondHelmetProbe.getRegistryName(), "inventory"));
//        ModelLoader.setCustomModelResourceLocation(goldHelmetProbe, 0, new ModelResourceLocation(goldHelmetProbe.getRegistryName(), "inventory"));
//        ModelLoader.setCustomModelResourceLocation(ironHelmetProbe, 0, new ModelResourceLocation(ironHelmetProbe.getRegistryName(), "inventory"));
//
//        probeNote.initModel();
//
//        if (ModSetup.baubles) {
//            BaubleTools.initProbeModel(probeGoggles);
//        }
//    }

    public static boolean isProbeInHand(ItemStack stack) {
        if (stack == null || stack.count <= 0) {
            return false;
        }
        
        if (stack.isOf(WhatsThis.probe) || stack.isOf(WhatsThis.creativeProbe)) {
            return true;
        }
        
        if (stack.getStationNbt() == null) {
            return false;
        }
        return stack.getStationNbt().contains(PROBETAG_HAND);
    }

    private static boolean isProbeHelmet(ItemStack stack) {
        if (stack == null || stack.count <= 0) {
            return false;
        }

        if (stack.getStationNbt() == null) {
            return false;
        }

        return stack.getStationNbt().contains(PROBETAG);
    }

    public static boolean hasAProbeSomewhere(PlayerEntity player) {
        return hasProbeInHand(player) || hasProbeInHelmet(player) || hasProbeInBauble(player);
    }

    private static boolean hasProbeInHand(PlayerEntity player) {
        return isProbeInHand(player.getHand());
    }

    private static boolean hasProbeInHelmet(PlayerEntity player) {
        return isProbeHelmet(player.inventory.armor[3]);
    }

    private static boolean hasProbeInBauble(PlayerEntity player) {
        return false;
//        if (ModSetup.baubles) {
//            return BaubleTools.hasProbeGoggle(player);
//        } else {
//            return false;
//        }
    }
}
