package io.harry.zealotboot.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "ajae_gag")
public class AjaeGag {
    @Id
    private String id;

    @NonNull
    private String url;

    private long createdAt;

    private boolean verified;

    public AjaeGag(String url) {
        this.url = url;
    }

    public AjaeGag(String url, long createdAt, boolean verified) {
        this.url = url;
        this.createdAt = createdAt;
        this.verified = verified;
    }
}
