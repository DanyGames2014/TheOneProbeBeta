package net.mcjty.whatsthis.apiimpl.elements;

import net.mcjty.whatsthis.api.IElement;
import net.mcjty.whatsthis.api.IEntityStyle;
import net.mcjty.whatsthis.apiimpl.TheOneProbeImp;
import net.mcjty.whatsthis.apiimpl.client.ElementEntityRender;
import net.mcjty.whatsthis.apiimpl.styles.EntityStyle;
import net.mcjty.whatsthis.network.NetworkTools;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ElementEntity implements IElement {

    private final String entityName;
    private final Integer playerID;
    private final NbtCompound entityNBT;
    private final IEntityStyle style;

    public ElementEntity(String entityName, IEntityStyle style) {
        this.entityName = entityName;
        this.entityNBT = new NbtCompound();
        this.style = style;
        this.playerID = null;
    }

    public ElementEntity(Entity entity, IEntityStyle style) {
        if (entity instanceof PlayerEntity player) {
            entityNBT = new NbtCompound();
            playerID = player.id;
        } else {
            entityNBT = new NbtCompound();
            entity.write(entityNBT);
            playerID = null;
        }
        this.entityName = EntityRegistry.getId(entity);
        this.style = style;
    }

    public ElementEntity(DataInputStream stream) throws IOException {
        entityName = NetworkTools.readString(stream);
        style = new EntityStyle()
                .width(stream.readInt())
                .height(stream.readInt())
                .scale(stream.readFloat());
        if (stream.readBoolean()) {
            entityNBT = NetworkTools.readNBT(stream);
        } else {
            entityNBT = null;
        }
        if (stream.readBoolean()) {
            playerID = stream.readInt();
        } else {
            playerID = null;
        }
    }

    @Override
    public void render(int x, int y) {
        if (playerID != null) {
            ElementEntityRender.renderPlayer(entityName, playerID, style, x, y);
        } else {
            ElementEntityRender.render(entityName, entityNBT, style, x, y);
        }
    }

    @Override
    public int getWidth() {
        return style.getWidth();
    }

    @Override
    public int getHeight() {
        return style.getHeight();
    }

    @Override
    public void toBytes(DataOutputStream stream) throws IOException {
        NetworkTools.writeString(stream, entityName);
        stream.writeInt(style.getWidth());
        stream.writeInt(style.getHeight());
        stream.writeFloat(style.getScale());
        if (entityNBT != null) {
            stream.writeBoolean(true);
            NetworkTools.writeNBT(stream, entityNBT);
        } else {
            stream.writeBoolean(false);
        }
        if (playerID != null) {
            stream.writeBoolean(true);
            stream.writeInt(playerID);
        } else {
            stream.writeBoolean(false);
        }
    }

    @Override
    public int getID() {
        return TheOneProbeImp.ELEMENT_ENTITY;
    }
}
