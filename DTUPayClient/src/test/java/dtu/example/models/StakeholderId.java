package dtu.example.models;

import lombok.Getter;
import lombok.Value;

@Getter
public class StakeholderId {
    private String id;

    public StakeholderId(String id) {
        this.id = id;
    }

    public StakeholderId() {
    }
}
