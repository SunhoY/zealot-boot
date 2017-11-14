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

    public AjaeGag(String url) {
        this.url = url;
    }
}
