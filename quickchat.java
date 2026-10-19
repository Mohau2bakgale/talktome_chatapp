mport java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuickChat {
    private final Scanner input = new Scanner(System.in);
    private final List<Message> messages = new ArrayList<>();
    private int totalSent = 0;

    public void startQuickChat() {
        System.out.println("Welcome to QuickChat.");

        int numberOfMessages = readInt("How many messages would you like to enter? ");

        while (messages.size() < numberOfMessages) {
            System.out.println("\n1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    sendMessage();
                    break;
                case 2:
                    System.out.println("Coming Soon.");
                    break;
                case 3:
                    System.out.println("Total messages sent: " + totalSent);
                    return;
                default:
                    System.out.println("Please choose 1, 2, or 3.");
            }
        }

        System.out.println("Total messages sent: " + totalSent);
    }

    private void sendMessage() {
        System.out.print("Enter recipient cell number: ");
        String recipient = input.nextLine().trim();

        System.out.print("Enter your message: ");
        String text = input.nextLine().trim();

        Message message = new Message(recipient, text, messages.size());

        if (!message.checkRecipientCell()) {
            System.out.println("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
            return;
        }

        if (!message.checkMessageLength()) {
            System.out.println("Please enter a message of less than 250 characters.");
            return;
        }

        System.out.println("Message ready to send.");

        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message");
        int action = readInt("Choose an option: ");

        if (action == 1) {
            message.markSent();
            messages.add(message);
            totalSent++;
            System.out.println("Message successfully sent.");
            System.out.println(message.getFullDetails());
        } else if (action == 2) {
            System.out.println("Press 0 to delete the message.");
        } else if (action == 3) {
            message.markStored();
            messages.add(message);
            System.out.println("Message successfully stored.");
        } else {
            System.out.println("Invalid choice; message disregarded.");
        }
    }

    public String printMessages() {
        StringBuilder result = new StringBuilder();
        for (Message message : messages) {
            if (message.isSent()) {
                result.append(message.getFullDetails()).append("\n");
            }
        }
        return result.toString();
    }

    public int returnTotalMessages() {
        return totalSent;
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = input.nextLine().trim();
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }
}
