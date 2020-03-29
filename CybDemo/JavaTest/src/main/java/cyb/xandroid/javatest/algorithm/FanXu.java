package cyb.xandroid.javatest.algorithm;

public class FanXu {

    static class Node {
        final String value;
        Node next;

        public Node(String v) {
            value = v;
        }

        public void printValue() {
            System.out.print(value + " ");
        }
    }


    public static void main(String[] args) {
        Node node = initLink();
        printNode(node);
        System.out.println(" ");
        node = revserve(node, 3);
        printNode(node);
    }

    public static void printNode(Node node) {
        while (node != null) {
            node.printValue();
            node = node.next;
        }
    }

    public static Node initLink() {
        Node nodeA = new Node("a");
        Node nodeB = new Node("b");
        Node nodeC = new Node("c");
        Node nodeD = new Node("d");
        Node nodeE = new Node("e");
        Node nodeF = new Node("f");
        nodeA.next = nodeB;
        nodeB.next = nodeC;
        nodeC.next = nodeD;
        nodeD.next = nodeE;
        nodeE.next = nodeF;
        return nodeA;
    }


    /**
     *
     */
    public static Node revserve(Node head, int lent) {
        Node start = head;
        Node end = head;
        Node pre = null;
        Node newHead = null;
        int i = 0;
        while (end != null && end.next != null) {
            end = end.next;
            i++;
            if (i % (lent - 1) == 0) {
                if(newHead == null){
                    newHead = end;
                }
                //记录的
                Node next1 = end.next;
                if (pre != null) {
                    pre.next = end;
                }
                // 反序，返回尾部节点
                pre = revserve(start, end);
                //反序后连接后面的字符串
                pre.next = next1;
                start = next1;
                end = next1;
            }
        }
        return newHead;
    }

    /**
     * 反序，返回尾部节点
     */
    public static Node revserve(Node head, Node end) {
        if (head.next != end) {
            end = revserve(head.next, end);
        }

        end.next = head;
        head.next = null;

        return head;
    }


}
