package AndreySemeynikov;

import java.util.ArrayList;
import java.util.List;

public class UndoableStringBuilder {
    private StringBuilder stringBuilder;
    private List<Command> commandHistory;

    public UndoableStringBuilder() {
        this.stringBuilder = new StringBuilder();
        this.commandHistory = new ArrayList<>();
    }

    public void append(String str) {
        stringBuilder.append(str);
        commandHistory.add(new AppendCommand(str));
    }

    public void delete(int start, int end) {
        String deletedSubstring = stringBuilder.substring(start, end);
        stringBuilder.delete(start, end);
        commandHistory.add(new DeleteCommand(start, deletedSubstring));
    }

    public void undo() {
        if (!commandHistory.isEmpty()) {
            Command lastCommand = commandHistory.remove(commandHistory.size() - 1);
            lastCommand.undo(stringBuilder);
        }
    }

    public String toString() {
        return stringBuilder.toString();
    }

    private interface Command {
        void undo(StringBuilder stringBuilder);
    }

    private class AppendCommand implements Command {
        private String str;

        public AppendCommand(String str) {
            this.str = str;
        }

        @Override
        public void undo(StringBuilder stringBuilder) {
            stringBuilder.delete(stringBuilder.length() - str.length(), stringBuilder.length());
        }
    }

    private class DeleteCommand implements Command {
        private int start;
        private String deletedSubstring;

        public DeleteCommand(int start, String deletedSubstring) {
            this.start = start;
            this.deletedSubstring = deletedSubstring;
        }

        @Override
        public void undo(StringBuilder stringBuilder) {
            stringBuilder.insert(start, deletedSubstring);
        }
    }
}

