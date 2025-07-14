package net.danygames2014.whatsthis;

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
        return gotNote.contains(name);
    }

    public void setGotNote(String name, boolean value) {
        if (value && !gotNote.contains(name)) {
            gotNote.add(name);
        } else {
            gotNote.remove(name);
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        NbtList nbtList = nbt.getList("gotNote");

        for (int i = 0; i < nbtList.size(); i++) {
            gotNote.add(nbtList.get(i).getKey());
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        NbtList nbtList = new NbtList();

        for (String player : gotNote) {
            nbtList.add(new NbtString(player));
        }

        nbt.put("gotNote", nbtList);
    }
}
