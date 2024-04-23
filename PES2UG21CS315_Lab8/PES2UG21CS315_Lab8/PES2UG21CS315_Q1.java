import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

class WordFrequencyTask implements Callable<Map<String, Integer>> {
    private final List<String> words;

    public WordFrequencyTask(List<String> words) {
        this.words = words;
    }

    @Override
    public Map<String, Integer> call() {
        Map<String, Integer> wordFrequency = new HashMap<>();

        for (String word : words) {
            wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
        }

        return wordFrequency;
    }
}

class WordFrequencyCounter {
    private static final int THREAD_POOL_SIZE = 4;
    private final ExecutorService executorService;
    private final Map<String, Integer> globalWordFrequency;

    public WordFrequencyCounter() {
        this.executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        this.globalWordFrequency = new ConcurrentHashMap<>();
    }

    public void processFile(String filePath) throws IOException, InterruptedException, ExecutionException {
        List<String> allWords = readWordsFromFile(filePath);
        List<List<String>> wordChunks = splitIntoChunks(allWords);

        List<Future<Map<String, Integer>>> futures = new ArrayList<>();

        for (List<String> chunk : wordChunks) {
            WordFrequencyTask task = new WordFrequencyTask(chunk);
            futures.add(executorService.submit(task));
        }

        for (Future<Map<String, Integer>> future : futures) {
            mergeWordFrequency(future.get());
        }

        executorService.shutdown();
        outputWordFrequency();
    }

    private List<String> readWordsFromFile(String filePath) throws IOException {
        List<String> words = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineWords = line.split("\\s+");
                words.addAll(Arrays.asList(lineWords));
            }
        }

        return words;
    }

    private List<List<String>> splitIntoChunks(List<String> words) {
        int chunkSize = words.size() / THREAD_POOL_SIZE;
        List<List<String>> chunks = new ArrayList<>();

        for (int i = 0; i < words.size(); i += chunkSize) {
            int end = Math.min(i + chunkSize, words.size());
            chunks.add(words.subList(i, end));
        }

        return chunks;
    }

    private void mergeWordFrequency(Map<String, Integer> localWordFrequency) {
        localWordFrequency.forEach((word, count) -> globalWordFrequency.merge(word, count, Integer::sum));
    }

    private void outputWordFrequency() {
        globalWordFrequency.forEach((word, count) -> System.out.println(word + ": " + count));
    }
}

public class PES2UG21CS315_Q1 {
    public static void main(String[] args) {
        WordFrequencyCounter wordFrequencyCounter = new WordFrequencyCounter();
        try {
            wordFrequencyCounter.processFile("C:\\Users\\Praka\\OneDrive\\Documents\\JAVA\\sample.txt");
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
