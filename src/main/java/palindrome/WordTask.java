package palindrome;

public class WordTask {

    private final long id;
    private final String word;
    private final String reversed;
    private boolean isPalindrome=false;
    private boolean isDefined=false;
    private String serviceHistory;

    public WordTask(long id, String word) {
        this.id = id;
        this.word = word;
        StringBuilder sb = new StringBuilder(word);
        this.reversed = sb.reverse().toString();
    }

    public long getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getReversed() {
        return reversed;
    }

    public boolean getPalindrome() { return isPalindrome; }
    public void setPalindrome(boolean isPalindrome) { this.isPalindrome = isPalindrome; }

    public boolean getDefined() { return isDefined; }
    public void setDefined(boolean isDefined) { this.isDefined = isDefined; }

    public String getServiceHistory() {
        return serviceHistory;
    }
    public void setServiceHistory(String history) {
        this.serviceHistory = history;
    }
}
