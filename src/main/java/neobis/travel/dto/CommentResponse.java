package neobis.travel.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponse {
    private String userImage;
    private String fullName;
    private String comment;

    public CommentResponse(String userImage, String fullName, String comment) {
        this.userImage = userImage;
        this.fullName = fullName;
        this.comment = comment;
    }

    public CommentResponse() {
    }
}
