public class LongList extends MyList {
    long value;
    LongList next;

    public LongList(LongList n, long data) {
        this.value = data;
        this.next = n;
    }

    public long getData() {
        return this.value;
    }

    public LongList next() {
        return this.next;
    }

    public void printNode() {
        System.out.println("LongList Node, data is: " + this.getData());
    }
}
