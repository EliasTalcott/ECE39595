public class IntList extends MyList {
    int value;
    IntList next;

    public IntList(IntList n, int data) {
        this.value = data;
        this.next = n;
    }

    public int getData() {
        return this.value;
    }

    public IntList next() {
        return this.next;
    }

    public void printNode() {
        System.out.println("IntList Node, data is: " + this.getData());
    }
}
