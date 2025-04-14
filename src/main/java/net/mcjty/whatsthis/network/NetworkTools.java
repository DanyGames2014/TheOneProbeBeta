package net.mcjty.whatsthis.network;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class NetworkTools {

    public static NbtCompound readNBT(DataInputStream stream) {
        return (NbtCompound) NbtElement.readTag(stream);
    }

    public static void writeNBT(DataOutputStream stream, NbtCompound nbt) {
        NbtElement.writeTag(nbt, stream);
    }


    /// This function supports itemstacks with more then 64 items.
    public static ItemStack readItemStack(DataInputStream stream) throws IOException {
        return new ItemStack(stream.readShort(), stream.readShort(), stream.readShort());
    }

    /// This function supports itemstacks with more then 64 items.
    public static void writeItemStack(DataOutputStream stream, ItemStack stack) throws IOException {
        stream.writeShort(stack.itemId);
        stream.writeShort(stack.count);
        stream.writeShort(stack.getDamage());
    }

    public static String readString(DataInputStream dataIn) throws IOException {
        int s = dataIn.readInt();
        if (s == -1) {
            return null;
        }
        if (s == 0) {
            return "";
        }
        byte[] dst = new byte[s];
        dataIn.readFully(dst);
        return new String(dst);
    }

    public static void writeString(DataOutputStream dataOut, String str) throws IOException {
        if (str == null) {
            dataOut.writeInt(-1);
            return;
        }
        byte[] bytes = str.getBytes();
        dataOut.writeInt(bytes.length);
        if (bytes.length > 0) {
            dataOut.write(bytes);
        }
    }

    public static String readStringUTF8(DataInputStream dataIn) throws IOException {
        int s = dataIn.readInt();
        if (s == -1) {
            return null;
        }
        if (s == 0) {
            return "";
        }
        byte[] dst = new byte[s];
        dataIn.read(dst);
        return new String(dst, java.nio.charset.StandardCharsets.UTF_8);
    }

    public static void writeStringUTF8(DataOutputStream dataOut, String str) throws IOException {
        if (str == null) {
            dataOut.writeInt(-1);
            return;
        }
        byte[] bytes = str.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        dataOut.writeInt(bytes.length);
        if (bytes.length > 0) {
            dataOut.write(bytes);
        }
    }

    public static int getNbtLength(NbtElement element) {
        // Semi-wasteful, but better than an actual output stream.
        return writeAndGetNbtLength(element, DataOutputStream.nullOutputStream());
    }

    public static int writeAndGetNbtLength(NbtElement element, OutputStream dataOutput) {
        DataOutputStream outputStream = new DataOutputStream(dataOutput);
        element.write(outputStream);
        try {
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return outputStream.size();
    }
}
