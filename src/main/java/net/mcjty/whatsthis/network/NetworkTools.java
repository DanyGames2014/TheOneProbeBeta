package net.mcjty.whatsthis.network;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.math.BlockPos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Collection;

public class NetworkTools {

    public static NbtCompound readNBT(DataInputStream dataIn) {
//        PacketBuffer buf = new PacketBuffer(dataIn);
//        try {
//            return buf.readCompoundTag();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return null;
    }

    public static void writeNBT(DataOutputStream dataOut, NbtCompound nbt) {
//        PacketBuffer buf = new PacketBuffer(dataOut);
//        try {
//            buf.writeCompoundTag(nbt);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }


    /// This function supports itemstacks with more then 64 items.
    public static ItemStack readItemStack(DataInputStream dataIn) {
//        PacketBuffer buf = new PacketBuffer(dataIn);
//        try {
//            NBTTagCompound nbt = buf.readCompoundTag();
//            ItemStack stack = new ItemStack(nbt);
//            stack.setCount(buf.readInt());
//            return stack;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return null;
    }

    /// This function supports itemstacks with more then 64 items.
    public static void writeItemStack(DataOutputStream dataOut, ItemStack itemStack) {
//        PacketBuffer buf = new PacketBuffer(dataOut);
//        NBTTagCompound nbt = new NBTTagCompound();
//        itemStack.writeToNBT(nbt);
//        try {
//            buf.writeCompoundTag(nbt);
//            buf.writeInt(itemStack.getCount());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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
        dataIn.read(dst);
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
