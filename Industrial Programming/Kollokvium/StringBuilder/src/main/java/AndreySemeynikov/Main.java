package AndreySemeynikov;

public class Main {
    public static void main(String[] args) {

        UndoableStringBuilder builder = new UndoableStringBuilder();

        builder.append("Hello");
        System.out.println("After append: " + builder.toString());

        System.out.println("After delete: " + builder.toString());

        builder.undo();
        System.out.println("After undo: " + builder.toString());
    }
}