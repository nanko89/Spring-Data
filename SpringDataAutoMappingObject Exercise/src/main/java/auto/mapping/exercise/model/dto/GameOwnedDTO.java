package auto.mapping.exercise.model.dto;

public class GameOwnedDTO {
    private String title;

    public GameOwnedDTO() {
    }

    public GameOwnedDTO(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return  title;
    }
}
