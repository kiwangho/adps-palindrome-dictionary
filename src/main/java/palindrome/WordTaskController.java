package palindrome;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class WordTaskController {

    private static final Logger logger =
            LogManager.getLogger(WordTaskController.class.getName());

    private final AtomicLong counter = new AtomicLong();
    private final LinkedList<WordTask> pastRequests = new LinkedList<>();
    private final String thisClass = getClass().getSimpleName();
    private final int MaxStoredRequests = 100;  // Limit the number of requests stored


    @RequestMapping("/palindrome")
    public WordTask wordtask(@RequestParam(value="word") String input) {


        String me = thisClass + ": wordtask: ";
        String word;
        if (input==null || input.isEmpty()) {
            logger.info(me + "empty input. no action");
            word = "";
        } else {
            // Split by whitespace and only process first whole word
            // TODO process multiple words
            String [] words = input.split("\\s+");
            word = words[0];
        }
        logger.info(me + "Word to process = " + word );
        WordTask wordTask = new WordTask(counter.incrementAndGet(), word);

        // Check for palindrome
        wordTask.setPalindrome(PalindromeUtil.isPalindrome(word));

        // Check for valid word in Dictionary
        DictionaryAPIEntryList dictionaryWord = DictionaryAPI.lookupWord(word);
        wordTask.setDefined(dictionaryWord!=null && dictionaryWord.getEntries()!=null && dictionaryWord.getEntries().size()>0);

        // Updated list of past submissions
        synchronized (pastRequests) {
            if (pastRequests.size() >= MaxStoredRequests) {
                pastRequests.removeFirst();
            }
            pastRequests.addLast(wordTask);
        }
        StringBuilder history = new StringBuilder();
        for (WordTask g: pastRequests) {
            history.append(String.format("[id=%d,word=%s,reverse=%s,palindrome=%s,defined=%s]",
                    g.getId(),g.getWord(),g.getReversed(),g.getPalindrome()?"true":"false", g.getDefined()?"true":"false"));
        }
        wordTask.setServiceHistory(history.toString());
        logger.debug(me + "Exiting");
        return wordTask;
    }


 }
