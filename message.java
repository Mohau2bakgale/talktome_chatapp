import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;

public class Message {
    private static final AtomicLong NEXT_ID = new AtomicLong(1);

    private final String messageID;
    private final String recipientCell;
    private final String message;
    private final String messageHash;
    private boolean sent;
    private boolean received;
    private boolean read;
    private boolean stored;

    public Message(String recipientCell, String message, int messageNumber) {
        this.messageID = String.format("%010d", NEXT_ID.getAndIncrement());
        this.recipientCell = recipientCell;
        this.message = message == null ? "" : message.trim();
        this.messageHash = createMessageHash(messageNumber);
        this.sent = false;
        this.received = false;
        this.read = false;
        this.stored = false;
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    /*
     * Accepts a South African local number beginning with 0, or an
     * international +27 number. The assignment's supplied test data
     * includes 08575975889, so local 0-prefixed numbers are accepted.
     */
    public boolean checkRecipientCell() {
        if (recipientCell == null) return false;
        return recipientCell.matches("^0\\d{10}$")
                || recipientCell.matches("^\\+27\\d{9}$");
    }

    public boolean checkMessageLength() {
        return message.length() <= 250;
    }

    private String createMessageHash(int messageNumber) {
        String[] words = message.trim().split("\\s+");
        String first = words.length == 0 ? "" : cleanWord(words[0]);
        String last = words.length == 0 ? "" : cleanWord(words[words.length - 1]);

        String firstTwoID = messageID.substring(0, 2);
        return (firstTwoID + ":" + messageNumber + ":" + first + last)
                .toUpperCase(Locale.ROOT);
    }

    private String cleanWord(String word) {
        return word.replaceAll("[^A-Za-z0-9]", "");
    }

    public String getMessageID() { return messageID; }
    public String getRecipientCell() { return recipientCell; }
    public String getMessage() { return message; }
    public String getMessageHash() { return messageHash; }
    public boolean isSent() { return sent; }
    public boolean isReceived() { return received; }
    public boolean isRead() { return read; }
    public boolean isStored() { return stored; }

    public void markSent() {
        sent = true;
        received = true;
        read = false;
    }

    public void markReceived() { received = true; }
    public void markRead() { read = true; }
    public void markStored() { stored = true; }

    public String getFullDetails() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipientCell
                + "\nMessage: " + message;
    }

    @Override
    public String toString() {
        return getFullDetails();
    }
}
