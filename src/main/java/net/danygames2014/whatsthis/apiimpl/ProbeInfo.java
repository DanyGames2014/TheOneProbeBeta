package net.danygames2014.whatsthis.apiimpl;

import net.danygames2014.whatsthis.WhatsThis;
import net.danygames2014.whatsthis.api.ElementAlignment;
import net.danygames2014.whatsthis.api.IElement;
import net.danygames2014.whatsthis.api.IElementFactory;
import net.danygames2014.whatsthis.apiimpl.elements.ElementVertical;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProbeInfo extends ElementVertical {

    public List<IElement> getElements() {
        return children;
    }

    public ProbeInfo() {
        super(null, 2, ElementAlignment.ALIGN_TOPLEFT);
    }

    public ProbeInfo(DataInputStream stream) {
        super(stream);
    }

    public static List<IElement> createElements(DataInputStream stream) throws IOException {
        int size = stream.readShort();
        List<IElement> elements = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            int id = stream.readInt();
            IElementFactory factory = WhatsThis.theOneProbeImp.getElementFactory(id);
            IElement element = factory.createElement(stream);
            elements.add(element);
        }
        return elements;
    }

    public static void writeElements(List<IElement> elements, DataOutputStream buf) throws IOException {
        buf.writeShort(elements.size());
        for (IElement element : elements) {
            buf.writeInt(element.getID());
            element.toBytes(buf);
        }
    }

    public void removeElement(IElement element) {
        this.getElements().remove(element);
    }
}
