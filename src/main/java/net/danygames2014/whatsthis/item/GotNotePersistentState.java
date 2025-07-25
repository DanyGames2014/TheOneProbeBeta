package net.danygames2014.whatsthis.item;

import net.danygames2014.whatsthis.WhatsThis;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;

import java.util.ArrayList;

public class GotNotePersistentState extends PersistentState {
    public ArrayList<String> gotNote;

    public GotNotePersistentState(String id) {
        super(id);
        gotNote = new ArrayList<>();
    }

    public static GotNotePersistentState get(World world, String id) {
        GotNotePersistentState gotNoteState = (GotNotePersistentState) world.persistentStateManager.getOrCreate(GotNotePersistentState.class, id);

        if (gotNoteState == null) {
            gotNoteState = new GotNotePersistentState(id);
            world.persistentStateManager.set(id, gotNoteState);
        }

        return gotNoteState;
    }

    public boolean gotNote(String name) {
        if (name == null || name.isBlank()) {
            WhatsThis.LOGGER.warn("Tried getting got_note state for blank username");
            return false;
        }
        
        return gotNote.contains(name);
    }

    public void setGotNote(String name, boolean value) {
        if (name == null || name.isBlank()) {
            WhatsThis.LOGGER.warn("Tried setting got_note state for blank username");
            return;
        }
        
        if (value && !gotNote.contains(name)) {
            gotNote.add(name);
        } else {
            gotNote.remove(name);
        }
        this.markDirty();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        NbtList nbtList = nbt.getList("gotNote");

        for (int i = 0; i < nbtList.size(); i++) {
            gotNote.add(nbtList.get(i).toString());
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        NbtList nbtList = new NbtList();

        for (String player : gotNote) {
            if (player.isBlank()) {
                continue;
            }
            nbtList.add(new NbtString(player));
        }

        nbt.put("gotNote", nbtList);
    }
}
