package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class VideoGame {
    private String reviewScore;

    private String releaseDate;

    private String name;

    private String rating;

    private String id;

    private String category;
}
