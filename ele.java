import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class VotingSystem {
    
    // This will store the hashed votes to maintain anonymity and prevent tampering.
    private Map<String, String> votes;
    
    // Constructor to initialize the vote storage
    public VotingSystem() {
        votes = new HashMap<>();
    }
    
    // Method to cast a vote (anonymously)
    public void castVote(String voterId, String vote) throws NoSuchAlgorithmException {
        // Hash the vote and store it using the voter's ID
        String hashedVote = hashVote(vote);
        
        // Ensure the voter can't vote more than once
        if (!votes.containsKey(voterId)) {
            votes.put(voterId, hashedVote);
            System.out.println("Vote cast successfully.");
        } else {
            System.out.println("You have already voted.");
        }
    }
    
    // Method to hash the vote using SHA-256 for security
    private String hashVote(String vote) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest(vote.getBytes());
        StringBuilder sb = new StringBuilder();
        
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }
        
        return sb.toString();
    }
    
    // Method to display voting results (for demonstration only)
    public void displayResults() {
        System.out.println("Voting Results (Hashed):");
        
        for (Map.Entry<String, String> entry : votes.entrySet()) {
            System.out.println("Voter ID: " + entry.getKey() + ", Vote: " + entry.getValue());
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VotingSystem votingSystem = new VotingSystem();
        
        try {
            System.out.println("Welcome to the Electronic Voting System.");
            
            while (true) {
                System.out.println("Enter your Voter ID:");
                String voterId = scanner.nextLine();
                
                System.out.println("Enter your vote:");
                String vote = scanner.nextLine();
                
                votingSystem.castVote(voterId, vote);
                
                System.out.println("Do you want to cast another vote? (yes/no)");
                String choice = scanner.nextLine();
                
                if (choice.equalsIgnoreCase("no")) {
                    break;
                }
            }
            
            votingSystem.displayResults();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("An error occurred while hashing the vote.");
        }
        
        scanner.close();
    }
}
