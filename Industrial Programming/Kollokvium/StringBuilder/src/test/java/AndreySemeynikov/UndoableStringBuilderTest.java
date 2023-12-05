package AndreySemeynikov;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UndoableStringBuilderTest {

    @Test
    public void append() {
        UndoableStringBuilder builder = new UndoableStringBuilder();
        builder.append("Hello");
        assertEquals("Hello", builder.toString());
    }

    @Test
    public void delete() {
        UndoableStringBuilder builder = new UndoableStringBuilder();
        builder.append("Hello");
        builder.delete(1, 4);
        assertEquals("Ho", builder.toString());
    }

    @Test
    public void undoAppend() {
        UndoableStringBuilder builder = new UndoableStringBuilder();
        builder.append("Hello");
        builder.undo();
        assertEquals("", builder.toString());
    }

    @Test
    public void UndoDelete() {
        UndoableStringBuilder builder = new UndoableStringBuilder();
        builder.append("Hello");
        builder.delete(1, 4);
        builder.undo();
        assertEquals("Hello", builder.toString());
    }
}