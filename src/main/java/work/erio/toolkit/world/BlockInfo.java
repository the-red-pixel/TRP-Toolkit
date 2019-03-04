package work.erio.toolkit.world;

public class BlockInfo {
    private short id;
    private byte meta;

    public BlockInfo(short id, byte meta) {
        this.id = id;
        this.meta = meta;
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public byte getMeta() {
        return meta;
    }

    public void setMeta(byte meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "BlockInfo{" +
                "id=" + id +
                ", meta=" + meta +
                '}';
    }
}
