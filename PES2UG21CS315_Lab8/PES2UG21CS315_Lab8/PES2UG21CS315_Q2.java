import java.io.*;
import java.util.ArrayList;
import java.util.List;

class MusicTrack implements Serializable {
    private String title;
    private String artist;
    private String album;
    private double duration;

    public MusicTrack(String title, String artist, String album, double duration) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    // Other getters and setters...

    @Override
    public String toString() {
        return "Title: " + title + ", Artist: " + artist + ", Album: " + album + ", Duration: " + duration + " minutes";
    }
}

class MusicLibraryManager {
    private List<MusicTrack> musicLibrary;

    public MusicLibraryManager() {
        this.musicLibrary = new ArrayList<>();
        // Add some default tracks for testing
        musicLibrary.add(new MusicTrack("Neverland:Freefall", "TXT", "Chapter:Temptation", 4.5));
        musicLibrary.add(new MusicTrack("Fever", "Enhypen", "Fever", 3.2));
        musicLibrary.add(new MusicTrack("Deja Vu", "Ateez", "Zero:Fever", 3.2));
    }

    public void addMusicTrack(MusicTrack track) {
        musicLibrary.add(track);
    }

    public void removeMusicTrack(String title) {
        musicLibrary.removeIf(track -> track.getTitle().equals(title));
    }

    public void updateMusicTrack(String title, MusicTrack updatedTrack) {
        for (int i = 0; i < musicLibrary.size(); i++) {
            if (musicLibrary.get(i).getTitle().equals(title)) {
                musicLibrary.set(i, updatedTrack);
                break;
            }
        }
    }

    public void viewMusicLibrary() {
        musicLibrary.forEach(System.out::println);
    }

    public void serializeMusicLibrary(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(musicLibrary);
            System.out.println("Music library serialized successfully.");
        } catch (IOException e) {
            System.err.println("Error during serialization: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void deserializeMusicLibrary(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            musicLibrary = (List<MusicTrack>) ois.readObject();
            System.out.println("Music library deserialized successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error during deserialization: " + e.getMessage());
        }
    }
}

public class PES2UG21CS315_Q2 {
    public static void main(String[] args) {
        MusicLibraryManager libraryManager = new MusicLibraryManager();

        // Adding a new track
        MusicTrack newTrack = new MusicTrack("House of Cards", "BTS", "HYYN", 5.0);
        libraryManager.addMusicTrack(newTrack);

        // Viewing the music library
        System.out.println("Music Library:");
        libraryManager.viewMusicLibrary();

        // Serializing the music library to a file
        libraryManager.serializeMusicLibrary("musicLibrary.ser");

        // Removing a track
        libraryManager.removeMusicTrack("House of Cards");

        // Updating a track
        MusicTrack updatedTrack = new MusicTrack("Fever", "Enhypen", "Fever:1", 3.5);
        libraryManager.updateMusicTrack("Fever", updatedTrack);

        // Viewing the updated music library
        System.out.println("\nUpdated Music Library:");
        libraryManager.viewMusicLibrary();

        // Deserializing the music library from the file
        libraryManager.deserializeMusicLibrary("musicLibrary.ser");

        // Viewing the music library after deserialization
        System.out.println("\nMusic Library After Deserialization:");
        libraryManager.viewMusicLibrary();
    }
}
