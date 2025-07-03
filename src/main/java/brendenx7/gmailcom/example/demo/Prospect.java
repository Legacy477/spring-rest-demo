package brendenx7.gmailcom.example.demo;

public class Prospect {
    private String name;
    private String email;
    private boolean isDecisionMaker;

    public Prospect(String name, String email, boolean isDecisionMaker) {
        this.name = name;
        this.email = email;
        this.isDecisionMaker = isDecisionMaker;
    }

    // Getters
    public String getName() { return name; }
    public String getEmail() { return email; }
    public boolean isDecisionMaker() { return isDecisionMaker; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setDecisionMaker(boolean decisionMaker) { this.isDecisionMaker = decisionMaker; }

    // Equals based on email for comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Prospect)) return false;
        Prospect that = (Prospect) o;
        return this.email.equalsIgnoreCase(that.email);
    }

    @Override
    public int hashCode() {
        return email.toLowerCase().hashCode();
    }
}
